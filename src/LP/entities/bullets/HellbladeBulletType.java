package LP.entities.bullets;

import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;

public class HellbladeBulletType extends ContinuousLaserBulletType{

    public float splashDamageMultiplier = 1.2f;
    public float splashRange = 0f;
    public float velocityDamageMultiplier = 0.1f;
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
        
        if(b.timer(1, damageInterval)){
            applyDamage(b);
        }

        if(shake > 0){
            Effect.shake(shake, shake, b);
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

        float x2 = b.x + Tmp.v1.trns(rot, length).x;
        float y2 = b.y + Tmp.v1.y;

        if(b.type.collidesGround && b.type.collidesTiles){
            mindustry.core.World.raycastEachWorld(b.x, b.y, x2, y2, (cx, cy) -> {
                Building build = mindustry.Vars.world.build(cx, cy);
                if(build != null && build.team != b.team && build.collide(b) && b.checkUnderBuild(build, cx * mindustry.Vars.tilesize, cy * mindustry.Vars.tilesize)){
                    float buildX = cx * mindustry.Vars.tilesize + mindustry.Vars.tilesize / 2f;
                    float buildY = cy * mindustry.Vars.tilesize + mindustry.Vars.tilesize / 2f;
                    
                    float splashDamage = currentDamage * splashDamageMultiplier;
                    Damage.damage(b.team, buildX, buildY, splashRange, splashDamage);
                    
                    hitEffect.at(buildX, buildY, rot);
                }
                return false;
            });
        }

        Units.nearbyEnemies(b.team, Tmp.r1.setPosition(b.x, b.y).setSize(x2 - b.x, y2 - b.y).normalize().grow(width), u -> {
            if(u.checkTarget(b.type.collidesAir, b.type.collidesGround) && u.hittable()){
                u.hitbox(Tmp.r2);
                Vec2 hitPoint = arc.math.geom.Geometry.raycastRect(b.x, b.y, x2, y2, Tmp.r2.grow(3f));
                if(hitPoint != null){
                    /** 相对速度增伤 */
                    float relativeVelocity = b.vel.len() - u.vel.len();
                    float velocityBonus = relativeVelocity > 0 ? (relativeVelocity / velocityThreshold) * velocityDamageMultiplier : 0f;
                    
                    /** 目标速度增伤 */
                    float targetSpeedBonus = (u.vel.len() * 2.5f) / 100f;
                    
                    /** 总增伤 */
                    float totalBonus = Math.min(velocityBonus + targetSpeedBonus, maxDamageMultiplier - 1f);
                    
                    /** 最终伤害 */
                    float finalMultiplier = 1f + totalBonus;
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
                    
                    hitEffect.at(hitPoint.x, hitPoint.y, rot);
                }
            }
        });

        b.damage = damage;
    }
}