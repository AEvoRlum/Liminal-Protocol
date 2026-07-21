package ContingencyContract.ui.dialogs;

import arc.Core;
import arc.audio.Music;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Dialog;
import arc.scene.ui.Image;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.scene.event.Touchable;
import arc.util.Log;
import arc.graphics.Color;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.gen.Icon;
import mindustry.Vars;

import ContingencyContract.ui.CCStyles;
import ContingencyContract.math.CCInterp;

import ContingencyContract.music.CCBackgroundMusic;
import static ContingencyContract.ui.dialogs.ContractTreeDialog.score;
import static LP.LPSettings.contingencyContractUISize;

public class ContingencyContractDialog extends BaseDialog {
    public static float size = contingencyContractUISize();
    /** 背景 */
    public static TextureRegion background = Core.atlas.find("lp-#0background");
    /** 合约图标 */
    public static TextureRegion contractIconRegion = Core.atlas.find("lp-#0");
    /** 合约图标大小 */
    public static float contractIconSize = 180 * size;

    /** 主场地图标 */
    public static TextureRegion mainContractIconRegion = Core.atlas.find("lp-#0mainContract");
    /** 主场地图标宽高 */
    public static float mainContractButtonWidth = 900 * size;
    public static float mainContractButtonHeight = 430 * size;
    /** 主场地标题 */
    public static TextureRegion mainContractTitleRegion = Core.atlas.find("lp-#0mainContractTitle");
    /** 主场地标题宽高 */
    public static float mainContractTitleWidth = 222.3f * size;
    public static float mainContractTitleHeight = 90 * size;
    /** 主场地合约分数 */
    public static String mainContractScore = "@MainContractScore";
    /** 主场地合约分数字体大小 */
    public static float mainContractScoreFontSize = 1.6f;

    /** 副场地图标 */
    public static TextureRegion subContractIconRegion0 = Core.atlas.find("ranai");
    public static TextureRegion subContractIconRegion1 = Core.atlas.find("ranai");
    public static TextureRegion subContractIconRegion2 = Core.atlas.find("ranai");
    public static TextureRegion subContractIconRegion3 = Core.atlas.find("ranai");
    /** 副场地图标宽高 */
    public static float subContractIconWidth = 256f * size;
    public static float subContractIconHeight = subContractIconWidth / 3f;
    /** 副场地标题 */
    public static String subContractTitle0 = "@SubContract0";
    public static String subContractTitle1 = "@SubContract1";
    public static String subContractTitle2 = "@SubContract2";
    public static String subContractTitle3 = "@SubContract3";
    /** 副场地标题字体大小 */
    public static float subContractTitleFontSize = 2f;

    /** 作战回顾图标 */
    public static TextureRegion operationsReviewIconRegion = Core.atlas.find("lp-#0operationsReviewIcon");
    /** 作战回顾宽高 */
    public static float operationsReviewIconWidth = subContractIconWidth;
    public static float operationsReviewIconHeight = subContractIconWidth;
    /** 作战回顾标题 */
    public static String operationsReviewTitle = "@OperationsReview";
    /** 作战回顾标题字体大小 */
    public static float operationsReviewTitleFontSize = 2f;

    /** 往期作战图标 */
    public static TextureRegion pastOperationsIconRegion = Core.atlas.find("lp-#0pastOperationsIcon");
    /** 往期作战图标宽高 */
    public static float pastOperationsIconWidth = subContractIconWidth;
    public static float pastOperationsIconHeight = subContractIconWidth / 2f;
    /** 往期作战标题 */
    public static String pastOperationsTitle = "@PastOperations";
    /** 往期作战标题字体大小 */
    public static float pastOperationsTitleFontSize = 2f;

    /** 合约标题 */
    public static TextureRegion contractTitleRegion = Core.atlas.find("lp-#0contractTitle");
    /** 合约标题宽高 */
    public static float contractTitleWidth = 562.5f * size;
    public static float contractTitleHeight = 135 * size;

    /** 灰黑字体 */
    public static Color darkGray = Color.valueOf("434041");

