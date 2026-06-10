package LP.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.event.*;
import arc.scene.ui.Tooltip.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.defense.turrets.*;

import static mindustry.Vars.*;
import LP.entities.bullets.*;

public class LPStatValues {
    public static StatValue string(String value, Object... args){
        String result = Strings.format(value, args);
        return table -> table.add(result);
    }

    public static StatValue bool(boolean value){
        return table ->  table.add(!value ? "@no" : "@yes");
    }

    public static String fixValue(float value){
        return Strings.autoFixed(value, 3);
    }

    private static String ammoStat(float val){
        return (val > 0 ? "[stat]+" : "[negstat]") + Strings.autoFixed(val, 1);
    }

    private static TextureRegion icon(UnlockableContent t){
        return t.uiIcon;
    }

    private static Cell<?> note(Table table, String text){
        table.row();
        return table.table(t -> {
            t.image(Icon.arrowNoteSmall.getRegion()).size(15).color(Pal.stat).scaling(Scaling.fit).padRight(6).padLeft(12);
            t.add(text);
        });
    }

    public static StatValue squared(float value, StatUnit unit){
        return table -> {
            String fixed = fixValue(value);
            table.add(fixed + "x" + fixed);
            table.add((unit.space ? " " : "") + unit.localized());
        };
    }

    private static Cell<?> sep(Table table, String text){
        table.row();
        return table.add(text);
    }

    public static <T extends Element> T withTooltip(T element, UnlockableContent content){
        return withTooltip(element, content, false);
    }

