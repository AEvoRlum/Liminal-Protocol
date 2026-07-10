package LP.planet;

import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import arc.graphics.Color;
import arc.util.noise.*;
import arc.files.Fi;
import arc.util.Nullable;
import mindustry.content.*;
import mindustry.game.Schematic;
import mindustry.game.Schematics;
import mindustry.maps.generators.*;
import mindustry.world.*;
import mindustry.Vars;
import mindustry.mod.Mods;
import mindustry.ai.*;

import LP.content.LPBlocks;
import LP.LPMod;

public class Mx1PlanetGenerator extends PlanetGenerator {
    public float heightScl = 0.9f, octaves = 8, persistence = 0.7f, heightPow = 3f, heightMult = 1.6f;

    public static float airThresh = 0.13f, airScl = 14;

    Block[] terrain = {
        LPBlocks.darkStone, 
        LPBlocks.darkStone, 
        LPBlocks.rhyoliticLimestone, 
        LPBlocks.rhyoliticLimestone,
        LPBlocks.rhyoliticLimestone,
        LPBlocks.rhyoliticRubble,
        LPBlocks.darkStoneRubble,
        Blocks.rhyolite,
        Blocks.rhyolite,
        Blocks.roughRhyolite
    };

    private static final String PIONEERS_SCHEMATIC_PATH = "assets/schematics/pioneers.msch";
    private static final String PIONEERS_SCHEMATIC_PATH_FALLBACK = "schematics/pioneers.msch";
    private static final String PIONEERS_DEFAULT_LOADOUT_BASE64 = "bXNjaAF4nGNgZmBmYWDJS8xNZeAoyMzPS00tKmbgSs7PK0nNK/FNLGBgqq5l4E5JLU4uyiwoASpgYGBgy0lMSs0pZmCKjmVk4M4p0IVrZGBgBCEgAQA17RaZ";

    {
        baseSeed = 2;
    }

    public Mx1PlanetGenerator(){
        defaultLoadout = loadPioneersLoadout();
    }

    private Schematic loadPioneersLoadout(){
        Schematic fromFile = tryReadModSchematicFile();
        if(fromFile != null) return fromFile;
        return Schematics.readBase64(PIONEERS_DEFAULT_LOADOUT_BASE64);
    }

    private Schematic tryReadModSchematicFile(){
        try{
            if(Vars.mods == null) return null;
            Mods.LoadedMod mod = Vars.mods.getMod(LPMod.class);
            if(mod == null || mod.root == null) return null;

            Schematic primary = readSchematicIfExists(mod.root.child(PIONEERS_SCHEMATIC_PATH));
            if(primary != null) return primary;
            return readSchematicIfExists(mod.root.child(PIONEERS_SCHEMATIC_PATH_FALLBACK));
        }catch(Throwable ignored){
            return null;
        }
    }

    @Nullable
    private Schematic readSchematicIfExists(@Nullable Fi file){
        if(file == null || !file.exists()) return null;
        try{
            return Schematics.read(file);
        }catch(Throwable ignored){
            return null;
        }
    }

    @Override
    public float getHeight(Vec3 position){
        return Mathf.pow(rawHeight(position), heightPow) * heightMult;
    }

    @Override
    public void getColor(Vec3 position, Color out){
        Block block = getBlock(position);
        out.set(block.mapColor).a(1f - block.albedo);
    }

    @Override
    public float getSizeScl(){
        return 2000 * 1.07f * 6f / 5f;
    }

    float rawHeight(Vec3 position){
        return Simplex.noise3d(seed, octaves, persistence, 1f/heightScl, 10f + position.x, 10f + position.y, 10f + position.z);
    }

    float rawTemp(Vec3 position){
        return position.dst(0, 0, 1)*2.2f - Simplex.noise3d(seed, 8, 0.54f, 1.4f, 10f + position.x, 10f + position.y, 10f + position.z) * 2.9f;
    }

