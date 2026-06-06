package LP.content;

import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.weapons.*;

import LP.graphics.LPPal;

public class LPUnits {
    public static UnitType pioneersUnit;

    public static void load() {
        pioneersUnit = new UnitType("pioneers-unit"){{
            constructor = UnitEntity::create;
            /**controller = u -> u.team.isAI() ? new BuilderAI(true, 200f) : new CommandAI();*/
            health = 255;
            armor = 2;
            hitSize = 8f;
            rotateSpeed = 8f;
            speed = 4.2f;
            accel = 0.2f;
            drag = 0.1f;
            engineSize = 2f;
            engineOffset = 6.5f;
            engineLayer = 110f;
            engineColor = LPPal.orange;
            flyingLayer = 110 + 50f;
            trailColor = LPPal.orange;
            trailLength = 10;
            fogRadius = 40;
            lightRadius = 100;
            buildSpeed = 1f;
            buildRange = 200f;
            itemCapacity = 60;
            mineRange = 200f;
            mineSpeed = 1.6f;
            mineTier = 2;
            outlineColor = LPPal.outline;
            range = 200f;
            maxRange = 200f;
            dpsEstimate = 0;
            flying = true;
            faceTarget = true;
            lowAltitude = false;
            targetable = false;
            useUnitCap = false;
            logicControllable = false;
            isEnemy = false;
            alwaysUnlocked = true;
            coreUnitDock = true;
            researchCostMultiplier = 0f;

            parts.add(pioneersFlarePart());
            weapons.add(pioneersRepairWeapon());
            weapons.add(pioneersBuildWeapon());
            weapons.add(pioneersMineWeapon());
        }};
    }

    //pioneers-unit
    private static FlarePart pioneersFlarePart() {
        return new FlarePart(){{
            x = 0;
            y = -6.5f;
            followRotation = false;
            color1 = LPPal.orange.cpy().a(1f);
            color2 = LPPal.orange;
            sides = 2;
            radius = 15f;
            radiusTo = 15f;
            spinSpeed = 0f;
            stroke = 0.5f;
            layer = 110f;
        }};
    }

    private static RepairBeamWeapon pioneersRepairWeapon() {
        RepairBeamWeapon p = new RepairBeamWeapon();
        p.reload = 20f;
        p.shootCone = 30f;
        p.x = 0f;
        p.y = 1.5f;
        p.shootY = 0f;
        p.shootX = 0f;
        p.layerOffset = 110 + 51f;
        p.mirror = false;
        p.rotate = false;
        p.controllable = true;
        p.alternate = false;
        p.widthSinMag = 0.15f;
        p.repairSpeed = 5f;
        p.beamWidth = 0.5f;
        p.fractionRepairSpeed = 1f;
        p.laserColor = LPPal.orange;
        p.healColor = Pal.heal;
        p.targetBuildings = true;
        p.autoTarget = false;
        BulletType bullet = new BulletType();
        bullet.maxRange = 200f;
        p.bullet = bullet;
        return p;
    }

    private static BuildWeapon pioneersBuildWeapon() {
        return new BuildWeapon(){{
            rotate = false;
            top = false;
            x = 0f;
            y = 1.5f;
            shootX = 0f;
            shootY = 0f;
        }};
    }

    private static MineWeapon pioneersMineWeapon() {
        return new MineWeapon(){{
            x = 0f;
            y = 1.5f;
            shootY = 0f;
            shootX = 0f;
        }};
    }
}
