package LP.entities.bullets;

import arc.Events;
import arc.audio.Sound;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.world.blocks.ConstructBlock;
import mindustry.entities.Effect;
import mindustry.entities.Fires;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.EventType.UnitBulletDestroyEvent;
import mindustry.game.EventType.UnitDamageEvent;
import mindustry.content.Fx;

public class EnergyBulletType extends BasicBulletType {
    static final UnitDamageEvent bulletDamageEvent = new UnitDamageEvent();
    /** 能量伤害(无视护甲) */
    public float energyDamage = 240f;
    /** 能量伤害对护盾伤害倍率 */
    public float shieldEnergyDamageMultiplier = 1f;
    /** 范围能量伤害 */
    public float rangeEnergyDamage = 0f;
    /** 范围能量伤害半径 */
    public float rangeEnergyDamageRadius = 0f;
    /** 范围治疗(同时对实体和建筑) */
    public float rangeHeal = 0f;
    /** 范围治疗半径 */
    public float rangeHealRadius = 0f;
    /** 延迟触发范围治疗(tick) */
    public float delayRangeHeal = 0f;
    /** 范围治疗特效 */
    public Effect rangeHealEffect = Fx.none;
    /** 范围治疗音效 */
    public Sound rangeHealSound = Sounds.none;
    /** 范围治疗音效音量 */
    public float rangeHealSoundVolume = 1f;

    public EnergyBulletType(){
        ammoMultiplier = 1f;
    }

    /** 范围治疗实现 */
    public void rangeHeal(Bullet b, float x, float y) {
        if (rangeHeal <= 0f || rangeHealRadius <= 0f) return;

        if (delayRangeHeal > 0f) {
            Time.run(delayRangeHeal, () -> doRangeHeal(b.team, x, y));
        } else {
            doRangeHeal(b.team, x, y);
        }
    }

    /** 执行范围治疗 */
    private void doRangeHeal(Team team, float x, float y) {
        if (rangeHealEffect != Fx.none) {
            rangeHealEffect.at(x, y);
        }

        if (rangeHealSound != Sounds.none) {
            rangeHealSound.at(x, y, rangeHealSoundVolume);
        }

        Units.nearby(team, x, y, rangeHealRadius, unit -> {
            unit.heal(rangeHeal);
        });

        Vars.indexer.eachBlock(team, x, y, rangeHealRadius, build -> build.team == team, build -> {
            healEffect.at(build.x, build.y, 0f, healColor, build.block);
            build.heal(rangeHeal);
        });
    }

    /** 范围能量伤害实现 */
    public void rangeEnergyDamage(Bullet b, float x, float y) {
        if (rangeEnergyDamage <= 0f || rangeEnergyDamageRadius <= 0f) return;

        Units.nearbyEnemies(b.team, x, y, rangeEnergyDamageRadius, unit -> {
            unit.damagePierce(rangeEnergyDamage);
        });

        Vars.indexer.eachBlock(null, x, y, rangeEnergyDamageRadius, build -> build.team != b.team, build -> {
            build.damagePierce(rangeEnergyDamage);
        });
    }

    @Override
    public float estimateDPS(){
        super.estimateDPS();
        return (energyDamage + rangeEnergyDamage + damage + splashDamage) * (pierce ? pierceCap == -1 ? 2 : Mathf.clamp(pierceCap, 1, 2) : 1f);
    }

    @Override
    public float shieldDamage(Bullet b){
        super.shieldDamage(b);
        return (energyDamage + rangeEnergyDamage) * shieldEnergyDamageMultiplier + (damage + splashDamage) * shieldDamageMultiplier;
    }

    @Override
    public boolean heals(){
        return healPercent > 0 || healAmount > 0 || rangeHeal > 0;
    }


    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        super.hitEntity(b, entity, health);
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
        if(build.team != b.team && build.isValid()){
            build.damage(energyDamage, true);
        }

        if(makeFire && build.team != b.team){
            Fires.create(build.tile);
        }

        if(heals() && build.team == b.team && (healAmount > 0f || healPercent > 0f) &&!(build.block instanceof ConstructBlock)){
            healEffect.at(build.x, build.y, 0f, healColor, build.block);
            build.heal(healPercent / 100f * build.maxHealth + healAmount);
            healSound.at(build, 1f + Mathf.range(0.1f), healSoundVolume);

            hit(b);
        }else if(build.team != b.team && direct){
            hit(b);

            if(lifesteal > 0f && b.owner instanceof Healthc o){
                float result = Math.max(Math.min(build.health, damage), 0);
                o.heal(result * lifesteal);
            }
        }

        handlePierce(b, initialHealth, x, y);

        rangeHeal(b, x, y);
        rangeEnergyDamage(b, x, y);
    }

    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b, x, y, true);

        rangeHeal(b, x, y);
        rangeEnergyDamage(b, x, y);
    }

    @Override
    public void despawned(Bullet b){
        super.despawned(b);

        rangeHeal(b, b.x(), b.y());
        rangeEnergyDamage(b, b.x(), b.y());
    }
}
