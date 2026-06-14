package LP.entities.bullets;

import arc.audio.Sound;
import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;

import LP.graphics.PositionLightning;

public class LightningLinkBulletType extends BasicBulletType {
    public static final Vec2 randVec = new Vec2();
    public float lightningLinkDamage = 12f;
    public float linkRange = 240f;
    public float hitSpacing = 10f;
    public int maxHit = 8;
    public float size = 8f;
    public float boltWidth = PositionLightning.WIDTH;
    public float randomGenerateRange = -1f;
    public float randomGenerateChance = 0.03f;
    public float randomLightningChance = 0.1f;
    public int randomLightningNum = 4;
    public Sound randomGenerateSound = Sounds.none;
    public Cons<Position> hitModifier = p -> {
    };
    public float range = -1;
    public int boltNum = 1;
    public int effectLingtning = 2;
    public float effectLightningChance = 0f;
    public float effectLightningLength = -1;
    public float effectLightningLengthRand = -1;
    public float trueHitChance = 0.7f;
    public Effect slopeEffect = Fx.none, liHitEffect = Fx.none, spreadEffect = Fx.none;

    public LightningLinkBulletType() {
    }

    @Override
    public boolean testCollision(Bullet bullet, Building tile) {
        return super.testCollision(bullet, tile);
    }

    @Override
    public float estimateDPS() {
        return lightningLinkDamage * maxHit * 0.75f * 60 / hitSpacing;
    }

    @Override
    public void init() {
        super.init();

        if (trailWidth < 0) trailWidth = size * 0.75f;
        if (trailLength < 0) trailLength = 12;

        drawSize = Math.max(drawSize, size * 2f);

        if (effectLightningLength < 0) effectLightningLength = size * 1.5f;
        if (effectLightningLengthRand < 0) effectLightningLengthRand = size * 2f;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        Effect.shake(hitShake, hitShake, b);
        if (b.timer(4, hitSpacing)) {
            PositionLightning.setHitChance(trueHitChance);
            PositionLightning.createRangeForLink(b, collidesAir, collidesGround, b, b.team, linkRange, maxHit, hitColor, Mathf.chanceDelta(randomLightningChance), lightningLinkDamage, lightningLength, PositionLightning.WIDTH, boltNum, p -> {
                liHitEffect.at(p.getX(), p.getY(), hitColor);
            });
            PositionLightning.setHitChanceDef();
        }

        if (randomGenerateRange > 0f && Mathf.chance(Time.delta * randomGenerateChance) && b.lifetime - b.time > PositionLightning.lifetime)
            PositionLightning.createRandomRange(b, b.team, b, randomGenerateRange, hitColor, Mathf.chanceDelta(randomLightningChance), 0, 0, boltWidth, boltNum, randomLightningNum, hitPos -> {
                randomGenerateSound.at(hitPos, Mathf.random(0.9f, 1.1f));
                Damage.damage(b.team, hitPos.getX(), hitPos.getY(), splashDamageRadius / 8, splashDamage * b.damageMultiplier() / 8, collidesAir, collidesGround);

                hitModifier.get(hitPos);
            });

        if (Mathf.chanceDelta(effectLightningChance) && b.lifetime - b.time > Fx.chainLightning.lifetime) {
            for (int i = 0; i < effectLingtning; i++) {
                Vec2 v = randVec.rnd(effectLightningLength + Mathf.random(effectLightningLengthRand)).add(b).add(Tmp.v1.set(b.vel).scl(Fx.chainLightning.lifetime / 2));
                Fx.chainLightning.at(b.x, b.y, 12f, hitColor, v.cpy());
            }
        }
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
    }

    @Override
    public void despawned(Bullet b) {
        PositionLightning.createRandomRange(b, b.team, b, randomGenerateRange, hitColor, Mathf.chanceDelta(randomLightningChance), 0, 0, boltWidth, boltNum, randomLightningNum, hitPos -> {
            Damage.damage(b.team, hitPos.getX(), hitPos.getY(), splashDamageRadius, splashDamage * b.damageMultiplier(), collidesAir, collidesGround);
            liHitEffect.at(hitPos);
            for (int j = 0; j < lightning; j++) {
                Lightning.create(b, hitColor, lightningLinkDamage < 0.0F ? damage : lightningLinkDamage, b.x, b.y, b.rotation() + Mathf.range(lightningCone / 2.0F) + lightningAngle, lightningLength + Mathf.random(lightningLengthRand));
            }
            hitSound.at(hitPos, Mathf.random(0.9f, 1.1f));

            hitModifier.get(hitPos);
        });

        super.despawned(b);
    }
}