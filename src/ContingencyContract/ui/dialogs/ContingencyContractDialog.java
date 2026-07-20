package ContingencyContract.ui.dialogs;

import arc.Core;
import arc.audio.Music;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Dialog;
import arc.scene.ui.Image;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.graphics.Color;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.gen.Icon;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import ContingencyContract.ui.CCStyles;

import java.lang.reflect.Method;

import ContingencyContract.music.CCBackgroundMusic;

public class ContingencyContractDialog extends BaseDialog{
    /** 背景图片 */
    public static TextureRegion background = Core.atlas.find("lp-#0background");
    /** 合约图标 */
    public static TextureRegion contractIconRegion = Core.atlas.find("lp-#0");
    /** 合约图标大小 */
    public static float contractIconSize = 180;

    /** 主场地图标 */
    public static TextureRegion mainContractIconRegion = Core.atlas.find("lp-#0mainContract");
    /** 主场地图标宽高 */
    public static float mainContractButtonWidth = 900;
    public static float mainContractButtonHeight = 430;
    /** 主场地标题 */
    public static TextureRegion mainContractTitleRegion = Core.atlas.find("lp-#0mainContractTitle");
    /** 主场地标题宽高 */
    public static float mainContractTitleWidth = 247;
    public static float mainContractTitleHeight = 100;

    /** 副场地图标 */
    public static TextureRegion subContractIconRegion0 = Core.atlas.find("ranai");
    public static TextureRegion subContractIconRegion1 = Core.atlas.find("ranai");
    public static TextureRegion subContractIconRegion2 = Core.atlas.find("ranai");
    public static TextureRegion subContractIconRegion3 = Core.atlas.find("ranai");
    /** 副场地图标宽高 */
    public static float subContractIconWidth = 256f;
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
    public static float contractTitleWidth = 562.5f;
    public static float contractTitleHeight = 135;

    /** 灰黑字体 */
    public static Color darkGray = Color.valueOf("434041");

    public ContingencyContractDialog(){
        super("", Styles.fullDialog);

        if (titleImage != null) titleImage.remove();

        shouldPause = false;
    }

    @Override
    public Dialog show() {
        refreshBackground();
        buildUI();
        
        if (CCBackgroundMusic.backgroundMusic != null) {
            playBackgroundMusic();
        }
        return super.show();
    }

    private void refreshBackground() {
        if(Core.atlas.has("lp-#0background")){
            setBackground(new TextureRegionDrawable(background));
        }
    }

    private void buildUI() {
        cont.clear();
        cont.setFillParent(true);

        // 退出按钮
        ImageButton exitButton = new ImageButton(Icon.left, Styles.clearTogglei);
        exitButton.clicked(this::hide);
        cont.add(exitButton).size(144, 72).padLeft(6f).padTop(15.5f).top().left();
        cont.row();

        Table contentTable = new Table();

        // 主场地
        ImageButton mainContractButton = new ImageButton(Styles.emptyi);
        mainContractButton.table(t -> {
            t.image(mainContractIconRegion).size(mainContractButtonWidth, mainContractButtonHeight);
            t.row();
            t.image(mainContractTitleRegion).size(mainContractTitleWidth, mainContractTitleHeight).color(darkGray);
        });
        mainContractButton.clicked(() -> {
            Vars.ui.planet.show();
            this.hide();
        });

        // 副场地
        Table subContractTable = new Table();
        subContractTable.top().left();

        ImageButton subContractButton0 = new ImageButton(Styles.emptyi);
        subContractButton0.table(t -> {
            t.image(subContractIconRegion0).size(subContractIconWidth, subContractIconHeight);
            t.row();
            t.add(subContractTitle0).style(CCStyles.DefaultLabel).right().fontScale(subContractTitleFontSize).color(darkGray);
        });
        subContractButton0.clicked(() -> {
        });
        subContractTable.add(subContractButton0).size(subContractIconWidth, subContractIconHeight).pad(16f);
        subContractTable.row();

        ImageButton subContractButton1 = new ImageButton(Styles.emptyi);
        subContractButton1.table(t -> {
            t.image(subContractIconRegion1).size(subContractIconWidth, subContractIconHeight);
            t.row();
            t.add(subContractTitle1).style(CCStyles.DefaultLabel).right().fontScale(subContractTitleFontSize).color(darkGray);
        });
        subContractButton1.clicked(() -> {
        });
        subContractTable.add(subContractButton1).size(subContractIconWidth, subContractIconHeight).pad(16f);
        subContractTable.row();

        ImageButton subContractButton2 = new ImageButton(Styles.emptyi);
        subContractButton2.table(t -> {
            t.image(subContractIconRegion2).size(subContractIconWidth, subContractIconHeight);
            t.row();
            t.add(subContractTitle2).style(CCStyles.DefaultLabel).right().fontScale(subContractTitleFontSize).color(darkGray);
        });
        subContractButton2.clicked(() -> {
        });
        subContractTable.add(subContractButton2).size(subContractIconWidth, subContractIconHeight).pad(16f);
        subContractTable.row();

        ImageButton subContractButton3 = new ImageButton(Styles.emptyi);
        subContractButton3.table(t -> {
            t.image(subContractIconRegion3).size(subContractIconWidth, subContractIconHeight);
            t.row();
            t.add(subContractTitle3).style(CCStyles.DefaultLabel).right().fontScale(subContractTitleFontSize).color(darkGray);
        });
        subContractButton3.clicked(() -> {
        });
        subContractTable.add(subContractButton3).size(subContractIconWidth, subContractIconHeight).pad(16f);
        subContractTable.row();

        Table leftColumn = new Table();
        leftColumn.top().left();

        // 作战回顾
        ImageButton operationsReviewButton = new ImageButton(Styles.emptyi);
        operationsReviewButton.table(t -> {
            t.image(operationsReviewIconRegion).size(operationsReviewIconWidth, operationsReviewIconHeight);
            t.row();
            t.add(operationsReviewTitle).style(CCStyles.DefaultLabel).left().padTop(8f).fontScale(operationsReviewTitleFontSize).color(darkGray);
        });
        operationsReviewButton.clicked(() -> {});
        leftColumn.add(operationsReviewButton).size(operationsReviewIconWidth, operationsReviewIconHeight + 30f).padBottom(16f);
        leftColumn.row();

        // 往期作战
        ImageButton pastOperationsButton = new ImageButton(Styles.emptyi);
        pastOperationsButton.table(t -> {
            t.image(pastOperationsIconRegion).size(pastOperationsIconWidth, pastOperationsIconHeight);
            t.row();
            t.add(pastOperationsTitle).style(CCStyles.DefaultLabel).left().padTop(8f).fontScale(pastOperationsTitleFontSize).color(darkGray);
        });
        pastOperationsButton.clicked(() -> {});
        leftColumn.add(pastOperationsButton).size(pastOperationsIconWidth, pastOperationsIconHeight + 30f);

        Table mainRow = new Table();
        mainRow.add(leftColumn).top().left().padRight(12f);
        mainRow.add(mainContractButton).center().growX();
        mainRow.add(subContractTable).top().left();

        contentTable.add(mainRow).center();
        contentTable.row();

        // 合约图标及标题
        Table contractTitleRow = new Table();
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
    }

    private void playBackgroundMusic() {
        Music music = CCBackgroundMusic.backgroundMusic;
        if (music == null) return;

        try {
            Method method = SoundControl.class.getMethod("play", Music.class);
            method.invoke(Vars.control.sound, music);
        } catch (Exception e) {
            Log.err("无法通过SoundControl播放音乐", e);
            music.play();
        }
    }
}