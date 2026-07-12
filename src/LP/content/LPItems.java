package LP.content;

import arc.graphics.Color;
import mindustry.type.Item;
import LP.graphics.LPPal;

public class LPItems {
    public static Item
    /** 建筑材料 */
    jynsteel, massisteel, litelnlay,  transchimericsteel, photosolidAlloy,
    /** 芯片材料 */
    crystalite, erocrys, bipolarchip, converchip, stockchip, buildchip,
    bipolarchipset, converchipset, stockchipset, buildchipset,
    /** 消耗材料 */
    ionopolymer, heterosoligen,
    /** 模块材料 */
    energyStorageModule, powerSupplyModule, chargeModule;

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

        /** 构建芯片 */
        buildchip = new Item("buildchip", LPPal.orangeMid){{
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

        /** 双极芯片组 */
        bipolarchipset = new Item("bipolarchipset", LPPal.orangeDark.cpy().lerp(Color.gold, 0.35f)){{
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

        /** 聚能芯片组 */
        converchipset = new Item("converchipset", LPPal.aureusMid.cpy().lerp(Color.gold, 0.35f)){{
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

        /** 物管芯片组 */
        stockchipset = new Item("stockchipset", LPPal.orange.cpy().lerp(Color.gold, 0.35f)){{
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

        /** 构建芯片组 */
        buildchipset = new Item("buildchipset", LPPal.orangeMid.cpy().lerp(Color.gold, 0.35f)){{
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

        /** 固态异氢 */
        heterosoligen = new Item("heterosoligen", Color.valueOf("8497B5")){{
            hardness = 0;
            radioactivity = 0.5f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 1f;
            healthScaling = 0f;
            frames = 0;
            buildable = false;
            alwaysUnlocked = false;
            databaseTag = "cons-mat";
        }};

        /** 储能模块 */
        energyStorageModule = new Item("energy-storage-module", LPPal.orangeMid){{
            hardness = 3;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 1f;
            healthScaling = 0.8f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "module-mat";
        }};

        /** 供能模块 */
        powerSupplyModule = new Item("power-supply-module", LPPal.aureusDarkMid){{
            hardness = 4;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 1f;
            healthScaling = 0.8f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "module-mat";
        }};

        /** 蓄能模块 */
        chargeModule = new Item("charge-module", LPPal.orangeRed){{
            hardness = 4;
            radioactivity = 0f;
            explosiveness = 0f;
            flammability = 0f;
            charge = 0f;
            cost = 1f;
            healthScaling = 0.8f;
            frames = 0;
            buildable = true;
            alwaysUnlocked = false;
            databaseTag = "module-mat";
        }};
    }
}