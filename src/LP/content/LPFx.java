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
import LP.graphics.TrailEffect;
import LP.graphics.TrailEffect.CTrail;
import LP.util.struct.Vec2Seq;
import LP.graphics.Drawn;

public class LPFx {
    public static void loadPriority(){
    }

    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final float lightningAlign = 0.5f;

    private LPFx() {}

    public static Effect smoothColorCircle(Color out, float rad, float lifetime) {
        return new Effect(lifetime, rad * 2, e -> {
            Draw.blend(Blending.additive);
            float radius = e.fin(Interp.pow3Out) * rad;
            Fill.light(e.x, e.y, circleVertices(radius), radius, Color.clear, Tmp.c1.set(out).a(e.fout(Interp.pow5Out)));
            Drawf.light(e.x, e.y, radius * 1.3f, out, 0.7f * e.fout(0.23f));
            Draw.blend();
        });
    }

    public static Effect smoothColorCircle(Color out, float rad, float lifetime, float alpha) {
        return new Effect(lifetime, rad * 2, e -> {
            Draw.blend(Blending.additive);
            float radius = e.fin(Interp.pow3Out) * rad;
            Fill.light(e.x, e.y, circleVertices(radius), radius, Color.clear, Tmp.c1.set(out).a(e.fout(Interp.pow5Out) * alpha));
            Drawf.light(e.x, e.y, radius * 1.3f, out, 0.7f * e.fout(0.23f));
            Draw.blend();
        });
    }

    public static Effect smoothCircleOut(float lifetime, Color color, float size, int amount, boolean smooth) {
        return new MultiEffect(
            new Effect(lifetime * 2.5f, e -> {
                color(color);

                e.scaled(lifetime, i -> {
                    stroke(3f * i.fout(Interp.pow2Out));
                    if (!smooth) circle(e.x, e.y, 3f + i.fin(Interp.circleOut) * size);
                    if (smooth) {
                        Draw.blend(Blending.additive);
                        circle(e.x, e.y, i.fin(Interp.circleOut) * size);
                        Drawn.shockWave(i.x, i.y, size, size * 0.25f * i.fout(Interp.pow2Out), i.fin(Interp.circleOut), color);
                        Draw.blend();
                    }
                });
                Drawf.light(e.x, e.y, size * 1.5f, color, 0.8f * e.fout());
            })
        );
    }

