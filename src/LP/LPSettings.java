package LP;

import arc.Core;
import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Icon;

public final class LPSettings {
    private static final String NAME = "Liminal-Protocol-Settings";
    public static final String AIMTURRETKEY = "aimTurretSetting";

    private LPSettings(){}

    public static void load(){
        initDefaults();
        Events.on(ClientLoadEvent.class, event -> Core.app.post(LPSettings::register));
    }

    private static void register(){
        if(Vars.headless || Vars.ui == null || Vars.ui.settings == null) return;

        Vars.ui.settings.addCategory(NAME, Icon.settings, table -> {
            table.checkPref(AIMTURRETKEY, true);
        });
    }

    private static void initDefaults(){
        if(!Core.settings.has(AIMTURRETKEY)){
            Core.settings.put(AIMTURRETKEY, true);
        }
    }

    public static boolean aimTurretEnabled(){
        return Core.settings.getBool(AIMTURRETKEY, true);
    }
}
