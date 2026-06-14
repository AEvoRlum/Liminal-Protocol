package LP.entities.units;

import mindustry.type.UnitType;
import mindustry.gen.Unit;

import LP.graphics.LPPal;
import LP.gen.LPUnit;

public class LPUnitType extends UnitType {
    public LPUnitType(String name){
        super(name);
        outlineColor = LPPal.outline;
        alwaysUnlocked = false;
    }

    protected float alpha(Unit unit){
        if (unit instanceof LPUnit){
            return 1f;
        }
        return 1f;
    }
}
