package LP.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.content.Liquids;
import mindustry.entities.part.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.*;

import LP.graphics.LPPal;
import LP.entities.blocks.*;
import LP.entities.bullets.*;

public class LPBlocks {
    //turret
    public static Block lucenser, disflux, impactor, repulstar, radiance, meteor, cloudpiercer;

    //production
    public static Block jynDrill, shearDrill, impactDrill0;

    //distribution
    public static Block jynDuct, jynDuctbridge, jynSorter, jynInvertedSorter, jynOverflow, jynUnderflow, jynUnloader;
    public static Block litConveyor, litBridgeConveyor, litJunction, litUnloader;

    //liquid
    public static Block masConduit, masLiquidBridgeConduit, masLiquidJunction, masLiquidRouter, traConduit, traBridgeConduit;
    public static Block masPump, masPumpHighSpeed;
    public static Block massisteelLiquidStorage , massisteelLiquidStorageLarge;

    //power
    public static Block jynPowerNode, jynPowerNodeLarge, jynBattery, heavyIonChamber;

    //wall
    public static Block jynWall, jynWallLarge, masWall, masWallLarge, traWall, traWallLarge, ttfWall;

    //craft
    public static Block masHeatRedirector, masHeatRedirectorSmall, masHeatRouter, masHeatRouterSmall, masSlagHeater;
    public static Block ionopolymerCrucible, ionopolymerCrucibleLarge, erocrysExtractory, transChimericFoundry, highSpeedTranschimericFoundry;

    //unit
    //storage
    public static Block pioneers, jynVault;

    //logic
    //environment
    public static Block jynSteelOre, massisteelOre, erocrysOre, litelnlayOre;

    public static Block coreFloor, darkStone, darkStoneRubble, rhyoliticLimestone, rhyoliticRubble, 
    relicfloor, relicfloor2, relicfloor3, relicfloor4, relicfloor5, relicfloor6, reliclines, reliclines2, relictiles, relictiles2, 
    reliclinesLines, reliclinesLinesTra, reliclinesLinesRup, reliclinesLinesRdo, reliclinesLinesLup, reliclinesLinesLdo;

    public static Block darkStoneRock;

    public static Block darkStoneWall, rhyoliticLimestoneWall, relicWall, relicWall2, reliclinesWall, reliclinesWall2, darkStoneCrystal, erocrysCrystal;

    //Process
    public static Block process1, process2, process3, process4;

