package main.java.entity;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import main.java.util.Commons;
/**
 *
 * @author
 */

public class Player extends Sprite implements Commons {
	// Test Commit
	private final int START_Y = 400;
	private final int START_X = 270;

	private final String player = "/img/craft.png";
	private final String player_shield = "/img/craft_shield.png";
	private int width;
	private int height;
	private int maxhealth;
	private int health;
	private long cd_shot;
	private long current_cd_shot;
	private int ShieldAmount;
	private ImageIcon ii;
	private ImageIcon ii_shield;

	private int MultiTrajectoryProjectiles;

	/*
	 * Constructor
	 */
	public Player() {
		ii = new ImageIcon(this.getClass().getResource(player));
		ii_shield = new ImageIcon(this.getClass().getResource(player_shield));
		//isDoubleTrajectoryProjectiles=true;
		MultiTrajectoryProjectiles=1;
		ShieldAmount=0;
		width = ii.getImage().getWidth(null);
		height = ii.getImage().getHeight(null);
		maxhealth = DEFAULT_MAX_HEALTH;
		health = maxhealth;
		cd_shot = DEFAULT_SHOT_CD;
		current_cd_shot = System.currentTimeMillis();
		setImage(ii.getImage());
		setX(START_X);
		setY(START_Y);
	}

	public void setShieldAmount(int amount){
		ShieldAmount=amount;
	}

	public void addShieldAmount(int amount){
		ShieldAmount+=amount;
	}

	public int getShield(){
		return ShieldAmount;
	}

	public void setUnShielded(){
		width = ii.getImage().getWidth(null);
		height = ii.getImage().getHeight(null);
		setImage(ii.getImage());
	}
	public void setShielded(){
		width = ii_shield.getImage().getWidth(null);
		height = ii_shield.getImage().getHeight(null);
		setImage(ii_shield.getImage());
	}
	/*
	public void setDoubleTrajectoryProjectiles(boolean b){
		this.isDoubleTrajectoryProjectiles=b;
	}
	public boolean isDoubleTrajectoryProjectiles(){
		return this.isDoubleTrajectoryProjectiles;
	}
	*/

	public void setMultiTrajectoryProjectiles(int num){
		if (num>1){
			MultiTrajectoryProjectiles=num;
		}else{
			MultiTrajectoryProjectiles=1;
		}
	}
	public int getMultiTrajectoryProjectiles(){
		return MultiTrajectoryProjectiles;
	}

	public int damage(int damage) {
		if(ShieldAmount>0){
			ShieldAmount-=damage;
			if(ShieldAmount<0){
				health += ShieldAmount;
				ShieldAmount=0;
			}
		}else {
			health -= damage;
		}
		return health;
	}
	public boolean canShoot(){
		long time = System.currentTimeMillis();
		if(time>current_cd_shot){
			current_cd_shot=time+cd_shot;
			return true;
		}
		return false;
	}
	public int damage() {
		if(ShieldAmount>0){
			ShieldAmount--;
		}else {
			health --;
		}
		return health;
	}

	public int getDefaultMaxhealth() {
		return DEFAULT_MAX_HEALTH;
	}

	public int getMaxhealth() {
		return maxhealth;
	}

	public boolean setShotCD(long newCD){
		if(newCD> MIN_SHOT_CD) {
			this.cd_shot = newCD;
			return true;
		}else{
			return false;
		}
	}

	public int addHealth(){
		health++;
		if(health>maxhealth){
			health=maxhealth;
		}
		return health;
	}

	public int getHealth(){
		return health;
	}

	public int addHealth(int i){
		health+=i;
		if(health>maxhealth){
			health=maxhealth;
		}
		return health;
	}

	public int setMaxhealth(int newMaxHealth, boolean toaddextra) {
		int i = newMaxHealth - maxhealth;
		maxhealth = newMaxHealth;
		if (toaddextra) {
			addHealth(i);
		}
		return health;
	}
	public void act() {
		x += dx;
		y += dy;
		if (x <= 2)
			x = 2;
		if (x >= BOARD_WIDTH - 2 * width)
			x = BOARD_WIDTH - 2 * width;
		if (y <= 2)
			y = 2;
		if (y >= BOARD_HEIGHT - 3 * height)
			y = BOARD_HEIGHT - 3 * height;

	}

	public void keyPressed(KeyEvent e) { // SHOULD DO WASD OR ARROW KEYS
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = -2;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -2;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 2;
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}

}