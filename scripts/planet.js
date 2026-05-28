// 导入所需 Java 类
// const SunMesh = Java.type("mindustry.graphics.g3d.SunMesh");
// const Color = Java.type("arc.graphics.Color");
const lib = require("base/lib");
// public class SunMesh extends HexMesh{

    // public SunMesh(Planet planet, int divisions, double octaves, double persistence, double scl, double pow, double mag, float colorScale, Color... colors){
        // super(planet, new HexMesher(){

            // @Override
            // public float getHeight(Vec3 position){
                // return 0;
            // }

            // @Override
            // public void getColor(Vec3 position, Color out){
                // double height = Math.pow(Simplex.noise3d(0, octaves, persistence, scl, position.x, position.y, position.z), pow) * mag;
                // out.set(colors[Mathf.clamp((int)(height * colors.length), 0, colors.length - 1)]).mul(colorScale);
            // }
        // }, divisions, Shaders.unlit);
    // }
// }

const mx = new Planet("mx", Planets.sun, 6, 6);
mx.meshLoader = () => new MultiMesh(
    new SunMesh(mx, 5, 5, 0.3, 1.5, 1.2, 1, 1.1, Color.valueOf("092398"), Color.valueOf("1D49FA"), Color.valueOf("305FFE"), Color.valueOf("2358F1"), Color.valueOf("386EFF"), Color.valueOf("3A84FF"), Color.valueOf("3196F5"), Color.valueOf("5EC9FF"), Color.valueOf("2C7AF6"), Color.valueOf("2C7AF6"), Color.valueOf("9CF8FF"), Color.valueOf("42ABFF"), Color.valueOf("42ABFF"))
);

mx.bloom = true;
mx.accessible = false;
mx.drawOrbit = false;
mx.orbitRadius = 6000;
mx.orbitTime = 6000;
mx.rotateTime = 6;
mx.alwaysUnlocked = false;
mx.solarSystem = mx;
mx.visible = true;
mx.radius = 6;
mx.iconColor = Color.valueOf("533DFF");
mx.localizedName = "[#533DFF]MX108";
exports.mx = mx;

// public HexMesh(Planet planet, int divisions){
        // super(planet, MeshBuilder.buildHex(planet.generator, divisions, planet.radius, 0.2f), Shaders.planet);
    // }

    // public HexMesh(Planet planet, HexMesher mesher, int divisions, Shader shader){
        // super(planet, MeshBuilder.buildHex(mesher, divisions, planet.radius, 0.2f), shader);
    // }

    // public HexMesh(){
// }

// public HexSkyMesh(Planet planet, int seed, float speed, float radius, int divisions, Color color, int octaves, float persistence, float scl, float thresh){
        // super(planet, MeshBuilder.buildHex(new HexMesher(){
            // @Override
            // public float getHeight(Vec3 position){
                // return 1f;
            // }

            // @Override
            // public void getColor(Vec3 position, Color out){
                // out.set(color);
            // }

            // @Override
            // public boolean skip(Vec3 position){
                // return Simplex.noise3d(7 + seed, octaves, persistence, scl, position.x, position.y * 3f, position.z) >= thresh;
            // }
        // }, divisions, planet.radius, radius), Shaders.clouds);

        // this.speed = speed;
    // }
// public class NoiseMesh extends HexMesh{

    // public NoiseMesh(Planet planet, int seed, int divisions, Color color, float radius, int octaves, float persistence, float scale, float mag){
        // this.planet = planet;
        // this.shader = Shaders.planet;
        // this.mesh = MeshBuilder.buildHex(new HexMesher(){
            // @Override
            // public float getHeight(Vec3 position){
                // return Simplex.noise3d(7 + seed, octaves, persistence, scale, 5f + position.x, 5f + position.y, 5f + position.z) * mag;
            // }

            // @Override
            // public void getColor(Vec3 position, Color out){
                // out.set(color);
            // }
        // }, divisions, radius, 0.2f);
    // }
/** Two-color variant. */
    // public NoiseMesh(Planet planet, int seed, int divisions, float radius, int octaves, float persistence, float scale, float mag, Color color1, Color color2, int coct, float cper, float cscl, float cthresh){

