package com.hyd.steamgss.service;

import com.hyd.steamgss.fx.FxAlert;
import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.ui.GameConfigurationPane;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * @author yiding_he
 */
public class GameConfigurationService {

    private static ObservableList<GameConfiguration>
            configurations = FXCollections.observableArrayList();

    private static GameConfiguration currentConfiguration;

    private static GameConfigurationPane gameConfigurationPane;

    static {
        ListChangeListener<GameConfiguration> listener =
                c -> ConfigPersistentService.save(configurations);
        configurations.addListener(listener);
    }

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
        currentConfiguration = configuration;
        gameConfigurationPane.showGameConfiguration(configuration);
    }

    public static void clearCurrentShowing() {
        currentConfiguration = null;
        gameConfigurationPane.clear();
    }

    public static void loadConfiguration() {
        configurations.addAll(ConfigPersistentService.load());
    }

    public static void deleteCurrentConfiguration() {
        if (currentConfiguration == null) {
            return;
        }

        String confirm = "确定要删除配置'" + currentConfiguration.getName() + "'吗？";
        if (!FxAlert.confirm(confirm)) {
            return;
        }

        configurations.remove(currentConfiguration);
    }
}
