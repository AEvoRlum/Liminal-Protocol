package ContingencyContract.ui;

import arc.Core;
import arc.util.Log;
import arc.graphics.g2d.Font;
import arc.files.Fi;
import mindustry.Vars;
import mindustry.mod.Mods.LoadedMod;
import mindustry.ui.Fonts;

public class CCFonts {
    public static Font NovecentoWide;
    public static Font FZYSGBK;
    public static Font Bender;

    public static boolean freetypeAvailable = false;

    public static void loadFonts() {
        checkFreetypeAvailable();

        if(!freetypeAvailable){
            Log.warn("CCFonts: arc-freetype module not available, using fallback fonts");
            useFallbackFonts();
            return;
        }

        LoadedMod mod = Vars.mods.getMod(LP.LPMod.class);
        if(mod == null){
            Log.err("CCFonts: mod is null");
            useFallbackFonts();
            return;
        }

        Log.info("CCFonts: mod name = " + mod.name);
        Log.info("CCFonts: mod root path = " + mod.root.path());

        Fi fontsDir = mod.root.child("fonts");
        Log.info("CCFonts: fonts dir path = " + fontsDir.path());
        Log.info("CCFonts: fonts dir exists = " + fontsDir.exists());

        if(fontsDir.exists()){
            Fi noveFile = fontsDir.child("NovecentoWide.ttf");
            Fi fzysFile = fontsDir.child("FZYSGBK.ttf");
            Fi benderFile = fontsDir.child("Bender.ttf");

            Log.info("CCFonts: NovecentoWide path = " + noveFile.path());
            Log.info("CCFonts: FZYSGBK path = " + fzysFile.path());
            Log.info("CCFonts: Bender path = " + benderFile.path());

            NovecentoWide = loadFont(noveFile, 18);
            FZYSGBK = loadFont(fzysFile, 18);
            Bender = loadFont(benderFile, 18);
        }else{
            Log.err("CCFonts: fonts directory not found: " + fontsDir.path());
            useFallbackFonts();
        }

        Log.info("CCFonts: NovecentoWide = " + (NovecentoWide != null));
        Log.info("CCFonts: FZYSGBK = " + (FZYSGBK != null));
        Log.info("CCFonts: Bender = " + (Bender != null));
    }

    private static void checkFreetypeAvailable() {
        try{
            Class.forName("arc.freetype.FreetypeFontGenerator");
            freetypeAvailable = true;
            Log.info("CCFonts: arc-freetype module is available");
        }catch(ClassNotFoundException e){
            freetypeAvailable = false;
            Log.warn("CCFonts: arc-freetype module NOT available: " + e.getMessage());
        }
    }

    private static void useFallbackFonts() {
        Font defaultFont = Fonts.def;
        NovecentoWide = defaultFont;
        FZYSGBK = defaultFont;
        Bender = defaultFont;
        Log.info("CCFonts: using fallback fonts (default font)");
    }

    private static Font loadFont(Fi file, int size) {
        if(!file.exists()){
            Log.err("CCFonts: font file not found: " + file.path());
            return Fonts.def;
        }

        Log.info("CCFonts: loading font: " + file.path());

        try{
            ClassLoader loader = Core.class.getClassLoader();
            
            Class<?> generatorClass = loader.loadClass("arc.freetype.FreetypeFontGenerator");
            Class<?> paramClass = loader.loadClass("arc.freetype.FreetypeFontGenerator$FreetypeFontParameter");
            
            Object param = paramClass.getDeclaredConstructor().newInstance();
            paramClass.getField("size").setInt(param, size);
            paramClass.getField("incremental").setBoolean(param, true);

            Object generator = generatorClass.getDeclaredConstructor(Fi.class).newInstance(file);
            Font font = (Font)generatorClass.getMethod("generateFont", paramClass).invoke(generator, param);
            generatorClass.getMethod("dispose").invoke(generator);

            Log.info("CCFonts: font loaded successfully: " + file.name());
            return font;
        }catch(Exception e){
            Log.err("CCFonts: error loading font " + file.name(), e);
            return Fonts.def;
        }
    }
}