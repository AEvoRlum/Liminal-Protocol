package LP.planet;

import arc.math.*;
import arc.math.geom.Vec3;
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

import LP.content.LPBlocks;
import LP.LPMod;

public class Mx1PlanetGenerator extends PlanetGenerator {
    public float heightScl = 0.9f, octaves = 8, persistence = 0.7f, heightPow = 3f, heightMult = 1.6f;

    public static float arkThresh = 0.28f, arkScl = 0.83f;
    public static int arkSeed = 7, arkOct = 2;
    public static float liqThresh = 0.64f, liqScl = 87f, redThresh = 3.1f, noArkThresh = 0.3f;
    public static int crystalSeed = 8, crystalOct = 2;
    public static float crystalScl = 0.9f, crystalMag = 0.3f;
    public static float airThresh = 0.13f, airScl = 14;
    
    public static float relicChance = 0.02f;
    public static float slagChance = 0.03f;

    Block[] terrain = {
        LPBlocks.darkStone, 
        LPBlocks.darkStone, 
        LPBlocks.rhyoliticLimestone, 
        LPBlocks.rhyoliticLimestone,
        LPBlocks.rhyoliticLimestone,
        LPBlocks.rhyoliticRubble,
        LPBlocks.darkStoneRubble
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
        float px = position.x, py = position.y, pz = position.z;

        float height = rawHeight(position);
        height *= 1.2f;
        height = Mathf.clamp(height);

        Block result = terrain[Mathf.clamp((int)(height * terrain.length), 0, terrain.length - 1)];

        if(Ridged.noise3d(seed + crystalSeed, px + 4f, py + 8f, pz + 1f, crystalOct, crystalScl) > arkThresh){
            if(rand.chance(0.5f)){
                return LPBlocks.darkStoneCrystal;
            }else{
                return LPBlocks.erocrysCrystal;
            }
        }

        if(Ridged.noise3d(seed + arkSeed, px + 2f, py + 8f, pz + 1f, arkOct, arkScl) > arkThresh){
            result = LPBlocks.relicfloor;
        }

        if(rand.chance(relicChance)){
            int relicType = rand.random(5);
            switch(relicType){
                case 0: result = LPBlocks.relicfloor2; break;
                case 1: result = LPBlocks.relicfloor3; break;
                case 2: result = LPBlocks.relicfloor4; break;
                case 3: result = LPBlocks.relicfloor5; break;
                case 4: result = LPBlocks.relicfloor6; break;
                default: result = LPBlocks.relicfloor; break;
            }
        }

        return result;
    }

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);

        if(tile.floor == LPBlocks.rhyoliticLimestone && rand.chance(0.01)){
            tile.floor = LPBlocks.darkStoneRock;
        }

        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, airScl) > airThresh){
            tile.block = Blocks.air;
        }

        if(Ridged.noise3d(seed + 2, position.x, position.y + 4f, position.z, 3, 6f) > 0.6){
            if(rand.chance(slagChance)){
                tile.floor = Blocks.slag;
                tile.block = Blocks.air;
            }
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

                    if(noise > 0.55f && floor == LPBlocks.relicfloor){
                        floor = LPBlocks.darkStone;
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
            if(floor == LPBlocks.relicfloor && noise(x, y, 2, 0.3f, 10f, 1f) > 0.6f){
                block = LPBlocks.relicWall;
            }
        });

        pass((x, y) -> {
            if(floor == LPBlocks.relicfloor2 && noise(x, y, 2, 0.3f, 10f, 1f) > 0.6f){
                block = LPBlocks.relicWall2;
            }
        });

        ore(LPBlocks.darkStone, LPBlocks.jynSteelOre, 0.08f, 15f);
        ore(LPBlocks.darkStone, LPBlocks.massisteelOre, 0.06f, 12f);
        ore(LPBlocks.rhyoliticLimestone, LPBlocks.erocrysOre, 0.04f, 10f);
        ore(LPBlocks.rhyoliticLimestone, LPBlocks.litelnlayOre, 0.03f, 8f);

        if(temp > 0.7){
            pass((x, y) -> {
                if(floor == Blocks.slag){
                    ore = Blocks.air;
                }
            });
        }

        if(temp < 0.5){
            pass((x, y) -> {
                if(floor == LPBlocks.darkStone && noise(x, y, 4, 0.5f, 80f, 1f) > 0.55f){
                    floor = LPBlocks.reliclines;
                }
            });
        }
    }
}
