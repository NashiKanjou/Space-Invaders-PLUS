package main.java.entity;


import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import main.java.graphics.AnimatedSprite;
import main.java.graphics.Sprite;
import main.java.manager.AnimationManager;
import main.java.util.Commons;

import javax.swing.*;
import java.awt.event.KeyEvent;
/**
 *
 * @author
 */

public class Player implements Commons {
	// Test Commit
	private final int START_Y = 400;
	private final int START_X = (BOARD_WIDTH - PLAYER_WIDTH) / 2;
	private final int Enemy_START_Y = 0;
	private final int Enemy_START_X = 270;

	private final String player = "/img/craft.png";
	private final String player_shield = "/img/craft_shield.png";
	private final String enemy = "/img/enemy.png";
	private final String enemy_shield = "/img/enemy_shield.png";
	private int width;
	private int height;
	private int maxhealth;
	private int health;
	private long cd_shot;
	private long current_cd_shot;
	private int ShieldAmount;
	private ImageIcon ii;
	private ImageIcon ii_shield;
	private int width_min;
	private int height_min;

	private int damage;
	private int MultiTrajectoryProjectiles;

	private AnimatedSprite animatedSprite;

	/*
	 * Constructor
	 */
	public Player(int health, int shield, long shooting_cooldown, int MultiProjectiles) {
		ii = new ImageIcon(this.getClass().getResource(player));
		ii_shield = new ImageIcon(this.getClass().getResource(player_shield));
		MultiTrajectoryProjectiles=MultiProjectiles;
		ShieldAmount=shield;
		width = ii.getImage().getWidth(null);
		height = ii.getImage().getHeight(null);
		height_min = ii.getImage().getHeight(null);
		width_min=ii.getImage().getWidth(null);
		maxhealth = health;
		this.health = maxhealth;
		cd_shot = shooting_cooldown;
		current_cd_shot = System.currentTimeMillis();
//		setImage(ii.getImage());
//		setX(START_X);
//		setY(START_Y);

		animatedSprite = AnimationManager.getInstance().get(AnimationManager.Assets.PLAYER_IDLE);
		animatedSprite.setX(START_X);
		animatedSprite.setY(START_Y);
		animatedSprite.setDying(false);
		animatedSprite.setVisible(true);
	}

	public Player(int health, int shield, long shooting_cooldown, int MultiProjectiles, boolean isEnemy) {
		if(isEnemy) {
			ii = new ImageIcon(this.getClass().getResource(enemy));
			ii_shield = new ImageIcon(this.getClass().getResource(enemy_shield));
//			setX(Enemy_START_X);
//			setY(Enemy_START_Y);
		}else{
			ii = new ImageIcon(this.getClass().getResource(player));
			ii_shield = new ImageIcon(this.getClass().getResource(player_shield));
//			setX(START_X);
//			setY(START_Y);
		}
		MultiTrajectoryProjectiles=MultiProjectiles;
		ShieldAmount=shield;
		width = ii.getImage().getWidth(null);
		height = ii.getImage().getHeight(null);
		height_min = ii.getImage().getHeight(null);
		width_min=ii.getImage().getWidth(null);
		maxhealth = health;
		this.health = maxhealth;
		cd_shot = shooting_cooldown;
		current_cd_shot = System.currentTimeMillis();
//		setImage(ii.getImage());

	}
	public Player(int health, int shield, long shooting_cooldown, int MultiProjectiles,int damage,boolean isEnemy) {
		if(isEnemy) {
			ii = new ImageIcon(this.getClass().getResource(enemy));
			ii_shield = new ImageIcon(this.getClass().getResource(enemy_shield));
//			setX(Enemy_START_X);
//			setY(Enemy_START_Y);
		}else{
			ii = new ImageIcon(this.getClass().getResource(player));
			ii_shield = new ImageIcon(this.getClass().getResource(player_shield));
//			setX(START_X);
//			setY(START_Y);
		}
		MultiTrajectoryProjectiles=MultiProjectiles;
		ShieldAmount=shield;
		width = ii.getImage().getWidth(null);
		height = ii.getImage().getHeight(null);
		height_min = ii.getImage().getHeight(null);
		width_min=ii.getImage().getWidth(null);
		maxhealth = health;
		this.health = maxhealth;
		cd_shot = shooting_cooldown;
		current_cd_shot = System.currentTimeMillis();
//		setImage(ii.getImage());
		this.damage=damage;
	}

	public Player() {
		ii = new ImageIcon(this.getClass().getResource(player));
		ii_shield = new ImageIcon(this.getClass().getResource(player_shield));
		//isDoubleTrajectoryProjectiles=true;
		MultiTrajectoryProjectiles=1;
		ShieldAmount=0;
		width = ii.getImage().getWidth(null);
		height = ii.getImage().getHeight(null);
		height_min = ii.getImage().getHeight(null);
		width_min=ii.getImage().getWidth(null);
		maxhealth = DEFAULT_MAX_HEALTH;
		health = maxhealth;
		cd_shot = DEFAULT_SHOT_CD;
		current_cd_shot = System.currentTimeMillis();
//		setImage(ii.getImage());
//		setX(START_X);
//		setY(START_Y);


//		sprite = assetManager.get(AssetManager.Assets.PLAYER_IDLE);
		animatedSprite = AnimationManager.getInstance().get(AnimationManager.Assets.PLAYER_IDLE);
		if (animatedSprite == null) {
			System.out.println("no animated sprite");
		}
		animatedSprite.setX(START_X);
		animatedSprite.setY(START_Y);
		animatedSprite.setDying(false);
		animatedSprite.setVisible(true);
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
		width = animatedSprite.getImage().getWidth(null);
		height = animatedSprite.getImage().getHeight(null);
		animatedSprite.setImage(ii.getImage());
	}
	public void setShielded(){
		width = ii_shield.getImage().getWidth(null);
		height = ii_shield.getImage().getHeight(null);
		animatedSprite.setImage(ii_shield.getImage());
	}
	public void setDamage(int damage){
		this.damage=damage;
	}
	public int getDamage(){
		return this.damage;
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

	public long getShooingCD(){
		return this.cd_shot;
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
	public void setHealth(int newhealth){
		health = newhealth;
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

	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}

	public void act() {
		var x = animatedSprite.getX();
		var y = animatedSprite.getY();

		x += animatedSprite.getDx() * PLAYER_SPEED;
		y += animatedSprite.getDy() * PLAYER_SPEED;

		animatedSprite.setX(x);
		animatedSprite.setY(y);

		checkBounds();
	}

	private void checkBounds() {
		var y = animatedSprite.getY();
		var x = animatedSprite.getX();

		if (x <= 2)
			animatedSprite.setX(2);
		if (x >= BOARD_WIDTH - width)
			animatedSprite.setX(BOARD_WIDTH - width);

		// TODO Make the max height the player can go to be the lowest row in the aliens
		if (y <= 2)
			animatedSprite.setY(2);

		if (y >= BOARD_HEIGHT - (BOARD_HEIGHT - GROUND) - width)
			animatedSprite.setY(BOARD_HEIGHT - (BOARD_HEIGHT - GROUND) - width);
	}

	public Sprite getSprite() {
		return animatedSprite;
	}


	public AnimatedSprite getAnimatedSprite() {
		return animatedSprite;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		var dx = animatedSprite.getDx();
		var dy = animatedSprite.getDy();

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
		var dx = animatedSprite.getDx();
		var dy = animatedSprite.getDy();

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