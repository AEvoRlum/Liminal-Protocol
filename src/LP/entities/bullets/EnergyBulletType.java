package LP.entities.bullets;

import arc.Events;
import arc.util.Tmp;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.EventType.UnitBulletDestroyEvent;
import mindustry.game.EventType.UnitDamageEvent;
import mindustry.gen.Bullet;
import mindustry.gen.Healthc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Shieldc;
import mindustry.gen.Unit;
import mindustry.gen.Building;

public class EnergyBulletType extends BasicBulletType {
    static final UnitDamageEvent bulletDamageEvent = new UnitDamageEvent();

    public float energyDamage = 240f;
    public float shieldEnergyDamageMultiplier = 1f;

    public EnergyBulletType(){
    }

    @Override
    public float shieldDamage(Bullet b){
        super.shieldDamage(b);
        return energyDamage * shieldEnergyDamageMultiplier;
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof Healthc h){
            float damage = energyDamage;
            float shield = entity instanceof Shieldc s ? Math.max(s.shield(), 0f) : 0f;
            if(maxDamageFraction > 0){
                float cap = h.maxHealth() * maxDamageFraction + shield;
                damage = Math.min(damage, cap);
                health = Math.min(health, cap);
            }else{
                health += shield;
            }
            if(lifesteal > 0f && b.owner instanceof Healthc o){
                float result = Math.max(Math.min(h.health(), damage), 0);
                o.heal(result * lifesteal);
            }
            h.damagePierce(energyDamage);
        }

        if(entity instanceof Unit unit){
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if(impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);

            Events.fire(bulletDamageEvent.set(unit, b));
        }

        if(!wasDead && entity instanceof Unit unit && unit.dead){
            Events.fire(new UnitBulletDestroyEvent(unit, b));
        }

        handlePierce(b, health, entity.x(), entity.y());
    }

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
        super.hitTile(b, build, x, y, initialHealth, direct);

        if(build.team != b.team && build.isValid()){
            build.damagePierce(energyDamage);
        }
    }
}
