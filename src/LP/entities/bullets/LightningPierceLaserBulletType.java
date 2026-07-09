package LP.entities.bullets;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.world.blocks.defense.ShieldWall;

import static mindustry.Vars.tilesize;

import LP.content.LPFx;
import LP.graphics.LPPal;
import LP.util.struct.Vec2Seq;

public class LightningPierceLaserBulletType extends LaserBulletType{
    /** 闪电伤害 */
    public float lightningDamage = 20f;
    /** 闪电是否与激光同步延申 */
    public boolean lightningSync = true;
    /** 随机闪电生成范围宽度(长度依激光长度) */
    public float lightningRandWidth = width * 2.25f;
    /** 随机闪电生成范围间距 */
    public float lightningRandSpacing = tilesize;

    /** 闪电颜色 */
    public Color lightningColor = LPPal.redMid;
    /** 闪电线条宽度 */
    public float lightningWidth = 2.5f;
    /** 命中点半径 */
    public float hitRadius = 8f;

    private static final Rand rand = new Rand();

    public LightningPierceLaserBulletType(float damage){
        this.damage = damage;
        this.pierce = pierceBuilding = true;
        this.speed = 0f;
        ammoMultiplier = 1f;
        colors = new Color[]{LPPal.redMidDark.cpy().a(0.5f), LPPal.orangeRed.cpy().a(0.7f), lightningColor};
        length = 65f * tilesize;
        width = 40f;
        lifetime = 40f;
        keepVelocity = false;
        collides = false;
        hittable = false;
        absorbable = false;
        removeAfterPierce = false;
        delayFrags = true;
        sideLength = 80f;
        sideAngle = 125f;
    }

    public LightningPierceLaserBulletType(){
        this.speed = 0f;
        ammoMultiplier = 1f;
        keepVelocity = false;
        collides = false;
        hittable = false;
        absorbable = false;
        removeAfterPierce = false;
        delayFrags = true;
    };

    @Override
    public void init(Bullet b){
        super.init(b);

        float resultLength = b.fdata;
        float rot = b.rotation();

        if(resultLength <= 0) return;

        // 计算最大随机命中点数: resultLength / lightningRandSpacing
        int maxPoints = lightningRandSpacing > 0 ? (int)(resultLength / lightningRandSpacing) : 60;
        maxPoints = Mathf.clamp(maxPoints, 5, 120);
        int numPoints = Mathf.random(5, maxPoints);

        // 在矩形范围内生成随机命中点 (长度=resultLength, 宽度=lightningRandWidth)
        Seq<Vec2> points = new Seq<>(numPoints);
        for(int i = 0; i < numPoints; i++){
            float dist = Mathf.random(resultLength);
            float offset = Mathf.range(lightningRandWidth / 2f);
            float px = b.x + Angles.trnsx(rot, dist) + Angles.trnsx(rot + 90f, offset);
            float py = b.y + Angles.trnsy(rot, dist) + Angles.trnsy(rot + 90f, offset);
            points.add(new Vec2(px, py));
        }

        // 按距离排序, 使闪电链从近到远延伸
        points.sort((a, c) -> Float.compare(b.dst(a), b.dst(c)));

        // 在每个命中点造成伤害
        float armorMult = armorMultiplier;
        float shieldMult = shieldDamageMultiplier;
        float buildingMult = buildingDamageMultiplier;
        for(Vec2 p : points){
            Units.nearbyEnemies(b.team, p.x, p.y, hitRadius, unit -> {
                if(unit.shield > 0.0001f){
                    // 有护盾: 先用 shieldDamageMultiplier 消耗护盾, 不越过护盾
                    float shieldDmg = lightningDamage * shieldMult;
                    float shieldTaken = Math.min(unit.shield, shieldDmg);
                    unit.shield -= shieldTaken;
                    // 护盾破裂后的剩余伤害才作用于生命值
                    float remaining = lightningDamage - shieldTaken;
                    if(remaining > 0){
                        if(pierceArmor){
                            unit.damagePierce(remaining);
                        }else if(armorMult != 1f){
                            unit.damageArmorMult(remaining, armorMult);
                        }else{
                            unit.damage(remaining);
                        }
                    }
                }else{
                    // 无护盾: 正常伤害
                    if(pierceArmor){
                        unit.damagePierce(lightningDamage);
                    }else if(armorMult != 1f){
                        unit.damageArmorMult(lightningDamage, armorMult);
                    }else{
                        unit.damage(lightningDamage);
                    }
                }
                if(status != null){
                    unit.apply(status, statusDuration);
                }
            });
            Units.nearbyBuildings(p.x, p.y, hitRadius, building -> {
                if(building.team != b.team && building.isValid()){
                    if(building instanceof ShieldWall.ShieldWallBuild shieldBuild && !shieldBuild.broken() && shieldBuild.shield > 0.0001f){
                        // 护盾墙: 先用 shieldDamageMultiplier 消耗护盾
                        float shieldDmg = lightningDamage * shieldMult;
                        float shieldTaken = Math.min(shieldBuild.shield, shieldDmg);
                        shieldBuild.shield -= shieldTaken;
                        if(shieldBuild.shield <= 0.00001f && shieldTaken > 0){
                            shieldBuild.breakTimer = ((ShieldWall)shieldBuild.block).breakCooldown;
                        }
                        // 护盾破裂后的剩余伤害才作用于建筑
                        float remaining = lightningDamage * buildingMult - shieldTaken;
                        if(remaining > 0){
                            building.damage(remaining);
                        }
                    }else{
                        building.damage(lightningDamage * buildingMult);
                    }
                }
            });
        }

        if(lightningSync){
            // 预计算弯折点偏移量，避免绘制时抖动
            rand.setSeed(b.id);
            Seq<float[]> lightningData = new Seq<>(points.size);
            for(int i = 0; i < points.size; i++){
                float angle = Mathf.random(-90f, 90f);
                float offset = rand.range(lightningRandWidth * 0.3f);
                lightningData.add(new float[]{angle, offset});
            }
            // 存储命中点和弯折数据供 draw() 使用
            b.data = new Object[]{points, lightningData};
        }else{
            // 使用 posLightning 特效
            Vec2Seq vec2Seq = new Vec2Seq(points.size + 1);
            vec2Seq.add(b.x, b.y);
            for(Vec2 p : points){
                vec2Seq.add(p.x, p.y);
            }
            LPFx.posLightning.at(b.x, b.y, lightningWidth, lightningColor, vec2Seq);
        }
    }

