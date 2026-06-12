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

public class Mx1TechTree {
    public static void load(){
        LPPlanets.mx1.techTree = nodeRoot("MXrd-01", pioneers, () -> {
            /** 物品 */
            nodeProduce(jynsteel, Seq.with(new OnSector(不容拒绝)), () -> {
                nodeProduce(erocrys, () -> {
                    nodeProduce(crystalite, () -> {});
                });

                nodeProduce(ionopolymer, () -> {});

                nodeProduce(massisteel, () -> {
                    nodeProduce(litelnlay, () -> {
                        nodeProduce(transchimericsteel, () -> {});
                    });
                });
            });

            /** 流体 */
            nodeProduce(slag, Seq.with(new OnSector(不容拒绝)), () -> {});

            /** 区块 */
            node(不容拒绝, () -> {

            });

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
                    new Research(jynDuctbridge),
                    new Research(jynUnloader),
                    new Research(jynSorter),
                    new Research(jynInvertedSorter),
                    new Research(jynOverflow),
                    new Research(jynUnderflow),
                    new Research(process4),
                    new OnSector(Rift)), () -> {
                    node(litJunction, () -> {});
                });
            });

            /** 功能 */
            node(jynVault, Seq.with(new SectorComplete(不容拒绝)), () -> {});
        });
    }
}
