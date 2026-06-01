package LP.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.content.Fx;
import mindustry.entities.effect.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.units.UnitAssembler.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;
import static mindustry.graphics.Pal.*;

import static LP.graphics.LPPal.*;


public class LPFx {
    private LPFx() {}
    
    //Status Effects
    public static final ParticleEffect claimEffect = new ParticleEffect() {{
        particles = 1;
        region = "lp-triangle";
        lifetime = 20f;
        length = 0f;
        baseLength = 0f;
        baseRotation = 0f;
        randLength = false;
        interp = Interp.fastSlow;
        sizeInterp = Interp.pow3In;
        sizeFrom = 3f;
        sizeTo = 0f;
        colorFrom = colorTo = Color.white;
    }};

    public static final MultiEffect disarrayEffect = new MultiEffect(
        new WrapEffect(Fx.dynamicSpikes, Color.valueOf("E5E5E5"), 24f),
        new ParticleEffect() {{
            particles = 12;
            line = true;
            length = 30f;
            baseLength = 0f;
            lifetime = 50f;
            interp = Interp.fastSlow;
            sizeInterp = Interp.pow3In;
            lenFrom = 8f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = colorTo = Color.valueOf("E5E5E5");
        }}
    );

    public static final ParticleEffect empIEffect = new ParticleEffect(){{
        particles = 2;
        lifetime = 30f;
        offset = 60f;
        cone = 360;
        line = true;
        useRotation = false;
        length = -15f;
        baseLength = 20f;
        baseRotation = 90f;
        interp = Interp.circleOut;
        lenFrom = 0;
        lenTo = 10;
        strokeFrom = 1.5f;
        strokeTo = 0.5f;
        colorFrom = colorTo = Color.valueOf("9F54E4");
    }};
    
    public static final ParticleEffect empIIEffect = new ParticleEffect(){{
        particles = 2;
        lifetime = 30f;
        offset = 60f;
        cone = 360;
        line = true;
        useRotation = false;
        length = -20f;
        baseLength = 25f;
        baseRotation = 90f;
        interp = Interp.circleOut;
        lenFrom = 0;
        lenTo = 10;
        strokeFrom = 1.5f;
        strokeTo = 0.5f;
        colorFrom = colorTo = Color.valueOf("9F54E4");
    }};

    public static final ParticleEffect empIIIEffect = new ParticleEffect(){{
        particles = 2;
        lifetime = 30f;
        offset = 60f;
        cone = 360;
        line = true;
        useRotation = false;
        length = -25f;
        baseLength = 30f;
        baseRotation = 90f;
        interp = Interp.circleOut;
        lenFrom = 0;
        lenTo = 13;
        strokeFrom = 1.5f;
        strokeTo = 0.5f;
        colorFrom = colorTo = Color.valueOf("9F54E4");
    }};

    public static final ParticleEffect flickerEffect = new ParticleEffect(){{
        particles = 1;
        region = "lp-square";
        lifetime = 30f;
        length = 0f;
        baseLength = 0f;
        baseRotation = 0f;
        interp = Interp.fastSlow;
        sizeFrom = 3f;
        sizeTo = 0f;
        colorFrom = colorTo = Color.valueOf("FFF589");
    }};

    public static final ParticleEffect stallEffect = new ParticleEffect(){{
        particles = 1;
        line = true;
        lifetime = 30f;
        length = -40f;
        baseLength = 42f;
        baseRotation = 90f;
        offset = 180f;
        cone = 0;
        lenFrom = 0f;
        lenTo = 24f;
        strokeFrom = 0f;
        strokeTo = 1.5f;
        colorFrom = colorTo = Color.valueOf("E5E5E5");
    }};
}
