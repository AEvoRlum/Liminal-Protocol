package LP.graphics;

import arc.graphics.Color;
import mindustry.graphics.Pal;

public class LPPal extends Pal {
    public static Color outline;
    public static Color aureus;
    public static Color aureusMid;
    public static Color aureusDark;
    public static Color aureusDarkMid;
    public static Color orange;
    public static Color orangeMid;
    public static Color orangeDark;
    public static Color orangeRed;
    public static Color redDark;
    public static Color redMid;
    public static Color redLight;
    public static Color purple;

    private LPPal() {
    }
    
    static {
        outline = Color.valueOf("262720");
        aureus = Color.valueOf("FFF589");
        aureusMid = Color.valueOf("FCF287");
        aureusDark = Color.valueOf("F8F0AF");
        aureusDarkMid = Color.valueOf("FCF8D7");
        orange = Color.valueOf("FFB570");
        orangeMid = Color.valueOf("E8D174");
        orangeDark = Color.valueOf("EB564B");
        orangeRed = Color.valueOf("FF5845");
        redDark = Color.valueOf("FF4040");
        redMid = Color.valueOf("FF6464");
        redLight = Color.valueOf("FF9492");
        purple = Color.valueOf("D1A4FF");
    }
}
