package LP.entities.bullets;

import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.entities.Damage;
import mindustry.entities.Units;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;

public class HellbladeBulletType extends ContinuousLaserBulletType{

    public float splashDamageMultiplier = 1.2f;
    public float splashRange = 0f;
    public float velocityDamageMultiplier = 0.05f;
    public float velocityThreshold = 2f;
    public float maxDamageMultiplier = 5f;

    public HellbladeBulletType(){
    }

    @Override
    public void init(Bullet b){
        super.init(b);
        if(splashRange <= 0f){
            splashRange = width * 8;
        }
    }

    @Override
    public void update(Bullet b){
        if(!continuous) return;

        applyDamage(b);

        if(shake > 0){
            mindustry.entities.Effect.shake(shake, shake, b);
        }

        updateBulletInterval(b);
    }

    @Override
    public void applyDamage(Bullet b){
        float damage = b.damage;
        if(timescaleDamage && b.owner instanceof Building build){
            b.damage *= build.timeScale();
        }

        float currentDamage = b.damage;
        float length = currentLength(b);
        float rot = b.rotation();

        Vec2 bulletVel = Tmp.v1.set(b.vel);
        float x2 = b.x + Tmp.v2.trns(rot, length).x;
        float y2 = b.y + Tmp.v2.y;

        if(b.type.collidesGround && b.type.collidesTiles){
            mindustry.core.World.raycastEachWorld(b.x, b.y, x2, y2, (cx, cy) -> {
                Building build = mindustry.Vars.world.build(cx, cy);
                if(build != null && build.team != b.team && build.collide(b) && b.checkUnderBuild(build, cx * mindustry.Vars.tilesize, cy * mindustry.Vars.tilesize)){
                    float buildX = cx * mindustry.Vars.tilesize + mindustry.Vars.tilesize / 2f;
                    float buildY = cy * mindustry.Vars.tilesize + mindustry.Vars.tilesize / 2f;
                    
                    float splashDamage = currentDamage * splashDamageMultiplier;
                    Damage.damage(b.team, buildX, buildY, splashRange, splashDamage);
                }
                return false;
            });
        }

        Units.nearbyEnemies(b.team, Tmp.r1.setPosition(b.x, b.y).setSize(x2 - b.x, y2 - b.y).normalize().grow(3f * 2), u -> {
            if(u.checkTarget(b.type.collidesAir, b.type.collidesGround) && u.hittable()){
                u.hitbox(Tmp.r2);
                Vec2 hitPoint = arc.math.geom.Geometry.raycastRect(b.x, b.y, x2, y2, Tmp.r2.grow(3f * 2));
                if(hitPoint != null){
                    float relativeVelocity = Tmp.v3.set(u.vel).sub(bulletVel).len();
                    float velocityMultiplier = 1f + (relativeVelocity / velocityThreshold) * velocityDamageMultiplier;
                    float finalMultiplier = Math.min(velocityMultiplier, maxDamageMultiplier);
                    float adjustedDamage = currentDamage * finalMultiplier;

                    float shieldDamage = adjustedDamage * b.type.shieldDamageMultiplier;
                    float armorDamage = adjustedDamage * b.type.armorMultiplier;

                    if(u.shield > 0){
                        u.shield -= shieldDamage;
                        if(u.shield <= 0){
                            float remainingDamage = -u.shield;
                            u.shield = 0;
                            float armor = u.armor();
                            float damageToHealth = remainingDamage > armor * b.type.armorMultiplier ? remainingDamage - armor * b.type.armorMultiplier : remainingDamage * 0.1f;
                            if(damageToHealth > 0){
                                u.health -= damageToHealth;
                            }
                        }
                    }else{
                        float armor = u.armor();
                        float damageToHealth = armorDamage > armor ? armorDamage - armor : armorDamage * 0.1f;
                        if(damageToHealth > 0){
                            u.health -= damageToHealth;
                        }
                    }

                    if(u.health <= 0 && !u.dead()){
                        u.kill();
                    }

                    float splashDamage = currentDamage * splashDamageMultiplier;
                    Damage.damage(b.team, hitPoint.x, hitPoint.y, splashRange, splashDamage);
                }
            }
        });

        Damage.collideLine(b, b.team, b.x, b.y, rot, length, largeHit, laserAbsorb, pierceCap);

        b.damage = damage;
    }
}