package LP.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.content.Fx;
import mindustry.entities.effect.*;
import mindustry.graphics.*;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

import LP.graphics.LPPal;
import LP.graphics.PositionLightning;
import LP.util.struct.Vec2Seq;

public class LPFx {
    public static void loadPriority(){
    }

    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    private LPFx() {}

    public static Effect smoothColorCircle(Color out, float rad, float lifetime) {
        return new Effect(lifetime, rad * 2, e -> {
            Draw.blend(Blending.additive);
            float radius = e.fin(Interp.pow3Out) * rad;
            Fill.light(e.x, e.y, circleVertices(radius), radius, Color.clear, Tmp.c1.set(out).a(e.fout(Interp.pow5Out)));
            Drawf.light(e.x, e.y, radius * 1.3f, out, 0.7f * e.fout(0.23f));
            Draw.blend();
        }).layer(Layer.effect + 0.15f);
    }

    public static Effect smoothColorCircle(Color out, float rad, float lifetime, float alpha) {
        return new Effect(lifetime, rad * 2, e -> {
            Draw.blend(Blending.additive);
            float radius = e.fin(Interp.pow3Out) * rad;
            Fill.light(e.x, e.y, circleVertices(radius), radius, Color.clear, Tmp.c1.set(out).a(e.fout(Interp.pow5Out) * alpha));
            Drawf.light(e.x, e.y, radius * 1.3f, out, 0.7f * e.fout(0.23f));
            Draw.blend();
        }).layer(Layer.effect + 0.15f);
    }

    public static Effect circleOut(Color color, float rad, float lifetime) {
        return circleOut(color, rad, lifetime, rad * 0.3f, 0);
    }
    
    public static Effect circleOut(Color color, float rad, float lifetime, float fadeRad) {
        return circleOut(color, rad, lifetime, fadeRad, 0);
    }
    
