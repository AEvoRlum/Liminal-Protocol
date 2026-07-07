package LP.entities.blocks.craft;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

import LP.ui.LPUi;

/**
 * 多配方工厂
 * <p>
 * 核心特性：
 * <ul>
 *   <li>{@link #craftPlans} 中每个 {@link CraftPlan} 都拥有独立的输入消耗（items / liquids / power / heat / payload）与独立的输出</li>
 *   <li>方块被放置后，玩家可在配置面板中切换当前生效的配方；切换不会清空物品缓冲</li>
 *   <li>液体输出方向可通过 {@link CraftPlan#liquidOutputDirections} 按配方指定</li>
 *   <li>支持 Payload（单位/方块货物）作为输入</li>
 *   <li>支持热力：每个配方可独立设置 {@link CraftPlan#heatRequirement} 与产出 {@link CraftPlan#heatOutput}</li>
 *   <li>每个配方维护自己的 {@link Consume} 数组；切换配方时通过 {@link MultiCrafterBuild#updateConsumption()} 重新计算效率</li>
 * </ul>
 * 所有 UI 相关的代码（配置面板、stat 面板、状态条面板、输入资源显示）均已抽出到 {@link LP.ui.LPUi}
 *
 */
public class MultiCrafter extends PayloadBlock{
    /** 配方列表。每个 CraftPlan 是一个完整独立的“迷你工厂方块” */
    public Seq<CraftPlan> craftPlans = new Seq<>();

    /** 如果 {@link #useBlockDrawer} 为 false，则每个配方独立使用自己的 drawer 来绘制 */
    public DrawBlock drawer = new DrawDefault();
    /** 是否使用方块自身的 drawer（而非每个配方独立的） */
    public boolean useBlockDrawer = false;

    /** 是否启用液体输出方向切换按钮（配置面板中出现 4 个方向按钮）*/
    public boolean hasDoubleOutput = false;

    /** 配方的 {@link CraftPlan#init()} 中会自动把输入/输出液体注册为状态条 */
    public boolean autoAddBar = true;
    
    /** 生产进度条的颜色 */
    public Color progressColor = Pal.accent;

    /** 是否在配方的状态条面板中显示生产进度条（每个配方独立显示自己的进度） */
    public boolean showProgressBar = true;

    /** 是否在“选中方块”时在方块脚下绘制一张液体悬浮表 */
    public boolean useLiquidTable = true;

    /** 滚动列表中最多同时展示的配方数；用于配置面板ScrollPane的高度 */
    public int maxList = 4;

    /** Payload 最大尺寸（单条边的tile数） */
    public float maxPayloadSize = 4f;

    /** 每个配方对应的缓存 PayloadSeq 中的最大条目数（防止单条缓存过大） */
    public int payloadCapacity = 20;

    /** 配置面板的滚动位置 */
    public float selectScroll;

    public MultiCrafter(String name){
        super(name);

        update = true;
        solid = true;
        hasItems = true;
        ambientSound = Sounds.loopMachine;
        sync = true;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.factory);
        drawArrow = false;

        configurable = true;
        saveConfig = true;
        /** 统一的配置格式：int[] { 旋转角度索引, 配方索引 } */
        config(int[].class, (MultiCrafterBuild tile, int[] in) -> tile.applyConfig(in));

