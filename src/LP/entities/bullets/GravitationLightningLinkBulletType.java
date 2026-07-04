package LP.entities.bullets;

import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.content.Fx;
import mindustry.gen.Bullet;

import static mindustry.Vars.tilesize;

import LP.graphics.PositionLightning;
import LP.graphics.LPPal;

public class GravitationLightningLinkBulletType extends BasicBulletType {
    /** 飞行过程中向(子弹)吸力力度(每帧) */
    public float gravitation = 2f;
    /** 飞行过程中向(子弹)吸力范围 */
    public float gravitationRange = 20f * tilesize;
    /** 命中时向(子弹)吸力力度(单次) */
    public float hitGravitation = 20f;
    /** 命中时向(子弹)吸力范围 */
    public float hitGravitationRange = 20f * tilesize * 1.5f;
    /** 相反力(以上全应用) */
    public boolean reverse = false;

    /** 飞行过程中屏震 */
    public float effectShake = 0f;
    /** 伤害间隔 */
    public float hitSpacing = 4f;
    /** 链接范围 */
    public float linkRange = 20f * tilesize;
    /** 最大链接目标数 */
    public int maxHit = 128;
    /** 随机闪电间隔 */
    public float randomSpacing = 0.1f;
    /** 随机闪电长度 */
    public int lightningLength = 12;
    /** 闪电链伤害 */
    public float lightningLinkDamage = 20f;
    /** 闪电链宽度 */
    public float boltWidth = PositionLightning.WIDTH;
    /** 闪电链数量 */
    public int boltNum = 1;
    /** 闪电链命中特效 */
    public Effect liHitEffect = Fx.none;

    public GravitationLightningLinkBulletType(){
        this.ammoMultiplier = 1;
        hitColor = lightColor = frontColor = backColor = trailColor = LPPal.redMid;
        damage = 0;
        armorMultiplier = 1.5f;
        buildingDamageMultiplier = 0.6f;
        shieldDamageMultiplier = 2f;
        trailWidth = 4f;
        trailLength = 24;
    }

    /** 吸力应用实现 
     * @param b 子弹
     * @param x 子弹x坐标
     * @param y 子弹y坐标
     * @param range 范围
     * @param strength 力度
     * @param frameRateIndependent 是否独立于帧率
    */
    private void applyGravitation(Bullet b, float x, float y, float range, float strength, boolean frameRateIndependent){
        Units.nearbyEnemies(b.team, x, y, range, unit -> {
            if(unit.checkTarget(collidesAir, collidesGround)){
                float dx = x - unit.x;
                float dy = y - unit.y;
                float dist = Mathf.sqrt(dx * dx + dy * dy);
                if(dist > 0.001f){
                    float force = strength * 80f * (reverse ? -1f : 1f);
                    if(frameRateIndependent) force *= Time.delta;
                    Tmp.v3.set(dx / dist, dy / dist).scl(force);
                    unit.impulse(Tmp.v3);
                }
            }
        });
    }

    @Override
    public float estimateDPS() {
        return lightningLinkDamage * maxHit * 0.5f * 60 / hitSpacing;
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(effectShake > 0){
            Effect.shake(effectShake, effectShake, b);
        }

        if(gravitation > 0f && gravitationRange > 0f){
            applyGravitation(b, b.x, b.y, gravitationRange, gravitation, true);
        }

        if (b.timer(4, hitSpacing)) {
            PositionLightning.setHitChance(1f);
            PositionLightning.createRangeForLink(b, collidesAir, collidesGround, b, b.team, linkRange, maxHit, hitColor, Mathf.chanceDelta(randomSpacing),
            lightningLinkDamage, lightningLength, boltWidth, boltNum, p -> {
                liHitEffect.at(p.getX(), p.getY(), hitColor);
            });
            PositionLightning.setHitChanceDef();
        }
    }

    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b, x, y);

        if(hitGravitation > 0f && hitGravitationRange > 0f){
            applyGravitation(b, x, y, hitGravitationRange, hitGravitation, false);
            b.data(true);
        }
    }

    @Override
    public void despawned(Bullet b){
        super.despawned(b);

        if(hitGravitation > 0f && hitGravitationRange > 0f && !(b.data() instanceof Boolean && (Boolean)b.data())){
            applyGravitation(b, b.x, b.y, hitGravitationRange, hitGravitation, false);
        }
    }
}