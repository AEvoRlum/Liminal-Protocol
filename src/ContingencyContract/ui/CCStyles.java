package ContingencyContract.ui;

import arc.util.Log;
import arc.graphics.Color;
import arc.scene.ui.Label.LabelStyle;
import mindustry.ui.Styles;

public class CCStyles {
    public static LabelStyle /** FZYSGBKLabel, */
    NovecentoWideLabel, BenderLabel, DefaultLabel;

    public static void loadStyles() {
        /** if(CCFonts.FZYSGBK != null){
            FZYSGBKLabel = new LabelStyle(CCFonts.FZYSGBK, Color.white);
        }else{
            FZYSGBKLabel = new LabelStyle(Styles.defaultLabel);
            Log.info("CCStyles: using defaultLabel for FZYSGBKLabel");
        } */

        if(CCFonts.NovecentoWide != null){
            NovecentoWideLabel = new LabelStyle(CCFonts.NovecentoWide, Color.white);
        }else{
            NovecentoWideLabel = new LabelStyle(Styles.defaultLabel);
            Log.info("CCStyles: using defaultLabel for NovecentoWideLabel");
        }

        if(CCFonts.Bender != null){
            BenderLabel = new LabelStyle(CCFonts.Bender, Color.white);
        }else{
            BenderLabel = new LabelStyle(Styles.defaultLabel);
            Log.info("CCStyles: using defaultLabel for BenderLabel");
        }

        DefaultLabel = new LabelStyle(CCFonts.STCN, Color.white);
    }
}