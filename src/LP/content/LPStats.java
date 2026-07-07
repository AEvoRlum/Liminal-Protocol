package LP.content;

import mindustry.world.meta.*;

public class LPStats {
    public static final Stat itemHealAmount = new Stat("lp-itemHealAmount"),
    powerWarmupTime = new Stat("lp-powerWarmupTime", StatCat.power),
    powerNodeSquareRange = new Stat("lp-powerNodeSquareRange", StatCat.power),

    itemAmmoTurretReload = new Stat("lp-itemAmmoTurretReload", StatCat.function),
    itemAmmoTurretShotInterval = new Stat("lp-itemAmmoTurretShotInterval", StatCat.function),
    itemAmmoTurretMaxAmount = new Stat("lp-itemAmmoTurretMaxAmount", StatCat.function),
    itemAmmoTurretAmmoUnitCost = new Stat("lp-itemAmmoTurretAmmoUnitCost", StatCat.function)
    ;
}
