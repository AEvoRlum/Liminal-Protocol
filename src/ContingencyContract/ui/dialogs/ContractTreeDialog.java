package ContingencyContract.ui.dialogs;

import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Dialog;
import arc.scene.ui.ImageButton;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

public class ContractTreeDialog extends BaseDialog {
    /** 分数 */
    public static int score = 325;
    /** 背景 */
    public TextureRegion background;

    public ContractTreeDialog(TextureRegion backgroundRegion) {
        super("", Styles.fullDialog);

        if (titleImage != null) titleImage.remove();

        shouldPause = false;

        this.background = backgroundRegion;
    }

    @Override
    public Dialog show() {
        setBackground(new TextureRegionDrawable(background));
        BuildUI();

        return super.show();
    }

    private void BuildUI() {
        cont.clear();
        cont.setFillParent(true);

        // 退出按钮
        ImageButton exitButton = new ImageButton(Icon.left, Styles.clearTogglei);
        exitButton.clicked(() -> {
            new ContingencyContractDialog().show(false);
            this.hide();
        });
        cont.add(exitButton).size(144, 72).padLeft(6f).padTop(15.5f).top().left();
        cont.row();
    }
}
