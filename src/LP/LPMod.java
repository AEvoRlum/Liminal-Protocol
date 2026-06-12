package LP;

import mindustry.mod.*;

import LP.content.*;

public class LPMod extends Mod {
    public LPMod() {
        super();
    }
    public static String ModName = "lp";
    public static String name(String add){
        return ModName + "-" + add;
    }

    @Override
    public void loadContent() {
        super.loadContent();
        LPContent.loadPriority();
        LPItems.load();
        LPPlanets.load();
        LPSounds.load();
        LPFx.loadPriority();
        LPStatusEffect.load();
        LPUnits.load();
        LPBlocks.load();
        
        Mx1Sectors.load();
        Mx1TechTree.load();
    }
}