    private boolean isShowing = false;
    private int loopTimer = 0;
    
    private float animationTime = 0f;
    private static final float animationDuration = 1f;
    private boolean isAnimating = false;
    private boolean firstShow = false;
    
    private Table contractTitleRow;
    private Table mainRow;
    private Table contentTable;
    private Table subContractTable;
    private Table leftColumn;
    private Table blackOverlay;

    public ContingencyContractDialog(){
        super("", Styles.fullDialog);

        if (titleImage != null) titleImage.remove();

        shouldPause = false;
    }
    
    public Dialog show(boolean fromMainMenu) {
        this.firstShow = fromMainMenu;
        return show();
    }

    @Override
    public Dialog show() {
        refreshBackground();
        buildUI();
        
        isShowing = true;
        loopTimer = 0;
        
        if (firstShow) {
            animationTime = 0f;
            isAnimating = true;
            applyAnimation(0f);
            
            if (CCBackgroundMusic.backgroundMusic != null) {
                playBackgroundMusic();
            }
        } else {
            isAnimating = false;
            applyAnimation(1f);
        }
        
        return super.show();
    }

    @Override
    public void hide() {
        isShowing = false;
        isAnimating = false;
        super.hide();
    }

    public void exit() {
        stopBackgroundMusic();
        hide();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        
        if (isAnimating) {
            animationTime += delta;
            
            float progress = Math.min(animationTime / animationDuration, 1f);
            
            applyAnimation(progress);
            
            if (animationTime >= animationDuration) {
                isAnimating = false;
                applyAnimation(1f);
            }
        }
        
        if (isShowing && CCBackgroundMusic.backgroundMusic != null) {
            updateMusicVolume();
            
            loopTimer++;
            
            if (loopTimer > 60) {
                loopTimer = 0;
                
                Music music = CCBackgroundMusic.backgroundMusic;
                if (music != null && !music.isPlaying()) {
                    try {
                        music.setPosition(0f);
                        music.play();
                    } catch (Exception e) {
                        Log.err("音乐循环播放失败", e);
                    }
                }
            }
        }
    }

    private void applyAnimation(float progress) {
        float screenWidth = Core.graphics.getWidth();
        float screenHeight = Core.graphics.getHeight();
        
        if (blackOverlay != null) {
            float blackThreshold = 0.35f;
            float blackAlpha;
            if (progress < blackThreshold) {
                blackAlpha = 1f;
            } else {
                float blackProgress = (progress - blackThreshold) / (1f - blackThreshold);
                blackAlpha = 1f - CCInterp.overshootOut.apply(blackProgress);
            }
            blackOverlay.color.a = blackAlpha;
        }

        if (mainRow != null) {
            float alpha;
            alpha = CCInterp.smoothIn.apply(progress);
            mainRow.color.a = alpha;
        }
        
        if (contractTitleRow != null) {
            float titleProgress = CCInterp.smoothOut.apply(progress);
            float startY = -screenHeight - contractTitleRow.getHeight();
            float currentY = startY * (1f - titleProgress);
            contractTitleRow.setTranslation(0, currentY);
        }
        
        if (subContractTable != null) {
            float subProgress = CCInterp.smoothOut.apply(progress);
            float startX = screenWidth + subContractTable.getWidth();
            float currentX = startX * (1f - subProgress);
            subContractTable.setTranslation(currentX, 0);
        }
        
        if (leftColumn != null) {
            float leftProgress = CCInterp.smoothOut.apply(progress);
            float startX = -screenWidth - leftColumn.getWidth();
            float currentX = startX * (1f - leftProgress);
            leftColumn.setTranslation(currentX, 0);
        }
    }