    @Override
    public void draw(Bullet b){
        super.draw(b);

        if(lightningSync && b.data instanceof Object[]){
            Object[] data = (Object[])b.data;
            @SuppressWarnings("unchecked")
            Seq<Vec2> points = (Seq<Vec2>)data[0];
            @SuppressWarnings("unchecked")
            Seq<float[]> lightningData = (Seq<float[]>)data[1];
            if(points.isEmpty()) return;

            // 同步进度: 与父类 baseLen 的延申速度一致
            float f = Mathf.curve(b.fin(), 0f, 0.2f);
            if(f <= 0f) return;

            // 计算总路径长度
            float totalLen = 0f;
            Tmp.v1.set(b.x, b.y);
            for(Vec2 p : points){
                totalLen += Tmp.v1.dst(p);
                Tmp.v1.set(p);
            }
            float currentLen = totalLen * f;

            Lines.stroke(lightningWidth * b.fout());
            Draw.color(lightningColor, Color.white, b.fout() * 0.6f);

            Lines.beginLine();
            Lines.linePoint(b.x, b.y);
            Fill.circle(b.x, b.y, Lines.getStroke() / 2f);

            float accumulated = 0f;
            Tmp.v1.set(b.x, b.y);

            for(int i = 0; i < points.size; i++){
                Vec2 p = points.get(i);
                float segDst = Tmp.v1.dst(p);
                if(accumulated + segDst <= currentLen){
                    // 使用预计算的弯折数据
                    float[] bendData = lightningData.get(i);
                    float bendAngle = bendData[0];  // -90度到+90度的转向
                    float offset = bendData[1];

                    // 计算当前段的方向向量（使用实际的 p 点位置）
                    float dx = p.x - Tmp.v1.x;
                    float dy = p.y - Tmp.v1.y;
                    float len = Math.max(segDst, 0.001f);
                    float nx = dx / len;  // 归一化方向x
                    float ny = dy / len;  // 归一化方向y

                    // 垂直于当前方向的向量
                    float perpX = -ny;
                    float perpY = nx;

                    // 在段中间位置添加弯折（支持90度左右转向）
                    float t = 0.5f;
                    float midX = Tmp.v1.x + dx * t;
                    float midY = Tmp.v1.y + dy * t;

                    // 根据弯折角度计算偏移方向（基于当前段的垂直方向）
                    // bendAngle = -90 表示向左转，+90 表示向右转
                    float bendRad = Mathf.degRad * bendAngle;
                    float cos = Mathf.cos(bendRad);
                    float sin = Mathf.sin(bendRad);
                    // 将偏移从垂直方向旋转 bendAngle 角度
                    float bendX = perpX * cos - nx * sin;
                    float bendY = perpY * cos - ny * sin;

                    Lines.linePoint(midX + bendX * offset, midY + bendY * offset);
                    Lines.linePoint(p.x, p.y);
                    Fill.circle(p.x, p.y, Lines.getStroke() / 2f);

                    accumulated += segDst;
                    Tmp.v1.set(p);
                }else{
                    // 部分段: 绘制到当前进度位置
                    float remaining = currentLen - accumulated;
                    float t = segDst > 0 ? remaining / segDst : 0f;
                    Tmp.v2.set(Tmp.v1).lerp(p, t);
                    Lines.linePoint(Tmp.v2.x, Tmp.v2.y);
                    Fill.circle(Tmp.v2.x, Tmp.v2.y, Lines.getStroke() / 2f);
                    break;
                }
            }

            Lines.endLine();
            Draw.reset();
        }
    }
}
