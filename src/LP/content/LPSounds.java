package LP.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import arc.util.Log;
import mindustry.Vars;

import java.lang.reflect.Field;

public class LPSounds {
    public static Sound 
        acceleratorCharge, acceleratorLaunch, acceleratorLightning1, airCrushLarge1,
        airCrushLarge2, airCrushMedium3, airCrushSmall2, antiMaterialRifleCharge, antiMaterialRifleShot, 
        artilleryHeavyShot, axisFlashlight, beamLargeCharge, beamLargeShot1, beamLargeShot2,
        blasterShot1, blasterShot2, blastShockwave, blockExplode1, blockExplode1Alt, 
        blockExplode2, blockExplode2Alt, blockExplode3, blockExplodeElectric, blockExplodeElectricBig,
        blockExplodeExplosive, blockExplodeExplosiveAlt, blockExplodeFlammable, blockExplodeWall, 
        bombEmpHit, bombHit, bombPlasmaHit, cannonLargeHit, cannonLargeShot, champbump, chargeCorvus, 
        chargeEmp, chargeLancer, chargePlasma, chargeRail1, chargeRail2, chargeSubsonic, chargeVela,
        electricBlockerBreak, electricBlockerBreakLoud, energyExplosion, energyHit, everspace2, 
        explosionAfflict, explosionArtilleryShockBig, explosionbig, explosionCleroi, explosionCrawler, 
        explosionDull, explosionHit, explosionMortar, explosionNavanax, explosionNuke, explosionQuad, 
        helicopterCrush, hitDefence, hitRepelback, largeBeam, laser2, laser3, laser4, laser5, laserLargeCharge, 
        laserLargeShot1, laserLargeShot2, laserShot, launch, loopBio, loopDifferential, loopElixir, loopNecroplasm, 
        loopPressureGenerator, loopRadarPulse, loopRegen, loopThoriumReactor, loopThruster, loudlyBlasterShot1, 
        martianCrash, mechDestroyed, mechLargeFootsteps, mechMediumFootsteps, mechStepHeavy, mgsvExplosion, 
        plasmaShoot, plasmaShoot1, plasmaShoot2, railGunBlast, railShot1, railShot2, ramielblast, shield, 
        shockBulletByte, shockBulletSyntax, shockwaveTower, shootAfflict, shootArc, shootArtillerySapBig, shootAvert, 
        shootBang, shootBeam, shootBeamPlasma, shootBeamPlasmaSmall, shootBlaster1, shootBlaster2, shootBlaster3, 
        shootBulwark, shootCoil1, shootCoil2, shootCoil3, shootCollaris, shootDefence, shootDiffuse, shootElectric, 
        shootEmp, shootFlak1, shootFlak2, shootFlak3, shootFlak4, shootForeshadow, shootGattling, shootGauss1, shootGauss2, 
        shootGauss3, shootHorizon, shootImpale, shootLancer, shootLaserBeam, shootMachinegun, shootMatrix, 
        shootMissiles, shootMortar, shootNavanax, shootNecroplasm, shootOmura, shootPierce, shootPlasma, shootPulse1, 
        shootPulse2, shootPulse3, shootPulse4, shootRailgun1, shootRailgun2, shootRailgun3, shootRancor, shootRepelback, 
        shootRipple, shootScepterSecondary, shootSegment, shootSmite, shootSnowWhite, shootSubsonic, shootSupernova, 
        shootToxopidShotgun, shootVulcanPlasma, sinusblast, sinusblast2, strawberrydeath, tankDestroyed, tankDestroyed2, 
        termignisPierceArmor, termignisShoot, weirdblast, writeIn
    ;

    public static void load() {
            try {
                for (Field field : LPSounds.class.getFields()) {
                    if (field.getType().equals(Sound.class)) {
                        field.set(null, loadSound(field.getName()));
                    }
                }
            } catch (IllegalAccessException e) {
                Log.err(e);
            }
        }

        private static Sound loadSound(String soundName) {
            if (Vars.headless) {
                return new Sound();
            }
            String path = "sounds/" + soundName;
            String filePath = Vars.tree.get(path + ".ogg").exists() ? path + ".ogg" : path + ".mp3";

            Sound sound = new Sound();
            AssetDescriptor<?> desc = Core.assets.load(filePath, Sound.class, new SoundLoader.SoundParameter(sound));
            desc.errored = Throwable::printStackTrace;
            return sound;
        }
}