    public static void load(){
        //turret
        lucenser = new ItemTurret("lucenser"){{
            size = 2;
            health = 147;
            armor = 4;
            requirements(Category.turret, with(LPItems.jynsteel, 73, LPItems.erocrys, 26));
            rotateSpeed = 4;
            reload = 60f;
            range = 160f;
            recoil = 1.4f;
            recoilTime = 40f;
            shake = 2f;
            shootCone = 1f;
            cooldownTime = 90f;
            heatColor = LPPal.redDark;
            outlineColor = LPPal.outline;
            shootSound = LPSounds.shootArtillerySapBig;
            maxAmmo = 20;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            buildTime = 120f;
            databaseTag = "cj";
            destroySound = LPSounds.blockExplode2Alt;
            destroyEffect = LPFx.lucenserDestroy;
            shootY = 4f;
            ammo(
                LPItems.jynsteel, new BasicBulletType(){{
                    sprite = "lp-pierce";
                    width = 4f;
                    height = 4f;
                    shrinkY = 0f;
                    hitColor = lightColor = frontColor = backColor = trailColor = LPPal.aureus;
                    trailWidth = 1f;
                    trailLength = 6;
                    ammoMultiplier = 1f;
                    armorMultiplier = 0.65f;
                    damage = 54f;
                    speed = 8f;
                    lifetime = 20f;
                    rangeOverride = 160f;
                    shootEffect = LPFx.lucenserJynsteelShoot;
                    hitSound = LPSounds.explosionCrawler;
                    hitShake = 3f;
                    hitEffect = LPFx.lucenserJynsteelHit;
                    despawnEffect = LPFx.lucenserJynsteelDespawn;
                }},

                LPItems.erocrys, new BasicBulletType(){{
                    sprite = "lp-crystal";
                    width = 4f;
                    height = 4f;
                    shrinkY = 0f;
                    hitColor = lightColor = frontColor = backColor = trailColor = LPPal.orange;
                    trailWidth = 1f;
                    trailLength = 6;
                    ammoMultiplier = 1f;
                    armorMultiplier = 0.8f;
                    damage = 34f;
                    speed = 8f;
                    lifetime = 20f;
                    rangeOverride = 160f;
                    shootEffect = LPFx.lucenserErocrysShoot;
                    hitSound = LPSounds.acceleratorLightning1;
                    hitShake = 3f;
                    hitEffect = LPFx.lucenserErocrysHit;
                    despawnEffect = LPFx.lucenserErocrysDespawn;
                    despawnHit = false;
                    fragOnHit = true;
                    fragOnDespawn = false;
                    fragBullets = 2;
                    fragBullet = new LightningBulletType(){{
                        lightningColor = LPPal.orange;
                        damage = 8;
                        lightningLength = 4;
                        lightningLengthRand = 6;
                        despawnEffect = Fx.none;
                        hitColor = LPPal.orange;
                        hitEffect = Fx.none;
                        status = StatusEffects.none;
                    }};
                }}
            );
        }};

        disflux = new ChainItemTurret("disflux"){{
            size = 2;
            health = 154;
            armor = 3;
            requirements(Category.turret, with(LPItems.jynsteel, 103));
            rotateSpeed = 4;
            reload = 80f;
            range = 148f;
            recoil = 1f;
            recoilTime = 60f;
            shake = 2f;
            shootCone = 1f;
            cooldownTime = 90f;
            heatColor = LPPal.redDark;
            outlineColor = LPPal.outline;
            shootSound = Sounds.shootArc;
            maxAmmo = 12;
            ammoPerShot = 4;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            buildTime = 120f;
            databaseTag = "gr";
            destroySound = LPSounds.blockExplode2;
            destroyEffect = LPFx.disfluxDestroy;
            shootY = 3f;
            ammo(LPItems.erocrys, new ChainBulletType(36f){{
                maxHit = 2;
                chainRange = 32f;
                maxRange = length = 148f;
                boltWidth = 1.5f;
                hitColor = lightColor = LPPal.orange;
                ammoMultiplier = 1f;
                armorMultiplier = 4f;
                shieldDamageMultiplier = 3f;
                buildingDamageMultiplier = 0.45f;
                status = LPStatusEffect.empI;
                statusDuration = 120f;
                hitShake = 2;
                hitSound = Sounds.shootArc;
                despawnShake = 2;
                shootEffect = LPFx.disfluxShoot;
                hitEffect = LPFx.disfluxHit;
                smokeEffect = Fx.none;
                fragBullets = 1;
                fragOffsetMax = 0f;
                fragOffsetMin = 0f;
                fragBullet = new EmpBulletType(){{
                    hitUnits = true;
                    instantDisappear = true;
                    armorMultiplier = 5.5f;
                    shieldDamageMultiplier = 2f;
                    radius = 64f;
                    speed = 0f;
                    damage = 4; 
                    status = LPStatusEffect.empI;
                    statusDuration = 60f;
                    powerSclDecrease = 0.8f;
                    powerDamageScl = 0.8f;
                    unitDamageScl = 0.65f;
                    timeIncrease = 1;
                    timeDuration = 0;
                    hitColor = LPPal.orange;
                    hitSound = Sounds.shootArc;
                    despawnEffect = Fx.none;
                    chainEffect = Fx.chainLightning;
                    hitPowerEffect = LPFx.disfluxHit;
                    applyEffect = LPFx.disfluxHit;
                }};
            }});
        }};

        impactor = new ItemTurret("impactor"){{
            size = 3;
            health = 244;
            armor = 7;
            requirements(Category.turret, with(LPItems.jynsteel, 133, LPItems.massisteel, 76, LPItems.erocrys, 33));
            reload = 80f;
            range = 224f;
            recoil = 2f;    
            recoilTime = 30f;
            shake = 3f;
            shootCone = 1f;
            cooldownTime = 90f;
            heatColor = LPPal.redDark;
            outlineColor = LPPal.outline;
            shootSound = LPSounds.martianCrash;
            maxAmmo = 24;
            ammoPerShot = 3;
            consumePower(1.5f);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            buildTime = 120f;
            databaseTag = "zd";
            destroySound = LPSounds.blockExplode2Alt;
            destroyEffect = LPFx.impactorDestroy;
            shootY = 2.5f;
            drawer = new DrawTurret(){{
                parts = Seq.with(
                    new FlarePart(){{
                        y = 2.5f;
                        followRotation = true;
                        color1 = LPPal.aureus.cpy().a(0.8f);
                        color2 = LPPal.aureus;
                        sides = 2;
                        radius = 0f;
                        radiusTo = 32f;
                        stroke = 1.6f;
                        spinSpeed = 0f;
                        rotation = 90f;
                        innerRadScl = 0.4f;
                        innerScl = 0.5f;
                        layer = 110f;
                        progress = PartProgress.recoil;
                    }},

                    new HaloPart(){{
                        y = 2.5f;
                        tri = true;
                        color = colorTo = LPPal.aureus;
                        shapes = 1;
                        radius = 0f;
                        radiusTo = 5f;
                        triLength = 32f;
                        triLengthTo = 32f;
                        haloRadius = 0f;
                        layer = 110f;
                        progress = PartProgress.recoil;
                    }},

                    new HaloPart(){{
                        y = 2.5f;
                        tri = true;
                        color = colorTo = LPPal.aureus;
                        shapes = 1;
                        radius = 0f;
                        radiusTo = 5f;
                        triLength = 16f;
                        triLengthTo = 16f;
                        shapeRotation = 180f;
                        haloRadius = 0f;
                        layer = 110f;
                        progress = PartProgress.recoil;
                    }}
                );
            }};
            ammo(
                LPItems.massisteel, new ImpactBulletType(72f, 12f, 22.4f, 10f, 3f, 2f){{
                    rangeOverride = 224f;
                    sprite = "lp-crystal";
                    width = 12;
                    height = 18;
                    shrinkY = 0f;
                    hitColor = lightColor = frontColor = backColor = trailColor = LPPal.aureus;
                    trailWidth = 3;
                    trailLength = 10;
                    ammoMultiplier =  1f;
                    armorMultiplier = 1.25f;
                    shieldDamageMultiplier = 1.45f;
                    buildingDamageMultiplier = 1.15f;
                    shootEffect = LPFx.impactorShoot;
                    hitSound = Sounds.explosionQuad;
                    hitShake = 6;
                    despawnEffect = hitEffect = LPFx.impactorHit;
                    despawnEffect = Fx.none;
                    subBulletWidth = 6f;
                    subBulletHeight = 9f;
                    subTrailWidth = 2f;
                    subTrailLength = 12;
                    subHitEffect = LPFx.impactorHitSmall;
                }}
            );
        }};

        //production
        jynDrill = new Drill("jyn-drill"){{
            size = 3;
            health = 87;
            tier = 2;
            drillTime = 180f;
            rotateSpeed = -0.4f;
            warmupSpeed = 0.005f;   
            hardnessDrillMultiplier = 3f;
            itemCapacity = 24;
            liquidCapacity = 16f;
            liquidBoostIntensity = 1.2f;
            drawRim = false;
            canOverdrive = true;
            outlineColor = LPPal.outline;
            blockedItems = Seq.with(Items.copper, Items.lead, Items.thorium, Items.titanium, Items.sand, Items.scrap, Items.beryllium, Items.tungsten, Items.coal);
            drillEffect = LPFx.jynDrillEffect;
            updateEffect = LPFx.jynDrillUpdateEffect;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            requirements(Category.production, with(LPItems.jynsteel, 31));
        }};
        
        shearDrill = new Drill("shear-drill"){{
            size = 3;
            health = 95;
            tier = 3;
            drillTime = 240f;
            rotateSpeed = -0.35f;
            warmupSpeed = 0.002f;
            hardnessDrillMultiplier = 4f;
            itemCapacity = 24;
            liquidCapacity = 24f;
            liquidBoostIntensity = 1.2f;
            drawRim = false;
            canOverdrive = true;
            outlineColor = LPPal.outline;
            blockedItems = Seq.with(Items.copper, Items.lead, Items.thorium, Items.titanium, Items.sand, Items.scrap, Items.beryllium, Items.tungsten, Items.coal);
            drillEffect = LPFx.shearDrillEffect;
            updateEffect = LPFx.shearDrillUpdateEffect;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.production, with(LPItems.jynsteel, 32, LPItems.erocrys, 18));
        }};
        