const mx1 = new Planet("mx1", mx, 0.62, 2);
mx1.generator = new ErekirPlanetGenerator();
mx1.generator = extend(ErekirPlanetGenerator, {
    getDefaultLoadout() {
        return Schematics.readBase64("bXNjaAF4nGNgZmBmZWDJS8xNZeB81j35Re+kFw2tDFzJ+XklqXklvokFDEzVtQzsxSWpibmZKQxcxckZqbmJJZnJxQzcKanFyUWZBSWZ+XkMDAxsOYlJqTnFDEzRsYwM3DkFugVA8dTUomKgHCMIAQkAvHUgSg==")
    }
});
// mx1.meshLoader = prov(() => new MultiMesh(
    // new NoiseMesh(mx1, 70, 5, 0.406, 3, 2, 5, 0.59, Color.valueOf("8A5B5B"), Color.valueOf("B96F3D"), 3, 0.4, 6, 0.45),
    // new NoiseMesh(mx1, 66, 5, 0.611, 3, 4, 12, 0.7, Color.valueOf("ed6542"), Color.valueOf("715555"), 3, 0.4, 2, 0.4),
    // new NoiseMesh(mx1, 58, 4.5, 0.502, 4.5, 3, 6, 0.66, Color.valueOf("856A6A"), Color.valueOf("9E6C6C"), 4.5, 0.4, 2, 0.42),
    // new NoiseMesh(mx1, 155, 3, 0.015, 8, 1, 1, 0.7, Color.valueOf("CD9D3C"), Color.valueOf("FF9D3C"), 8, 1, 0.5, 0.42),
    // new NoiseMesh(mx1, 5, 4, 0.12, 5, 0.5, 1, 0.5, Color.valueOf("8A6969"), Color.valueOf("715555"), 5, 0.5, 1, 0.42)
// ));
mx1.meshLoader = prov(() => new MultiMesh(
    new NoiseMesh(mx1, 70, 5, 0.406, 6, 0.35, 12, 0.25, Color.valueOf("8A5B5B"), Color.valueOf("B96F3D"), 4, 0.4, 8, 0.3),
    new NoiseMesh(mx1, 66, 5, 0.611, 6, 0.3, 14, 0.28, Color.valueOf("eba768"), Color.valueOf("715555"), 4, 0.4, 3, 0.42),
    new NoiseMesh(mx1, 58, 4.5, 0.502, 5, 0.3, 12, 0.26, Color.valueOf("856A6A"), Color.valueOf("9E6C6C"), 5, 0.4, 5, 0.28),
    new NoiseMesh(mx1, 155, 3, 0.015, 8, 0.25, 8, 0.2, Color.valueOf("CD9D3C"), Color.valueOf("FF9D3C"), 8, 1, 4, 0.3),
    new NoiseMesh(mx1, 5, 4, 0.12, 7, 0.3, 10, 0.2, Color.valueOf("8A6969"), Color.valueOf("715555"), 6, 0.5, 5, 0.25),
    //new SunMesh(mx1, 5, 5, 0.3, 0.26, 1, 0.2, Color.valueOf("FFB570") )
));
mx1.cloudMeshLoader = prov(() => new MultiMesh(
    new HexSkyMesh(mx1, 574, 0.4, 0.052, 4, Color.valueOf("D7723080"), 7, 0.42, 0.9, 0.43),
    new HexSkyMesh(mx1, 72, 0.6, 0.1, 4, Color.valueOf("FF975180"), 7, 0.42, 0.9, 0.42)
));
mx1.parent = mx;
mx1.solarSystem = mx;
mx1.tidalLock = false;
mx1.alwaysUnlocked = true;
mx1.clearSectorOnLose = true;
mx1.showRtsAIRule = false;
mx1.allowCampaignRules = true;
// mx1.campaignRuleDefaults.fog = true;
// mx1.campaignRuleDefaults.showSpawns = true;
// mx1.campaignRuleDefaults.rtsAI = true;
mx1.visible = true;
mx1.drawOrbit = true;
mx1.accessible = true;
mx1.hasAtmosphere = true;
mx1.updateLighting = true;
mx1.allowLaunchToNumbered = false;
mx1.allowSectorInvasion = true;
// mx1.allowWaveSimulation = true;
mx1.bloom = false;
/*mx1.allowWaves = true;*/
/*mx1.allowLegacyLaunchPads = true;*/
mx1.allowLaunchSchematics = true;
/*mx1.enemyCoreSpawnReplace = true;*/
/*mx1.allowLaunchLoadout = true;*/
mx1.lightSrcTo = 0.5;
mx1.lightDstFrom = 0.1;
mx1.atmosphereRadIn = 0.01;
mx1.atmosphereRadOut = 0.3;
mx1.orbitRadius = 34;
mx1.orbitTime = 1200 * 60;
mx1.orbitSpacing = 2;
mx1.rotateTime = 1200 * 60;
mx1.atmosphereColor = Color.valueOf("f07218");
mx1.lightColor = Color.valueOf("FF9D3C50");
mx1.landCloudColor = Color.valueOf("ed6542");
mx1.iconColor = Color.valueOf("CD9D3C");
mx1.radius = 0.63;
mx1.minZoom = 1;
mx1.maxZoom = 6;
mx1.totalRadius += 3;
//mx1.launchMusic = "Music.game4";
//mx1.sectorSeed = 0;
// mx1.launchCapacityMultiplier = 0.1;
mx1.startSector = 74;
//mx1.sectorSize = 2.6;
//mx1.seed = 0;
//mx1.heightScl = 1;
mx1.localizedName = "[#CD9D3C]MXrd-01";
exports.mx1 = mx1;

