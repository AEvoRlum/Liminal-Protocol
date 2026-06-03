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

import LP.graphics.LPPal;

public class LPFx {
    public static void loadPriority(){
    }

    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    private LPFx() {}
    public static final Effect
    
    //Status Effects
    claimEffect = new ParticleEffect() {{
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
    }},

    disarrayEffect = new MultiEffect(
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
    ),

    empIEffect = new ParticleEffect(){{
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
    }},
    
    empIIEffect = new ParticleEffect(){{
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
    }},

    empIIIEffect = new ParticleEffect(){{
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
    }},

    flickerEffect = new ParticleEffect(){{
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
    }},

    stallEffect = new ParticleEffect(){{
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
    }},

    //block
    pioneersDestroyEffect = new MultiEffect(
        new ParticleEffect(){{
            particles = 15;
            line = true;
            length = 80f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 68f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 120f;
            baseLength = 5f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = Color.valueOf("54545400");
        }},
        new WaveEffect(){{
            lifetime = 40f;
            sizeFrom = 0f;
            sizeTo = 240f;
            strokeFrom = 3f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }}
    ),

    wallDestroyEffect = new MultiEffect(
        new ParticleEffect(){{
            particles = 4;
            line = true;
            length = 8f;
            baseLength = 2f;
            lifetime = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 10f;
            lenTo = 0f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureusDark;
        }},
        new ParticleEffect(){{
            particles = 8;
            length = 32f;
            baseLength = 2f;
            lifetime = 30f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 6f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("45454570");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 30f;
            interp = Interp.circleOut;
            sizeFrom = 0f;
            sizeTo = 15f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureusDark;
        }}
    ),

    wallLargeDestroyEffect = new MultiEffect(
        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 12f;
            baseLength = 2f;
            lifetime = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 12f;
            lenTo = 0f;
            strokeFrom = 1.2f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureusDark;
        }},
        new ParticleEffect(){{
            particles = 16;
            length = 36f;
            baseLength = 2f;
            lifetime = 30f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 7f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("45454570");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 30f;
            interp = Interp.circleOut;
            sizeFrom = 0f;
            sizeTo = 32f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    heavyIonChamberConsumption = new MultiEffect(
        new ParticleEffect(){{
            particles = 3;
            region = "lp-square";
            length = 32f;
            baseLength = 2f;
            lifetime = 40f;
            spin = -2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeMid;
        }},
        new ParticleEffect(){{
            particles = 3;
            region = "lp-square";
            length = 32f;
            baseLength = 2f;
            lifetime = 40f;
            spin = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeMid;
        }},
        new ParticleEffect(){{
            particles = 2;
            region = "lp-triangle";
            length = 28f;
            baseLength = 2f;

            lifetime = 40f;
            spin = -2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeMid;
        }},
        new ParticleEffect(){{
            particles = 2;
            region = "lp-triangle";
            length = 28f;
            baseLength = 2f;
            lifetime = 40f;
            spin = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeMid;
        }},
        new ParticleEffect(){{
            particles = 1;
            region = "lp-decrementCircle";
            length = 0f;
            baseLength = 0f;
            randLength = false;
            lifetime = 120f;
            useRotation = false;
            interp = Interp.linear;
            sizeInterp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 24f;
            colorFrom = LPPal.orange;
            colorTo = Color.valueOf("E8D17400");
            layer = 109.9f;
        }}
    ),

    jynDrillEffect = new MultiEffect(
        new ParticleEffect(){{
            particles = 4;
            region = "lp-square";
            lifetime = 90f;
            length = 45f;
            baseLength = 2f;;
            spin = 1f;
            interp = Interp.fastSlow;
            sizeFrom = 4f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("FFB57050");
            colorTo = Color.valueOf("45454500");
        }}
    ),

    jynDrillUpdateEffect = new MultiEffect(
        new ParticleEffect(){{
            particles = 2;
            region = "lp-triangle";
            lifetime = 60f;
            length = 30f;
            baseLength = 5f;
            spin = 3f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 2f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("F0F0F0");
            colorTo = Color.valueOf("47474700");
        }}
    ),

    shearDrillEffect = new MultiEffect(
        new ParticleEffect(){{
            particles = 5;
            region = "lp-triangle";
            lifetime = 90f;
            length = 45f;
            baseLength = 2f;
            spin = 2f;
            interp = Interp.fastSlow;
            sizeFrom = 4f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("FFB57050");
            colorTo = Color.valueOf("47474700");
        }}
    ),

    shearDrillUpdateEffect = new MultiEffect(
        new ParticleEffect(){{
            particles = 3;
            region = "lp-triangle";
            lifetime = 60f;
            length = 30f;
            baseLength = 5f;
            spin = -3.5f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 2f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("F0F0F050");
            colorTo = Color.valueOf("57575700");
        }}
    ),

    drillSteam = new Effect(220f, e -> {

        float length = 3f + e.finpow() * 20f;
        rand.setSeed(e.id);
        for(int i = 0; i < 13; i++){
            v.trns(rand.random(360f), rand.random(length));
            float sizer = rand.random(1.3f, 3.7f);

            e.scaled(e.lifetime * rand.random(0.5f, 1f), b -> {
                color(Color.gray, b.fslope() * 0.93f);

                Fill.circle(e.x + v.x, e.y + v.y, sizer + b.fslope() * 1.2f);
            });
        }
    }),

    dynamicSpikes = new Effect(40f, 100f, e -> {
        color(e.color);
        stroke(e.fout() * 2f);
        float circleRad = 4f + e.finpow() * e.rotation;
        Lines.circle(e.x, e.y, circleRad);

        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 6f, e.rotation * 1.5f * e.fout(), i*90);
        }

        color();
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 3f, e.rotation * 1.45f / 3f * e.fout(), i*90);
        }

        Drawf.light(e.x, e.y, circleRad * 1.6f, Pal.heal, e.fout());
    }),

    impactDrillEffect = new MultiEffect(
        new ParticleEffect(){{
            particles = 10;
            line = true;
            lifetime = 35f;
            length = 42f;
            baseLength = 4f;
            interp = Interp.pow2Out;
            sizeInterp = Interp.pow3In;
            lenFrom = 8f;
            lenTo = 0f;
            sizeFrom = 1f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }},

        new ParticleEffect(){{
            particles = 4;
            line = true;
            lifetime = 35f;
            length = 42f;
            baseLength = 4f;
            interp = Interp.pow2Out;
            sizeInterp = Interp.pow3In;
            lenFrom = 12f;
            lenTo = 0f;
            sizeFrom = 1f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }}, 

        new ParticleEffect(){{
            particles = 9;
            region = "lp-square";
            lifetime = 45f;
            length = 32f;
            baseLength = 2f;;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow4In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }},

        new SoundEffect(){{
            sound = LPSounds.shootBang;
            effect = drillSteam;
            minVolume = 0.6f;
            maxVolume = 0.6f;
            maxPitch = 0.5f;
            minPitch = 0.5f;
        }},

        new WrapEffect(dynamicSpikes, LPPal.orange, 28f)
    );
}
