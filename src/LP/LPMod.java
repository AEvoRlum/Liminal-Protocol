package LP;

import LP.content.*;
import arc.*;
import arc.util.*;
import mindustry.game.EventType;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

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
        LPStatusEffect.load();
        LPContent.loadPriority();
        LPItems.load();
        LPPlanets.load();
        LPSounds.load();
        LPBlocks.load();
        LPUnits.load();
    }
}
