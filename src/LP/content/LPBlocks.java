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

import LP.graphics.LPPal;

import static mindustry.gen.Sounds.*;

public class LPBlocks {
    //turret
    //production
    //distribution
    //liquid
    //power
    //wall
    public static Block jynWall;
    public static Block jynWallLarge;
    public static Block masWall;
    public static Block masWallLarge;
    public static Block traWall;
    public static Block traWallLarge;
    public static Block ttfWall;
    //craft
    //unit
    //storage
    public static Block pioneers;
    public static Block jynVault;
    //logic
    
    public static void load(){
        //wall
        jynWall = new Wall("jyn-wall"){{
            size = 1;
            health = 216;
            armor = 2;
            flashHit = true;
            chanceDeflect = 0.02f;
            flashColor = LPPal.orange;
            priority = 2;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallDestroyEffect;
            destroySound = LPSounds.blockExplode1Alt;
            destroySoundVolume = 1.2f;
            requirements(Category.defense, with(LPItems.jynsteel, 16));
        }};

        jynWallLarge = new Wall("jyn-wall-large"){{
            size = 2;
            health = 704;
            armor = 3;
            flashHit = true;
            chanceDeflect = 0.04f;
            flashColor = LPPal.orange;
            priority = 2;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallLargeDestroyEffect;
            destroySound = LPSounds.blockExplode2;
            destroySoundVolume = 2f;
            requirements(Category.defense, with(LPItems.jynsteel, 36));
        }};

        masWall = new Wall("mas-wall"){{
            size = 1;
            health = 285;
            armor = 3;
            flashHit = true;
            chanceDeflect = 0.02f;
            flashColor = LPPal.aureus;
            priority = 2;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallDestroyEffect;
            destroySound = LPSounds.blockExplode1Alt;
            destroySoundVolume = 1.2f;
            requirements(Category.defense, with(LPItems.massisteel, 16));
        }};

        masWallLarge = new Wall("mas-wall-large"){{
            size = 2;
            health = 812;
            armor = 5;
            flashHit = true;
            flashColor = LPPal.aureus;
            priority = 2;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0.4f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallLargeDestroyEffect;
            destroySound = LPSounds.blockExplode2;
            destroySoundVolume = 2f;
            requirements(Category.defense, with(LPItems.massisteel, 36));
        }};

        traWall = new Wall("tra-wall"){{
            size = 1;
            health = 325;
            armor = 4;
            flashHit = true;
            crushDamageMultiplier = 0.8f;
            flashColor = LPPal.redDark;
            priority = 2;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallDestroyEffect;
            destroySound = LPSounds.blockExplode1Alt;
            destroySoundVolume = 1.2f;
            requirements(Category.defense, with(LPItems.transchimericsteel, 16));
        }};

        traWallLarge = new Wall("tra-wall-large"){{
            size = 2;
            health = 1110;
            armor = 6;
            flashHit = true;
            crushDamageMultiplier = 0.7f;
            flashColor = LPPal.redDark;
            priority = 2;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0.1f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallLargeDestroyEffect;
            destroySound = LPSounds.blockExplode3;
            destroySoundVolume = 1.2f;
            requirements(Category.defense, with(LPItems.transchimericsteel, 36));
        }};

        ttfWall = new Wall("325"){{
            size = 3;
            health = 325;
            alwaysUnlocked = true;
            researchCostMultiplier = 0.4f;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        
        //storage
        pioneers = new CoreBlock("pioneers") {{
            size = 3;
            health = 732;
            armor = 5;
            itemCapacity = 2000;
            unitCapModifier = 16;
            unitType = LPUnits.pioneersUnit;
            priority = 10;
            alwaysUnlocked = true;
            canOverdrive = false;
            isFirstTier = true;
            requiresCoreZone = false;
            researchCostMultiplier = 0f;
            destroyEffect = LPFx.pioneersDestroyEffect;
            destroySound = LPSounds.blockExplodeFlammable;
            destroySoundVolume = 4;
            requirements(Category.effect, with(LPItems.jynsteel, 1000, LPItems.erocrys, 800));
        }};

        jynVault = new StorageBlock("jyn-vault") {{
            size = 2;
            health = 70;
            itemCapacity = 400;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.effect, with(LPItems.jynsteel, 20, LPItems.erocrys, 8));
        }};
    }
}
