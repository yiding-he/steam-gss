package com.hyd.steamgss.ui;

import com.hyd.steamgss.Icons;
import com.hyd.steamgss.fx.Fx;
import com.hyd.steamgss.service.GameConfigurationService;
import com.hyd.steamgss.service.GameSavingService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
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

        Button backupButton = Fx.button("存档备份(_B)", "big-button", GameConfigurationService::backupCurrent);
        Button restoreButton = Fx.button("存档恢复(_R)", "big-button", GameConfigurationService::restoreCurrent);
        ImageView loadingImageView = createLoading();

        GameSavingService.setLoadingImageView(loadingImageView);
        GameSavingService.setBackupButton(backupButton);
        GameSavingService.setRestoreButton(restoreButton);

        HBox hbox = Fx.hbox(backupButton, restoreButton, loadingImageView);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0));
        return hbox;
    }

    private ImageView createLoading() {
        ImageView loadingImageView = new ImageView(Icons.LOADING);
        loadingImageView.setFitWidth(18);
        loadingImageView.setFitHeight(18);
        loadingImageView.setVisible(false);
        return loadingImageView;
    }

    private VBox createLeft() {
        GameList gameList = new GameList();
        VBox.setVgrow(gameList, Priority.ALWAYS);

        HBox gameListButtonsBar = gameListButtonsBar();
        HBox allGamesButtonBar = allGamesButtonBar();
        VBox vBox = new VBox(10,
                new Label("游戏列表："), gameList, gameListButtonsBar, allGamesButtonBar);

        vBox.maxWidthProperty().bind(gameListButtonsBar.minWidthProperty());
        vBox.setPadding(new Insets(10));
        return vBox;
    }

    private HBox allGamesButtonBar() {

        ImageView loadingAll = createLoading();
        GameSavingService.setLoadingAllImageView(loadingAll);

        HBox hBox = new HBox(10,
                Fx.button("全部备份", this::backupAll),
                Fx.button("全部恢复", this::restoreAll),
                loadingAll
        );
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }

    private HBox gameListButtonsBar() {
        HBox hBox = new HBox(10,
                Fx.button("添加配置(_A)", this::newConfiguration),
                Fx.button("删除(_D)", this::deleteConfiguration)
        );
        hBox.setMinWidth(USE_PREF_SIZE);
        return hBox;
    }

    private void restoreAll() {
        GameConfigurationService.restoreAll();
    }

    private void backupAll() {
        GameConfigurationService.backupAll();
    }

    private void deleteConfiguration() {
        GameConfigurationService.deleteCurrentConfiguration();
    }

    private void newConfiguration() {
        GameConfigurationService.newConfiguration();
    }
}
