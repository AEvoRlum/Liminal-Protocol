package LP.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.Vars;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.graphics.Layer;
import LP.LPMod;

import static LP.LPMod.name;

public class LPContent extends Content{
    private static final TextureRegion emptyRegion = new TextureRegion();

    public static TextureRegion arrowRegion,
    pointerRegion,
    strafeRegion, missileRegion, bombRegion, annihilateArrow,
    bombard, fleet, objective, airborne,

    dropPod, dropPodTeam, dropPodSide1, dropPodSideTeam1, dropPodSide2, dropPodSideTeam2;

    public static final float HEXAGONAL_SHIELD = Layer.shields + 12f;
    public static final float VOID_SHIELD = Layer.shields + 9f;
    public static final float POWER_AREA = Layer.power + 3f;
    public static final float POWER_DYNAMIC = Layer.power + 4f;

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }

    public static TextureRegion safeRegion(TextureRegion region) {
        return region == null ? emptyRegion : region;
    }

    public static boolean hasRegion(TextureRegion region) {
        return region != null && region.width > 0f && region.height > 0f;
    }

    public static void loadPriority(){
        if (Vars.headless) return;
        if (Core.atlas == null) {
            return;
        }
        new LPContent().load();
    }

    public void load(){
        if (Core.atlas == null) return;

        arrowRegion = Core.atlas.find(LPMod.name("jump-gate-arrow"));
        pointerRegion = Core.atlas.find(LPMod.name("jump-gate-pointer"));
        strafeRegion = Core.atlas.find(LPMod.name("strafe-mode"));
        missileRegion = Core.atlas.find(LPMod.name("missile-mode"));
        bombRegion = Core.atlas.find(LPMod.name("bomb-mode"));
        annihilateArrow = Core.atlas.find(LPMod.name("Annihilate-arrow"));
        bombard = Core.atlas.find(LPMod.name("bombard"));
        fleet = Core.atlas.find(LPMod.name("fleet"));
        objective = Core.atlas.find(LPMod.name("objective"));
        airborne = Core.atlas.find(LPMod.name("airborne"));

        String dp = "space-marine-drop-pod";
        dropPod = Core.atlas.find(name(dp));
        dropPodTeam = Core.atlas.find(name(dp + "-team"));
        dropPodSide1 = Core.atlas.find(name(dp + "-side1"));
        dropPodSideTeam1 = Core.atlas.find(name(dp + "-side1-team"));
        dropPodSide2 = Core.atlas.find(name(dp + "-side2"));
        dropPodSideTeam2 = Core.atlas.find(name(dp + "-side2-team"));
    }
}

