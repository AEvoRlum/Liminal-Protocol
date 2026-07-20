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
    public static Font NovecentoWide;
    public static Font STCN;
    public static Font Bender;

    public static void loadFonts() {
        LoadedMod mod = Vars.mods.getMod(LP.LPMod.class);
        if (mod == null) {
            Log.err("CCFonts: 无法获取模组实例，使用后备字体");
            useFallbackFonts();
            return;
        }

        Fi fontsDir = mod.root.child("fonts");
        if (!fontsDir.exists()) {
            Log.err("CCFonts: fonts 目录不存在: " + fontsDir.path());
            useFallbackFonts();
            return;
        }

        // 打印目录内容以调试
        Log.info("CCFonts: fonts 目录内容:");
        for (Fi file : fontsDir.list()) {
            Log.info(" - " + file.name() + (file.isDirectory() ? "/" : ""));
        }

        NovecentoWide = loadFont(fontsDir.child("NovecentoWide.ttf"), 18, "NovecentoWide");
        STCN = loadFont(fontsDir.child("STCN.ttf"), 18, "STCN");
        Bender = loadFont(fontsDir.child("Bender.ttf"), 18, "Bender");

        Log.info("CCFonts: NovecentoWide = " + (NovecentoWide != null && NovecentoWide != Fonts.def));
        Log.info("CCFonts: STCN = " + (STCN != null && STCN != Fonts.def));
        Log.info("CCFonts: Bender = " + (Bender != null && Bender != Fonts.def));
    }

    private static Font loadFont(Fi file, int size, String name) {
        if (!file.exists()) {
            Log.err("CCFonts: 字体文件不存在: " + file.path());
            return Fonts.def;
        }

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
                Log.warn("CCFonts: 字体 " + name + " 未生成有效字形，使用后备字体");
                return Fonts.def;
            }

            Log.info("CCFonts: 字体 " + name + " 加载成功: " + file.path());
            return font;

        } catch (Exception e) {
            Log.err("CCFonts: 加载字体 " + name + " 失败", e);
            return Fonts.def;
        }
    }

    private static void useFallbackFonts() {
        Font defaultFont = Fonts.def;
        NovecentoWide = defaultFont;
        STCN = defaultFont;
        Bender = defaultFont;
        Log.info("CCFonts: 已切换为后备字体（默认字体）");
    }

    public static void dispose() {
        disposeFont(NovecentoWide);
        disposeFont(STCN);
        disposeFont(Bender);
        Log.info("CCFonts: 字体资源已释放");
    }

    private static void disposeFont(Font font) {
        if (font != null && font != Fonts.def) {
            font.dispose();
        }
    }
}