        impactDrill0 = new BurstDrill("impact-drill0"){{
            size = 4;
            health = 224;
            tier = 3;
            drillTime = 120f;
            invertedTime = 60f;
            itemCapacity = 48;
            liquidCapacity = 36f;
            consumePower(0.7f);
            shake = 2f;
            arrows = 2;
            arrowSpacing = 3f;
            arrowOffset = -2.5f;
            arrowColor = LPPal.orange;
            baseArrowColor = Color.valueOf("828A94");
            glowColor = LPPal.orange;
            fogRadius = 4;
            liquidBoostIntensity = 1.1f;
            outlineColor = LPPal.outline;
            hasPower = true;
            squareSprite = true;
            drillSound = LPSounds.shootBang;
            blockedItems = Seq.with(Items.copper, Items.lead, Items.thorium, Items.titanium, Items.sand, Items.scrap, Items.beryllium, Items.tungsten, Items.coal);
            drillMultipliers = new ObjectFloatMap<>();
            drillMultipliers.put(LPItems.jynsteel, 1.4f);
            drillMultipliers.put(LPItems.erocrys, 1.5f);
            drillMultipliers.put(LPItems.massisteel, 1.4f);
            drillMultipliers.put(LPItems.litelnlay, 1.45f);
            drillEffect = LPFx.impactDrillEffect;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            requirements(Category.production, with(LPItems.transchimericsteel, 92, LPItems.jynsteel, 32, LPItems.erocrys, 24));
        }};

        //distribution
        jynDuctbridge = new DuctBridge("jyn-ductbridge"){{
            size = 1;
            health = 34;
            range = 8;
            speed = 4f;
            itemCapacity = 12;
            solid = false;
            underBullets = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            requirements(Category.distribution, with(LPItems.jynsteel, 12, LPItems.erocrys, 4));
        }};

        jynDuct = new Duct("jyn-duct"){{
            size = 1;
            health = 18;
            speed = 3f;
            itemCapacity = 2;
            bridgeReplacement = jynDuctbridge;
            solid = false;
            underBullets = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            requirements(Category.distribution, with(LPItems.jynsteel, 2));
        }};

        jynSorter = new  Sorter("jyn-sorter"){{
            size = 1;
            health = 28;
            invert = false;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            requirements(Category.distribution, with(LPItems.jynsteel, 6));
        }};

        jynInvertedSorter = new Sorter("jyn-inverted-sorter"){{
            size = 1;
            health = 30;
            invert = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            requirements(Category.distribution, with(LPItems.jynsteel, 6, LPItems.erocrys, 3));
        }};

        jynOverflow = new OverflowGate("jyn-overflow"){{
            size = 1;
            health = 23;
            invert = false;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            requirements(Category.distribution, with(LPItems.jynsteel, 5));
        }};

        jynUnderflow = new OverflowGate("jyn-underflow"){{
            size = 1;
            health = 25;
            invert = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            requirements(Category.distribution, with(LPItems.jynsteel, 5, LPItems.erocrys, 2));
        }};

        jynUnloader = new Unloader("jyn-unloader"){{
            size = 1;
            health = 42;
            speed = 2.5f;
            group = BlockGroup.transportation;
            update = true;
            canOverdrive = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
            requirements(Category.distribution, with(LPItems.jynsteel, 12, LPItems.erocrys, 4));
        }};