const mx2 = new Planet("mx2", mx, 3, 1);
mx2.generator = new SerpuloPlanetGenerator();
mx2.cloudMeshLoader = prov(() => new MultiMesh(
    new HexSkyMesh(mx2, 33, -6, 0.02, 7.7, Color.valueOf("ADC9C2"), 9.7, 0.5, 0.88, 0.42),
    new HexSkyMesh(mx2, 44, 1.6, 0.09, 7.9, Color.valueOf("B08A85"), 6.5, 0.5, 0.91, 0.42),
    new HexSkyMesh(mx2, 64, -1.2, 0.13, 7.8, Color.valueOf("9B766E"), 7, 0.5, 0.82, 0.44),
    new HexSkyMesh(mx2, 24, 2, 0.21, 7.9, Color.valueOf("267392"), 5, 0.42, 0.42, 0.42),
    new HexSkyMesh(mx2, 72, -1, 0.06, 7.8, Color.valueOf("BDB8D7"), 9, 0.6, 0.66, 0.5),
    new HexSkyMesh(mx2, 4, 4, 0.08, 7.7, Color.valueOf("AE968E"), 7.8, 0.54, 0.56, 0.46),
    new HexSkyMesh(mx2, 11, -1.9, 0.05, 7.6, Color.valueOf("1380A9"), 6.6, 0.79, 0.48, 0.48),
    new HexSkyMesh(mx2, 87, -2.5, 0.14, 7.8, Color.valueOf("B5D5CF"), 8.2, 0.55, 0.7, 0.4),
    new HexSkyMesh(mx2, 92, 3.2, 0.08, 7.9, Color.valueOf("C49A95"), 7.0, 0.48, 0.8, 0.45),
    new HexSkyMesh(mx2, 15, -0.8, 0.17, 7.5, Color.valueOf("8A6A63"), 6.8, 0.52, 0.6, 0.5),
    new HexSkyMesh(mx2, 51, 5, 0.04, 7.4, Color.valueOf("2A839F"), 5.5, 0.6, 0.5, 0.38),
    new HexSkyMesh(mx2, 38, -3, 0.23, 7.7, Color.valueOf("A9A3C2"), 8.5, 0.65, 0.72, 0.46),
    new HexSkyMesh(mx2, 29, 1.2, 0.12, 7.9, Color.valueOf("BCAAA3"), 7.2, 0.5, 0.6, 0.43),
    new HexSkyMesh(mx2, 63, -2.1, 0.07, 7.8, Color.valueOf("1F6B8F"), 6.0, 0.7, 0.5, 0.41),
    new HexSkyMesh(mx2, 79, 2.8, 0.11, 7.6, Color.valueOf("D3D8DD"), 9.5, 0.45, 0.9, 0.44),
    new HexSkyMesh(mx2, 8, -4, 0.24, 7.5, Color.valueOf("5C5278"), 5.8, 0.58, 0.55, 0.39),
    new HexSkyMesh(mx2, 56, 1.9, 0.05, 7.7, Color.valueOf("8FCCC4"), 8.8, 0.5, 0.78, 0.42)
));
mx2.meshLoader = prov(() => new MultiMesh(
    new NoiseMesh(mx2, 101, 5, 2.0, 3, 0.35, 18, 0.3, Color.valueOf("1380A9"), Color.valueOf("4486A4"), 3, 0.4, 2, 0.4),
    new NoiseMesh(mx2, 102, 5, 2.2, 4, 0.3, 16, 0.25, Color.valueOf("CDD3D6"), Color.valueOf("D6B9AC"), 3, 0.4, 2, 0.4),
    new NoiseMesh(mx2, 103, 5, 1.8, 3, 0.4, 20, 0.35, Color.valueOf("5D5971"), Color.valueOf("334166"), 3, 0.4, 2, 0.4),
    new NoiseMesh(mx2, 104, 5, 2.5, 3, 0.3, 15, 0.28, Color.valueOf("B4B2CC"), Color.valueOf("83C0D4"), 3, 0.4, 2, 0.4),
    new NoiseMesh(mx2, 105, 5, 1.5, 4, 0.35, 22, 0.32, Color.valueOf("929081"), Color.valueOf("0F6294"), 3, 0.4, 2, 0.4)
));
mx2.parent = mx;
mx2.solarSystem = mx;
mx2.tidalLock = false;
mx2.bloom = false;
mx2.accessible = false;
mx2.alwaysUnlocked = false;
mx2.visible = mx2.drawOrbit = mx2.updateLighting = mx2.hasAtmosphere = true;
mx2.radius = 3;
mx2.orbitRadius = 86;
mx2.orbitTime = 9000*60;
mx2.orbitSpacing = 2.2;
mx2.rotateTime = 60*60;
mx2.atmosphereRadIn = 0.1;
mx2.atmosphereRadOut = 0.9;
mx2.lightDstFrom = 0.1;
mx2.lightSrcTo = 0.6;
mx2.atmosphereColor = Color.valueOf("9B766E");
mx2.lightColor = Color.valueOf("6964AC50");
mx2.landCloudColor = Color.valueOf("EFDDD8");
mx2.minZoom = 1;
mx2.maxZoom = 6;
mx2.totalRadius += 3;
mx2.localizedName = "[#6964AC]MXrd-02";
exports.mx2 = mx2;

