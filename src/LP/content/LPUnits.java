package LP.content;

import arc.func.Cons;
import arc.func.Func;
import arc.func.Prov;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Position;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.ai.ControlPathfinder;
import mindustry.ai.Pathfinder;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.entities.units.UnitController;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.weapons.*;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;
import mindustry.entities.bullet.*;

import LP.graphics.LPPal;

public class LPUnits {
    public static UnitType pioneersUnit;

    public static void load() {
        pioneersUnit = new UnitType("pioneers-unit"){{
            constructor = UnitEntity::create;
            controller = u -> u.team.isAI() ? new BuilderAI(true, 200f) : new CommandAI();
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
            ammoCapacity = 325;
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
        RepairBeamWeapon repair = new RepairBeamWeapon();
        repair.reload = 20f;
        repair.shootCone = 30f;
        repair.x = 0f;
        repair.y = 1.5f;
        repair.shootY = 0f;
        repair.shootX = 0f;
        repair.mirror = false;
        repair.rotate = false;
        repair.controllable = true;
        repair.alternate = false;
        repair.widthSinMag = 0.15f;
        repair.repairSpeed = 5f;
        repair.beamWidth = 0.5f;
        repair.fractionRepairSpeed = 1f;
        repair.laserColor = LPPal.orange;
        repair.healColor = Pal.heal;
        repair.targetBuildings = true;
        repair.autoTarget = false;
        BulletType bullet = new BulletType();
        bullet.maxRange = 200f;
        repair.bullet = bullet;
        return repair;
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
