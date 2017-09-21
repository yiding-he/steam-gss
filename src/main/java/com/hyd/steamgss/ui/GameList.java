package com.hyd.steamgss.ui;


import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.service.GameConfigurationService;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * @author yidin
 */
public class GameList extends ListView<GameConfiguration> {

    public GameList() {
        super(GameConfigurationService.getConfigurations());
        setupCellDisplay();
        setupEvents();
    }

    private void setupEvents() {
        this.getSelectionModel().selectedItemProperty().addListener((_ob, _old, _new) -> {
            if (_new != null) {
                GameConfigurationService.showConfiguration(_new);
            } else {
                GameConfigurationService.clearCurrentShowing();
            }
        });

        this.getItems().addListener((ListChangeListener<GameConfiguration>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    GameConfiguration added = c.getAddedSubList().get(0);
                    getSelectionModel().select(added);
                }
            }
        });
    }

    private void setupCellDisplay() {
        setCellFactory(lv -> new ListCell<GameConfiguration>() {
            @Override
            protected void updateItem(GameConfiguration c, boolean empty) {
                super.updateItem(c, empty);
                if (!empty) {
                    setText(c.getName());
                } else {
                    setText(null);
                }
            }
        });
    }
}