// const mx3 = new Planet("mx3", mx2, 1, 3);
// mx2.generator = new SerpuloPlanetGenerator();
// // 设置行星视觉属性（大气颜色等，但用户说其余不需，故只设必要项）
// Object.assign(mx3, {
    // // 基础属性
    // solarSystem: mx,
    // parent: mx2,
    // accessible: false, // 不可登陆，仅作为视觉行星
    // visible: true,
    // bloom: false,
    // hasAtmosphere: true,
    // atmosphereRadIn: 0.02,
    // atmosphereRadOut: 0.3,
    // atmosphereColor: Color.valueOf("BAC9DD"), // 主大气颜色
    // lightColor: Color.valueOf("AABDD950"),
    // lightDstFrom: 0.1,
    // lightSrcTo: 0.5
    // minZoom: 1,
    // maxZoom: 6,
    // radius: 1,
    // orbitRadius: 8,
    // orbitTime: 2000 * 60,
    // orbitSpacing: 1,
    // rotateTime: 120 * 60,
    // // 光照颜色等可省略
// });

// mx3.cloudMeshLoader = prov(() => new MultiMesh(
    // new HexSkyMesh(mx3, 101, -1.2, 0.02, 7.5, Color.valueOf("88A1C9"), 8, 0.5, 0.8, 0.4),
    // new HexSkyMesh(mx3, 102, 0.5, 0.05, 7.6, Color.valueOf("AABDD9"), 9, 0.6, 0.85, 0.45),
    // new HexSkyMesh(mx3, 103, 2.0, 0.08, 7.7, Color.valueOf("BAC9DD"), 10, 0.7, 0.9, 0.5),
    // new HexSkyMesh(mx3, 104, -0.8, 0.03, 7.5, Color.valueOf("8DA9C5"), 7, 0.55, 0.78, 0.42),
    // new HexSkyMesh(mx3, 105, 1.2, 0.06, 7.6, Color.valueOf("B2C4DD"), 8.5, 0.65, 0.88, 0.48),
    // new HexSkyMesh(mx3, 106, -2.5, 0.01, 7.8, Color.valueOf("7F9ABF"), 9, 0.45, 0.7, 0.38)
