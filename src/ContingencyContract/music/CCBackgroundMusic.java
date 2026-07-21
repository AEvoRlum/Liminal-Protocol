package ContingencyContract.music;

import arc.files.Fi;
import arc.audio.Music;
import arc.util.Log;
import mindustry.Vars;

public class CCBackgroundMusic {
    public static Music backgroundMusic;
    private static boolean loaded = false;

    public static void loadMusic() {
        if (loaded) return;
        
        String basePath = "sounds/laserLargeCharge";
        String[] extensions = {".ogg", ".mp3"};
        String filePath = null;
        
        for (String ext : extensions) {
            Fi f = Vars.tree.get(basePath + ext);
            if (f.exists()) {
                filePath = basePath + ext;
                break;
            }
        }
        
        if (filePath == null) {
            Log.err("CCBackgroundMusic: music file not found: " + basePath + " (tried .ogg, .mp3)");
            return;
        }

        try {
            arc.Core.assets.load(filePath, Music.class);
            arc.Core.assets.finishLoadingAsset(filePath);
            backgroundMusic = arc.Core.assets.get(filePath, Music.class);
            backgroundMusic.setLooping(true);
            loaded = true;
        } catch (Exception e) {
            Log.err("CCBackgroundMusic: music load failed: " + filePath, e);
            backgroundMusic = null;
        }
    }

    public static void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
            backgroundMusic = null;
        }
        loaded = false;
    }
}