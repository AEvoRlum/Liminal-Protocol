package LP.content;

import arc.struct.Seq;
import mindustry.game.Objectives.*;

import static mindustry.content.TechTree.nodeRoot;
import static mindustry.content.TechTree.nodeProduce;
import static mindustry.content.TechTree.node;

import static mindustry.content.Liquids.slag;

import static LP.content.LPBlocks.*;
import static LP.content.Mx1Sectors.*;
import static LP.content.LPItems.*;
import static LP.content.LPLiquids.*;
import static LP.content.LPUnits.*;
import static LP.content.LPEnemyUnits.*;

public class Mx1TechTree {
    public static void load(){
        LPPlanets.mx1.techTree = nodeRoot("MXrd-01", pioneers, () -> {
            /** 物流 */
            node(jynDuct, Seq.with(new OnSector(不容拒绝)), () -> {
                node(jynDuctbridge, () -> {
                    node(litBridgeConveyor, Seq.with(new Research(litConveyor)), () -> {});
                });

                node(jynUnloader, () -> {
                    node(litUnloader, Seq.with(new Research(litConveyor)), () -> {});
                });

                node(jynSorter, () -> {
                    node(jynInvertedSorter, () -> {});
                });

                node(jynOverflow, () -> {
                    node(jynUnderflow, () -> {});
                });

                node(litConveyor, Seq.with(
                    new Research(process4),
                    new OnSector(Rift)), () -> {
                    node(litJunction, () -> {});
                });
            });

            
            /** 功能 */
            node(jynVault, Seq.with(new Research(process1)), () -> {
                node(jynVaultLarge, () -> {});
            });

            /** 流体 */
            node(masConduit, Seq.with(new OnSector(why)), () -> {
                node(traConduit, Seq.with(
                    new Research(litConveyor),
                    new Research(process4),
                    new OnSector(Rift)), () -> {});
                node(massisteelLiquidStorage, () -> {
                    node(massisteelLiquidStorageLarge, () -> {});
                });
                node(masPump, () -> {
                    node(masPumpHighSpeed, Seq.with(new SectorComplete(instead)), () -> {});
                });
                node(masLiquidBridgeConduit, () -> {
                    node(traBridgeConduit, Seq.with(new Research(traConduit)), () -> {});
                });
                node(masLiquidJunction, () -> {});
                node(masLiquidRouter, () -> {});
            });

            /** 钻头 */
            node(jynDrill, Seq.with(new OnSector(不容拒绝)), () -> {
                node(shearDrill, () -> {
                    node(impactDrill0, Seq.with(
                    new Research(litConveyor)), () -> {});
                });
            });

            /** 工厂 */
            node(ionopolymerCrucible, Seq.with(new OnSector(why)), () -> {
                node(ionopolymerCrucibleLarge, Seq.with(new Research(impactDrill0)), () -> {});

                node(erocrysExtractory, Seq.with(new SectorComplete(repulse)), () -> {});

                node(transChimericFoundry, Seq.with(new Research(litConveyor),
                new Research(litBridgeConveyor),
                new Research(litJunction),
                new Research(litUnloader)), () -> {
                    node(highSpeedTranschimericFoundry, Seq.with(new Research(ionopolymerCrucibleLarge)), () -> {});
                });

                node(integratedAlloyCmeltingCuringRefinery, Seq.with(new SectorComplete(Rift),
                new Research(chipFabricator),
                new Research(bipolarchip),
                new Research(converchip),
                new Research(stockchip)), () -> {});

                node(chipFabricator, Seq.with(new SectorComplete(Rift),
                new Research(highSpeedTranschimericFoundry)), () -> {});

                node(moduleFabricator, Seq.with(new Research(chipFabricator),
                new Research(photosolidAlloy),
                new Research(bipolarchip),
                new Research(converchip)), () -> {});

                node(heterohydrogenCollector, Seq.with(new Research(integratedAlloyCmeltingCuringRefinery)), () -> {
                    node(heterohydrogenLiquefier, () -> {});
                });
            });

            /** 热量 */
            node(masHeatRedirector, Seq.with(new OnSector(why)), () -> {
                node(masHeatRedirectorSmall, () -> {});

                node(masHeatRouter, () -> {
                    node(masHeatRouterSmall, () -> {});
                });

                node(masSlagHeater, Seq.with(new Research(masHeatRedirectorSmall),
                new Research(masHeatRouter),
                new Research(masHeatRouterSmall)), () -> {
                    node(powerHeater, Seq.with(new OnSector(Rift)), () -> {});
                });
            });

            /** 电力 */
            node(jynPowerNode, Seq.with(new SectorComplete(不容拒绝)), () -> {
                node(jynPowerNodeLarge, () -> {
                    node(traPowerNode, () -> {
                        node(traPowerTower, () -> {});
                    });
                });

                node(jynBattery, () -> {
                    node(traBattery, () -> {});
                });

                node(heavyIonChamber, () -> {
                    node(annihilationReactor, Seq.with(new Research(heterohydrogenLiquefier),
                    new Research(autoBuildTower),
                    new Research(traPowerNode),
                    new Research(traPowerTower),
                    new Research(traBattery)), () -> {});
                });
            });

            /** 防御/墙 */
            node(jynWall, Seq.with(new OnSector(不容拒绝)), () -> {
                node(jynWallLarge, () -> {});

                node(masWall, () -> {
                    node(masWallLarge, () -> {});
                });

                node(traWall, () -> {
                    node(traWallLarge, () -> {});
                });

                node(autoBuildTower, Seq.with(new Research(heterohydrogen)), () -> {});
            });

            /** 炮塔 */
            node(lucenser, Seq.with(new OnSector(不容拒绝)), () -> {
                node(disflux, () -> {
                    node(radiance, Seq.with(new Research(process2),
                    new SectorComplete(why),
                    new OnSector(repulse)), () -> {
                        node(cloudpiercer, Seq.with(new Research(meteor),
                        new SectorComplete(repulse)), () -> {
                            node(crimsondwarf, Seq.with(new Research(hushstrike),
                            new Research(highSpeedTranschimericFoundry)), () -> {
                                node(eclipsion, Seq.with(new Research(annihilationReactor),
                                new Research(chargeModule),
                                new Research(energyStorageModule),
                                new Research(powerSupplyModule)), () -> {});
                            });
                        });
                        node(hushstrike, Seq.with(new Research(repelback)), () -> {});
                    });
                    node(infernoblade, Seq.with(new Research(crimsondwarf)), () -> {
                        node(threshold, Seq.with(new Research(defence)),() -> {});
                    });
                });
                node(impactor, Seq.with(new Research(process1),
                new OnSector(why),
                new Research(heavyIonChamber)), () -> {
                    node(repulstar, () -> {
                        node(repelback, Seq.with(new Research(fallenstar),
                        new Research(ionopolymerCrucibleLarge)), () -> {});
                    });
                });
                node(meteor, Seq.with(new Research(repulstar),
                new Research(process2),
                new OnSector(repulse)), () -> {
                    node(fallenstar, Seq.with(new Research(process4),
                    new OnSector(Rift),
                    new Research(transChimericFoundry),
                    new Research(traWallLarge)), () -> {
                        node(defence, Seq.with(new Research(eclipsion)), () -> {});
                    });
                });
                node(recursion, Seq.with(new Research(infernoblade)), () -> {});
            });

            /** 单位 */
            node(pioneersUnit, () -> {});

            /** 区块/进程 */
            node(不容拒绝, () -> {
                node(process1, Seq.with(new Research(jynsteel),
                new Research(erocrys),
                new SectorComplete(不容拒绝),
                new Research(jynDuctbridge),
                new Research(jynUnloader),
                new Research(jynSorter),
                new Research(jynInvertedSorter),
                new Research(jynOverflow),
                new Research(jynUnderflow),
                new Research(lucenser),
                new Research(disflux),
                new Research(jynDrill),
                new Research(shearDrill),
                new Research(jynPowerNode),
                new Research(jynPowerNodeLarge)),  () -> {
                    node(process2, Seq.with(new Research(massisteel),
                        new SectorComplete(why),
                        new Research(masConduit),
                        new Research(masLiquidBridgeConduit),
                        new Research(masLiquidJunction),
                        new Research(masLiquidRouter),
                        new Research(masPump),
                        new Research(massisteelLiquidStorage),
                        new Research(massisteelLiquidStorageLarge),
                        new Research(masWall),
                        new Research(masWallLarge),
                        new Research(masHeatRedirector),
                        new Research(masHeatRedirectorSmall),
                        new Research(masHeatRouter),
                        new Research(masHeatRouterSmall),
                        new Research(masSlagHeater),
                        new Research(ionopolymerCrucible),
                        new Research(heavyIonChamber),
                        new Research(impactor),
                        new Research(repulstar),
                        new Research(jynVault)), () -> {
                            node(process3, Seq.with(new Research(crystalite),
                            new Research(ionopolymer),
                            new SectorComplete(repulse),
                            new Research(radiance),
                            new Research(meteor),
                            new Research(cloudpiercer),
                            new Research(erocrysExtractory),
                            new Research(jynBattery)), () -> {
                                node(process4, Seq.with(new SectorComplete(instead)), () -> {
                                    node(process41, Seq.with(new SectorComplete(Rift),
                                    new Research(bipolarchip),
                                    new Research(converchip),
                                    new Research(stockchip),
                                    new Research(buildchip),
                                    new Research(energyStorageModule),
                                    new Research(powerSupplyModule),
                                    new Research(chargeModule),
                                    new Research(photosolidAlloy),
                                    new Research(heterohydrogen),
                                    new Research(eclipsion),
                                    new Research(defence),
                                    new Research(threshold)
                                    ), () -> {});
                                });
                            });
                        });
                    });
                node(why, Seq.with(new Research(process1)), () -> {
                    node(repulse, Seq.with(new Research(process2)), () -> {
                        node(instead, Seq.with(new Research(process3)), () -> {
                            node(Rift, Seq.with(new Research(process4)), () -> {
                                node(Outcry, Seq.with(new Research(process41)), () -> {});
                            });
                        });
                    });
                });
            });


            /** 物品 */
            nodeProduce(jynsteel, Seq.with(new OnSector(不容拒绝)), () -> {
                nodeProduce(erocrys, () -> {
                    nodeProduce(crystalite, () -> {});
                });

                nodeProduce(massisteel, () -> {
                    nodeProduce(litelnlay, () -> {
                        nodeProduce(transchimericsteel, () -> {});
                    });
                });
                nodeProduce(photosolidAlloy, () -> {});

                nodeProduce(ionopolymer, () -> {});
                nodeProduce(heterosoligen, () -> {});

                nodeProduce(bipolarchip, () -> {});
                nodeProduce(converchip, () -> {});
                nodeProduce(stockchip, () -> {});
                nodeProduce(buildchip, () -> {});

                nodeProduce(energyStorageModule, () -> {});
                nodeProduce(powerSupplyModule, () -> {});
                nodeProduce(chargeModule, () -> {});
            });

            /** 流体 */
            nodeProduce(slag, Seq.with(new OnSector(不容拒绝)), () -> {
                nodeProduce(heterohydrogen, () -> {});
            });

            /** 敌方 */
            node(ttfWall, Seq.with(new SectorComplete(不容拒绝)), () -> {
                node(riptide, Seq.with(new SectorComplete(不容拒绝)), () -> {
                    node(stormpole, Seq.with(new SectorComplete(Rift)), () -> {});
                });
                node(rupture, Seq.with(new SectorComplete(Rift)), () -> {
                    node(starmeter, Seq.with(new SectorComplete(Rift)), () -> {});
                });
                node(crystalburst, Seq.with(new SectorComplete(repulse)), () -> {
                    node(ravager, Seq.with(new SectorComplete(instead)), () -> {
                        node(annihicore, Seq.with(new SectorComplete(Rift)), () -> {});
                    });
                });
                node(eradicator, Seq.with(new SectorComplete(Rift)), () -> {});
                node(rusher, Seq.with(new SectorComplete(why)), () -> {
                    node(vectruptor, Seq.with(new SectorComplete(Rift)), () -> {
                        node(sustainer, Seq.with(new SectorComplete(Rift)), () -> {});
                    });
                });
            });
        });
    }
}
