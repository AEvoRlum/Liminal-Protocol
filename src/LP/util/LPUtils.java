package LP.util;

import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Intersector;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.ai.types.MissileAI;
import mindustry.core.World;
import mindustry.entities.Mover;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.gen.Healthc;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.gen.Velc;
import mindustry.world.blocks.ControlBlock;

import static mindustry.Vars.*;

public final class LPUtils{
    public static final Rand rand = new Rand(0);

    private static final Vec2 v1 = new Vec2();
    private static final Rect rect = new Rect(), hitRect = new Rect();
    private static final FloatSeq distances = new FloatSeq();
    private static float maxDst = 0f;

    private LPUtils(){
    }

    public static Rand rand(long id){
        rand.setSeed(id);
        return rand;
    }

    public static <T> void shuffle(Seq<T> seq, Rand rand){
        T[] items = seq.items;
        for(int i = seq.size - 1; i >= 0; i--){
            int ii = Mathf.random(i);
            T temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
    }

    public static Bullet anyOtherCreate(Bullet bullet, BulletType bt, Entityc shooter, Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, Mover mover, float aimX, float aimY, @Nullable Teamc target){
        if(bt == null) return null;
        angle += bt.angleOffset + Mathf.range(bt.randomAngleOffset);

        if(!Mathf.chance(bt.createChance)) return null;
        if(bt.ignoreSpawnAngle) angle = 0;
        if(bt.spawnUnit != null){
            if(!net.client()){
                Unit spawned = bt.spawnUnit.create(team);
                spawned.set(x, y);
                spawned.rotation = angle;
                if(bt.spawnUnit.missileAccelTime <= 0f){
                    spawned.vel.trns(angle, bt.spawnUnit.speed);
                }
                if(spawned.controller() instanceof MissileAI ai){
                    if(shooter instanceof Unit unit){
                        ai.shooter = unit;
                    }

                    if(shooter instanceof ControlBlock control){
                        ai.shooter = control.unit();
                    }

                }
                spawned.add();
                Units.notifyUnitSpawn(spawned);
            }
            if(bt.killShooter && owner instanceof Healthc h && !h.dead()) h.kill();

            return null;
        }

        bullet.type = bt;
        bullet.owner = owner;
        bullet.shooter = (shooter == null ? owner : shooter);
        bullet.team = team;
        bullet.time = 0f;
        bullet.originX = x;
        bullet.originY = y;
        if(!(aimX == -1f && aimY == -1f)){
            bullet.aimTile = target instanceof Building b ? b.tile : world.tileWorld(aimX, aimY);
        }
        bullet.aimX = aimX;
        bullet.aimY = aimY;

        bullet.initVel(angle, bt.speed * velocityScl * (bt.velocityScaleRandMin != 1f || bt.velocityScaleRandMax != 1f ? Mathf.random(bt.velocityScaleRandMin, bt.velocityScaleRandMax) : 1f));
        bullet.set(x, y);
        bullet.lastX = x;
        bullet.lastY = y;
        bullet.lifetime = bt.lifetime * lifetimeScl * (bt.lifeScaleRandMin != 1f || bt.lifeScaleRandMax != 1f ? Mathf.random(bt.lifeScaleRandMin, bt.lifeScaleRandMax) : 1f);
        bullet.data = data;
        bullet.hitSize = bt.hitSize;
        bullet.mover = mover;
        bullet.damage = (damage < 0 ? bt.damage : damage) * bullet.damageMultiplier();
        bullet.buildingDamageMultiplier = bt.buildingDamageMultiplier;
        if(bullet.trail != null){
            bullet.trail.clear();
        }
        bullet.add();

        if(bt.keepVelocity && owner instanceof Velc v) bullet.vel.add(v.vel());
        return bullet;
    }

    public static float findLaserPierceLength2(Bullet b, int pierceCap, boolean laser, float length, float angle){
        v1.trnsExact(angle, length);
        rect.setPosition(b.x, b.y).setSize(v1.x, v1.y).normalize().grow(3f);
        maxDst = Float.POSITIVE_INFINITY;

        distances.clear();

        if(b.type.collidesGround && b.type.collidesTiles){
            World.raycast(b.tileX(), b.tileY(), World.toTile(b.x + v1.x), World.toTile(b.y + v1.y), (x, y) -> {
                var build = world.build(x, y);

                if(build != null && build.team != b.team && build.collide(b) && b.checkUnderBuild(build, x * tilesize, y * tilesize)){
                    float dst2 = b.dst(build);
                    distances.add(dst2);

                    if(laser && build.absorbLasers()){
                        maxDst = dst2;
                        return true;
                    }
                }
                return false;
            });
        }

        Units.nearbyEnemies(b.team, rect, u -> {
            u.hitbox(hitRect);

            if(u.checkTarget(b.type.collidesAir, b.type.collidesGround) && u.hittable() &&
            Intersector.intersectSegmentRectangle(b.x, b.y, b.x + v1.x, b.y + v1.y, hitRect)){
                Tmp.v2.trns(b.rotation(), length * 1.5f).add(b);

                distances.add(b.dst(u));
            }
        });


        distances.sort();

        return Math.min(distances.size < pierceCap || pierceCap < 0 ? length : Math.max(6f, distances.get(pierceCap - 1)), maxDst);
    }
}