package LP.content;

import arc.graphics.Color;
import arc.math.*;
import mindustry.type.Weapon;
import mindustry.gen.Bullet;
import mindustry.gen.ElevationMoveUnit;
import mindustry.gen.MechUnit;
import mindustry.gen.Sounds;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;

import LP.entities.units.SpawnUnitType;
import LP.entities.units.LPUnitType;
import LP.graphics.LPPal;
import static LP.LPMod.name;

public class LPEnemyUnits {
    /** 悬浮 */
    public static LPUnitType riptide, stormpole;
    /** 机甲 */
    public static LPUnitType rupture, starmeter;
    /** 爬爬 */
    public static LPUnitType crystalburst, ravager, annihicore;
    /** 堡垒 */
    public static LPUnitType eradicator;
    /** 飞行 */
    public static LPUnitType rusher, vectruptor, sustainer;

    /** 生成，暂留的，脑子不行技术也不过关，按原内容重构QwQ */
    /** 悬浮 */
    public static SpawnUnitType riptideSpawn, stormpoleSpawn;
    /** 机甲 */
    public static SpawnUnitType ruptureSpawn, starmeterSpawn;
    /** 爬爬 */
    public static SpawnUnitType crystalburstSpawn, ravagerSpawn, annihicoreSpawn;
    /** 堡垒 */
    public static SpawnUnitType eradicatorSpawn;
    /** 飞行 */
    public static SpawnUnitType rusherSpawn, vectruptorSpawn, sustainerSpawn;