// ));

// exports.mx3 = mx3;

const mx3 = new Planet("mx3", mx, 1, 3.5);
mx3.generator = new SerpuloPlanetGenerator();
//mx3.defaultAttributes = a;
mx3.cloudMeshLoader = prov(() => new MultiMesh(
    new HexSkyMesh(mx3, 77, -1.2, 0.08, 6.5, Color.valueOf("88A1C980"), 5, 0.5, 0.8, 0.4),
    new HexSkyMesh(mx3, 65, 0.5, 0.13, 6.6, Color.valueOf("AABDD980"), 6, 0.6, 0.85, 0.45),
    new HexSkyMesh(mx3, 24, 2.0, 0.086, 6.7, Color.valueOf("BAC9DD80"), 7, 0.7, 0.9, 0.5),
));
mx3.meshLoader = prov(() => new MultiMesh(
	new HexMesh(mx3, 7),
    /** Two-color variant. */
    // public NoiseMesh(Planet planet, int seed, int divisions, float radius, int octaves, float persistence, float scale, float mag, Color color1, Color color2, int coct, float cper, float cscl, float cthresh){
    // new NoiseMesh(mx3, 6, 6, 0.9, 8, 0.6, 10, 0.2, Color.valueOf("87ADD7"), Color.valueOf("5578A1"), 8, 0.6, 4.5, 0.42),/*海洋*/
    // new NoiseMesh(mx3, 71, 6, 0.92, 10, 0.5, 12, 0.1, Color.valueOf("737378"), Color.valueOf("52525C"), 10, 0.5, 9, 0.42),/*陆地*/
    // new NoiseMesh(mx3, 99, 6, 0.92, 10, 0.5, 0.5, 0.5, Color.valueOf("52525C"), Color.valueOf("737378"), 10, 0.5, 0.5, 0.42),/*陆地*/
));
mx3.parent = mx2;
mx3.solarSystem = mx;
mx3.showRtsAIRule = mx3.allowLaunchToNumbered = mx3.bloom = mx3.tidalLock = false;
mx3.alwaysUnlocked = true;
mx3.clearSectorOnLose = mx3.visible = mx3.drawOrbit = mx3.accessible = mx3.hasAtmosphere = mx3.updateLighting = mx3.allowSectorInvasion = mx3.allowWaves = mx3.allowLegacyLaunchPads = mx3.allowLaunchSchematics = mx3.enemyCoreSpawnReplace = mx3.allowLaunchLoadout = true;
mx3.lightSrcTo = 0.3;
mx3.lightDstFrom = 0.1;
mx3.atmosphereRadIn = 0.01;
mx3.atmosphereRadOut = 0.5;
mx3.orbitRadius = 16;
mx3.orbitTime = 600 * 60;
mx3.orbitSpacing = 1.6;
mx3.rotateTime = 2000 * 60;
mx3.atmosphereColor = Color.valueOf("5297CD30");
mx3.lightColor = Color.valueOf("509ACC50");
mx3.landCloudColor = Color.valueOf("7A94AD7F");
mx3.iconColor = Color.valueOf("5297CD");
mx3.radius = 1;
mx3.minZoom = 1;
mx3.maxZoom = 6;
mx3.startSector = 0;
mx3.sectorSeed = 2;
//mx3.waveTeam = Team.blue;
mx3.localizedName = "[#5297CD]MXrd-03";
exports.mx3 = mx3;