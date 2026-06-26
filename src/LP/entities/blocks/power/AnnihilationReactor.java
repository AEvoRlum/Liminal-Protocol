package LP.entities.blocks.power;

import arc.*;
import arc.audio.Sound;
import arc.graphics.*;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.power.PowerDistributor;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

import java.util.ArrayList;
import java.util.List;

import LP.graphics.LPPal;
import LP.graphics.Drawn;
import LP.content.LPLiquids;
import LP.content.LPStats;
import LP.content.LPSounds;

import static LP.graphics.Drawn.circlePercentFlip;

public class AnnihilationReactor extends PowerDistributor{
    
    /** 电力输入(每秒) */
    public float inputPower = 3600f;
    /** 电力输出(每秒) */
    public float outputPower = 14400f + inputPower;
    /** 满功率所需时间(tick) */
    public float warmupTime = 3600f;

    /** 消耗物品后回血量 */
    public float healAmount = 80f;

    /** 运行时震动 */
    public float loopShake = 1f;
    /** 震动触发间隔(tick) */
    public float shakeInterval = 3f;

    /** 运行时随机闪电 */
    public boolean updateLightning = true;
    /** 闪电颜色 */
    public Color updateLightningColor = LPPal.orangeRed;
    /** 固定闪电数 */
    public int updateLightningCount = 2;
    /** 随机闪电数± */
    public int randUpdateLightningCount = 1;
    /** 闪电长度 */
    public float updateLightningLength = 7f;
    /** 随机闪电长度± */
    public float randUpdateLightningLength = 5f;
    /** 闪电绘制范围 */
    public float updateLightningRange = 120f;
    /** 触发闪电间隔(tick) */
    public float updateLightningInterval = 30f;
    /** 随机触发闪电间隔± */
    public float randUpdateLightningInterval = 20f;

    /** 运行时范围伤害 */
    public float updateSplashDamage = 60f;
    /** 运行时范围伤害半径 */
    public float updateSplashRadius = 120f;
    /** 运行时范围伤害间隔(tick) */
    public float updateSplashInterval = 90f;
    /** 随机运行时范围伤害间隔± */
    public float randUpdateSplashInterval = 30f;
    /** 触发范围伤害时特效 */
    public Effect updateSplashEffect = Fx.none;
    /** 触发范围伤害时音效 */
    public Sound updateSplashSound = LPSounds.explosionArtilleryShockBig;
    /** 触发范围伤害时音效音量 */
    public float updateSplashSoundVolume = 1f;

    /** 运行时触发特效 */
    public Effect updateEffect = Fx.none;
    /** 触发运行时特效间隔(tick) */
    public float updateEffectInterval = 10f;
    /** 随机触发运行时特效间隔± */
    public float randUpdateEffectInterval = 10f;
    /** 运行时音效 */
    public Sound updateSound = LPSounds.loopAnnihilation;
    /** 运行时音效音量 */
    public float updateSoundVolume = 1f;

    /** 方块绘制 */
    public DrawBlock drawer = new DrawDefault();

    /** 光晕颜色 */
    public Color lightColor = Color.valueOf("FF584545");
    /** 光晕半径 */
    public float lightRadius = 120f;

    /** 特效半径 */
    public float effectRadius = 12f;

    /** 运行时损毁自爆 */
    protected BulletType destroyed;
    /** 自爆伤害倍率 */
    public float destroyedDamageMultiplier = 1f;
    /** 自爆伤害范围倍率 */
    public float destroyedSplashDamageMultiplier = 1f;

    /** 单流体消耗处理 */
    protected ConsumeLiquid liquidConsume;
    /** 多流体消耗处理 */
    protected ConsumeLiquids liquidConsumes;
    /** 统流体处理 */
    protected List<ConsumeLiquid> allLiquidConsumes = new ArrayList<>();