    public static void load() {
        /** 悬浮 */
        riptide = new LPUnitType("riptide"){{
            constructor = ElevationMoveUnit::create;
            databaseTag = "enemy";
            health = 540;
            armor = 3f;
            hitSize = 24f;
            rotateSpeed = 6f;
            speed = 3f;
            accel = 0.02f;
            drag = 0.02f;
            engineSize = 0f;
            range = maxRange = fogRadius = 110f;
            itemCapacity = 40;
            shadowElevation = 0.1f;
            lowAltitude = true;
            hovering = true;
            flying = false;
            canDrown = false;
            alwaysUnlocked = false;
            useUnitCap = false;
            researchCostMultiplier = 0f;
            loopSound = Sounds.loopHover;
            loopSoundVolume = 0.5f;
            parts.addAll(
                new ShapePart(){{
                    y = 9f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 5.5f;
                    stroke = strokeTo = 1.5f;
                    rotation = 0f;
                    layer = 110f;
                }},

                new HoverPart(){{
                    x = 10f;
                    y = 7f;
                    mirror = true;
                    color = LPPal.orange;
                    sides = 4;
                    circles = 3;
                    radius = 10f;
                    phase = 60f;
                    stroke = 1f;
                    layerOffset = -0.001f;
                }},

                new HoverPart(){{
                    x = 11f;
                    y = -8f;
                    mirror = true;
                    color = LPPal.orange;
                    sides = 4;
                    circles = 3;
                    radius = 10f;
                    phase = 60f;
                    stroke = 1f;
                    layerOffset = -0.001f;
                }}
            );
            weapons.add(new Weapon(name("riptide-b")){{
                x = 4.7f;
                y = 0f;
                mirror = true;
                rotate = false;
                top = true;
                shootY = 2.5f;
                shootCone = 5f;
                shootSound = LPSounds.shootNavanax;
                shake = 2f;
                reload = 30;
                recoil = 2f;
                recoilTime = 20;
                layerOffset = 0.001f;
                bullet = new MultiBulletType(
                    new BasicBulletType(){{
                        angleOffset = -1f;
                        sprite = "circle-bullet";
                        width = height = 2f;
                        shrinkY = 0f;
                        lightColor = frontColor = backColor = trailColor = Color.valueOf("FEB380");
                        trailWidth = 1.1f;
                        trailLength = 4;
                        weaveScale = 2f;
                        weaveMag = 4f;
                        weaveRandom = false;
                        armorMultiplier = 1.5f;
                        ammoMultiplier = 1f;
                        shieldDamageMultiplier = 1.45f;
                        rangeOverride = 110f;
                        homingRange = 110f;
                        homingDelay = 4f;
                        homingPower = 1f;
                        damage = 20f;
                        speed = 5f;
                        lifetime = 22f;
                        hitSound = LPSounds.explosionCleroi;
                        hitShake = 3;
                        hitEffect = new MultiEffect(
                            new ParticleEffect(){{
                                particles = 3;
                                region = "lp-triangle";
                                lifetime = 30f;
                                length = 20f;
                                baseLength = 2f;
                                interp = Interp.pow3Out;
                                sizeInterp = Interp.pow2In;
                                spin = 3f;
                                sizeFrom = 4f;
                                sizeTo = 0f;
                                colorFrom = colorTo = Color.valueOf("FEB380");
                            }},

                            new ParticleEffect(){{
                                particles = 2;
                                line = true;
                                lifetime = 30f;
                                length = 22f;
                                baseLength = 1f;
                                interp = Interp.pow10Out;
                                sizeInterp = Interp.pow2In;
                                lenFrom = 11f;
                                lenTo = 0f;
                                strokeFrom = 1.6f;
                                strokeTo = 0f;
                                colorFrom = colorTo = Color.valueOf("FEB380");
                            }}
                        );
                        despawnEffect = new WaveEffect(){{
                            lifetime = 15f;
                            sizeFrom = 0f;
                            sizeTo = 6f;
                            strokeFrom = 1f;
                            strokeTo = 0f;
                            colorFrom = Color.white;
                            colorTo = Color.valueOf("FEB380");
                        }};
                    }},

                    new BasicBulletType(){{
                        angleOffset = -1f;
                        sprite = "circle-bullet";
                        width = height = 2f;
                        shrinkY = 0f;
                        lightColor = frontColor = backColor = trailColor = Color.valueOf("FEB380");
                        trailWidth = 1.1f;
                        trailLength = 4;
                        weaveScale = 2f;
                        weaveMag = -4f;
                        weaveRandom = false;
                        armorMultiplier = 1.5f;
                        ammoMultiplier = 1f;
                        shieldDamageMultiplier = 1.45f;
                        rangeOverride = 110f;
                        homingRange = 110f;
                        homingDelay = 4f;
                        homingPower = 1f;
                        damage = 20f;
                        speed = 5f;
                        lifetime = 22f;
                        hitSound = LPSounds.explosionCleroi;
                        hitShake = 3;
                        hitEffect = new MultiEffect(
                            new ParticleEffect(){{
                                particles = 3;
                                region = "lp-triangle";
                                lifetime = 30f;
                                length = 20f;
                                baseLength = 2f;
                                interp = Interp.pow3Out;
                                sizeInterp = Interp.pow2In;
                                spin = 3f;
                                sizeFrom = 4f;
                                sizeTo = 0f;
                                colorFrom = colorTo = Color.valueOf("FEB380");
                            }},

                            new ParticleEffect(){{
                                particles = 2;
                                line = true;
                                lifetime = 30f;
                                length = 22f;
                                baseLength = 1f;
                                interp = Interp.pow10Out;
                                sizeInterp = Interp.pow2In;
                                lenFrom = 11f;
                                lenTo = 0f;
                                strokeFrom = 1.6f;
                                strokeTo = 0f;
                                colorFrom = colorTo = Color.valueOf("FEB380");
                            }}
                        );
                        despawnEffect = new WaveEffect(){{
                            lifetime = 15f;
                            sizeFrom = 0f;
                            sizeTo = 6f;
                            strokeFrom = 1f;
                            strokeTo = 0f;
                            colorFrom = Color.white;
                            colorTo = Color.valueOf("FEB380");
                        }};
                    }}
                ){{
                    damage = 40f;
                    armorMultiplier = 1.5f;
                    ammoMultiplier = 1f;
                    shieldDamageMultiplier = 1.45f;
                    homingRange = 110f;
                    homingDelay = 4f;
                    homingPower = 1f;
                    maxRange = rangeOverride = 110f;
                    shootEffect = new ParticleEffect(){{
                        particles = 4;
                        lifetime = 25f;
                        length = 14f;
                        baseLength = 2f;
                        interp = Interp.pow5Out;
                        sizeInterp = Interp.pow2In;
                        cone = 3f;
                        sizeFrom = 1.6f;
                        sizeTo = 0f;
                        colorFrom = colorTo = Color.valueOf("FEB380");
                    }};
                    smokeEffect = Fx.none;
                }};
            }});
        }};

        riptideSpawn = new SpawnUnitType("riptide-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = true;
                shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = Sounds.none;
                bullet = new BasicBulletType(){
                    {
                        despawnUnit = riptide;
                        shootEffect = smokeEffect = Fx.none;
                        width = height = shrinkY = 0f;
                        speed = damage = despawnUnitRadius = 0f;
                        lifetime = 120f;
                        killShooter = despawnHit = true;
                        ignoreSpawnAngle = true;
                        collides = absorbable = hittable = false;
                        hitSound = despawnSound = LPSounds.laser2;
                        hitShake = despawnShake = 12f;
                        hitEffect = despawnEffect = new MultiEffect(
                            new WaveEffect(){{
                                lifetime = 30f;
                                sides = 4;
                                colorFrom = LPPal.eyesLight;
                                colorTo = Color.valueOf("F3E6E600");
                                sizeFrom = 32f;
                                sizeTo = 36f;
                                strokeFrom = 5f;
                                strokeTo = 6f;
                                rotation = 0f;
                                layer = 110f;
                            }},

                            new ParticleEffect(){{
                                particles = 1;
                                region = "lp-star";
                                lifetime = 20f;
                                length = 0f;
                                baseLength = 0f;
                                baseRotation = 0f;
                                cone = 0f;
                                randLength = false;
                                spin = 5f;
                                sizeFrom = 64f;
                                sizeTo = 0f;
                                colorFrom = colorTo = LPPal.orange;
                            }},

                            new ParticleEffect(){{
                                particles = 1;
                                region = "lp-star";
                                lifetime = 20f;
                                length = 0f;
                                baseLength = 0f;
                                baseRotation = 0f;
                                cone = 0f;
                                randLength = false;
                                spin = 5f;
                                sizeFrom = 48f;
                                sizeTo = 0f;
                                colorFrom = colorTo = LPPal.eyesLight;
                            }},

                            new ParticleEffect(){{
                                particles = 12;
                                line = true;
                                length = 70f;
                                baseLength = 2f;
                                lifetime = 30f;
                                interp = Interp.pow5Out;
                                sizeInterp = Interp.pow2In;
                                lenFrom = 24f;
                                lenTo = 0f;
                                strokeFrom = 1.5f;
                                strokeTo = 0f;
                                colorFrom = LPPal.orange;
                                colorTo = LPPal.orangeDark;
                            }}
                        );
                        parts.addAll(
                            new ShapePart(){{
                                color = colorTo = LPPal.eyesLight;
                                hollow = true;
                                sides = 4;
                                radius = 28f;
                                radiusTo = 32f;
                                stroke = 4f;
                                strokeTo = 5f;
                                layer = 110f;
                            }},

                            new EffectSpawnerPart(){{
                                effect = new ParticleEffect(){{
                                    particles = 1;
                                    line = true;
                                    length = -50f;
                                    baseLength = 50f;
                                    randLength = false;
                                    interp = Interp.circleIn;
                                    lifetime = 20f;
                                    lenFrom = 0;
                                    lenTo = 16;
                                    strokeFrom = 1.4f;
                                    strokeTo = 1.4f;
                                    colorFrom = colorTo = LPPal.orange;
                                    layer = 110f;
                                }};
                                effectChance = 0.4f;
                                progress = PartProgress.life;
                            }},

                            new EffectSpawnerPart(){{
                                effect = new ParticleEffect(){{
                                    particles = 1;
                                    line = true;
                                    length = -60f;
                                    baseLength = 60f;
                                    randLength = false;
                                    interp = Interp.circleIn;
                                    lifetime = 20f;
                                    lenFrom = 0;
                                    lenTo = 24;
                                    strokeFrom = 1.4f;
                                    strokeTo = 1.4f;
                                    colorFrom = colorTo = LPPal.orange;
                                    layer = 110f;
                                }};
                                effectChance = 0.2f;
                                progress = PartProgress.life;
                            }}
                        );
                    }
                    @Override
                    public void update(Bullet b) {
                        super.update(b);
                    
                        if (b.timer(1, 4)) {
                            for (int l = 0; l < 3; l++) {
                                LP.graphics.Drawn.randFadeLightningEffect(b.x, b.y, Mathf.random(60f), Mathf.random(0.5f, 3f), LPPal.orange, Mathf.chance(0.5));
                            }
                        }
                    }
                };
            }});
        }};

        stormpole = new LPUnitType("stormpole"){{
            constructor = ElevationMoveUnit::create;
            databaseTag = "enemy";
            health = 860f;
            armor = 5f;
            hitSize = 32f;
            rotateSpeed = 2.8f;
            speed = 2.2f;
            accel = 0.1f;
            drag = 0.04f;
            engineSize = 0f;
            buildSpeed = 0f;
            itemCapacity = 200;
            range = maxRange = fogRadius = 176f;
            shadowElevation = 0.4f;
            lowAltitude = true;
            hovering = true;
            flying = false;
            faceTarget = false;
            alwaysUnlocked = false;
            canDrown = false;
            drawCell = false;
            useUnitCap = false;
            parts.addAll(
                new ShapePart(){{
                    y = 18f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 8f;
                    stroke = strokeTo = 1.8f;
                    rotation = 0f;
                    layer = 110f;
                }},

                new HoverPart(){{
                    mirror = false;
                    color = LPPal.aureusDark;
                    sides = 4;
                    circles = 3;
                    radius = 50f;
                    phase = 60f;
                    stroke = 2f;
                    layerOffset = -0.001f;
                }}
            );
            weapons.add(new Weapon(name("stormpole-1")){{
                x = 0f;
                y = -2.25f;
                mirror = false;
                rotate = true;
                shootY = 0f;
                shootCone = 2f;
                shootSound = LPSounds.plasmaShot1;
                shootSoundVolume = 1.4f;
                rotateSpeed = 3f;
                reload = 100f;
                recoil = 4f;
                recoilTime = 60f;
                shake = 5f;
                shoot = new ShootPattern(){{
                    shots = 2;
                    shotDelay = 10f;
                }};
                bullet = new BasicBulletType(){{
                    sprite = "lp-energy-bullet";
                    width = height = 9f;
                    shrinkY = 0f;
                    lightColor = frontColor = backColor = trailColor = hitColor = LPPal.aureusDark;
                    trailWidth = 2f;
                    trailLength = 6;
                    scaleLife = true;
                    keepVelocity = true;
                    speed = 17.6f;
                    lifetime = 10f;
                    rangeOverride = maxRange = 176f;
                    damage = 0f;
                    splashDamage = 60f;
                    splashDamageRadius = 64f;
                    ammoMultiplier = 1f;
                    shootEffect = new MultiEffect(
                        LPFx.XSharpShoot(18f, LPPal.aureusDark, 50f),
                        new ParticleEffect(){{
                            particles = 4;
                            line = true;
                            lifetime = 18f;
                            length = 24f;
                            baseLength = 4f;
                            cone = 15f;
                            interp = Interp.pow2Out;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 10f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new ParticleEffect(){{
                            particles = 5;
                            line = true;
                            lifetime = 18f;
                            length = 34f;
                            baseLength = 6f;
                            cone = 20f;
                            interp = Interp.pow4Out;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 16f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }}
                    );
                    smokeEffect = Fx.none;
                    hitShake = despawnShake = 7f;
                    hitSound = despawnSound = LPSounds.airCrushSmall2;
                    hitSoundVolume = 1.8f;
                    hitEffect = despawnEffect = new MultiEffect(
                        LPFx.XSharpHit(40f, LPPal.aureusDark, 60f),
                        LPFx.circleOut(40f, LPPal.aureusDark, 60f),
                        LPFx.smoothCircleOut(40f, LPPal.aureusDark, 60f, 120, true)
                    );
                }};
            }});
        }};

        stormpoleSpawn = new SpawnUnitType("stormpole-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = true;
                shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = LPSounds.chargeRail2;
                shootSoundVolume = 1.25f;
                bullet = new BasicBulletType(){{
                    width = height = shrinkY = 0f;
                    killShooter = ignoreSpawnAngle = true;
                    collides = absorbable = hittable = keepVelocity = false;
                    speed = damage = 0f;
                    lifetime = 110f;
                    hitSound = LPSounds.shootPulse3;
                    hitSoundVolume = 2.5f;
                    hitEffect = despawnEffect = Fx.none;
                    shootEffect = smokeEffect = Fx.none;
                    parts.addAll(
                        new ShapePart(){{
                            hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 0f;
                            radiusTo = 8f;
                            stroke = 0f;
                            strokeTo = 2f;
                            rotation = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0f, 0.24f).curve(Interp.pow2In);
                        }},

                        new ShapePart(){{
                            circle = true;
                            hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            radius = 0f;
                            radiusTo = 20f;
                            stroke = 0f;
                            strokeTo = 4f;
                            rotation = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.3f, 0.46f).curve(Interp.pow2In).mul(0f).sin(1f, 0.2f).add(1f).mul(PartProgress.life.mul(0.8f).sin(1f, 0.2f)).add(0f).curve(Interp.circleIn);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 6f;
                            triLength = 0f;
                            triLengthTo = 32f;
                            haloRadius = 0f;
                            haloRadiusTo = 24f;
                            haloRotateSpeed = -4f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.4f, 0.6f).curve(Interp.pow2In);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 6f;
                            triLength = 0f;
                            triLengthTo = 6f;
                            shapeRotation = 180f;
                            haloRadius = 0f;
                            haloRadiusTo = 24f;
                            haloRotateSpeed = -4f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.4f, 0.6f).curve(Interp.pow2In);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 6f;
                            triLength = 0f;
                            triLengthTo = 24f;
                            haloRadius = 0f;
                            haloRadiusTo = 24f;
                            haloRotation = 24f;
                            haloRotateSpeed = 5f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.45f, 0.6f).curve(Interp.pow2In);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 6f;
                            triLength = 0f;
                            triLengthTo = 4f;
                            shapeRotation = 180f;
                            haloRadius = 0f;
                            haloRadiusTo = 24f;
                            haloRotation = 24f;
                            haloRotateSpeed = 5f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.45f, 0.6f).curve(Interp.pow2In);
                        }},

                        new FlarePart(){{
                            followRotation = false;
                            color1 = color2 = LPPal.eyesLight;
                            sides = 2;
                            radius = 0f;
                            radiusTo = 60f;
                            stroke = 1f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.3f, 1f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 20f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.3f, 0.32f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 40f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.32f, 0.34f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 60f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.34f, 0.36f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 80f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.36f, 0.38f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 100f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.38f, 0.40f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 120f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.40f, 0.42f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 140f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.42f, 0.44f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 160f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.44f, 0.46f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 180f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.46f, 0.48f).curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 0f;
                            radiusTo = 1f;
                            triLength = 70f;
                            triLengthTo = 0f;
                            haloRadius = 0f;
                            haloRotation = 200f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.48f, 0.50f).curve(Interp.circleOut);
                        }}
                    );
                    despawnUnit = stormpole;
                    despawnUnitRadius = 0f;
                    fragBullets = 1;
                    fragVelocityMin = fragVelocityMax =fragOffsetMin = fragOffsetMax = 0;
                    fragLifeMin = fragLifeMax = 1;
                    fragBullet = new BasicBulletType(){{
                        width = height = shrinkY = 0f;
                        ignoreSpawnAngle = true;
                        collides = absorbable = hittable = keepVelocity = false;
                        speed = damage = 0f;
                        lifetime = 30f;
                        hitSound = Sounds.none;
                        hitEffect = despawnEffect = Fx.none;
                        parts.addAll(
                            new ShapePart(){{
                                hollow = true;
                                color = LPPal.eyesLight;
                                colorTo = Color.valueOf("F3E6E600");
                                sides = 4;
                                radius = 0f;
                                radiusTo = 36f;
                                stroke = 3f;
                                strokeTo = 0f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = LPPal.eyesLight;
                                colorTo = Color.valueOf("F3E6E600");
                                sides = 4;
                                radius = 0f;
                                radiusTo = 40f;
                                stroke = 2f;
                                strokeTo = 0f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                circle = true;
                                hollow = true;
                                color = colorTo = LPPal.eyesLight;
                                radius = 0f;
                                radiusTo = 48f;
                                stroke = 2f;
                                strokeTo = 0f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new FlarePart(){{
                                followRotation = false;
                                color1 = color2 = LPPal.eyesLight;
                                sides = 2;
                                radius = 0f;
                                radiusTo = 80f;
                                stroke = 1f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new HaloPart(){{
                                tri = true;
                                color = colorTo = LPPal.eyesLight;
                                shapes = 2;
                                radius = 8f;
                                radiusTo = 0f;
                                triLength = 25f;
                                triLengthTo = 40f;
                                haloRadius = 24f;
                                haloRadiusTo = 48f;
                                haloRotation = 45;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new HaloPart(){{
                                tri = true;
                                color = colorTo = LPPal.eyesLight;
                                shapes = 2;
                                radius = 8f;
                                radiusTo = 0f;
                                triLength = 8f;
                                triLengthTo = 6f;
                                shapeRotation = 180f;
                                haloRadius = 24f;
                                haloRadiusTo = 48f;
                                haloRotation = 45;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }}
                        );
                    }};
                }};
            }});
        }};

        rupture = new LPUnitType("rupture"){{
            constructor = MechUnit::create;
            databaseTag = "enemy";
            health = 980f;
            armor = 8f;
            hitSize = 38f;
            rotateSpeed = 2.8f;
            speed = 0.58f;
            accel = 0.2f;
            drag = 0.2f;
            engineSize = 0f;
            buildSpeed = 0f;
            itemCapacity = 0;
            fogRadius = range = maxRange = 224f;
            shadowElevation = 0.2f;
            groundLayer = 60f;
            mechLandShake = 0.5f;
            mechLegColor = Color.valueOf("303238");
            stepSound = LPSounds.mechStepHeavy;
            stepSoundVolume = 1f;
            stepSoundPitch = 0.8f;
            mechStepParticles = true;
            drawCell = false;
            useUnitCap = false;
            parts.addAll(
                new ShapePart(){{
                    y = 14f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 8f;
                    stroke = strokeTo = 1.8f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            weapons.add(new Weapon(name("rupture-1")){{
                top = predictTarget = false;
                x = 20f;
                y = 1f;
                shootY = 13f;
                shootX = -1.25f;
                shootCone = 10f;
                shootSound = LPSounds.beamLargeShot1;
                shootSoundVolume = 0.7f;
                reload = 72f;
                recoil = 2f;
                recoilTime = 30f;
                shake = 5f;
                bullet = new MultiBulletType(
                    new LaserBulletType(){{
                        lightColor = hitColor = Color.valueOf("FF6464");
                        length = 224f;
                        width = 24f;
                        sideAngle = 80f;
                        sideWidth = 0.8f;
                        sideLength = 45f;
                        colors = new Color[]{LPPal.redDark, LPPal.orangeRed, Color.valueOf("FF6461"), LPPal.redLight};
                        lifetime = 12f;
                        damage = 30f;
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 4;
                        ammoMultiplier = 1f;
                        armorMultiplier = 1.85f;
                        shieldDamageMultiplier = 2.15f;
                        laserEffect = Fx.none;
                    }},

                    new BasicBulletType(){{
                        sprite = "lp-pierce";
                        width = 9f;
                        height = 12f;
                        shrinkY = 0f;
                        lightColor = frontColor = backColor = frontColor = trailColor = Color.valueOf("FF6464");
                        trailWidth = 3f;
                        trailLength = 3;
                        speed = 22.4f;
                        lifetime = 10f;
                        maxRange = rangeOverride = 224f;
                        damage = 50f;
                        keepVelocity = pierce = pierceBuilding = true;
                        pierceCap = 4;
                        ammoMultiplier = 1f;
                        armorMultiplier = 1.85f;
                        shieldDamageMultiplier = 2.15f;
                        hitShake = 6f;
                        hitSound = LPSounds.shootArtillerySapBig;
                        hitSoundVolume = 1.2f;
                        hitEffect = new MultiEffect(
                            new ParticleEffect(){{
                                particles = 6;
                                line = true;
                                lifetime = 18f;
                                length = 28f;
                                baseLength = 4f;
                                cone = 20f;
                                interp = Interp.pow3Out;
                                sizeInterp = Interp.pow2In;
                                lenFrom = 16f;
                                lenTo = 0f;
                                strokeFrom = 1.2f;
                                strokeTo = 0f;
                                colorFrom = LPPal.redLight;
                                colorTo = Color.valueOf("FF6464");
                            }},
                            LPFx.cutting(40f, Color.valueOf("FF6464"), Color.valueOf("FF6464"), false, 30f, 55f)
                        );
                        despawnSound = Sounds.none;
                        despawnEffect = Fx.none;
                        despawnHit = fragOnDespawn = false;
                    }}
                ){{
                    damage = 80f;
                    pierce = pierceBuilding = true;
                    pierceCap = 4;
                    ammoMultiplier = 1f;
                    armorMultiplier = 1.85f;
                    shieldDamageMultiplier = 2.15f;
                    shootEffect = Fx.none;
                    smokeEffect = Fx.smokeCloud;
                    maxRange = rangeOverride = 224f;
                }};
            }});
        }};

        ruptureSpawn = new SpawnUnitType("rupture-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = true;
                shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                bullet = new MultiBulletType(
                    new BasicBulletType(){{
                        width = height = shrinkY = 0f;
                        ignoreSpawnAngle = true;
                        collides = absorbable = hittable = keepVelocity = false;
                        speed = damage = 0f;
                        lifetime = 90f;
                        hitSound = despawnSound = LPSounds.plasmaShot2;
                        hitEffect = despawnEffect = Fx.none;
                        despawnUnit = rupture;
                        despawnUnitRadius = 0f;
                        parts.addAll(
                            new ShapePart(){{
                                hollow = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 30f;
                                stroke = 0f;
                                strokeTo = 2.8f;
                                rotation = 0f;
                                layer = 110;
                                progress = PartProgress.life.curve(Interp.pow2Out);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 30f;
                                stroke = 0f;
                                strokeTo = 2.8f;
                                rotation = 0f;
                                layer = 110;
                                progress = PartProgress.life.compress(0.7f, 1f).curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 30f;
                                stroke = 0f;
                                strokeTo = 2.8f;
                                rotation = 0f;
                                layer = 110;
                                progress = PartProgress.life.compress(0.75f, 1f).curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 30f;
                                stroke = 0f;
                                strokeTo = 2.8f;
                                rotation = 0f;
                                layer = 110;
                                progress = PartProgress.life.compress(0.8f, 1f).curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 30f;
                                stroke = 0f;
                                strokeTo = 2.8f;
                                rotation = 0f;
                                layer = 110;
                                progress = PartProgress.life.compress(0.85f, 1f).curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 30f;
                                stroke = 0f;
                                strokeTo = 2.8f;
                                rotation = 0f;
                                layer = 110;
                                progress = PartProgress.life.compress(0.9f, 1f).curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 30f;
                                stroke = 0f;
                                strokeTo = 2.8f;
                                rotation = 0f;
                                layer = 110;
                                progress = PartProgress.life.compress(0.95f, 1f).curve(Interp.circleOut);
                            }},

                            new FlarePart(){{
                                followRotation = false;
                                color1 = color2 = LPPal.eyesLight;
                                sides = 2;
                                radius = 0f;
                                radiusTo = 70f;
                                stroke = 1f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.pow2In);
                            }},

                            new FlarePart(){{
                                followRotation = false;
                                color1 = color2 = LPPal.eyesLight;
                                sides = 2;
                                radius = 0f;
                                radiusTo = 60f;
                                stroke = 1f;
                                spinSpeed = 8f;
                                layer = 110f;
                                progress = PartProgress.life.compress(0.7f, 1f).curve(Interp.pow2In);
                            }},

                            new FlarePart(){{
                                followRotation = false;
                                color1 = color2 = LPPal.eyesLight;
                                sides = 2;
                                radius = 0f;
                                radiusTo = 48f;
                                stroke = 1f;
                                spinSpeed = -9f;
                                layer = 110f;
                                progress = PartProgress.life.compress(0.7f, 1f).curve(Interp.pow2In);
                            }}
                        );
                        despawnHit = true;
                        fragBullets = 1;
                        fragVelocityMin = fragVelocityMax =fragOffsetMin = fragOffsetMax = 0;
                        fragLifeMin = fragLifeMax = 1;
                        fragBullet = new BasicBulletType(){{
                            width = height = shrinkY = 0f;
                            killShooter = ignoreSpawnAngle = true;
                            collides = absorbable = hittable = keepVelocity = false;
                            speed = damage = 0f;
                            lifetime = 48;
                            hitSound = despawnSound = Sounds.none;
                            hitEffect = despawnEffect = Fx.none;
                            shootEffect = smokeEffect = Fx.none;
                            parts.addAll(
                                new ShapePart(){{
                                    hollow = true;
                                    color = LPPal.eyesLight;
                                    colorTo = Color.valueOf("F3E6E600");
                                    sides = 4;
                                    radius = 30f;
                                    radiusTo = 40f;
                                    stroke = 2.8f;
                                    strokeTo = 4f;
                                    rotation = 0f;
                                    layer = 110;
                                    progress = PartProgress.life.curve(Interp.pow3Out);
                                }},

                                new ShapePart(){{
                                    hollow = true;
                                    color = LPPal.eyesLight;
                                    colorTo = Color.valueOf("F3E6E600");
                                    sides = 4;
                                    radius = 30f;
                                    radiusTo = 40f;
                                    stroke = 2.8f;
                                    strokeTo = 4f;
                                    rotation = 0f;
                                    layer = 110;
                                    progress = PartProgress.life.curve(Interp.pow4Out);
                                }},

                                new ShapePart(){{
                                    hollow = true;
                                    color = LPPal.eyesLight;
                                    colorTo = Color.valueOf("F3E6E600");
                                    sides = 4;
                                    radius = 30f;
                                    radiusTo = 40f;
                                    stroke = 2.8f;
                                    strokeTo = 4f;
                                    rotation = 0f;
                                    layer = 110;
                                    progress = PartProgress.life.curve(Interp.pow5Out);
                                }},

                                new FlarePart(){{
                                    followRotation = false;
                                    color1 = color2 = LPPal.eyesLight;
                                    sides = 2;
                                    radius = 60f;
                                    radiusTo = 0f;
                                    stroke = 1f;
                                    spinSpeed = 8f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }},

                                new FlarePart(){{
                                    followRotation = false;
                                    color1 = color2 = LPPal.eyesLight;
                                    sides = 2;
                                    radius = 48f;
                                    radiusTo = 0f;
                                    stroke = 1f;
                                    spinSpeed = -9f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }}
                            );
                        }};
                    }}
                ){{
                    killShooter = true;
                    shootEffect = smokeEffect = Fx.none;
                }};
            }});
        }};
    }
}
