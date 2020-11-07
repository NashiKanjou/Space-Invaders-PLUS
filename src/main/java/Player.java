import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 *
 * @author
 */
public class Player extends Sprite implements Commons {
	// Test Commit
	private final int START_Y = 400;
	private final int START_X = 270;

	private final String player = "/img/craft.png";
	private int width;
	private int height;
	private int maxhealth;
	private int health;
	private long cd_shot;
	private long current_cd_shot;

	private int MultiTrajectoryProjectiles;

	/*
	 * Constructor
	 */
	public Player() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(player));
		//isDoubleTrajectoryProjectiles=true;
		MultiTrajectoryProjectiles=1;

		width = ii.getImage().getWidth(null);
		height = ii.getImage().getHeight(null);
		maxhealth = default_maxhealth;
		health = maxhealth;
		cd_shot = default_shot_cd;
		current_cd_shot = System.currentTimeMillis();
		setImage(ii.getImage());
		setX(START_X);
		setY(START_Y);
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
		health -= damage;
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
		health--;
		return health;
	}

	public int getDefaultMaxhealth() {
		return default_maxhealth;
	}

	public int getMaxhealth() {
		return maxhealth;
	}

	public boolean setShotCD(long newCD){
		if(newCD>min_shot_cd) {
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
		if (y >= BOARD_HEIGTH - 3 * height)
			y = BOARD_HEIGTH - 3 * height;

	}

	public void keyPressed(KeyEvent e) {
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