    Block getBlock(Vec3 position){
        float height = rawHeight(position);
        height *= 1.2f;
        height = Mathf.clamp(height);

        return terrain[Mathf.clamp((int)(height * terrain.length), 0, terrain.length - 1)];
    }

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);

        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, airScl) > airThresh){
            tile.block = Blocks.air;
        }

        if(Ridged.noise3d(seed + 2, position.x, position.y + 4f, position.z, 3, 6f) > 0.6){
            tile.floor = Blocks.slag;
        }
    }

    @Override
    protected void generate(){
        float temp = rawTemp(sector.tile.v);

        if(temp > 0.7){
            pass((x, y) -> {
                if(floor != Blocks.redIce){
                    float noise = noise(x + 782, y, 7, 0.8f, 280f, 1f);
                    if(noise > 0.62f){
                        if(noise > 0.635f){
                            floor = Blocks.slag;
                        }else{
                            floor = LPBlocks.darkStone;
                        }
                        ore = Blocks.air;
                    }
                }
            });
        }

        cells(4);

        pass((x, y) -> {
            if(floor == LPBlocks.darkStone && noise(x, y, 3, 0.4f, 13f, 1f) > 0.59f){
                block = LPBlocks.darkStoneWall;
            }
        });

        pass((x, y) -> {
            if(floor == LPBlocks.rhyoliticLimestone && noise(x, y, 3, 0.4f, 13f, 1f) > 0.59f){
                block = LPBlocks.rhyoliticLimestoneWall;
            }
        });

        pass((x, y) -> {
            if(floor == LPBlocks.rhyoliticLimestone && block == Blocks.air && rand.chance(0.01f)){
                block = LPBlocks.darkStoneRock;
            }
        });

        pass((x, y) -> {
            if(floor == Blocks.rhyolite && noise(x, y, 3, 0.4f, 13f, 1f) > 0.59f){
                block = Blocks.rhyoliteWall;
            }
        });

        distort(10f, 12f);
        distort(5f, 7f);

        if(temp > 0.7){
            pass((x, y) -> {
                if(floor == Blocks.slag){
                    ore = Blocks.air;
                }
            });
        }

        

        float length = width/2.6f;
        Vec2 trns = Tmp.v1.trns(rand.random(360f), length);
        int
        spawnX = (int)(trns.x + width/2f), spawnY = (int)(trns.y + height/2f),
        endX = (int)(-trns.x + width/2f), endY = (int)(-trns.y + height/2f);
        float maxd = Mathf.dst(width/2f, height/2f);

        erase(spawnX, spawnY, 15);
        brush(pathfind(spawnX, spawnY, endX, endY, tile -> (tile.solid() ? 300f : 0f) + maxd - tile.dst(width/2f, height/2f)/10f, Astar.manhattan), 9);
        erase(endX, endY, 15);

        blend(Blocks.slag, LPBlocks.darkStone, 4);

        median(3, 0.6, Blocks.slag);

        pass((x, y) -> {
            if(floor == Blocks.slag && Mathf.within(x, y, spawnX, spawnY, 30f + noise(x, y, 2, 0.8f, 9f, 15f))){
                floor = LPBlocks.darkStone;
            }
        });

        inverseFloodFill(tiles.getn(spawnX, spawnY));

        pass((x, y) -> {
            if(block != Blocks.air) return;

            boolean hasWall = false;
            for(Point2 p : Geometry.d4){
                Tile other = tiles.get(x + p.x, y + p.y);
                if(other != null && other.block().solid){
                    hasWall = true;
                    break;
                }
            }

            if(!hasWall) return;

            if(floor == LPBlocks.darkStone && rand.chance(0.07f) && !near(x, y, 4, LPBlocks.darkStoneCrystal)){
                block = LPBlocks.darkStoneCrystal;
            }else if(floor == LPBlocks.rhyoliticLimestone && rand.chance(0.07f) && !near(x, y, 4, LPBlocks.erocrysCrystal)){
                block = LPBlocks.erocrysCrystal;
            }
        });

        pass((x, y) -> {
            if(!floor.asFloor().hasSurface()) return;

            if(noise(x + 150, y + x*2 + 100, 4, 0.8f, 55f, 1f) > 0.76f){
                ore = LPBlocks.jynSteelOre;
            }

            if(noise(x + 999, y + 600 - x, 4, 0.8f, 50f, 1f) > 0.78f){
                ore = LPBlocks.massisteelOre;
            }

            if(noise(x + 782, y - x*1.5f + 300, 4, 0.75f, 48f, 1f) > 0.77f){
                ore = LPBlocks.erocrysOre;
            }

            if(noise(x + 555, y + x*1.3f + 800, 4, 0.85f, 52f, 1f) > 0.80f){
                ore = LPBlocks.litelnlayOre;
            }
        });

        pass((x, y) -> {
            if(block != Blocks.air){
                ore = Blocks.air;
            }
        });

        for(Tile tile : tiles){
            if(tile.overlay().needsSurface && !tile.floor().hasSurface()){
                tile.setOverlay(Blocks.air);
            }
        }

        trimDark();

        tiles.getn(endX, endY).setOverlay(Blocks.spawn);

        Schematics.placeLaunchLoadout(spawnX, spawnY);
    }
}