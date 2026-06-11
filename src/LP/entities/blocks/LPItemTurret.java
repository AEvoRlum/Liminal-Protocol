package LP.entities.blocks;

import arc.util.Nullable;
import mindustry.world.meta.*;
import mindustry.world.blocks.defense.turrets.*;

import LP.content.LPStatValues;
import LP.graphics.LPPal;

public class LPItemTurret extends ItemTurret{
    public @Nullable String special;
    public LPItemTurret(String name){
        super(name);
        hasItems = true;
        outlineColor = LPPal.outline;
    }
    @Override
    public void init(){
        super.init();
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, LPStatValues.ammo(ammoTypes, name));
    }
}