package LP;

import arc.Core;
import arc.Events;
import arc.scene.style.TextureRegionDrawable;
import mindustry.Vars;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.*;
import mindustry.ui.fragments.MenuFragment;
import ContingencyContract.ui.CCFonts;
import ContingencyContract.ui.CCStyles;
import ContingencyContract.ui.dialogs.ContingencyContractDialog;
import ContingencyContract.music.CCBackgroundMusic;

import LP.content.*;

public class LPMod extends Mod {
    public LPMod() {
        super();
        LPSettings.load();
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
        LPLiquids.load();
        LPSounds.load();
        LPFx.loadPriority();
        LPStatusEffect.load();
        LPUnits.load();
        LPEnemyUnits.load();
        LPBlocks.load();
        LPPlanets.load();
        
        Mx1Sectors.load();
        Mx1TechTree.load();

        Events.on(ClientLoadEvent.class, e -> {
            CCFonts.loadFonts();
            CCStyles.loadStyles();
            CCBackgroundMusic.loadMusic();
            
            MenuFragment menu = Vars.ui.menufrag;
            if (menu != null) {
                menu.addButton("@ContingencyContract", new TextureRegionDrawable(Core.atlas.find("lp-ContingencyContractIcon")), () -> safeLaunch(() -> {
                    new ContingencyContractDialog().show(true);
                }));
            }
        });
    }

    private void safeLaunch(Runnable action) {
        if (!Vars.mods.hasContentErrors()) {
            action.run();
        } else {
            Vars.ui.showInfo("@mod.noerrorplay");
        }
    }
}