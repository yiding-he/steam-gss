package com.hyd.steamgss.ui;

import com.hyd.steamgss.Fx;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * @author yidin
 */
public class GameConfigurationPane extends GridPane {

    private final TextField txtGameName = Fx.textField();

    public GameConfigurationPane() {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10));

        this.getColumnConstraints().addAll(
                headerCons(),
                fieldCons()
        );

        ///////////////////////////////////////////////

        this.add(Fx.label("游戏名称："), 0, 0);
        this.add(txtGameName, 1, 0);

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
}
