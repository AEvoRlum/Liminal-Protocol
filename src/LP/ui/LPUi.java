package LP.ui;

import arc.Core;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

import LP.graphics.LPPal;

import static mindustry.Vars.tilesize;

import LP.entities.blocks.craft.MultiCrafter;
import LP.entities.blocks.turret.ItemAmmoTurret;

/**
 * 多配方方块的 UI 辅助方法集中地
 * 负责：stat 面板转换、配方选择配置面板、状态条面板、输入资源显示面板
 * 游戏逻辑（生产、接受物品、效率计算、热量处理等）请见 {@link LP.entities.blocks.craft.MultiCrafter}
 */
public final class LPUi{

    public static TextureRegion ammoTurretReloadIcon = Core.atlas.find("lp-ammo-turret-reload");

    private LPUi(){}

    /** 把 Stats 的条目依次写入 Table（无分类分组，扁平排列） */
    public static void statToTable(Stats stat, Table table){
        var cats = stat.toMap().keys().toSeq();
        for(int i = 0; i < cats.size; i++){
            var ss = stat.toMap().get(cats.get(i)).keys().toSeq();
            for(int j = 0; j < ss.size; j++){
                var vs = stat.toMap().get(cats.get(i)).get(ss.get(j));
                for(int k = 0; k < vs.size; k++){
                    vs.get(k).display(table);
                }
            }
        }
    }

    /** 分类写入 Stats，带 "category.xxx" 标题行 */
    public static void statTurnTable(Stats stat, Table table){
        for(StatCat cat : stat.toMap().keys()){
            var map = stat.toMap().get(cat);
            if(map.size == 0) continue;

            if(stat.useCategories){
                table.add("@category." + cat.name).color(LPPal.accent.cpy()).fillX();
                table.row();
            }

            for(Stat s : map.keys()){
                table.table(inset -> {
                    inset.left();
                    inset.add("[lightgray]" + s.localized() + ":[] ").left().top();
                    Seq<StatValue> arr = map.get(s);
                    for(StatValue value : arr){
                        value.display(inset);
                        inset.add().size(10f);
                    }
                }).fillX().padLeft(10);
                table.row();
            }
        }
    }

    /** 构建配方选择配置面板（含可选的液体输出方向切换按钮） */
    public static void buildRecipeConfig(MultiCrafter owner, MultiCrafter.MultiCrafterBuild build, Table table){
        Table rot = new Table();
        rot.left().defaults().size(55);

        Table cont = new Table().top();
        cont.left().defaults().left().growX();

        Runnable rebuild = () -> {
            rot.clearChildren();
            if(owner.hasDoubleOutput){
                for(int i = 0; i < 4; i++){
                    var button = new ImageButton();
                    int ii = i;
                    button.table(img -> img.image(Icon.right).color(Color.white).size(40).pad(10f));
                    button.changed(() -> build.configure(new int[]{ii, build.craftPlanIndex()}));
                    button.update(() -> button.setChecked(build.rotation == ii));
                    button.setStyle(Styles.clearNoneTogglei);
                    rot.add(button).tooltip(String.valueOf(i * 90));
                }
            }

            cont.clearChildren();
            for(MultiCrafter.CraftPlan plan : owner.craftPlans){
                var button = new ImageButton();
                button.table(info -> {
                    info.left();
                    info.table(from -> {
                        Stats s = new Stats();
                        s.timePeriod = plan.craftTime;
                        if(plan.hasConsumers) for(var c : plan.consumers) c.display(s);
                        if(plan.heatRequirement > 0f){
                            s.add(Stat.input, plan.heatRequirement, StatUnit.heatUnits);
                            s.add(Stat.maxEfficiency, (int)(plan.maxHeatEfficiency * 100f), StatUnit.percent);
                        }
                        LPUi.statToTable(s, from);
                    }).left().pad(6);
                    info.row();
                    info.table(to -> {
                        if(plan.heatOutput > 0f){
                            StatValues.number(plan.heatOutput, StatUnit.heatUnits).display(to);
                        }
                        if(plan.powerProduction > 0f){
                            StatValues.number(plan.powerProduction * 60f, StatUnit.powerSecond).display(to);
                        }
                        if(plan.outputItems.length > 0){
                            StatValues.items(plan.craftTime, plan.outputItems).display(to);
                        }
                        if(plan.outputLiquids.length > 0){
                            StatValues.liquids(1f, plan.outputLiquids).display(to);
                        }
                    }).left().pad(6);
                }).grow().left().pad(5);
                button.setStyle(Styles.clearNoneTogglei);
                button.changed(() -> build.configure(new int[]{build.rotation, owner.craftPlans.indexOf(plan)}));
                button.update(() -> button.setChecked(build.craftPlan == plan));
                cont.add(button);
                cont.row();
            }
        };

        rebuild.run();

        Table main = new Table().background(Styles.black6);
        main.add(rot).left().row();

        ScrollPane pane = new ScrollPane(cont, Styles.smallPane);
        pane.setScrollingDisabled(true, false);
        pane.setScrollYForce(owner.selectScroll);
        pane.update(() -> owner.selectScroll = pane.getScrollY());
        pane.setOverscroll(false, false);
        main.add(pane).maxHeight(100 * owner.maxList);
        table.top().add(main);
    }

