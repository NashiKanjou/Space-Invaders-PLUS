package main.java.scene;

import main.java.entity.Player;
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


public class VSGameScene extends BaseScene {
    private static final long serialVersionUID = 1L;
    private static ArrayList<Shot> shots;
    private static ArrayList<Shot> addShots;
    private static ArrayList<Shot> enemy_shots;
    private static Player player;
    private static Player enemy;

    private boolean havewon = true;
    private final String expl = "/img/explosion.png";

    private int deaths = 0;

    private double angle = 0; // aiming angle for shooting, default straight
    private static final int offsetY=70;
    private String IP;
    private int Port;
    private ServerSocket ss;
    private Socket s;
    private Thread ComRThread;
    private Thread ComSThread;

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
        ArrayList<Shot> shots;

        public Package(int playerx,int playery,int health,int shield,ArrayList<Shot> shots){
            this.playerx=playerx;
            this.playery=playery;
            this.health=health;
            this.shield=shield;
            this.shots=shots;
        }

        public Package(String raw){
            String[] str = raw.split("#");
            this.playerx=Integer.parseInt(str[0]);
            this.playery=Integer.parseInt(str[1]);
            this.health=Integer.parseInt(str[2]);
            this.shield=Integer.parseInt(str[3]);
            if(str.length>=5){
                String[] rawshot = str[4].split(";");
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
            }else{
                shots = new ArrayList<>();
            }
        }

        public String toString(){
            String str = "";
            str+=playerx+"#"+playery+"#"+health+"#"+shield;//x,y,a;x,y,a
            for(int i = 0;i<shots.size();i++){
                if(i!=0){
                    str+=";";
                }else{
                    str+="#";
                }
                Shot s = shots.get(i);
                str+=s.getX()+","+s.getY()+","+s.getAngle();
            }
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
                        Package p = new Package(str);
                        enemy.setHealth(p.health);
                        enemy.setShieldAmount(p.shield);
                        enemy.setX(BOARD_WIDTH-p.playerx);
                        enemy.setY(BOARD_HEIGHT-offsetY-p.playery);
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
                        Package p = new Package(player.getX(),player.getY(),player.getHealth(),player.getShield(),addShots);
                        String data = p.toString();
                        addShots.clear();
                        dout.writeUTF(data);
                    }catch(Exception e){

                    }

                }
            }
        };
        ComRThread.start();
        ComSThread.start();
    }

    private void init() {
        enemy = new Player(5,5,500,3,true);
        //player = new Player();
        player = new Player(5,0,500,3);
        shots = new ArrayList<>();
        enemy_shots = new ArrayList<>();
        addShots=new ArrayList<>();
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
        drawAim(g);
        drawHealth(g);
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
        if (deaths == 1) {
            // player won
            // TODO add message to the Won scene
            // message = SpaceInvaders.lang.getEndingWinMessage();
            gsm.addScene(new Won(gsm), true);
        }

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
                            if(enemy.damage()<=0){
                                ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                                enemy.setImage(ii.getImage());
                                enemy.setDying(true);
                                deaths++;
                            }
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
                        if (shotX >= (playerX-player.getWidth()/2) && shotX <= (playerX + player.getWidth()/2) && shotY >= (playerY)//test
                                && shotY <= (playerY + player.getHeight())) {
                            ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
                            int hp = player.damage();
                            if(hp<=0) {
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