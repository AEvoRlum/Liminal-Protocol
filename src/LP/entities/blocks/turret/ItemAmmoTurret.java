package LP.entities.blocks.turret;

import arc.*;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.math.Mathf;
import mindustry.game.EventType.Trigger;
import mindustry.gen.Building;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.type.*;
import mindustry.world.meta.*;
import mindustry.ui.Bar;

import java.lang.reflect.Constructor;

import static mindustry.Vars.*;

import LP.content.LPStats;
import LP.ui.LPUi;

public class ItemAmmoTurret extends LPItemTurret {
    /** 弹药单位数量上限 */
    public int maxAmmoUnits = 10;
    /** 每次开火消耗的弹药单位数量 */
    public int ammoUnitCost = 1;
    /** 开火间隔 */
    public float interval = 180f;

    public ItemAmmoTurret(String name){
        super(name);
        update = true;
        configurable = true;
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("ammo");
        removeBar("activationtimer");
        removeBar("items");

        if(maxAmmoUnits > 0){
            addBar("ammoUnits",  (ItemAmmoTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.lp-ammounits", (int)entity.maxAmmoUnits, (int)entity.totalAmmoUnits),
                () -> Pal.ammo,
                () -> (float)entity.totalAmmoUnits / entity.maxAmmoUnits
            ));
        }

        addBar("itemcount", (ItemAmmoTurretBuild entity) -> {
            return new Bar(
                () -> Core.bundle.format("bar.lp-itemcount", (int)entity.totalAmmo, (int)maxAmmo),
                () -> Pal.ammo,
                () -> (float)entity.totalAmmo / maxAmmo
            );
        });

        addBar("shotinterval", (ItemAmmoTurretBuild entity) -> {
            return new Bar(
                () -> Core.bundle.format("bar.lp-shotinterval", (int)(entity.shotIntervalRemainingBar)),
                () -> Pal.lightOrange,
                () -> entity.shotIntervalBar
            );
        });
    }

    @Override
    public void setStats(){
        super.setStats();
        
        stats.remove(Stat.reload);
        stats.add(Stat.reload, 60f / (interval + (!reloadWhileCharging ? shoot.firstShotDelay : 0f)) * shoot.shots, StatUnit.perSecond);
        
        stats.add(LPStats.itemAmmoTurretReload, reload / 60f, StatUnit.seconds);
        stats.add(LPStats.itemAmmoTurretShotInterval, interval / 60f, StatUnit.seconds);
        stats.add(LPStats.itemAmmoTurretMaxAmount, maxAmmoUnits, StatUnit.shots);
        stats.add(LPStats.itemAmmoTurretAmmoUnitCost, ammoUnitCost, StatUnit.shots);
    }

    @Override
    public void init(){
        consume(new ConsumeItemFilter(i -> ammoTypes.containsKey(i)){
            @Override
            public void build(Building build, Table table){
            }

            @Override
            public float efficiency(Building build){
                return 1f;
            }

            @Override
            public void display(Stats stats){
            }
        });

        super.init();
    }

    public class ItemAmmoTurretBuild extends ItemTurretBuild {
        public int maxAmmoUnits;
        public int ammoUnitCost;
        public int totalAmmoUnits;
        public BulletType currentAmmoType;
        public float shotCounter;
        public float shotIntervalRemaining;
        public float shotIntervalRemainingBar;
        public float shotIntervalBar;

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();
            maxAmmoUnits = ((ItemAmmoTurret)block).maxAmmoUnits;
            ammoUnitCost = ((ItemAmmoTurret)block).ammoUnitCost;
            if(totalAmmoUnits < 0){
                totalAmmoUnits = 0;
            }
            if(cheating() && totalAmmoUnits <= 0){
                totalAmmoUnits = maxAmmoUnits;
            }
            if(currentAmmoType == null && !ammoTypes.isEmpty()){
                currentAmmoType = ammoTypes.values().iterator().next();
            }
        }

        @Override
        protected boolean canHeal(){
            return targetHealing && hasAmmo() && (peekAmmo().collidesTeam || peekAmmo().scaleLife) && peekAmmo().heals();
        }

