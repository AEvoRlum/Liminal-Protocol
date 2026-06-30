package LP.entities.blocks.power;

import arc.util.*;
import arc.struct.*;
import arc.Core;
import arc.func.*;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.graphics.g2d.Draw;
import mindustry.core.UI;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.Bar;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.modules.*;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.*;
import LP.graphics.LPPal;
import LP.content.LPStats;

public class SquareRangePowerNode extends PowerBlock {
    /** 连接范围（格) */
    public float range = 30f;     
    /** 还制连接范围的颜色 */
    public Color drawRangeColor = LPPal.powerBar;
    /** 最大连接数 */
    public int maxConnections = 24;

    @SuppressWarnings({"unchecked"})
    public SquareRangePowerNode(String name) {
        super(name);
        configurable = true;
        ignoreResizeConfig = true;
        consumesPower = false;
        outputsPower = false;
        canOverdrive = false;
        swapDiagonalPlacement = true;
        schematicPriority = -10;
        drawDisabled = false;
        envEnabled |= Env.space;
        destructible = true;
        delayLandingConfig = true;

        priority = -1f;
        alwaysUnlocked = false;
        researchCostMultiplier = 0.4f;
        this.conductivePower = true;

        /** 注册配置处理器 */
        config(Integer.class, (entity, value) -> {
            PowerModule power = entity.power;
            Building other = world.build(value);
            boolean contains = power.links.contains(value);
            boolean valid = other != null && other.power != null;

            if (contains) {
                /** 断开连接 */
                power.links.removeValue(value);
                if (valid) other.power.links.removeValue(entity.pos());

                PowerGraph newGraph = new PowerGraph();
                newGraph.reflow(entity);
                newGraph.update();

                if (valid && other.power.graph != newGraph) {
                    PowerGraph og = new PowerGraph();
                    og.reflow(other);
                    og.update();
                }
            } else if (linkValid(entity, other) && valid && power.links.size < maxConnections) {
                /** 建立连接 */
                power.links.addUnique(other.pos());
                if (other.team == entity.team) {
                    other.power.links.addUnique(entity.pos());
                }
                power.graph.addGraph(other.power.graph);
            }
        });

        config(Point2[].class, (tile, value) -> {
            IntSeq old = new IntSeq(tile.power.links);
            /** 先断开所有旧连接 */
            for (int i = 0; i < old.size; i++) {
                configurations.get(Integer.class).get(tile, old.get(i));
            }
            /** 再建立新连接 */
            for (Point2 p : value) {
                configurations.get(Integer.class).get(tile, Point2.pack(p.x + tile.tileX(), p.y + tile.tileY()));
            }
        });
    }
    
    @Override
    public void setBars(){
        super.setBars();
        addBar("power", makePowerBalance());
        addBar("batteries", makeBatteryBalance());

        addBar("connections", entity -> new Bar(() ->
        Core.bundle.format("bar.powerlines", entity.power.links.size, maxConnections),
            () -> Pal.items,
            () -> (float)entity.power.links.size / (float)maxConnections
        ));
    }

    public static Func<Building, Bar> makePowerBalance(){
        return entity -> new Bar(() ->
        Core.bundle.format("bar.powerbalance",
            ((entity.power.graph.getPowerBalance() >= 0 ? "+" : "") + UI.formatAmount((long)(entity.power.graph.getPowerBalance() * 60)))),
            () -> Pal.powerBar,
            () -> Mathf.clamp(entity.power.graph.getLastPowerProduced() / entity.power.graph.getLastPowerNeeded())
        );
    }

