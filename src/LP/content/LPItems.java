package LP.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;
import LP.graphics.LPPal;

public class LPItems {
    public static Item jynsteel, massisteel, litelnlay,  transchimericsteel, photosolidAlloy, crystalite, erocrys, bipolarchip, converchip, stockchip, ionopolymer;

    public static final Seq <Item> mx1Items = new Seq<>();
    private LPItems(){
        
    }
    
    public static void load(){
        /** 精钢 */
        jynsteel = new Item("jynsteel", Color.valueOf("5F6372")){{
            hardness = 2;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 0.4f;
            healthScaling = 1.12f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};

        /** 重质钢 */
        massisteel = new Item("massisteel", Color.valueOf("545F66")){{
            hardness = 3;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0.2f;
            cost = 0.8f;
            healthScaling = 1.2f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};

        /** 嵌合钢 */
        litelnlay = new Item("litelnlay", Color.valueOf("E8EBFF")){{
            hardness = 2;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 0.35f;
            healthScaling = 1.05f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};

        /** 转质嵌合钢 */
        transchimericsteel = new Item("transchimericsteel", Color.valueOf("AAACB5")){{
            hardness = 3;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 0.85f;
            healthScaling = 1.5f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};

        /** 固化涤光体 */
        photosolidAlloy = new Item("photosolid-alloy", Color.valueOf("666775")){{
            hardness = 4;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 1f;
            healthScaling = 1.7f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};

        /** 晶质 */
        crystalite = new Item("crystalite", LPPal.orangeDark){{
            hardness = 1;
            radioactivity = 0.2f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 0.7f;
            healthScaling = 0.1f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "chip-mat";
        }};

        /** 蚀晶 */
        erocrys = new Item("erocrys", LPPal.orange){{
            hardness = 0;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0.2f;
            cost = 0.5f;
            healthScaling = 0.98f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "chip-mat";
        }};

        /** 双极芯片 */
        bipolarchip = new Item("bipolarchip", LPPal.orangeDark){{
            hardness = 1;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 0f;
            healthScaling = 0f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "chip-mat";
        }};

        /** 聚能芯片 */
        converchip = new Item("converchip", LPPal.aureusMid){{
            hardness = 2;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 0f;
            healthScaling = 0f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "chip-mat";
        }};

        /** 物管芯片 */
        stockchip = new Item("stockchip", LPPal.orange){{
            hardness = 1;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 0f;
            healthScaling = 0f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "chip-mat";
        }};

        /** 离子聚合物 */
        ionopolymer = new Item("ionopolymer", Color.valueOf("E8D174")){{
            hardness = 2;
            radioactivity = 0.1f;
            explosiveness = 0.3f;
            flammability = 0f;
            charge = 1f;
            cost = 1f;
            healthScaling = 0f;
            frames = 0;
            buildable = false;
            alwaysUnlocked = false;
            databaseTag = "cons-mat";
        }};
    }
}