        @Override
        protected void findTarget(){
            super.findTarget();
            
            if(target == null && canHeal()){
                float Range = range();
                Unit allyUnit = Units.closest(team, x, y, Range, u -> u != null && u != unit && u.damaged());
                if(allyUnit != null){
                    target = allyUnit;
                }
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();
            float interval = ((ItemAmmoTurret)block).interval;

            if(totalAmmoUnits > 0){
                shotCounter += delta() * ammoReloadMultiplier() * Math.max(baseReloadSpeed(), potentialEfficiency);
                shotCounter = Math.min(shotCounter, interval);
                shotIntervalRemaining = Math.max(0, (interval - shotCounter));
                shotIntervalBar = totalAmmoUnits > 0 ? Mathf.clamp(shotCounter / interval) : 0f;
            } else {
                shotCounter = 0f;
                shotIntervalRemaining = 0f;
                shotIntervalBar = 0f;
            }

            if (shotIntervalRemaining > 0){
                shotIntervalRemainingBar = shotIntervalRemaining / 60f;
            } else if (shotIntervalBar == 1f){
                shotIntervalRemainingBar = 0f;
            } else {
                shotIntervalRemainingBar = interval / 60f;
            }
        }

        @Override
        public void buildConfiguration(Table table){
            LPUi.buildReloadButton(this, table);
        }

        @Override
        public boolean hasAmmo(){

            if(totalAmmoUnits <= 0 || currentAmmoType == null) return false;
            if(!canConsume()) return false;
            return true;
        }

        @Override
        public BulletType useAmmo(){
            return null;
        }

        @Override
        public BulletType peekAmmo(){
            return currentAmmoType;
        }

        @Override
        public float estimateDps(){
            if(!hasAmmo()) return 0f;
            float interval = ((ItemAmmoTurret)block).interval;
            return shoot.shots / interval * 60f * (peekAmmo() == null ? 0f : peekAmmo().estimateDPS()) * potentialEfficiency * timeScale;
        }

        @Override
        public float progress(){
            return totalAmmoUnits <= 0 ? Mathf.clamp(reloadCounter / reload) : 0f;
        }

        @Override
        public boolean shouldConsume(){
            return isShooting() || (totalAmmoUnits <= 0 && reloadCounter < reload);
        }

        @Override
        protected void updateReload(){

            if(totalAmmoUnits <= 0){
                reloadCounter += delta() * ammoReloadMultiplier() * baseReloadSpeed();
                reloadCounter = Math.min(reloadCounter, reload);

                if(reloadCounter >= reload){
                    if(totalAmmo >= ammoPerShot && !ammo.isEmpty()){
                        ItemEntry entry = (ItemEntry)ammo.peek();
                        int consumeAmount = ammoPerShot;
                        BulletType type = entry.type();

                        if(entry.amount >= consumeAmount){
                            entry.amount -= consumeAmount;
                            if(entry.amount <= 0){
                                ammo.pop();
                                if(ammo.isEmpty() && currentAmmoType != null){
                                    Item item = null;
                                    for(var e : ammoTypes.entries()){
                                        if(e.value == currentAmmoType){
                                            item = e.key;
                                            break;
                                        }
                                    }
                                    if(item == null && !ammoTypes.isEmpty()){
                                        item = ammoTypes.keys().next();
                                    }
                                    if(item != null){
                                        ammo.add(createItemEntry(ItemAmmoTurret.this, item, 0));
                                    }
                                }
                            }
                            totalAmmo -= consumeAmount;
                            totalAmmo = Math.max(totalAmmo, 0);

                            totalAmmoUnits = maxAmmoUnits;
                            if(type != null) currentAmmoType = type;
                            reloadCounter = 0f;
                            Events.fire(Trigger.resupplyTurret);
                        }
                    }
                }
            }
        }

        @Override
        protected void updateShooting(){
            float interval = ((ItemAmmoTurret)block).interval;

            if(totalAmmoUnits > 0 && currentAmmoType != null && shotCounter >= interval && !charging() && shootWarmup >= minWarmup){
                shoot(currentAmmoType);
                totalAmmoUnits -= ammoUnitCost;
                if(totalAmmoUnits < 0) totalAmmoUnits = 0;
                shotCounter %= interval;
            }
        }

        @Override
        protected void updateCooling(){
            if(coolant != null && coolant.efficiency(this) > 0 && efficiency > 0){
                float capacity = coolant instanceof ConsumeLiquidFilter filter ? filter.getConsumed(this).heatCapacity : (coolant.consumes(liquids.current()) ? liquids.current().heatCapacity : 0.4f);
                float amount = coolant.amount * coolant.efficiency(this);
                coolant.update(this);
                float interval = ((ItemAmmoTurret)block).interval;
                
                if(totalAmmoUnits > 0){
                    shotCounter += amount * edelta() * capacity * coolantMultiplier * ammoReloadMultiplier();
                    shotCounter = Math.min(shotCounter, interval);
                } else {
                    reloadCounter += amount * edelta() * capacity * coolantMultiplier * ammoReloadMultiplier();
                    reloadCounter = Math.min(reloadCounter, reload);
                }

                if(Mathf.chance(0.06 * amount)){
                    coolEffect.at(x + Mathf.range(size * tilesize / 2f), y + Mathf.range(size * tilesize / 2f));
                }
            }
        }

        @Override
        public void handleItem(Building source, Item item){
            super.handleItem(source, item);
        }

        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            if(ammoTypes.get(item) == null) return 0;
            return Math.min(maxAmmo - totalAmmo, amount);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return ammoTypes.get(item) != null && totalAmmo < maxAmmo;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(totalAmmoUnits);
            write.f(shotCounter);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            totalAmmoUnits = read.s();
            if(revision >= 1){
                shotCounter = read.f();
            }
        }

        @Override
        public byte version(){
            return 3;
        }

        public void manualReload(){
            if(totalAmmoUnits <= 0) return;

            totalAmmoUnits = 0;
            shotCounter = 0f;
            shotIntervalRemaining = 0f;
            shotIntervalBar = 0f;
            reloadCounter = 0f;
            Events.fire(Trigger.resupplyTurret);
        }
    }

    private static ItemEntry createItemEntry(ItemTurret turret, Item item, int amount){
        try{
            Constructor<?> constructor = ItemEntry.class.getDeclaredConstructor(ItemTurret.class, Item.class, int.class);
            constructor.setAccessible(true);
            return (ItemEntry) constructor.newInstance(turret, item, amount);
        }catch(Exception e){
            throw new RuntimeException("Failed to create ItemEntry", e);
        }
    }
}