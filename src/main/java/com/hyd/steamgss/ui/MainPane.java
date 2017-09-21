package com.hyd.steamgss.ui;

import com.hyd.steamgss.fx.Fx;
import com.hyd.steamgss.service.GameConfigurationService;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
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
                createRight()
        );
        return splitPane;
    }

    private Parent createRight() {
        GameConfigurationPane confPane = new GameConfigurationPane();
        VBox vbox = Fx.vbox(confPane, configButtonBar());
        VBox.setVgrow(confPane, Priority.ALWAYS);
        return vbox;
    }

    private HBox configButtonBar() {
        HBox hbox = Fx.hbox(
                Fx.button("游戏存档备份(_B)"),
                Fx.button("游戏存档恢复(_R)")
        );
        hbox.setPadding(new Insets(0));
        return hbox;
    }

    private VBox createLeft() {
        GameList gameList = new GameList();
        VBox.setVgrow(gameList, Priority.ALWAYS);

        HBox hBox = gameListButtonsBar();
        VBox vBox = new VBox(10,
                new Label("游戏列表："), gameList, hBox);

        vBox.maxWidthProperty().bind(hBox.minWidthProperty());
        vBox.setPadding(new Insets(10));
        return vBox;
    }

    private HBox gameListButtonsBar() {
        HBox hBox = new HBox(10,
                Fx.button("添加配置(_A)", this::newConfiguration),
                Fx.button("删除(_D)", this::deleteConfiguration)
        );
        hBox.setMinWidth(USE_PREF_SIZE);
        return hBox;
    }

    private void deleteConfiguration() {
        GameConfigurationService.deleteCurrentConfiguration();
    }

    private void newConfiguration() {
        GameConfigurationService.newConfiguration();
    }
}
