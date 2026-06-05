package LP.content;

import arc.graphics.Color;
import mindustry.type.StatusEffect;
public class LPStatusEffect{
    
    public static StatusEffect claim;
    public static StatusEffect disarray;
    public static StatusEffect empI;
    public static StatusEffect empII;
    public static StatusEffect empIII;
    public static StatusEffect flicker;
    public static StatusEffect stall;
    public static StatusEffect stop;

    private LPStatusEffect() {
    }

    public static void load(){
        claim = new StatusEffect("claim"){{
            databaseTag = "amplify";
            color = Color.white;
            speedMultiplier = 2f;
            dragMultiplier = 0.8f;
            effectChance = 0.08f;
            effect = LPFx.claimEffect;
        }};

        disarray = new StatusEffect("disarray"){{
            databaseTag = "slow";
            color = Color.valueOf("E5E5E5");
            reloadMultiplier = 0f;
            speedMultiplier = 0f;
            buildSpeedMultiplier = 0f;
            effectChance = 0.02f;
            effect = LPFx.disarrayEffect;
        }};

        empI = new StatusEffect("emp-I"){{
            databaseTag = "slow";
            color = Color.valueOf("9F54E4");
            reloadMultiplier = 0.8f;
            speedMultiplier = 0.5f;
            buildSpeedMultiplier = 0.4f;
            effectChance = 0.02f;
            effect = LPFx.empIEffect;
        }};

        empII = new StatusEffect("emp-II"){{
            databaseTag = "slow";
            color = Color.valueOf("9F54E4");
            reloadMultiplier = 0.7f;
            speedMultiplier = 0.35f;
            buildSpeedMultiplier = 0.2f;
            effectChance = 0.04f;
            effect = LPFx.empIIEffect;
        }};

        empIII = new StatusEffect("emp-III"){{
            databaseTag = "slow";
            color = Color.valueOf("9F54E4");
            reloadMultiplier = 0.5f;
            speedMultiplier = -0.5f;
            buildSpeedMultiplier = 0f;
            effectChance = 0.05f;
            effect = LPFx.empIIIEffect;
        }};

        flicker = new StatusEffect("flicker"){{
            databaseTag = "damage";
            color = Color.valueOf("FF6464");
            healthMultiplier = 0.85f;
            damage = 0.2666666666666f;
            effectChance = 0.08f;
            effect = LPFx.flickerEffect;
        }};

        stall = new StatusEffect("stall"){{
            databaseTag = "slow";
            color = Color.valueOf("737373");
            dragMultiplier = 325000f;
            speedMultiplier = 0f;
            reloadMultiplier = 0f;
            buildSpeedMultiplier = 0f;
            effectChance = 0.8f;
            effect = LPFx.stallEffect;
        }};

        stop = new StatusEffect("stop"){{
            databaseTag = "slow";
            color = Color.white;
            reloadMultiplier = 0f;
        }};
    }
}
