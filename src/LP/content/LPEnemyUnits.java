package LP.content;

import arc.graphics.Color;
import arc.math.*;
import arc.struct.Seq;
import arc.util.Time;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import mindustry.ai.types.*;
import mindustry.type.Weapon;
import mindustry.world.meta.BlockFlag;
import mindustry.gen.Bullet;
import mindustry.gen.ElevationMoveUnit;
import mindustry.gen.LegsUnit;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.entities.abilities.*;

import LP.entities.units.SpawnUnitType;
import LP.entities.abilities.DeathSpawnBulletAbility;
import LP.entities.bullets.EffectBulletType;
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
                shootSound = Sounds.none;
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

        starmeter = new LPUnitType("starmeter"){{
            constructor = MechUnit::create;
            databaseTag = "enemy";
            health = 1410f;
            armor = 10f;
            hitSize = 48f;
            rotateSpeed = 2f;
            speed = accel = drag = 0.5f;
            engineSize = 0f;
            fogRadius = range = maxRange = 240f;
            shadowElevation = 0.35f;
            groundLayer = 70f;
            mechLandShake = 0.8f;
            mechLegColor = Color.valueOf("303238");
            stepSound = LPSounds.mechStepHeavy;
            stepSoundVolume = 1f;
            mechStepParticles = true;
            canDrown = drawCell = useUnitCap = false;
            deathSound = LPSounds.mechDestroyed;
            deathSoundVolume = 1.5f;
            parts.addAll(
                new ShapePart(){{
                    y = 18f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 12f;
                    stroke = strokeTo = 2.25f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            weapons.add(new Weapon(name("starmeter-1")){{
                top = false;
                x = 28.75f;
                y = -0.5f;
                shootY = 22.5f;
                shootX = -2f;
                shootCone = 10f;
                shootSound = LPSounds.shootRailgun1;
                shootSoundVolume = 3.2f;
                reload = 160f;
                recoil = 2f;
                recoilTime = 60f;
                shake = 7f;
                heatColor = LPPal.redDark;
                cooldownTime = 120f;
                bullet = new BasicBulletType(){{
                    sprite = "lp-energy-bullet";
                    width = 9f;
                    height = 12f;
                    shrinkY = 0f;
                    lightColor = frontColor = backColor = frontColor = trailColor = LPPal.aureusDark;
                    trailWidth = 3f;
                    trailLength = 12;
                    absorbable = false;
                    keepVelocity = true;
                    speed = 24f;
                    lifetime = 10f;
                    damage = 280f;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 3;
                    ammoMultiplier = 1f;
                    armorMultiplier = 0.4f;
                    hitShake = 12f;
                    hitSound = LPSounds.laser4;
                    hitSoundVolume = 1.8f;
                    hitEffect = new MultiEffect(
                        LPFx.cutting(30f, LPPal.aureusDark, LPPal.black, 40f, 60f, 111f),
                        LPFx.cutting(40f, LPPal.aureusDark, LPPal.black, 40f, 140f, 111f),
                        LPFx.sharpHitSpark(60f, LPPal.aureusDark, 12, 100f, 30f, Interp.pow10Out, 40f)
                    );
                    despawnEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 6;
                            line = true;
                            lifetime = 30f;
                            length = 14f;
                            baseLength = 8f;
                            interp = Interp.pow4Out;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 12f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new ParticleEffect(){{
                            particles = 8;
                            line = true;
                            lifetime = 30f;
                            length = 18f;
                            baseLength = 8f;
                            interp = Interp.pow5Out;
                            sizeInterp = Interp.pow3In;
                            lenFrom = 8f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new WaveEffect(){{
                            lifetime = 30f;
                            interp = Interp.pow2Out;
                            sizeFrom = 0f;
                            sizeTo = 30f;
                            strokeFrom = 2f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }}
                    );
                    shootEffect = new MultiEffect(
                        LPFx.XSharpShoot(15f, LPPal.aureusDark, 60f),
                        new WaveEffect(){{
                            lifetime = 15f;
                            interp = Interp.circleOut;
                            sizeFrom = 0f;
                            sizeTo = 40f;
                            strokeFrom = 3f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new WaveEffect(){{
                            lifetime = 15f;
                            interp = Interp.circleOut;
                            sizeFrom = 0f;
                            sizeTo = 32f;
                            strokeFrom = 2f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new ParticleEffect(){{
                            particles = 6;
                            line = true;
                            lifetime = 15f;
                            length = 32f;
                            baseLength = 4f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 12f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new ParticleEffect(){{
                            particles = 5;
                            line = true;
                            lifetime = 15f;
                            length = 32f;
                            baseLength = 4f;
                            interp = Interp.pow5Out;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 16f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }}
                    );
                    smokeEffect = Fx.none;
                    pierceFragCap = 4;
                    fragBullets = 4;
                    fragVelocityMin = fragVelocityMax = 0f;
                    fragLifeMin = fragLifeMax = 1f;
                    fragOffsetMin = 8f;
                    fragOffsetMax = 28f;
                    fragBullet = new BasicBulletType(){{
                        width = height = shrinkY = 0f;
                        collides = absorbable = hittable = keepVelocity = false;
                        ignoreSpawnAngle = true;
                        angleOffset = speed = damage = 0f;
                        lifetime = 50f;
                        splashDamage = 40f;
                        splashDamageRadius = 40f;
                        armorMultiplier = 0.4f;
                        hitShake = 8f;
                        hitSound = LPSounds.shootBlaster2;
                        hitSoundVolume = 3.3f;
                        hitEffect = LPFx.FFstarHit;
                        despawnEffect = Fx.none;
                        parts.addAll(
                            new ShapePart(){{
                                circle = hollow = true;
                                color = colorTo = LPPal.aureusDark;
                                radius = 12f;
                                radiusTo = 0f;
                                stroke = 1f;
                                strokeTo = 0f;
                                rotation = 0;
                                layer = 110;
                                progress = PartProgress.life.curve(Interp.pow3In);
                            }},

                            new HaloPart(){{
                                tri = true;
                                color = colorTo = LPPal.aureusDark;
                                shapes = 4;
                                radius = 4f;
                                radiusTo = 0f;
                                triLength = 20f;
                                triLengthTo = 0f;
                                haloRadius = 0f;
                                haloRotation = 45f;
                                progress = PartProgress.life.curve(Interp.pow4In);
                            }}
                        );
                    }};
                }};
            }});
        }};

        starmeterSpawn = new SpawnUnitType("starmeter-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = true;
                shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = Sounds.none;
                bullet = new BasicBulletType(){{
                    width = height = shrinkY = 0f;
                    killShooter = ignoreSpawnAngle = true;
                    collides = absorbable = hittable = keepVelocity = false;
                    speed = damage = 0f;
                    lifetime = 40f;
                    hitSound = despawnSound = LPSounds.plasmaShot2;
                    shootEffect = smokeEffect = hitEffect = despawnEffect = Fx.none;
                    parts.addAll(
                        new ShapePart(){{
                            hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 0f;
                            radiusTo = 48f;
                            stroke = 0f;
                            strokeTo = 4f;
                            rotation = 0;
                            layer = 110;
                            progress = PartProgress.life.curve(Interp.pow3In);
                        }},

                        new ShapePart(){{
                            circle = hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            radius = 0f;
                            radiusTo = 56f;
                            stroke = 0f;
                            strokeTo = 2f;
                            rotation = 0;
                            layer = 110;
                            progress = PartProgress.life.curve(Interp.pow2In);
                        }},

                        new ShapePart(){{
                            circle = hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            radius = 0f;
                            radiusTo = 56f;
                            stroke = 0f;
                            strokeTo = 2f;
                            rotation = 0;
                            layer = 110;
                            progress = PartProgress.life.compress(0.5f, 1f).curve(Interp.pow2In);
                        }},

                        new ShapePart(){{
                            circle = hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            radius = 0f;
                            radiusTo = 56f;
                            stroke = 0f;
                            strokeTo = 2f;
                            rotation = 0;
                            layer = 110;
                            progress = PartProgress.life.compress(0.6f, 1f).curve(Interp.pow2In);
                        }},

                        new ShapePart(){{
                            circle = hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            radius = 0f;
                            radiusTo = 56f;
                            stroke = 0f;
                            strokeTo = 2f;
                            rotation = 0;
                            layer = 110;
                            progress = PartProgress.life.compress(0.7f, 1f).curve(Interp.pow2In);
                        }},

                        new ShapePart(){{
                            circle = hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            radius = 0f;
                            radiusTo = 56f;
                            stroke = 0f;
                            strokeTo = 2f;
                            rotation = 0;
                            layer = 110;
                            progress = PartProgress.life.compress(0.8f, 1f).curve(Interp.pow2In);
                        }},

                        new ShapePart(){{
                            circle = hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            radius = 0f;
                            radiusTo = 56f;
                            stroke = 0f;
                            strokeTo = 2f;
                            rotation = 0;
                            layer = 110;
                            progress = PartProgress.life.compress(0.9f, 1f).curve(Interp.pow2In);
                        }}
                    );
                    fragBullets = 1;
                    fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                    fragLifeMin = fragLifeMax = 1f;
                    fragBullet = new BasicBulletType(){{
                        width = height = shrinkY = 0f;
                        ignoreSpawnAngle = true;
                        collides = absorbable = hittable = keepVelocity = false;
                        speed = damage = 0f;
                        lifetime = 30f;
                        hitSound = LPSounds.champbump;
                        hitSoundVolume = 1.1f;
                        hitShake = 15f;
                        hitEffect = despawnEffect = Fx.none;
                        despawnUnit = starmeter;
                        despawnUnitRadius = 0;
                        parts.addAll(
                            new ShapePart(){{
                                hollow = true;
                                color = colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 48f;
                                radiusTo = 0f;
                                stroke = 4f;
                                strokeTo = 0f;
                                rotation = 0;
                                layer = 110;
                                progress = PartProgress.life.curve(Interp.pow4In);
                            }},

                            new ShapePart(){{
                                circle = hollow = true;
                                color = colorTo = LPPal.eyesLight;
                                radius = 56f;
                                radiusTo = 0f;
                                stroke = 2f;
                                strokeTo = 0f;
                                rotation = 0;
                                layer = 110;
                                progress = PartProgress.life.curve(Interp.pow3In);
                            }},

                            new FlarePart(){{
                                followRotation = false;
                                color1 = color2 = LPPal.eyesLight;
                                sides = 2;
                                radius = 0f;
                                radiusTo = 80f;
                                spinSpeed = 0;
                                stroke = 1f;
                                layer = 110;
                                progress = PartProgress.life.curve(Interp.pow5In);
                            }},

                            new FlarePart(){{
                                followRotation = false;
                                color1 = color2 = LPPal.eyesLight;
                                sides = 2;
                                radius = 0f;
                                radiusTo = 60f;
                                spinSpeed = 0;
                                stroke = 1f;
                                rotation = 90f;
                                layer = 110;
                                progress = PartProgress.life.curve(Interp.pow5In);
                            }}
                        );
                        fragBullets = 1;
                        fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                        fragLifeMin = fragLifeMax = 1f;
                        fragBullet = new BasicBulletType(){{
                            width = height = shrinkY = 0f;
                            ignoreSpawnAngle = true;
                            collides = absorbable = hittable = keepVelocity = false;
                            speed = damage = 0f;
                            lifetime = 30f;
                            hitSound = despawnSound = LPSounds.plasmaShot2;
                            hitEffect = despawnEffect = Fx.none;
                            parts.addAll(
                                new FlarePart(){{
                                    followRotation = false;
                                    color1 = color2 = LPPal.eyesLight;
                                    sides = 2;
                                    radius = 80f;
                                    radiusTo = 0f;
                                    spinSpeed = 0;
                                    stroke = 1f;
                                    layer = 110;
                                    progress = PartProgress.life.curve(Interp.pow3Out);
                                }},

                                new FlarePart(){{
                                    followRotation = false;
                                    color1 = color2 = LPPal.eyesLight;
                                    sides = 2;
                                    radius = 60f;
                                    radiusTo = 0f;
                                    spinSpeed = 0;
                                    stroke = 1f;
                                    rotation = 90f;
                                    layer = 110;
                                    progress = PartProgress.life.curve(Interp.pow3Out);
                                }},

                                new ShapePart(){{
                                    hollow = true;
                                    color = LPPal.eyesLight;
                                    colorTo = Color.valueOf("F3E6E600");
                                    sides = 4;
                                    radius = 48f;
                                    radiusTo = 56f;
                                    stroke = 4f;
                                    strokeTo = 6f;
                                    rotation = 0;
                                    layer = 110;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }}
                            );
                        }};
                    }};
                }};
            }});
        }};

        crystalburst = new LPUnitType("crystalburst"){{
            constructor = LegsUnit::create;
            databaseTag = "enemy";
            health = 300f;
            armor = 10f;
            hitSize = 18f;
            rotateSpeed = 4f;
            speed = 2.5f;
            accel = 0.03f;
            drag = 0.03f;
            engineSize = 0f;
            fogRadius = range = maxRange = 144f;
            stepShake = 1f;
            stepSound = LPSounds.mechStepHeavy;
            stepSoundVolume = 0.4f;
            stepSoundPitch = 0.8f;
            legCount = 4;
            legLength = 24f;
            legBaseOffset = 6f;
            legMoveSpace = 1.5f;
            legForwardScl = 0.5f;
            drawCell = useUnitCap = false;
            deathSound = LPSounds.shootRipple;
            deathSoundVolume = 5f;
            deathExplosionEffect = Fx.none;
            targetFlags = new BlockFlag[]{BlockFlag.turret, null};
            parts.addAll(
                new ShapePart(){{
                    y = 9.5f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 6f;
                    stroke = strokeTo = 1.5f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            weapons.add(new Weapon(){{
                mirror = false;
                top = true;
                x = 0f;
                y = 9.5f;
                shootSound = LPSounds.shootOmura;
                shake = 2f;
                reload = 60f;
                bullet = new BasicBulletType(){{
                    sprite = "lp-pointy-bullet";
                    width = height = 10f;
                    shrinkY = 0f;
                    lightColor = frontColor = backColor = frontColor = trailColor = Color.valueOf("FEB380");
                    trailWidth = 3f;
                    trailLength = 9;
                    speed = 4f;
                    lifetime = 36f;
                    rangeOverride = maxRange = 144f;
                    damage = 25f;
                    armorMultiplier = 0.55f;
                    shieldDamageMultiplier = 0.3f;
                    hitSound = LPSounds.energyHit;
                    hitShake = 3f;
                    hitEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 6;
                            line = true;
                            lifetime = 30f;
                            length = 22f;
                            baseLength = 2f;
                            baseRotation = 180f;
                            cone = 40f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 14f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = Color.valueOf("FEB380");
                        }},

                        new ParticleEffect(){{
                            particles = 3;
                            line = true;
                            lifetime = 30f;
                            length = 24f;
                            baseLength = 2f;
                            baseRotation = 180f;
                            cone = 30f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 18f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = Color.valueOf("FEB380");
                        }},

                        new ParticleEffect(){{
                            particles = 3;
                            region = "lp-star";
                            lifetime = 30f;
                            length = 24f;
                            baseLength = 2f;
                            baseRotation = 180f;
                            cone = 90f;
                            interp = Interp.pow5Out;
                            sizeInterp = Interp.pow3In;
                            sizeFrom = 4f;
                            sizeTo = 0f;
                            colorFrom = colorTo = Color.valueOf("FEB380");
                        }}
                    );
                    despawnEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 6;
                            line = true;
                            lifetime = 20f;
                            length = 24f;
                            baseLength = 2f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 10f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = Color.valueOf("FEB380");
                        }},

                        new WaveEffect(){{
                            lifetime = 20f;
                            interp = Interp.pow2Out;
                            sizeFrom = 0f;
                            sizeTo = 24f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = Color.valueOf("FEB380");
                        }}
                    );
                }};
            }});
            abilities.add(new DeathSpawnBulletAbility(new BasicBulletType(){{
                width = height = shrinkY = damage = 0f;
                splashDamage = 400;
                splashDamageRadius = 48;
                shieldDamageMultiplier = 0.5f;
                hittable = false;
                speed = 0f;
                lifetime = 0f;
                hitSound = despawnSound = LPSounds.shootRipple;
                hitSoundVolume = 2f;
                hitShake = despawnShake = 6f;
                hitEffect = despawnEffect = new MultiEffect(
                    LPFx.sharpHitSpark(30f, Color.valueOf("FEB380"), 12, 60f, 22f, Interp.pow10Out),
                    LPFx.cutting(30f, Color.valueOf("FEB380"), Color.valueOf("FEB380"), false, 20, 0f),
                    LPFx.cutting(30f, Color.valueOf("FEB380"), Color.valueOf("FEB380"), false, 20, 90f),
                    new ParticleEffect(){{
                        particles = 16;
                        line = true;
                        lifetime = 30f;
                        length = 40f;
                        baseLength = 2f;
                        interp = Interp.pow5Out;
                        sizeInterp = Interp.pow2In;
                        lenFrom = 20f;
                        lenTo = 0f;
                        strokeFrom = 1.5f;
                        strokeTo = 0f;
                        colorFrom = colorTo = Color.valueOf("FEB380");
                    }},

                    new WaveEffect(){{
                        lifetime = 30f;
                        interp = Interp.pow3Out;
                        sizeFrom = 0f;
                        sizeTo = 48f;
                        strokeFrom = 3f;
                        strokeTo = 0f;
                        colorFrom = colorTo = Color.valueOf("FEB380");
                    }},

                    new WaveEffect(){{
                        lifetime = 30f;
                        interp = Interp.pow2Out;
                        sizeFrom = 0f;
                        sizeTo = 48f;
                        strokeFrom = 2f;
                        strokeTo = 0f;
                        colorFrom = colorTo = Color.valueOf("FEB380");
                    }}
                );
            }}, 1, 0f, 0f, 400f, 48f));
        }};

        crystalburstSpawn = new SpawnUnitType("crystalburst-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = Sounds.none;
                bullet = new BasicBulletType(){
                    {
                        width = height = shrinkY = 0f;
                        killShooter = ignoreSpawnAngle = true;
                        collides = absorbable = hittable = keepVelocity = false;
                        speed = damage = 0f;
                        lifetime = 40f;
                        hitSound = LPSounds.laser2;
                        hitSoundVolume = 3f;
                        despawnSound = Sounds.none;
                        shootEffect = smokeEffect = hitEffect = despawnEffect = Fx.none;
                        despawnUnit = crystalburst;
                        despawnUnitRadius = 0f;
                        parts.addAll(
                            new ShapePart(){{
                                hollow = true;
                                color = colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 32f;
                                stroke = 0f;
                                strokeTo = 3f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleIn);
                            }}
                        );
                        fragBullets = 1;
                        fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                        fragLifeMin = fragLifeMax = 1f;
                        fragBullet = new BasicBulletType(){{
                            width = height = shrinkY = 0f;
                            ignoreSpawnAngle = true;
                            collides = absorbable = hittable = keepVelocity = false;
                            speed = damage = 0f;
                            lifetime = 30f;
                            hitSound = despawnSound = Sounds.none;
                            hitEffect = despawnEffect = Fx.none;
                            parts.addAll(
                                new ShapePart(){{
                                    hollow = true;
                                    color = LPPal.eyesLight;
                                    colorTo = Color.valueOf("F3E6E600");
                                    sides = 4;
                                    radius = 32f;
                                    radiusTo = 32f;
                                    stroke = 3f;
                                    strokeTo = 5f;
                                    rotation = 0f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }},

                                new HaloPart(){{
                                    tri = true;
                                    color = Color.valueOf("F3E6E600");
                                    colorTo = LPPal.eyesLight;
                                    shapes = 4;
                                    radius = 8f;
                                    radiusTo = 0f;
                                    triLength = 10f;
                                    triLengthTo = 10f;
                                    shapeRotation = 180f;
                                    haloRadius = 0f;
                                    haloRadiusTo = 32f;
                                    haloRotation = 45f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }},

                                new HaloPart(){{
                                    tri = true;
                                    color = Color.valueOf("F3E6E600");
                                    colorTo = LPPal.eyesLight;
                                    shapes = 4;
                                    radius = 8f;
                                    radiusTo = 0f;
                                    triLength = 10f;
                                    triLengthTo = 24f;
                                    haloRadius = 0f;
                                    haloRadiusTo = 32f;
                                    haloRotation = 45f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }}
                            );
                        }};
                    }
                    @Override
                    public void update(Bullet b) {
                        super.update(b);
                    
                        if (b.timer(1, 5)) {
                            for (int l = 0; l < 3; l++) {
                                LP.graphics.Drawn.randFadeLightningEffect(b.x, b.y, Mathf.random(80f), Mathf.random(0.5f, 3f), LPPal.eyesLight, Mathf.chance(0.5));
                            }
                        }
                    }
                };
            }});
        }};

        ravager = new LPUnitType("ravager"){{
            constructor = LegsUnit::create;
            databaseTag = "enemy";
            health = 550f;
            armor = 8f;
            hitSize = 32f;
            rotateSpeed = 3.5f;
            speed = 0.75f;
            accel = 0.05f;
            drag = 0.05f;
            engineSize = 0f;
            fogRadius = range = maxRange = 200f;
            stepShake = 1.5f;
            stepSound = LPSounds.mechStepHeavy;
            stepSoundVolume = 0.6f;
            stepSoundPitch = 0.8f;
            legCount = 6;
            legLength = 28f;
            legMaxLength = 1.2f;
            legMinLength = 0.6f;
            legLengthScl = 1f;
            legForwardScl = 1.5f;
            legBaseOffset = 1f;
            legStraightness = 0.4f;
            legSplashRange = 16f;
            legSplashDamage = 4f;
            rippleScale = 0.2f;
            legMoveSpace = 0.5f;
            drawCell = useUnitCap = false;
            targetFlags = new BlockFlag[]{BlockFlag.turret, null};
            parts.addAll(
                new ShapePart(){{
                    y = 12f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 12f;
                    stroke = strokeTo = 2.5f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            abilities.add(new ForceFieldAbility(80f, 2f, 800f, 240f, 4, 0f){{
                breakSound = LPSounds.shootElectric;
                hitSound = LPSounds.shootEmp;
                hitSoundVolume = 0.2f;
            }});
            weapons.add(new Weapon(name("ravager-1")){{
                top = mirror = false;
                x = shootY = 0f;
                shootCone = 45f;
                shootSound = LPSounds.shootPlasma;
                shake = 3f;
                inaccuracy = 30f;
                reload = 20f;
                recoil = 0f;
                cooldownTime = 90f;
                heatColor = LPPal.redDark;
                parentizeEffects = true;
                layerOffset = 0.001f;
                bullet = new BasicBulletType(){{
                    sprite = "lp-stardart";
                    width = height = 24f;
                    shrinkY = 0f;
                    lightColor = frontColor = backColor = trailColor = Color.valueOf("FEB380");
                    trailWidth = 3f;
                    trailLength = 12;
                    spin = 4f;
                    weaveMag = 2.5f;
                    weaveScale = 4f;
                    speed = 5f;
                    lifetime = 24f;
                    drag = -0.04f;
                    maxRange = rangeOverride = homingRange = 200f;
                    homingDelay = 4f;
                    homingPower = 0.08f;
                    damage = 70f;
                    shieldDamageMultiplier = 1.65f;
                    armorMultiplier = 1.5f;
                    status = LPStatusEffect.empII;
                    statusDuration = 120f;
                    hitSound = despawnSound = LPSounds.shootSupernova;
                    hitSoundVolume = 0.85f;
                    hitShake = despawnShake = 4f;
                    hitEffect = despawnEffect = new MultiEffect(
                        LPFx.cutting(120f, Color.valueOf("FEB380"), Color.valueOf("FEB380"), false, 15f, 0f),
                        LPFx.cutting(120f, Color.valueOf("FEB380"), Color.valueOf("FEB380"), false, 15f, 90f),
                        new ParticleEffect(){{
                            particles = 8;
                            region = "lp-triangle";
                            length = 40f;
                            baseLength = 4f;
                            useRotation = false;
                            interp = Interp.pow5Out;
                            sizeInterp = Interp.pow3In;
                            sizeFrom = 5f;
                            sizeTo = 0f;
                            colorFrom = colorTo = Color.valueOf("FEB380");
                        }}
                    );
                    shootEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 6;
                            line = true;
                            lifetime = 20f;
                            length = 30f;
                            baseLength = 2f;
                            interp = Interp.pow4Out;
                            sizeInterp = Interp.pow2In;
                            cone = 40f;
                            lenFrom = 12f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = Color.valueOf("FEB380");
                        }},

                        new ParticleEffect(){{
                            particles = 4;
                            line = true;
                            lifetime = 20f;
                            length = 32f;
                            baseLength = 2f;
                            interp = Interp.pow5Out;
                            sizeInterp = Interp.pow2In;
                            cone = 30f;
                            lenFrom = 16f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = Color.valueOf("FEB380");
                        }}
                    );
                    smokeEffect = new ParticleEffect(){{
                        particles = 6;
                        region = "lp-triangle";
                        lifetime = 15f;
                        length = 24f;
                        baseLength = 24f;
                        interp = Interp.pow5Out;
                        sizeInterp = Interp.pow3In;
                        sizeFrom = 4f;
                        sizeTo = 0f;
                        colorFrom = colorTo = Color.valueOf("FEB380");
                    }};
                }};
            }});
        }};

        ravagerSpawn = new SpawnUnitType("ravager-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = Sounds.none;
                bullet = new BasicBulletType(){{
                    width = height = shrinkY = 0f;
                    killShooter = ignoreSpawnAngle = true;
                    collides = absorbable = hittable = keepVelocity = false;
                    speed = damage = 0f;
                    lifetime = 90f;
                    hitEffect = new ParticleEffect(){{
                        particles = 1;
                        region = "lp-prismatic";
                        length = 0f;
                        baseLength = 0f;
                        randLength = false;
                        baseRotation = 0f;
                        interp = Interp.circleOut;
                        sizeInterp = Interp.pow2In;
                        sizeFrom = 32f;
                        sizeTo = 24f;
                        colorFrom = LPPal.eyesLight;
                        colorTo = Color.valueOf("F3E6E600");
                    }};
                    hitSound = LPSounds.laser2;
                    hitSoundVolume = 3f;
                    despawnSound = LPSounds.plasmaShot2;
                    shootEffect = smokeEffect = despawnEffect = Fx.none;
                    parts.addAll(
                        new ShapePart(){{
                            hollow = true;
                            color = colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 0f;
                            radiusTo = 32f;
                            stroke = 0f;
                            strokeTo = 3f;
                            rotation = 0f;
                            layer = 110;
                            progress = PartProgress.life.mul(0f).sin(1f, 0.2f).add(1f).mul(PartProgress.life.curve(Interp.circleIn));
                        }},

                        new FlarePart(){{
                            color1 = color2 = LPPal.eyesLight;
                            followRotation = false;
                            rotation = 45f;
                            sides = 4;
                            radius = 0f;
                            radiusTo = 80f;
                            stroke = 6f;
                            spinSpeed = 0f;
                            layer = 110f;
                            progress = PartProgress.life.mul(0f).sin(1f, 0.2f).add(1f).mul(PartProgress.life.curve(Interp.circleIn));
                        }}
                    );
                    despawnUnit = ravager;
                    despawnUnitRadius = 0f;
                    fragBullets = 5;
                    fragBullet = new LightningBulletType(){{
                        damage = 15f;
                        lightningLength = 12;
                        lightningLengthRand = 12;
                        lightningColor = LPPal.eyesLight;
                    }};
                }};
            }});
        }};

        annihicore = new LPUnitType("annihicore"){{
            constructor = LegsUnit::create;
            databaseTag = "enemy";
            health = 1590f;
            armor = 10f;
            hitSize = 48f;
            rotateSpeed = 2f;
            speed = 0.65f;
            accel = 0.2f;
            drag = 0.2f;
            engineSize = 0f;
            fogRadius = range = maxRange = 160f;
            shadowElevation = 0.8f;
            groundLayer = 85f;
            stepShake = 2f;
            stepSound = LPSounds.mechStepHeavy;
            stepSoundVolume = 0.8f;
            stepSoundPitch = 0.8f;
            legCount = 6;
            legPairOffset = 4f;
            legLength = 60f;
            legExtension = -10f;
            legBaseOffset = 16f;
            legLengthScl = 0.85f;
            rippleScale = 1.8f;
            legSpeed = 0.2f;
            legSplashRange = 24f;
            legSplashDamage = 16f;
            lockLegBase = true;
            legContinuousMove = true;
            deathSound = Sounds.none;
            deathExplosionEffect = Fx.none;
            hovering = true;
            useUnitCap = drawCell = false;
            parts.addAll(
                new ShapePart(){{
                    y = 26f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 12f;
                    stroke = strokeTo = 2.5f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            abilities.add(new EnergyFieldAbility(20f, 10f, 160f){{
                y = 4.5f;
                color = LPPal.orangeRed;
                effectRadius = 7f;
                sectorRad = 0.1f;
                sectors = 5;
                blinkScl = 5f;
                blinkSize = 0.15f;
                rotateSpeed = -12f;
                healPercent = 0.5f;
                healEffect = new MultiEffect(
                    new SoundEffect(LPSounds.shootPulse4, new ParticleEffect(){{
                        particles = 3;
                        line = true;
                        lifetime = 18f;
                        length = 24f;
                        baseLength = 2f;
                        interp = Interp.pow4Out;
                        sizeInterp = Interp.pow2In;
                        lenFrom = 8f;
                        lenTo = 0f;
                        strokeFrom = 1.2f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.orangeRed;
                    }}){{
                        minPitch = maxPitch = 1f;
                    }},

                    new WrapEffect(Fx.squareWaveEffect, Pal.heal, 35f)
                );
                hitEffect = new MultiEffect(
                    new SoundEffect(LPSounds.shootPulse4, new ParticleEffect(){{
                        particles = 3;
                        line = true;
                        lifetime = 18f;
                        length = 24f;
                        baseLength = 2f;
                        interp = Interp.pow4Out;
                        sizeInterp = Interp.pow2In;
                        lenFrom = 8f;
                        lenTo = 0f;
                        strokeFrom = 1.2f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.orangeRed;
                    }}){{
                        minPitch = maxPitch = 1f;
                    }},

                    new WrapEffect(Fx.squareWaveEffect, LPPal.orangeRed, 35f)
                );
                shootSound = LPSounds.shootPulse4;
                damageEffect = Fx.chainLightning;
                status = StatusEffects.none;
                statusDuration = 0;
                targetGround = targetAir = hitBuildings = hitUnits = true;
                maxTargets = 3;
                layer = 109.999f;
            }});
            abilities.add(new DeathSpawnBulletAbility(new EffectBulletType(0f){{
                hitSound = LPSounds.electricBlockerBreakLoud;
                hitSoundVolume = 0.95f;
                hitShake = 20;
                fragBullets = 1;
                fragVelocityMin = fragVelocityMax = fragLifeMin = fragLifeMax = 0f;
                fragOffsetMin = fragOffsetMax = 0;
                fragBullet = new MultiBulletType(
                    new BulletType(0f, 0f){{
                        lifetime = 0f;
                        instantDisappear = true;
                        hitSound = despawnSound = Sounds.none;
                        hitEffect = despawnEffect = new MultiEffect(
                            LPFx.circleOut(60f, Color.valueOf("FF6464"), 192f),
                            LPFx.smoothCircleOut(60f, Color.valueOf("FF6464"), 192f, 80, true),
                            LPFx.cutting(60f, Color.valueOf("FF6464"), LPPal.redLight, 60f, 45f, 110f),
                            LPFx.cutting(60f, Color.valueOf("FF6464"), LPPal.redLight, 60f, 135f, 110f)
                        );
                    }},
                    new EffectBulletType(0f){{
                        hitSound = Sounds.none;
                        fragBullets = 240;
                        fragVelocityMin = fragVelocityMax = fragLifeMin = fragLifeMax = 1f;
                        fragOffsetMin = fragOffsetMax = 0;
                        fragSpread = 1.5f;
                        fragRandomSpread = 0f;
                        fragBullet = new BasicBulletType(){{
                            sprite = "lp-plasma";
                            width = height = 9f;
                            shrinkY = 0f;
                            hitSize = 24f;
                            lightColor = hitColor = frontColor = backColor = trailColor = Color.valueOf("FF6464");
                            trailWidth = 3f;
                            trailLength = 12;
                            trailInterval = 0.5f;
                            trailEffect = new MultiEffect(
                                new ParticleEffect(){{
                                    particles = 1;
                                    region = "lp-square";
                                    lifetime = 5;
                                    length = 16f;
                                    baseLength = 8f;
                                    interp = Interp.circleOut;
                                    sizeInterp = Interp.pow2In;
                                    sizeFrom = 5f;
                                    sizeTo = 0f;
                                    colorFrom = colorTo = LPPal.redLight;
                                }},

                                new ParticleEffect(){{
                                    particles = 1;
                                    region = "lp-square";
                                    lifetime = 15;
                                    length = 16f;
                                    baseLength = 8f;
                                    interp = Interp.circleOut;
                                    sizeInterp = Interp.pow2In;
                                    sizeFrom = 5f;
                                    sizeTo = 0f;
                                    colorFrom = colorTo = Color.valueOf("FF6464");
                                }}
                            );
                            speed = 6f;
                            lifetime = 32f;
                            maxRange = 192f;
                            damage = 60f;
                            collidesAir = absorbable = hittable = false;
                            pierce = true;
                            pierceBuilding = true;
                            pierceCap = 3;
                            hitSound = LPSounds.shootBeamPlasma;
                            hitSoundVolume = 1.1f;
                            hitShake = 6;
                            hitEffect = despawnEffect = Fx.none;
                        }};
                    }}
                );
            }}, 1, 0f, 60f, 0f, 0f));
            weapons.add(new Weapon(){{
                mirror = false;
                rotate = false;
                x = 0f;
                y = 4.5f;
                reload = 144f;
                recoil = shootY = 0f;
                shootSound = LPSounds.termignisPierceArmor;
                shoot = new ShootPattern(){{
                    shots = 2;
                    shotDelay = 30f;
                }};
                bullet = new BasicBulletType(){{
                    sprite = "lp-energy-bullet";
                    width = 12f;
                    height = 16f;
                    shrinkY = 0f;
                    lightColor = hitColor = frontColor = backColor = trailColor = Color.valueOf("FF6464");
                    trailWidth = 2f;
                    trailLength = 8;
                    speed = 20f;
                    lifetime = 8f;
                    maxRange = rangeOverride = 160f;
                    damage = 80f;
                    ammoMultiplier = 1;
                    despawnHit = true;
                    fragOnDespawn = true;
                    fragOnHit = true;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 2;
                    weaveMag = 1.8f;
                    weaveScale = 2;
                    hitShake = despawnShake = 8;
                    hitSound = despawnSound = LPSounds.bombEmpHit;
                    hitSoundVolume = 1.2f;
                    hitEffect = despawnEffect = new MultiEffect(
                        LPFx.cutting(80f, Color.valueOf("FF6464"), Color.valueOf("FF6464"), false, 20f, 45f),
                        LPFx.cutting(60f, Color.valueOf("FF6464"), Color.valueOf("FF6464"), false, 20f, 135f),
                        LPFx.circleOut(80f, Color.valueOf("FF6464"), 48f),
                        LPFx.smoothCircleOut(80f, Color.valueOf("FF6464"), 48f, 80, true),
                        LPFx.trailHitSpark(80f, Color.valueOf("FF6464"), 4, 48f, 1.5f, 12f)
                    );
                    shootEffect = LPFx.XSharpShoot(30f, Color.valueOf("FF6464"), 60f);
                    smokeEffect = new ParticleEffect(){{
                        particles = 12;
                        line = true;
                        lifetime = 30f;
                        length = 48f;
                        baseLength = 8f;
                        cone = 30f;
                        interp = Interp.circleOut;
                        sizeInterp = Interp.pow3In;
                        lenFrom = 16f;
                        lenTo = 0f;
                        strokeFrom = 1.5f;
                        strokeTo = 0f;
                        colorFrom = colorTo = Color.valueOf("FF6464");
                    }};
                }};
            }});
        }
        @Override
        public void draw(Unit unit){
            super.draw(unit);
            Draw.color(Color.valueOf("FF6464"));
            Lines.stroke(2f);
            LP.graphics.Drawn.circlePercentFlip(unit.x, unit.y, 44f, Time.time, 120f);
        }};

        annihicoreSpawn = new SpawnUnitType("annihicore-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = Sounds.none;
                bullet = new BasicBulletType(){{
                    width = height = shrinkY = 0f;
                    killShooter = ignoreSpawnAngle = true;
                    collides = absorbable = hittable = keepVelocity = false;
                    speed = damage = 0f;
                    lifetime = 90f;
                    hitSound = despawnSound = LPSounds.shootRailgun3;
                    hitSoundVolume = 3.5f;
                    hitEffect = despawnEffect = Fx.none;
                    despawnUnit = annihicore;
                    despawnUnitRadius = 0;
                    parts.addAll(
                        new ShapePart(){{
                            hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 0f;
                            radiusTo = 48f;
                            stroke = 0f;
                            strokeTo = 4f;
                            rotation = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0f, 0.5f).curve(Interp.pow3Out);
                        }},

                        new ShapePart(){{
                            hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 4f;
                            radiusTo = 48f;
                            stroke = 0f;
                            strokeTo = 4f;
                            rotation = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0f, 0.5f).curve(Interp.pow3Out);
                        }},

                        new ShapePart(){{
                            hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 8f;
                            radiusTo = 48f;
                            stroke = 0f;
                            strokeTo = 4f;
                            rotation = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0f, 0.5f).curve(Interp.pow3Out);
                        }},

                        new FlarePart(){{
                            followRotation = false;
                            color1 = color2 = LPPal.eyesLight;
                            sides = 2;
                            radius = 0f;
                            radiusTo = 120f;
                            spinSpeed = 0f;
                            stroke = 1.5f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.4f, 1f).curve(Interp.pow2Out);
                        }},

                        new FlarePart(){{
                            followRotation = false;
                            color1 = color2 = LPPal.eyesLight;
                            sides = 2;
                            radius = 0f;
                            radiusTo = 55f;
                            spinSpeed = 6f;
                            stroke = 6f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.4f, 1f).curve(Interp.pow2Out);
                        }},

                        new FlarePart(){{
                            followRotation = false;
                            color1 = color2 = LPPal.eyesLight;
                            sides = 2;
                            radius = 0f;
                            radiusTo = 48f;
                            spinSpeed = -7f;
                            stroke = 6f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0.4f, 1f).curve(Interp.pow2Out);
                        }}
                    );
                    fragBullets = 1;
                    fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                    fragLifeMin = fragLifeMax = 1f;
                    fragBullet = new BasicBulletType(){{
                        width = height = shrinkY = 0f;
                        ignoreSpawnAngle = true;
                        collides = absorbable = hittable = keepVelocity = false;
                        speed = damage = 0f;
                        lifetime = 60f;
                        hitSound = despawnSound = Sounds.none;
                        hitEffect = despawnEffect = Fx.none;
                        parts.addAll(
                            new ShapePart(){{
                                hollow = true;
                                color = LPPal.eyesLight;
                                colorTo = Color.valueOf("F3E6E600");
                                sides = 4;
                                radius = 48f;
                                radiusTo = 64f;
                                stroke = 4f;
                                strokeTo = 6f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 70f;
                                stroke = 4f;
                                strokeTo = 0f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 10f;
                                radiusTo = 70f;
                                stroke = 4f;
                                strokeTo = 0f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new HaloPart(){{
                                tri = true;
                                color = LPPal.eyesLight;
                                colorTo = Color.valueOf("FF6464");
                                shapes = 2;
                                radius = 1.5f;
                                radiusTo = 1.5f;
                                triLength = 120f;
                                triLengthTo = 80f;
                                haloRadius = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }}
                        );
                    }};
                }};
            }});
        }};

        eradicator = new LPUnitType("eradicator"){{
            constructor = LegsUnit::create;
            databaseTag = "enemy";
            health = 1800f;
            armor = 12f;
            hitSize = 56f;
            rotateSpeed = 1.5f;
            speed = 0.3f;
            accel = 0.5f;
            drag = 0.5f;
            engineSize = 0f;
            fogRadius = 330f;
            range = maxRange = 220f;
            shadowElevation = 1f;
            groundLayer = 70f;
            stepShake = 4f;
            stepSound = LPSounds.mechStepHeavy;
            stepSoundVolume = 1f;
            stepSoundPitch = 0.8f;
            legCount = 6;
            legMoveSpace = 1.2f;
            legPairOffset = 10f;
            legLength = 32f;
            legMaxLength = 38f;
            legExtension = -8f;
            legBaseOffset = 12f;
            legLengthScl = 0.5f;
            legForwardScl = 0.45f;
            rippleScale = 2f;
            legSpeed = 1.2f;
            legGroupSize = 4;
            legStraightness = 0.4f;
            baseLegStraightness = 0.5f;
            legSplashRange = 24f;
            legSplashDamage = 24f;
            lockLegBase = true;
            legContinuousMove = true;
            hovering = true;
            useUnitCap = drawCell = false;
            deathSound = LPSounds.tankDestroyed2;
            deathSoundVolume = 1.2f;
            parts.addAll(
                new ShapePart(){{
                    y = 24f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 12f;
                    stroke = strokeTo = 2.5f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            abilities.add(new ShieldArcAbility(){{
                radius = 56f;
                regen = 2.2f;
                max = 2400f;
                cooldown = 320f;
                angle = 140f;
                angleOffset = 0f;
                x = 0f;
                y = -10f;
                whenShooting = false;
                width = 10f;
                chanceDeflect = 0.2f;
                deflectSound = LPSounds.shootSubsonic;
                breakSound = LPSounds.shield;
                hitSound = LPSounds.shootPulse4;
            }});
            abilities.add(new ShieldRegenFieldAbility(200f, 800f, 250f, 330f){{
                applyEffect = new WaveEffect(){{
                    lifetime = 30f;
                    interp = Interp.circleOut;
                    sizeFrom = 90f;
                    sizeTo = 0f;
                    strokeFrom = 2f;
                    strokeTo = 0f;
                    colorFrom = colorTo = LPPal.aureusDark;
                }};
                activeEffect = new MultiEffect(
                    new WaveEffect(){{
                        lifetime = 20f;
                        interp = Interp.pow2Out;
                        sizeFrom = 0f;
                        sizeTo = 60f;
                        strokeFrom = 3f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.aureusDark;
                    }},

                    new WaveEffect(){{
                        lifetime = 20f;
                        interp = Interp.pow3Out;
                        sizeFrom = 0f;
                        sizeTo = 65f;
                        strokeFrom = 3f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.aureusDark;
                    }},

                    new WaveEffect(){{
                        lifetime = 20f;
                        interp = Interp.pow4Out;
                        sizeFrom = 0f;
                        sizeTo = 70f;
                        strokeFrom = 3f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.aureusDark;
                    }}
                );
                sound = LPSounds.acceleratorLightning1;
                parentizeEffects = true;
            }});
            weapons.add(new Weapon(name("eradicator-1")){{
                rotate = true;
                top = false;
                x = 31f;
                y = 0f;
                shootX = -2f;
                shootY = 6f;
                shootCone = 20f;
                shootSound = LPSounds.shootFlak1;
                shootSoundVolume = 3f;
                rotationLimit = 30f;
                rotateSpeed = 1f;
                reload = 240f;
                recoil = 4f;
                recoilTime = 60f;
                shake = 7f;
                layerOffset = -0.01f;
                bullet = new BasicBulletType(){{
                    sprite = "lp-stardart";
                    width = height = 24f;
                    shrinkY = shrinkX = 0.2f;
                    lightColor = hitColor = frontColor = backColor = trailColor = LPPal.aureusDark;
                    trailWidth = 3f;
                    trailLength = 6;
                    spin = 6f;
                    speed = 22f;
                    lifetime = 10f;
                    scaleLife = true;
                    collides = collidesTiles = false;
                    rangeOverride = maxRange = 220f;
                    damage = 0f;
                    splashDamage = 240f;
                    splashDamageRadius = 40f;
                    ammoMultiplier = 1f;
                    armorMultiplier = 0.7f;
                    hitShake = 10f;
                    hitSound = LPSounds.launch;
                    hitSoundVolume = 3f;
                    hitEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 12;
                            line = true;
                            lifetime = 60f;
                            length = 32f;
                            baseLength = 6f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 12f;
                            lenTo = 0f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new ParticleEffect(){{
                            particles = 1;
                            lifetime = 60f;
                            length = baseLength = 0f;
                            interp = sizeInterp = Interp.pow4In;
                            sizeFrom = 6f;
                            sizeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        LPFx.circleOut(60f, hitColor, 40f)
                    );
                    despawnEffect = smokeEffect = Fx.none;
                    shootEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 6;
                            line = true;
                            lifetime = 15f;
                            length = 18f;
                            baseLength = 6f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow2In;
                            lenFrom = 8f;
                            lenTo = 0f;
                            strokeFrom = 1.2f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new ParticleEffect(){{
                            particles = 1;
                            lifetime = 15f;
                            length = baseLength = 0f;
                            interp = sizeInterp = Interp.pow2In;
                            sizeFrom = 6f;
                            sizeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new WaveEffect(){{
                            lifetime = 15f;
                            interp = Interp.pow2Out;
                            sizeFrom = 0f;
                            sizeTo = 24f;
                            strokeFrom = 2f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new WaveEffect(){{
                            lifetime = 15f;
                            interp = Interp.pow3Out;
                            sizeFrom = 0f;
                            sizeTo = 32f;
                            strokeFrom = 3f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }}
                    );
                    fragBullets = 3;
                    fragVelocityMin = 0.5f;
                    fragVelocityMax = 1f;
                    fragOffsetMin = fragOffsetMax = 0f;
                    fragLifeMin = fragLifeMax = 1f;
                    fragBullet = new BasicBulletType(){{
                        sprite = "circle-bullet";
                        width = height = 8f;
                        shrinkY = 0f;
                        lightColor = hitColor = frontColor = backColor = trailColor = LPPal.aureusDark;
                        trailWidth = 3f;
                        trailLength = 240;
                        lifetime = 40f;
                        speed = 8f;
                        drag = 0.05f;
                        damage = 60f;
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 128;
                        armorMultiplier = 0.7f;
                        hitShake = 4f;
                        hitEffect = LPFx.cutting(40f, hitColor, hitColor, false, 24f, -1f);
                        hitSound = LPSounds.shootSmite;
                        hitSoundVolume = 1.4f;
                        despawnEffect = new MultiEffect(
                            new ParticleEffect(){{
                                particles = 8;
                                line = true;
                                lifetime = 30f;
                                length = 32f;
                                baseLength = 8f;
                                interp = Interp.circleOut;
                                sizeInterp = Interp.pow2In;
                                lenFrom = 16f;
                                lenTo = 0f;
                                strokeFrom = 1.5f;
                                strokeTo = 0f;
                                colorFrom = colorTo = LPPal.aureusDark;
                            }},

                            new WaveEffect(){{
                                lifetime = 30f;
                                interp = Interp.circleOut;
                                sizeFrom = 0f;
                                sizeTo = 40f;
                                strokeFrom = 2f;
                                strokeTo = 0f;
                                colorFrom = colorTo = LPPal.aureusDark;
                            }},

                            new WaveEffect(){{
                                startDelay = 5f;
                                lifetime = 25f;
                                interp = Interp.circleOut;
                                sizeFrom = 0f;
                                sizeTo = 40f;
                                strokeFrom = 2f;
                                strokeTo = 0f;
                                colorFrom = colorTo = LPPal.aureusDark;
                            }}
                        );
                        despawnHit = false;
                        fragOnDespawn = false;
                        parts.addAll(
                            new HaloPart(){{
                                color = colorTo = hitColor;
                                sides = 256;
                                shapes = 1;
                                radius = 6f;
                                radiusTo = 0f;
                                triLength = 6f;
                                triLengthTo = 0f;
                                haloRadius = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.pow10In);
                            }}
                        );
                    }};
                }};
            }});
            weapons.add(new Weapon(name("eradicator-2")){{
                rotate = true;
                x = 19f;
                y = 1f;
                shootX = 0f;
                shootY = 1f;
                shootCone = 8f;
                shootSound = LPSounds.shootGauss3;
                shootSoundVolume = 2.2f;
                rotateSpeed = 4f;
                reload = 100f;
                recoil = 2f;
                recoilTime = 30f;
                shake = 4f;
                shoot = new ShootPattern(){{
                    shots = 4;
                    shotDelay = 5f;
                }};
                bullet = new MultiBulletType(
                    new BasicBulletType(){{
                        sprite = "lp-pointy-bullet";
                        width = height = 9f;
                        shrinkY = 0f;
                        lightColor = hitColor = frontColor = backColor = trailColor = LPPal.aureusDark;
                        trailWidth = 3f;
                        trailLength = 6;
                        lifetime = 22f;
                        speed = 10f;
                        weaveMag = 2f;
                        weaveScale = 3f;
                        damage = 12f;
                        hitShake = despawnShake = 4f;
                        hitSound = despawnSound = LPSounds.shootFlak3;
                        hitSoundVolume = 2.5f;
                        hitEffect = despawnEffect = new ParticleEffect(){{
                            particles = 5;
                            region = "lp-square";
                            lifetime = 40f;
                            length = 20f;
                            baseLength = 4f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow2In;
                            sizeFrom = 3f;
                            sizeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }};
                    }},

                    new BasicBulletType(){{
                        sprite = "lp-pointy-bullet";
                        width = height = 9f;
                        shrinkY = 0f;
                        lightColor = hitColor = frontColor = backColor = trailColor = LPPal.aureusDark;
                        trailWidth = 3f;
                        trailLength = 6;
                        lifetime = 22f;
                        speed = 10f;
                        weaveMag = 2f;
                        weaveScale = 2.5f;
                        damage = 12f;
                        hitShake = despawnShake = 4f;
                        hitSound = despawnSound = LPSounds.shootFlak3;
                        hitSoundVolume = 2.5f;
                        hitEffect = despawnEffect = new ParticleEffect(){{
                            particles = 5;
                            region = "lp-square";
                            lifetime = 40f;
                            length = 20f;
                            baseLength = 4f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow2In;
                            sizeFrom = 3f;
                            sizeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }};
                    }},

                    new BasicBulletType(){{
                        sprite = "lp-pointy-bullet";
                        width = height = 9f;
                        shrinkY = 0f;
                        lightColor = hitColor = frontColor = backColor = trailColor = LPPal.aureusDark;
                        trailWidth = 3f;
                        trailLength = 6;
                        lifetime = 22f;
                        speed = 10f;
                        weaveMag = 2f;
                        weaveScale = 2f;
                        damage = 12f;
                        hitShake = despawnShake = 4f;
                        hitSound = despawnSound = LPSounds.shootFlak3;
                        hitSoundVolume = 2.5f;
                        hitEffect = despawnEffect = new ParticleEffect(){{
                            particles = 5;
                            region = "lp-square";
                            lifetime = 40f;
                            length = 20f;
                            baseLength = 4f;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.pow2In;
                            sizeFrom = 3f;
                            sizeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }};
                    }}
                ){{
                    speed = 22f;
                    lifetime = 10f;
                    rangeOverride = maxRange = 220f;
                    damage = 36f;
                    ammoMultiplier = 1f;
                    shootEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 4;
                            region = "lp-square";
                            lifetime = 20f;
                            length = 12f;
                            baseLength = 4f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow2In;
                            sizeFrom = 3f;
                            sizeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }},

                        new WaveEffect(){{
                            lifetime = 20f;
                            interp = Interp.pow3Out;
                            sizeFrom = 0f;
                            sizeTo = 18f;
                            strokeFrom = 2f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.aureusDark;
                        }}
                    );
                    smokeEffect = Fx.none;
                }};
            }});
        }};

        eradicatorSpawn = new SpawnUnitType("eradicator-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = Sounds.none;
                bullet = new BasicBulletType(){{
                    width = height = shrinkY = 0f;
                    killShooter = ignoreSpawnAngle = true;
                    collides = absorbable = hittable = keepVelocity = false;
                    speed = damage = 0f;
                    lifetime = 30f;
                    hitSound = despawnSound = Sounds.none;
                    shootEffect = smokeEffect = hitEffect = despawnEffect = Fx.none;
                    parts.addAll(
                        new ShapePart(){{
                            hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 0f;
                            radiusTo = 56f;
                            stroke = 0f;
                            strokeTo = 4f;
                            rotation = 0f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.pow3Out);
                        }},

                        new ShapePart(){{
                            hollow = true;
                            color = Color.valueOf("F3E6E600");
                            colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 120f;
                            radiusTo = 56f;
                            stroke = 6f;
                            strokeTo = 4f;
                            rotation = 0f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.pow3Out);
                        }}
                    );
                    fragBullets = 1;
                    fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                    fragLifeMin = fragLifeMax = 1f;
                    fragBullet = new EffectBulletType(0f){{
                        spawnBulletRandomSpread = 360f;
                        spawnBullets = Seq.with(
                            new BasicBulletType(){{
                                width = height = shrinkY = 0f;
                                ignoreSpawnAngle = true;
                                collides = absorbable = hittable = keepVelocity = false;
                                speed = damage = 0f;
                                lifetime = 90f;
                                hitEffect = despawnEffect = Fx.none;
                                hitSound = LPSounds.shootRailgun3;
                                hitSoundVolume = 3.8f;
                                hitShake = 20f;
                                despawnUnit = eradicator;
                                despawnUnitRadius = 0f;
                                parts.addAll(
                                    new ShapePart(){{
                                        hollow = true;
                                        color = colorTo = LPPal.eyesLight;
                                        sides = 4;
                                        radius = 56f;
                                        radiusTo = 0f;
                                        stroke = 4f;
                                        strokeTo = 0f;
                                        rotation = 0f;
                                        layer = 110f;
                                        progress = PartProgress.life.curve(Interp.pow10In);
                                    }}
                                );
                                fragBullets = 1;
                                fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                                fragLifeMin = fragLifeMax = 1f;
                                fragBullet = new BasicBulletType(){{
                                    width = height = shrinkY = 0f;
                                    ignoreSpawnAngle = true;
                                    collides = absorbable = hittable = keepVelocity = false;
                                    speed = damage = 0f;
                                    lifetime = 60f;
                                    hitSound = despawnSound = Sounds.none;
                                    hitEffect = despawnEffect = Fx.none;
                                    parts.addAll(
                                        new ShapePart(){{
                                            hollow = true;
                                            color = LPPal.eyesLight;
                                            colorTo = Color.valueOf("F3E6E600");
                                            sides = 4;
                                            radius = 56f;
                                            radiusTo = 90f;
                                            stroke = 4f;
                                            strokeTo = 6f;
                                            rotation = 0f;
                                            layer = 110f;
                                            progress = PartProgress.life.curve(Interp.circleOut);
                                        }},

                                        new ShapePart(){{
                                            hollow = true;
                                            color = colorTo = LPPal.eyesLight;
                                            sides = 4;
                                            radius = 16f;
                                            radiusTo = 0f;
                                            stroke = 4f;
                                            strokeTo = 4f;
                                            rotation = 0f;
                                            layer = 110f;
                                            progress = PartProgress.life.curve(Interp.circleOut);
                                        }},

                                        new HaloPart(){{
                                            tri = true;
                                            color = colorTo = LPPal.eyesLight;
                                            shapes = 2;
                                            radius = 1f;
                                            radiusTo = 0f;
                                            triLength = 120f;
                                            triLengthTo = 120f;
                                            haloRadius = 0f;
                                            layer = 110f;
                                            progress = PartProgress.life.curve(Interp.pow2In);
                                        }},

                                        new HaloPart(){{
                                            tri = true;
                                            color = colorTo = LPPal.eyesLight;
                                            shapes = 2;
                                            radius = 1.5f;
                                            radiusTo = 0f;
                                            triLength = 56f;
                                            triLengthTo = 0f;
                                            haloRadius = 0f;
                                            layer = 110f;
                                            progress = PartProgress.life.curve(Interp.pow2In);
                                        }}
                                    );
                                }};
                            }},

                            new BasicBulletType(){{
                                width = height = shrinkY = 0f;
                                ignoreSpawnAngle = true;
                                collides = absorbable = hittable = keepVelocity = false;
                                speed = damage = 0f;
                                lifetime = 75f;
                                hitSound = despawnSound = Sounds.none;
                                hitEffect = despawnEffect = Fx.none;
                                bulletInterval = 5f;
                                intervalBullets = 2;
                                intervalBullet = new BasicBulletType(){{
                                    width = height = shrinkY = 0f;
                                    ignoreSpawnAngle = true;
                                    collides = absorbable = hittable = keepVelocity = false;
                                    speed = damage = lifetime = 0f;
                                    hitSound = despawnSound = Sounds.none;
                                    hitEffect = despawnEffect = Fx.none;
                                    hitShake = 5f;
                                    fragBullets = 1;
                                    fragVelocityMin = fragVelocityMax = 0f;
                                    fragOffsetMin = 16f;
                                    fragOffsetMax = 120f;
                                    fragLifeMin = 0.2f;
                                    fragLifeMax = 1f;
                                    fragBullet = new BasicBulletType(){{
                                        width = height = shrinkY = 0f;
                                        ignoreSpawnAngle = true;
                                        collides = absorbable = hittable = keepVelocity = false;
                                        speed = damage = 0f;
                                        lifetime = 15f;
                                        hitSound = despawnSound = Sounds.none;
                                        hitEffect = despawnEffect = Fx.none;
                                        parts.addAll(
                                            new HaloPart(){{
                                                tri = true;
                                                color = colorTo = LPPal.eyesLight;
                                                shapes = 2;
                                                radius = 12f;
                                                radiusTo = 12f;
                                                triLength = 80f;
                                                triLengthTo = 0f;
                                                haloRotation = 90f;
                                                haloRadius = 0f;
                                                layer = 110f;
                                                progress = PartProgress.life.curve(Interp.pow2In);
                                            }}
                                        );
                                    }};
                                }};
                            }}
                        );
                    }};
                }};
            }});
        }};

        rusher = new LPUnitType("rusher"){{
            constructor = UnitEntity::create;
            databaseTag = "enemy";
            health = 344f;
            armor = 5f;
            hitSize = 24f;
            rotateSpeed = 2f;
            speed = 1.8f;
            accel = 0.05f;
            drag = 0.02f;
            engineSize = 3f;
            engineColor = LPPal.orange;
            engineOffset = 12.5f;
            fogRadius = maxRange = 160f;
            shadowElevation = 0.25f;
            loopSound = Sounds.loopHover;
            crashDamageMultiplier = 0.45f;
            wreckHealthMultiplier = 0.8f;
            flying = true;
            flyingLayer = 99f;
            canDrown = false;
            alwaysUnlocked = false;
            drawCell = false;
            autoDropBombs = true;
            circleTarget = true;
            faceTarget = false;
            targetAir = false;
            useUnitCap = false;
            targetFlags = new BlockFlag[]{BlockFlag.factory, null};
            parts.addAll(
                new ShapePart(){{
                    y = 11.5f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 6f;
                    stroke = strokeTo = 1.5f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            weapons.add(new Weapon(){{
                mirror = false;
                x = y = shootY = 0f;
                reload = 140f;
                shootCone = 180f;
                shootSound = LPSounds.shootPulse3;
                shootStatus = LPStatusEffect.claim;
                shootStatusDuration = 180;
                shoot = new ShootPattern(){{
                    shots = 3;
                    shotDelay = 20f;
                }};
                bullet = new BasicBulletType(){{
                    sprite = "lp-stardart";
                    width = height = 12f;
                    shrinkX = shrinkY = 0.6f;
                    frontColor = backColor = lightColor = hitColor = LPPal.orange;
                    layer = 98.99f;
                    ignoreSpawnAngle = true;
                    collidesAir = collides = keepVelocity = false;
                    lifetime = 40f;
                    speed = 0f;
                    spin = 2f;
                    damage = 8f;
                    splashDamage = 16f;
                    splashDamageRadius = 32f;
                    hitSound = despawnSound = LPSounds.shootBlaster3;
                    hitShake = despawnShake = 4f;
                    hitEffect = despawnEffect = new MultiEffect(
                        new ParticleEffect(){{
                            particles = 1;
                            region = "lp-star";
                            lifetime = 30f;
                            length = 0f;
                            baseLength = 0f;
                            baseRotation = 45f;
                            useRotation = false;
                            interp = Interp.circleOut;
                            sizeInterp = Interp.circleIn;
                            sizeFrom = 16f;
                            sizeTo = 0f;
                            colorFrom = colorTo = LPPal.orange;
                        }},

                        new WaveEffect(){{
                            lifetime = 30f;
                            interp = Interp.circleOut;
                            sizeFrom = 0f;
                            sizeTo = 24f;
                            strokeFrom = 3f;
                            strokeTo = 0f;
                            colorFrom = colorTo = LPPal.orange;
                        }}
                    );
                    shootEffect = smokeEffect = Fx.none;
                }};
            }});
        }};

        rusherSpawn = new SpawnUnitType("rusher-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = Sounds.none;
                bullet = new BasicBulletType(){{
                    width = height = shrinkY = 0f;
                    killShooter = ignoreSpawnAngle = true;
                    collides = absorbable = hittable = keepVelocity = false;
                    speed = damage = 0f;
                    lifetime = 30f;
                    hitSound = despawnSound = Sounds.none;
                    hitEffect = despawnEffect = shootEffect = smokeEffect = Fx.none;
                    parts.addAll(
                        new ShapePart(){{
                            hollow = true;
                            color = colorTo = LPPal.eyesLight;
                            sides = 4;
                            radius = 20f;
                            radiusTo = 32f;
                            stroke = 0f;
                            strokeTo = 3f;
                            rotation = 0f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.circleIn);
                        }}
                    );
                    fragBullets = 1;
                    fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                    fragLifeMin = fragLifeMax = 1f;
                    fragBullet = new BasicBulletType(){{
                        width = height = shrinkY = 0f;
                        ignoreSpawnAngle = true;
                        collides = absorbable = hittable = keepVelocity = false;
                        speed = damage = 0f;
                        lifetime = 40f;
                        hitSound = despawnSound = LPSounds.laser2;
                        hitSoundVolume = 3f;
                        hitEffect = despawnEffect = Fx.none;
                        parts.addAll(
                            new ShapePart(){{
                                hollow = true;
                                color = colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 32f;
                                stroke = 3f;
                                strokeTo = 3f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new HaloPart(){{
                                tri = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                shapes = 4;
                                radius = 12f;
                                radiusTo = 0f;
                                triLength = triLengthTo = 12f;
                                shapeRotation = 180f;
                                haloRadius = 32f;
                                haloRadiusTo = 0f;
                                haloRotation = 45f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }},

                            new HaloPart(){{
                                tri = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                shapes = 4;
                                radius = 12f;
                                radiusTo = 0f;
                                triLength = 12f;
                                triLengthTo = 80f;
                                haloRadius = 32f;
                                haloRadiusTo = 0f;
                                haloRotation = 45f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleOut);
                            }}
                        );
                        despawnUnit = rusher;
                        despawnUnitRadius = 0f;
                        fragBullets = 1;
                        fragVelocityMin = fragVelocityMax = fragLifeMin = fragLifeMax = 1f;
                        fragBullet = new MultiBulletType(
                            new EffectBulletType(0f){{
                                fragBullets = 5;
                                fragVelocityMin = fragVelocityMax = fragLifeMin = fragLifeMax = 1f;
                                fragBullet = new LightningBulletType(){{
                                    damage = 15f;
                                    lightningLength = 8;
                                    lightningLengthRand = 8;
                                    lightningColor = LPPal.eyesLight;
                                }};
                            }},

                            new BasicBulletType(){{
                                width = height = shrinkY = 0f;
                                ignoreSpawnAngle = true;
                                collides = absorbable = hittable = keepVelocity = false;
                                speed = damage = 0f;
                                lifetime = 20f;
                                hitSound = despawnSound = Sounds.none;
                                hitEffect = despawnEffect = Fx.none;
                                parts.addAll(
                                    new ShapePart(){{
                                        hollow = true;
                                        color = Color.valueOf("F3E6E680");
                                        colorTo = Color.valueOf("F3E6E600");
                                        sides = 4;
                                        radius = 32f;
                                        radiusTo = 24f;
                                        stroke = 3f;
                                        strokeTo = 3f;
                                        rotation = 0f;
                                        layer = 110f;
                                        progress = PartProgress.life.curve(Interp.circleIn);
                                    }}
                                );
                            }}
                        ){{}};
                    }};
                }};
            }});
        }};

        vectruptor = new LPUnitType("vectruptor"){{
            constructor = UnitEntity::create;
            databaseTag = "enemy";
            health = 400f;
            armor = 12f;
            hitSize = 32f;
            rotateSpeed = 0f;
            speed = 2f;
            accel = 0.08f;
            drag = 0.08f;
            engineSize = 0f;
            fogRadius = maxRange = 160f;
            flyingLayer = 100f;
            drownTimeMultiplier = -1f;
            wreckHealthMultiplier = 0f;
            loopSound = LPSounds.axisFlashlight;
            deathSound = Sounds.none;
            deathExplosionEffect = Fx.none;
            flying = true;
            drawCell = drawBody = lowAltitude = faceTarget = canDrown = wobble = false;
            circleTarget = true;
            circleTargetRadius = 120f;
            createScorch = false;
            createWreck = false;
            useUnitCap = false;
            targetFlags = new BlockFlag[]{BlockFlag.turret, null};
            parts.addAll(
                new ShapePart(){{
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 10f;
                    stroke = strokeTo = 1.5f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            abilities.add(new UnitSpawnAbility(this, 1500f, 0f, 0f){
                {
                    spawnEffect = new WaveEffect(){{
                        lifetime = 40f;
                        interp = Interp.circleOut;
                        sides = 4;
                        sizeFrom = 0f;
                        sizeTo = 120f;
                        strokeFrom = 3f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.eyesLight;
                    }};
                    parentizeEffects = true;
                    timer = 60f;
                }
                @Override
                public void draw(Unit unit){}
            });
            abilities.add(new DeathSpawnBulletAbility(new BasicBulletType(){{
                width = height = shrinkY = 0f;
                speed = lifetime = damage = 0f;
                splashDamage = 1000f;
                splashDamageRadius = 120f;
                splashDamagePierce = true;
                armorMultiplier = 0.25f;
                hitSound = LPSounds.blastShockwave;
                hitSoundVolume = 2.6f;
                hitShake = 5f;
                hitEffect = LPFx.sharpHitRotateBlast(90f, LPPal.eyesLight, 20, 120f, Mathf.random(1f));
                fragBullets = 1;
                fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                fragLifeMin = fragLifeMax = 1f;
                fragBullet = new BasicBulletType(){{
                    width = height = shrinkY = 0f;
                    ignoreSpawnAngle = true;
                    collides = absorbable = hittable = keepVelocity = false;
                    speed = damage = 0f;
                    lifetime = 160f;
                    hitSound = despawnSound = Sounds.none;
                    hitEffect = despawnEffect = Fx.none;
                    parts.addAll(
                        new HaloPart(){{
                            hollow = true;
                            color = colorTo = LPPal.eyesLight;
                            sides = 4;
                            shapes = 1;
                            radius = 18f;
                            radiusTo = 120f;
                            triLength = 18f;
                            triLengthTo = 120f;
                            stroke = 3f;
                            strokeTo = 0f;
                            haloRadius = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0f, 0.8f).curve(Interp.pow10In);
                        }},

                        new HaloPart(){{
                            hollow = true;
                            color = colorTo = LPPal.eyesLight;
                            sides = 4;
                            shapes = 1;
                            radius = 18f;
                            radiusTo = 120f;
                            triLength = 18f;
                            triLengthTo = 120f;
                            stroke = 3f;
                            strokeTo = 0f;
                            haloRadius = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0f, 0.9f).curve(Interp.pow10In);
                        }},

                        new HaloPart(){{
                            hollow = true;
                            color = colorTo = LPPal.eyesLight;
                            sides = 4;
                            shapes = 1;
                            radius = 18f;
                            radiusTo = 120f;
                            triLength = 18f;
                            triLengthTo = 120f;
                            stroke = 3f;
                            strokeTo = 0f;
                            haloRadius = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0f, 1f).curve(Interp.pow10In);
                        }},

                        new HaloPart(){{
                            hollow = true;
                            color = LPPal.eyesLight;
                            colorTo = Color.valueOf("F3E6E600");
                            sides = 4;
                            shapes = 1;
                            radius = 120f;
                            radiusTo = 0f;
                            triLength = 120f;
                            triLengthTo = 0f;
                            stroke = 3f;
                            strokeTo = 6f;
                            haloRadius = 0f;
                            layer = 110f;
                            progress = PartProgress.life.compress(0f, 0.25f).curve(Interp.pow10In);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 4;
                            radius = 8f;
                            radiusTo = 0f;
                            triLength = 24f;
                            triLengthTo = 30f;
                            haloRadius = 0f;
                            haloRadiusTo = 40f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.pow2Out);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 4;
                            radius = 8f;
                            radiusTo = 0f;
                            triLength = 4f;
                            triLengthTo = 10f;
                            shapeRotation = 180f;
                            haloRadius = 0f;
                            haloRadiusTo = 40f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.pow2Out);
                        }}
                    );
                }};
            }}, 1, 0f, 0f, 1000f, 120f));
            weapons.add(new Weapon(name("vectruptor")){{
                mirror = display = autoTarget = controllable = ignoreRotation = false;
                x = y = shootY = 0f;
                minWarmup = 10f;
                baseRotation = 90f;
            }});
            weapons.add(new Weapon(){{
                mirror =  false;
                top = false;
                rotate = true;
                x = y = shootY = 0f;
                reload = 100f;
                rotateSpeed = 6f;
                shootSound = LPSounds.shootFlak3;
                shootSoundVolume = 4f;
                shake = 5f;
                bullet = new BasicBulletType(){{
                    sprite = "lp-pointy-bullet";
                    width = 6.5f;
                    height = 8f;
                    shrinkY = 0;
                    lightColor = frontColor = backColor = trailColor = hitColor = LPPal.eyesLight;
                    trailWidth = 2f;
                    trailLength = 12;
                    speed = 8f;
                    lifetime = 20f;
                    damage = 180f;
                    armorMultiplier = 1.75f;
                    shieldDamageMultiplier = 1.6f;
                    hitSound = despawnSound = LPSounds.strawberrydeath;
                    hitShake = despawnShake = 6f;
                    hitEffect = despawnEffect = new MultiEffect(
                        LPFx.trailHitSpark(60f, hitColor, 16, 40f, 1f, 10f),
                        LPFx.cutting(60f, hitColor, hitColor, 12f, 36f, 110f),
                        LPFx.cutting(60f, hitColor, hitColor, 10f, 96f, 110f)
                    );
                    shootEffect = smokeEffect = Fx.none;
                }};
            }});
        }};

        vectruptorSpawn = new SpawnUnitType("vectruptor-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = LPSounds.beamLargeCharge;
                shootSoundVolume = 0.55f;
                bullet = new BasicBulletType(){{
                    width = height = shrinkY = 0f;
                    killShooter = ignoreSpawnAngle = true;
                    collides = absorbable = hittable = keepVelocity = false;
                    speed = damage = 0f;
                    lifetime = 80f;
                    hitSound = despawnSound = Sounds.none;
                    shootEffect = smokeEffect = hitEffect = despawnEffect = Fx.none;
                    parts.addAll(
                        new HaloPart(){{
                            hollow = true;
                            color = colorTo = LPPal.eyesLight;
                            sides = 4;
                            shapes = 1;
                            radius = 0f;
                            radiusTo = 32f;
                            triLength = 0f;
                            triLengthTo = 32f;
                            stroke = 3f;
                            strokeTo = 3f;
                            haloRadius = 0f;
                            layer = 110f;
                            progress = PartProgress.life.curve(Interp.circleOut);
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 1f;
                            radiusTo = 1.5f;
                            triLength = 0f;
                            triLengthTo = 80f;
                            haloRadius = 0f;
                            layer = 110f;
                            progress = PartProgress.life.mul(0f).sin(1f, 0.2f).add(0.9f)
                            .mul(PartProgress.life.curve(Interp.circleIn));
                        }},

                        new HaloPart(){{
                            tri = true;
                            color = colorTo = LPPal.eyesLight;
                            shapes = 2;
                            radius = 1f;
                            radiusTo = 1.5f;
                            triLength = 0f;
                            triLengthTo = 40f;
                            haloRadius = 0f;
                            haloRotation = 90f;
                            layer = 110f;
                            progress = PartProgress.life.mul(0f).sin(1f, 0.2f).add(0.9f)
                            .mul(PartProgress.life.curve(Interp.circleIn));
                        }}
                    );
                    fragBullets = 1;
                    fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                    fragLifeMin = fragLifeMax = 1f;
                    fragBullet = new BasicBulletType(){{
                        width = height = shrinkY = 0f;
                        ignoreSpawnAngle = true;
                        collides = absorbable = hittable = keepVelocity = false;
                        speed = damage = 0f;
                        lifetime = 80f;
                        hitSound = despawnSound = LPSounds.laser2;
                        hitSoundVolume = 3f;
                        hitEffect = despawnEffect = Fx.none;
                        despawnUnit = vectruptor;
                        despawnUnitRadius = 0f;
                        parts.addAll(
                            new HaloPart(){{
                                tri = true;
                                color = LPPal.eyesLight;
                                colorTo = Color.valueOf("F3E6E600");
                                shapes = 2;
                                radius = 1.5f;
                                radiusTo = 1.5f;
                                triLength = 80f;
                                triLengthTo = 120f;
                                haloRadius = 0f;
                                layer = 110f;
                                progress = PartProgress.life.mul(0f).sin(0.1f, 1f).add(0.9f)
                                .mul(PartProgress.life.curve(Interp.pow10In));
                            }},

                            new HaloPart(){{
                                tri = true;
                                color = LPPal.eyesLight;
                                colorTo = Color.valueOf("F3E6E600");
                                shapes = 2;
                                radius = 1.5f;
                                radiusTo = 1.5f;
                                triLength = 40f;
                                triLengthTo = 60f;
                                haloRadius = 0f;
                                haloRotation = 90f;
                                layer = 110f;
                                progress = PartProgress.life.mul(0f).sin(0.1f, 1f).add(0.9f)
                                .mul(PartProgress.life.curve(Interp.pow10In));
                            }},

                            new HaloPart(){{
                                hollow = true;
                                color = colorTo = LPPal.eyesLight;
                                sides = 4;
                                shapes = 1;
                                radius = 32f;
                                radiusTo = 48f;
                                triLength = 32f;
                                triLengthTo = 48f;
                                stroke = 3f;
                                strokeTo = 3f;
                                haloRadius = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.pow2Out);
                            }}
                        );
                        fragBullets = 1;
                        fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                        fragLifeMin = fragLifeMax = 1f;
                        fragBullet = new BasicBulletType(){{
                            width = height = shrinkY = 0f;
                            ignoreSpawnAngle = true;
                            collides = absorbable = hittable = keepVelocity = false;
                            speed = damage = 0f;
                            lifetime = 40f;
                            hitSound = despawnSound = Sounds.none;
                            hitEffect = despawnEffect = Fx.none;
                            parts.addAll(
                                new HaloPart(){{
                                    hollow = true;
                                    color = LPPal.eyesLight;
                                    colorTo = Color.valueOf("F3E6E600");
                                    sides = 4;
                                    shapes = 1;
                                    radius = 48f;
                                    radiusTo = 32f;
                                    triLength = 48f;
                                    triLengthTo = 32f;
                                    stroke = 3f;
                                    strokeTo = 6f;
                                    haloRadius = 0f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }}
                            );
                        }};
                    }};
                }};
            }});
        }};

        sustainer = new LPUnitType("sustainer"){{
            constructor = UnitEntity::create;
            aiController = FlyingFollowAI::new;
            databaseTag = "enemy";
            health = 570f;
            armor = 7f;
            hitSize = 48f;
            rotateSpeed = 3f;
            speed = 1.5f;
            accel = 0.1f;
            drag = 0.1f;
            engineSize = 0.5f;
            engineOffset = 21.5f;
            engineColor = LPPal.orangeRed;
            engineLayer = 98.999f;
            engines.addAll(
                new UnitEngine(0f, -21.5f, 8f, -90f),
                new UnitEngine(8f, -18f, 7.5f, -75f),
                new UnitEngine(-8f, -18f, 7.5f, 255f)
            );
            trailLength = 240;
            trailColor = LPPal.orangeRed;
            buildSpeed = 1.5f;
            itemCapacity = 192;
            lightColor = LPPal.orangeRed;
            fogRadius = 240;
            range = maxRange = 96f;
            flyingLayer = 99.001f;
            drownTimeMultiplier = -1f;
            wreckHealthMultiplier = 0.5f;
            shadowElevation = 1f;
            deathSound = Sounds.none;
            deathExplosionEffect = Fx.none;
            drawCell = drawBuildBeam = false;
            flying = true;
            lowAltitude = canDrown = false;
            autoDropBombs = true;
            faceTarget = targetAir = false;
            useUnitCap = false;
            circleTarget = true;
            circleTargetRadius = 240f;
            parts.addAll(
                new ShapePart(){{
                    y = 10f;
                    hollow = true;
                    color = colorTo = LPPal.eyesLight;
                    sides = 4;
                    radius = radiusTo = 12f;
                    stroke = strokeTo = 2f;
                    rotation = 0f;
                    layer = 110f;
                }}
            );
            abilities.add(new EnergyFieldAbility(12f, 168f, 240f){{
                x = 0f;
                y = 10f;
                color = Color.valueOf("FF5845");
                effectRadius = 3f;
                sectorRad = 0.2f;
                sectors = 4;
                rotateSpeed = -6f;
                healPercent = 5f;
                sameTypeHealMult = 0.8f;
                displayHeal = false;
                blinkScl = 20f;
                blinkSize = 0.1f;
                layer = 110f;
                useAmmo = true;
                healEffect = new MultiEffect(
                    new SoundEffect(LPSounds.shootOmura, Fx.healBlock){{
                        minPitch = maxPitch = 1f;
                        minVolume = maxVolume = 1f;
                    }},
                    new WrapEffect(Fx.healWaveDynamic, Color.valueOf("98ffa9"), 32f)
                );
                hitEffect = new MultiEffect(
                    new SoundEffect(LPSounds.shootOmura, Fx.attackCommand){{
                        minPitch = maxPitch = 1f;
                        minVolume = maxVolume = 1f;
                    }}
                );
                shootSound = LPSounds.shootOmura;
                damageEffect = new MultiEffect(Fx.chainLightning, Fx.chainEmp, Fx.lightning);
                status = StatusEffects.none;
                statusDuration = 0f;
                targetGround = true;
                targetAir = true;
                hitBuildings = true;
                hitUnits = true;
                maxTargets = 12;
            }});
            abilities.add(new ForceFieldAbility(96f, 0.5f, 1600f, 300f){{
                sides = 6;
                rotation = 0f;
                breakSound = LPSounds.shockBulletSyntax;
                hitSound = LPSounds.sinusblast2;
                hitSoundVolume = 0.3f;
            }});
            abilities.add(new DeathSpawnBulletAbility(new BasicBulletType(){{
                width = height = shrinkY = 0f;
                collides = absorbable = hittable = keepVelocity = false;
                speed = damage = lifetime = 0f;
                splashDamage = 240f;
                splashDamageRadius = 240f;
                hitSound = despawnSound = LPSounds.shootRailgun2;
                hitSoundVolume = 0.3f;
                hitShake = despawnShake = 12f;
                hitEffect = despawnEffect = new MultiEffect(
                    LPFx.annihilation(160f, LPPal.orangeRed, 24f, 12),
                    LPFx.smoothCircleOut(160f, LPPal.orangeRed, 264f, 60, true),
                    new ParticleEffect(){{
                        particles = 24;
                        line = true;
                        lifetime = 160f;
                        length = 240f;
                        baseLength = 8f;
                        interp = Interp.circleOut;
                        sizeInterp = Interp.pow10In;
                        lenFrom = 16f;
                        lenTo = 0f;
                        strokeFrom = 1.5f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.orangeRed;
                    }},

                    new ParticleEffect(){{
                        particles = 30;
                        line = true;
                        lifetime = 160f;
                        length = 240f;
                        baseLength = 8f;
                        interp = Interp.circleOut;
                        sizeInterp = Interp.pow10In;
                        lenFrom = 8f;
                        lenTo = 0f;
                        strokeFrom = 1.5f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.orangeRed;
                    }},

                    new ParticleEffect(){{
                        particles = 24;
                        line = true;
                        lifetime = 160f;
                        length = 240f;
                        baseLength = 8f;
                        interp = Interp.circleOut;
                        sizeInterp = Interp.pow10In;
                        lenFrom = 16f;
                        lenTo = 0f;
                        strokeFrom = 1.5f;
                        strokeTo = 0f;
                        colorFrom = colorTo = LPPal.orangeRed;
                    }},

                    new ParticleEffect(){{
                        particles = 8;
                        region = "lp-star";
                        lifetime = 120f;
                        length = 200f;
                        baseLength = 40f;
                        interp = Interp.circleOut;
                        sizeInterp = Interp.pow4In;
                        sizeFrom = 12;
                        sizeTo = 0;
                        colorFrom = colorTo = LPPal.orangeRed;
                    }}
                );
            }}, 1, 0f, 0f, 240f, 240f));
            weapons.add(new Weapon(){{
                mirror = false;
                x = shootY = 0f;
                reload = 240f;
                shootCone = 361f;
                shootSound = LPSounds.shootBlaster1;
                shootSoundVolume = 1.7f;
                layerOffset = -1;
                shoot = new ShootPattern(){{
                    shots = 2;
                    shotDelay = 30f;
                }};
                bullet = new BasicBulletType(){{
                    sprite = "lp-starenergy-bullet";
                    width = height = 48f;
                    shrinkX = shrinkY = 0.5f;
                    lightRadius = 96f;
                    lightColor = frontColor = backColor = hitColor = LPPal.orangeRed;
                    layer = 99f;
                    maxRange = rangeOverride = 96f;
                    ignoreSpawnAngle = true;
                    collidesAir = collides = keepVelocity = false;
                    lifetime = 40f;
                    speed = damage = 0f;
                    spin = 4f;
                    splashDamage = 120f;
                    splashDamageRadius = 96f;
                    hitShake = despawnShake = 12f;
                    hitSound = despawnSound = LPSounds.ramielblast;
                    hitSoundVolume = 2f;
                    hitEffect = despawnEffect = new MultiEffect(
                        LPFx.cutting(60f, hitColor, hitColor, false, 30f, 45f),
                        LPFx.cutting(60f, hitColor, hitColor, false, 30f, 135f),
                        LPFx.circleOut(60f, hitColor, 96f),
                        LPFx.smoothCircleOut(60f, hitColor, 96f, 20, true)
                    );
                    shootEffect = smokeEffect = Fx.none;
                }};
            }});
        }};

        sustainerSpawn = new SpawnUnitType("sustainer-spawn"){{
            weapons.add(new Weapon(){{
                alwaysShooting = shootOnDeath = true;
                mirror = false;
                controllable = aiControllable = false;
                x = shootY = 0f;
                shootSound = LPSounds.chargeCorvus;
                bullet = new BasicBulletType(){
                    {
                        width = height = shrinkY = 0f;
                        killShooter = ignoreSpawnAngle = true;
                        collides = absorbable = hittable = keepVelocity = false;
                        speed = damage = 0f;
                        lifetime = 95f;
                        hitSound = despawnSound = LPSounds.railGunBlast;
                        hitSoundVolume = 1.5f;
                        hitShake = 16f;
                        shootEffect = smokeEffect = hitEffect = despawnEffect = Fx.none;
                        parts.addAll(
                            new ShapePart(){{
                                hollow = true;
                                color = Color.valueOf("F3E6E600");
                                colorTo = LPPal.eyesLight;
                                sides = 4;
                                radius = 0f;
                                radiusTo = 48f;
                                stroke = 0f;
                                strokeTo = 4f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.circleIn);
                            }},

                            new ShapePart(){{
                                hollow = true;
                                color = LPPal.eyesLight;
                                colorTo = Color.valueOf("F3E6E600");
                                sides = 4;
                                radius = 4f;
                                radiusTo = 8f;
                                stroke = 1f;
                                strokeTo = 8f;
                                rotation = 0f;
                                layer = 110f;
                                progress = PartProgress.life.curve(Interp.pow10In);
                            }},

                            new EffectSpawnerPart(){{
                                effect = new ParticleEffect(){{
                                    line = true;
                                    particles = 1;
                                    length = -90f;
                                    baseLength = 90f;
                                    randLength = false;
                                    interp = Interp.pow3Out;
                                    sizeInterp = Interp.pow2Out;
                                    lifetime = 20f;
                                    lenFrom = 0f;
                                    lenTo = 24f;
                                    strokeFrom = 2f;
                                    strokeTo = 0f;
                                    colorFrom = colorTo = LPPal.eyesLight;
                                    layer = 110f;
                                }};
                                effectChance = 0.8f;
                                progress = PartProgress.life;
                            }},

                            new EffectSpawnerPart(){{
                                effect = new ParticleEffect(){{
                                    line = true;
                                    particles = 1;
                                    length = -120f;
                                    baseLength = 120f;
                                    randLength = false;
                                    interp = Interp.pow3Out;
                                    sizeInterp = Interp.pow2Out;
                                    lifetime = 20f;
                                    lenFrom = 0f;
                                    lenTo = 16f;
                                    strokeFrom = 2f;
                                    strokeTo = 0f;
                                    colorFrom = colorTo = LPPal.eyesLight;
                                    layer = 110f;
                                }};
                                effectChance = 0.4f;
                                progress = PartProgress.life;
                            }}
                        );
                        despawnUnit = sustainer;
                        despawnUnitRadius = 0f;
                        fragBullets = 1;
                        fragVelocityMin = fragVelocityMax = fragOffsetMin = fragOffsetMax = 0;
                        fragLifeMin = fragLifeMax = 1f;
                        fragBullet = new BasicBulletType(){{
                            width = height = shrinkY = 0f;
                            ignoreSpawnAngle = true;
                            collides = absorbable = hittable = keepVelocity = false;
                            speed = damage = 0f;
                            lifetime = 60f;
                            hitSound = despawnSound = Sounds.none;
                            hitEffect = despawnEffect = Fx.none;
                            parts.addAll(
                                new ShapePart(){{
                                    hollow = true;
                                    color = LPPal.eyesLight;
                                    colorTo = Color.valueOf("F3E6E600");
                                    sides = 4;
                                    radius = 48f;
                                    radiusTo = 64f;
                                    stroke = 4f;
                                    strokeTo = 6f;
                                    rotation = 0f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }},

                                new ShapePart(){{
                                    hollow = true;
                                    color = colorTo = LPPal.eyesLight;
                                    sides = 4;
                                    radius = 0f;
                                    radiusTo = 64f;
                                    stroke = 6f;
                                    strokeTo = 0f;
                                    rotation = 0f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }},

                                new ShapePart(){{
                                    hollow = true;
                                    color = colorTo = LPPal.eyesLight;
                                    sides = 4;
                                    radius = 0f;
                                    radiusTo = 70f;
                                    stroke = 2f;
                                    strokeTo = 0f;
                                    rotation = 0f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }},

                                new HaloPart(){{
                                    tri = true;
                                    color = colorTo = LPPal.eyesLight;
                                    shapes = 4;
                                    radius = 16f;
                                    radiusTo = 0f;
                                    triLength = 30f;
                                    triLengthTo = 60f;
                                    haloRadius = 0f;
                                    haloRadiusTo = 64f;
                                    haloRotation = 45f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }},

                                new HaloPart(){{
                                    tri = true;
                                    color = colorTo = LPPal.eyesLight;
                                    shapes = 4;
                                    radius = 16f;
                                    radiusTo = 0f;
                                    triLength = 8f;
                                    triLengthTo = 16f;
                                    shapeRotation = 180f;
                                    haloRadius = 0f;
                                    haloRadiusTo = 64f;
                                    haloRotation = 45f;
                                    layer = 110f;
                                    progress = PartProgress.life.curve(Interp.circleOut);
                                }}
                            );
                        }};
                    }
                    @Override
                    public void update(Bullet b) {
                        super.update(b);
                    
                        if (b.timer(1, 2)) {
                            for (int l = 0; l < 3; l++) {
                                LP.graphics.Drawn.randFadeLightningEffect(b.x, b.y, Mathf.random(120f), Mathf.random(1f, 5f), LPPal.orange, Mathf.chance(0.5));
                            }
                        }
                    }
                };
            }});
        }};
    }
}