    public static Effect circleOut(Color color, float rad, float lifetime, float fadeRad, int sides) {
        return new Effect(lifetime, rad * 1.5f, e -> {
            rand.setSeed(e.id);
            float alpha = e.fout();
            float circleRad = e.fin(Interp.circleOut) * rad;
            float innerRad = Math.max(0f, circleRad - fadeRad);
            
            Draw.blend(Blending.additive);
            
            if (sides <= 0) {
                if (innerRad > 0) {
                    Fill.light(e.x, e.y, circleVertices(circleRad), innerRad, Color.clear, Tmp.c1.set(color).a(alpha));
                } else {
                    Draw.color(color);
                    Draw.alpha(alpha);
                    Fill.circle(e.x, e.y, circleRad);
                    Draw.color();
                }
            } else {
                Draw.color(color);
                float bandWidth = circleRad - innerRad;
                int steps = Math.max(3, (int)(bandWidth) + 1);
                
                for (int i = 0; i < steps; i++) {
                    float t = i / (float)(steps - 1);
                    float r = innerRad + bandWidth * t;
                    float ringAlpha = alpha * t;
                    
                    Draw.alpha(ringAlpha);
                    Fill.poly(e.x, e.y, sides, r);
                }
                Draw.color();
            }
            
            int intensity = (int)Mathf.clamp(rad / 12f, 9f, 60f);
            for (int i = 0; i < intensity; i++) {
                Tmp.v1.set(1, 0).setToRandomDirection(rand).scl(circleRad);
                float width = rand.random(circleRad / 16f, circleRad / 12f) * e.fout();
                float length = rand.random(circleRad / 4f, circleRad / 1.5f) * (1f + e.fin()) / 2f;
                Draw.color(color);
                Draw.alpha(alpha);
                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, width, length, Tmp.v1.angle() - 180f);
            }
            Draw.color();
            
            Drawf.light(e.x, e.y, circleRad * 1.3f, color, 0.7f * e.fout(0.23f));
            Draw.blend();
        }).layer(Layer.effect + 0.15f);
    }

    public static Effect impactHit(Color color, float lifetime, float size) {
        return new Effect(lifetime, e -> {
            Draw.color(color);
            Lines.stroke(e.fout() * 2f);
            float rad = e.fin(Interp.circleOut) * size;
            Lines.circle(e.x, e.y, rad);

            float rot = e.rotation;
            float tw = size / 9f * e.fout(Interp.pow3Out);
            float th = 4f / 3f * size;

            float offset1 = 30f + Mathf.randomSeedRange(e.id, 8f);
            float offset2 = -30f + Mathf.randomSeedRange(e.id, 8f);

            Drawf.tri(e.x, e.y, tw, th, rot);
            Drawf.tri(e.x, e.y, tw, th / 2f, rot + 180f);
            Drawf.tri(e.x, e.y, tw, size, rot + offset1);
            Drawf.tri(e.x, e.y, tw, size / 2f, rot + 180f + offset1);
            Drawf.tri(e.x, e.y, tw, size, rot + offset2);
            Drawf.tri(e.x, e.y, tw, size / 2f, rot + 180f + offset2);

            Drawf.light(e.x, e.y, rad * 1.5f, color, e.fout());
        });
    }

    public static final Effect

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

    posLightning = (new Effect(PositionLightning.lifetime, 1500, e -> {
            if (!(e.data instanceof Vec2Seq)) return;
            Vec2Seq lines = e.data();

            color(e.color, e.color, e.fout() * 0.6f);

            stroke(e.rotation * e.fout());

            Fill.circle(lines.firstTmp().x, lines.firstTmp().y, getStroke() / 2f);

            for (int i = 0; i < lines.size() - 1; i++) {
                Vec2 cur = lines.setVec2(i, Tmp.v1);
                Vec2 next = lines.setVec2(i + 1, Tmp.v2);

                line(cur.x, cur.y, next.x, next.y, false);
                Fill.circle(next.x, next.y, getStroke() / 2f);
            }
        })).layer(Layer.effect - 0.001f),

        lightningSpark = new Effect(Fx.chainLightning.lifetime, (e) -> {
            color(e.color, e.color, e.fin() + 0.25F);
            stroke(0.65F + e.fout());
            randLenVectors(e.id, 3, e.fin() * e.rotation + 6.0F, (x, y) -> {
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 4.0F + 2.0F);
            });
            Fill.circle(e.x, e.y, 2.5F * e.fout());
        }),
    
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
        new WrapEffect(LPFx.dynamicSpikes, Color.valueOf("E5E5E5"), 24f),
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

        smoothColorCircle(LPPal.orangeMid, 40f, 120f)
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
    ),

    ionopolymerCrucibleCraft = new MultiEffect(
        new WaveEffect(){{
            colorFrom = Color.valueOf("FFEF6D00");
            colorTo = Color.valueOf("FFEF6D");
            sizeFrom = 12f;
            sizeTo = 6f;
            sides = 4;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.circleOut;
        }},

        new WaveEffect(){{
            colorFrom = Color.valueOf("FFEF6D00");
            colorTo = Color.valueOf("FFEF6D");
            sizeFrom = 12f;
            sizeTo = 18f;
            sides = 4;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.circleOut;
        }}
    ),

    ionopolymerCrucibleLargeCraft = new MultiEffect(
        new WaveEffect(){{
            colorFrom = Color.valueOf("FFEF6D00");
            colorTo = Color.valueOf("FFEF6D");
            sizeFrom = 18f;
            sizeTo = 12f;
            sides = 4;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.circleOut;
        }},

        new WaveEffect(){{
            colorFrom = Color.valueOf("FFEF6D00");
            colorTo = Color.valueOf("FFEF6D");
            sizeFrom = 18f;
            sizeTo = 24f;
            sides = 4;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.circleOut;
        }}
    ),

    erocrysExtractoryUpdate = new ParticleEffect(){{
        particles = 1;
        region = "lp-triangle";
        lifetime = 40f;
        length = 0f;
        baseLength = 0f;
        baseRotation = 0f;
        randLength = false;
        interp = Interp.pow4Out;
        sizeInterp = Interp.pow2In;
        sizeFrom = 2f;
        sizeTo = 0f;
        colorFrom = Color.valueOf("EDA76E");
        colorTo = Color.valueOf("EDA76E");
    }},

    transChimericFoundryCraft = new MultiEffect(
        new WaveEffect(){{
            colorFrom = Color.valueOf("E8EBFF00");
            colorTo = Color.valueOf("E8EBFF");
            sizeFrom = 16f;
            sizeTo = 12f;
            sides = 4;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.circleOut;
        }},

        new WaveEffect(){{
            colorFrom = Color.valueOf("E8EBFF00");
            colorTo = Color.valueOf("E8EBFF");
            sizeFrom = 16f;
            sizeTo = 22f;
            sides = 4;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.circleOut;
        }}
    ),

    highSpeedTranschimericFoundryUpdate = new ParticleEffect(){{
        particles = 1;
        region = "lp-square";
        lifetime = 30f;
        length = 0f;
        baseLength = 0f;
        baseRotation = 0f;
        randLength = false;
        interp = Interp.pow4Out;
        sizeInterp = Interp.pow2In;
        sizeFrom = 2f;
        sizeTo = 0f;
        colorFrom = Color.valueOf("E8EBFF");
        colorTo = Color.valueOf("E8EBFF");
    }},

    highSpeedTranschimericFoundryCraft = new MultiEffect(
        new WaveEffect(){{
            colorFrom = Color.valueOf("E8EBFF");
            colorTo = Color.valueOf("E8EBFF");
            sizeFrom = 40f;
            sizeTo = 0f;
            sides = 4;
            strokeFrom = 2f;
            strokeTo = 0f;
            interp = Interp.pow4Out;
        }},

        new WaveEffect(){{
            colorFrom = Color.valueOf("E8EBFF");
            colorTo = Color.valueOf("E8EBFF");
            sizeFrom = 50f;
            sizeTo = 0f;
            sides = 4;
            strokeFrom = 2f;
            strokeTo = 0f;
            interp = Interp.pow3Out;
        }}
    ),

    //turret
    lucenserDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 9;
            line = true;
            lifetime = 40f;
            length = 16f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new WaveEffect(){{
            lifetime = 40f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
            sizeFrom = 0f;
            sizeTo = 30f;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.pow5Out;
        }}
    ),

    lucenserJynsteelShoot = new MultiEffect(
        new ParticleEffect(){{
            particles = 4;
            lifetime = 40f;
            length = 12f;
            baseLength = 1f;
            cone = 3f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 2f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 5;
            line = true;
            lifetime = 30f;
            length = 18f;
            baseLength = 2f;
            cone = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 15f;
            lenTo = 0f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    lucenserJynsteelHit = new MultiEffect(
        new ParticleEffect(){{
            particles = 5;
            line = true;
            lifetime = 24f;
            length = 18f;
            baseLength = 2f;
            baseRotation = 180f;
            cone = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 18f;
            lenTo = 0f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 4;
            line = true;
            lifetime = 24f;
            length = 24f;
            baseLength = 2f;
            baseRotation = 180f;
            cone = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 10f;
            lenTo = 0f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    lucenserJynsteelDespawn = new MultiEffect(
        new ParticleEffect(){{
            particles = 7;
            line = true;
            lifetime = 28f;
            length = 18f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new WaveEffect(){{
            lifetime = 30f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
            sizeFrom = 0f;
            sizeTo = 30f;
            strokeFrom = 2f;
            strokeTo = 0f;
            interp = Interp.circleOut;
        }}
    ),

    lucenserErocrysShoot = new MultiEffect(
        new ParticleEffect(){{
            particles = 5;
            lifetime = 40f;
            length = 12f;
            baseLength = 1f;
            cone = 3f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 2f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }},

        new ParticleEffect(){{
            particles = 5;
            line = true;
            lifetime = 30f;
            length = 18f;
            baseLength = 2f;
            cone = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 15f;
            lenTo = 0f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }}
    ),

    lucenserErocrysHit = new MultiEffect(
        new ParticleEffect(){{
            particles = 3;
            region = "lp-triangle";
            lifetime = 40f;
            length = 24f;
            baseLength = 1f;
            baseRotation = 180f;
            spin = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 4f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 4;
            region = "lp-triangle";
            lifetime = 40f;
            length = 24f;
            baseLength = 1f;
            baseRotation = 180f;
            spin = 3f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }}
    ),

    lucenserErocrysDespawn = new MultiEffect(
        new ParticleEffect(){{
            particles = 7;
            line = true;
            lifetime = 28f;
            length = 18f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new WaveEffect(){{
            lifetime = 30f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
            sizeFrom = 0f;
            sizeTo = 30f;
            strokeFrom = 2f;
            strokeTo = 0f;
            interp = Interp.circleOut;
        }}
    ),

    disfluxDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 5;
            line = true;
            lifetime = 40f;
            length = 10f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 32f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 5;
            region = "lp-triangle";
            lifetime = 40f;
            length = 35f;
            baseLength = 2f;
            spin = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 6f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new WaveEffect(){{
            lifetime = 40f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
            sizeFrom = 0f;
            sizeTo = 36f;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.pow5Out;
        }}
    ),

    disfluxShoot = new ParticleEffect(){{
        particles = 4;
        line = true;
        lifetime = 30f;
        length = 25f;
        baseLength = 2f;
        cone = 30f;
        interp = Interp.pow3Out;
        sizeInterp = Interp.pow2In;
        lenFrom = 12f;
        lenTo = 0f;
        strokeFrom = 1f;
        strokeTo = 0f;
        colorFrom = LPPal.orange;
        colorTo = LPPal.orangeDark;
    }},

    disfluxHit = new ParticleEffect(){{
        particles = 5;
        line = true;
        lifetime = 30f;
        length = 28f;
        baseLength = 2f;
        interp = Interp.pow3Out;
        sizeInterp = Interp.pow2In;
        lenFrom = 6f;
        lenTo = 0f;
        strokeFrom = 1f;
        strokeTo = 0f;
        colorFrom = LPPal.orange;
        colorTo = LPPal.orangeDark;
    }},

    impactorDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 11;
            line = true;
            lifetime = 40f;
            length = 20f;
            baseLength = 2f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 40f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new WaveEffect(){{
            lifetime = 40f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
            sizeFrom = 0f;
            sizeTo = 50f;
            strokeFrom = 1f;
            strokeTo = 0f;
            interp = Interp.pow3Out;
        }}
    ),

    impactorShoot = new MultiEffect(
        new ParticleEffect(){{
            particles = 6;
            line = true;
            lifetime = 30f;
            length = 28f;
            baseLength = 2f;
            cone = 6f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 20f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},
        new ParticleEffect(){{
            particles = 5;
            lifetime = 30f;
            length = 30f;
            baseLength = 2f;
            cone = 5f;
            interp = Interp.pow10Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 2f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    impactorHit= new MultiEffect(
        LPFx.impactHit(LPPal.aureus, 30f, 54f),

        new ParticleEffect(){{
            particles = 8;
            line = true;
            lifetime = 40f;
            length = 34f;
            baseLength = 6f;
            cone = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 4;
            line = true;
            lifetime = 40f;
            length = 40f;
            baseLength = 10f;
            cone = 20f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 30f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    impactorHitSmall = new MultiEffect(
        LPFx.impactHit(LPPal.aureus, 30f, 54f),

        new ParticleEffect(){{
            particles = 4;
            line = true;
            lifetime = 40f;
            length = 20f;
            baseLength = 2f;
            cone = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 12f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 4;
            line = true;
            lifetime = 40f;
            length = 14f;
            baseLength = 10f;
            cone = 20f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 18f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    repulstarDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 12;
            line = true;
            lifetime = 30f;
            length = 12f;
            baseLength = 2f;
            interp = Interp.pow10Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 40f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 16;
            line = true;
            lifetime = 30f;
            length = 40f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            lifetime = 30f;
            length = 32f;
            baseLength = 2f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 8f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("45454570");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 30f;
            interp = Interp.pow5Out;
            sizeFrom = 0f;
            sizeTo = 80f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }}
    ),

    repulstarShoot = new MultiEffect(
        new ParticleEffect(){{
            particles = 1;
            region = "lp-starenergy-bullet-back";
            lifetime = 20f;
            length = 0f;
            baseLength = 0f;
            baseRotation = 45f;
            useRotation = false;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow3In;
            sizeFrom = 12f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},
        
        new ParticleEffect(){{
            particles = 9;
            line = true;
            lifetime = 25f;
            length = 40f;
            baseLength = 2f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            cone = 50f;
            lenFrom = 18f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 6;
            region = "lp-triangle";
            lifetime = 20f;
            length = 28f;
            baseLength = 2f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            spin = 3f;
            cone = 50f;
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 6;
            region = "lp-triangle";
            lifetime = 20f;
            length = 28f;
            baseLength = 2f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            spin = -3f;
            cone = 50f;
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    repulstarSmoke = new ParticleEffect(){{
        particles = 6;
        lifetime = 30f;
        length = 16f;
        baseLength = 1f;
        interp = Interp.pow10Out;
        sizeInterp = Interp.pow5In;
        sizeFrom = 5f;
        sizeTo = 0f;
        colorFrom = Color.valueOf("45454570");
        colorTo = Color.valueOf("48484800");
    }},

    repulstarHit = new MultiEffect(
        LPFx.smoothColorCircle(LPPal.aureus, 80f, 25f),
        LPFx.smoothColorCircle(LPPal.aureus, 60f, 20f),

        new ParticleEffect(){{
            particles = 1;
            region = "lp-star";
            lifetime = 20f;
            length = 0f;
            baseLength = 0f;
            baseRotation = 0f;
            useRotation = false;
            interp = Interp.circleIn;
            sizeInterp = Interp.circleIn;
            sizeFrom = 80f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            startDelay = 20f;
            particles = 1;
            region = "lp-star";
            lifetime = 20f;
            length = 0f;
            baseLength = 0f;
            baseRotation = 0f;
            useRotation = false;
            interp = Interp.circleIn;
            sizeInterp = Interp.circleIn;
            sizeFrom = 80f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 16;
            line = true;
            lifetime = 25f;
            length = 40f;
            baseLength = 2f;
            interp = Interp.circleOut;
            sizeInterp = Interp.circleIn;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},
        
        new ParticleEffect(){{
            particles = 2;
            region = "lp-triangle";
            lifetime = 30f;
            length = 64f;
            baseLength = -2f;
            interp = Interp.circleOut;
            sizeInterp = Interp.pow10In;
            spin = 7f;
            sizeFrom = 4f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 2;
            region = "lp-triangle";
            lifetime = 30f;
            length = 64f;
            baseLength = -2f;
            interp = Interp.circleOut;
            sizeInterp = Interp.pow10In;
            spin = -7f;
            sizeFrom = 4f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 4;
            region = "lp-star";
            lifetime = 60f;
            length = 64f;
            baseLength = 2f;
            baseRotation = 0f;
            useRotation = false;
            interp = Interp.circleOut;
            sizeInterp = Interp.pow10In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    radianceDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 12;
            line = true;
            lifetime = 40f;
            length = 45f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 55f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 12;
            lifetime = 40f;
            length = 90f;
            baseLength = 5f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow4In;
            sizeFrom = 7f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = Color.valueOf("54545400");
        }},

        new ParticleEffect(){{
            particles = 12;
            lifetime = 45f;
            length = 32f;
            baseLength = 2f;
            interp = Interp.pow10Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 8f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("45454570");
            colorTo = Color.valueOf("47474700");
        }},

        new WaveEffect(){{
            lifetime = 40f;
            interp = Interp.pow5Out;
            sizeFrom = 0f;
            sizeTo = 80f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }}
    ),

    radianceShoot = new MultiEffect(
        new ParticleEffect(){{
            particles = 8;
            line = true;
            lifetime = 30f;
            length = 28f;
            baseLength = 2f;
            cone = 30f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow3In;
            lenFrom = 18f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},
        new ParticleEffect(){{
            particles = 5;
            lifetime = 20f;
            length = 28f;
            baseLength = 2f;
            cone = 30f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow4In;
            sizeFrom = 6f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    radianceSmoke = new ParticleEffect(){{
        particles = 8;
        lifetime = 35f;
        length = 18f;
        baseLength = 2f;
        interp = Interp.pow5Out;
        sizeInterp = Interp.pow10In;
        sizeFrom = 5f;
        sizeTo = 0f;
        colorFrom = Color.valueOf("45454570");
        colorTo = Color.valueOf("47474700");
    }},

    radianceHit = new MultiEffect(
        new WrapEffect(){{
            effect = Fx.instBomb;
            color = LPPal.aureus;
            rotation = 1f;
        }},
        new ParticleEffect(){{
            particles = 24;
            line = true;
            lifetime = 15f;
            length = 48f;
            baseLength = 2f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 28f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},
        new ParticleEffect(){{
            particles = 8;
            lifetime = 15f;
            length = 48f;
            baseLength = 2f;
            interp = Interp.pow2Out;
            sizeInterp = Interp.pow3In;
            sizeFrom = 7f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},
        new WaveEffect(){{
            lifetime = 15f;
            interp = Interp.circleOut;
            sizeFrom = 0f;
            sizeTo = 72f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    meteorDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 12;
            line = true;
            lifetime = 30f;
            length = 12f;
            baseLength = 2f;
            interp = Interp.pow10Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 40f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 16;
            line = true;
            lifetime = 30f;
            length = 40f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            lifetime = 30f;
            length = 32f;
            baseLength = 2f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 8f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("45454570");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 30f;
            interp = Interp.pow5Out;
            sizeFrom = 0f;
            sizeTo = 80f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }}
    ),

    meteorErocrysShoot = new ParticleEffect(){{
        particles = 7;
        line = true;
        lifetime = 30f;
        length = 16f;
        baseLength = 2f;
        cone = 32f;
        interp = Interp.pow3Out;
        sizeInterp = Interp.pow2In;
        lenFrom = 8f;
        lenTo = 0f;
        strokeFrom = 1.5f;
        strokeTo = 0f;
        colorFrom = LPPal.orangeDark;
        colorTo = LPPal.orangeDark;
    }},

    meteorErocrysSmoke = new ParticleEffect(){{
        particles = 2;
        line = true;
        lifetime = 30f;
        length = 12f;
        baseLength = 2f;
        cone = 30f;
        interp = Interp.pow2Out;
        sizeInterp = Interp.pow3In;
        lenFrom = 12f;
        lenTo = 0f;
        strokeFrom = 1.5f;
        strokeTo = 0f;
        colorFrom = LPPal.orange;
        colorTo = LPPal.orangeDark;
    }},

    meteorErocrysHit = new MultiEffect(
        new WrapEffect(LPFx.dynamicSpikes, LPPal.orangeDark, 32f),

        new ParticleEffect(){{
            particles = 6;
            line = true;
            lifetime = 30f;
            length = 32f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow3In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.orangeDark;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 6;
            region = "lp-square";
            lifetime = 30f;
            length = 32f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow3In;
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = LPPal.orangeDark;
            colorTo = LPPal.orangeDark;
        }}
    ),

    meteorJynsteelShoot = new ParticleEffect(){{
        particles = 7;
        line = true;
        lifetime = 30f;
        length = 16f;
        baseLength = 2f;
        cone = 32f;
        interp = Interp.pow3Out;
        sizeInterp = Interp.pow2In;
        lenFrom = 4f;
        lenTo = 0f;
        strokeFrom = 1.5f;
        strokeTo = 0f;
        colorFrom = LPPal.aureus;
        colorTo = LPPal.aureus;
    }},

    meteorJynsteelSmoke = new ParticleEffect(){{
        particles = 2;
        line = true;
        lifetime = 30f;
        length = 12f;
        baseLength = 2f;
        cone = 30f;
        interp = Interp.pow2Out;
        sizeInterp = Interp.pow2In;
        lenFrom = 8f;
        lenTo = 0f;
        strokeFrom = 1.5f;
        strokeTo = 0f;
        colorFrom = LPPal.aureus;
        colorTo = LPPal.aureus;
    }},

    meteorJynsteelHit = new MultiEffect(
        new WrapEffect(LPFx.dynamicSpikes, LPPal.aureus, 38f),

        new ParticleEffect(){{
            particles = 8;
            region = "lp-square";
            lifetime = 50f;
            length = 24f;
            baseLength = 2f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow3In;
            sizeFrom = 4f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 4;
            line = true;
            lifetime = 50f;
            length = 36f;
            baseLength = 2f;
            baseRotation = 180f;
            cone = 40f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 30f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    meteorJynsteelDespawn = new MultiEffect(
        new WrapEffect(LPFx.dynamicSpikes, LPPal.aureus, 38f),

        new ParticleEffect(){{
            particles = 4;
            region = "lp-square";
            lifetime = 50f;
            length = 24f;
            baseLength = 2f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow3In;
            sizeFrom = 4f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 5;
            line = true;
            lifetime = 50f;
            length = 36f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 15f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    );
}