    private void updateMusicVolume() {
        Music music = CCBackgroundMusic.backgroundMusic;
        if (music == null) return;
        
        float musicVol = getMusicVolumeSetting();
        boolean muteMusic = Core.settings.getBool("mutemusic", false);
        
        if (isAnimating) {
            float targetVolume = Vars.state.isMenu() ? 1f : 0.5f;
            float fadeProgress = Math.min(animationTime / animationDuration, 1f);
            float desiredVolume = targetVolume * fadeProgress;
            
            float actualVolume = desiredVolume * musicVol;
            if (muteMusic) {
                actualVolume = 0f;
            }
            
            music.setVolume(actualVolume);
        } else {
            float targetVolume = Vars.state.isMenu() ? 1f : 0.5f;
            float actualVolume = targetVolume * musicVol;
            if (muteMusic) {
                actualVolume = 0f;
            }
            
            music.setVolume(actualVolume);
        }
    }
    
    private float getMusicVolumeSetting() {
        Object value = Core.settings.get("musicvol", 1f);
        if (value instanceof Float) {
            return ((Float)value).floatValue();
        } else if (value instanceof Integer) {
            return ((Integer)value).floatValue() / 100f;
        } else if (value instanceof Number) {
            return ((Number)value).floatValue();
        }
        return 1f;
    }

    private void refreshBackground() {
        setBackground(new TextureRegionDrawable(background));
    }

