package main.java.scene;

import main.java.entity.Player;
import main.java.entity.PowerUps;
import main.java.entity.Shot;
import main.java.manager.GameSceneManager;
import main.java.manager.KeyboardManager;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;


public class VSGameScene extends BaseScene {
    private static final long serialVersionUID = 1L;
    private static ArrayList<Shot> shots;
    private static ArrayList<Shot> addShots;
    private static ArrayList<Shot> enemy_shots;
    private static ArrayList<PowerUps> powerups;
    private static ArrayList<PowerUps> addpowerups;
    private static ArrayList<PowerUps.PowerUp> allpowerups;
    private static Player player;
    private static Player enemy;

    private boolean havewon = true;
    private final String expl = "/img/explosion.png";

    private int deaths = 0;

    private double angle = 0; // aiming angle for shooting, default straight
    private static final int offsetY=60;//57
    private String IP;
    private int Port;
    private ServerSocket ss;
    private Socket s;
    private Thread ComRThread;
    private Thread ComSThread;

    private final static long eventtime = 10000;
    private static long lastcheck = System.currentTimeMillis();
    private static final int rate = 100;
    private static Random random = new Random();

    public VSGameScene(GameSceneManager gsm,boolean isHost,String ip_address,int port) throws IOException {
        super(gsm);
        Port=port;
        IP=ip_address;
        init();
        ServerThread(isHost);
    }
    public boolean haveWon(){
        return this.havewon;
    }

    public static class Package{
        int playerx;
        int playery;
        int health;
        int shield;
        int damage;
        ArrayList<Shot> shots;
        ArrayList<PowerUps> powerups;

        public Package(int playerx,int playery,int damage,int health,int shield,ArrayList<Shot> shots,ArrayList<PowerUps> powerups){
            this.damage=damage;
            this.playerx=playerx;
            this.playery=playery;
            this.health=health;
            this.shield=shield;
            this.shots=shots;
            this.powerups=powerups;
        }

        public Package(String raw,boolean isHost){
            String[] str = raw.split("#");
            this.playerx=Integer.parseInt(str[0]);
            this.playery=Integer.parseInt(str[1]);
            this.damage=Integer.parseInt(str[2]);
            this.health=Integer.parseInt(str[3]);
            this.shield=Integer.parseInt(str[4]);
            String[] rawshot = str[5].split(";");
            shots = new ArrayList<>();
            try{
                for(String rs:rawshot){
                    String[] info = rs.split(",");
                    int x;
                    if(enemy.getShield()>0){
                        x=(int) (BOARD_WIDTH+0.6*enemy.getWidth()-Integer.parseInt(info[0]));
                    }else{
                        x=(int) (BOARD_WIDTH+0.35*enemy.getWidth()-Integer.parseInt(info[0]));
                    }
                    int y=BOARD_HEIGHT-offsetY+enemy.getHeight()-Integer.parseInt(info[1]);
                    double a=Double.parseDouble(info[2])+180;
                    VSGameScene.enemy_shots.add(new Shot(x,y,a));
                }
            }catch(Exception e){}

            powerups = new ArrayList<>();
            try {
                String[] rawpowerup = str[6].split(";");
                for (String rp : rawpowerup) {
                    String[] infor = rp.split(",");
                    int x = (BOARD_WIDTH - Integer.parseInt(infor[0]));
                    int y = BOARD_HEIGHT - offsetY+19 - Integer.parseInt(infor[1]);
                    if (!isHost) {
                        VSGameScene.powerups.add(new PowerUps(x, y, PowerUps.PowerUp.valueOf(infor[2])));
                    }
                    powerups.add(new PowerUps(x, y, PowerUps.PowerUp.valueOf(infor[2])));
                }
            }catch(Exception e){}
        }

        public String toString(){
            String str = "";
            str+=playerx+"#"+playery+"#"+damage+"#"+health+"#"+shield+"#";//x,y,a;x,y,a
            if(shots.size()<=0){
                str+=BOARD_WIDTH+100+","+BOARD_HEIGHT+100+","+0;
            }
            for(int i = 0;i<shots.size();i++){
                if(i!=0){
                    str+=";";
                }
                Shot s = shots.get(i);
                str+=s.getX()+","+s.getY()+","+s.getAngle();
            }
            str+="#";

            if(powerups.size()<=0){
                str+="none";
            }
            for(int i = 0;i<powerups.size();i++){
                if(i!=0){
                    str+=";";
                }
                PowerUps p = powerups.get(i);
                str+=p.getX()+","+p.getY()+","+p.getType().name();
            }
            str+="#";
            return str;
        }
    }

