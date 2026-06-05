package LP.entities.bullets;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.entities.Units;
import arc.util.Tmp;

public class SplashKnockbackBulletType extends BasicBulletType {
    public float splashKnockback = 40f;
    public float splashKnockbackRadius = 96f;

    public SplashKnockbackBulletType(float speed, float lifetime, float damage){
        super(speed, damage);
        this.lifetime = lifetime;
    }

    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b, x, y);
        
        Units.nearbyEnemies(b.team, x, y, splashKnockbackRadius, unit -> {
            if(unit.checkTarget(collidesAir, collidesGround)){
                Tmp.v3.set(unit).sub(x, y).nor().scl(splashKnockback * 80f);
                unit.impulse(Tmp.v3);
            }
        });
    }

    @Override
    public void hit(Bullet b){
        super.hit(b);
        
        Units.nearbyEnemies(b.team, b.x, b.y, splashKnockbackRadius, unit -> {
            if(unit.checkTarget(collidesAir, collidesGround)){
                Tmp.v3.set(unit).sub(b.x, b.y).nor().scl(splashKnockback * 80f);
                unit.impulse(Tmp.v3);
            }
        });
    }
}