    /** 为 Building 渲染当前配方的所有状态条（方块公共条 + 配方私有条） */
    public static void buildRecipeBars(MultiCrafter owner, MultiCrafter.MultiCrafterBuild build, Table table){
        table.clear();
        for(Func<Building, Bar> bar : owner.listBars()){
            Bar result = bar.get(build);
            if(result != null){
                table.add(result).growX();
                table.row();
            }
        }
        if(build.craftPlan == null || !build.craftPlan.hasBars()) return;
        for(Func<Building, Bar> bar : build.craftPlan.listBars()){
            Bar result = bar.get(build);
            if(result == null) continue;
            table.add(result).growX();
            table.row();
        }
    }

    /** 显示当前配方所需要的输入资源（选中方块时的提示面板） */
    public static void buildConsumption(MultiCrafter.MultiCrafterBuild build, Table table){
        if(build.craftPlan == null) return;
        table.left();
        MultiCrafter.CraftPlan[] last = {build.craftPlan};
        table.table(t -> {
            table.update(() -> {
                if(last[0] != build.craftPlan){
                    rebuildConsumption(build, t);
                    last[0] = build.craftPlan;
                }
            });
            rebuildConsumption(build, t);
        });
    }

    private static void rebuildConsumption(MultiCrafter.MultiCrafterBuild build, Table table){
        table.clear();
        for(var cons : build.craftPlan.consumers){
            if(!cons.optional || !cons.booster){
                cons.build(build, table);
            }
        }
    }

    /** ItemAmmoTurret手动重新装填按钮 */
    public static void buildReloadButton(ItemAmmoTurret.ItemAmmoTurretBuild build, Table table){
        if(build == null || table == null) return;

        float size = build.block.size * tilesize;
        float buttonSize = Math.max(size, 64f);

        ImageButton button = new ImageButton(Styles.clearTogglei);
        button.setSize(buttonSize, buttonSize);

        Image icon = button.image(ammoTurretReloadIcon).size(buttonSize).color(Color.white).get();

        button.update(() -> {
            boolean enabled = build.totalAmmoUnits > 0;
            button.setDisabled(!enabled);
            if(!enabled){
                icon.setColor(Color.gray);
            } else {
                icon.setColor(Color.white);
            }
        });

        button.clicked(() -> {
            if(build.totalAmmoUnits > 0){
                build.manualReload();
                button.setChecked(false);
            }
        });

        table.add(button).size(buttonSize).pad(0f);
    }
}
