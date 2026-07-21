package ContingencyContract.ui;

import arc.files.Fi;
import arc.graphics.g2d.Font;
import arc.util.Log;
import mindustry.Vars;
import mindustry.mod.Mods.LoadedMod;
import mindustry.ui.Fonts;
import arc.freetype.FreeTypeFontGenerator;
import arc.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class CCFonts {
    public static Font NovecentoWide, NovecentoWideBig;
    public static Font STCN;
    public static Font Bender;

    public static boolean loaded = false;
    private static boolean loadFailed = false;

    public static void loadFonts() {
        if (loaded || loadFailed) return;
        
        new Throwable().printStackTrace(System.out);

        try {
            new arc.util.SharedLibraryLoader().load("arc-freetype");
        } catch (Throwable t) {
            Log.err("CCFonts: can't load arc-freetype library, using default fonts", t);
            loadFailed = true;
            useFallbackFonts();
            return;
        }

        LoadedMod mod = Vars.mods.getMod(LP.LPMod.class);

        Fi fontsDir = mod.root.child("fonts");
        if (!fontsDir.exists()) {
            useFallbackFonts();
            return;
        }

        NovecentoWide = loadFont(fontsDir.child("NovecentoWide.ttf"), 24, "NovecentoWide");
        NovecentoWideBig = loadFont(fontsDir.child("NovecentoWide.ttf"), 60, "NovecentoWideBig");

        STCN = loadFont(fontsDir.child("STCN.ttf"), 18, "STCN");
        Bender = loadFont(fontsDir.child("Bender.ttf"), 24, "Bender");

        Log.info("CCFonts: NovecentoWide = " + (NovecentoWide != null && NovecentoWide != Fonts.def));
        Log.info("CCFonts: STCN = " + (STCN != null && STCN != Fonts.def));
        Log.info("CCFonts: Bender = " + (Bender != null && Bender != Fonts.def));

        if (NovecentoWide == Fonts.def && STCN == Fonts.def && Bender == Fonts.def) {
            Log.warn("CCFonts: all fonts failed to load, using default fonts");
            loadFailed = true;
        } else {
            loaded = true;
        }
    }

    private static Font loadFont(Fi file, int size, String name) {
        if (!file.exists()) return Fonts.def;

        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);

            FreeTypeFontParameter param = new FreeTypeFontParameter();
            param.size = size;
            param.incremental = true;
            param.characters = "\0";
            param.magFilter = arc.graphics.Texture.TextureFilter.linear;
            param.minFilter = arc.graphics.Texture.TextureFilter.linear;

            Font font = generator.generateFont(param);

            if (font.getData().getGlyph(' ') == null && font.getData().missingGlyph == null) {
                return Fonts.def;
            }
            return font;

        } catch (Exception e) {
            return Fonts.def;
        }
    }

    private static void useFallbackFonts() {
        Font defaultFont = Fonts.def;
        NovecentoWide = defaultFont;
        STCN = defaultFont;
        Bender = defaultFont;
    }

    public static void dispose() {
        disposeFont(NovecentoWide);
        disposeFont(STCN);
        disposeFont(Bender);
    }

    private static void disposeFont(Font font) {
        if (font != null && font != Fonts.def) {
            font.dispose();
        }
    }
}