        litJunction = new Junction("lit-junction"){{
            size = 1;
            health = 24;
            speed = 12f;
            displayedSpeed = 28f;
            solid = false;
            underBullets = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.distribution, with(LPItems.litelnlay, 4));
        }};

         litBridgeConveyor = new BufferedItemBridge("lit-bridge-conveyor"){{
            size = 1;
            health = 30;
            range = 12;
            speed = 18f;
            displayedSpeed = 14f;
            solid = false;
            underBullets = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.distribution, with(LPItems.litelnlay, 14));
        }};

        litConveyor = new Conveyor("lit-conveyor"){{
            size = 1;
            health = 14;
            speed = 0.2f;
            displayedSpeed = 25f;
            itemCapacity = 3;
            junctionReplacement = litJunction;
            bridgeReplacement = litBridgeConveyor;
            solid = false;
            underBullets = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.distribution, with(LPItems.litelnlay, 2));
        }};

        litUnloader = new Unloader("lit-unloader"){{
            size = 1;
            health = 56;
            speed = 2f;
            group = BlockGroup.transportation;
            update = true;
            canOverdrive = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.distribution, with(LPItems.litelnlay, 16, LPItems.crystalite, 4));
        }};

        //liquid
        masLiquidJunction = new LiquidJunction("mas-liquid-junction"){{
            health = 28;
            liquidCapacity = 15f;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.liquid, with(LPItems.massisteel, 5));
        }};

        masLiquidBridgeConduit = new DirectionLiquidBridge("mas-liquid-bridge-conduit"){{
            health = 46;
            range = 8;
            liquidCapacity = 20f;
            hasPower = false;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.liquid, with(LPItems.massisteel, 12, LPItems.erocrys, 5));
        }};

        masConduit = new Conduit("mas-conduit"){{
            health = 20;
            liquidCapacity = 10f;
            liquidPressure = 1f;
            botColor = Color.valueOf("2F3036");
            junctionReplacement = masLiquidJunction;
            rotBridgeReplacement = masLiquidBridgeConduit;
            bridgeReplacement = masLiquidBridgeConduit;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.liquid, with(LPItems.massisteel, 2));
        }};

        masLiquidRouter = new LiquidRouter("mas-liquid-router"){{
            health = 25;
            liquidCapacity = 15f;
            liquidPadding = 3f;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.liquid, with(LPItems.massisteel, 5));
        }};

        traBridgeConduit = new LiquidBridge("tra-bridge-conduit"){{
            health = 64;
            range = 12;
            liquidCapacity = 32f;
            arrowPeriod = 0.7f;
            arrowTimeScl = 3f;
            floating = true;
            hasPower = true;
            canOverdrive = false;
            pulse = true;
            consumePower(1 / 60f);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            requirements(Category.liquid, with(LPItems.transchimericsteel, 14, LPItems.crystalite, 4));
        }};

        traConduit = new ArmoredConduit("tra-conduit"){{
            health = 28;
            liquidCapacity = 16f;
            liquidPressure = 1.2f;
            botColor = Color.valueOf("333940");
            junctionReplacement = masLiquidJunction;
            rotBridgeReplacement = traBridgeConduit;
            bridgeReplacement = traBridgeConduit;
            solid = false;
            floating = true;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            requirements(Category.liquid, with(LPItems.transchimericsteel, 2, LPItems.crystalite, 1));
        }};

        masPump = new Pump("mas-pump"){{
            size = 2;
            health = 65;
            pumpAmount = 0.020835f;
            warmupSpeed = 0.05f;
            liquidCapacity = 40f;
            hasPower = false;
            floating = true;
            placeableLiquid = true;
            squareSprite = true;
            var parts = new DrawBlockParts();
            parts.parts.add(new ShapePart(){{
                circle = false;
                hollow = true;
                color = colorTo = LPPal.aureus;
                sides = 4;
                radius = radiusTo = 6f;
                stroke = 0f;
                strokeTo = 1.5f;
                layer = 110f;
                progress = PartProgress.warmup.curve(Interp.circleIn);
            }});

            parts.parts.add(new HaloPart(){{
                tri = true;
                color = colorTo = LPPal.aureus;
                shapes = 4;
                radius = 0f;
                radiusTo = 2f;
                triLength = 3f;
                triLengthTo = 3f;
                haloRadius = 5f;
                haloRotateSpeed = 0.5f;
                layer = 110f;
                progress = PartProgress.warmup.curve(Interp.circleIn);
            }});

            parts.parts.add(new HaloPart(){{
                tri = true;
                color = colorTo = LPPal.aureus;
                shapes = 4;
                radius = 0f;
                radiusTo = 2f;
                triLength = 3f;
                triLengthTo = 3f;
                shapeRotation = 180f;
                haloRadius = 5f;
                haloRotateSpeed = 0.5f;
                layer = 110f;
                progress = PartProgress.warmup.curve(Interp.circleIn);
            }});
            drawer = new DrawMulti(new DrawPumpLiquid(), new DrawDefault(), parts);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.liquid, with(LPItems.massisteel, 26, LPItems.jynsteel, 16));
        }};

        masPumpHighSpeed = new Pump("mas-pump-high-speed"){{
            size = 4;
            health = 187;
            pumpAmount = 0.1f;
            warmupSpeed = 0.5f;
            liquidCapacity = 124f;
            hasPower = true;
            floating = true;
            placeableLiquid = true;
            squareSprite = true;
            consumePower(1.6f);
            var parts = new DrawBlockParts();
            parts.parts.add(new ShapePart(){{
                circle = false;
                hollow = true;
                color = colorTo = LPPal.aureus;
                sides = 4;
                radius = radiusTo = 14.8f;
                stroke = 0f;
                strokeTo = 2f;
                layer = 110f;
                progress = PartProgress.warmup.curve(Interp.circleIn);
            }});

            parts.parts.add(new HaloPart(){{
                tri = true;
                color = colorTo = LPPal.aureus;
                shapes = 2;
                radius = 0f;
                radiusTo = 3f;
                triLength = 16f;
                triLengthTo = 16f;
                haloRadius = 18.5f;
                haloRotateSpeed = 0.3f;
                layer = 110f;
                progress = PartProgress.warmup.curve(Interp.circleIn);
            }});

            parts.parts.add(new HaloPart(){{
                tri = true;
                color = colorTo = LPPal.aureus;
                shapes = 2;
                radius = 0f;
                radiusTo = 3f;
                triLength = 3f;
                triLengthTo = 3f;
                shapeRotation = 180f;
                haloRadius = 18.5f;
                haloRotateSpeed = 0.3f;
                layer = 110f;
                progress = PartProgress.warmup.curve(Interp.circleIn);
            }});

            parts.parts.add(new HaloPart(){{
                tri = true;
                color = colorTo = LPPal.aureus;
                shapes = 2;
                radius = 0f;
                radiusTo = 3f;
                triLength = 10f;
                triLengthTo = 10f;
                haloRadius = 18.5f;
                haloRotation = 90f;
                haloRotateSpeed = -0.5f;
                layer = 110f;
                progress = PartProgress.warmup.curve(Interp.circleIn);
            }});

            parts.parts.add(new HaloPart(){{
                tri = true;
                color = colorTo = LPPal.aureus;
                shapes = 2;
                radius = 0f;
                radiusTo = 3f;
                triLength = 3f;
                triLengthTo = 3f;
                shapeRotation = 180f;
                haloRadius = 18.5f;
                haloRotation = 90f;
                haloRotateSpeed = -0.5f;
                layer = 110f;
                progress = PartProgress.warmup.curve(Interp.circleIn);
            }});
            drawer = new DrawMulti(new DrawDefault(), new DrawPumpLiquid(), new DrawCircles(){{
                color = Color.valueOf("FCF287A8");
                amount = 6;
                sides = 4;
                strokeMin = 0.6f;
                strokeMax = 1.2f;
                radius = 15f;
                strokeInterp = Interp.circleOut;
            }}, new DrawRegion("-top"), parts);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.liquid, with(LPItems.massisteel, 120, LPItems.jynsteel, 36, LPItems.crystalite, 27));
        }};

        massisteelLiquidStorage = new LiquidRouter("massisteel-liquid-storage"){{
            size = 2;
            health = 58;
            liquidCapacity = 800f;
            liquidPadding = 5f;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.liquid, with(LPItems.massisteel, 22, LPItems.jynsteel, 8));
        }};

        massisteelLiquidStorageLarge = new LiquidRouter("massisteel-liquid-storage-large"){{
            size = 3;
            health = 67;
            liquidCapacity = 1600f;
            liquidPadding = 3f;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.liquid, with(LPItems.massisteel, 30, LPItems.jynsteel, 16));
        }};

        //power
        jynPowerNode = new PowerNode("jyn-power-node"){{
            health = 17;
            laserRange = 8f;
            maxNodes = 5;
            laserColor1 = Color.valueOf("FCB570");
            laserColor2 = Color.valueOf("EB564B");
            priority = -1f;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.power, with(LPItems.jynsteel, 5, LPItems.erocrys, 2));
        }};
        
        jynPowerNodeLarge = new PowerNode("jyn-power-node-large"){{
            size = 2;
            health = 33;
            laserRange = 18f;
            maxNodes = 10;
            laserColor1 = LPPal.orange;
            laserColor2 = LPPal.orangeDark;
            priority = -1f;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.power, with(LPItems.jynsteel, 16, LPItems.erocrys, 6));
        }};
        
        jynBattery = new Battery("jyn-battery"){{
            size = 3;
            health = 112;
            emptyLightColor = LPPal.orangeDark;
            fullLightColor = Color.valueOf("D99F6B");
            conductivePower = true;
            solid = true;
            hasPower = true;
            consumePowerBuffered(6120f);
            priority = -1f;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.power, with(LPItems.jynsteel, 54,LPItems.crystalite, 54, LPItems.erocrys, 24));
        }};
        
        heavyIonChamber = new ConsumeGenerator("heavy-ion-chamber"){{
            size = 5;
            health = 342;
            powerProduction = 28f;
            itemDuration = 240f;
            itemCapacity = 24;
            hasLiquids = false;
            hasItems = true;
            consumeItem(LPItems.ionopolymer, 4);
            generateEffect = Fx.none;
            consumeEffect = LPFx.heavyIonChamberConsumption;
            ambientSound = LPSounds.loopThoriumReactor;
            ambientSoundVolume = 0.8f;
            baseLightRadius = 80f;
            var parts = new DrawBlockParts();
            parts.parts = Seq.with(
                new ShapePart(){{
                    circle = false;
                    hollow = true;
                    color = colorTo = LPPal.orangeMid;
                    sides = 4;
                    radius = radiusTo = 18f;
                    stroke = 0f;
                    strokeTo = 2.5f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }},

                new ShapePart(){{
                    circle = false;
                    hollow = true;
                    color = colorTo = LPPal.orangeMid;
                    sides = 4;
                    radius = radiusTo = 9.3f;
                    stroke = 0f;
                    strokeTo = 1.5f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }},

                new HaloPart(){{
                    tri = true;
                    color = colorTo = LPPal.orangeMid;
                    shapes = 2;
                    radius = 0f;
                    radiusTo = 3f;
                    triLength = 12f;
                    triLengthTo = 24f;
                    haloRadius = 0f;
                    haloRotation = 0f;
                    haloRotateSpeed = 0.2f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }},

                new HaloPart(){{
                    tri = true;
                    color = colorTo = LPPal.orangeMid;
                    shapes = 2;
                    radius = 0f;
                    radiusTo = 3f;
                    triLength = 5f;
                    triLengthTo = 10f;
                    haloRadius = 0f;
                    haloRotation = 0f;
                    haloRotateSpeed = -0.3f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }},

                new HaloPart(){{
                    tri = true;
                    color = Color.valueOf("E8D17400");
                    colorTo = LPPal.orangeMid;
                    shapes = 2;
                    radius = 0.5f;
                    radiusTo = 0.5f;
                    triLength = 20f;
                    triLengthTo = 40f;
                    haloRadius = 0f;
                    haloRotation = 0f;
                    haloRotateSpeed = 0f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }}
            );
            drawer = new DrawMulti(new DrawDefault(), new DrawGlowRegion("-glow"){{
                glowScale = 6;
                alpha = 1f;
                color = LPPal.orange;
            }}, parts);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.power, with(LPItems.jynsteel, 240, LPItems.massisteel, 160, LPItems.erocrys, 160));
        }};

        //wall
        jynWall = new Wall("jyn-wall"){{
            size = 1;
            health = 216;
            armor = 2;
            flashHit = true;
            chanceDeflect = 0.02f;
            flashColor = LPPal.orange;
            priority = 2f;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallDestroyEffect;
            destroySound = LPSounds.blockExplode1Alt;
            destroySoundVolume = 1.2f;
            requirements(Category.defense, with(LPItems.jynsteel, 16));
        }};

        jynWallLarge = new Wall("jyn-wall-large"){{
            size = 2;
            health = 704;
            armor = 3;
            flashHit = true;
            chanceDeflect = 0.04f;
            flashColor = LPPal.orange;
            priority = 2f;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallLargeDestroyEffect;
            destroySound = LPSounds.blockExplode2;
            destroySoundVolume = 2f;
            requirements(Category.defense, with(LPItems.jynsteel, 36));
        }};

        masWall = new Wall("mas-wall"){{
            size = 1;
            health = 285;
            armor = 3;
            flashHit = true;
            chanceDeflect = 0.02f;
            flashColor = LPPal.aureus;
            priority = 2f;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallDestroyEffect;
            destroySound = LPSounds.blockExplode1Alt;
            destroySoundVolume = 1.2f;
            requirements(Category.defense, with(LPItems.massisteel, 16));
        }};

        masWallLarge = new Wall("mas-wall-large"){{
            size = 2;
            health = 812;
            armor = 5;
            flashHit = true;
            flashColor = LPPal.aureus;
            priority = 2f;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0.4f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallLargeDestroyEffect;
            destroySound = LPSounds.blockExplode2;
            destroySoundVolume = 2f;
            requirements(Category.defense, with(LPItems.massisteel, 36));
        }};

        traWall = new Wall("tra-wall"){{
            size = 1;
            health = 325;
            armor = 4;
            flashHit = true;
            crushDamageMultiplier = 0.8f;
            flashColor = LPPal.redDark;
            priority = 2f;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallDestroyEffect;
            destroySound = LPSounds.blockExplode1Alt;
            destroySoundVolume = 1.2f;
            requirements(Category.defense, with(LPItems.transchimericsteel, 16));
        }};

        traWallLarge = new Wall("tra-wall-large"){{
            size = 2;
            health = 1110;
            armor = 6;
            flashHit = true;
            crushDamageMultiplier = 0.7f;
            flashColor = LPPal.redDark;
            priority = 2f;
            buildCostMultiplier = 0.8f;
            researchCostMultiplier = 0.1f;
            alwaysUnlocked = false;
            destroyEffect = LPFx.wallLargeDestroyEffect;
            destroySound = LPSounds.blockExplode3;
            destroySoundVolume = 1.2f;
            requirements(Category.defense, with(LPItems.transchimericsteel, 36));
        }};

        ttfWall = new Wall("325"){{
            size = 3;
            health = 325;
            alwaysUnlocked = true;
            researchCostMultiplier = 0.4f;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};

        //craft
        masHeatRedirector = new HeatConductor("mas-heat-redirector"){{
            size = 2;
            health = 28;
            visualMaxHeat = 12f;
            rotateDraw = false;
            canOverdrive = true;
            alwaysUnlocked = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawHeatInput("-heat"));
            researchCostMultiplier = 0.4f;
            requirements(Category.crafting, with(LPItems.massisteel, 12, LPItems.erocrys, 4));
        }};
        
        masHeatRedirectorSmall= new HeatConductor("mas-heat-redirector-small"){{
            size = 1;
            health = 12;
            visualMaxHeat = 6f;
            rotateDraw = false;
            canOverdrive = true;
            alwaysUnlocked = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawHeatInput("-heat"));
            researchCostMultiplier = 0.4f;
            requirements(Category.crafting, with(LPItems.massisteel, 6, LPItems.erocrys, 3));
        }};
        
        masHeatRouter = new HeatConductor("mas-heat-router"){{
            size = 2;
            health = 36;
            visualMaxHeat = 16f;
            rotateDraw = false;
            canOverdrive = true;
            splitHeat = true;
            alwaysUnlocked = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(-1, true), new DrawHeatOutput(), new DrawHeatOutput(1, true), new DrawHeatInput("-heat"));
            researchCostMultiplier = 0.4f;
            requirements(Category.crafting, with(LPItems.massisteel, 17, LPItems.erocrys, 6));
        }};
        
        masHeatRouterSmall = new HeatConductor("mas-heat-router-small"){{
            size = 1;
            health = 16;
            visualMaxHeat = 8f;
            rotateDraw = false;
            canOverdrive = true;
            splitHeat = true;
            alwaysUnlocked = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(-1, true), new DrawHeatOutput(), new DrawHeatOutput(1, true), new DrawHeatInput("-heat"));
            researchCostMultiplier = 0.4f;
            requirements(Category.crafting, with(LPItems.massisteel, 8, LPItems.erocrys, 4));
        }};
        
        masSlagHeater = new HeatProducer("mas-slag-heater"){{
            size = 3;
            health = 86;
            itemCapacity = 0;
            liquidCapacity = 80f;
            consumeLiquid(Liquids.slag, 0.2f);
            heatOutput = 24f;
            warmupRate = 0.05f;
            craftTime = 60f;
            ambientSound = Sounds.loopHum;
            rotateDraw = false;
            canOverdrive = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.slag), new DrawDefault(), new DrawHeatOutput()); 
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.crafting, with(LPItems.massisteel, 40, LPItems.erocrys, 24));
        }};

        ionopolymerCrucible = new HeatCrafter("ionopolymer-crucible"){{
            size = 3;
            health = 126;
            itemCapacity = 20;
            liquidCapacity = 0f;
            heatRequirement = 12f;
            maxEfficiency = 3f;
            craftTime = 180f;
            consumeItems(with(LPItems.jynsteel, 4, LPItems.erocrys, 2, LPItems.massisteel, 1));
            outputItem = new ItemStack(LPItems.ionopolymer, 1);
            ambientSound = Sounds.loopDifferential;
            ambientSoundVolume = 0.1f;
            canOverdrive = false;
            craftEffect = LPFx.ionopolymerCrucibleCraft;
            var parts = new DrawBlockParts();
            parts.parts = Seq.with(
                new ShapePart(){{
                    circle = false;
                    hollow = true;
                    color = Color.valueOf("FFEF6D00");
                    colorTo = Color.valueOf("FFB570");
                    sides = 4;
                    radius = 0f;
                    radiusTo = 12f;
                    stroke = 0f;
                    strokeTo = 2f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }}
            );
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatInput(), new DrawHeatRegion("-glow"), new DrawFlame(Color.valueOf("FFEF6D")), parts);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.crafting, with(LPItems.massisteel, 55,LPItems.jynsteel, 35, LPItems.erocrys, 20));
        }};
        
        ionopolymerCrucibleLarge = new HeatCrafter("ionopolymer-crucible-large"){{
            size = 5;
            health = 555;
            itemCapacity = 96;
            liquidCapacity = 0f;
            heatRequirement = 48f;
            maxEfficiency = 1f;
            craftTime = 240f;
            consumePower(3.5f);
            consumeItems(with(LPItems.jynsteel, 48, LPItems.erocrys, 24, LPItems.massisteel, 12));
            outputItem = new ItemStack(LPItems.ionopolymer, 12);
            ambientSound = Sounds.loopDifferential;
            ambientSoundVolume = 0.1f;
            canOverdrive = false;
            craftEffect = LPFx.ionopolymerCrucibleLargeCraft;
            var parts = new DrawBlockParts();
            parts.parts = Seq.with(
                new ShapePart(){{
                    circle = false;
                    hollow = true;
                    color = Color.valueOf("FFEF6D00");
                    colorTo = Color.valueOf("FFB570");
                    sides = 4;
                    radius = 0f;
                    radiusTo = 18f;
                    stroke = 0f;
                    strokeTo = 2.5f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }},

                new ShapePart(){{
                    circle = false;
                    hollow = true;
                    color = Color.valueOf("FFEF6D00");
                    colorTo = Color.valueOf("FFB570");
                    sides = 4;
                    radius = 0f;
                    radiusTo = 12f;
                    stroke = 0f;
                    strokeTo = 1.5f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.linear).sin(0.1f, 0.4f).mul(PartProgress.warmup.add(0f).mul(10000f).clamp().curve(Interp.circleIn));
                }}
            );
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatInput(), new DrawHeatRegion("-glow"), new DrawFlame(){{
                flameColor = Color.valueOf("FFEF6D");
                lightRadius = 64f;
                flameRadius = 7f;
                flameRadiusIn = 6f;
                flameRadiusScl = 3f;
                flameRadiusMag = 3f;
                flameRadiusInMag = 0.5f;
            }}, parts);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            requirements(Category.crafting, with(LPItems.transchimericsteel, 220, LPItems.massisteel, 216, LPItems.jynsteel, 48, LPItems.crystalite, 44));
        }};

        erocrysExtractory = new HeatCrafter("erocrys-extractory"){{
            size = 4;
            health = 202;
            itemCapacity = 30;
            liquidCapacity = 0f;
            heatRequirement = 8f;
            maxEfficiency = 2f;
            craftTime = 240f;
            consumePower(1.2f);
            consumeItem(LPItems.erocrys, 15);
            outputItem = new ItemStack(LPItems.crystalite, 3);
            ambientSound = LPSounds.loopNecroplasm;
            ambientSoundVolume = 0.2f;
            updateEffect = LPFx.erocrysExtractoryUpdate;
            var parts = new DrawBlockParts();
            parts.parts = Seq.with(
                new ShapePart(){{
                    circle = false;
                    hollow = true;
                    color = Color.valueOf("EDA76E");
                    colorTo = Color.valueOf("EDA76E");
                    sides = 4;
                    radius = 17f;
                    radiusTo = 17f;
                    stroke = 0f;
                    strokeTo = 2.5f;
                    rotateSpeed = -0.2f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }},

                new ShapePart(){{
                    circle = false;
                    hollow = true;
                    color = Color.valueOf("EDA76E");
                    colorTo = Color.valueOf("EDA76E");
                    sides = 4;
                    radius = 10f;
                    radiusTo = 10f;
                    stroke = 0f;
                    strokeTo = 1.5f;
                    rotateSpeed = 0.2f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }}
            );
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatInput(), new DrawHeatRegion("-glow"), parts);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.crafting, with(LPItems.massisteel, 96, LPItems.jynsteel, 82, LPItems.erocrys, 80));
        }};

        transChimericFoundry = new GenericCrafter("trans-chimeric-foundry"){{
            size = 4;
            health = 314;
            itemCapacity = 32;
            liquidCapacity = 0f;
            craftTime = 160f;
            consumePower(2.8f);
            consumeItems(with(LPItems.litelnlay, 6, LPItems.massisteel, 4));
            outputItem = new ItemStack(LPItems.transchimericsteel, 4);
            ambientSound = LPSounds.loopPressureGenerator;
            ambientSoundVolume = 0.1f;
            canOverdrive = true;
            craftEffect = LPFx.transChimericFoundryCraft;
            var parts = new DrawBlockParts();
            parts.parts = Seq.with(
                new ShapePart(){{
                    circle = false;
                    hollow = true;
                    color = LPPal.orange;
                    colorTo = Color.valueOf("E8EBFF");
                    sides = 4;
                    radius = 16f;
                    radiusTo = 16f;
                    stroke = 0f;
                    strokeTo = 2f;
                    layer = 110f;
                    progress = PartProgress.warmup.curve(Interp.circleIn);
                }}
            );
            drawer = new DrawMulti(new DrawDefault(), new DrawGlowRegion("-glow"){{color = LPPal.orange; glowIntensity = 0.8f;}}, 
            new DrawGlowRegion("-glow0"){{color = LPPal.orange; glowScale = 12f; glowIntensity = 1.2f;}}, 
            new DrawGlowRegion("-glow1"){{color = LPPal.orange; glowScale = 14f; glowIntensity = 1.6f;}}, new DrawFlame(){{
                flameColor = Color.valueOf("E8EBFF");
                lightRadius = 40f;
                flameRadius = 4.5f;
                flameRadiusIn = 4f;
                flameRadiusScl = 4f;
                flameRadiusMag = 3f;
                flameRadiusInMag = 0.5f;
            }}, parts);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.crafting, with(LPItems.litelnlay, 85, LPItems.massisteel, 66, LPItems.jynsteel, 64, LPItems.crystalite, 32));
        }};

        highSpeedTranschimericFoundry = new GenericCrafter("high-speed-transchimeric-foundry"){{
            size = 5;
            health = 513;
            itemCapacity = 128;
            liquidCapacity = 0f;
            craftTime = 300f;
            consumePower(5f);
            consumeItems(with(LPItems.litelnlay, 45, LPItems.massisteel, 30));
            outputItem = new ItemStack(LPItems.transchimericsteel, 30);
            ambientSound = LPSounds.loopRegen;
            ambientSoundVolume = 0.2f;
            canOverdrive = true;
            updateEffect = LPFx.highSpeedTranschimericFoundryUpdate;
            craftEffect = LPFx.highSpeedTranschimericFoundryCraft;
            var parts = new DrawBlockParts();
            parts.parts = Seq.with(
                new RegionPart(){{
                    outline = false;
                    mirror = false;
                    x = 0;
                    y = 0;
                    color = Color.valueOf("FFFFFF00");
                    colorTo = Color.valueOf("FFFFFF00");
                    xScl = 0;
                    yScl = 0;
                    layer = 110;
                    progress = PartProgress.warmup.curve(Interp.pow10Out);
                    moveRot = 90;
                    children = Seq.with(
                        new ShapePart(){{
                            circle = false;
                            hollow = true;
                            color = Color.valueOf("E8EBFF00");
                            colorTo = Color.valueOf("E8EBFF");
                            sides = 4;
                            radius = 0f;
                            radiusTo = 18f;
                            stroke = 0f;
                            strokeTo = 2.5f;
                            layer = 110f;
                            progress = PartProgress.warmup.curve(Interp.circleIn);
                        }}
                    );
                }}
            );
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(){{
                flameColor = Color.valueOf("E8EBFF");
                lightRadius = 56f;
                flameRadius = 3.5f;
                flameRadiusIn = 3.5f;
                flameRadiusScl = 8f;
                flameRadiusMag = 7f;
                flameRadiusInMag = 2f;
            }}, parts);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            requirements(Category.crafting, with(LPItems.transchimericsteel, 180, LPItems.massisteel, 112, LPItems.jynsteel, 142, LPItems.crystalite, 40));
        }};

        //storage
        pioneers = new CoreBlock("pioneers") {{
            size = 3;
            health = 732;
            armor = 5;
            itemCapacity = 2000;
            unitCapModifier = 16;
            unitType = LPUnits.pioneersUnit;
            priority = 10f;
            alwaysUnlocked = true;
            canOverdrive = false;
            isFirstTier = true;
            requiresCoreZone = false;
            researchCostMultiplier = 0f;
            destroyEffect = LPFx.pioneersDestroyEffect;
            destroySound = LPSounds.blockExplodeFlammable;
            destroySoundVolume = 4;
            requirements(Category.effect, with(LPItems.jynsteel, 1000, LPItems.erocrys, 800));
        }};

        jynVault = new StorageBlock("jyn-vault") {{
            size = 2;
            health = 70;
            itemCapacity = 400;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            requirements(Category.effect, with(LPItems.jynsteel, 20, LPItems.erocrys, 8));
        }};

        //environment
        coreFloor = new Floor("core-floor"){{
            variants = 0;
            drawEdgeIn = false;
            drawEdgeOut = false;
        }};

        darkStone = new Floor("darkstone"){{
            variants = 3;
        }};

        darkStoneRubble = new Floor("darkstone-rubble"){{
            variants = 4;
        }};
        
        
        rhyoliticLimestone = new Floor("rhyolitic-limestone"){{
            variants = 4;
        }};
        
        rhyoliticRubble = new Floor("rhyolitic-rubble"){{
            variants = 3;
        }};

        relicfloor = new Floor("relicfloor"){{
            variants = 0;
        }};
    
        relicfloor2 = new Floor("relicfloor-2"){{
            variants = 0;
        }};
    
        relicfloor3 = new Floor("relicfloor-3"){{
            variants = 0;
        }};
    
        relicfloor4 = new Floor("relicfloor-4"){{
            variants = 0;
        }};
    
        relicfloor5 = new Floor("relicfloor-5"){{
            variants = 0;
        }};
    
        relicfloor6 = new Floor("relicfloor-6"){{
            variants = 0;
        }};
    
        reliclines = new Floor("reliclines"){{
            variants = 15;
        }};

        reliclines2 = new Floor("reliclines-2"){{
            variants = 15;
        }};

        relictiles = new Floor("relictiles"){{
            variants = 25;
            drawEdgeIn = false;
            drawEdgeOut = false;
        }};

        relictiles2 = new Floor("relictiles-2"){{
            variants = 18;
            drawEdgeIn = false;
            drawEdgeOut = false;
        }};

        reliclinesLines = new Floor("reliclines-lines"){{
            variants = 4;
            emitLight = true;
            lightRadius = 12f;
            lightColor = Color.valueOf("F2D3D3FF");
            mapColor = Color.valueOf("494949");
        }};

        reliclinesLinesTra = new Floor("reliclines-lines-tra"){{
            variants = 4;
            emitLight = true;
            lightRadius = 12f;
            lightColor = Color.valueOf("F2D3D3FF");
            mapColor = Color.valueOf("494949");
        }};
        
        reliclinesLinesRup = new Floor("reliclines-lines-rup"){{
            variants = 4;
            emitLight = true;
            lightRadius = 12f;
            lightColor = Color.valueOf("F2D3D3FF");
            mapColor = Color.valueOf("494949");
        }};
        
        reliclinesLinesRdo = new Floor("reliclines-lines-rdo"){{
            variants = 4;
            emitLight = true;
            lightRadius = 12f;
            lightColor = Color.valueOf("F2D3D3FF");
            mapColor = Color.valueOf("494949");
        }};
        
        reliclinesLinesLup = new Floor("reliclines-lines-lup"){{
            variants = 4;
            emitLight = true;
            lightRadius = 12f;
            lightColor = Color.valueOf("F2D3D3FF");
            mapColor = Color.valueOf("494949");
        }};
        
        reliclinesLinesLdo = new Floor("reliclines-lines-ldo"){{
            variants = 4;
            emitLight = true;
            lightRadius = 12f;
            lightColor = Color.valueOf("F2D3D3FF");
            mapColor = Color.valueOf("494949");
        }};

        jynSteelOre = new OreBlock("jynsteel-ore"){{
            variants = 3;
            itemDrop = LPItems.jynsteel;
            useColor = true;
            wallOre = false;
            mapColor = Color.valueOf("5F6372");
        }};

        massisteelOre = new OreBlock("massisteel-ore"){{
            variants = 4;
            itemDrop = LPItems.massisteel;
            useColor = true;
            wallOre = false;
            mapColor = Color.valueOf("7B7B7B");
        }};

        erocrysOre = new OreBlock("erocrys-ore"){{
            variants = 3;
            itemDrop = LPItems.erocrys;
            useColor = true;
            wallOre = false;
            mapColor = Color.valueOf("FFB570");
        }};

        litelnlayOre = new OreBlock("litelnlay-ore"){{
            variants = 4;
            itemDrop = LPItems.litelnlay;
            useColor = true;
            wallOre = false;
            mapColor = Color.valueOf("E8EBFF");
        }};

        darkStoneRock = new Prop("darkstone-rock"){{
            variants = 4;
        }};

        darkStoneWall = new StaticWall("darkstone-wall"){{
            variants = 3;
            darkStone.asFloor().wall = darkStoneRubble.asFloor().wall = this;
        }};
        
        rhyoliticLimestoneWall = new StaticWall("rhyolitic-limestone-wall"){{
            variants = 3;
            rhyoliticLimestone.asFloor().wall = rhyoliticRubble.asFloor().wall = this;
        }};
        
        
        relicWall = new StaticWall("relicwall"){{
            variants = 50;
        }};
        
        relicWall2 = new StaticWall("relicwall-2"){{
            variants = 30;
        }};
        
        reliclinesWall = new StaticWall("reliclines-wall"){{
            variants = 6;
        }};
        
        reliclinesWall2 = new StaticWall("reliclines-wall-2"){{
            variants = 6;
        }};
        
        darkStoneCrystal = new TallBlock("darkstone-crystal"){{
            variants = 1;
            clipSize = 96f;
            shadowAlpha = 0.5f;
            shadowOffset = -1.55f;
        }};
        
        erocrysCrystal = new TallBlock("erocrys-crystal"){{
            variants = 2;
            clipSize = 96f;
            shadowAlpha = 0.5f;
            shadowOffset = -1.55f;
        }};

        //Process

        process1 = new MessageBlock("process1"){{
            alwaysUnlocked = false;
            placeablePlayer = false;
            displayFlow = false;
            databaseTag = "proc";
        }};

        process2 = new MessageBlock("process2"){{
            alwaysUnlocked = false;
            placeablePlayer = false;
            displayFlow = false;
            databaseTag = "proc";
        }};

        process3 = new MessageBlock("process3"){{
            alwaysUnlocked = false;
            placeablePlayer = false;
            displayFlow = false;
            databaseTag = "proc";
        }};
        
        process4 = new MessageBlock("process4"){{
            alwaysUnlocked = false;
            placeablePlayer = false;
            displayFlow = false;
            databaseTag = "proc";
        }};
    }
}
