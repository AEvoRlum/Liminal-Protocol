package ContingencyContract.ui;

import arc.graphics.Color;
import arc.graphics.g2d.Font;
import arc.scene.ui.Label.LabelStyle;
import arc.util.Log;
import mindustry.ui.Fonts;

public class CCStyles {
    public static LabelStyle
    NovecentoWideLabel, NovecentoWideLabelBig, BenderLabel, DefaultLabel;

    private static boolean loaded = false;

    public static void loadStyles() {
        if (loaded) return;
        loaded = true;

        Font defaultFont = Fonts.def;
        
        NovecentoWideLabel = createLabelStyle(CCFonts.NovecentoWide, defaultFont);
        NovecentoWideLabelBig = createLabelStyle(CCFonts.NovecentoWideBig, defaultFont);
        BenderLabel = createLabelStyle(CCFonts.Bender, defaultFont);
        DefaultLabel = createLabelStyle(CCFonts.STCN, defaultFont);
    }

    private static LabelStyle createLabelStyle(Font customFont, Font fallbackFont) {
        Font font = (customFont != null) ? customFont : fallbackFont;
        if (font == null) {
            Log.err("CCStyles: both custom and fallback fonts are null!");
            return new LabelStyle();
        }
        return new LabelStyle(font, Color.white);
    }

    public static void dispose() {
        NovecentoWideLabel = null;
        BenderLabel = null;
        DefaultLabel = null;
        loaded = false;
    }
}