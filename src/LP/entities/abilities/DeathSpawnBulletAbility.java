package LP.entities.abilities;

import arc.Core;
import arc.math.Angles;
import arc.math.Mathf;
import arc.scene.ui.layout.*;
import arc.util.Strings;
import mindustry.gen.*;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.Vars;

import static LP.LPMod.name;
import static mindustry.Vars.tilesize;

public class DeathSpawnBulletAbility extends Ability {
    public BulletType bullet;
    public int amount = 1;
    public float spread = 0f;
    public float displayDamage = 0f;
    public float displaySplashDamage = 0f;
    public float displaySplashDamageRadius = 0f;

    public DeathSpawnBulletAbility(BulletType bullet, int amount, float spread, float displayDamage, float displaySplashDamage, float displaySplashDamageRadius) {
        this.bullet = bullet;
        this.amount = amount;
        this.spread = spread;
        this.displayDamage = displayDamage;
        this.displaySplashDamage = displaySplashDamage;
        this.displaySplashDamageRadius = displaySplashDamageRadius;
    }

    public DeathSpawnBulletAbility() {
    }

    @Override
    public void addStats(Table t){
        super.addStats(t);
        t.row();
        t.add(Core.bundle.format("ability.lp-DeathSpawnBulletAbility.description"));
        
        if(displayDamage > 0){
            t.row();
            t.add(Core.bundle.format("stat.lp-DeathSpawnBulletAbility-displayDamage", Strings.fixed(displayDamage, 0)));
        }

        if(displaySplashDamage > 0){
            t.row();
            t.add(Core.bundle.format("stat.lp-DeathSpawnBulletAbility-displaySplashDamage", 
            Strings.fixed(displaySplashDamage, 0), 
            Strings.fixed(displaySplashDamageRadius / tilesize, 1)));
        }
    }

    @Override
    public void death(Unit unit) {
        if(!Vars.net.client() && bullet != null){
            int count = amount;
            
            for(int i = 0; i < count; i++){
                float angle = Mathf.random(spread);
                
                float x = unit.x + Angles.trnsx(angle, spread);
                float y = unit.y + Angles.trnsy(angle, spread);
                
                bullet.create(unit, x, y, angle);
            }
        }
    }

    @Override
    public String localized(){
        return Core.bundle.format("ability." + name("DeathSpawnBulletAbility"));
    }
}
