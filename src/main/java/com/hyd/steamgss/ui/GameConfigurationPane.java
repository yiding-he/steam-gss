package com.hyd.steamgss.ui;

import com.hyd.steamgss.fx.Fx;
import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.service.ConfigPersistentService;
import com.hyd.steamgss.service.GameConfigurationService;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * @author yidin
 */
public class GameConfigurationPane extends GridPane {

    private final TextField txtGameName = Fx.textField();

    private final FileField ffGameSaving = Fx.file();

    private final FileField ffBackup = Fx.file();

    public GameConfigurationPane() {
        GameConfigurationService.setGameConfigurationPane(this);

        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10));

        this.getColumnConstraints().addAll(
                headerCons(),
                fieldCons()
        );

        ///////////////////////////////////////////////

        int row = 0;

        this.add(Fx.label("存档设置", true), 0, row);
        row++;

        this.add(Fx.label("游戏名称："), 0, row);
        this.add(txtGameName, 1, row);
        row++;

        this.add(Fx.label("存档目录："), 0, row);
        this.add(ffGameSaving, 1, row);
        row++;

        this.add(Fx.label("存档备份目录："), 0, row);
        this.add(ffBackup, 1, row);

        ///////////////////////////////////////////////

        GridPane.setHgrow(txtGameName, Priority.ALWAYS);

        setupEvents();
    }

    private void setupEvents() {
        txtGameName.focusedProperty().addListener((_ob, _old, _new) -> {
            if (_old && !_new) {
                GameConfigurationService.getCurrentConfiguration().setName(txtGameName.getText());
                ConfigPersistentService.save();
            }
        });

        ffGameSaving.setOnChosenFileChanged(() -> {
            GameConfigurationService.getCurrentConfiguration()
                    .setLocalSavingPath(ffGameSaving.getChosenFile().getAbsolutePath());
            ConfigPersistentService.save();
        });

        ffBackup.setOnChosenFileChanged(() -> {
            GameConfigurationService.getCurrentConfiguration()
                    .setBackupPath(ffBackup.getChosenFile().getAbsolutePath());
            ConfigPersistentService.save();
        });
    }

    private ColumnConstraints fieldCons() {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPrefWidth(100);
        cc.setHalignment(HPos.LEFT);
        cc.setFillWidth(true);
        cc.setHgrow(Priority.ALWAYS);
        return cc;
    }

    private ColumnConstraints headerCons() {
        return new ColumnConstraints();
    }

    public void showGameConfiguration(GameConfiguration gc) {
        this.txtGameName.setText(gc.getName());
        this.ffGameSaving.setPath(gc.getLocalSavingPath());
        this.ffBackup.setPath(gc.getBackupPath());
    }

    public void clear() {
        this.txtGameName.setText("");
    }
}
