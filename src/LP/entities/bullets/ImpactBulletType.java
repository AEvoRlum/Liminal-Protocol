package LP.entities.bullets;

import arc.math.Rand;
import arc.util.Tmp;
import arc.graphics.Color;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.entities.Effect;
import mindustry.content.Fx;

import LP.graphics.LPPal;

public class ImpactBulletType extends BasicBulletType {
    public float maxDamage = 72f;
    public float maxKnockback = 12f;
    public float mag = 2f;
    public float scale = 2f;

    public float subBulletWidth = 6f;
    public float subBulletHeight = 9f;
    public float subTrailWidth = 2f;
    public int subTrailLength = 12;
    public Effect subHitEffect = Fx.none;
    public Color subColor = LPPal.aureus;
    public String subSprite = "lp-pointy-bullet";
    public Effect subDespawnEffect = Fx.none;
    public float subKnockbackScale = 0.1f;

    protected BasicBulletType subBulletType1;
    protected BasicBulletType subBulletType2;

    public ImpactBulletType(float maxDamage, float maxKnockback, float speed, float lifetime, float mag, float scale){
        super(speed, maxDamage);
        weaveRandom = false;
        impact = true;
        knockback = maxKnockback;
        this.lifetime = lifetime;

        subBulletType1 = createSubBulletType(speed, maxDamage, ImpactBulletType.this.lifetime, mag, scale, maxKnockback);

        subBulletType2 = createSubBulletType(speed, maxDamage, ImpactBulletType.this.lifetime, -mag, scale, maxKnockback);
    }

    protected BasicBulletType createSubBulletType(float speed, float damage, float lifetime, float mag, float scale, float knockback){
        return new BasicBulletType(ImpactBulletType.this.speed, damage * 0f, subSprite){{
            angleOffset = 0f;
            weaveRandom = false;
            lifetime = ImpactBulletType.this.lifetime;
            this.weaveMag = mag;
            this.weaveScale = scale;
            despawnEffect = subDespawnEffect;
            
            if(subColor != null){
                hitColor = lightColor = frontColor = backColor = trailColor = subColor;
            }else{
                hitColor = lightColor = frontColor = backColor = trailColor = ImpactBulletType.this.hitColor;
            }
            
            hitEffect = subHitEffect;
            hitSound = ImpactBulletType.this.hitSound;
            hitSoundVolume = ImpactBulletType.this.hitSoundVolume;
            hitShake = ImpactBulletType.this.hitShake;
            width = subBulletWidth;
            height = subBulletHeight;
            trailWidth = subTrailWidth;
            if(subTrailLength > 0){
                trailLength = subTrailLength;
            }
            
            this.knockback = knockback * subKnockbackScale;
        }};
    }

    public void setSubHitEffect(Effect effect){
        this.subHitEffect = effect;
        if(subBulletType1 != null){
            subBulletType1.hitEffect = effect;
        }
        if(subBulletType2 != null){
            subBulletType2.hitEffect = effect;
        }
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        Rand rand1 = new Rand(b.id);
        Rand rand2 = new Rand(b.id + 1);

        Bullet b1 = subBulletType1.create(b, b.x, b.y, b.rotation());
        if(b1 != null){
            b1.vel.set(Tmp.v1.set(1, 0).setToRandomDirection(rand1)).add(b.vel.x, b.vel.y);
        }

        Bullet b2 = subBulletType2.create(b, b.x, b.y, b.rotation());
        if(b2 != null){
            b2.vel.set(Tmp.v1.set(1, 0).setToRandomDirection(rand2)).add(b.vel.x, b.vel.y);
        }
    }

    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b, x, y);
    }

    @Override
    public void hit(Bullet b){
        super.hit(b);
    }

}
