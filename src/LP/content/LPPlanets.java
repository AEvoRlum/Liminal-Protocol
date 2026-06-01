package LP.content;

import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.Planets;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class LPPlanets {
    public static Planet mx, mx1, mx2, mx3;

    public static void load() {
        mx = new Planet("mx", Planets.sun, 6, 6);
        mx.meshLoader = () -> new MultiMesh(
            new SunMesh(mx, 5, 5, 0.3f, 1.5f, 1.2f, 1, 1.1f,
                Color.valueOf("092398"), Color.valueOf("1D49FA"), Color.valueOf("305FFE"),
                Color.valueOf("2358F1"), Color.valueOf("386EFF"), Color.valueOf("3A84FF"),
                Color.valueOf("3196F5"), Color.valueOf("5EC9FF"), Color.valueOf("2C7AF6"),
                Color.valueOf("2C7AF6"), Color.valueOf("9CF8FF"), Color.valueOf("42ABFF"),
                Color.valueOf("42ABFF"))
        );
        setPlanet(mx, p -> {
            p.bloom = true;
            p.accessible = false;
            p.drawOrbit = false;
            p.orbitRadius = 6000;
            p.orbitTime = 6000;
            p.rotateTime = 6;
            p.alwaysUnlocked = false;
            p.solarSystem = p;
            p.visible = true;
            p.radius = 6;
            p.iconColor = Color.valueOf("533DFF");
            p.localizedName = "[#533DFF]MX108";
        });

        mx1 = new Planet("mx1", mx, 0.62f, 2);
        mx1.meshLoader = () -> new MultiMesh(
            new NoiseMesh(mx1, 70, 5, 0.406f, 6, 0.35f, 12, 0.25f, Color.valueOf("8A5B5B"), Color.valueOf("B96F3D"), 4, 0.4f, 8, 0.3f),
            new NoiseMesh(mx1, 66, 5, 0.611f, 6, 0.3f, 14, 0.28f, Color.valueOf("eba768"), Color.valueOf("715555"), 4, 0.4f, 3, 0.42f),
            new NoiseMesh(mx1, 58, 4, 0.502f, 5, 0.3f, 12, 0.26f, Color.valueOf("856A6A"), Color.valueOf("9E6C6C"), 5, 0.4f, 5, 0.28f),
            new NoiseMesh(mx1, 155, 3, 0.015f, 8, 0.25f, 8, 0.2f, Color.valueOf("CD9D3C"), Color.valueOf("FF9D3C"), 8, 1f, 4, 0.3f),
            new NoiseMesh(mx1, 5, 4, 0.12f, 7, 0.3f, 10, 0.2f, Color.valueOf("8A6969"), Color.valueOf("715555"), 6, 0.5f, 5, 0.25f)
        );
        mx1.cloudMeshLoader = () -> new MultiMesh(
            new HexSkyMesh(mx1, 574, 0.4f, 0.052f, 4, Color.valueOf("D7723080"), 7, 0.42f, 0.9f, 0.43f),
            new HexSkyMesh(mx1, 72, 0.6f, 0.1f, 4, Color.valueOf("FF975180"), 7, 0.42f, 0.9f, 0.42f)
        );
        setPlanet(mx1, p -> {
            p.parent = mx;
            p.solarSystem = mx;
            p.tidalLock = false;
            p.alwaysUnlocked = true;
            p.clearSectorOnLose = true;
            p.showRtsAIRule = false;
            p.allowCampaignRules = true;
            p.allowLegacyLaunchPads = true;
            p.allowWaves = true;
            p.allowLaunchLoadout = true;
            p.visible = true;
            p.drawOrbit = true;
            p.accessible = true;
            p.hasAtmosphere = true;
            p.updateLighting = true;
            p.allowLaunchToNumbered = false;
            p.allowSectorInvasion = true;
            p.bloom = false;
            p.allowLaunchSchematics = true;
            p.lightSrcTo = 0.5f;
            p.lightDstFrom = 0.1f;
            p.atmosphereRadIn = 0.01f;
            p.atmosphereRadOut = 0.3f;
            p.orbitRadius = 34;
            p.orbitTime = 1200 * 60;
            p.orbitSpacing = 2;
            p.rotateTime = 1200 * 60;
            p.atmosphereColor = Color.valueOf("f07218");
            p.lightColor = Color.valueOf("FF9D3C50");
            p.landCloudColor = Color.valueOf("ed6542");
            p.iconColor = Color.valueOf("CD9D3C");
            p.radius = 0.63f;
            p.minZoom = 1;
            p.maxZoom = 6;
            p.totalRadius += 3;
            p.startSector = 74;
            p.localizedName = "[#CD9D3C]MXrd-01";
        });

        mx2 = new Planet("mx2", mx, 3f, 1);
        mx2.cloudMeshLoader = () -> new MultiMesh(
            new HexSkyMesh(mx2, 33, -6f, 0.02f, 8, Color.valueOf("ADC9C2"), 10, 0.5f, 0.88f, 0.42f),
            new HexSkyMesh(mx2, 44, 1.6f, 0.09f, 8, Color.valueOf("B08A85"), 7, 0.5f, 0.91f, 0.42f),
            new HexSkyMesh(mx2, 64, -1.2f, 0.13f, 8, Color.valueOf("9B766E"), 7, 0.5f, 0.82f, 0.44f),
            new HexSkyMesh(mx2, 24, 2f, 0.21f, 8, Color.valueOf("267392"), 5, 0.42f, 0.42f, 0.42f),
            new HexSkyMesh(mx2, 72, -1f, 0.06f, 8, Color.valueOf("BDB8D7"), 9, 0.6f, 0.66f, 0.5f),
            new HexSkyMesh(mx2, 4, 4f, 0.08f, 8, Color.valueOf("AE968E"), 8, 0.54f, 0.56f, 0.46f),
            new HexSkyMesh(mx2, 11, -1.9f, 0.05f, 8, Color.valueOf("1380A9"), 7, 0.79f, 0.48f, 0.48f),
            new HexSkyMesh(mx2, 87, -2.5f, 0.14f, 8, Color.valueOf("B5D5CF"), 8, 0.55f, 0.7f, 0.4f),
            new HexSkyMesh(mx2, 92, 3.2f, 0.08f, 8, Color.valueOf("C49A95"), 7, 0.48f, 0.8f, 0.45f),
            new HexSkyMesh(mx2, 15, -0.8f, 0.17f, 8, Color.valueOf("8A6A63"), 7, 0.52f, 0.6f, 0.5f),
            new HexSkyMesh(mx2, 51, 5f, 0.04f, 7, Color.valueOf("2A839F"), 6, 0.6f, 0.5f, 0.38f),
            new HexSkyMesh(mx2, 38, -3f, 0.23f, 8, Color.valueOf("A9A3C2"), 9, 0.65f, 0.72f, 0.46f),
            new HexSkyMesh(mx2, 29, 1.2f, 0.12f, 8, Color.valueOf("BCAAA3"), 7, 0.5f, 0.6f, 0.43f),
            new HexSkyMesh(mx2, 63, -2.1f, 0.07f, 8, Color.valueOf("1F6B8F"), 6, 0.7f, 0.5f, 0.41f),
            new HexSkyMesh(mx2, 79, 2.8f, 0.11f, 8, Color.valueOf("D3D8DD"), 10, 0.45f, 0.9f, 0.44f),
            new HexSkyMesh(mx2, 8, -4f, 0.24f, 8, Color.valueOf("5C5278"), 6, 0.58f, 0.55f, 0.39f),
            new HexSkyMesh(mx2, 56, 1.9f, 0.05f, 8, Color.valueOf("8FCCC4"), 9, 0.5f, 0.78f, 0.42f)
        );
        mx2.meshLoader = () -> new MultiMesh(
            new NoiseMesh(mx2, 101, 5, 2.0f, 3, 0.35f, 18, 0.3f, Color.valueOf("1380A9"), Color.valueOf("4486A4"), 3, 0.4f, 2, 0.4f),
            new NoiseMesh(mx2, 102, 5, 2.2f, 4, 0.3f, 16, 0.25f, Color.valueOf("CDD3D6"), Color.valueOf("D6B9AC"), 3, 0.4f, 2, 0.4f),
            new NoiseMesh(mx2, 103, 5, 1.8f, 3, 0.4f, 20, 0.35f, Color.valueOf("5D5971"), Color.valueOf("334166"), 3, 0.4f, 2, 0.4f),
            new NoiseMesh(mx2, 104, 5, 2.5f, 3, 0.3f, 15, 0.28f, Color.valueOf("B4B2CC"), Color.valueOf("83C0D4"), 3, 0.4f, 2, 0.4f),
            new NoiseMesh(mx2, 105, 5, 1.5f, 4, 0.35f, 22, 0.32f, Color.valueOf("929081"), Color.valueOf("0F6294"), 3, 0.4f, 2, 0.4f)
        );
        setPlanet(mx2, p -> {
            p.parent = mx;
            p.solarSystem = mx;
            p.tidalLock = false;
            p.bloom = false;
            p.accessible = false;
            p.alwaysUnlocked = false;
            p.visible = true;
            p.drawOrbit = true;
            p.updateLighting = true;
            p.hasAtmosphere = true;
            p.radius = 3;
            p.orbitRadius = 86;
            p.orbitTime = 9000 * 60;
            p.orbitSpacing = 2.2f;
            p.rotateTime = 60 * 60;
            p.atmosphereRadIn = 0.1f;
            p.atmosphereRadOut = 0.9f;
            p.lightDstFrom = 0.1f;
            p.lightSrcTo = 0.6f;
            p.atmosphereColor = Color.valueOf("9B766E");
            p.lightColor = Color.valueOf("6964AC50");
            p.landCloudColor = Color.valueOf("EFDDD8");
            p.minZoom = 1;
            p.maxZoom = 6;
            p.totalRadius += 3;
            p.localizedName = "[#6964AC]MXrd-02";
        });

        mx3 = new Planet("mx3", mx, 1f, 3);
        mx3.generator = new SerpuloPlanetGenerator();
        mx3.cloudMeshLoader = () -> new MultiMesh(
            new HexSkyMesh(mx3, 77, -1.2f, 0.08f, 7, Color.valueOf("88A1C980"), 5, 0.5f, 0.8f, 0.4f),
            new HexSkyMesh(mx3, 65, 0.5f, 0.13f, 7, Color.valueOf("AABDD980"), 6, 0.6f, 0.85f, 0.45f),
            new HexSkyMesh(mx3, 24, 2.0f, 0.086f, 7, Color.valueOf("BAC9DD80"), 7, 0.7f, 0.9f, 0.5f)
        );
        mx3.meshLoader = () -> new MultiMesh(new HexMesh(mx3, 7));
        setPlanet(mx3, p -> {
            p.parent = mx2;
            p.solarSystem = mx;
            p.showRtsAIRule = false;
            p.allowLaunchToNumbered = false;
            p.bloom = false;
            p.tidalLock = false;
            p.alwaysUnlocked = true;
            p.clearSectorOnLose = true;
            p.visible = true;
            p.drawOrbit = true;
            p.accessible = true;
            p.hasAtmosphere = true;
            p.updateLighting = true;
            p.allowSectorInvasion = true;
            p.allowWaves = true;
            p.allowLegacyLaunchPads = true;
            p.allowLaunchSchematics = true;
            p.enemyCoreSpawnReplace = true;
            p.allowLaunchLoadout = true;
            p.lightSrcTo = 0.3f;
            p.lightDstFrom = 0.1f;
            p.atmosphereRadIn = 0.01f;
            p.atmosphereRadOut = 0.5f;
            p.orbitRadius = 16;
            p.orbitTime = 600 * 60;
            p.orbitSpacing = 1.6f;
            p.rotateTime = 2000 * 60;
            p.atmosphereColor = Color.valueOf("5297CD30");
            p.lightColor = Color.valueOf("509ACC50");
            p.landCloudColor = Color.valueOf("7A94AD7F");
            p.iconColor = Color.valueOf("5297CD");
            p.radius = 1;
            p.minZoom = 1;
            p.maxZoom = 6;
            p.startSector = 0;
            p.sectorSeed = 2;
            p.localizedName = "[#5297CD]MXrd-03";
        });
    }

    private static void setPlanet(Planet planet, PlanetConfig config) {
        config.apply(planet);
    }

    @FunctionalInterface
    private interface PlanetConfig {
        void apply(Planet planet);
    }
}
