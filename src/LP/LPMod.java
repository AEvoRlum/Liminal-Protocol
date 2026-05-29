package LP;

import LP.content.LPSounds;
import arc.*;
import arc.util.*;
import mindustry.game.EventType;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class LPMod extends Mod {
    public LPMod() {
    }
    public static String ModName = "lp";
    public static String name(String add){
        return ModName + "-" + add;
    }
    @Override
    public void init() {
    }

    @Override
    public void loadContent() {
        LPSounds.load();
    }
}
