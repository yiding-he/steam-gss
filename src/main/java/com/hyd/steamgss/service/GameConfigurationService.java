package com.hyd.steamgss.service;

import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.ui.GameConfigurationPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author yiding_he
 */
public class GameConfigurationService {

    private static ObservableList<GameConfiguration>
            configurations = FXCollections.observableArrayList();

    private static GameConfigurationPane gameConfigurationPane;

    public static void setGameConfigurationPane(GameConfigurationPane gameConfigurationPane) {
        GameConfigurationService.gameConfigurationPane = gameConfigurationPane;
    }

    public static ObservableList<GameConfiguration> getConfigurations() {
        return configurations;
    }

    public static void newConfiguration() {
        GameConfiguration gc = new GameConfiguration();
        gc.setName(getNewConfigurationName());

        configurations.add(gc);
    }

    private static String getNewConfigurationName() {
        int counter = 0;
        String name = "新游戏", prefix = name;
        while (configNameExists(name)) {
            counter++;
            name = prefix + counter;
        }
        return name;
    }

    private static boolean configNameExists(String name) {
        return configurations.stream().anyMatch(c -> c.getName().equals(name));
    }

    public static void showConfiguration(GameConfiguration configuration) {
        gameConfigurationPane.showGameConfiguration(configuration);
    }

    public static void clearCurrentShowing() {
        gameConfigurationPane.clear();
    }

    public static void loadConfiguration() {
        configurations.addAll(ConfigPersistentService.load());
    }
}
