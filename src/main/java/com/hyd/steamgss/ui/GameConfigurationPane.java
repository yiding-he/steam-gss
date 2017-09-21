package com.hyd.steamgss.ui;

import com.hyd.steamgss.Fx;
import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.service.GameConfigurationService;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yidin
 */
public class GameConfigurationPane extends GridPane {

    private final TextField txtGameName = Fx.textField();

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

        ///////////////////////////////////////////////

        GridPane.setHgrow(txtGameName, Priority.ALWAYS);
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
    }

    public void clear() {
        this.txtGameName.setText("");
    }
}
