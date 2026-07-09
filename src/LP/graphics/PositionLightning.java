package LP.graphics;
import arc.Events;
import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;
import LP.content.*;
import LP.entities.bullets.*;
import LP.util.struct.*;

import static mindustry.Vars.*;

public final class PositionLightning{
    public static final BulletType hitter = new EffectBulletType(5.0F){
        {
            this.absorbable = true;
            this.collides = this.collidesAir = this.collidesGround = this.collidesTiles = true;
            this.status = StatusEffects.none;
            this.statusDuration = 10.0f;
            this.hittable = false;
        }
    };
    public static final Cons<Position> none = (p) -> {
    };
    public static float lifetime;
    public static final float WIDTH = 2.5F;
    public static final float RANGE_RAND = 5.0F;
    public static final float ROT_DST = 4.8F;
    public static float trueHitChance;
    private static Building furthest;
    private static final Rect rect;
    private static final Rand rand;
    private static final FloatSeq floatSeq;
    private static final Vec2 tmp1;
    private static final Vec2 tmp2;
    private static final Vec2 tmp3;
    private static final Seq<Healthc> entityBuffer = new Seq<>();

    private PositionLightning(){
    }

    public static void setHitChance(float f){
        trueHitChance = f;
    }

    public static void setHitChanceDef(){
        trueHitChance = 1.0F;
    }

    private static float getBoltRandomRange(){
        return Mathf.random(1.0F, 7.0F);
    }

    public static void createRange(Bullet owner, float range, int maxHit, Color color, boolean createSubLightning, float width, int lightningNum, Cons<Position> hitPointMovement){
        createRange(owner, owner, owner.team, range, maxHit, color, createSubLightning, 0, 0, width, lightningNum, hitPointMovement);
    }

