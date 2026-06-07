package LP.entities.blocks;

import arc.struct.*;
import mindustry.entities.bullet.*;
import mindustry.logic.*;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.*;

import LP.content.LPStatValues;

public class LPPowerTurret extends Turret{
    public BulletType shootType;

    public LPPowerTurret(String name){
        super(name);
        hasPower = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, LPStatValues.ammo(ObjectMap.of(this, shootType)));
    }

    public void limitRange(float margin){
        limitRange(shootType, margin);
    }

    public class LPPowerTurretBuild extends TurretBuild{

        @Override
        public float getAmmoFraction(){
            return power == null ? 0f : power.status;
        }

        @Override
        public double sense(LAccess sensor){
            return switch(sensor){
                case ammo -> power == null ? 0f : power.status;
                case ammoCapacity -> 1;
                default -> super.sense(sensor);
            };
        }

        @Override
        public BulletType useAmmo(){
            return shootType;
        }

        @Override
        public boolean hasAmmo(){
            return true;
        }

        @Override
        public BulletType peekAmmo(){
            return shootType;
        }
    }
}
