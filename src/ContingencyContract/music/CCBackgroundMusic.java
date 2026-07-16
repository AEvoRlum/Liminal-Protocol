package ContingencyContract.music;

import arc.Core;
import arc.audio.Music;
import arc.util.Log;

public class CCBackgroundMusic {
    public static Music backgroundMusic;

    public static void loadMusic() {
        String path = "sounds/laserLargeCharge.ogg";
        if (!Core.files.internal(path).exists()) {
            Log.err("音乐文件不存在: " + path);
            return;
        }
        Core.assets.load(path, Music.class).loaded = m -> {
            backgroundMusic = m;
            backgroundMusic.setLooping(true);
            Log.info("加载成功！");
        };
    }
}
