package LP.entities.bullets;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.abilities.ShieldArcAbility;
import mindustry.entities.abilities.ShieldRegenFieldAbility;
import mindustry.entities.abilities.Ability;
import mindustry.entities.Effect;
import mindustry.content.Fx;
import mindustry.gen.Bullet;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.gen.Hitboxc;
import mindustry.gen.Healthc;

public class EnergyBulletType extends BasicBulletType {
    public float energyDamage = 240f;
    public float shieldEnergyDamageMultiplier = 1f;
    public Effect shieldHitEffect = Fx.none;

    public EnergyBulletType(float speed, float damage){
        super(speed, damage);
    }

    public EnergyBulletType(){
        super();
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        super.hitEntity(b, entity, health);

        if(entity instanceof Healthc h){
            float eDmg = energyDamage;

            if(entity instanceof Unit unit){
                for(Ability ability : unit.abilities){
                    if(ability instanceof ForceFieldAbility ||
                       ability instanceof ShieldArcAbility ||
                       ability instanceof ShieldRegenFieldAbility){
                        eDmg *= shieldEnergyDamageMultiplier;
                        shieldHitEffect.at(unit.x, unit.y, b.rotation(), hitColor);
                        break;
                    }
                }
            }

            h.damagePierce(eDmg);
        }
    }

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
        super.hitTile(b, build, x, y, initialHealth, direct);

        if(build.team != b.team && build.isValid()){
            build.damagePierce(energyDamage);
        }
    }
}
