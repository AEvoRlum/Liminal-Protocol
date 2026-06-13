package LP.entities.units;

import mindustry.type.UnitType;

import LP.graphics.LPPal;

public class LPUnitType extends UnitType {
    public LPUnitType(String name){
        super(name);
        outlineColor = LPPal.outline;
    }
}
