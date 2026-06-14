package LP.content;

import arc.graphics.Color;
import arc.math.*;
import mindustry.gen.UnitEntity;
import mindustry.type.Weapon;
import mindustry.gen.Bullet;
import mindustry.gen.ElevationMoveUnit;
import mindustry.gen.Sounds;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.effect.*;

import LP.entities.units.SpawnUnitType;
import LP.entities.units.LPUnitType;
import LP.graphics.LPPal;

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
            weapons.add(new Weapon("riptide-b"){{
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
            constructor = UnitEntity::create;
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
    }
}