    private void ServerThread(boolean isHost) throws IOException {
        if(!isHost){
            s = new Socket(IP, Port);
        }else {
            ss = new ServerSocket(Port);
            s = ss.accept();
        }
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        DataInputStream dis = new DataInputStream(s.getInputStream());
        ComRThread = new Thread("Receive Thread"){
            @Override
            public void run() {
                while(gsm.isIngame()){
                    try{
                        String str = dis.readUTF();
                        Package p = new Package(str,isHost);
                        enemy.setMaxhealth(p.health,false);
                        enemy.setHealth(p.health);
                        enemy.setDamage(p.damage);
                        enemy.setShieldAmount(p.shield);
                        enemy.setX(BOARD_WIDTH-p.playerx);
                        enemy.setY(BOARD_HEIGHT-offsetY+8-p.playery);
                        if(enemy.getHealth()<=0){
                            enemy.setDying(true);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }
        };
        ComSThread = new Thread("Send Thread"){
            @Override
            public void run() {
                while(gsm.isIngame()){
                    try{
                        if(isHost) {
                            createPowerUps();
                        }
                        Package p = new Package(player.getX(),player.getY(),player.getDamage(),player.getHealth(),player.getShield(),addShots,addpowerups);
                        String data = p.toString();
                        addShots.clear();
                        addpowerups.clear();
                        dout.writeUTF(data);
                    }catch(Exception e){

                    }
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ComRThread.start();
        ComSThread.start();
    }

    private void init() {
        enemy = new Player(3,0,1000,1,true);
        player = new Player(3,0,1000,1);
        shots = new ArrayList<>();
        enemy_shots = new ArrayList<>();
        addShots=new ArrayList<>();
        powerups=new ArrayList<>();
        addpowerups=new ArrayList<>();
        allpowerups=new ArrayList<>();
        for(PowerUps.PowerUp pw: PowerUps.PowerUp.values()){
            allpowerups.add(pw);
        }
    }

    @Override
    public void update() {
        player.act();
        animationCycle();
    }

    @Override
    public void drawHealth(Graphics g) {
        int max = player.getMaxhealth();
        int hp = player.getHealth();
        g.setColor(Color.black);
        g.fillRect(0, 450, 200, 20);
        g.setColor(Color.red);
        g.fillRect(0, 450, 200/max*hp, 20);

        int shield = player.getShield();
        g.setColor(Color.cyan);
        int s = -200/max*shield;
        if(s<-200){
            s=-200;
        }
        g.fillRect(200, 450, s, 20);
    }

    @Override
    public void input(KeyboardManager keyboardManager) {

        // update the player movement on key events
        if (keyboardManager.left.down) {
            player.setDx(-2);
        } else if (keyboardManager.right.down) {
            player.setDx(2);
        } else {
            player.setDx(0);
        }

        if (keyboardManager.up.down) {
            player.setDy(-2);
        } else if (keyboardManager.down.down) {
            player.setDy(2);
        } else {
            player.setDy(0);
        }

        // shoot the bullet
        if (keyboardManager.space.down) {
            if (player.canShoot()) {
                PlayerShoot(player,angle);
            }
        }

        // change the shot angle
        if (keyboardManager.angleDec.clicked) {
            angle += 15;
        }
        if (keyboardManager.angleInc.clicked) {
            angle -= 15;
        }

        // check for quit
        //TODO Remove quit from game level and make into a paused screen that has an option to quit or resume
        if (keyboardManager.escape.clicked)
            gsm.ingame = false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
        drawPlayer(g);
        drawEnemy(g);
        drawShot(g);
        drawPowerUps(g);
        drawAim(g);
        drawHealth(g);
    }

    public void drawPowerUps(Graphics g) {
//        if (shot.isVisible())
//            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        for (int i = 0; i < powerups.size(); i++) {
            try {
                PowerUps pw = powerups.get(i);
                if (pw.isVisible() && !pw.isDying()) {
                    g.drawImage(pw.getImage(), pw.getX(), pw.getY(), this);
                }
            } catch (Exception e) {
                // porb null, current modify, out of bound
            }
        }
    }

    public void drawShot(Graphics g) {
//        if (shot.isVisible())
//            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        for (int i = 0; i < shots.size(); i++) {
            try {
                Shot shot = shots.get(i);
                if (shot.isVisible() && !shot.isDying())
                    g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            } catch (Exception e) {
                // porb null, current modify, out of bound
            }
        }
        for (int i = 0; i < enemy_shots.size(); i++) {
            try {
                Shot shot = enemy_shots.get(i);
                if (shot.isVisible() && !shot.isDying())
                    g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            } catch (Exception e) {
                // porb null, current modify, out of bound
            }
        }
    }

    public void drawAim(Graphics g) {
        if (gsm.ingame) {
            g.setColor(Color.WHITE);

            g.drawLine(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, // from the center of the
                    // player
                    (int) (player.getX() + player.getWidth() / 2 - 20 * -Math.cos(angle * Math.PI / 180.0)), // draw end of
                    // line x a
                    // distance of
                    // 20 adj. for
                    // angle from
                    // center of
                    // player
                    (int) (player.getY() + player.getHeight() / 2 - 20 * Math.sin(angle * Math.PI / 180.0))); // draw end of
            // line y a
            // distance of
            // 20 adj. for
            // angle from
            // center of
            // player
        }
    }

    public void PlayerShoot(Player player,double angle){
        int m = player.getMultiTrajectoryProjectiles();
        int i = m / 2;
        int x,y;

        if(player.getShield()>0){
            x = player.getX() + (player.getWidth() / 5);
            y = player.getY() + (player.getHeight() / 5);
        }else{
            x = player.getX();
            y = player.getY();
        }

        for (int a = 0; a < i; a++) {
            int b = a - i;
            addShots.add(new Shot(x - 5 * b, y,angle));
            addShots.add(new Shot(x + 5 * b, y,angle));
            shots.add(new Shot(x - 5 * b, y,angle));
            shots.add(new Shot(x + 5 * b, y,angle));
        }
        if (m % 2 != 0) {
            shots.add(new Shot(x, y,angle));
            addShots.add(new Shot(x, y,angle));
        }
        /*
         * if(player.isDoubleTrajectoryProjectiles()) { shots.add(new Shot(x-5, y));
         * shots.add(new Shot(x+5, y)); }else{ shots.add(new Shot(x, y)); }
         */
    }


    private void createPowerUps(){
        // this should only be called by server
        if(lastcheck<=System.currentTimeMillis()){
            lastcheck=System.currentTimeMillis()+eventtime;
            if(random.nextInt(100)<rate){
                PowerUps pw = new PowerUps(random.nextInt(BOARD_WIDTH-100)+50,random.nextInt(BOARD_HEIGHT-100)+100,getRandomPowerUp());
                powerups.add(pw);
                addpowerups.add(pw);
            }
        }
    }

    private PowerUps.PowerUp getRandomPowerUp(){
        try {
            return allpowerups.get(random.nextInt(allpowerups.size()+1));
        }catch(Exception e) {
            return null;
        }
    }
    private void checkPowerUps(){
        ArrayList<PowerUps> temp = new ArrayList<>();
        for (int i = 0; i < powerups.size(); i++) {
            try {
                PowerUps p = powerups.get(i);
                if (p.isVisible()) {
                    int pX = p.getX();
                    int pY = p.getY();

                    int enemyX = enemy.getX();
                    int enemyY = enemy.getY();

                    if (enemy.isVisible() && !p.isDying()&&p.isVisible() && !enemy.isDying()) {
                        if (pX >= (enemyX-enemy.getWidth()-1) && pX <= (enemyX + enemy.getWidth()+1) && pY >= (enemyY-enemy.getHeight()-1)
                                && pY <= (enemyY + enemy.getHeight()-1)) {
                            p.die();
                            temp.add(p);
                        }
                    }

                    int playerX = player.getX();
                    int playerY = player.getY();
                    if (player.isVisible() && !p.isDying()&&p.isVisible() && !player.isDying()) {
                        if (pX >= (playerX-player.getWidth()/2) && pX <= (playerX + player.getWidth()/2) && pY >= (playerY-player.getHeight()/2)
                                && pY <= (playerY + player.getHeight()/2)) {
                            p.die();
                            temp.add(p);
                            PowerUps.PowerUp type = p.getType();
                            switch(type){
                                case MaxHealth:
                                    player.setMaxhealth(player.getMaxhealth(),true);
                                    break;
                                case Shield:
                                    player.addShieldAmount(1);
                                    break;
                                case HealthRecovery:
                                    player.addHealth(1);
                                    break;
                                case MultiProjectile:
                                    player.setMultiTrajectoryProjectiles(player.getMultiTrajectoryProjectiles()+1);
                                    break;
                                case ShootingCooldown:
                                    player.setShotCD(player.getShooingCD()-200);
                                    break;
                                case Damage:
                                    player.setDamage(player.getDamage()+1);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }catch (Exception e){}
        }
        powerups.removeAll(temp);
        temp.clear();
    }

    @Override
    public void dispose() {
    }

    public void drawPlayer(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            player.die();
            havewon = false;
            // TODO go to the game over screen when the player dies
            gsm.addScene(new GameOver(gsm), true);
        }
    }
    public void drawEnemy(Graphics g) {
        if (enemy.isVisible()) {
            g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
        }

        if (enemy.isDying()) {
            enemy.die();
            havewon = true;
            // TODO go to the game over screen when the enemy dies
            gsm.addScene(new Won(gsm), true);
        }
    }
    public void animationCycle() {
        if(enemy.getHealth()<=0){
            ImageIcon ii = new ImageIcon(getClass().getResource(expl));
            enemy.setImage(ii.getImage());
            enemy.setDying(true);
            deaths++;
        }

        if (deaths == 1) {
            // player won
            // TODO add message to the Won scene
            // message = SpaceInvaders.lang.getEndingWinMessage();
            gsm.addScene(new Won(gsm), true);
        }
        //powerup
        checkPowerUps();

        // player

        player.act();
        if(player.getShield()>0) {
            player.setShielded();
        }else{
            player.setUnShielded();
        }
        enemy.act();
        if(enemy.getShield()>0) {
            enemy.setShielded();
        }else{
            enemy.setUnShielded();
        }

        // new shooting
        ArrayList<Shot> temp = new ArrayList<>();
        for (int i = 0; i < shots.size(); i++) {
            try {
                Shot shot = shots.get(i);
                if (shot.isVisible()) {
                    int shotX = shot.getX();
                    int shotY = shot.getY();

                    int enemyX = enemy.getX();
                    int enemyY = enemy.getY();

                    if (enemy.isVisible() && shot.isVisible() && !enemy.isDying()) {
                        if (shotX >= (enemyX) && shotX <= (enemyX + enemy.getWidth()) && shotY >= (enemyY)
                                && shotY <= (enemyY + enemy.getHeight())) {
                            shot.die();
                            temp.add(shot);
                        }
                    }


                    // do aiming code here
                    // do shot.setY and .setX to fit position of aiming place
                    // using mouse or keyboard.

                    Point p = MouseInfo.getPointerInfo().getLocation();
                    int mouseX = p.x;
                    int mouseY = p.y;
                    // do trig between mouse and spaceship to know trajectory
                    // include to update X as well
                    // SHOT POSITION UPDATING LINE
                    int y = shot.getY();
                    int x = shot.getX();
                    // shooting angle

                    double rads = shot.getAngle() * Math.PI / 180.0;

                    // SHOT TRAVEL SPEED
                    int shotSpeed = 8;
                    // shot direction in x and y coordinates

                    x += (int) (shotSpeed * Math.cos(rads));
                    y -= (int) (shotSpeed * Math.sin(rads));
                    if (y < 0 || x < 0 || x > BOARD_WIDTH || y > BOARD_HEIGHT) {
                        shot.die();
                        temp.add(shot);
                    } else {
                        shot.setY(y);
                        shot.setX(x);
                    }
                }
            } catch (Exception e) {
            }
        }
        shots.removeAll(temp);

        temp.clear();
        for (int i = 0; i < enemy_shots.size(); i++) {
            try {
                Shot shot = enemy_shots.get(i);
                if (shot.isVisible()) {
                    int shotX = shot.getX();
                    int shotY = shot.getY();

                    int playerX = player.getX();
                    int playerY = player.getY();

                    if (player.isVisible() && !shot.isDying()) {
                        if (shotX >= (playerX-player.getWidth()/2) && shotX <= (playerX + player.getWidth()/2) && shotY >= (playerY- player.getHeight()/3)//test
                                && shotY <= (playerY + player.getHeight()/3)) {
                            ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
                            if(player.damage()<=0) {
                                player.setImage(ii.getImage());
                                player.setDying(true);
                            }
                            shot.die();
                            temp.add(shot);
                        }
                    }

                    int y = shot.getY();
                    int x = shot.getX();
                    // shooting angle

                    double rads = shot.getAngle() * Math.PI / 180.0;

                    // SHOT TRAVEL SPEED
                    int shotSpeed = 8;
                    // shot direction in x and y coordinates

                    x += (int) (shotSpeed * Math.cos(rads));
                    y -= (int) (shotSpeed * Math.sin(rads));
                    if (y < 0 || x < 0 || x > BOARD_WIDTH || y > BOARD_HEIGHT) {
                        shot.die();
                        temp.add(shot);
                    } else {
                        shot.setY(y);
                        shot.setX(x);
                    }
                }
            } catch (Exception e) {
            }
        }
        shots.removeAll(temp);

    }
}