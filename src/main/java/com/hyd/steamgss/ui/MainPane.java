package com.hyd.steamgss.ui;

import com.hyd.steamgss.Fx;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author yidin
 */
public class MainPane extends BorderPane {

    public MainPane() {
        setCenter(createCenter());
        setBottom(new BottomPane());
    }

    private SplitPane createCenter() {
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(
                createLeft(),
                new GameConfigurationPane()
        );
        return splitPane;
    }

    private VBox createLeft() {
        GameList gameList = new GameList();
        VBox.setVgrow(gameList, Priority.ALWAYS);

        HBox hBox = actionButtonsBar();
        VBox vBox = new VBox(10,
                new Label("游戏列表："), gameList, hBox);

        vBox.maxWidthProperty().bind(hBox.minWidthProperty());
        vBox.setPadding(new Insets(10));
        return vBox;
    }

    private HBox actionButtonsBar() {
        HBox hBox = new HBox(10,
                Fx.button("备份存档(_B)..."),
                Fx.button("还原存档(_R)...")
        );
        hBox.setMinWidth(USE_PREF_SIZE);
        return hBox;
    }
}