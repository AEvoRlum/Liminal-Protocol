package LP.content;

import mindustry.type.*;

import static LP.content.LPPlanets.*;

public class Mx1Sectors {
    public static SectorPreset 不容拒绝, why, repulse, instead, Rift;

    public static void load(){

        不容拒绝 = new SectorPreset("不容拒绝", mx1, 74){{
            alwaysUnlocked = true;
            captureWave = 3;
            difficulty = 1;
        }};

        why = new SectorPreset("why", mx1, 20){{
            alwaysUnlocked = false;
            captureWave = 12;
            difficulty = 2;
        }};

        repulse = new SectorPreset("repulse", mx1, 82){{
            alwaysUnlocked = false;
            captureWave = 16;
            difficulty = 3;
        }};

        instead = new SectorPreset("instead", mx1, 86){{
            alwaysUnlocked = false;
            captureWave = 20;
            difficulty = 4;
        }};

        Rift = new SectorPreset("Rift", mx1, 40){{
            alwaysUnlocked = false;
            captureWave = 2;
            difficulty = 5;
        }};
    }
}
