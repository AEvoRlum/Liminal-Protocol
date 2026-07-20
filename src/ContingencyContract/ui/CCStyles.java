package ContingencyContract.ui;

import arc.graphics.Color;
import arc.scene.ui.Label.LabelStyle;
import mindustry.ui.Styles;

public class CCStyles {
    public static LabelStyle
    NovecentoWideLabel, BenderLabel, DefaultLabel;

    public static void loadStyles() {

        if(CCFonts.NovecentoWide != null){
            NovecentoWideLabel = new LabelStyle(CCFonts.NovecentoWide, Color.white);
        }else{
            NovecentoWideLabel = new LabelStyle(Styles.defaultLabel);
        }

        if(CCFonts.Bender != null){
            BenderLabel = new LabelStyle(CCFonts.Bender, Color.white);
        }else{
            BenderLabel = new LabelStyle(Styles.defaultLabel);
        }

        DefaultLabel = new LabelStyle(CCFonts.STCN, Color.white);
    }
}