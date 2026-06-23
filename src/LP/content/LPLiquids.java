package LP.content;

import arc.graphics.*;
import mindustry.content.Liquids;
import mindustry.type.*;

public class LPLiquids {
    public static Liquid heterohydrogen;

    public static void load(){
        heterohydrogen = new Liquid("heterohydrogen", Color.valueOf("C7DFED")){{
            temperature = 0.2f;
            viscosity = 0.4f;
            heatCapacity = 0.8f;
            boilPoint = 0.5f;
            lightColor = barColor = color;
            canStayOn.add(Liquids.water);
            alwaysUnlocked = false;
        }};
    }
}