    public static Effect circleOut(float lifetime, Color color, float range) {
        return new Effect(lifetime, range * 2f, (e) -> {
            rand.setSeed(e.id);
            color(color.cpy().lerp(Color.white, 0.8f), color, e.fin(Interp.pow5Out));
            float circleRad = e.fin(Interp.circleOut) * range;
            stroke(Mathf.clamp(range / 24f, 3f, 20f) * e.fout());
            circle(e.x, e.y, circleRad);

            int intensity = (int)Mathf.clamp(range / 8, 9, 60);
            for (int i = 0; i < intensity; ++i) {
                Tmp.v1.set(1f, 0f).setToRandomDirection(rand).scl(circleRad);
                Drawn.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, rand.random(circleRad / 12f, circleRad / 12f) * e.fout(),
                rand.random(circleRad / 4f, circleRad / 1.5f) * (1f + e.fin()) / 2f, Tmp.v1.angle() - 180f);
            }
        });
    }

    public static Effect XSharpHit(float lifetime, Color color, float range) {
        return new Effect(lifetime, range * 2f, (e) -> {
            color(color.cpy().lerp(Color.white, 0.8f), color, e.fin(Interp.pow5Out));
            float tl = range * 0.8f;
            float tw = range / 12f * e.fout(Interp.pow3Out);

            float m = e.fin(Interp.pow3Out) * (range - tl * 0.4f);

            float[] angles = {45f, 135f, 225f, 315f};
            for (float angle : angles) {
                float rad = angle * Mathf.degRad;
                float x = e.x + Mathf.cos(rad) * m;
                float y = e.y + Mathf.sin(rad) * m;

                Drawn.tri(x, y, tw, tl * (1f + 0.2f * e.fin(Interp.pow3Out)), angle);
                Drawn.tri(x, y, tw, tl * (1f + 0.2f * e.fin(Interp.pow3Out)) * 0.1f, angle + 180f);
            }
        });
    }

    public static Effect XSharpShoot(float lifetime, Color color, float size) {
        return new Effect(lifetime, size * 1.5f, (e) -> {
            color(color);
            float tw = size / 8f * e.fout(Interp.circleIn);

            for (int i : Mathf.signs) {
                Drawn.tri(e.x, e.y, tw, size * (1 + 0.2f * e.fin(Interp.circleIn)), e.rotation + 90 * i);
                Drawn.tri(e.x, e.y, tw, size * (1 + 0.2f * e.fin(Interp.circleIn)) * 0.7f, e.rotation + 55f + 90 * i);
            }

            color(Color.white);
            for (int i : Mathf.signs) {
                Drawn.tri(e.x, e.y, tw * 0.2f, size * (1f + 0.2f * e.fin(Interp.circleIn)) * 0.5f, e.rotation + 90 * i);
                Drawn.tri(e.x, e.y, tw * 0.2f, size * (1f + 0.2f * e.fin(Interp.circleIn) * 0.7f) * 0.5f, e.rotation + 55f + 90 * i);
            }
        }).followParent(true);
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

    public static Effect energyExplosion(Color color, float lifetime, float radius, int count) {
        return new Effect(lifetime, radius * 2, e -> {
            Draw.color(color);

            float thMin = radius * 0.4f;
            float thMax = radius;

            rand.setSeed(e.id);

            for (int i = 0; i < count; i++) {
                float th = rand.random(thMin, thMax) * e.fout();
                float tw = rand.random(8f, 16f) * e.fout();
                float tr = e.rotation + rand.random(360f);

                Drawf.tri(e.x, e.y, tw, th, tr);
            }

            Drawf.light(e.x, e.y, radius * 1.2f, color, e.fout());
        });
    }
    
    public static Effect energyExplosion(Color color, float lifetime, float radius, int count, float maxWidth, float minWidth) {
        return new Effect(lifetime, radius * 2, e -> {
            Draw.color(color);

            float thMin = radius * 0.4f;
            float thMax = radius;

            rand.setSeed(e.id);

            for (int i = 0; i < count; i++) {
                float th = rand.random(thMin, thMax) * e.fout();
                float tw = rand.random(minWidth, maxWidth) * e.fout();
                float tr = e.rotation + rand.random(360f);

                Drawf.tri(e.x, e.y, tw, th, tr);
            }

            Drawf.light(e.x, e.y, radius * 1.2f, color, e.fout());
        });
    }

    public static Effect BlackHoleHit(Color color, float lifetime, float radius) {
        return new Effect(lifetime, radius * 2, e -> {
            color(color);

            float maxRad = radius;
            float minRad = (radius / 3f * 2f);
            
            Fill.circle(e.x, e.y, e.fout(Interp.circleIn) * maxRad);

            Draw.z(111f);
            color(Color.black);
            Fill.circle(e.x, e.y, e.fout(Interp.circleIn) * minRad);
            
            Draw.z();
        });
    }

    public static TrailEffect trailHitSpark(float lifetime, Color color, int num, float range, float stroke, float length) {
        int intensity = num;
        return new TrailEffect(lifetime, range * 2, color, color, intensity, (int) length, stroke)
            .trailUpdater((e, trail, x, y, width, len, index) -> {
                long id = e.id + index * 45L;
                rand.setSeed(e.id + id);
                randLenVectors(e.id + id, 1, e.fin(Interp.pow3Out) * range, e.rotation, 360, (x1, y1) -> {
                    float newX = x + x1;
                    float newY = y + y1;
                    trail.length = (int) (len * LPFx.fout(e.fin(), 0.06f));
                    trail.update(newX, newY, width * e.fout());
                    if (trail instanceof CTrail) {
                        ((CTrail) trail).updateLastPos(newX, newY);
                    }
                });
            }).drawTri(true);
    }

    /** 插值要反着填pow10In -> pow10Out */
    public static Effect sharpHitSpark(float lifetime, Color color, int num, float range, float length, Interp interp) {
        return sharpHitSpark(lifetime, color, num, range, length, interp, 360f);
    }

    public static Effect sharpHitSpark(float lifetime, Color color, int num, float range, float length, Interp interp, float cone) {
        return new Effect(lifetime, length * 2, e -> {
            color(color);

            for (int i = 0; i < num; i++) {
                rand.setSeed(e.id + i);
                
                float tw = rand.random(length / 6f, length / 6f * 1.5f) * e.fout(interp);
                float tl = length * (1f - 0.4f * e.fin(Interp.circleIn));
                float tl1 = tl * 0.4f;
                float rad = range - length;
                float minRad = tl1 * 1.3f;
                
                float angle = e.rotation + rand.range(cone);
                float randomRad = rand.random(minRad, rad);
                float px = e.x + Angles.trnsx(angle, randomRad);
                float py = e.y + Angles.trnsy(angle, randomRad);
                
                float triAngle = Angles.angle(px, py, e.x, e.y);
                
                Drawf.tri(px, py, tw, tl, triAngle + 180f);
                Drawf.tri(px, py, tw, tl1, triAngle);
            }
        });
    }

    public static Effect sharpHitRotateBlast(float lifetime, Color color, int num, float range, float rotSpeed) {
        return new Effect(lifetime, range * 2, e -> {
            color(color);
            
            rand.setSeed(e.id);
            
            float prog = e.fin(Interp.circleIn);
            float width = range / 12f * (1f - prog);
            float baseLength = range * 0.6f * (1f - prog * 0.5f);
            
            for(int i = 0; i < num; i++){
                float randomDist = rand.random(baseLength, range - baseLength);
                
                float sizeScale = rand.random(0.5f, 1f);
                float actualLength = baseLength * sizeScale;
                float smallLength = actualLength * 0.3f;
                
                float angle = Time.time * rand.random(rotSpeed, rotSpeed * 2f) + rand.random(360f);
                
                float px = e.x + Angles.trnsx(angle, randomDist);
                float py = e.y + Angles.trnsy(angle, randomDist);
                
                float pointRot = Angles.angle(px, py, e.x, e.y);
                
                Drawf.tri(px, py, width, actualLength, pointRot + 180f);
                Drawf.tri(px, py, width, smallLength, pointRot);
            }
        });
    }

    /** 我为什么要写这个石 */
    public static Effect railShoot(float lifetime, Color color, float size, Interp interp) {
        return new Effect(lifetime, size * 2, (e) -> {
            color(color);

            float fout = e.fout(interp);
            float lightLen = size * 1.5f;

            float tw = size / 8f * fout;
            float tl = size;
            
            Drawf.tri(e.x, e.y, tw * fout, lightLen, e.rotation + 90f);
            Drawf.tri(e.x, e.y, tw * fout, lightLen, e.rotation - 90f);

            Drawf.tri(e.x, e.y, tw, tl * 1.4f, e.rotation);
            Drawf.tri(e.x, e.y, tw, tl * 1.2f, e.rotation + 180f);

            Drawf.tri(e.x, e.y, tw, tl, e.rotation + 15f);
            Drawf.tri(e.x, e.y, tw, tl * 0.8f, e.rotation + 20f + 180f);
            Drawf.tri(e.x, e.y, tw, tl, e.rotation - 15f);
            Drawf.tri(e.x, e.y, tw, tl * 0.8f, e.rotation - 20f + 180f);

            color(Color.white);
            Drawf.tri(e.x, e.y, tw * fout * 0.5f, lightLen * 0.5f, e.rotation + 90f);
            Drawf.tri(e.x, e.y, tw * fout * 0.5f, lightLen * 0.5f, e.rotation - 90f);

            Drawf.tri(e.x, e.y, tw * 0.5f, tl * 1.4f * 0.5f, e.rotation);
            Drawf.tri(e.x, e.y, tw * 0.5f, tl * 1f * 0.5f, e.rotation + 180f);

            Drawf.tri(e.x, e.y, tw * 0.5f, tl * 0.5f, e.rotation + 15f);
            Drawf.tri(e.x, e.y, tw * 0.5f, tl * 0.8f * 0.5f, e.rotation + 20f + 180f);
            Drawf.tri(e.x, e.y, tw * 0.5f, tl * 0.5f, e.rotation - 15f);
            Drawf.tri(e.x, e.y, tw * 0.5f, tl * 0.8f * 0.5f, e.rotation - 20f + 180f);
        });
    }

    public static Effect cutting(float lifetime, Color color, Color bottomColor, Boolean drawBottom, float length, float rotation) {
        return new Effect(lifetime, length * 2f, (e) -> {
            float len, ang;
            if (e.data instanceof Float) {
                len = (Float) e.data;
                ang = e.rotation;
            } else {
                len = length;
                ang = rotation == -1f ? Mathf.randomSeed(e.id, 0, 360) : rotation;
            }

            color(color);
            Drawf.light(e.x, e.y, e.fout() * len, color, 0.7f);
            float fout = e.fout(Interp.exp10Out);
            for (int i : Mathf.signs) {
                Drawn.tri(e.x, e.y, len / 12f * fout * (Mathf.absin(0.8f, 0.07f) + 1),
                len * 2 * Interp.swingOut.apply(Mathf.curve(e.fin(), 0, 0.7f)) * (Mathf.absin(0.8f, 0.12f) + 1) * (1f + e.fin(Interp.circleOut) * 0.2f),
                ang + i * 90);
            }

            if (drawBottom) {
                color(bottomColor);
                z(Layer.effect + 0.0001f);
                for (int i : Mathf.signs) {
                    Drawn.tri(e.x, e.y, (len * 0.7f) / 12f * fout * (Mathf.absin(0.8f, 0.07f) + 1),
                    len * 2 * 0.7f * Interp.swingOut.apply(Mathf.curve(e.fin(), 0, 0.7f)) * (Mathf.absin(0.8f, 0.12f) + 1) * (1f + e.fin(Interp.circleOut) * 0.5f),
                    ang + i * 90);
                }

                z(Layer.effect + 0.0001f);
                for (int i : Mathf.signs) {
                    Drawn.tri(e.x, e.y, (len * 0.7f) / 12f * fout * (Mathf.absin(0.8f, 0.07f) + 1),
                    len * 2 * 0.7f * Interp.swingOut.apply(Mathf.curve(e.fin(), 0, 0.7f)) * (Mathf.absin(0.8f, 0.12f) + 1) * (1f + e.fin(Interp.circleOut) * 0.5f),
                    ang + i * 90);
                }
            }

        }).layer(Layer.effect - 1f);
    }

    public static Effect cutting(float lifetime, Color color, Color bottomColor, float length, float rotation, float bottomLayer) {
        return new Effect(lifetime, length * 2f, (e) -> {
            float len, ang;
            if (e.data instanceof Float) {
                len = (Float) e.data;
                ang = e.rotation;
            } else {
                len = length;
                ang = rotation == -1f ? Mathf.randomSeed(e.id, 0, 360) : rotation;
            }

            color(color);
            Drawf.light(e.x, e.y, e.fout() * len, color, 0.7f);
            float fout = e.fout(Interp.exp10Out);
            for (int i : Mathf.signs) {
                Drawn.tri(e.x, e.y, len / 12f * fout * (Mathf.absin(0.8f, 0.07f) + 1),
                len * 2 * Interp.swingOut.apply(Mathf.curve(e.fin(), 0, 0.7f)) * (Mathf.absin(0.8f, 0.12f) + 1) * (1f + e.fin(Interp.circleOut) * 0.2f),
                ang + i * 90);
            }

            color(bottomColor);
            z(bottomLayer);
            for (int i : Mathf.signs) {
                Drawn.tri(e.x, e.y, (len * 0.7f) / 12f * fout * (Mathf.absin(0.8f, 0.07f) + 1),
                len * 2 * 0.7f * Interp.swingOut.apply(Mathf.curve(e.fin(), 0, 0.7f)) * (Mathf.absin(0.8f, 0.12f) + 1) * (1f + e.fin(Interp.circleOut) * 0.5f),
                ang + i * 90);
            }

            z(bottomLayer);
                for (int i : Mathf.signs) {
                Drawn.tri(e.x, e.y, (len * 0.7f) / 12f * fout * (Mathf.absin(0.8f, 0.07f) + 1),
                len * 2 * 0.7f * Interp.swingOut.apply(Mathf.curve(e.fin(), 0, 0.7f)) * (Mathf.absin(0.8f, 0.12f) + 1) * (1f + e.fin(Interp.circleOut) * 0.5f),
                ang + i * 90);
            }
            
        }).layer(Layer.effect - 1f);
    }

    public static Effect annihilation(float lifetime, Color color, float length, int num) {
        return new Effect(lifetime, length * 20, e -> {
            color(color);
            float baseLen = length * 10f;
            float range = baseLen * 2f;

            Draw.z(110.001f);
            Draw.color(Color.black);
            float circleRadius = length * (1 + 1.4f * (e.fin(Interp.circleOut) * 0.5f)) * e.fout(Interp.circleOut);
            Fill.circle(e.x, e.y, circleRadius);

            Draw.z(110f);
            rand.setSeed(e.id);
            for (int i = 0; i < num; i++) {
                float angle = e.rotation + rand.random(360f);
                float lenProg = e.fin();
                float triLen;

                if(lenProg < 0.8f){
                    triLen = baseLen * (1f + 0.5f * lenProg / 0.8f) * 0.8f;
                }else{
                    float innerProg = (lenProg - 0.8f) / 0.2f;
                    triLen = baseLen * 1.5f * (1f - innerProg) * 0.8f;
                }

                float triWidth = baseLen / 6f;
                if(lenProg >= 0.8f){
                    float innerProg = (lenProg - 0.8f) / 0.2f;
                    triWidth *= (1f - innerProg);
                }
                
                Draw.color(color);
                Drawf.tri(e.x, e.y, triWidth, triLen, angle);
                
                Draw.color(Color.black);
                Drawf.tri(e.x, e.y, triWidth * 0.4f, triLen * 0.6f, angle);
            }
            Draw.color(color.cpy().lerp(Color.white, 0.8f), color, e.fin(Interp.pow5Out));
            float circleRad = e.fin(Interp.circleOut) * range * 0.55f;
            stroke(Mathf.clamp(range / 24f, 3f, 20f) * e.fout());
            circle(e.x, e.y, circleRad);
            
            int intensity = (int)Mathf.clamp(range / 12f, 9, 30);
            for (int i = 0; i < intensity; ++i) {
                Tmp.v1.set(1f, 0f).setToRandomDirection(rand).scl(circleRad);
                Drawn.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 
                    rand.random(circleRad / 12f, circleRad / 12f) * e.fout(),
                    rand.random(circleRad / 4f, circleRad / 1.5f) * (1f + e.fin()) / 2f, 
                    Tmp.v1.angle() - 180f);
            }
            
            Draw.reset();
        });
    }

    public static Effect triHitSpark(float lifetime, Color color, int num, float radius) {
        return triHitSpark(lifetime, color, num, radius, 25f, Interp.pow3Out);
    }

    public static Effect triHitSpark(float lifetime, Color color, int num, float radius, Interp foutInterp) {
        return triHitSpark(lifetime, color, num, radius, 25f, foutInterp);
    }

    public static Effect triHitSpark(float lifetime, Color color, int num, float radius, float cone) {
        return triHitSpark(lifetime, color, num, radius, cone, Interp.pow3Out);
    }

    public static Effect triHitSpark(float lifetime, Color color, int num, float radius, float cone, Interp foutInterp) {
        return new Effect(lifetime, radius * 1.5f, e -> {
            color(color);

            for (int i = 0; i < num; i++) {
                rand.setSeed(e.id + i);

                float rot = e.rotation + rand.range(cone);

                float w = rand.random(radius / 16f, radius / 10f) * e.fout(foutInterp);
                float l = rand.random(radius * 0.3f, radius) * e.fout(Interp.pow5Out);

                Drawn.tri(e.x, e.y, w, l, rot);
                Drawn.tri(e.x, e.y, w, l * 0.4f, rot + 180f);
            }

            Drawn.tri(e.x, e.y, radius / 16f * e.fout(foutInterp), radius * 0.7f, e.rotation);
            Drawn.tri(e.x, e.y, radius / 16f * e.fout(foutInterp), radius * 0.5f, e.rotation + 180f);
            Drawf.light(e.x, e.y, radius * e.fout(), color, 0.7f);
        });
    }

    /**public static Effect TurretDestroy(float lifetime, Color color, int size) {
        return new Effect(lifetime, size * 2, b -> {
            rand.setSeed(b.id);
            color(color);

            stroke(2f + size * 0.2f * b.fout(Interp.pow10In));
            Lines.circle(b.x, b.y, size * 1.325f * b.fin(Interp.pow10Out));

            int totalLines = 4 * (int)(size / 2f);
            float lenScale = size / 5f;
            float strokeScale = size / 5f;

            for (int i = 0; i < totalLines; i++) {
                boolean useF = i < totalLines * 5 / 11;
                float lenFrom = useF ? 55f : 24f;
                float strokeFrom = 2f;
                Interp lenInterp = useF ? Interp.pow5Out : Interp.pow3Out;

                float angle = rand.random(360f);
                float d = rand.random(0f, lenFrom * lenScale);
                float x = b.x + Angles.trnsx(angle, d);
                float y = b.y + Angles.trnsy(angle, d);

                float l = lenFrom * lenScale * b.fin(lenInterp);
                float w = strokeFrom * strokeScale * b.fin(Interp.pow2In) * b.fout();

                Lines.stroke(w);
                Lines.lineAngle(x, y, Mathf.angle(x, y), l);
                Drawf.light(x, y, l * 2f, color, 0.6f * b.fout());
            }

            float smokeLifetime = 40f * (size / 5f);
            b.scaled(smokeLifetime, inner -> {
                int baseParticles = 24;
                int totalParticles = (int)(baseParticles * (size / 5f));
                float scale = size / 5f;

                for (int i = 0; i < totalParticles; i++) {
                    boolean useF = rand.nextBoolean();
                    float len = useF ? 90f : 54f;
                    float sizeFrom = useF ? 5f : 12f;
                    Color colorFrom = useF ? LPPal.orange : Color.valueOf("454545");
                    Color colorTo = useF ? Color.valueOf("54545400") : Color.valueOf("47474700");
                    Interp lenInterp = useF ? Interp.pow3Out : Interp.pow4Out;
                    Interp sizeInterp = useF ? Interp.pow2In : Interp.pow5In;

                    float angle = rand.random(360f);
                    float dist = rand.random(0f, len * scale * inner.fin(lenInterp));
                    float x = inner.x + Angles.trnsx(angle, dist);
                    float y = inner.y + Angles.trnsy(angle, dist);

                    float sizeNow = sizeFrom * scale * inner.fin(sizeInterp) * inner.fout();
                    if (sizeNow <= 0.01f) continue;

                    Tmp.c1.set(colorFrom).lerp(colorTo, inner.fin());
                    Draw.color(Tmp.c1);

                    Fill.circle(x, y, sizeNow);
                }
            });

            Drawf.light(b.x, b.y, size * 1.5f * b.fout(), color, 0.7f);
        });
    }*/

    public static float fout(float fin, float margin) {
        return fin >= 1 - margin ? 1 - (fin - (1 - margin)) / margin : 1;
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

    FFstarHit = new Effect(40f, e -> {
        color(LPPal.aureusDark);
        stroke(1f * e.fout(Interp.circleOut));
        Lines.circle(e.x, e.y, 12f * e.fin(Interp.circleOut));

        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 6f * e.fout(Interp.pow3In), 28f * e.fout(Interp.pow3In), 45f + i * 90);
        }
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

    chainLightningFadeReversed = new Effect(220f, 500f, e -> {
        if (!(e.data instanceof Position)) return;
        Position p = e.data();
        float tx = e.x, ty = e.y, dst = Mathf.dst(p.getX(), p.getY(), tx, ty);
        Tmp.v1.set(e.x, e.y).sub(p).nor();

        e.lifetime = dst * 0.3f;
        float normx = Tmp.v1.x, normy = Tmp.v1.y;
        float range = e.rotation;
        int links = Mathf.ceil(dst / range);
        float spacing = dst / links;

        Lines.stroke(2.5f * Mathf.curve(e.fout(), 0, 0.7f));
        color(e.color, Color.white, e.fout() * 0.6f);

        Lines.beginLine();

        Fill.circle(p.getX(), p.getY(), Lines.getStroke() / 2);
        Lines.linePoint(p);

        rand.setSeed(e.id);

        float fin = Mathf.curve(e.fin(), 0, lightningAlign);
        int i;
        float nx = p.getX(), ny = p.getY();
        for (i = 0; i < (int) (links * fin); i++) {
            if (i == links - 1) {
                nx = tx;
                ny = ty;
            } else {
                float len = (i + 1) * spacing;
                Tmp.v1.setToRandomDirection(rand).scl(range / 2f);
                nx = p.getX() + normx * len + Tmp.v1.x;
                ny = p.getY() + normy * len + Tmp.v1.y;
            }

            linePoint(nx, ny);
        }

        if (i < links) {
            float f = Mathf.clamp(fin * links % 1);
            float len = (i + 1) * spacing;
            Tmp.v1.setToRandomDirection(rand).scl(range / 2f);
            Tmp.v2.set(nx, ny);
            if (i == links - 1) Tmp.v2.lerp(tx, ty, f);
            else Tmp.v2.lerp(p.getX() + (normx * len + Tmp.v1.x), p.getY() + (normy * len + Tmp.v1.y), f);

            linePoint(Tmp.v2.x, Tmp.v2.y);
            Fill.circle(Tmp.v2.x, Tmp.v2.y, getStroke() / 2);
        }

        Lines.endLine();
    }).followParent(false),

    chainLightningFade = new Effect(220f, 500f, e -> {
        if (!(e.data instanceof Position)) return;
        Position p = e.data();
        float tx = p.getX(), ty = p.getY(), dst = Mathf.dst(e.x, e.y, tx, ty);
        Tmp.v1.set(p).sub(e.x, e.y).nor();

        e.lifetime = dst * 0.3f;
        float normx = Tmp.v1.x, normy = Tmp.v1.y;
        float range = e.rotation;
        int links = Mathf.ceil(dst / range);
        float spacing = dst / links;

        stroke(2.5f * Mathf.curve(e.fout(), 0, 0.7f));
        color(e.color, Color.white, e.fout() * 0.6f);

        beginLine();

        Fill.circle(e.x, e.y, getStroke() / 2);
        linePoint(e.x, e.y);

        rand.setSeed(e.id);

        float fin = Mathf.curve(e.fin(), 0, lightningAlign);
        int i;
        float nx = e.x, ny = e.y;
        for (i = 0; i < (int) (links * fin); i++) {
            if (i == links - 1) {
                nx = tx;
                ny = ty;
            } else {
                float len = (i + 1) * spacing;
                Tmp.v1.setToRandomDirection(rand).scl(range / 2f);
                nx = e.x + normx * len + Tmp.v1.x;
                ny = e.y + normy * len + Tmp.v1.y;
            }

            linePoint(nx, ny);
        }

        if (i < links) {
            float f = Mathf.clamp(fin * links % 1);
            float len = (i + 1) * spacing;
            Tmp.v1.setToRandomDirection(rand).scl(range / 2f);
            Tmp.v2.set(nx, ny);
            if (i == links - 1) Tmp.v2.lerp(tx, ty, f);
            else Tmp.v2.lerp(e.x + (normx * len + Tmp.v1.x), e.y + (normy * len + Tmp.v1.y), f);

            linePoint(Tmp.v2.x, Tmp.v2.y);
            Fill.circle(Tmp.v2.x, Tmp.v2.y, getStroke() / 2);
        }

        endLine();
    }).followParent(false),

    fallenStar = new Effect(30f, e -> {
        color(e.color);
        stroke(e.fout() * 2f);
        float rad = 32f * e.fin(Interp.circleOut) * e.rotation;
        Lines.circle(e.x, e.y, rad);

        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, 5f * e.fout(Interp.circleOut) * e.rotation, 48f * e.fout(Interp.circleOut) * e.rotation, i * 90);
        }

        Drawf.light(e.x, e.y, rad * 1.5f, LPPal.aureusMid, e.fout());
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

    moduleFabricatorUpdate = new MultiEffect(
        new ParticleEffect(){{
            particles = 1;
            region = "lp-triangle";
            lifetime = 30f;
            length = 0f;
            baseLength = 0f;
            baseRotation = 0f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow3In;
            spin = Mathf.random(-2, 2);
            sizeFrom = 2.5f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("F7E97E");
            colorTo = Color.valueOf("F7E97E");
        }},

        new ParticleEffect(){{
            particles = 1;
            region = "lp-triangle";
            lifetime = 30f;
            length = 30f;
            baseLength = 2f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow3In;
            spin = Mathf.random(-3, 3);
            sizeFrom = 3f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("F7E97E");
            colorTo = Color.valueOf("F7E97E");
        }}
    ),

    moduleFabricatorCraft = new WaveEffect(){{
        lifetime = 40f;
        colorFrom = Color.valueOf("F7E97E");
        colorTo = Color.valueOf("F7E97E");
        sizeFrom = 12f;
        sizeTo = 24f;
        sides = 4;
        strokeFrom = 2f;
        strokeTo = 0f;
        interp = Interp.pow2Out;
    }},

    moduleFabricatorCraftBig = new WaveEffect(){{
        lifetime = 40f;
        colorFrom = Color.valueOf("F7E97E");
        colorTo = Color.valueOf("F7E97E");
        sizeFrom = 12f;
        sizeTo = 36f;
        sides = 4;
        strokeFrom = 2f;
        strokeTo = 0f;
        interp = Interp.pow3Out;
    }},

    chipAssembleUpdate = new ParticleEffect(){{
        particles = 1;
        region = "lp-triangle";
        lifetime = 30f;
        length = 0f;
        baseLength = 0f;
        baseRotation = 0f;
        interp = Interp.pow3Out;
        sizeInterp = Interp.pow3In;
        sizeFrom = 2.5f;
        sizeTo = 0f;
        colorFrom = Color.valueOf("F7E97E");
        colorTo = Color.valueOf("F7E97E");
    }},

    moduleEnhancerUpdate = new MultiEffect(
        new WaveEffect(){{
            lifetime = 40f;
            colorFrom = LPPal.orangeDark;
            colorTo = LPPal.orangeDark;
            sizeFrom = 0f;
            sizeTo = 16f;
            strokeFrom = 1.2f;
            strokeTo = 0f;
            interp = Interp.pow3Out;
        }},

        new ParticleEffect(){{
            particles = 4;
            region = "lp-triangle";
            lifetime = 30f;
            length = -16f;
            baseLength = 16f;
            baseRotation = 0f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            spin = Mathf.random(-2, 2);
            sizeFrom = 2.5f;
            sizeTo = 0f;
            colorFrom = LPPal.orangeDark;
            colorTo = LPPal.orangeDark;
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
    ),

    cloudpiercerDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 14;
            line = true;
            lifetime = 40f;
            length = 45f;
            baseLength = 2f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 55f;
            lenTo = 0f;
            strokeFrom = 1.5f;
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
            sizeInterp = Interp.pow2In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = Color.valueOf("54545400");
        }},
        new ParticleEffect(){{
            particles = 12;
            lifetime = 45f;
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
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 100f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.orangeDark;
        }}
    ),

    cloundpiercerHitEffect = new MultiEffect(
        LPFx.energyExplosion(Color.valueOf("FF6464"), 30f, 160f, 40),
        LPFx.smoothColorCircle(Color.valueOf("FF6464"), 80f, 40f),

        new ParticleEffect(){{
            particles = 16;
            line = true;
            lifetime = 30f;
            length = 10f;
            baseLength = 2f;
            interp = Interp.pow10Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 80f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = Color.valueOf("FF6464");
            colorTo = Color.valueOf("FF6464");
        }},

        new ParticleEffect(){{
            particles = 12;
            region = "lp-square";
            lifetime = 30f;
            length = 120f;
            baseLength = 8f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            spin = -4f;
            sizeFrom = 6f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("FF6464");
            colorTo = Color.valueOf("FF6464");
        }},

        new ParticleEffect(){{
            particles = 12;
            region = "lp-triangle";
            lifetime = 30f;
            length = 120f;
            baseLength = 8f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            spin = -4f;
            sizeFrom = 6f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("FF6464");
            colorTo = Color.valueOf("FF6464");
        }}
    ),

    fallenstarDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 10;
            line = true;
            length = 60f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 55f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 54f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 14;
            region = "lp-square";
            length = 90f;
            baseLength = 5f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = Color.valueOf("54545400");
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 54f;
            baseLength = 2f;
            lifetime = 45f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 12f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 40f;
            interp = Interp.circleOut;
            sizeFrom = 0f;
            sizeTo = 120f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }}
    ),

    repelbackDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 10;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 55f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 90f;
            baseLength = 5f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = Color.valueOf("54545400");
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 54f;
            baseLength = 2f;
            lifetime = 45f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 12f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 120f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }}
    ),

    repelbackHit = new MultiEffect(
        LPFx.energyExplosion(LPPal.aureus, 20f, 64f, 36, 3f, 5f),
        LPFx.smoothColorCircle(LPPal.aureus, 64f, 16f, 0.7f),

        new ParticleEffect(){{
            particles = 8;
            line = true;
            lifetime = 16f;
            length = 60f;
            baseLength = 4f;
            interp = Interp.circleOut;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureusDark;
        }},
        
        new ParticleEffect(){{
            particles = 6;
            line = true;
            lifetime = 16f;
            length = 72f;
            baseLength = 4f;
            interp = Interp.circleOut;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 6;
            line = true;
            lifetime = 16f;
            length = 62f;
            baseLength = 4f;
            interp = Interp.circleOut;
            sizeInterp = Interp.pow2In;
            lenFrom = 16f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},
        
        new ParticleEffect(){{
            particles = 8;
            line = true;
            lifetime = 16f;
            length = 64f;
            baseLength = 4f;
            interp = Interp.circleOut;
            sizeInterp = Interp.pow2In;
            lenFrom = 12f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }},

        new ParticleEffect(){{
            particles = 7;
            region = "lp-triangle";
            lifetime = 16f;
            length = 54f;
            baseLength = 8f;
            interp = Interp.circleOut;
            sizeInterp = Interp.pow3In;
            sizeFrom = 6f;
            sizeTo = 0f;
            colorFrom = LPPal.aureus;
            colorTo = LPPal.aureus;
        }}
    ),

    hushstrikeDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 10;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 55f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 12;
            length = 90f;
            baseLength = 5f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = Color.valueOf("54545400");
        }},

        new ParticleEffect(){{
            particles = 12;
            length = 54f;
            baseLength = 2f;
            lifetime = 45f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 12f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("47474700");
        }},

        new WaveEffect(){{
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 120f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }}
    ),

    crimsondwarfDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 10;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 55f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 12;
            length = 90f;
            baseLength = 5f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = Color.valueOf("54545400");
        }},

        new ParticleEffect(){{
            particles = 12;
            length = 54f;
            baseLength = 2f;
            lifetime = 45f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 12f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("47474700");
        }},

        new WaveEffect(){{
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 120f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }}
    ),

    crimsondwarfShoot = new MultiEffect(
        new ParticleEffect(){{
            particles = 18;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 60f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow3In;
            lenFrom = 16f;
            lenTo = 0f;
            strokeFrom = 1.5f;
            strokeTo = 0f;
            colorFrom = Color.valueOf("FF6464");
            colorTo = LPPal.redDark;
        }},

        new WaveEffect(){{
            lifetime = 60f;
            interp = Interp.circleOut;
            sizeFrom = 0f;
            sizeTo = 48f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = Color.valueOf("FF6464");
            colorTo = LPPal.redDark;
        }},

        new WaveEffect(){{
            lifetime = 60f;
            interp = Interp.circleOut;
            sizeFrom = 0f;
            sizeTo = 60f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = Color.valueOf("FF6464");
            colorTo = LPPal.redDark;
        }}
    ),

    infernobladeDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 10;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 55f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},

        new ParticleEffect(){{
            particles = 12;
            length = 90f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = Color.valueOf("54545400");
        }},

        new ParticleEffect(){{
            particles = 12;
            length = 54;
            baseLength = 2f;
            lifetime = 45f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 12f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("54545400");
        }},

        new WaveEffect(){{
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 120f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }}
    ),

    recursionDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 10;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 55f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 48f;
            baseLength = 2f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 90f;
            baseLength = 5f;
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 5f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = Color.valueOf("54545400");
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 54f;
            baseLength = 2f;
            lifetime = 45f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 12f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 40f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 120f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orangeDark;
        }}
    ),

    eclipsionDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 64f;
            baseLength = 2f;
            lifetime = 60f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 32f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.redMid;
        }},
        new ParticleEffect(){{
            particles = 14;
            line = true;
            length = 64f;
            baseLength = 2f;
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 24f;
            lenTo = 0f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.redMid;
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 90f;
            baseLength = 5f;
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 7f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("545454");
            colorTo = Color.valueOf("54545400");
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 64f;
            baseLength = 2f;
            lifetime = 60f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 12f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 120f;
            strokeFrom = 2f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.redMid;
        }}
    ),

    defenceDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 54f;
            baseLength = 1.7f;
            lifetime = 60f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 27f;
            lenTo = 0f;
            strokeFrom = 1.7f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.aureusDark;
        }},
        new ParticleEffect(){{
            particles = 14;
            line = true;
            length = 54f;
            baseLength = 1.7f;
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 20f;
            lenTo = 0f;
            strokeFrom = 1.7f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.aureusDark;
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 76f;
            baseLength = 4.2f;
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 6f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("545454");
            colorTo = Color.valueOf("54545400");
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 54f;
            baseLength = 1.7f;
            lifetime = 60f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 10f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 112f;
            strokeFrom = 1.7f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.aureusDark;
        }}
    ),

    thresholdDestroy = new MultiEffect(
        new ParticleEffect(){{
            particles = 12;
            line = true;
            length = 54f;
            baseLength = 1.7f;
            lifetime = 60f;
            interp = Interp.pow5Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 27f;
            lenTo = 0f;
            strokeFrom = 1.7f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }},
        new ParticleEffect(){{
            particles = 14;
            line = true;
            length = 54f;
            baseLength = 1.7f;
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            lenFrom = 20f;
            lenTo = 0f;
            strokeFrom = 1.7f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 76f;
            baseLength = 4.2f;
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 6f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("545454");
            colorTo = Color.valueOf("54545400");
        }},
        new ParticleEffect(){{
            particles = 12;
            length = 54f;
            baseLength = 1.7f;
            lifetime = 60f;
            interp = Interp.pow4Out;
            sizeInterp = Interp.pow5In;
            sizeFrom = 10f;
            sizeTo = 0f;
            colorFrom = Color.valueOf("454545");
            colorTo = Color.valueOf("47474700");
        }},
        new WaveEffect(){{
            lifetime = 60f;
            interp = Interp.pow3Out;
            sizeFrom = 0f;
            sizeTo = 112f;
            strokeFrom = 1.7f;
            strokeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }},
        new ParticleEffect(){{
            particles = 8;
            line = false;
            length = 30f;
            baseLength = 0f;
            lifetime = 40f;
            interp = Interp.pow2Out;
            sizeInterp = Interp.pow2In;
            sizeFrom = 4f;
            sizeTo = 0f;
            colorFrom = LPPal.orange;
            colorTo = LPPal.orange;
        }}
    );
}