    private void buildUI() {
        cont.clear();
        cont.setFillParent(true);

        ImageButton exitButton = new ImageButton(Icon.left, Styles.clearTogglei);
        exitButton.clicked(this::exit);
        cont.add(exitButton).size(144, 72).padLeft(6f).padTop(15.5f).top().left();
        cont.row();

        contentTable = new Table();

        ImageButton mainContractButton = new ImageButton(Styles.emptyi);
        mainContractButton.table(t -> {
            t.image(mainContractIconRegion).size(mainContractButtonWidth, mainContractButtonHeight);
            t.row();
            t.table(info -> {
                info.image(mainContractTitleRegion).size(mainContractTitleWidth, mainContractTitleHeight).color(darkGray).left().padRight(454);
                info.add(mainContractScore).style(CCStyles.DefaultLabel).fontScale(mainContractScoreFontSize).color(darkGray).left().padRight(8f);
                if (score >= 0) {
                    info.add(String.valueOf(score)).style(CCStyles.NovecentoWideLabelBig).fontScale(mainContractScoreFontSize).color(darkGray).left();
                } else {
                    info.add("-").style(CCStyles.NovecentoWideLabelBig).fontScale(mainContractScoreFontSize).color(darkGray).left();
                }
            }).left();
        });
        mainContractButton.clicked(() -> {
            new ContractTreeDialog(Core.atlas.find("lp-#0background")).show();
            this.hide();
        });

        subContractTable = new Table();
        subContractTable.top().left();

        ImageButton subContractButton0 = new ImageButton(Styles.emptyi);
        subContractButton0.table(t -> {
            t.image(subContractIconRegion0).size(subContractIconWidth, subContractIconHeight);
            t.row();
            t.add(subContractTitle0).style(CCStyles.DefaultLabel).right().fontScale(subContractTitleFontSize).color(darkGray);
        });
        subContractButton0.clicked(() -> {});
        subContractTable.add(subContractButton0).size(subContractIconWidth, subContractIconHeight).pad(16f);
        subContractTable.row();

        ImageButton subContractButton1 = new ImageButton(Styles.emptyi);
        subContractButton1.table(t -> {
            t.image(subContractIconRegion1).size(subContractIconWidth, subContractIconHeight);
            t.row();
            t.add(subContractTitle1).style(CCStyles.DefaultLabel).right().fontScale(subContractTitleFontSize).color(darkGray);
        });
        subContractButton1.clicked(() -> {});
        subContractTable.add(subContractButton1).size(subContractIconWidth, subContractIconHeight).pad(16f);
        subContractTable.row();

        ImageButton subContractButton2 = new ImageButton(Styles.emptyi);
        subContractButton2.table(t -> {
            t.image(subContractIconRegion2).size(subContractIconWidth, subContractIconHeight);
            t.row();
            t.add(subContractTitle2).style(CCStyles.DefaultLabel).right().fontScale(subContractTitleFontSize).color(darkGray);
        });
        subContractButton2.clicked(() -> {});
        subContractTable.add(subContractButton2).size(subContractIconWidth, subContractIconHeight).pad(16f);
        subContractTable.row();

        ImageButton subContractButton3 = new ImageButton(Styles.emptyi);
        subContractButton3.table(t -> {
            t.image(subContractIconRegion3).size(subContractIconWidth, subContractIconHeight);
            t.row();
            t.add(subContractTitle3).style(CCStyles.DefaultLabel).right().fontScale(subContractTitleFontSize).color(darkGray);
        });
        subContractButton3.clicked(() -> {});
        subContractTable.add(subContractButton3).size(subContractIconWidth, subContractIconHeight).pad(16f);
        subContractTable.row();

        leftColumn = new Table();
        leftColumn.top().left();

        ImageButton operationsReviewButton = new ImageButton(Styles.emptyi);
        operationsReviewButton.table(t -> {
            t.image(operationsReviewIconRegion).size(operationsReviewIconWidth, operationsReviewIconHeight);
            t.row();
            t.add(operationsReviewTitle).style(CCStyles.DefaultLabel).left().padTop(8f).fontScale(operationsReviewTitleFontSize).color(darkGray);
        });
        operationsReviewButton.clicked(() -> {});
        leftColumn.add(operationsReviewButton).size(operationsReviewIconWidth, operationsReviewIconHeight + 30f).padBottom(16f);
        leftColumn.row();

        ImageButton pastOperationsButton = new ImageButton(Styles.emptyi);
        pastOperationsButton.table(t -> {
            t.image(pastOperationsIconRegion).size(pastOperationsIconWidth, pastOperationsIconHeight);
            t.row();
            t.add(pastOperationsTitle).style(CCStyles.DefaultLabel).left().padTop(8f).fontScale(pastOperationsTitleFontSize).color(darkGray);
        });
        pastOperationsButton.clicked(() -> {});
        leftColumn.add(pastOperationsButton).size(pastOperationsIconWidth, pastOperationsIconHeight + 30f);

        mainRow = new Table();
        mainRow.add(leftColumn).top().left().padRight(12f);
        mainRow.add(mainContractButton).center().growX();
        mainRow.add(subContractTable).top().left();
        mainRow.color.a = 0;

        contentTable.add(mainRow).center();
        contentTable.row();

        contractTitleRow = new Table();
        Image contractIconImage = new Image(contractIconRegion);
        contractIconImage.setSize(contractIconSize, contractIconSize);
        contractTitleRow.add(contractIconImage).padRight(8f);

        ImageButton contractTitleButton = new ImageButton(Styles.emptyi);
        contractTitleButton.setChecked(false);
        contractTitleButton.table(t -> {
            t.image(contractTitleRegion).size(contractTitleWidth, contractTitleHeight).color(darkGray);
        });
        contractTitleRow.add(contractTitleButton);
        
        contentTable.add(contractTitleRow).center().padTop(16f);

        cont.add(contentTable).center().expand().fill();
        
        blackOverlay = new Table();
        blackOverlay.setFillParent(true);
        blackOverlay.touchable = Touchable.disabled;
        blackOverlay.add(new Image(Tex.whiteui){{
            setColor(new Color(0, 0, 0, 1));
            setFillParent(true);
        }}).grow();
        blackOverlay.color.a = 0f;
        addChild(blackOverlay);
        blackOverlay.setZIndex(Integer.MAX_VALUE);
    }

    private void playBackgroundMusic() {
        Music music = CCBackgroundMusic.backgroundMusic;
        if (music == null) return;

        try {
            if (!music.isPlaying()) {
                music.setLooping(false);
                music.setPosition(0f);
                music.setVolume(0f);
                music.play();
            }
        } catch (Exception e) {
            Log.err("音乐播放失败", e);
        }
    }

    private void stopBackgroundMusic() {
        Music music = CCBackgroundMusic.backgroundMusic;
        if (music == null) return;

        try {
            if (music.isPlaying()) {
                music.setLooping(false);
                music.stop();
            }
        } catch (Exception e) {
            Log.err("音乐停止失败", e);
        }
    }
}