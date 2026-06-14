package LP.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.content.Liquids;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.UnitSorts;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.SoundEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.graphics.Layer;
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
    public static Block fallenstar, repelback, hushstrike, crimsondwarf, infernoblade, recursion;

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

        disflux = new LPItemTurret("disflux"){{
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

        impactor = new LPItemTurret("impactor"){{
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
                    subBulletWidth = 6f;
                    subBulletHeight = 9f;
                    subTrailWidth = 2f;
                    subTrailLength = 12;
                    subHitEffect = LPFx.impactorHitSmall;
                }}
            );
        }};

        repulstar = new LPItemTurret("repulstar"){{
            size = 4;
            health = 456;
            armor = 7;
            requirements(Category.turret, with(LPItems.massisteel, 240, LPItems.jynsteel, 126, LPItems.erocrys, 72));
            rotateSpeed = 3f;
            reload = 140f;
            range = 304f;
            recoil = 4f;
            recoilTime = 60f;
            shake = 5f;
            shootCone = 2f;
            outlineColor = LPPal.outline;
            shootSound = LPSounds.blasterShot1;
            shootSoundVolume = 1.8f;
            maxAmmo = 20;
            ammoPerShot = 2;
            heatRequirement = 4f;
            maxHeatEfficiency = 1f;
            consumePower(2f);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            buildTime = 120f;
            databaseTag = "zd";
            destroySound = Sounds.blockExplode3;
            destroyEffect = LPFx.repulstarDestroy;
            drawer = new DrawTurret(){{
                parts = Seq.with(new RegionPart("-barrel"){{
                    x = y = 0f;
                    heatColor = LPPal.redDark;
                    heatProgress = PartProgress.recoil;
                    progress = PartProgress.recoil;
                    under = false;
                    mirror = false;
                    moveY = -5;
                }});
            }};
            ammo(
                LPItems.massisteel, new SplashKnockbackBulletType(38f, 8f, 5f){{
                    sprite = "lp-plasma";
                    width = 8;
                    height = 8;
                    shrinkY = 0f;
                    hitSize = 64f;
                    hitColor = lightColor = frontColor = backColor = trailColor = LPPal.aureus;
                    trailWidth = 3;
                    trailLength = 2;
                    rangeOverride = 304f;
                    knockback = 40f;
                    splashKnockback = 40f;
                    splashKnockbackRadius = 96f;
                    splashDamage = 65f;
                    splashDamageRadius = 96f;
                    ammoMultiplier = 1f;
                    armorMultiplier = 4f;
                    shieldDamageMultiplier = 1.1f;
                    buildingDamageMultiplier = 1.05f;
                    keepVelocity = false;
                    shootEffect = LPFx.repulstarShoot;
                    smokeEffect = LPFx.repulstarSmoke;
                    hitEffect = LPFx.repulstarHit;
                    hitSound = LPSounds.hitRepelback;
                    hitSoundVolume = 1.2f;
                    hitShake = 12;
                    despawnEffect = Fx.none;
                }}
            );
        }};

        radiance = new LPPowerTurret("radiance"){{
            size = 4;
            health = 448;
            armor = 7;
            requirements(Category.turret, with(LPItems.jynsteel, 245, LPItems.massisteel, 121, LPItems.erocrys, 62));
            rotateSpeed = 3.5f;
            reload = 160f;
            range = 320f;
            recoil = 4f;
            recoilTime = 60f;
            shake = 5f;
            cooldownTime = 160f;
            heatColor = LPPal.redDark;
            outlineColor = LPPal.outline;
            targetAir = false;
            shootSound = LPSounds.antiMaterialRifleShot;
            shootSoundVolume = 0.85f;
            databaseTag = "gr";
            consumePower(4.5f);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            buildTime = 120f;
            destroySound = LPSounds.blockExplodeExplosiveAlt;
            destroyEffect = LPFx.radianceDestroy;
            drawer = new DrawTurret(){{
                parts = Seq.with(new RegionPart("-glow"){{
                    under = false;
                    outline = false;
                    mirror = false;
                    blending = Blending.additive;
                    color = Color.valueOf("FFB57000");
                    colorTo = LPPal.orange;
                    progress = PartProgress.reload.inv().add(PartProgress.recoil).clamp().mul(0.5f).add(-0.5f).sin(1.5f, 5f);
                }});
            }};
            shootY = 4f;
            shootType = new ArtilleryBulletType(){{
                sprite = "lp-plasma";
                width = height = 9;
                shrinkY = 0f;
                hitColor = lightColor = backColor = frontColor = trailColor = LPPal.orange;
                trailWidth = 3f;
                trailLength = 3;
                trailInterval = 0.4f;
                trailEffect = new MultiEffect(){{
                    new ParticleEffect(){{
                        particles = 1;
                        region = "lp-triangle";
                        lifetime =  20f;
                        length = 6f;
                        baseLength = 1f;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow2In;
                        spin = 7f;
                        sizeFrom = 5f;
                        sizeTo = 0f;
                        colorFrom = colorTo = LPPal.orange;
                    }};

                    new ParticleEffect(){{
                        particles = 1;
                        region = "lp-triangle";
                        lifetime =  20f;
                        length = 6f;
                        baseLength = 1f;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow2In;
                        spin = -7f;
                        sizeFrom = 5f;
                        sizeTo = 0f;
                        colorFrom = colorTo = LPPal.orange;
                    }};
                }};
                speed = 16f;
                lifetime = 20f;
                rangeOverride =  320f;
                splashDamage = 116f;
                splashDamageRadius =  64f;
                ammoMultiplier = 1f;
                armorMultiplier = 1.55f;
                buildingDamageMultiplier = 0.8f;
                status = LPStatusEffect.stall;
                statusDuration = 30f;
                hitColor = LPPal.orange;
                hitSoundVolume = 3.5f;
                hitSound = LPSounds.shootBlaster3;
                hitShake = 8f;
                hitEffect = despawnEffect = LPFx.radianceHit;
                shootEffect = LPFx.radianceShoot;
                smokeEffect = LPFx.radianceSmoke;
            }};
        }};

        meteor = new LPItemTurret("meteor"){{
            size = 4;
            health = 471;
            armor = 6;
            requirements(Category.turret, with(LPItems.massisteel, 252, LPItems.jynsteel, 131, LPItems.erocrys, 124));
            rotateSpeed = 3.8f;
            reload = 30f;
            range = 360f;
            recoil = 2f;
            recoilTime = 60f;
            shake = 6f;
            shootCone = 3;
            inaccuracy = 3f;
            outlineColor = LPPal.outline;
            shootSound = LPSounds.cannonLargeShot; 
            shootSoundVolume = 1.12f;
            maxAmmo = 24;
            ammoPerShot = 3;
            minWarmup = 0.99f;
            warmupMaintainTime = 10f;
            shootWarmupSpeed = 0.25f;
            targetGround = false;
            targetAir = true;
            consumePower(6f);
            alwaysUnlocked = false;
            researchCostMultiplier = 0.01f;
            buildTime = 120f;
            databaseTag = "ps";
            destroySound = LPSounds.blockExplode3;
            destroyEffect = LPFx.meteorDestroy;
            drawer = new DrawTurret(){{
                parts = Seq.with(
                    new RegionPart("-barrel"){{
                        heatColor = LPPal.redDark;
                        heatProgress = PartProgress.recoil;
                        progress = PartProgress.recoil;
                        under = false;
                        mirror = false;
                        moveY = -3f;
                    }},

                    new RegionPart("-side1"){{
                        heatColor = LPPal.redDark;
                        heatProgress = PartProgress.warmup;
                        progress = PartProgress.warmup;
                        under = false;
                        mirror = true;
                        moveX = 1.2f;
                    }},

                    new RegionPart("-side2"){{
                        heatColor = LPPal.redDark;
                        heatProgress = PartProgress.warmup;
                        progress = PartProgress.warmup;
                        under = false;
                        mirror = true;
                        moveX = 2.2f;
                        moveY = -0.5f;
                    }}
                );
            }}; 
            shootY = 9f;
            shoot = new ShootAlternate(0f){{
                shots = 3;
            }};
            ammo(
                LPItems.erocrys, new BasicBulletType(){{
                    sprite = "lp-plasma";
                    width = 9;
                    height = 9;
                    shrinkY = 0f;
                    hitColor = lightColor = backColor = frontColor = trailColor = LPPal.orangeDark;
                    trailWidth = 2.5f;
                    trailLength = 6;
                    trailChance = -1;
                    trailInterval = 2.8f;
                    trailRotation = true;
                    trailEffect = new MultiEffect(
                        Fx.colorSpark,
                        Fx.disperseTrail
                    );
                    parts = Seq.with(
                        new FlarePart(){{
                            followRotation = false;
                            color1 = LPPal.orangeDark;
                            color2 = LPPal.orangeDark;
                            sides = 4;
                            radius = 12f;
                            radiusTo = 12f;
                            spinSpeed = 4f;
                            stroke = 3f;
                            layer = Layer.effect;
                        }}
                    );
                    speed = 8f;
                    lifetime = 45f;
                    rangeOverride = 360f;
                    damage = 54f;
                    ammoMultiplier = 1f;
                    reloadMultiplier = 1f;
                    armorMultiplier = 1.8f;
                    shieldDamageMultiplier = 2f;
                    status = LPStatusEffect.empII;
                    statusDuration = 60f;
                    collidesAir = true;
                    scaleLife = true;
                    collidesGround = false;
                    shootEffect = LPFx.meteorErocrysShoot;
                    smokeEffect = LPFx.meteorErocrysSmoke;
                    hitSound = LPSounds.airCrushMedium3;
                    hitSoundVolume = 1.22f;
                    hitShake = 7f;
                    hitEffect = LPFx.meteorErocrysHit;
                    fragBullets = 1;
                    fragOffsetMax = 0f;
                    fragOffsetMin = 0f;
                    fragBullet = new BasicBulletType(){{
                        hitEffect = Fx.none;
                        hitSound = Sounds.none;
                        despawnEffect = Fx.none;
                        collidesAir = false;
                        hittable = false;
                        keepVelocity = false;
                        width = height = shrinkY = speed = lifetime = damage = 0f;
                        splashDamage = 6f;
                        splashDamageRadius = 16f;
                    }};
                }},

                LPItems.jynsteel, new BasicBulletType(){{
                    sprite = "lp-plasma";
                    width = 9;
                    height = 9;
                    shrinkY = 0f;
                    hitColor = lightColor = backColor = frontColor = trailColor = LPPal.aureus;
                    trailWidth = 2.5f;
                    trailLength = 6;
                    trailChance = -1;
                    trailInterval = 2.8f;
                    trailRotation = true;
                    trailEffect = new ParticleEffect(){{
                        particles = 3;
                        region = "lp-square";
                        lifetime = 20;
                        length = 8;
                        baseLength = 2;
                        interp = Interp.pow3Out;
                        sizeInterp = Interp.pow4In;
                        sizeFrom = 2;
                        sizeTo = 0;
                        colorFrom = colorTo = LPPal.aureus;
                    }};
                    parts = Seq.with(
                        new FlarePart(){{
                            followRotation = false;
                            color1 = LPPal.aureus;
                            color2 = LPPal.aureus;
                            sides = 4;
                            radius = 12f;
                            radiusTo = 12f;
                            spinSpeed = 0f;
                            stroke = 3f;
                            rotation = 45f;
                            layer = Layer.effect;
                        }}
                    );
                    speed = 8f;
                    lifetime = 45f;
                    rangeOverride = 360f;
                    damage = 34f;
                    ammoMultiplier = 1f;
                    reloadMultiplier = 1.2f;
                    rangeChange = 80f;
                    armorMultiplier = 0.2f;
                    collidesAir = true;
                    scaleLife = true;
                    collidesGround = false;
                    shootEffect = LPFx.meteorJynsteelShoot;
                    smokeEffect = LPFx.meteorJynsteelSmoke;
                    hitSound = LPSounds.cannonLargeHit;
                    hitSoundVolume = 1.2f;
                    hitShake = 7f;
                    hitEffect = LPFx.meteorJynsteelHit;
                    despawnEffect = LPFx.meteorJynsteelDespawn;
                    despawnSound = LPSounds.artilleryHeavyShot;
                }}
            );
        }};

        cloudpiercer = new LPPowerTurret("cloudpiercer"){{
            size = 4;
            health = 486;
            armor = 8f;
            rotateSpeed = 3f;
            reload = 240f;
            range = 560f;
            recoil = 5f;
            recoilTime = 40f;
            shake = 6f;
            cooldownTime = 120f;
            heatColor = LPPal.redDark;
            canOverdrive = false;
            databaseTag = "xn";
            requirements(Category.turret, with(LPItems.jynsteel, 277, LPItems.massisteel, 145, LPItems.erocrys, 93));
            alwaysUnlocked = false;
            researchCostMultiplier = 0.4f;
            buildTime = 120f;
            shootSound = LPSounds.electricBlockerBreak;
            shootSoundVolume = 0.95f;
            destroySound = LPSounds.blockExplodeExplosiveAlt;
            destroySoundVolume = 2f;
            destroyEffect = LPFx.cloudpiercerDestroy;
            shootY = 4f;
            shoot.firstShotDelay = 220f;
            consumePower(8.2f);
            chargeSound = LPSounds.acceleratorCharge;
            loopSound = LPSounds.loopThruster;
            outlineColor = LPPal.outline;
            drawer = new DrawTurret(){{
                parts = Seq.with(
                    new ShapePart(){{
                        hollow = true;
                        color = Color.valueOf("FF584500");
                        colorTo = Color.valueOf("FF584500");
                        sides = 4;
                        radius = radiusTo = 31f;
                        stroke = 2f;
                        strokeTo = 1f;
                        rotation = 0f;
                        layer = Layer.effect;
                        progress = PartProgress.recoil.compress(0f, 0.5f);
                    }},

                    new FlarePart(){{
                        y = 4f;
                        followRotation = false;
                        color1 = Color.valueOf("FF5845");
                        color2 = Color.valueOf("FF6464");
                        sides = 2;
                        radius = 0f;
                        radiusTo = 28f;
                        stroke = 1.6f;
                        spinSpeed = 12f;
                        rotation = 0f;
                        innerRadScl = 0.4f;
                        innerScl = 0.5f;
                        layer = Layer.effect;
                        progress = PartProgress.charge.compress(0.85f, 0.95f);
                    }},

                    new FlarePart(){{
                        y = 4f;
                        followRotation = false;
                        color1 = Color.valueOf("FF5845");
                        color2 = Color.valueOf("FF6464");
                        sides = 2;
                        radius = 0f;
                        radiusTo = 28f;
                        stroke = 1.6f;
                        spinSpeed = -12f;
                        rotation = 0f;
                        innerRadScl = 0.4f;
                        innerScl = 0.5f;
                        layer = Layer.effect;
                        progress = PartProgress.charge.compress(0.85f, 0.95f);
                    }},

                    new HaloPart(){{
                        y = 4f;
                        tri = true;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF6464");
                        radius = 0f;
                        radiusTo = 2f;
                        triLength = 4f;
                        triLengthTo = 10f;
                        haloRadius = 0f;
                        haloRotateSpeed = -16f;
                        layer = Layer.effect;
                        progress = PartProgress.charge.compress(0.7f, 0.95f);
                    }},

                    new HaloPart(){{
                        y = 4f;
                        tri = true;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF6464");
                        radius = 0f;
                        radiusTo = 1.6f;
                        triLength = 1.6f;
                        triLengthTo = 10f;
                        shapeRotation = 180f;
                        haloRadius = 0f;
                        haloRotateSpeed = 12f;
                        layer = Layer.effect;
                        progress = PartProgress.charge.compress(0.7f, 0.9f);
                    }},

                    new HaloPart(){{
                        y = 4f;
                        tri = true;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF6464");
                        radius = 0f;
                        radiusTo = 1.6f;
                        triLength = 1.6f;
                        triLengthTo = 6f;
                        shapeRotation = 180f;
                        haloRadius = 0f;
                        haloRadiusTo = 3.5f;
                        haloRotateSpeed = 12f;
                        layer = Layer.effect;
                        progress = PartProgress.charge.compress(0.7f, 0.9f);
                    }},

                    new HaloPart(){{
                        y = 4f;
                        tri = true;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF6464");
                        radius = 0f;
                        radiusTo = 1.5f;
                        triLength = 0f;
                        triLengthTo = 8f;
                        haloRadius = 0f;
                        haloRadiusTo = 1.5f;
                        haloRotateSpeed = 3f;
                        layer = Layer.effect;
                        progress = PartProgress.charge.compress(0.2f, 0.7f);
                    }},

                    new HaloPart(){{
                        y = 4f;
                        tri = true;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF6464");
                        radius = 0f;
                        radiusTo = 1.5f;
                        triLength = 0f;
                        triLengthTo = 12f;
                        haloRadius = 0f;
                        haloRadiusTo = 1.5f;
                        haloRotateSpeed = 6f;
                        layer = Layer.effect;
                        progress = PartProgress.charge.compress(0.4f, 1f);
                    }},

                    new HaloPart(){{
                        y = 4f;
                        shapes = 1;
                        sides = 256;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF6464");
                        radius = 0f;
                        radiusTo = 3f;
                        triLength = 0f;
                        triLengthTo = 3f;
                        haloRadius = 0f;
                        layer = Layer.effect - 0.1f;
                        progress = PartProgress.charge.compress(0f, 0.8f);
                    }},

                    new HaloPart(){{
                        y = 4f;
                        shapes = 1;
                        sides = 256;
                        color = Color.valueOf("FF584500");
                        colorTo = Color.valueOf("FF6464");
                        radius = 1f;
                        radiusTo = 1f;
                        triLength = 1f;
                        triLengthTo = 1f;
                        haloRadius = 0f;
                        layer = Layer.effect;
                        progress = PartProgress.charge.compress(0f, 0.0001f);
                    }},

                    new HaloPart(){{
                        y = 4f;
                        shapes = 1;
                        sides = 256;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF646400");
                        radius = 1f;
                        radiusTo = 0f;
                        triLength = 1f;
                        triLengthTo = 0f;
                        haloRadius = 0f;
                        layer = Layer.effect;
                        progress = PartProgress.reload;
                    }},

                    new EffectSpawnerPart(){{
                        y = 4f;
                        effect = new ParticleEffect(){{
                            region = "lp-plasma";
                            particles = 1;
                            length = -50f;
                            baseLength = 50f;
                            randLength = false;
                            interp = Interp.circleIn;
                            lifetime = 30f;
                            spin = 2f;
                            sizeFrom = 6f;
                            sizeTo = 2f;
                            colorFrom = Color.valueOf("FF6464");
                            colorTo = Color.valueOf("FF584590");
                            layer = 110f;
                        }};
                        effectChance = 0.55f;
                        progress = PartProgress.charge;
                    }},

                    new EffectSpawnerPart(){{
                        y = 4f;
                        effect = new ParticleEffect(){{
                            line = true;
                            particles = 1;
                            length = -55f;
                            baseLength = 55f;
                            randLength = false;
                            interp = Interp.circleIn;
                            lenFrom = 0f;
                            lenTo = 12f;
                            sizeFrom = 1.2f;
                            sizeTo = 1.2f;
                            colorFrom = Color.valueOf("FF584590");
                            colorTo = Color.valueOf("FF6464");
                            layer = 110f;
                        }};
                        effectChance = 0.4f;
                        progress = PartProgress.charge;
                    }},

                    new EffectSpawnerPart(){{
                        y = 4f;
                        effect = new ParticleEffect(){{
                            line = true;
                            particles = 1;
                            length = -70f;
                            baseLength = 70f;
                            randLength = false;
                            interp = Interp.circleIn;
                            lenFrom = 0f;
                            lenTo = 18f;
                            sizeFrom = 1.2f;
                            sizeTo = 1.2f;
                            colorFrom = Color.valueOf("FF584590");
                            colorTo = Color.valueOf("FF6464");
                            layer = 110f;
                        }};
                        effectChance = 0.2f;
                        progress = PartProgress.reload;
                    }},

                    new RegionPart(){{
                        outline = false;
                        suffix = "-glow1";
                        blending = Blending.additive;
                        color = Color.valueOf("FF584500");
                        colorTo = Color.valueOf("FF584550");
                        progress = PartProgress.charge.sin(0f, 5f, 15f);
                    }},

                    new RegionPart(){{
                        outline = false;
                        suffix = "-glow2";
                        blending = Blending.additive;
                        color = Color.valueOf("FF584500");
                        colorTo = Color.valueOf("FF584550");
                        progress = PartProgress.charge.sin(60f, 5f, 15f);
                    }},

                    new RegionPart(){{
                        outline = false;
                        suffix = "-glow3";
                        blending = Blending.additive;
                        color = Color.valueOf("FF584500");
                        colorTo = Color.valueOf("FF584550");
                        progress = PartProgress.charge.sin(120f, 5f, 15f);
                    }},

                    new RegionPart(){{
                        outline = false;
                        suffix = "-glow4";
                        blending = Blending.additive;
                        color = Color.valueOf("FF584500");
                        colorTo = Color.valueOf("FF584550");
                        progress = PartProgress.charge.sin(180f, 5f, 15f);
                    }},

                    new RegionPart(){{
                        outline = false;
                        suffix = "-glow5";
                        blending = Blending.additive;
                        color = Color.valueOf("FF584500");
                        colorTo = Color.valueOf("FF584550");
                        progress = PartProgress.charge.sin(240f, 5f, 15f);
                    }},

                    new RegionPart(){{
                        outline = false;
                        suffix = "-glow6";
                        blending = Blending.additive;
                        color = Color.valueOf("FF584500");
                        colorTo = Color.valueOf("FF584550");
                        progress = PartProgress.charge.sin(300f, 5f, 15f);
                    }}
                );
            }};
            shootType = new EnergyBulletType(){{
                sprite = "lp-plasma";
                width = 9f;
                height = 9f;
                shrinkY = 0f;
                hitColor = Color.valueOf("FF6464");
                lightColor = Color.valueOf("FF6464");
                backColor = Color.valueOf("FF6464");
                frontColor = Color.valueOf("FF6464");
                trailColor = Color.valueOf("FF6464");
                trailWidth = 3f;
                trailLength = 6;
                speed = 56f;
                lifetime = 10f;
                maxRange = rangeOverride = 560f;
                damage = 0f;
                energyDamage = 240f;
                shieldEnergyDamageMultiplier = 3f;
                ammoMultiplier = 1f;
                armorMultiplier = 1.6f;
                buildingDamageMultiplier = 0.3f;
                splashDamage = 300f;
                splashDamageRadius = 120f;
                smokeEffect = Fx.none;
                shootEffect = Fx.none;
                hitSound = LPSounds.acceleratorLaunch;
                hitSoundVolume = 0.9f;
                hitShake = 16f;
                despawnHit = true;
                hitEffect = LPFx.cloundpiercerHitEffect;
                parts = Seq.with(
                    new HaloPart(){{
                        tri = true;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF6464");
                        radius = 3f;
                        radiusTo = 3f;
                        triLength = 16f;
                        triLengthTo = 16f;
                        haloRadius = 0f;
                        haloRotateSpeed = 12f;
                        layer = Layer.effect;
                    }},

                    new HaloPart(){{
                        tri = true;
                        color = Color.valueOf("FF6464");
                        colorTo = Color.valueOf("FF6464");
                        radius = 3f;
                        radiusTo = 3f;
                        triLength = 10f;
                        triLengthTo = 10f;
                        haloRadius = 0f;
                        haloRotateSpeed = -12f;
                        layer = Layer.effect;
                    }}
                );
            }};
        }};

        fallenstar = new LPItemTurret("fallenstar"){{
            size = 5;
            health = 844;
            armor = 10;
            requirements(Category.turret, with(LPItems.transchimericsteel, 296, LPItems.jynsteel, 217, LPItems.crystalite, 83, LPItems.erocrys, 68));
            rotateSpeed = 4f;
            reload = 160f;
            range = 440f;
            trackingRange = 600f;
            recoil = 3f;
            recoilTime = 60f;
            shake = 6f;
            shootCone = 2;
            inaccuracy = 2f;
            outlineColor = LPPal.outline;
            shootSound = LPSounds.shootPulse2;
            shootSoundVolume = 1.4f;
            maxAmmo = 64;
            ammoPerShot = 12;
            targetGround = false;
            targetAir = true;
            consumePower(7.5f);
            priority = -1f;
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            buildTime = 180f;
            databaseTag = "ps";
            destroySound = LPSounds.blockExplode3;
            destroySoundVolume = 1.1f;
            destroyEffect = LPFx.fallenstarDestroy;
            shoot = new ShootBarrel(){{
                barrels = new float[]{3, 4, 0, -3, 4, 0};
                shots = 12;
                shotDelay = 5f;
            }};
            shootY = 6f;
            ammo(
                LPItems.transchimericsteel, new BasicBulletType(){{
                    sprite = "lp-pointy-bullet";
                    width = 6f;
                    height = 8f;
                    shrinkY = 0f;
                    hitColor = lightColor = backColor = frontColor = trailColor = LPPal.aureusMid;
                    trailWidth = 2f;
                    trailLength = 9;
                    damage = 60f;
                    armorMultiplier = 0.55f;
                    ammoMultiplier = 1f;
                    speed = 8f;
                    lifetime = 54f;
                    scaleLife = true;
                    collidesGround = false;
                    absorbable = false;
                    maxRange = 440f;
                    rangeOverride = 440f;
                    homingRange = 80f;
                    homingDelay = 16f;
                    homingPower = 0.4f;
                    hitSound = LPSounds.shootBlaster2;
                    hitSoundVolume = 1.2f;
                    hitShake = 7f;
                    hitEffect = new MultiEffect(
                        new WrapEffect(LPFx.fallenStar, LPPal.aureusMid, 1f),
                        new ParticleEffect(){{
                            particles = 5;
                            region = "lp-square";
                            lifetime = 30f;
                            length = 32f;
                            baseLength = 2f;
                            interp = Interp.pow4Out;
                            sizeInterp = Interp.pow2In;
                            sizeFrom = 4f;
                            sizeTo = 0f;
                            colorFrom = LPPal.aureusMid;
                            colorTo = LPPal.aureusMid;
                        }}
                    );
                    shootEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 5;
                            line = true;
                            lifetime = 30f;
                            length = 40f;
                            baseLength = 2f;
                            cone = 5f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 8f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = LPPal.aureusMid;
                            colorTo = LPPal.aureusMid;
                        }},
                        new ParticleEffect(){{
                            particles = 4;
                            line = true;
                            lifetime = 30f;
                            length = 40f;
                            baseLength = 2f;
                            cone = 8f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 12f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = LPPal.aureusMid;
                            colorTo = LPPal.aureusMid;
                        }}
                    );
                    smokeEffect = new ParticleEffect(){{
                        particles = 5;
                        lifetime = 40f;
                        length = 32f;
                        baseLength = 2f;
                        cone = 5f;
                        interp = Interp.pow4Out;
                        sizeInterp = Interp.pow5In;
                        sizeFrom = 3;
                        sizeTo = 0;
                        colorFrom = Color.valueOf("676767");
                        colorTo = Color.valueOf("45454500");
                    }};
                    despawnEffect = Fx.none;
                    despawnHit = true;
                }}
            );
        }};

        repelback = new LPItemTurret("repelback"){{
            size = 5;
            health = 847;
            armor = 11f;
            requirements(Category.turret, with(LPItems.transchimericsteel, 341, LPItems.massisteel, 216, LPItems.crystalite, 73));
            rotateSpeed = 3f;
            reload = 20f;
            range = 360f;
            trackingRange = 540f;
            recoil = 5f;
            recoilTime = 20f;
            shake = 5f;
            inaccuracy = 5f;
            minWarmup = 0.999f;
            shootWarmupSpeed = 0.05f;
            warmupMaintainTime = 180f;
            cooldownTime = 120f;
            heatColor = LPPal.redDark;
            canOverdrive = false;
            maxAmmo = 48;
            ammoPerShot = 2;
            databaseTag = "zd";
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            buildTime = 180f;
            destroySound = LPSounds.blockExplodeExplosiveAlt;
            destroySoundVolume = 2f;
            destroyEffect = LPFx.repelbackDestroy;
            consumePower(8.5f);
            heatRequirement = 12;
            maxHeatEfficiency = 2;
            outlineColor = LPPal.outline;
            drawer = new DrawTurret(){{
                parts = Seq.with(
                    new RegionPart(){{
                        under = false;
                        outline = false;
                        mirror = false;
                        suffix = "-glow";
                        blending = Blending.additive;
                        color = Color.valueOf("FF404000");
                        colorTo = LPPal.redDark;
                        heatProgress = PartProgress.warmup.add(PartProgress.heat);
                        progress = PartProgress.warmup.add(PartProgress.heat);
                    }},

                    new RegionPart(){{
                        y = 8;
                        under = false;
                        outline = false;
                        mirror = false;
                        suffix = "-rail";
                        blending = Blending.additive;
                        xScl = 0.19f;
                        yScl = 0.19f;
                        color = Color.valueOf("FFF58900");
                        colorTo = LPPal.aureus;
                        rotation = 90f;
                        layer = 110f;
                        moveY = 12f;
                        progress = PartProgress.warmup.compress(0f,0.5f);
                    }},

                    new RegionPart(){{
                        y = 8;
                        under = false;
                        outline = false;
                        mirror = false;
                        suffix = "-rail";
                        blending = Blending.additive;
                        xScl = 0.19f;
                        yScl = 0.19f;
                        color = Color.valueOf("FFF58900");
                        colorTo = LPPal.aureus;
                        rotation = 90f;
                        layer = 110f;
                        moveY = 36f;
                        progress = PartProgress.warmup.compress(0.3f,0.75f);
                    }},

                    new RegionPart(){{
                        y = 8;
                        under = false;
                        outline = false;
                        mirror = false;
                        suffix = "-rail";
                        blending = Blending.additive;
                        xScl = 0.19f;
                        yScl = 0.19f;
                        color = Color.valueOf("FFF58900");
                        colorTo = LPPal.aureus;
                        rotation = 90f;
                        layer = 110f;
                        moveY = 60f;
                        progress = PartProgress.warmup.compress(0.6f,1f);
                    }},

                    new RegionPart(){{
                        x = 9f;
                        y = 20f;
                        under = false;
                        outline = false;
                        mirror = true;
                        xScl = yScl = 0f;
                        color = colorTo = Color.valueOf("000000");
                        layer = 110f;
                        moveX = 4f;
                        progress = PartProgress.recoil;
                        children = Seq.with(
                            new HaloPart(){{
                                x = 4f;
                                moveX = -4f;
                                y = 0f;
                                tri = true;
                                color = colorTo = LPPal.aureus;
                                shapes = 1;
                                radius = 0f;
                                radiusTo = 1.5f;
                                triLength = 0f;
                                triLengthTo = 120f;
                                haloRadius = 0f;
                                progress = PartProgress.warmup.compress(0f,0.4f);
                            }},

                            new HaloPart(){{
                                x = 4f;
                                moveX = -4f;
                                y = 0.4f;
                                tri = true;
                                color = colorTo = LPPal.aureus;
                                shapes = 1;
                                radius = 0f;
                                radiusTo = 1.5f;
                                triLength = 0f;
                                triLengthTo = 16f;
                                shapeRotation = 180f;
                                haloRotation = 45f;
                                haloRadius = 0f;
                                progress = PartProgress.warmup.compress(0f,0.4f);
                            }}
                        );
                    }}
                );
            }};
            shootY = 8f;
            shootCone = 10f;
            shootSound = LPSounds.shootRepelback;
            ammo(
                LPItems.transchimericsteel, new SplashKnockbackBulletType(){{
                    sprite = "lp-pointy-bullet";
                    width = 12f;
                    height = 16f;
                    shrinkY = 0f;
                    hitSize = 256f;
                    lightRadius = 24f;
                    hitColor = lightColor = backColor = frontColor = trailColor = LPPal.aureus;
                    trailRotation = true;
                    trailWidth = 4f;
                    trailLength = 4;
                    trailInterval = 0.2f;
                    trailEffect = new ParticleEffect(){{
                        particles = 3;
                        region = "lp-triangle";
                        lifetime = 15f;
                        length = 24f;
                        baseLength = 3f;
                        baseRotation = 180f;
                        cone = 30f;
                        interp = Interp.circleOut;
                        sizeInterp = Interp.pow2In;
                        sizeFrom = 3.5f;
                        sizeTo = 0f;
                        colorFrom = LPPal.aureus;
                        colorTo = LPPal.aureus;
                    }};
                    hitSize = 12f;
                    rangeOverride = 360f;
                    maxRange = 360f;
                    speed = 36f;
                    lifetime = 10f;
                    damage = 24f;
                    splashDamage = 32f;
                    splashDamageRadius = 64f;
                    splashKnockback = 48f;
                    splashKnockbackRadius = 64f;
                    ammoMultiplier = 1;
                    armorMultiplier = 1.4f;
                    hitSound = LPSounds.martianCrash;
                    hitSoundVolume = 1f;
                    hitShake = 7f;
                    despawnEffect = Fx.none;
                    despawnHit = true;
                    hitEffect = LPFx.repelbackHit;
                }}
            );
        }};

        hushstrike = new LPPowerTurret("hushstrike"){{
            size = 5;
            health = 822;
            armor = 10f;
            requirements(Category.turret, with(LPItems.transchimericsteel, 284, LPItems.massisteel, 132, LPItems.jynsteel, 105, LPItems.crystalite, 95));
            rotateSpeed = 4f;
            reload = 180f;
            range = 440f;
            trackingRange = 660f;
            recoil = 7f;
            recoilTime = 60f;
            shake = 7f;
            cooldownTime = 120f;
            heatColor = LPPal.redDark;
            canOverdrive = true;
            databaseTag = "gr";
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            buildTime = 180f;
            destroySound = LPSounds.blockExplodeExplosiveAlt;
            destroySoundVolume = 2.2f;
            destroyEffect = LPFx.hushstrikeDestroy;
            outlineColor = LPPal.outline;
            consumePower(8f);
            shootY = 8f;
            shootSound = LPSounds.shootPulse3;
            shootCone = 2f;
            shootType = new BasicBulletType(){{
                width = height = shrinkY = 0f;
                parts = Seq.with(
                    new HaloPart(){{
                        color = colorTo = LPPal.purple;
                        sides = 256;
                        shapes = 1;
                        radius = 5f;
                        radiusTo = 5f;
                        triLength = 5f;
                        triLengthTo = 5f;
                        haloRadius = 0f;
                        layer = 110;
                        progress = PartProgress.life;
                    }},

                    new HaloPart(){{
                        hollow = true;
                        color = colorTo = LPPal.purple;
                        sides = 256;
                        shapes = 1;
                        radius = 8f;
                        radiusTo = 0f;
                        triLength = 8f;
                        triLengthTo = 0f;
                        stroke = strokeTo = 1.5f;
                        haloRadius = 0f;
                        layer = 110;
                        progress = PartProgress.life.curve(Interp.pow10In);
                    }},

                    new HaloPart(){{
                        tri = true;
                        color = colorTo = LPPal.purple;
                        sides = 256;
                        shapes = 2;
                        radius = 5f;
                        radiusTo = 0f;
                        triLength = 20f;
                        triLengthTo = 0f;
                        haloRadius = 0f;
                        haloRotateSpeed = -4f;
                        layer = 110;
                        progress = PartProgress.life.curve(Interp.pow10In);
                    }},

                    new HaloPart(){{
                        tri = true;
                        color = colorTo = LPPal.purple;
                        sides = 256;
                        shapes = 2;
                        radius = 5f;
                        radiusTo = 0f;
                        triLength = 28f;
                        triLengthTo = 0f;
                        haloRadius = 0f;
                        haloRotateSpeed = 3f;
                        layer = 110;
                        progress = PartProgress.life.curve(Interp.pow10In);
                    }}
                );
                hitSize = 16f;
                trailInterval = 1f;
                trailRotation = true;
                trailEffect = new ParticleEffect(){{
                    particles = 4;
                    region = "lp-triangle";
                    lifetime = 20f;
                    length = 24f;
                    baseLength = 3f;
                    baseRotation = 180f;
                    cone = 60f;
                    interp = Interp.circleOut;
                    sizeInterp = Interp.pow2In;
                    sizeFrom = 3.5f;
                    sizeTo = 0f;
                    colorFrom = colorTo = LPPal.purple;
                }};
                rangeOverride = 440f;
                maxRange = 440f;
                speed = 8f;
                lifetime = 54.5f;
                damage = 0f;
                ammoMultiplier = 1f;
                hitShake = 8f;
                hitSound = LPSounds.shootCoil1;
                hitSoundVolume = 2f;
                hitEffect = new MultiEffect(
                    new WrapEffect(LPFx.fallenStar, LPPal.purple, 2.4f),
                    LPFx.circleOut(60f, LPPal.purple, 72f),
                    LPFx.smoothCircleOut(60f, LPPal.purple, 72f, 80, true)
                );
                despawnEffect = Fx.none;
                despawnHit = true;
                fragBullets = 3;
                fragOffsetMin = 16f;
                fragOffsetMax = 64f;
                fragLifeMax = 1f;
                fragLifeMin = 0.8f;
                fragBullet = new LightningLinkBulletType(){{
                    sprite = "lp-stardart";
                    width = height = 9f;
                    shrinkY = 0.8f;
                    spin = Mathf.random(-3f, 3f);
                    size = 0f;
                    speed = 0f;
                    lifetime = 55f;
                    hitColor = lightColor = backColor = frontColor = LPPal.purple;
                    trailColor = Color.valueOf("00000000");
                    damage = 0f;
                    ammoMultiplier = 1f;
                    lightningLinkDamage = 16f;
                    linkRange = 96f;
                    hitSpacing = 2f;
                    maxHit = 9;
                    boltWidth = 3f;
                    randomGenerateRange = 120f;
                    randomGenerateChance = 0.2f;
                    randomLightningChance = 0.3f;
                    randomLightningNum = 2;
                    effectLingtning = 3;
                    effectLightningChance = 0.3f;
                    effectLightningLength = 8;
                    effectLightningLengthRand = 8;
                    trueHitChance = 0.9f;
                    liHitEffect = new WrapEffect(LPFx.lightningSpark, LPPal.purple, 10f);
                    slopeEffect = Fx.none;
                    spreadEffect = Fx.none;
                    hitEffect = Fx.none;
                    despawnEffect = Fx.none;
                    status = LPStatusEffect.empII;
                    statusDuration = 120f;
                    splashDamage = 0f;
                    armorMultiplier = 2f;
                    shieldDamageMultiplier = 1.8f;
                }};
            }};
        }};

        crimsondwarf = new AimLPPowerTurret("crimsondwarf"){{
            size = 5;
            health = 877;
            armor = 12f;
            requirements(Category.turret, with(LPItems.transchimericsteel, 314, LPItems.jynsteel, 107, LPItems.massisteel, 76, LPItems.crystalite, 74));
            rotateSpeed = 2.7f;
            reload = 400f;
            range = 480f;
            trackingRange = 576f;
            recoil = 6f;
            recoilTime = 60f;
            shake = 10f;
            cooldownTime = 60f;
            heatColor = LPPal.redDark;
            canOverdrive = false;
            databaseTag = "xn";
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            buildTime = 180f;
            shootSound = LPSounds.energyHit;
            shootSoundVolume = 2.5f;
            chargeSound = LPSounds.chargeSubsonic;
            destroySound = LPSounds.blockExplodeExplosiveAlt;
            destroySoundVolume = 2.2f;
            destroyEffect = LPFx.crimsondwarfDestroy;
            consumePower(10.5f);
            outlineColor = LPPal.outline;
            shootY = 24f;
            shootCone = 2f;
            shoot.firstShotDelay = 120f;
            drawer = new DrawTurret(){{
                parts.addAll(
                    new HaloPart(){{
                        y = 12f;
                        moveY = 12f;
                        color = colorTo = Color.valueOf("000000");
                        sides = 256;
                        shapes = 1;
                        radius = 0;
                        radiusTo = 6;
                        triLength = 0;
                        triLengthTo = 6;
                        haloRadius = 0f;
                        layer = 111f;
                        progress = PartProgress.charge.mul(0f).sin(1f, 0.15f).add(1f).compress(0f, 0.9f).mul(PartProgress.charge.curve(Interp.pow4Out));
                    }},

                    new HaloPart(){{
                        y = 12f;
                        moveY = 12f;
                        color = colorTo = Color.valueOf("FF6464");
                        sides = 256;
                        shapes = 1;
                        radius = 0;
                        radiusTo = 9;
                        triLength = 0;
                        triLengthTo = 9;
                        haloRadius = 0f;
                        layer = 110f;
                        progress = PartProgress.charge.mul(0f).sin(1f, 0.15f).add(1f).compress(0f, 0.9f).mul(PartProgress.charge.curve(Interp.pow4Out));
                    }},

                    new HaloPart(){{
                        y = 12f;
                        moveY = 12f;
                        tri = true;
                        color = colorTo = Color.valueOf("FF6464");
                        radius = 0;
                        radiusTo = 4;
                        triLength = 0;
                        triLengthTo = 18;
                        haloRadius = 0f;
                        haloRotateSpeed = 4f;
                        layer = 110f;
                        progress = PartProgress.charge.mul(0f).sin(1f, 0.15f).add(1f).compress(0f, 0.4f).mul(PartProgress.charge.curve(Interp.pow4Out));
                    }},

                    new HaloPart(){{
                        y = 12f;
                        moveY = 12f;
                        tri = true;
                        color = colorTo = Color.valueOf("FF6464");
                        radius = 0;
                        radiusTo = 4;
                        triLength = 0;
                        triLengthTo = 28;
                        haloRadius = 0f;
                        haloRotateSpeed = 4f;
                        layer = 110f;
                        progress = PartProgress.charge.mul(0f).sin(1f, 0.2f).add(1f).compress(0.6f, 0.8f).mul(PartProgress.charge.curve(Interp.pow4Out));
                    }},

                    new HaloPart(){{
                        y = 12f;
                        moveY = 12f;
                        tri = true;
                        color = colorTo = Color.valueOf("FF6464");
                        radius = 0;
                        radiusTo = 4;
                        triLength = 0;
                        triLengthTo = 20;
                        haloRadius = 0f;
                        haloRotateSpeed = -8f;
                        layer = 110f;
                        progress = PartProgress.charge.mul(0f).sin(1f, 0.2f).add(1f).compress(0.8f, 1f).mul(PartProgress.charge.curve(Interp.pow4Out));
                    }},

                    new RegionPart(){{
                        under = false;
                        outline = false;
                        mirror = false;
                        suffix = "-glow";
                        blending = Blending.additive;
                        color = Color.valueOf("FF404000");
                        colorTo = LPPal.redDark;
                        progress = PartProgress.charge.compress(0f, 0.4f);
                    }},

                    new RegionPart(){{
                        under = false;
                        outline = false;
                        mirror = false;
                        suffix = "-glow";
                        blending = Blending.additive;
                        color = Color.valueOf("FF404000");
                        colorTo = LPPal.redDark;
                        progress = PartProgress.recoil;
                    }},

                    new EffectSpawnerPart(){{
                        y = 24f;
                        effect = new ParticleEffect(){{
                            line = true;
                            particles = 1;
                            length = -60;
                            baseLength = 60;
                            randLength = false;
                            interp = Interp.circleIn;
                            lifetime = 18;
                            lenFrom = 0;
                            lenTo = 12;
                            strokeFrom = 1.2f;
                            strokeTo = 1.2f;
                            colorFrom = LPPal.redDark;
                            colorTo = Color.valueOf("FF6464");
                            layer = 110f;
                        }};
                        effectChance = 0.4f;
                        progress = PartProgress.charge.mul(0f).sin(1f, 0.2f).add(1.2f).mul(PartProgress.charge.curve(Interp.pow4Out));
                    }}
                );
            }};
            shootType = new LightningLinkBulletType(){
                {
                    parts.addAll(
                        new HaloPart(){{
                            color = colorTo = Color.valueOf("000000");
                            sides = 256;
                            shapes = 1;
                            radius = 6;
                            radiusTo = 6;
                            triLength = 6;
                            triLengthTo = 6;
                            haloRadius = 0f;
                            layer = 111f;
                            progress = PartProgress.life.curve(Interp.pow10In);
                        }},

                        new HaloPart(){{
                            color = colorTo = Color.valueOf("FF6464");
                            sides = 256;
                            shapes = 1;
                            radius = 9;
                            radiusTo = 9;
                            triLength = 9;
                            triLengthTo = 9;
                            haloRadius = 0f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.pow10In);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = Color.valueOf("FF6464");
                            radius = 4;
                            radiusTo = 4;
                            triLength = 28;
                            triLengthTo = 0;
                            haloRadius = 0f;
                            haloRotateSpeed = 4f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.pow10In);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = Color.valueOf("FF6464");
                            radius = 4;
                            radiusTo = 4;
                            triLength = 20;
                            triLengthTo = 0;
                            haloRadius = 0f;
                            haloRotateSpeed = -8f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.pow10In);
                        }}
                    );
                    backColor = trailColor = hitColor = lightColor = lightningColor = Color.valueOf("FF6464");
                    frontColor = LPPal.redMidDark;
                    trailWidth = 4f;
                    lightRadius = 12f;
                    lightColor = Color.valueOf("FF6464");
                    ammoMultiplier = 1f;
                    armorMultiplier = 1.4f;
                    buildingDamageMultiplier = 0.4f;
                    shieldDamageMultiplier = 2.25f;
                    damage = 540f;
                    splashDamage = 240f;
                    splashDamageRadius = 120f;
                    speed = 5f;
                    lifetime = 480f / speed;
                    range = rangeOverride = 480f;
                    scaleLife = despawnHit = true;
                    collidesGround = collidesAir = true;
                    trailInterval = 4f;
                    trailEffect = new ParticleEffect(){{
                        particles = 8;
                        line = true;
                        lifetime = 20f;
                        length = 12f;
                        baseLength = 1f;
                        interp = Interp.circleOut;
                        sizeInterp = Interp.circleIn;
                        lenFrom = 12f;
                        lenTo = 0f;
                        strokeFrom = 1.5f;
                        strokeTo = 0f;
                        colorFrom = Color.valueOf("FF6464");
                        colorTo = LPPal.redDark;
                    }};
                    lightningLinkDamage = 32f;
                    linkRange = 160f;
                    hitSpacing = 6f;
                    maxHit = 9;
                    boltWidth = 4f;
                    randomGenerateRange = 160f;
                    randomGenerateChance = 0.3f;
                    randomLightningChance = 0.5f;
                    randomLightningNum = 3;
                    slopeEffect = liHitEffect = spreadEffect = Fx.none;
                    smokeEffect = Fx.none;
                    shootEffect = LPFx.crimsondwarfShoot;
                    chargeEffect = new SoundEffect(LPSounds.chargeSubsonic, Fx.none){{
                        minPitch = maxPitch = 1f;
                        maxVolume = minVolume = 2f;
                    }};
                    hitSound = LPSounds.shootLaserBeam;
                    hitShake = 4f;
                    hitEffect = new MultiEffect(
                        LPFx.circleOut(60f, Color.valueOf("FF6464"), 120f),
                        LPFx.smoothCircleOut(60f, Color.valueOf("FF6464"), 120f, 40, true),
                        LPFx.BlackHoleHit(Color.valueOf("FF6464"), 60f, 24f),
                        LPFx.trailHitSpark(60f, Color.valueOf("FF6464"), 18, 120f, 1.5f, 16f),
                        LPFx.sharpHitSpark(60f, Color.valueOf("FF6464"), 8, 120f, 32f, Interp.circleOut),
                        new ParticleEffect(){{
                            particles = 18;
                            line = true;
                            lifetime = 60f;
                            length = 72f;
                            baseLength = 6f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 12f;
                            lenTo = 0f;
                            strokeFrom = 1.6f;
                            strokeTo = 0f;
                            colorFrom = Color.valueOf("FF6464");
                            colorTo = Color.valueOf("FF6464");
                        }},

                        new Effect(160f, (e) -> {
                            if (e.time % 2 == 0) {
                                for (int i = 0; i < 3; i++) {
                                    LP.graphics.Drawn.randFadeLightningEffect(e.x, e.y, Mathf.random(160), Mathf.random(2, 4), hitColor, Mathf.chance(0.5));
                                }
                            }
                        })
                    );
                    despawnEffect = Fx.none;
                }
                
                @Override
                public void update(Bullet b) {
                    super.update(b);
                    
                    if (b.timer(1, 4)) {
                        for (int l = 0; l < 3; l++) {
                            LP.graphics.Drawn.randFadeLightningEffect(b.x, b.y, Mathf.random(160), Mathf.random(4, 7), hitColor, Mathf.chance(0.5));
                        }
                    }
                }
            };
            drawAimPoint = true;
        }};

        infernoblade = new LPLaserTurret("infernoblade"){{
            size = 5;
            health = 868;
            armor = 11f;
            requirements(Category.turret, with(LPItems.transchimericsteel, 302, LPItems.massisteel, 287, LPItems.jynsteel, 95, LPItems.crystalite, 82));
            rotateSpeed = 2.4f;
            reload = 240f;
            range = 192f;
            trackingRange = 288f;
            recoil = 4f;
            recoilTime = 60f;
            shake = 3f;
            cooldownTime = 90f;
            heatColor = LPPal.redDark;
            minWarmup = 0.999f;
            shootWarmupSpeed = 0.2f;
            warmupMaintainTime = 360f;
            firingMoveFract = 0.3f;
            shootCone = 8f;
            shootDuration = 320f;
            canOverdrive = false;
            targetAir = false;
            databaseTag = "jg";
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            buildTime = 180f;
            shootSound = LPSounds.largeBeam;
            shootSoundVolume = 0f;
            loopSound = LPSounds.largeBeam;
            loopSoundVolume = 6f;
            destroySound = LPSounds.blockExplodeExplosiveAlt;
            destroySoundVolume = 2.2f;
            destroyEffect = LPFx.infernobladeDestroy;
            consumePower(16f);
            shootY = 20f;
            drawer = new DrawTurret(){{
                parts.addAll(
                    new RegionPart(){{
                        suffix = "-barrel";
                        heatColor = LPPal.redDark;
                        heatProgress = PartProgress.heat;
                        progress = PartProgress.warmup;
                        under = false;
                        mirror = false;
                        outline = true;
                        replaceOutline = true;
                        moveY = -4f;
                    }},

                    new RegionPart(){{
                        suffix = "-bottom";
                        heatColor = LPPal.redDark;
                        heatProgress = PartProgress.heat;
                        progress = PartProgress.warmup;
                        under = true;
                        mirror = true;
                        outline = true;
                        moveY = 8f;
                        moveX = 2f;
                        moveRot = 12f;
                        layerOffset = -0.01f;
                    }},

                    new RegionPart(){{
                        suffix = "-side";
                        heatColor = LPPal.redDark;
                        heatProgress = PartProgress.heat;
                        progress = PartProgress.warmup;
                        under = true;
                        mirror = true;
                        outline = true;
                        moveX = 12f;
                        moveRot = -45f;
                        layerOffset = -0.01f;
                    }},

                    new HaloPart(){{
                        y = 20f;
                        moveX = 4f;
                        tri = true;
                        mirror = true;
                        color = colorTo = LPPal.aureus;
                        shapes = 1;
                        radius = 0f;
                        radiusTo = 1.2f;
                        triLength = 0f;
                        triLengthTo = 180f;
                        haloRadius = 0f;
                        layer = 110f;
                        progress = PartProgress.recoil.compress(0f, 0.8f).curve(Interp.circleIn);
                    }},

                    new HaloPart(){{
                        y = 20f;
                        moveX = 3.5f;
                        tri = true;
                        mirror = true;
                        color = colorTo = LPPal.aureus;
                        shapes = 1;
                        radius = 0f;
                        radiusTo = 1.2f;
                        triLength = 0f;
                        triLengthTo = 160f;
                        haloRadius = 0f;
                        layer = 110f;
                        progress = PartProgress.recoil.compress(0f, 0.8f).curve(Interp.circleIn);
                    }},

                    new HaloPart(){{
                        y = 20f;
                        moveX = 3f;
                        tri = true;
                        mirror = true;
                        color = colorTo = LPPal.aureus;
                        shapes = 1;
                        radius = 0f;
                        radiusTo = 1.2f;
                        triLength = 0f;
                        triLengthTo = 140f;
                        haloRadius = 0f;
                        layer = 110f;
                        progress = PartProgress.recoil.compress(0f, 0.8f).curve(Interp.circleIn);
                    }},

                    new HaloPart(){{
                        y = 20f;
                        color = colorTo = LPPal.aureus;
                        sides = 256;
                        shapes = 1;
                        radius = 0f;
                        radiusTo = 5.5f;
                        triLength = 0f;
                        triLengthTo = 5.5f;
                        haloRadius = 0f;
                        layer = 110f;
                        progress = PartProgress.recoil.compress(0f, 0.8f).curve(Interp.circleIn);
                    }},

                    new ShapePart(){{
                        y = 20f;
                        circle = true;
                        hollow = true;
                        color = colorTo = LPPal.aureus;
                        radius = 0f;
                        radiusTo = 8f;
                        stroke = 0f;
                        strokeTo = 1f;
                        layer = 110f;
                        progress = PartProgress.recoil.compress(0f, 0.8f).curve(Interp.circleIn);
                    }},

                    new HaloPart(){{
                        y = 20f;
                        tri = true;
                        color = colorTo = LPPal.aureus;
                        shapes = 2;
                        radius = 0f;
                        radiusTo = 3f;
                        triLength = 0f;
                        triLengthTo = 18f;
                        haloRadius = 0f;
                        haloRadiusTo = 4f;
                        haloRotateSpeed = 0.45f;
                        layer = 110f;
                        progress = PartProgress.recoil.curve(Interp.circleIn);
                    }},

                    new HaloPart(){{
                        y = 20f;
                        tri = true;
                        color = colorTo = LPPal.aureus;
                        shapes = 2;
                        radius = 0f;
                        radiusTo = 3f;
                        triLength = 0f;
                        triLengthTo = 32f;
                        haloRadius = 0f;
                        haloRadiusTo = 4f;
                        haloRotateSpeed = -0.3f;
                        layer = 110f;
                        progress = PartProgress.recoil.compress(0f, 0.9f).curve(Interp.circleIn);
                    }},

                    new HaloPart(){{
                        y = 20f;
                        tri = true;
                        color = colorTo = Color.black;
                        shapes = 3;
                        radius = 0f;
                        radiusTo = 3f;
                        triLength = 0f;
                        triLengthTo = 16f;
                        haloRadius = haloRadiusTo = 4f;
                        haloRotateSpeed = -0.5f;
                        layer = 110f;
                        progress = PartProgress.recoil.compress(0.7f, 1f).curve(Interp.circleIn);
                    }},

                    new EffectSpawnerPart(){{
                        y = 20f;
                        effect = LPFx.sharpHitSpark(16f, LPPal.aureus, 2, 192, 18f, Interp.pow10In, 3f);
                        effectChance = 0.3f;
                        progress = PartProgress.recoil.curve(Interp.pow5In);
                    }},

                    new EffectSpawnerPart(){{
                        y = 20f;
                        effect = LPFx.sharpHitSpark(16f, LPPal.aureus, 3, 192, 18f, Interp.pow10In, 6f);
                        effectChance = 0.7f;
                        progress = PartProgress.recoil.curve(Interp.circleIn);
                    }}
                );
            }};
            shootType = new HellbladeBulletType(){{
                fadeTime = 10f;
                colors = new Color[]{LPPal.aureusMid, LPPal.aureus, Color.valueOf("E8D074"), Color.valueOf("D89F6B")};
                hitColor = lightColor = LPPal.aureus;
                length = 192f;
                width = 0f;
                oscScl = 0f;
                oscMag = 0f;
                length = 172f;
                drawSize = 0f;
                hitSize = 128f;
                makeFire = false;
                collidesAir = false;
                timescaleDamage = true;
                largeHit = true;
                pierce = true;
                pierceBuilding = true;
                continuous = true;
                pierceCap = 128;
                maxRange = 192;
                damage = 32;
                splashRange = 40f;
                armorMultiplier = 1.8f;
                shieldDamageMultiplier = 3f;
                buildingDamageMultiplier = 0.45f;
                ammoMultiplier = 1f;
                hitShake = 2;
                hitEffect = LPFx.sharpHitSpark(24f, hitColor, 4, 90f, 20f, Interp.pow10In, 60f);
                shootEffect = smokeEffect = Fx.none;
            }};
        }};

        recursion = new LPItemTurret("recursion"){{
            size = 5;
            health = 904;
            armor = 12;
            requirements(Category.turret, with(LPItems.transchimericsteel, 337, LPItems.massisteel, 106, LPItems.jynsteel, 85, LPItems.litelnlay, 82,  LPItems.crystalite, 80));
            rotateSpeed = 2.5f;
            reload = 240f;
            range = 520;
            trackingRange = 780;
            recoil = 6.5f;
            recoilTime = 60;
            shake = 12;
            minWarmup = 0.999f;
            shootWarmupSpeed = 0.08f;
            warmupMaintainTime = 120;
            cooldownTime = 90;
            heatColor = LPPal.redDark;
            canOverdrive = true;
            predictTarget = false;
            maxAmmo = 32;
            ammoPerShot = 4;
            unitSort = UnitSorts.strongest;
            databaseTag = "dn";
            alwaysUnlocked = false;
            researchCostMultiplier = 0.1f;
            buildTime = 180;
            destroySound = LPSounds.blockExplodeExplosiveAlt;
            destroySoundVolume = 2.2f;
            destroyEffect = LPFx.recursionDestroy;
            consumePower(12f);
            heatRequirement = 12f;
            maxHeatEfficiency = 1f;
            drawer = new DrawTurret(){{
                parts.addAll(
                    new RegionPart(){{
                        mirror = true;
                        under = true;
                        suffix = "-side2";
                        progress = PartProgress.warmup;
                        moveX = 2.8f;
                        moveRot = -15f;
                        moves.add(new PartMove(PartProgress.recoil, 0f, -2f, 0f));
                    }},

                    new RegionPart(){{
                        mirror = true;
                        under = true;
                        suffix = "-side1";
                        progress = PartProgress.warmup;
                        moveX = 1.8f;
                        moves.add(new PartMove(PartProgress.recoil, -0.8f, -4f, 0f));
                    }}
                );
            }};
            shootCone = 2f;
            shootY = 4f;
            shootSound = LPSounds.cannonLargeShot;
            shootSoundVolume = 1.5f;
            soundPitchMin = 1.8f;
            soundPitchMax = 2f;
            ammo(
                LPItems.transchimericsteel, new RailBulletType(){{
                    trailColor = hitColor = lightColor = lightningColor = LPPal.aureus;
                    length = 520f;
                    damage = 820f;
                    knockback = 12f;
                    impact = true;
                    pierce = true;
                    pierceBuilding = false;
                    pierceCap = 128;
                    pierceDamageFactor = 0.75f;
                    ammoMultiplier = 1f;
                    armorMultiplier = 0.1f;
                    shieldDamageMultiplier = 0.45f;
                    hitSound = LPSounds.cannonLargeHit;
                    hitSoundVolume = 2.8f;
                    hitShake = 8;
                    shootEffect = new MultiEffect(
                        LPFx.railShoot(30f, LPPal.aureus, 80f, Interp.circleOut),
                        new ParticleEffect(){{
                            particles = 12;
                            line = true;
                            lifetime = 24f;
                            length = 48f;
                            baseLength = 2f;
                            cone = 30f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 24f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureus;
                        }},
                        new ParticleEffect(){{
                            particles = 16;
                            line = true;
                            lifetime = 24f;
                            length = 48f;
                            baseLength = 8f;
                            baseRotation = 180f;
                            cone = 80f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 30f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureus;
                        }}
                    );
                    smokeEffect = Fx.none;
                    hitSound = LPSounds.cannonLargeHit;
                    hitSoundVolume = 2.8f;
                    hitShake = 8;
                    lineEffect = LPFx.chainLightningFadeReversed;
                    pointEffect = endEffect = new ParticleEffect(){{
                        particles = 1;
                        region = "lp-railblast";
                        lifetime = 30f;
                        length = 0f;
                        baseLength = 0f;
                        cone = 0f;
                        randLength = false;
                        interp = Interp.circleIn;
                        sizeFrom = 16f;
                        sizeTo = 0f;
                        colorFrom = colorTo = LPPal.aureus;
                    }};
                    hitEffect = despawnEffect = new MultiEffect(
                        LPFx.sharpHitSpark(30f, LPPal.aureus, 9, 72, 30f, Interp.circleIn, 30f),
                        new ParticleEffect(){{
                            particles = 16;
                            line = true;
                            lifetime = 30f;
                            length = 60f;
                            baseLength = 12f;
                            cone = 30f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 24f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureus;
                        }}
                    );
                    pierceEffect = new MultiEffect(
                        LPFx.cutting(30f, LPPal.aureus, LPPal.aureusMid, false, 50f, 45f),
                        
                        new ParticleEffect(){{
                            particles = 12;
                            line = true;
                            lifetime = 30f;
                            length = 70f;
                            baseLength = 12f;
                            baseRotation = -225f;
                            useRotation = false;
                            cone = 12f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 18f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureus;
                        }},

                        new ParticleEffect(){{
                            particles = 4;
                            line = true;
                            lifetime = 30f;
                            length = 76f;
                            baseLength = 12f;
                            baseRotation = -225f;
                            useRotation = false;
                            cone = 10f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 20f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new ParticleEffect(){{
                            particles = 12;
                            line = true;
                            lifetime = 30f;
                            length = 70f;
                            baseLength = 12f;
                            baseRotation = -45f;
                            useRotation = false;
                            cone = 12f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 18f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureus;
                        }},

                        new ParticleEffect(){{
                            particles = 4;
                            line = true;
                            lifetime = 30f;
                            length = 76f;
                            baseLength = 12f;
                            baseRotation = -45f;
                            useRotation = false;
                            cone = 10f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 20f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }}
                    );
                    fragOnDespawn = false;
                    fragOnHit = false;
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
            alwaysUnlocked = false;
            researchCostMultiplier = 0f;
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
