package LP;

import arc.Core;
import arc.Events;
import arc.math.Mathf;
import arc.scene.ui.Image;
import mindustry.Vars;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.ui.dialogs.SettingsMenuDialog;

public final class LPSettings {
    private static final String NAME = "setting.lp-Liminal-Protocol-Settings";
    public static final String AIMTURRETKEY = "lp-aimTurretSetting";
    public static final String ANNIHILATIONREACTOR_LIGHTNING = "lp-annihilationReactorLightningSetting";
    private static final String CONTINGENCYCONTRACTUI_SIZE = "lp-contingencyContractUISize";

    private LPSettings(){}

    public static void load(){
        initDefaults();
        Events.on(ClientLoadEvent.class, event -> Core.app.post(LPSettings::register));
    }

    private static void register(){
        if(Vars.headless || Vars.ui == null || Vars.ui.settings == null) return;

        Vars.ui.settings.addCategory(Core.bundle.get(NAME), Icon.settings, table -> {
            table.pref(new TitleSetting("lp-title", "setting.lp-LPDraw"));
  
            table.checkPref(AIMTURRETKEY, true);
            table.checkPref(ANNIHILATIONREACTOR_LIGHTNING, true);

            table.pref(new TitleSetting("lp-title", "setting.lp-CCUI"));

            table.sliderPref(CONTINGENCYCONTRACTUI_SIZE, 80, 20, 200, 5, i -> i + "%");
        });
    }

    private static void initDefaults(){
        if(!Core.settings.has(AIMTURRETKEY)){
            Core.settings.put(AIMTURRETKEY, true);
        }
        if(!Core.settings.has(ANNIHILATIONREACTOR_LIGHTNING)){
            Core.settings.put(ANNIHILATIONREACTOR_LIGHTNING, true);
        }
        if(!Core.settings.has(CONTINGENCYCONTRACTUI_SIZE)){
            Core.settings.put(CONTINGENCYCONTRACTUI_SIZE, 80);
        }
    }

    public static boolean aimTurretEnabled(){
        return Core.settings.getBool(AIMTURRETKEY, true);
    }

    public static boolean annihilationReactorLightningEnabled(){
        return Core.settings.getBool(ANNIHILATIONREACTOR_LIGHTNING, true);
    }

    public static int contingencyContractUISizePercent(){
        return Mathf.clamp(Core.settings.getInt(CONTINGENCYCONTRACTUI_SIZE, 80), 20, 100);
    }

    public static float contingencyContractUISize(){
        return contingencyContractUISizePercent() / 100f;
    }

    public static class TitleSetting extends SettingsMenuDialog.SettingsTable.Setting {
        private final String textKey;

        public TitleSetting(String name, String textKey) {
            super(name);
            this.textKey = textKey;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table) {
            table.add(Core.bundle.get(textKey)).padTop(10f).padBottom(5f).color(Pal.stat).get().setFontScale(1.2f);
            table.row();

            Image sep = new Image();
            sep.setColor(Pal.stat);
            sep.setSize(120, 2);
            table.add(sep).growX().pad(4f);
            table.row();
        }
    }
}
