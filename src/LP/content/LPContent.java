package LP.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import LP.LPMod;

public class LPContent extends Content{
    private static final TextureRegion emptyRegion = new TextureRegion();

    public static TextureRegion arrowRegion,
    pointerRegion,
    strafeRegion, missileRegion, bombRegion, annihilateArrow,
    bombard, fleet, objective, airborne,
    artillery, suppress, disruption,
    laser, missile, ballistic, plasma,
    shieldGenerator, shieldProjector,
    drill, pump, conveyor, router,
    fabricator, assembler, mixer,
    powerNode, powerSource, powerConduit,
    wall, gate, turret,
    core, storage,
    repair, healing,
    radar, sensor,
    unitFactory, unitAssembler,
    liquidTank, liquidPump,
    heatSink, coolant,
    processor, quantumProcessor,
    phaseWeaver, surgeSmelter;

    public LPContent(){
        super();
    }

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }

    @Override
    public void load(){
        arrowRegion = Core.atlas.find(LPMod.ModName + "-arrow", emptyRegion);
        pointerRegion = Core.atlas.find(LPMod.ModName + "-pointer", emptyRegion);
        strafeRegion = Core.atlas.find(LPMod.ModName + "-strafe", emptyRegion);
        missileRegion = Core.atlas.find(LPMod.ModName + "-missile", emptyRegion);
        bombRegion = Core.atlas.find(LPMod.ModName + "-bomb", emptyRegion);
        annihilateArrow = Core.atlas.find(LPMod.ModName + "-annihilate-arrow", emptyRegion);
        bombard = Core.atlas.find(LPMod.ModName + "-bombard", emptyRegion);
        fleet = Core.atlas.find(LPMod.ModName + "-fleet", emptyRegion);
        objective = Core.atlas.find(LPMod.ModName + "-objective", emptyRegion);
        airborne = Core.atlas.find(LPMod.ModName + "-airborne", emptyRegion);
        artillery = Core.atlas.find(LPMod.ModName + "-artillery", emptyRegion);
        suppress = Core.atlas.find(LPMod.ModName + "-suppress", emptyRegion);
        disruption = Core.atlas.find(LPMod.ModName + "-disruption", emptyRegion);
        laser = Core.atlas.find(LPMod.ModName + "-laser", emptyRegion);
        missile = Core.atlas.find(LPMod.ModName + "-missile-icon", emptyRegion);
        ballistic = Core.atlas.find(LPMod.ModName + "-ballistic", emptyRegion);
        plasma = Core.atlas.find(LPMod.ModName + "-plasma", emptyRegion);
        shieldGenerator = Core.atlas.find(LPMod.ModName + "-shield-generator", emptyRegion);
        shieldProjector = Core.atlas.find(LPMod.ModName + "-shield-projector", emptyRegion);
        drill = Core.atlas.find(LPMod.ModName + "-drill", emptyRegion);
        pump = Core.atlas.find(LPMod.ModName + "-pump", emptyRegion);
        conveyor = Core.atlas.find(LPMod.ModName + "-conveyor", emptyRegion);
        router = Core.atlas.find(LPMod.ModName + "-router", emptyRegion);
        fabricator = Core.atlas.find(LPMod.ModName + "-fabricator", emptyRegion);
        assembler = Core.atlas.find(LPMod.ModName + "-assembler", emptyRegion);
        mixer = Core.atlas.find(LPMod.ModName + "-mixer", emptyRegion);
        powerNode = Core.atlas.find(LPMod.ModName + "-power-node", emptyRegion);
        powerSource = Core.atlas.find(LPMod.ModName + "-power-source", emptyRegion);
        powerConduit = Core.atlas.find(LPMod.ModName + "-power-conduit", emptyRegion);
        wall = Core.atlas.find(LPMod.ModName + "-wall", emptyRegion);
        gate = Core.atlas.find(LPMod.ModName + "-gate", emptyRegion);
        turret = Core.atlas.find(LPMod.ModName + "-turret", emptyRegion);
        core = Core.atlas.find(LPMod.ModName + "-core", emptyRegion);
        storage = Core.atlas.find(LPMod.ModName + "-storage", emptyRegion);
        repair = Core.atlas.find(LPMod.ModName + "-repair", emptyRegion);
        healing = Core.atlas.find(LPMod.ModName + "-healing", emptyRegion);
        radar = Core.atlas.find(LPMod.ModName + "-radar", emptyRegion);
        sensor = Core.atlas.find(LPMod.ModName + "-sensor", emptyRegion);
        unitFactory = Core.atlas.find(LPMod.ModName + "-unit-factory", emptyRegion);
        unitAssembler = Core.atlas.find(LPMod.ModName + "-unit-assembler", emptyRegion);
        liquidTank = Core.atlas.find(LPMod.ModName + "-liquid-tank", emptyRegion);
        liquidPump = Core.atlas.find(LPMod.ModName + "-liquid-pump", emptyRegion);
        heatSink = Core.atlas.find(LPMod.ModName + "-heat-sink", emptyRegion);
        coolant = Core.atlas.find(LPMod.ModName + "-coolant", emptyRegion);
        processor = Core.atlas.find(LPMod.ModName + "-processor", emptyRegion);
        quantumProcessor = Core.atlas.find(LPMod.ModName + "-quantum-processor", emptyRegion);
        phaseWeaver = Core.atlas.find(LPMod.ModName + "-phase-weaver", emptyRegion);
        surgeSmelter = Core.atlas.find(LPMod.ModName + "-surge-smelter", emptyRegion);
    }

    public void update(){
    }

    public void removed(){
    }

    public void init(){
        super.init();
    }

    public static void loadPriority(){
    }
}
