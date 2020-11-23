package main.java.entity;

import javax.swing.*;

public class PowerUps extends Sprite {
    public enum PowerUp{MaxHealth,Shield,HealthRecovery,MultiProjectile,ShootingCooldown,Damage};
    private String maxhp = "/img/PowerUps/maxhp.png";
    private String shield = "/img/PowerUps/shield.png";
    private String hp_recovery = "/img/PowerUps/recovery.png";
    private String multi_projectile = "/img/PowerUps/multi_projectile.png";
    private String shooting_cooldown = "/img/PowerUps/shooting_cooldown.png";
    private String damage = "/img/PowerUps/damage.png";
    private String random = "/img/PowerUps/random.png";

    private final int H_SPACE = 6;
    private final int V_SPACE = 1;
    private PowerUp type;
    public PowerUps() {
    }
public PowerUp getType(){
        return this.type;
}
    public PowerUps(int x, int y, PowerUp type) {
        this.type = type;
        ImageIcon ii;
        switch(type){
    case MaxHealth:
        ii = new ImageIcon(this.getClass().getResource(maxhp));
        break;
    case Shield:
        ii = new ImageIcon(this.getClass().getResource(shield));
        break;
    case HealthRecovery:
        ii = new ImageIcon(this.getClass().getResource(hp_recovery));
        break;
    case MultiProjectile:
        ii = new ImageIcon(this.getClass().getResource(multi_projectile));
        break;
    case ShootingCooldown:
        ii = new ImageIcon(this.getClass().getResource(shooting_cooldown));
        break;
    case Damage:
        ii = new ImageIcon(this.getClass().getResource(damage));
        break;
    default://random
        ii = new ImageIcon(this.getClass().getResource(random));
        break;
}

        setImage(ii.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}