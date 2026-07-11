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
    public float hitRadius = 6f;

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
        sideLength = 50f;
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
    public float estimateDPS(){
        if (pierce){
            float dps = damage * 1.5f + lightningDamage * (length / lightningRandSpacing * 0.1f);
            return dps;
        }
        return damage + lightningDamage * (length / lightningRandSpacing * 0.1f);
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        float resultLength = b.fdata;
        float rot = b.rotation();

        if(resultLength <= 0) return;

        // 计算最大随机命中点数: resultLength / lightningRandSpacing
        int maxPoints = lightningRandSpacing > 0 ? (int)(resultLength / lightningRandSpacing) : 60;
        maxPoints = Mathf.clamp(maxPoints, 5, 120);
        int numPoints = Mathf.random((int)(5 * maxPoints / 12), maxPoints);

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
            b.data = points;
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

        if(lightningSync && b.data instanceof Seq){
            @SuppressWarnings("unchecked")
            Seq<Vec2> points = (Seq<Vec2>)b.data;
            if(points.isEmpty()) return;

            float fin = Mathf.curve(b.fin(), 0f, 0.2f);
            if(fin <= 0f) return;

            Lines.stroke(lightningWidth * b.fout());
            Draw.color(lightningColor, Color.white, b.fout() * 0.6f);

            Lines.beginLine();
            Fill.circle(b.x, b.y, Lines.getStroke() / 2f);
            Lines.linePoint(b.x, b.y);

            int i;
            float nx = b.x, ny = b.y;
            for(i = 0; i < (int)(points.size * fin); i++){
                Vec2 p = points.get(i);
                nx = p.x;
                ny = p.y;
                Lines.linePoint(nx, ny);
                Fill.circle(nx, ny, Lines.getStroke() / 2f);
            }

            if(i < points.size){
                float f = Mathf.clamp(fin * points.size % 1);
                Vec2 p = points.get(i);
                Tmp.v2.set(nx, ny).lerp(p.x, p.y, f);
                Lines.linePoint(Tmp.v2.x, Tmp.v2.y);
                Fill.circle(Tmp.v2.x, Tmp.v2.y, Lines.getStroke() / 2f);
            }

            Lines.endLine();
            Draw.reset();
        }
    }
}