    public AnnihilationReactor(String name) {
        super(name);
        this.hasItems = true;
        this.hasPower = true;
        this.outputsPower = conductivePower = true;
        this.consumesPower = true;
        this.canOverdrive = false;
        drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(LPLiquids.heterohydrogen), new DrawDefault(), new DrawGlowRegion(){{
            alpha = 0.8f;
            glowIntensity = 0.2f;
            color = LPPal.orangeRed;
        }});
    }

    @Override
    public void init(){
        consumePower(inputPower / 60f);
        super.init();
    }

    @Override
    public void load(){
        super.load();
        drawer.load(this);
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.finalIcons(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, updateSplashRadius, LPPal.redDark);
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.basePowerGeneration, outputPower - inputPower, StatUnit.powerSecond);
        stats.add(LPStats.powerWarmupTime, warmupTime / 60f, StatUnit.seconds);
        stats.add(LPStats.itemHealAmount, healAmount);
    }

    @Override
    public void setBars(){
        super.setBars();

        // 电力输出条
        addBar("power", (AnnihilationReactorBuild entity) -> new Bar(() ->
        Core.bundle.format("bar.lp-poweroutput",
        Strings.fixed(entity.getPowerProduction() * 60f - inputPower, 1)),
        () -> Pal.powerBar,
        () -> Mathf.clamp(entity.warmup * entity.timeScale())));

        // 外部电力输入条
        if(consPower != null){
            addBar("power-input",  (AnnihilationReactorBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.lp-powerinput", Strings.fixed(entity.power.status * inputPower, 1)),
                () -> Pal.powerBar,
                () -> entity.power.status
            ));
        }
    }

    public class AnnihilationReactorBuild extends Building{
        public float warmup;
        public float totalProgress;
        public float lightningTimer;
        public float nextLightningInterval;
        public float splashTimer;
        public float nextSplashInterval;
        public float shakeTimer;
        public float effectTimer;
        public float nextEffectInterval;
        public float healTimer;
        public float liquidTimer;

        /** public float updateSoundTimer; */

        @Override
        public void created() {
            super.created();

             for (Consume cons : block.consumers) {
                if (cons instanceof ConsumeLiquid) {
                    allLiquidConsumes.add((ConsumeLiquid) cons);
                } else if (cons instanceof ConsumeLiquids) {
                    LiquidStack[] stacks = ((ConsumeLiquids) cons).liquids;
                    for (LiquidStack stack : stacks) {
                        allLiquidConsumes.add(new ConsumeLiquid(stack.liquid, stack.amount));
                    }
                }
            }
        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();
            nextLightningInterval = updateLightningInterval;
            nextSplashInterval = updateSplashInterval;
            nextEffectInterval = updateEffectInterval;
        }

        @Override
        public void updateTile(){
            boolean canRun = canRun();

            if(canRun && enabled){
                warmup = Mathf.approachDelta(warmup, 1f, 1f / warmupTime);
            }else{
                /** 当外部电力输入为0时，在300 tick内降至0 */
                float cooldownTime = inputPower <= 0f ? 300f : warmupTime;
                warmup = Mathf.approachDelta(warmup, 0f, 1f / cooldownTime);
            }

            totalProgress += warmup * Time.delta;

            if(warmup > 0f){
                if(loopShake > 0f){
                    shakeTimer += Time.delta;
                    if(shakeTimer >= shakeInterval){
                        shakeTimer = 0f;
                        Effect.shake(loopShake, loopShake, this);
                    }
                }

                liquidTimer += Time.delta;
                if (liquidConsume != null && liquidTimer >= 1.5f/** 浮点值补偿 */){
                    liquidTimer = 0f;
                    float amount = liquidConsume.amount;
                    Liquid liquid = liquidConsume.liquid;
                    if (liquids.get(liquid) >= amount){
                        liquids.remove(liquid, amount);
                    } else {
                        warmup = Mathf.lerpDelta(warmup, 0f, 0.001f);
                    }
                }

                effectTimer += Time.delta;
                if(effectTimer >= nextEffectInterval){
                    effectTimer = 0f;
                    nextEffectInterval = updateEffectInterval + Mathf.random(0f, randUpdateEffectInterval) - Mathf.random(randUpdateEffectInterval);
                    if (warmup > 0.5f){
                        if(updateEffect != Fx.none){
                            updateEffect.at(x, y);
                        }
                    }
                }
            }

            if(updateLightning && warmup > 0.45f){
                lightningTimer += Time.delta;
                if(lightningTimer >= nextLightningInterval){
                    lightningTimer = 0f;
                    nextLightningInterval = updateLightningInterval + Mathf.random(0f, randUpdateLightningInterval) - Mathf.random(randUpdateLightningInterval);
                    int count = updateLightningCount + (int)(Mathf.random(randUpdateLightningCount + 1));
                    for(int i = 0; i < count; i++){
                        float len = updateLightningLength + Mathf.random(randUpdateLightningLength);
                        float angle = Mathf.random(360f);
                        float dist = Mathf.random(updateLightningRange);
                        float lx = x + Angles.trnsx(angle, dist);
                        float ly = y + Angles.trnsy(angle, dist);
                        Drawn.randFadeLightningEffect(lx, ly, len * 20, len, updateLightningColor, Mathf.chance(0.5f));
                    }
                }
            }

           if(updateSound != Sounds.none && warmup > 0f){
                Vars.control.sound.loop(updateSound, this, updateSoundVolume * warmup, 1f);
            } else {
                updateSound.stop();
            }

            if(warmup >= 0.325f && updateSplashDamage > 0f){
                splashTimer += Time.delta;
                if(splashTimer >= nextSplashInterval){
                    splashTimer = 0f;
                    nextSplashInterval = (updateSplashInterval + Mathf.random(0f, randUpdateSplashInterval) - Mathf.random(randUpdateSplashInterval)) / warmup;
                    doSplashDamage();
                    if(updateSplashEffect != Fx.none){
                        updateSplashEffect.at(x, y);
                    }
                    if(updateSplashSound != Sounds.none){
                        updateSplashSound.at(x, y, 1f, updateSplashSoundVolume);
                    }
                }
            }

            /** 检查是否有 ConsumeLiquid 流体消耗定义 */
            for (Consume cons : block.consumers) {
                if (cons instanceof ConsumeLiquid) {
                    liquidConsume = (ConsumeLiquid) cons;
                    break;
                }
                
                if (cons instanceof ConsumeLiquids) {
                    liquidConsumes = (ConsumeLiquids) cons;
                }
            }

            /** 回血：检查是否有 ConsumeItems 物品消耗定义 */
            ItemStack[] healStacks = null;
            for(Consume cons : block.consumers){
                if(cons instanceof ConsumeItems){
                    healStacks = ((ConsumeItems)cons).items;
                    break;
                }
            }

            if(healStacks != null && damaged()){
                healTimer += Time.delta;
                if(healTimer >= 60f){
                    healTimer = 0f;
                    tryHeal(healStacks);
                }
            }
        }

        /** 检查是否可以运行：电力及除物品外的所有消耗是否满足 */
        public boolean canRun(){
            if(power == null) return false;
            if(efficiency < 0.999f) return false;

            for(Consume cons : block.consumers){
                if(cons.optional || cons.ignore()) continue;
                if(cons instanceof ConsumePower) continue;
                if(cons instanceof ConsumeItems) continue;
                if(cons.efficiency(this) <= 0.0000001f) return false;
            }
            return true;
        }

        protected boolean tryConsumeLiquid() {
            if (allLiquidConsumes.isEmpty()) return true;
            
            /** 先检查所有液体是否充足 */
            for (ConsumeLiquid cons : allLiquidConsumes) {
                if (liquids.get(cons.liquid) < cons.amount) {
                    return false;
                }
            }
            /** 全部充足，执行扣除 */
            for (ConsumeLiquid cons : allLiquidConsumes) {
                liquids.remove(cons.liquid, cons.amount);
            }
            return true;
        }

        public void tryHeal(ItemStack[] healStacks){
            /** 检查是否有足够的物品 */
            for(ItemStack stack : healStacks){
                if(items.get(stack.item) < stack.amount) return;
            }

            /** 消耗物品 */
            for(ItemStack stack : healStacks){
                items.remove(stack.item, stack.amount);
            }

            heal(healAmount);
            if(health > maxHealth) health = maxHealth;
        }

        public void doSplashDamage(){
            float radius = updateSplashRadius;
            float damage = updateSplashDamage;

            /** 对所有单位造成伤害 */
            Units.nearby(null, x, y, radius, unit -> {
                float dst = unit.dst(x, y);
                float falloff = 1f - dst / radius;
                unit.damagePierce(damage * falloff);
            });

            /** 对所有方块造成伤害 */
            indexer.eachBlock(null, x, y, radius, bool -> true, build -> {
                float dst = build.dst(x, y);
                float falloff = 1f - dst / radius;
                build.damagePierce(damage * falloff);
            });
        }

        @Override
        public void remove(){
            super.remove();
            if(updateSound != Sounds.none){
                updateSound.stop();
            }
        }

        @Override
        public void onDestroyed() {
            super.onDestroyed();

            if (updateSound != Sounds.none){
                updateSound.stop();
            }
            
            if (warmup <= 0f) return;

            destroyed.create(this, Team.derelict, this.x, this.y, 0);
            Damage.damage(null, this.x, this.y, 160f * destroyedDamageMultiplier, 1120f * destroyedSplashDamageMultiplier);
        }

        @Override
        public float getPowerProduction(){
            return enabled ? (outputPower / 60f) * warmup : 0f;
        }

        @Override
        public void draw(){
            drawer.draw(this);

            float effectSize = (effectRadius + Mathf.absin(effectRadius, effectRadius * 0.7f) * warmup) * warmup;
            float len = (effectRadius * 12f * (Mathf.absin(0.8f, 0.07f) + 1) + Mathf.absin(effectRadius, effectRadius * 0.7f)) * warmup;
            float lightLen = 0.8f * warmup;

            Draw.z(Layer.effect);

            Draw.color(LPPal.orangeRed);
            Fill.circle(this.x, this.y, (effectRadius + Mathf.absin(effectRadius, effectRadius * 0.7f) * warmup) * warmup * 0.8f);
            Drawf.tri(this.x, this.y, lightLen, len, 0f);
            Drawf.tri(this.x, this.y, lightLen, len, 180f);

            Lines.stroke(effectRadius / 8f * warmup);
            circlePercentFlip(this.x, this.y, effectSize * 1.6f, Time.time * 0.5f, 12f);
            circlePercentFlip(this.x, this.y, effectSize * 3.25f, Time.time * 0.8f, 15f);
            circlePercentFlip(this.x, this.y, effectSize * 5f, Time.time, 18f);

            Draw.color(LPPal.redLight);
            Fill.circle(this.x, this.y, (effectRadius + Mathf.absin(effectRadius, effectRadius * 0.7f) * warmup) * warmup * 0.4f);

            Draw.z(Layer.effect - 0.001f);

            Drawf.tri(this.x, this.y, lightLen * 0.5f, len * 0.5f, 0f);
            Drawf.tri(this.x, this.y, lightLen * 0.5f, len * 0.5f, 180f);
        }

        /** 光晕绘制 */
        @Override
        public void drawLight(){
            super.drawLight();
            Drawf.light(x, y, (lightRadius + Mathf.absin(10f, 5f)) * size, lightColor, 0.5f * warmup);
        }

        @Override
        public float warmup(){
            return warmup;
        }

        @Override
        public float totalProgress(){
            return totalProgress;
        }

        @Override
        public float ambientVolume(){
            return Mathf.clamp(warmup);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            /** 检查是否有 ConsumeItems 物品消耗定义 */
            for(Consume cons : block.consumers){
                if(cons instanceof ConsumeItems){
                    for(ItemStack stack : ((ConsumeItems)cons).items){
                        if(stack.item == item){
                            return items.get(item) < itemCapacity;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(warmup);
            write.f(totalProgress);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            warmup = read.f();
            if(revision >= 1){
                totalProgress = read.f();
            }
        }
    }
}
