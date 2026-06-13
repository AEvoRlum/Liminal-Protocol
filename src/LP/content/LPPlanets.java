package LP.content;

import arc.graphics.Color;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;
import mindustry.maps.planet.*;

import static mindustry.content.Planets.sun;

public class LPPlanets {
    public static Planet mx, mx1, mx2, mx3;

    public static void load() {
        mx = new Planet("mx", sun, 6f, 6){{
            meshLoader = () -> new MultiMesh(
                new SunMesh(mx, 5, 5, 0.3f, 1.5f, 1.2f, 1, 1.1f,
                    Color.valueOf("092398"), Color.valueOf("1D49FA"), Color.valueOf("305FFE"),
                    Color.valueOf("2358F1"), Color.valueOf("386EFF"), Color.valueOf("3A84FF"),
                    Color.valueOf("3196F5"), Color.valueOf("5EC9FF"), Color.valueOf("2C7AF6"),
                    Color.valueOf("2C7AF6"), Color.valueOf("9CF8FF"), Color.valueOf("42ABFF"),
                    Color.valueOf("42ABFF"))
            );
            bloom = true;
            accessible = false;
            drawOrbit = false;
            orbitRadius = 6000;
            orbitTime = 6000;
            rotateTime = 6;
            alwaysUnlocked = false;
            solarSystem = this;
            visible = true;
            radius = 6f;
            iconColor = Color.valueOf("533DFF");
            localizedName = "[#533DFF]MX108";
        }};

        mx1 = new Planet("mx1", mx, 0.62f, 2){{
            generator = new ErekirPlanetGenerator();
            meshLoader = () -> new MultiMesh(
                new NoiseMesh(this, 70, 5, 0.406f, 6, 0.35f, 12, 0.25f, Color.valueOf("8A5B5B"), Color.valueOf("B96F3D"), 4, 0.4f, 8, 0.3f),
                new NoiseMesh(this, 66, 5, 0.611f, 6, 0.3f, 14, 0.28f, Color.valueOf("eba768"), Color.valueOf("715555"), 4, 0.4f, 3, 0.42f),
                new NoiseMesh(this, 58, 4, 0.502f, 5, 0.3f, 12, 0.26f, Color.valueOf("856A6A"), Color.valueOf("9E6C6C"), 5, 0.4f, 5, 0.28f),
                new NoiseMesh(this, 155, 3, 0.015f, 8, 0.25f, 8, 0.2f, Color.valueOf("CD9D3C"), Color.valueOf("FF9D3C"), 8, 1f, 4, 0.3f),
                new NoiseMesh(this, 5, 4, 0.12f, 7, 0.3f, 10, 0.2f, Color.valueOf("8A6969"), Color.valueOf("715555"), 6, 0.5f, 5, 0.25f)
            );
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 574, 0.4f, 0.052f, 4, Color.valueOf("D7723080"), 7, 0.42f, 0.9f, 0.43f),
                new HexSkyMesh(this, 72, 0.6f, 0.1f, 4, Color.valueOf("FF975180"), 7, 0.42f, 0.9f, 0.42f)
            );
            parent = mx;
            solarSystem = mx;
            tidalLock = false;
            alwaysUnlocked = true;
            clearSectorOnLose = true;
            showRtsAIRule = false;
            allowCampaignRules = true;
            allowLegacyLaunchPads = true;
            allowWaves = true;
            allowLaunchLoadout = true;
            visible = true;
            drawOrbit = true;
            accessible = true;
            hasAtmosphere = true;
            updateLighting = true;
            allowLaunchToNumbered = false;
            allowSectorInvasion = true;
            bloom = false;
            allowLaunchSchematics = true;
            ruleSetter = r -> {
                r.loadout = null;
            };
            lightSrcTo = 0.5f;
            lightDstFrom = 0.1f;
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.3f;
            orbitRadius = 34;
            orbitTime = 1200 * 60;
            orbitSpacing = 2;
            rotateTime = 1200 * 60;
            atmosphereColor = Color.valueOf("f07218");
            lightColor = Color.valueOf("FF9D3C50");
            landCloudColor = Color.valueOf("ed6542");
            iconColor = Color.valueOf("CD9D3C");
            radius = 0.63f;
            minZoom = 1;
            maxZoom = 6;
            totalRadius += 3;
            startSector = 74;
            localizedName = "[#CD9D3C]MXrd-01";
            defaultCore = LPBlocks.pioneers;
        }};

        mx2 = new Planet("mx2", mx, 3f, 1){{
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 33, -6f, 0.02f, 8, Color.valueOf("ADC9C2"), 10, 0.5f, 0.88f, 0.42f),
                new HexSkyMesh(this, 44, 1.6f, 0.09f, 8, Color.valueOf("B08A85"), 7, 0.5f, 0.91f, 0.42f),
                new HexSkyMesh(this, 64, -1.2f, 0.13f, 8, Color.valueOf("9B766E"), 7, 0.5f, 0.82f, 0.44f),
                new HexSkyMesh(this, 24, 2f, 0.21f, 8, Color.valueOf("267392"), 5, 0.42f, 0.42f, 0.42f),
                new HexSkyMesh(this, 72, -1f, 0.06f, 8, Color.valueOf("BDB8D7"), 9, 0.6f, 0.66f, 0.5f),
                new HexSkyMesh(this, 4, 4f, 0.08f, 8, Color.valueOf("AE968E"), 8, 0.54f, 0.56f, 0.46f),
                new HexSkyMesh(this, 11, -1.9f, 0.05f, 8, Color.valueOf("1380A9"), 7, 0.79f, 0.48f, 0.48f),
                new HexSkyMesh(this, 87, -2.5f, 0.14f, 8, Color.valueOf("B5D5CF"), 8, 0.55f, 0.7f, 0.4f),
                new HexSkyMesh(this, 92, 3.2f, 0.08f, 8, Color.valueOf("C49A95"), 7, 0.48f, 0.8f, 0.45f),
                new HexSkyMesh(this, 15, -0.8f, 0.17f, 8, Color.valueOf("8A6A63"), 7, 0.52f, 0.6f, 0.5f),
                new HexSkyMesh(this, 51, 5f, 0.04f, 7, Color.valueOf("2A839F"), 6, 0.6f, 0.5f, 0.38f),
                new HexSkyMesh(this, 38, -3f, 0.23f, 8, Color.valueOf("A9A3C2"), 9, 0.65f, 0.72f, 0.46f),
                new HexSkyMesh(this, 29, 1.2f, 0.12f, 8, Color.valueOf("BCAAA3"), 7, 0.5f, 0.6f, 0.43f),
                new HexSkyMesh(this, 63, -2.1f, 0.07f, 8, Color.valueOf("1F6B8F"), 6, 0.7f, 0.5f, 0.41f),
                new HexSkyMesh(this, 79, 2.8f, 0.11f, 8, Color.valueOf("D3D8DD"), 10, 0.45f, 0.9f, 0.44f),
                new HexSkyMesh(this, 8, -4f, 0.24f, 8, Color.valueOf("5C5278"), 6, 0.58f, 0.55f, 0.39f),
                new HexSkyMesh(this, 56, 1.9f, 0.05f, 8, Color.valueOf("8FCCC4"), 9, 0.5f, 0.78f, 0.42f)
            );
            meshLoader = () -> new MultiMesh(
                new NoiseMesh(this, 101, 5, 2.0f, 3, 0.35f, 18, 0.3f, Color.valueOf("1380A9"), Color.valueOf("4486A4"), 3, 0.4f, 2, 0.4f),
                new NoiseMesh(this, 102, 5, 2.2f, 4, 0.3f, 16, 0.25f, Color.valueOf("CDD3D6"), Color.valueOf("D6B9AC"), 3, 0.4f, 2, 0.4f),
                new NoiseMesh(this, 103, 5, 1.8f, 3, 0.4f, 20, 0.35f, Color.valueOf("5D5971"), Color.valueOf("334166"), 3, 0.4f, 2, 0.4f),
                new NoiseMesh(this, 104, 5, 2.5f, 3, 0.3f, 15, 0.28f, Color.valueOf("B4B2CC"), Color.valueOf("83C0D4"), 3, 0.4f, 2, 0.4f),
                new NoiseMesh(this, 105, 5, 1.5f, 4, 0.35f, 22, 0.32f, Color.valueOf("929081"), Color.valueOf("0F6294"), 3, 0.4f, 2, 0.4f)
            );
            parent = mx;
            solarSystem = mx;
            tidalLock = false;
            bloom = false;
            accessible = false;
            alwaysUnlocked = false;
            visible = true;
            drawOrbit = true;
            updateLighting = true;
            hasAtmosphere = true;
            radius = 3f;
            orbitRadius = 86;
            orbitTime = 9000 * 60;
            orbitSpacing = 2.2f;
            rotateTime = 60 * 60;
            atmosphereRadIn = 0.1f;
            atmosphereRadOut = 0.9f;
            lightDstFrom = 0.1f;
            lightSrcTo = 0.6f;
            atmosphereColor = Color.valueOf("9B766E");
            lightColor = Color.valueOf("6964AC50");
            landCloudColor = Color.valueOf("EFDDD8");
            minZoom = 1;
            maxZoom = 6;
            totalRadius += 3;
            localizedName = "[#6964AC]MXrd-02";
        }};

        mx3 = new Planet("mx3", mx, 1f, 3){{
            generator = new SerpuloPlanetGenerator();
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 77, -1.2f, 0.08f, 7, Color.valueOf("88A1C980"), 5, 0.5f, 0.8f, 0.4f),
                new HexSkyMesh(this, 65, 0.5f, 0.13f, 7, Color.valueOf("AABDD980"), 6, 0.6f, 0.85f, 0.45f),
                new HexSkyMesh(this, 24, 2.0f, 0.086f, 7, Color.valueOf("BAC9DD80"), 7, 0.7f, 0.9f, 0.5f)
            );
            meshLoader = () -> new MultiMesh(new HexMesh(this, 7));
            parent = mx2;
            solarSystem = mx;
            showRtsAIRule = false;
            allowLaunchToNumbered = false;
            bloom = false;
            tidalLock = false;
            alwaysUnlocked = true;
            clearSectorOnLose = true;
            visible = true;
            drawOrbit = true;
            accessible = true;
            hasAtmosphere = true;
            updateLighting = true;
            allowSectorInvasion = true;
            allowWaves = true;
            allowLegacyLaunchPads = true;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            lightSrcTo = 0.3f;
            lightDstFrom = 0.1f;
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.5f;
            orbitRadius = 16;
            orbitTime = 600 * 60;
            orbitSpacing = 1.6f;
            rotateTime = 2000 * 60;
            atmosphereColor = Color.valueOf("5297CD30");
            lightColor = Color.valueOf("509ACC50");
            landCloudColor = Color.valueOf("7A94AD7F");
            iconColor = Color.valueOf("5297CD");
            radius = 1f;
            minZoom = 1;
            maxZoom = 6;
            startSector = 0;
            sectorSeed = 2;
            localizedName = "[#5297CD]MXrd-03";
        }};
    }
}