    public static Func<Building, Bar> makeBatteryBalance(){
        return entity -> new Bar(() ->
        Core.bundle.format("bar.powerstored",
            (UI.formatAmount((long)entity.power.graph.getLastPowerStored())), UI.formatAmount((long)entity.power.graph.getLastCapacity())),
            () -> Pal.powerBar,
            () -> Mathf.clamp(entity.power.graph.getLastPowerStored() / entity.power.graph.getLastCapacity())
        );
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.powerConnections, maxConnections, StatUnit.none);
        stats.add(LPStats.powerNodeSquareRange, range, StatUnit.blocks);
    }

    @Override
    public boolean outputsItems() { 
        return false;
    }

    /** 建造时绘制连接范围 */
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        Tile tile = world.tile(x, y);

        /** 部分配置界面复现 */
        getPotentialLinks(tile, player.team(), link -> {
            float s = link.block.size * tilesize * 1.1f + Mathf.absin(Time.time, 5f, 2);
            Drawf.dashSquare(Pal.accent, link.x, link.y, s);
        });

        float pixelRange = range * tilesize * 2;
        Drawf.dashSquare(drawRangeColor, x * tilesize + offset, y * tilesize + offset, pixelRange);
        Drawf.dashSquare(drawRangeColor, x * tilesize + offset, y * tilesize + offset, size * tilesize * 1.25f);
    }

    /** 区域检测 */
    public boolean overlaps(float srcx, float srcy, Tile other, float range) {
        if (other == null) return false;
        return Intersector.overlaps(
            Tmp.r1.setCentered(srcx, srcy, range * 2, range * 2),
            other.getHitbox(Tmp.r2)
        );
    }

    /** 判断两个建筑是否可连接 */
    public boolean linkValid(Building tile, Building link) {
        if (tile == link || link == null || !link.block.hasPower || !link.block.connectedPower || tile.team != link.team)
            return false;

        /** 检查双向范围 */
        boolean rangeCheck = overlaps(tile.x, tile.y, link.tile, range * tilesize) ||
        (link.block instanceof PowerNode && overlaps(link.x, link.y, tile.tile, ((PowerNode) link.block).laserRange * tilesize));

        if (!rangeCheck) return false;

        /** 检查链接上限 */
        if (link.block instanceof PowerNode) {
            PowerNode node = (PowerNode) link.block;
            return link.power.links.size < node.maxNodes || link.power.links.contains(tile.pos());
        } else if (link.block instanceof SquareRangePowerNode) {
            SquareRangePowerNode node = (SquareRangePowerNode) link.block;
            return link.power.links.size < node.maxConnections || link.power.links.contains(tile.pos());
        }
        return true;
    }

    /** 自动连接逻辑 */
    public void autoLink(Building entity) {
        if (net.client() || entity.power.links.size > 0) return;

        final Seq<Building> candidates = new Seq<>();
        final ObjectSet<PowerGraph> graphs = new ObjectSet<>();

        /** 候选筛选 */
        Boolf<Building> valid = other -> {
            if (other == null || other.tile == entity.tile) return false;
            if (!other.block.connectedPower || other.power == null) return false;
            if (!(other.block.outputsPower || other.block.consumesPower || other.block instanceof PowerNode)) return false;

            boolean rangeCheck = overlaps(entity.x, entity.y, other.tile, range * tilesize) ||
                (other.block instanceof PowerNode && overlaps(other.x, other.y, entity.tile, ((PowerNode) other.block).laserRange * tilesize));
            if (!rangeCheck) return false;

            if (other.team != entity.team) return false;
            // 只有非输出者才检查graphs，输出者允许通过
            if (!other.block.outputsPower && graphs.contains(other.power.graph)) return false;
            if (PowerNode.insulated(entity.tile, other.tile)) return false;

            if (other.block instanceof SquareRangePowerNode) {
                SquareRangePowerNode node = (SquareRangePowerNode) other.block;
                if (other.power.links.size >= node.maxConnections) return false;
            }
            if (other.block instanceof PowerNode) {
                PowerNode node = (PowerNode) other.block;
                if (other.power.links.size >= node.maxNodes) return false;
            }

            if (Structs.contains(Edges.getEdges(size), p -> {
                Tile t = world.tile(entity.tileX() + p.x, entity.tileY() + p.y);
                return t != null && t.build == other;
            })) return false;

            return true;
        };

        // 收集已联网的图
        for (var p : Edges.getEdges(size)) {
            Tile other = entity.tile.nearby(p);
            if (other != null && other.team() == entity.team && other.build != null && other.build.power != null) {
                graphs.add(other.build.power.graph);
            }
        }
        if (entity.power != null) graphs.add(entity.power.graph);

        float worldRange = range * tilesize;
        var tree = entity.team.data().buildingTree;
        if (tree != null) {
            tree.intersect(entity.x - worldRange, entity.y - worldRange, worldRange * 2, worldRange * 2, build -> {
                if (valid.get(build) && !candidates.contains(build)) {
                    candidates.add(build);
                }
            });
        }

        if (candidates.isEmpty()) return;

        candidates.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));

        // 分类
        Seq<Building> needConsumers = new Seq<>();
        Seq<Building> unlinkedBuffered = new Seq<>();
        Seq<Building> powerProducers = new Seq<>();
        Seq<Building> linkedBuffered = new Seq<>();
        Seq<Building> existingConsumers = new Seq<>();
        Seq<Building> others = new Seq<>();

        for (Building b : candidates) {
            if (b.block.consumesPower && !b.block.outputsPower) {
                if (b.power.links.isEmpty()) needConsumers.add(b);
                else existingConsumers.add(b);
            } else if (!b.block.consumesPower && !b.block.outputsPower && b.block.hasPower) {
                if (b.power.links.isEmpty()) unlinkedBuffered.add(b);
                else linkedBuffered.add(b);
            } else if (b.block.outputsPower) {
                powerProducers.add(b);
            } else {
                others.add(b);
            }
        }

        // 各类按距离排序
        needConsumers.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));
        unlinkedBuffered.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));
        powerProducers.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));
        linkedBuffered.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));
        existingConsumers.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));
        others.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));

        int usedLinks = 0;

        // 强制优先：只要存在输出者，就优先连接一个输出者
        if (!powerProducers.isEmpty()) {
            Building target = powerProducers.first(); // 最近的一个输出者
            if (!entity.power.links.contains(target.pos())) {
                entity.configure(target.pos());
                graphs.add(target.power.graph);
                usedLinks++;
            }
        }

        // 然后处理其他连接
        boolean hasNeed = !needConsumers.isEmpty() || !unlinkedBuffered.isEmpty();

        // 辅助分组函数
        java.util.function.Function<Seq<Building>, Seq<Building>> groupByGraph = (list) -> {
            ObjectMap<PowerGraph, Building> nearest = new ObjectMap<>();
            for (Building b : list) {
                PowerGraph graph = b.power.graph;
                Building existing = nearest.get(graph);
                if (existing == null || b.dst2(entity) < existing.dst2(entity)) {
                    nearest.put(graph, b);
                }
            }
            Seq<Building> result = new Seq<>();
            for (Building b : nearest.values()) {
                result.add(b);
            }
            result.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));
            return result;
        };

        if (hasNeed) {
            // 连接未联网消耗者
            Seq<Building> groupedNeed = groupByGraph.apply(needConsumers);
            for (Building target : groupedNeed) {
                if (usedLinks >= maxConnections) break;
                if (!entity.power.links.contains(target.pos())) {
                    entity.configure(target.pos());
                    graphs.add(target.power.graph);
                    usedLinks++;
                }
            }

            // 连接未联网存储
            if (usedLinks < maxConnections) {
                Seq<Building> groupedBuffered = groupByGraph.apply(unlinkedBuffered);
                for (Building target : groupedBuffered) {
                    if (usedLinks >= maxConnections) break;
                    if (!entity.power.links.contains(target.pos())) {
                        entity.configure(target.pos());
                        graphs.add(target.power.graph);
                        usedLinks++;
                    }
                }
            }

            // 如果还有剩余连接数，且之前没有连接任何电源，可以连接已联网存储或消耗者
            if (usedLinks < maxConnections) {
                Seq<Building> extra = new Seq<>();
                extra.addAll(linkedBuffered);
                extra.addAll(existingConsumers);
                extra.addAll(others);
                extra.sort((a, b) -> Float.compare(a.dst2(entity), b.dst2(entity)));
                for (Building target : extra) {
                    if (usedLinks >= maxConnections) break;
                    if (!entity.power.links.contains(target.pos())) {
                        entity.configure(target.pos());
                        graphs.add(target.power.graph);
                        usedLinks++;
                    }
                }
            }
        } else {
            // 没有未联网消耗者和存储，连接已联网存储、已联网消耗者、其他
            for (Building target : linkedBuffered) {
                if (usedLinks >= maxConnections) break;
                if (!entity.power.links.contains(target.pos())) {
                    entity.configure(target.pos());
                    graphs.add(target.power.graph);
                    usedLinks++;
                }
            }
            for (Building target : existingConsumers) {
                if (usedLinks >= maxConnections) break;
                if (!entity.power.links.contains(target.pos())) {
                    entity.configure(target.pos());
                    graphs.add(target.power.graph);
                    usedLinks++;
                }
            }
            for (Building target : others) {
                if (usedLinks >= maxConnections) break;
                if (!entity.power.links.contains(target.pos())) {
                    entity.configure(target.pos());
                    graphs.add(target.power.graph);
                    usedLinks++;
                }
            }
        }

        // 如果仍然没有连接任何建筑，尝试连接一个其他节点
        if (usedLinks == 0 && !others.isEmpty()) {
            Building target = others.first();
            if (!entity.power.links.contains(target.pos())) {
                entity.configure(target.pos());
            }
        }
    }

    /** 获取潜在连接 */
    protected void getPotentialLinks(Tile tile, Team team, Cons<Building> links) {
        final ObjectSet<PowerGraph> graphs = new ObjectSet<>();
        final Seq<Building> tempBuilds = new Seq<>();

        /** 复现autoLink的筛选条件 */
        Boolf<Building> valid = other -> {
            if (other == null || other.tile == tile) return false;
            if (!other.block.connectedPower || other.power == null) return false;
            if (!(other.block.outputsPower || other.block.consumesPower || other.block instanceof PowerNode)) return false;

            float pixelRange = range * tilesize;
            boolean rangeCheck = overlaps(tile.worldx(), tile.worldy(), other.tile, pixelRange) ||
                (other.block instanceof PowerNode && overlaps(other.x, other.y, tile, ((PowerNode) other.block).laserRange * tilesize));
            if (!rangeCheck) return false;

            if (other.team != team) return false;
            // 只有非输出者才检查graphs，输出者允许通过
            if (!other.block.outputsPower && graphs.contains(other.power.graph)) return false;
            if (PowerNode.insulated(tile, other.tile)) return false;

            if (other.block instanceof SquareRangePowerNode) {
                SquareRangePowerNode node = (SquareRangePowerNode) other.block;
                if (other.power.links.size >= node.maxConnections) return false;
            }
            if (other.block instanceof PowerNode) {
                PowerNode node = (PowerNode) other.block;
                if (other.power.links.size >= node.maxNodes) return false;
            }

            if (Structs.contains(Edges.getEdges(size), p -> {
                Tile t = world.tile(tile.x + p.x, tile.y + p.y);
                return t != null && t.build == other;
            })) return false;

            return true;
        };

        /** 收集相邻已连接的图 */
        for (var p : Edges.getEdges(size)) {
            Tile other = tile.nearby(p);
            if (other != null && other.team() == team && other.build != null && other.build.power != null) {
                graphs.add(other.build.power.graph);
            }
        }
        if (tile.build != null && tile.build.power != null) {
            graphs.add(tile.build.power.graph);
        }

        float worldRange = range * tilesize;
        var tree = team.data().buildingTree;
        if (tree != null) {
            tree.intersect(tile.worldx() - worldRange, tile.worldy() - worldRange, worldRange * 2, worldRange * 2, build -> {
                if (valid.get(build) && !tempBuilds.contains(build)) {
                    tempBuilds.add(build);
                }
            });
        }

        if (tempBuilds.isEmpty()) return;

        tempBuilds.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));

        /** 分类 */
        Seq<Building> needConsumers = new Seq<>();
        Seq<Building> unlinkedBuffered = new Seq<>();
        Seq<Building> powerProducers = new Seq<>();
        Seq<Building> linkedBuffered = new Seq<>();
        Seq<Building> existingConsumers = new Seq<>();
        Seq<Building> others = new Seq<>();

        for (Building b : tempBuilds) {
            if (b.block.consumesPower && !b.block.outputsPower) {
                if (b.power.links.isEmpty()) needConsumers.add(b);
                else existingConsumers.add(b);
            } else if (!b.block.consumesPower && !b.block.outputsPower && b.block.hasPower) {
                if (b.power.links.isEmpty()) unlinkedBuffered.add(b);
                else linkedBuffered.add(b);
            } else if (b.block.outputsPower) {
                powerProducers.add(b);
            } else {
                others.add(b);
            }
        }

        /** 各类按距离排序 */
        needConsumers.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));
        unlinkedBuffered.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));
        powerProducers.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));
        linkedBuffered.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));
        existingConsumers.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));
        others.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));

        /** 构建最终候选 */
        Seq<Building> finalCandidates = new Seq<>();
        int usedLinks = 0;
        int maxConn = maxConnections;

        /** ---- 1. 强制优先：只要存在输出者，就添加一个输出者 ---- */
        if (!powerProducers.isEmpty()) {
            Building target = powerProducers.first(); // 最近的一个输出者
            if (!finalCandidates.contains(target)) {
                finalCandidates.add(target);
                usedLinks++;
            }
        }

        /** ---- 2. 处理其他连接 ---- */
        boolean hasNeed = !needConsumers.isEmpty() || !unlinkedBuffered.isEmpty();

        /** 辅助分组函数 */
        java.util.function.Function<Seq<Building>, Seq<Building>> groupByGraph = (list) -> {
            ObjectMap<PowerGraph, Building> nearest = new ObjectMap<>();
            for (Building b : list) {
                PowerGraph graph = b.power.graph;
                Building existing = nearest.get(graph);
                if (existing == null || b.dst2(tile) < existing.dst2(tile)) {
                    nearest.put(graph, b);
                }
            }
            Seq<Building> result = new Seq<>();
            for (Building b : nearest.values()) {
                result.add(b);
            }
            result.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));
            return result;
        };

        if (hasNeed) {
            /** 2.1 添加未联网消耗者 */
            Seq<Building> groupedNeed = groupByGraph.apply(needConsumers);
            for (Building target : groupedNeed) {
                if (usedLinks >= maxConn) break;
                if (!finalCandidates.contains(target)) {
                    finalCandidates.add(target);
                    usedLinks++;
                }
            }

            /** 2.2 添加未联网存储 */
            if (usedLinks < maxConn) {
                Seq<Building> groupedBuffered = groupByGraph.apply(unlinkedBuffered);
                for (Building target : groupedBuffered) {
                    if (usedLinks >= maxConn) break;
                    if (!finalCandidates.contains(target)) {
                        finalCandidates.add(target);
                        usedLinks++;
                    }
                }
            }

            /** 2.3 如果还有剩余连接数，添加其他节点 */
            if (usedLinks < maxConn) {
                Seq<Building> extra = new Seq<>();
                extra.addAll(linkedBuffered);
                extra.addAll(existingConsumers);
                extra.addAll(others);
                extra.sort((a, b) -> Float.compare(a.dst2(tile), b.dst2(tile)));
                for (Building target : extra) {
                    if (usedLinks >= maxConn) break;
                    if (!finalCandidates.contains(target)) {
                        finalCandidates.add(target);
                        usedLinks++;
                    }
                }
            }
        } else {
            /** 2.4 没有未联网消耗者和存储：添加已联网存储、已联网消耗者、其他 */
            for (Building target : linkedBuffered) {
                if (usedLinks >= maxConn) break;
                if (!finalCandidates.contains(target)) {
                    finalCandidates.add(target);
                    usedLinks++;
                }
            }
            for (Building target : existingConsumers) {
                if (usedLinks >= maxConn) break;
                if (!finalCandidates.contains(target)) {
                    finalCandidates.add(target);
                    usedLinks++;
                }
            }
            for (Building target : others) {
                if (usedLinks >= maxConn) break;
                if (!finalCandidates.contains(target)) {
                    finalCandidates.add(target);
                    usedLinks++;
                }
            }
        }

        // 回调所有最终候选
        for (Building link : finalCandidates) {
            if (link != null) links.get(link);
        }
    }

    public class SquareRangePowerNodeBuild extends Building {
        @Override
        public void placed() {
            super.placed();
            /** 放置时自动连接 */
            autoLink(this);
        }

        @Override
        public void dropped() {
            power.links.clear();
            updatePowerGraph();
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            /** 点击其他建筑：尝试连接/断开 */
            if (linkValid(this, other)) {
                configure(other.pos());
                return false;
            }

            /** 双击自身：清空连接或自动连接 */
            if (this == other) {
                if (power.links.size == 0) {

                    /** 没有连接时，尝试自动连接 */
                    Seq<Point2> points = new Seq<>();
                    final ObjectSet<PowerGraph> graphs = new ObjectSet<>();
                    Boolf<Building> valid = b -> linkValid(this, b) && !graphs.contains(b.power.graph);

                    /** 快速收集范围内的建筑 */
                    float worldRange = range * tilesize;
                    team.data().buildingTree.intersect(x - worldRange, y - worldRange, worldRange * 2, worldRange * 2, build -> {
                        if (valid.get(build) && points.size < maxConnections) {
                            points.add(new Point2(build.tileX() - tile.x, build.tileY() - tile.y));
                            graphs.add(build.power.graph);
                        }
                    });
                    configure(points.toArray(Point2.class));
                } else {
                    /** 已有连接，清空连接 */
                    configure(new Point2[0]);
                }
                deselect();
                return false;
            }
            return true;
        }

        @Override
        public void drawConfigure() {

            /** 遍历所有连接，为每个连接的建筑绘制方形边框 */
            for (int i = 0; i < power.links.size; i++) {
                Building link = world.build(power.links.get(i));
                if (link == null || !linkValid(this, link)) continue;

                /** 绘制边框 */
                float s = link.block.size * tilesize * 1.1f + Mathf.absin(Time.time, 5f, 2);
                Drawf.dashSquare(Pal.accent, link.x, link.y, s);
            }

            if (range <= 0f) return;

            /** 四角坐标计算 */
            float half = range * tilesize;
            float thisSize = tile.block().size * tilesize * 1.25f / 2f;

            float[] cornersX = {x - half, x + half, x + half, x - half};
            float[] cornersY = {y - half, y - half, y + half, y + half};
            float[] cornersSizeX = {x - thisSize, x + thisSize, x + thisSize, x - thisSize};
            float[] cornersSizeY = {y - thisSize, y - thisSize, y + thisSize, y + thisSize};

            /** 中心向四角虚线绘制 */
            for (int i = 0; i < 4; i++) {
                Drawf.dashLine(drawRangeColor, cornersSizeX[i], cornersSizeY[i], cornersX[i], cornersY[i]);
            }

            /** 范围边框及本体边框绘制 */
            Drawf.dashSquare(drawRangeColor, x, y, range * tilesize * 2);
            Drawf.dashSquare(drawRangeColor, x, y, tile.block().size * tilesize * 1.25f);

            Draw.reset();
        }

        @Override
        public Point2[] config() {
            Point2[] out = new Point2[power.links.size];
            for (int i = 0; i < out.length; i++) {
                out[i] = Point2.unpack(power.links.get(i)).sub(tile.x, tile.y);
            }
            return out;
        }
    }
}