        public static <T extends Element> T withTooltip(T element, UnlockableContent content, boolean tooltip){
        if(content != null){
            if(!mobile){
                if(tooltip){
                    element.addListener(Tooltips.getInstance().create(content.localizedName, mobile));
                }
                element.addListener(new HandCursorListener(() -> !content.isHidden(), true));
            }
            element.clicked(() -> {
                if(!content.isHidden()){
                    Vars.ui.content.show(content);
                }
            });
        }
        return element;
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map){
        return ammo(map, false, false, null);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, boolean showUnit){
        return ammo(map, false, showUnit, null);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, String blockName){
        return ammo(map, false, false, blockName);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, boolean showUnit, String blockName){
        return ammo(map, false, showUnit, blockName);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, boolean nested, boolean showUnit){
        return ammo(map, nested, showUnit, null);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, boolean nested, boolean showUnit, @Nullable String blockName){
        return table -> {

            table.row();

            var orderedKeys = map.keys().toSeq();
            orderedKeys.sort();

            for(T t : orderedKeys){
                boolean compact = t instanceof UnitType && !showUnit || nested;

                BulletType type = map.get(t);

                if(type.spawnUnit != null && type.spawnUnit.weapons.size > 0){
                    ammo(ObjectMap.of(t, type.spawnUnit.weapons.first().bullet), nested, false, blockName).display(table);
                    continue;
                }

                table.table(Styles.grayPanel, bt -> {
                    bt.left().top().defaults().padRight(3).left();
                    //no point in displaying unit icon twice
                    if(!compact && !(t instanceof Turret)){
                        bt.table(title -> {
                            title.image(icon(t)).size(3 * 8).padRight(4).right().scaling(Scaling.fit).top().with(i -> withTooltip(i, t, false));

                            title.add(t.localizedName).padRight(10).left().top();

                            if(type.displayAmmoMultiplier && type.statLiquidConsumed > 0f){
                                title.add("[stat]" + fixValue(type.statLiquidConsumed / type.ammoMultiplier * 60f) + " [lightgray]" + StatUnit.perSecond.localized());
                            }
                        });
                        bt.row();
                    }

                    if(blockName != null && t instanceof UnlockableContent){
                        UnlockableContent content = (UnlockableContent) t;
                        String key = "block." + blockName + "." + content.name + ".info";
                        if(Core.bundle.has(key)){
                            bt.table(desc -> {
                                desc.image(Icon.info.getRegion()).size(20).color(Color.lightGray).scaling(Scaling.fit).padRight(8).padLeft(12);
                                desc.add("[lightgray]" + Core.bundle.get(key));
                            });
                            
                            bt.row();
                            bt.add().height(10f);
                            bt.row();
                        }
                    }

                    if(type.damage > 0 && (type.collides || type.splashDamage <= 0)){
                        bt.add(Core.bundle.format("bullet.damage", type.damage) + (type.continuousDamage() > 0 ? 
                        "[lightgray] ~ [stat]" + Core.bundle.format("bullet.damage", type.continuousDamage()) + StatUnit.perSecond.localized() : ""));
                    }

                    if(type instanceof LightningLinkBulletType b && b.lightningLinkDamage > 0){
                        float range = b.linkRange / tilesize;
                        float freq = 60 / b.hitSpacing;
                        String damageStr = b.lightningLinkDamage == (int)b.lightningLinkDamage ? String.valueOf((int)b.lightningLinkDamage) : String.valueOf(b.lightningLinkDamage);
                        String rangeStr = range == (int)range ? String.valueOf((int)range) : Strings.fixed(range, 2);
                        String freqStr = freq == (int)freq ? String.valueOf((int)freq) : Strings.fixed(freq, 3);
                        sep(bt, Core.bundle.format("bullet.lp-lightningLinkDamage", damageStr, String.valueOf((int)b.maxHit), rangeStr, freqStr));
                    }

                    if(type instanceof EnergyBulletType b && b.energyDamage > 0){
                        sep(bt, Core.bundle.format("bullet.lp-energyDamage", b.energyDamage, Strings.fixed(b.shieldEnergyDamageMultiplier * 100, 1)));
                    }

                    if(type instanceof ChainBulletType b && b.maxHit > 0){
                        sep(bt, Core.bundle.format("bullet.lp-maxHit", (int)b.maxHit));
                    }

                    if(type.buildingDamageMultiplier != 1){
                        sep(bt, Core.bundle.format("bullet.buildingdamage", ammoStat((int)(type.buildingDamageMultiplier * 100 - 100))));
                    }

                    if(type.rangeChange != 0 && !compact){
                        sep(bt, Core.bundle.format("bullet.range", ammoStat(type.rangeChange / tilesize)));
                    }

                    if(type.shieldDamageMultiplier != 1){
                        sep(bt, Core.bundle.format("bullet.shielddamage", ammoStat((int)(type.shieldDamageMultiplier * 100 - 100))));
                    }

                    if(type.splashDamage > 0){
                        sep(bt, Core.bundle.format("bullet.lp-splashdamage", (int)type.splashDamage, Strings.fixed(type.splashDamageRadius / tilesize, 1)));
                    }

                    if(type.statLiquidConsumed <= 0f && !compact && !Mathf.equal(type.ammoMultiplier, 1f) && type.displayAmmoMultiplier && (!(t instanceof Turret turret) || turret.displayAmmoMultiplier)){
                        sep(bt, Core.bundle.format("bullet.multiplier", (int)type.ammoMultiplier));
                    }

                    if(!compact && !Mathf.equal(type.reloadMultiplier, 1f)){
                        int val = (int)(type.reloadMultiplier * 100 - 100);
                        sep(bt, Core.bundle.format("bullet.reload", ammoStat(val)));
                    }

                    if(type.knockback > 0){
                        sep(bt, Core.bundle.format("bullet.knockback", Strings.autoFixed(type.knockback, 2)));
                    }

                    if(type instanceof SplashKnockbackBulletType b && b.splashKnockback > 0){
                        sep(bt, Core.bundle.format("bullet.lp-splashknockback", b.splashKnockback, Strings.fixed(b.splashKnockbackRadius / tilesize, 1)));
                    }

                    if(type.healPercent > 0f){
                        sep(bt, Core.bundle.format("bullet.healpercent", Strings.autoFixed(type.healPercent, 2)));
                    }

                    if(type.healAmount > 0f){
                        sep(bt, Core.bundle.format("bullet.healamount", Strings.autoFixed(type.healAmount, 2)));
                    }

                    if(type.pierce || type.pierceCap != -1){
                        sep(bt, type.pierceCap == -1 ? "@bullet.infinitepierce" : Core.bundle.format("bullet.pierce", type.pierceCap));
                    }

                    if(type.incendAmount > 0){
                        sep(bt, "@bullet.incendiary");
                    }

                    if(type.homingPower > 0.01f){
                        sep(bt, "@bullet.homing");
                    }

                    if(type.lightning > 0){
                        sep(bt, Core.bundle.format("bullet.lightning", type.lightning, type.lightningDamage < 0 ? type.damage : type.lightningDamage));
                    }

                    if(type instanceof LaserBulletType b && b.lightningSpacing > 0){
                        int count = (int)(b.length / b.lightningSpacing) * 2 + 2;
                        float damage = b.lightningDamage < 0 ? b.damage : b.lightningDamage;
                        sep(bt, Core.bundle.format("bullet.lightning", count, damage));
                        note(bt, Core.bundle.format("bullet.lightninginterval", Strings.autoFixed(b.lightningSpacing / tilesize, 2), Strings.autoFixed(b.lightningLength, 2))).left();
                    }

                    if(type instanceof EmpBulletType b && b.radius > 0f){
                        sep(bt, Core.bundle.format("bullet.empradius", Strings.fixed(b.radius / tilesize, 1)));
                        if(b.timeDuration > 0f && b.timeIncrease > 1f){
                            sep(bt, Core.bundle.format("bullet.empboost", Strings.autoFixed(b.timeIncrease * 100f, 2),
                            Strings.autoFixed(b.timeDuration / 60f, 1)) + " " + StatUnit.seconds.localized());
                        }
                        if(b.timeDuration > 0f && b.powerSclDecrease < 1f){
                            sep(bt, Core.bundle.format("bullet.empslowdown", 
                            (b.powerSclDecrease < 1f ? "[negstat]" : "") + Strings.autoFixed((b.powerSclDecrease - 1f) * 100f, 2),
                            Strings.autoFixed(b.timeDuration / 60f, 1)) + " " + StatUnit.seconds.localized());
                        }
                        if(!Mathf.equal(b.powerDamageScl, 1f)){
                            sep(bt, Core.bundle.format("bullet.empdamage", Strings.autoFixed(b.powerDamageScl * 100f, 2)));
                        }
                        if(b.hitUnits){
                            sep(bt, Core.bundle.format("bullet.empunitdamage",
                            (b.unitDamageScl < 1f ? "[negstat]" : "") + Strings.autoFixed(b.unitDamageScl * 100f, 2)));
                        }
                    }

                    if(type.pierceArmor){
                        sep(bt, "@bullet.armorpierce");
                    }

                    if(!type.pierceArmor){
                        if(type.armorMultiplier != 1f){
                            if(type.armorMultiplier > 1f){
                                sep(bt, Core.bundle.format("bullet.armorweakness", (type.armorMultiplier)));
                            }else if(Mathf.sign(type.armorMultiplier) == 1){
                                sep(bt, Core.bundle.format("bullet.partialarmorpierce", (int)((1 - type.armorMultiplier) * 100)));
                            }else{
                                sep(bt, Core.bundle.format("bullet.antiarmor", (-type.armorMultiplier)));
                            }
                        }

                        if(type.blockArmorMultiplier != 1f){
                            if(type.blockArmorMultiplier > 1f){
                                sep(bt, Core.bundle.format("bullet.blockarmorweakness", (type.blockArmorMultiplier)));
                            }else if(Mathf.sign(type.blockArmorMultiplier) == 1){
                                sep(bt, Core.bundle.format("bullet.blockpartialarmorpierce", (int)((1 - type.blockArmorMultiplier) * 100)));
                            }else{
                                sep(bt, Core.bundle.format("bullet.blockantiarmor", (-type.blockArmorMultiplier)));
                            }
                        }
                    }

                    if(type.maxDamageFraction > 0){
                        sep(bt, Core.bundle.format("bullet.maxdamagefraction", (int)(type.maxDamageFraction * 100)));
                    }

                    if(type.suppressionRange > 0){
                        sep(bt, Core.bundle.format("bullet.suppression", Strings.autoFixed(type.suppressionDuration / 60f, 2), Strings.fixed(type.suppressionRange / tilesize, 1)));
                    }

                    if(type.status != StatusEffects.none){
                        sep(bt, (type.status.hasEmoji() ? type.status.emoji() : "") + "[stat]" + type.status.localizedName + (type.status.reactive ? "" : "[lightgray] ~ [stat]" +
                            Strings.autoFixed(type.statusDuration / 60f, 1) + "[lightgray] " + Core.bundle.get("unit.seconds"))).with(c -> withTooltip(c, type.status));
                    }

                    if(!type.targetMissiles){
                        sep(bt, "@bullet.notargetsmissiles");
                    }

                    if(!type.targetBlocks){
                        sep(bt, "@bullet.notargetsbuildings");
                    }

                    if(type.intervalBullet != null){
                        bt.row();

                        Table ic = new Table();
                        ammo(ObjectMap.of(t, type.intervalBullet), true, false).display(ic);
                        Collapser coll = new Collapser(ic, true);
                        coll.setDuration(0.1f);

                        bt.table(it -> {
                            it.left().defaults().left();

                            it.add(Core.bundle.format("bullet.interval", Strings.autoFixed(type.intervalBullets / type.bulletInterval * 60, 2)));
                            it.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bt.row();
                        bt.add(coll);
                    }

                    if(type.fragBullet != null){
                        bt.row();

                        Table fc = new Table();
                        ammo(ObjectMap.of(t, type.fragBullet), true, false).display(fc);
                        Collapser coll = new Collapser(fc, true);
                        coll.setDuration(0.1f);

                        bt.table(ft -> {
                            ft.left().defaults().left();

                            ft.add(Core.bundle.format("bullet.frags", type.fragBullets));
                            ft.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bt.row();
                        bt.add(coll);
                    }

                    if(type.spawnBullets != null && type.spawnBullets.size > 0){
                        bt.row();

                        Table sc = new Table();
                        for(BulletType spawn : type.spawnBullets){
                            if(spawn.showStats) ammo(ObjectMap.of(t, spawn), true, false).display(sc);
                        }
                        Collapser coll = new Collapser(sc, true);
                        coll.setDuration(0.1f);

                        bt.table(st -> {
                            st.left().defaults().left();

                            st.add(Core.bundle.format("bullet.spawnBullets", type.spawnBullets.size));
                            if(sc.getChildren().size > 0) st.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bt.row();
                        bt.add(coll);
                    }

                }).padLeft(5).padTop(5).padBottom(compact ? 0 : 5).growX().margin(compact ? 0 : 10);
                table.row();
            }
        };
    }
}