        /** 便捷入口：只传入配方索引，旋转保持不变 */
        config(Integer.class, (MultiCrafterBuild tile, Integer in) ->
            tile.applyConfig(new int[]{tile.rotation, in}));
    }

    @Override
    public void init(){
        for(CraftPlan plan : craftPlans){
            plan.owner = this;
            plan.init();
            if(plan.outputLiquids.length > 0){
                hasLiquids = true;
                outputsLiquid = true;
            }
            if(plan.outputItems.length > 0){
                hasItems = true;
            }
            if(plan.consPower != null){
                hasPower = true;
                consumesPower = true;
            }
            if(plan.consPayload != null){
                acceptsPayload = true;
                acceptsUnitPayloads = true;
            }
            if(plan.powerProduction > 0){
                hasPower = true;
                outputsPower = true;
            }
        }

        // 动态电力：当前配方用多少电，就请求多少电
        if(hasPower && consumesPower) consumePowerDynamic(b ->
            b instanceof MultiCrafterBuild tile ? tile.formulaPower() : 0f);

        super.init();
        hasConsumers = craftPlans.any();
    }

    @Override
    public void setBars(){
        addBar("health", entity -> new Bar("stat.health", Pal.health, entity::healthf).blink(Color.white));

        if(consPower != null){
            boolean buffered = consPower.buffered;
            float capacity = consPower.capacity;
            addBar("power", entity -> new Bar(
                () -> buffered ? Core.bundle.format("bar.poweramount", Float.isNaN(entity.power.status * capacity) ? "<ERROR>" : UI.formatAmount((int)(entity.power.status * capacity))) :
                    Core.bundle.get("bar.power"),
                () -> Pal.powerBar,
                () -> Mathf.zero(consPower.requestedPower(entity)) && entity.power.graph.getPowerProduced() + entity.power.graph.getBatteryStored() > 0f ? 1f : entity.power.status)
            );
        }

        // 热量条：只有某些配方需要热量时才显示
        if(craftPlans.contains(c -> c.heatRequirement > 0f)){
            addBar("heat", (MultiCrafterBuild entity) -> new Bar(
                "bar.heat",
                Pal.lightOrange,
                () -> {
                    float req = entity.heatRequirement();
                    return req <= 0f ? 1f : Mathf.clamp(entity.heat / req, 0f, 1f);
                }));
        }

        if(unitCapModifier != 0){
            stats.add(Stat.maxUnits, (unitCapModifier < 0 ? "-" : "+") + Math.abs(unitCapModifier));
        }
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.output, table -> {
            table.row();
            for(CraftPlan plan : craftPlans){
                table.table(Styles.grayPanel, info -> {
                    info.left().defaults().left();
                    Stats s = new Stats();
                    s.timePeriod = plan.craftTime;
                    if(plan.hasConsumers) for(Consume c : plan.consumers) c.display(s);

                    if((hasItems && itemCapacity > 0) || plan.outputItems.length > 0)
                        s.add(Stat.productionTime, plan.craftTime / 60f, StatUnit.seconds);

                    if(plan.heatRequirement > 0f){
                        s.add(Stat.input, plan.heatRequirement, StatUnit.heatUnits);
                        s.add(Stat.maxEfficiency, (int)(plan.maxHeatEfficiency * 100f), StatUnit.percent);
                    }

                    if(plan.heatOutput > 0f){
                        s.add(Stat.output, plan.heatOutput, StatUnit.heatUnits);
                    }

                    if(plan.outputItems.length > 0)
                        s.add(Stat.output, StatValues.items(plan.craftTime, plan.outputItems));

                    if(plan.outputLiquids.length > 0)
                        s.add(Stat.output, StatValues.liquids(1f, plan.outputLiquids));

                    info.table(t -> LPUi.statTurnTable(s, t)).pad(8).left();
                }).growX().left().pad(10);
                table.row();
            }
        });
    }

    @Override
    public void load(){
        super.load();
        if(useBlockDrawer){
            drawer.load(this);
        }else{
            for(CraftPlan p : craftPlans) p.drawer.load(this);
        }
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        if(useBlockDrawer){
            drawer.drawPlan(this, plan, list);
            return;
        }
        DrawBlock first = craftPlans.any() ? craftPlans.get(0).drawer : null;
        if(first == null){
            super.drawPlanRegion(plan, list);
        }else{
            first.drawPlan(this, plan, list);
        }
    }

    @Override
    protected TextureRegion[] icons(){
        if(useBlockDrawer) return drawer.icons(this);
        DrawBlock fallback = craftPlans.any() ? craftPlans.get(0).drawer : null;
        return fallback == null ? super.icons() : fallback.icons(this);
    }

    /** 将配方索引夹到 [0, craftPlans.size-1]；空配方列表返回 -1 */
    public int clampCraftPlanIndex(int index){
        return craftPlans.isEmpty() ? -1 : Mathf.clamp(index, 0, craftPlans.size - 1);
    }

    // ------------------------------------------------------------------
    // Building
    // ------------------------------------------------------------------

    /** {@link MultiCrafter} 的建筑实例，拥有独立的当前配方、进度、热量、Payload 缓冲 */
    public class MultiCrafterBuild extends PayloadBlockBuild<Payload> implements HeatBlock, HeatConsumer{
        /** 当前选中的配方；null 表示禁用 */
        public CraftPlan craftPlan = craftPlans.any() ? craftPlans.get(0) : null;
        /** 当前配方的生产进度（0..1） */
        public float progress;
        /** 总进度（用于贴图动画） */
        public float totalProgress;
        /** 预热，范围 0..1，每次 tick 朝 warmupTarget() 线性逼近 */
        public float warmup;
        /** 当前配方的热量输入，由 {@link HeatBlock} 接口的邻居在每 tick 更新 */
        public float heat;
        public float[] sideHeat = new float[4];
        public PayloadSeq payloads = new PayloadSeq();

        public int[] configs = {0, 0};
        public int lastRotation = -1;
        public float selectScroll;

        // ------------------------------------------------------------------
        // 配置切换
        // ------------------------------------------------------------------

        /** 返回当前配方在 {@link #craftPlans} 中的索引；无配方时返回 -1 */
        public int craftPlanIndex(){
            if(craftPlan == null) return -1;
            int idx = craftPlans.indexOf(craftPlan);
            return idx < 0 ? -1 : idx;
        }

        /** 设置配方索引；越界会被 {@link MultiCrafter#clampCraftPlanIndex(int)} 修正 */
        public void setCraftPlanIndex(int index){
            int resolved = clampCraftPlanIndex(index);
            if(resolved < 0){
                craftPlan = null;
                configs[1] = -1;
                return;
            }
            craftPlan = craftPlans.get(resolved);
            configs[1] = resolved;
        }

        /** 应用来自配置面板或存档的配置数组 */
        public void applyConfig(int[] in){
            if(in == null || in.length != 2) return;
            rotation = Mathf.mod(in[0], 4);
            configs[0] = rotation;
            setCraftPlanIndex(in[1]);
        }

        // ------------------------------------------------------------------
        // 绘制
        // ------------------------------------------------------------------

        @Override
        public void draw(){
            (useBlockDrawer || craftPlan == null ? drawer : craftPlan.drawer).draw(this);
        }

        @Override
        public void drawStatus(){
            if(block.enableDrawStatus && craftPlan != null && craftPlan.hasConsumers){
                float multiplier = block.size > 1 ? 1 : 0.64f;
                float brcX = x + (float)(block.size * 8) / 2f - 8f * multiplier / 2f;
                float brcY = y - (float)(block.size * 8) / 2f + 8f * multiplier / 2f;
                Draw.z(71f);
                Draw.color(Pal.gray);
                Fill.square(brcX, brcY, 2.5f * multiplier, 45);
                Draw.color(status().color);
                Fill.square(brcX, brcY, 1.5f * multiplier, 45);
                Draw.color();
            }
        }

        /** 预热上限；当前实现固定返回 1。可被覆写实现不同配方不同预热上限 */
        public float warmupTarget(){
            return 1f;
        }

        // ------------------------------------------------------------------
        // 电力 / 热量
        // ------------------------------------------------------------------

        /** 当前配方需要的电力（用于 {@link ConsumePowerDynamic}） */
        public float formulaPower(){
            if(craftPlan == null || craftPlan.consPower == null) return 0f;
            return craftPlan.consPower.usage;
        }

        /** 基于热量/配方需求计算生产效率缩放 */
        public float heatEfficiency(){
            heat = calculateHeat(sideHeat);
            if(craftPlan == null || craftPlan.heatRequirement <= 0f) return 1f;
            return Mathf.clamp(heat / craftPlan.heatRequirement, 0f, craftPlan.maxHeatEfficiency);
        }

        @Override
        public float efficiencyScale(){
            return heatEfficiency();
        }

        @Override
        public float[] sideHeat(){ return sideHeat; }

        @Override
        public float heatRequirement(){
            return craftPlan == null ? 0f : craftPlan.heatRequirement;
        }

        @Override
        public float heat(){
            if(craftPlan == null || craftPlan.heatOutput <= 0f) return 0f;
            return craftPlan.heatOutput * warmup;
        }

        @Override
        public float heatFrac(){
            if(craftPlan == null || craftPlan.heatOutput <= 0f) return 0f;
            return Mathf.clamp(heat() / craftPlan.heatOutput, 0f, 1f);
        }

        @Override
        public PayloadSeq getPayloads(){ return payloads; }

        @Override
        public float getPowerProduction(){
            if(craftPlan == null || !enabled) return 0f;
            return craftPlan.powerProduction * efficiency;
        }

        // ------------------------------------------------------------------
        // 主循环：生产/输出
        // ------------------------------------------------------------------

        @Override
        public void updateTile(){
            if(lastRotation != rotation){
                Fx.placeBlock.at(x, y, size);
                lastRotation = rotation;
            }

            if(craftPlan == null) return;

            if(efficiency > 0f){
                float inc = getProgressIncrease(craftPlan.craftTime, craftPlan);
                if(inc > 0f){
                    progress += inc;
                    warmup = Mathf.approachDelta(warmup, warmupTarget(), craftPlan.warmupSpeed);

                    if(craftPlan.outputLiquids.length > 0){
                        float liquidInc = getProgressIncrease(1f);
                        for(LiquidStack output : craftPlan.outputLiquids){
                            handleLiquid(this, output.liquid,
                                Math.min(output.amount * liquidInc, liquidCapacity - liquids.get(output.liquid)));
                        }
                    }

                    if(wasVisible && Mathf.chanceDelta(craftPlan.updateEffectChance)){
                        craftPlan.updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                    }
                }else{
                    warmup = Mathf.approachDelta(warmup, 0f, craftPlan.warmupSpeed);
                }
            }else{
                warmup = Mathf.approachDelta(warmup, 0f, craftPlan.warmupSpeed);
            }

            totalProgress += warmup * Time.delta;

            if(progress >= 1f){
                craft(craftPlan);
            }

            dumpOutputs(craftPlan);
        }

        @Override
        public float totalProgress(){ return totalProgress; }

        @Override
        public float progress(){ return progress; }

        @Override
        public float warmup(){ return warmup; }

        /**
         * 按配方/液体约束获取生产进度增量
         * <p>
         * 与原版 {@link Building#getProgressIncrease(float)} 的区别：
         * 若 {@link CraftPlan#ignoreLiquidFullness} 为 false，则会检查每个输出液体是否有剩余空间，
         * 并按最紧的一个缩放进度；此举防止“液体已满，但物品/其他液体还在继续生产”造成不匹配
         */
        public float getProgressIncrease(float baseTime, CraftPlan plan){
            if(plan.ignoreLiquidFullness){
                return super.getProgressIncrease(baseTime);
            }

            float scaling = 1f, max = 1f;
            if(plan.outputLiquids.length > 0){
                max = 0f;
                for(LiquidStack output : plan.outputLiquids){
                    float value = (liquidCapacity - liquids.get(output.liquid)) / (output.amount * edelta());
                    scaling = Math.min(scaling, value);
                    max = Math.max(max, value);
                }
            }

            return super.getProgressIncrease(baseTime) * (plan.dumpExtraLiquid ? Math.min(max, 1f) : scaling);
        }

        /**
         * 触发一次合成：扣除输入（由 {@link #consume()} 完成），然后按配方产出物品/特效
         */
        public void craft(CraftPlan plan){
            consume();

            for(ItemStack output : plan.outputItems){
                for(int i = 0; i < output.amount; i++){
                    offload(output.item);
                }
            }

            if(wasVisible){
                plan.craftEffect.at(x, y);
            }
            progress %= 1f;
        }

        /** 倾倒输出：物品向四周传送带倾倒；液体按 {@link CraftPlan#liquidOutputDirections} 定向倾倒 */
        public void dumpOutputs(CraftPlan plan){
            if(plan.outputItems.length > 0 && timer(timerDump, dumpTime / timeScale)){
                for(ItemStack output : plan.outputItems) dump(output.item);
            }

            if(plan.outputLiquids.length > 0){
                for(int i = 0; i < plan.outputLiquids.length; i++){
                    int dir = plan.liquidOutputDirections.length > i ? plan.liquidOutputDirections[i] : -1;
                    dumpLiquid(plan.outputLiquids[i].liquid, 2f, dir);
                }
            }
        }

        // ------------------------------------------------------------------
        // 应该消费/应该接收
        // ------------------------------------------------------------------

        @Override
        public boolean shouldConsume(){
            if(craftPlan == null) return false;
            for(ItemStack output : craftPlan.outputItems){
                if(items.get(output.item) + output.amount > itemCapacity) return false;
            }
            if(craftPlan.outputLiquids.length > 0 && !craftPlan.ignoreLiquidFullness){
                boolean allFull = true;
                for(LiquidStack output : craftPlan.outputLiquids){
                    if(liquids.get(output.liquid) >= liquidCapacity - 0.001f){
                        if(!craftPlan.dumpExtraLiquid) return false;
                    }else{
                        allFull = false;
                    }
                }
                if(allFull) return false;
            }
            return enabled;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            if(craftPlan == null) return false;
            return craftPlan.getConsumeItem(item) && items.get(item) < itemCapacity;
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            if(craftPlan == null) return false;
            return block.hasLiquids && craftPlan.getConsumeLiquid(liquid);
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            if(craftPlan == null || payload == null) return false;
            if(!block.acceptsPayload || !payload.fits(maxPayloadSize)) return false;

            UnlockableContent content = payload.content();
            if(content == null) return false;

            int need = craftPlan.payloadNeed(content, this);
            return need > 0 && payloads.get(content) < need && payloads.total() < payloadCapacity;
        }

        @Override
        public void handlePayload(Building source, Payload payload){
            if(craftPlan == null || payload == null) return;
            UnlockableContent content = payload.content();
            if(content == null) return;
            int need = craftPlan.payloadNeed(content, this);
            if(need <= 0 || payloads.get(content) >= need || payloads.total() >= payloadCapacity) return;
            payloads.add(content);
        }

        /**
         * 扣除输入：遍历当前配方的 {@link CraftPlan#consumers} 并触发每个 {@link Consume#trigger(Building)}
         */
        @Override
        public void consume(){
            if(craftPlan == null) return;
            for(Consume cons : craftPlan.consumers) cons.trigger(this);
        }

        // ------------------------------------------------------------------
        // UI：统一委托给 LPUi
        // ------------------------------------------------------------------

        @Override
        public void displayConsumption(Table table){
            LPUi.buildConsumption(this, table);
        }

        @Override
        public void drawSelect(){
            super.drawSelect();
            if(craftPlan == null || !useLiquidTable) return;

            if(craftPlan.outputLiquids.length > 0){
                for(int i = 0; i < craftPlan.outputLiquids.length; i++){
                    int dir = craftPlan.liquidOutputDirections.length > i ? craftPlan.liquidOutputDirections[i] : -1;
                    if(dir != -1){
                        Draw.rect(
                            craftPlan.outputLiquids[i].liquid.fullIcon,
                            x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
                            y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
                            8f, 8f);
                    }
                }
            }
        }

        @Override
        public void displayBars(Table table){
            LPUi.buildRecipeBars(MultiCrafter.this, this, table);
        }

        @Override
        public boolean shouldAmbientSound(){
            return efficiency > 0;
        }

        // ------------------------------------------------------------------
        // 效率计算（基于当前配方的 consumers）
        // ------------------------------------------------------------------

        public transient boolean shouldConsumePower;

        /**
         * 每 tick 基于当前配方的 consumers 重算效率
         * 原版 {@link Building#updateConsumption} 使用 {@link Block#consumers}；此处替换为
         * {@link CraftPlan#consumers / optionalConsumers / nonOptionalConsumers}，因此切换配方时会自然切换逻辑
         */
        @Override
        public void updateConsumption(){
            if(craftPlan == null) return;

            if(!craftPlan.hasConsumers || cheating()){
                potentialEfficiency = enabled && productionValid() ? 1f : 0f;
                efficiency = optionalEfficiency = shouldConsume() ? potentialEfficiency : 0f;
                shouldConsumePower = true;
                updateEfficiencyMultiplier();
                return;
            }

            if(!enabled){
                potentialEfficiency = efficiency = optionalEfficiency = 0f;
                shouldConsumePower = true;
                return;
            }

            boolean valid = shouldConsume() && productionValid();
            float minEfficiency = 1f;
            efficiency = optionalEfficiency = 1f;
            shouldConsumePower = true;

            for(Consume cons : craftPlan.nonOptionalConsumers){
                float result = cons.efficiency(this);
                if(cons != consPower && result <= 0.0000001f){
                    shouldConsumePower = false;
                }
                minEfficiency = Math.min(minEfficiency, result);
            }

            for(Consume cons : craftPlan.optionalConsumers){
                optionalEfficiency = Math.min(optionalEfficiency, cons.efficiency(this));
            }

            efficiency = minEfficiency;
            optionalEfficiency = Math.min(optionalEfficiency, minEfficiency);
            potentialEfficiency = efficiency;
            if(!valid) efficiency = optionalEfficiency = 0f;

            updateEfficiencyMultiplier();

            if(valid && efficiency > 0){
                for(Consume cons : craftPlan.updateConsumers) cons.update(this);
            }
        }

        // ------------------------------------------------------------------
        // 配置面板
        // ------------------------------------------------------------------

        @Override
        public void buildConfiguration(Table table){
            LPUi.buildRecipeConfig(MultiCrafter.this, this, table);
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.progress) return progress;
            return super.sense(sensor);
        }

        @Override
        public int[] config(){
            return new int[]{rotation, craftPlanIndex()};
        }

        @Override
        public void configure(Object value){
            super.configure(value);
            deselect();
        }

        @Override
        public byte version(){ 
            return 2; 
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(progress);
            write.f(warmup);
            write.i(lastRotation);
            write.i(craftPlanIndex());
            payloads.write(write);
            write.f(selectScroll);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            progress = read.f();
            warmup = read.f();
            lastRotation = read.i();
            setCraftPlanIndex(read.i());
            configs[0] = rotation;
            selectScroll = read.f();
            if(revision >= 1){
                payloads.read(read);
            }else{
                payloads.clear();
            }
        }
    }

    // ------------------------------------------------------------------
    // 配方定义
    // ------------------------------------------------------------------

    /** 一个完整的“配方”，包含自己的 consumers、输出、状态条、绘制器等 */
    public static class CraftPlan{
        /** 消费者：填充自 {@link #consumeBuilder}；仅在 {@link MultiCrafter#init()} 后有效 */
        public Consume[] consumers = {}, optionalConsumers = {}, nonOptionalConsumers = {}, updateConsumers = {};
        /** 电力消耗（若此配方耗电） */
        public ConsumePower consPower = null;
        /** Payload 消耗（若此配方需要 payload 作为输入） */
        public ConsumePayloads consPayload = null;

        /** 制作一次所需的 tick 时间 */
        public float craftTime = 60f;
        /** 本配方是否有任何消费者；仅在 {@link #init()} 之后有效 */
        public boolean hasConsumers = false;

        /** 本配方每次合成产出的物品 */
        public ItemStack[] outputItems = {};
        /** 本配方在合成过程中持续产出的液体（每 tick 按进度分配） */
        public LiquidStack[] outputLiquids = {};

        /**
         * 对应每个 {@link #outputLiquids} 的输出方向（0..3 对应右/上/左/下，-1 表示全方向）
         * 若未指定足够数量，默认使用 -1
         */
        public int[] liquidOutputDirections = {-1};

        /** 若为 true，即使液体输出已满，生产进度也会继续推进（仅适用于没有液体缓冲约束的场景） */
        public boolean ignoreLiquidFullness = false;

        /** 若为 true：即使某些液体输出已满，也允许其他液体继续推进进度；在配置面板仍可看到全部液体状态条 */
        public boolean dumpExtraLiquid = true;

        /** 预热速度（默认 0.02） */
        public float warmupSpeed = 0.02f;

        /** 每 tick 播放“生产中”特效的概率 */
        public float updateEffectChance = 0.05f;

        /** 每 tick 额外产出的电力（仅在 efficiency>0 时生效） */
        public float powerProduction = 0f;

        /** 热力输入需求；大于 0 时根据实际 heat 计算 {@link MultiCrafterBuild#heatEfficiency()} */
        public float heatRequirement = 0f;
        /** 热力输出 */
        public float heatOutput = 0f;
        public float warmupRate = 0.15f;
        /** 热力输出对效率的最大缩放（例如 2.0 表示可在超热时提供双倍输出） */
        public float maxHeatEfficiency = 1f;

        public Effect updateEffect = Fx.none;
        public Effect craftEffect = Fx.none;

        public DrawBlock drawer = new DrawDefault();

        /** 本配方接收哪些物品/液体（由 consumeXxx 自动填充） */
        public ObjectMap<Item, Boolean> itemFilter = new ObjectMap<>();
        public ObjectMap<Liquid, Boolean> liquidFilter = new ObjectMap<>();
        public Seq<PayloadStack> payloadRequirements = new Seq<>();

        protected MultiCrafter owner = null;
        protected Seq<Consume> consumeBuilder = new Seq<>();
        protected OrderedMap<String, Func<Building, Bar>> barMap = new OrderedMap<>();

        /**
         * 由 {@link MultiCrafter#init()} 调用：将构建期 consumers 分成四类，
         * 并（若启用）自动为输入/输出液体注册各自的状态条
         */
        public void init(){
            consumers = consumeBuilder.toArray(Consume.class);
            optionalConsumers = consumeBuilder.select(c -> c.optional && !c.ignore()).toArray(Consume.class);
            nonOptionalConsumers = consumeBuilder.select(c -> !c.optional && !c.ignore()).toArray(Consume.class);
            updateConsumers = consumeBuilder.select(c -> c.update && !c.ignore()).toArray(Consume.class);
            hasConsumers = consumers.length > 0;

            if(owner.autoAddBar){
                if(!liquidFilter.isEmpty()){
                    for(Liquid l : liquidFilter.keys().toSeq()) addLiquidBar(l);
                }
                for(LiquidStack l : outputLiquids) addLiquidBar(l.liquid);
            }
            
            if(owner.showProgressBar){
                addProgressBar();
            }
        }

        public void setApply(UnlockableContent content){
            if(content instanceof Item item) itemFilter.put(item, true);
            if(content instanceof Liquid liquid) liquidFilter.put(liquid, true);
        }

        public Iterable<Func<Building, Bar>> listBars(){ return barMap.values(); }

        public boolean hasBars(){ return !barMap.isEmpty(); }

        public void addBar(String name, Func<Building, Bar> sup){ barMap.put(name, sup); }

        public void addLiquidBar(Liquid liquid){
            addBar("liquid-" + liquid.name, build -> !liquid.unlockedNow() ? null : new Bar(
                () -> liquid.localizedName,
                liquid::barColor,
                () -> build.liquids.get(liquid) / owner.liquidCapacity));
        }

        public void addProgressBar(){
            addBar("progress", build -> {
                MultiCrafter.MultiCrafterBuild b = (MultiCrafter.MultiCrafterBuild) build;
                return new Bar(
                    () -> Core.bundle.get("bar.progress"),
                    () -> owner.progressColor,
                    () -> b.progress
                );
            });
        }

        public MultiCrafter owner(){ return owner; }

        @SuppressWarnings("unchecked")
        public <T extends Consume> T findConsumer(Boolf<Consume> filter){
            return consumers.length == 0
                ? (T)consumeBuilder.find(filter)
                : (T)Structs.find(consumers, filter);
        }

        public boolean hasConsumer(Consume cons){ return consumeBuilder.contains(cons); }

        public void removeConsumer(Consume cons){
            if(consumers.length > 0) return;
            consumeBuilder.remove(cons);
        }

        public void removeConsumers(Boolf<Consume> b){
            consumeBuilder.removeAll(b);
            if(!consumeBuilder.contains(c -> c instanceof ConsumePower)) consPower = null;
            if(!consumeBuilder.contains(c -> c instanceof ConsumePayloads)){
                consPayload = null;
                payloadRequirements.clear();
            }
        }

        public boolean getConsumeItem(Item item){
            return itemFilter.containsKey(item) && itemFilter.get(item);
        }

        public boolean getConsumeLiquid(Liquid liquid){
            return liquidFilter.containsKey(liquid) && liquidFilter.get(liquid);
        }

        /**
         * 判断某内容（unit / block payload）在当前配方中需要多少个
         * 这个数量会被 {@link ConsumePayloads#multiplier} 进一步缩放
         */
        public int payloadNeed(UnlockableContent content, Building build){
            if(content == null || consPayload == null || payloadRequirements.isEmpty()) return 0;
            float mult = consPayload.multiplier.get(build);
            int need = 0;
            for(PayloadStack stack : payloadRequirements){
                if(stack.item == content) need += Math.round(stack.amount * mult);
            }
            return need;
        }

        // ------------------------------------------------------------------
        // 便捷 API：使用链式调用定义配方
        // ------------------------------------------------------------------

        public void consumeLiquid(Liquid liquid, float amount){
            setApply(liquid);
            consume(new ConsumeLiquid(liquid, amount));
        }

        public void consumeLiquids(LiquidStack... stacks){
            for(LiquidStack s : stacks) setApply(s.liquid);
            consume(new ConsumeLiquids(stacks));
        }

        public void consumePower(float powerPerTick){
            consume(new ConsumePower(powerPerTick, 0.0f, false));
        }

        public void consumePayload(UnlockableContent content){ consumePayload(content, 1); }

        public void consumePayload(UnlockableContent content, int amount){
            consumePayloads(PayloadStack.with(content, amount));
        }

        public void consumePayloads(PayloadStack... payloads){
            consumePayloads(new Seq<>(payloads));
        }

        public void consumePayloads(Seq<PayloadStack> payloads){
            payloadRequirements.clear();
            payloadRequirements.addAll(payloads);
            Seq<PayloadStack> req = new Seq<>();
            req.addAll(payloadRequirements);
            consume(new ConsumePayloads(req));
        }

        public void consumeItem(Item item){
            consumeItem(item, 1);
        }

        public void consumeItem(Item item, int amount){
            setApply(item);
            consume(new ConsumeItems(new ItemStack[]{new ItemStack(item, amount)}));
        }

        public void consumeItems(ItemStack... items){
            for(ItemStack s : items) setApply(s.item);
            consume(new ConsumeItems(items));
        }

        /**
         * 添加一个消费者；如果这是电力/Payload 消费者，会覆盖旧的同类消费者
          * （一个配方只允许一个 {@link ConsumePower} 和一个 {@link ConsumePayloads}）
         */
        public <T extends Consume> void consume(T consume){
            if(consume instanceof ConsumePower cp){
                consumeBuilder.removeAll(b -> b instanceof ConsumePower);
                consPower = cp;
            }
            if(consume instanceof ConsumePayloads cp){
                consumeBuilder.removeAll(b -> b instanceof ConsumePayloads);
                consPayload = cp;
                payloadRequirements.clear();
                payloadRequirements.addAll(cp.payloads);
            }
            consumeBuilder.add(consume);
        }
    }
}
