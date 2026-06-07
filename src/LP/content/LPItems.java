package LP.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;
import LP.graphics.LPPal;

public class LPItems {
    public static Item jynsteel, massisteel, litelnlay,  transchimericsteel, crystalite, erocrys, ionopolymer;

    public static final Seq <Item> mx1Items = new Seq<>();
    private LPItems(){
        
    }
    
    public static void load(){
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

        mx1Items.addAll(
            jynsteel, massisteel, litelnlay,  transchimericsteel, crystalite, erocrys, ionopolymer
        );
    }
}