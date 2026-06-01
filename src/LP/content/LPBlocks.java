package LP.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.*;
import mindustry.content.UnitTypes;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;
import static mindustry.type.ItemStack.*;
import static mindustry.gen.Sounds.*;


public class LPBlocks {
    public static Block pioneers;
    
    public static void load(){
        pioneers = new CoreBlock("pioneers") {{
            size = 3;
            health = 732;
            armor = 5;
            itemCapacity = 2000;
            unitCapModifier = 16;
            unitType = UnitTypes.alpha;
            priority = 10;
            alwaysUnlocked = true;
            canOverdrive = false;
            isFirstTier = true;
            requiresCoreZone = false;
            researchCostMultiplier = 0;
            destroySound = blockExplodeFlammable;
            destroySoundVolume = 4;
            requirements(Category.effect, with(LPItems.jynsteel, 1000, LPItems.erocrys, 800));
        }};
    }
}