    public static void createRange(Bullet owner, Position from, Team team, float range, int maxHit, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement){
        createRange(owner, owner == null || owner.type.collidesAir, owner == null || owner.type.collidesGround, from, team, range, maxHit, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
    }

    public static void createRange(Bullet owner, boolean hitAir, boolean hitGround, Position from, Team team, float range, int maxHit, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement){
        entityBuffer.clear();
        whetherAdd(entityBuffer, team, rect.setSize(range * 2f).setCenter(from.getX(), from.getY()), maxHit, hitGround, hitAir);
        float armorMultiplier = owner != null ? owner.type.armorMultiplier : 1f;
        float shieldDamageMultiplier = owner != null ? owner.type.shieldDamageMultiplier : 1f;
        float buildingDamageMultiplier = owner != null ? owner.type.buildingDamageMultiplier : 1f;
        for(Healthc p : entityBuffer)
            create(owner, team, from, p, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement, armorMultiplier, shieldDamageMultiplier, buildingDamageMultiplier);
    }

    public static void createLength(Bullet owner, Team team, Position from, float length, float angle, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement){
        create(owner, team, from, tmp2.trns(angle, length).add(from), color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement, 
            owner != null ? owner.type.armorMultiplier : 1f, 
            owner != null ? owner.type.shieldDamageMultiplier : 1f, 
            owner != null ? owner.type.buildingDamageMultiplier : 1f);
    }

    public static void create(Entityc owner, Team team, Position from, Position target, Color color, boolean createSubLightning,
                              float damage, int subLightningLength, float lightningWidth, int lightningNum, Cons<Position> hitPointMovement,
                              float armorMultiplier, float shieldDamageMultiplier, float buildingDamageMultiplier){
        if(!Mathf.chance(trueHitChance)) return;
        Position sureTarget = findInterceptedPoint(from, target, team);
        hitPointMovement.get(sureTarget);

        if(createSubLightning){
            if(owner instanceof Bullet b){
                for(int i = 0; i < b.type.lightning; i++)
                    mindustry.entities.Lightning.create(b, color, b.type.lightningDamage < 0f ? b.damage : b.type.lightningDamage, sureTarget.getX(), sureTarget.getY(), b.rotation() + Mathf.range(b.type.lightningCone / 2f) + b.type.lightningAngle, b.type.lightningLength + Mathf.random(b.type.lightningLengthRand));
            }else for(int i = 0; i < 3; i++)
                mindustry.entities.Lightning.create(team, color, damage <= 0f ? 1f : damage, sureTarget.getX(), sureTarget.getY(), Mathf.random(360f), subLightningLength);
        }

        float realDamage = damage;

        if(realDamage <= 0){
            if(owner instanceof Bullet b){
                realDamage = b.damage > 0 ? b.damage : 1;
            }else realDamage = 1;
        }

        StatusEffect status = owner instanceof Bullet b ? b.type.status : StatusEffects.none;
        float statusDuration = owner instanceof Bullet b ? b.type.statusDuration : 0f;

        if(sureTarget instanceof Healthc healthc){
            float buildingDamage = realDamage * buildingDamageMultiplier;

            if(healthc instanceof Unit unit){
                float finalDamage = owner instanceof Bullet b ? b.damage : realDamage;
                
                if(owner instanceof Bullet b){
                    if(b.type.pierceArmor){
                        unit.damagePierce(finalDamage);
                    } else if(b.type.armorMultiplier != 1){
                        unit.damageArmorMult(finalDamage, b.type.armorMultiplier);
                    } else {
                        unit.damage(finalDamage);
                    }
                    
                    unit.apply(b.type.status, b.type.statusDuration);
                    Events.fire(new mindustry.game.EventType.UnitDamageEvent().set(unit, b));
                } else {
                    if(armorMultiplier != 1){
                        unit.damageArmorMult(finalDamage, armorMultiplier);
                    } else {
                        unit.damage(finalDamage);
                    }
                    
                    if(status != StatusEffects.none){
                        unit.apply(status, statusDuration);
                    }
                }
            } else if(healthc instanceof Building building){
                float finalDamage = owner instanceof Bullet b ? b.damage * b.type.buildingDamageMultiplier : buildingDamage;
                building.damage(finalDamage);
                
                if(status != StatusEffects.none && building instanceof Unitc unitc){
                    unitc.apply(status, statusDuration);
                }
            }
        } else {
            hitter.armorMultiplier = armorMultiplier;
            hitter.shieldDamageMultiplier = shieldDamageMultiplier;
            hitter.buildingDamageMultiplier = buildingDamageMultiplier;
            hitter.status = owner instanceof Bullet b ? b.type.status : StatusEffects.none;
            hitter.statusDuration = owner instanceof Bullet b ? b.type.statusDuration : 0f;
            hitter.create(owner, team, sureTarget.getX(), sureTarget.getY(), 1).damage(realDamage);
        }

        createEffect(from, sureTarget, color, lightningNum, lightningWidth);
    }

    public static void createForLink(Entityc owner, Team team, Position from, Position target, Color color, boolean createSubLightning,
                              float damage, int subLightningLength, float lightningWidth, int lightningNum, Cons<Position> hitPointMovement,
                              float armorMultiplier, float shieldDamageMultiplier, float buildingDamageMultiplier){
        if(!Mathf.chance(trueHitChance)) return;
        Position sureTarget = findInterceptedPoint(from, target, team);
        hitPointMovement.get(sureTarget);

        if(createSubLightning){
            if(owner instanceof Bullet b){
                for(int i = 0; i < b.type.lightning; i++)
                    mindustry.entities.Lightning.create(b, color, b.type.lightningDamage < 0f ? b.damage : b.type.lightningDamage, sureTarget.getX(), sureTarget.getY(), b.rotation() + Mathf.range(b.type.lightningCone / 2f) + b.type.lightningAngle, b.type.lightningLength + Mathf.random(b.type.lightningLengthRand));
            }else for(int i = 0; i < 3; i++)
                mindustry.entities.Lightning.create(team, color, damage <= 0f ? 1f : damage, sureTarget.getX(), sureTarget.getY(), Mathf.random(360f), subLightningLength);
        }

        float realDamage = damage;

        if(realDamage <= 0){
            if(owner instanceof Bullet b){
                realDamage = b.damage > 0 ? b.damage : 1;
            }else realDamage = 1;
        }

        StatusEffect status = owner instanceof Bullet b ? b.type.status : StatusEffects.none;
        float statusDuration = owner instanceof Bullet b ? b.type.statusDuration : 0f;

        if(sureTarget instanceof Healthc healthc){
            float buildingDamage = realDamage * buildingDamageMultiplier;

            if(healthc instanceof Unit unit){
                float finalDamage = realDamage;
                
                if(owner instanceof Bullet b){
                    if(b.type.pierceArmor){
                        unit.damagePierce(finalDamage);
                    } else if(b.type.armorMultiplier != 1){
                        unit.damageArmorMult(finalDamage, b.type.armorMultiplier);
                    } else {
                        unit.damage(finalDamage);
                    }
                    
                    unit.apply(b.type.status, b.type.statusDuration);
                    Events.fire(new mindustry.game.EventType.UnitDamageEvent().set(unit, b));
                } else {
                    if(armorMultiplier != 1){
                        unit.damageArmorMult(finalDamage, armorMultiplier);
                    } else {
                        unit.damage(finalDamage);
                    }
                    
                    if(status != StatusEffects.none){
                        unit.apply(status, statusDuration);
                    }
                }
            } else if(healthc instanceof Building building){
                float finalDamage = owner instanceof Bullet b ? realDamage * b.type.buildingDamageMultiplier : buildingDamage;
                building.damage(finalDamage);
                
                if(status != StatusEffects.none && building instanceof Unitc unitc){
                    unitc.apply(status, statusDuration);
                }
            }
        } else {
            hitter.armorMultiplier = armorMultiplier;
            hitter.shieldDamageMultiplier = shieldDamageMultiplier;
            hitter.buildingDamageMultiplier = buildingDamageMultiplier;
            hitter.status = owner instanceof Bullet b ? b.type.status : StatusEffects.none;
            hitter.statusDuration = owner instanceof Bullet b ? b.type.statusDuration : 0f;
            hitter.create(owner, team, sureTarget.getX(), sureTarget.getY(), 1).damage(realDamage);
        }

        createEffect(from, sureTarget, color, lightningNum, lightningWidth);
    }

    public static void createRangeForLink(Bullet owner, boolean hitAir, boolean hitGround, Position from, Team team, float range, int maxHit, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement){
        entityBuffer.clear();
        whetherAdd(entityBuffer, team, rect.setSize(range * 2f).setCenter(from.getX(), from.getY()), maxHit, hitGround, hitAir);
        float armorMultiplier = owner != null ? owner.type.armorMultiplier : 1f;
        float shieldDamageMultiplier = owner != null ? owner.type.shieldDamageMultiplier : 1f;
        float buildingDamageMultiplier = owner != null ? owner.type.buildingDamageMultiplier : 1f;
        for(Healthc p : entityBuffer)
            createForLink(owner, team, from, p, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement, armorMultiplier, shieldDamageMultiplier, buildingDamageMultiplier);
    }

    public static void createRandom(Bullet owner, Team team, Position from, float rand, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement){
        float armorMultiplier = owner != null ? owner.type.armorMultiplier : 1f;
        float shieldDamageMultiplier = owner != null ? owner.type.shieldDamageMultiplier : 1f;
        float buildingDamageMultiplier = owner != null ? owner.type.buildingDamageMultiplier : 1f;
        create(owner, team, from, tmp2.rnd(rand).scl(Mathf.random(1.0F)).add(from), color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement, armorMultiplier, shieldDamageMultiplier, buildingDamageMultiplier);
    }

    public static void createRandom(Team team, Position from, float rand, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, Cons<Position> hitPointMovement){
        createRandom(null, team, from, rand, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
    }

    public static void createRandomRange(Team team, Position from, float rand, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, int generateNum, Cons<Position> hitPointMovement){
        createRandomRange(null, team, from, rand, color, createSubLightning, damage, subLightningLength, width, lightningNum, generateNum, hitPointMovement);
    }

    public static void createRandomRange(Bullet owner, float rand, Color color, boolean createSubLightning, float damage, float width, int lightningNum, int generateNum, Cons<Position> hitPointMovement){
        createRandomRange(owner, owner.team, owner, rand, color, createSubLightning, damage, owner.type.lightningLength + Mathf.random(owner.type.lightningLengthRand), width, lightningNum, generateNum, hitPointMovement);
    }

    public static void createRandomRange(Bullet owner, Team team, Position from, float rand, Color color, boolean createSubLightning, float damage, int subLightningLength, float width, int lightningNum, int generateNum, Cons<Position> hitPointMovement){
        for(int i = 0; i < generateNum; ++i){
            createRandom(owner, team, from, rand, color, createSubLightning, damage, subLightningLength, width, lightningNum, hitPointMovement);
        }

    }

    public static void createEffect(Position from, float length, float angle, Color color, int lightningNum, float width){
        if(!Vars.headless){
            createEffect(from, tmp2.trns(angle, length).add(from), color, lightningNum, width);
        }
    }

    public static void createEffect(Position from, Position to, Color color, int lightningNum, float width){
        if(headless) return;

        float dst = from.dst(to);
        int maxSegments = Mathf.clamp((int)(dst / (ROT_DST * 3f)) + 1, 5, 60);

        for(int i = 0; i < lightningNum; i++){
            float len = getBoltRandomRange();
            float randRange = len * RANGE_RAND;

            floatSeq.clear();
            int segments = Math.min((int)(dst / (ROT_DST * len)) + 1, maxSegments);
            for(int num = 0; num < segments; num++){
                floatSeq.add(Mathf.range(randRange) / (num * 0.025f + 1));
            }
            createBoltEffect(color, width, computeVectors(floatSeq, from, to));
        }
    }

    private static void whetherAdd(Seq<Healthc> points, Team team, Rect selectRect, int maxHit, boolean targetGround, boolean targetAir) {
        points.clear();

        float cx = selectRect.getX() + selectRect.getWidth() / 2f;
        float cy = selectRect.getY() + selectRect.getHeight() / 2f;
        float range = selectRect.getWidth() / 2f;

        if (targetAir) {
            Units.nearbyEnemies(team, cx, cy, range, unit -> {
                if (unit.isFlying() && unit.checkTarget(targetAir, targetGround)) points.add(unit);
            });
        }

        if (targetGround) {
            Units.nearbyEnemies(team, cx, cy, range, unit -> {
                if (!unit.isFlying() && unit.checkTarget(targetAir, targetGround)) points.add(unit);
            });

            Units.nearbyBuildings(cx, cy, range, b -> {
                if (b.team != team && b.isValid()) points.add(b);
            });
        }

        points.shuffle();
        points.truncate(maxHit);
    }

    public static Position findInterceptedPoint(Position from, Position target, Team fromTeam){
        furthest = null;
        return Geometry.raycast(
        World.toTile(from.getX()),
        World.toTile(from.getY()),
        World.toTile(target.getX()),
        World.toTile(target.getY()),
        (x, y) -> (furthest = world.build(x, y)) != null && furthest.team() != fromTeam && furthest.block.insulated
        ) && furthest != null ? furthest : target;
    }

    public static void createBoltEffect(Color color, float width, Vec2Seq vets) {
        vets.each(((x, y) -> {
            if(Mathf.chance(0.0855f)) LPFx.lightningSpark.at(x, y, rand.random(2f + width, 4f + width), color);
        }));
        LPFx.posLightning.at((vets.firstTmp().x + vets.peekTmp().x) / 2f, (vets.firstTmp().y + vets.peekTmp().y) / 2f, width, color, vets);
    }

    private static Vec2Seq computeVectors(FloatSeq randomVec, Position from, Position to) {
        int param = randomVec.size;
        float angle = from.angleTo(to);

        Vec2Seq lines = new Vec2Seq(param);
        tmp1.trns(angle, from.dst(to) / (param - 1));

        lines.add(from);
        for (int i = 1; i < param - 2; i++)
            lines.add(tmp3.trns(angle - 90, randomVec.get(i)).add(tmp1, i).add(from.getX(), from.getY()));
        lines.add(to);

        return lines;
    }

    static{
        lifetime = Fx.chainLightning.lifetime;
        trueHitChance = 1.0F;
        rect = new Rect();
        rand = new Rand();
        floatSeq = new FloatSeq();
        tmp1 = new Vec2();
        tmp2 = new Vec2();
        tmp3 = new Vec2();
    }
}
