package LP.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import LP.content.graphics.LPPal;

public class LPItems {
    public static Item crystalite, erocrys, ionopolymer, jynsteel, litelnlay, massisteel, transchimericsteel;

    public static Seq <Item> items = new Seq<>();
    private LPItems(){
        
    }
    
    private static void load(){
        crystalite = new Item("Crystalite", Color.valueOf("EDA76E")){{
            hardness = 0.5;
            radioactivity = 0.2;
            explosiveness = 0;
            flammability = 0;
            charge = 0;
            cost = 0.7;
            healthScaling = 0.1;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "chip-mat";
        }};
        
        erocrys = new Item("Eerocrys", Color.valueOf("FFB570")){{
            hardness = 0;
            radioactivity = 0;
            explosiveness = 0;
            flammability = 0;
            charge = 0.2;
            cost = 0.5;
            healthScaling = 0.98;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "chip-mat";
        }};

        ionopolymer = new Item("Ionopolymer", Color.valueOf("E8D174")){{
            hardness = 2;
            radioactivity = 0.1;
            explosiveness = 0.3;
            flammability = 0;
            charge = 1;
            cost = 1;
            healthScaling = 0;
            frames = 0;
            buildable = false;
            alwaysUnlocked = false;
            databaseTag = "cons-mat";
        }};

        jynsteel = new Item("Jynsteel", Color.valueOf("5F6372")){{
            hardness = 2;
            radioactivity = 0;
            explosiveness = 0;
            flammability = 0;
            charge = 0;
            cost = 0.4;
            healthScaling = 1.12;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};

        litelnlay = new Item("Litelnlay", Color.valueOf("E8EBFF")){{
            hardness = 1.75;
            radioactivity = 0;
            explosiveness = 0;
            flammability = 0;
            charge = 0;
            cost = 0.35;
            healthScaling = 1.05;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};

        massisteel = new Item("Massisteel", Color.valueOf("545F66")){{
            hardness = 3;
            radioactivity = 0;
            explosiveness = 0;
            flammability = 0;
            charge = 0.2;
            cost = 0.8;
            healthScaling = 1.2;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};

        transchimericsteel = new Item("Transchimericsteel", Color.valueOf("AAACB5")){{
            hardness = 3;
            radioactivity = 0;
            explosiveness = 0;
            flammability = 0;
            charge = 0;
            cost = 0.85;
            healthScaling = 1.5;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "build-mat";
        }};
    }
}