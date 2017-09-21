package com.hyd.steamgss.service;

import com.hyd.steamgss.fx.FxAlert;
import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.ui.GameConfigurationPane;
import com.hyd.steamgss.utils.Pth;
import com.hyd.steamgss.utils.Str;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
                c -> ConfigPersistentService.save();
        configurations.addListener(listener);
    }

    public static GameConfiguration getCurrentConfiguration() {
        return currentConfiguration;
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

    public static void backupCurrent() {
        if (currentConfiguration == null) {
            FxAlert.error("没有选择要备份的游戏");
        }

        String backupPath = currentConfiguration.getBackupPath();
        String savingPath = currentConfiguration.getLocalSavingPath();

        if (Str.isBlank(backupPath)) {
            FxAlert.error("没有选择备份目录");
            return;
        }

        if (Str.isBlank(savingPath)) {
            FxAlert.error("没有选择存档目录");
            return;
        }

        if (!Pth.fileExists(savingPath)) {
            FxAlert.error("存档没有找到");
            return;
        }

        if (!Pth.getOrCreateDir(backupPath)) {
            FxAlert.error("备份目录无法创建");
            return;
        }

        try {
            Pth.copyDir(savingPath, backupPath);
            FxAlert.info("备份已完成。");
        } catch (IOException e) {
            FxAlert.error("备份失败：" + e);
        }
    }

    public static void restoreCurrent() {
        if (currentConfiguration == null) {
            FxAlert.error("没有选择要备份的游戏");
        }

        String backupPath = currentConfiguration.getBackupPath();
        String savingPath = currentConfiguration.getLocalSavingPath();

        if (Str.isBlank(backupPath)) {
            FxAlert.error("没有选择备份目录");
            return;
        }

        if (Str.isBlank(savingPath)) {
            FxAlert.error("没有选择存档目录");
            return;
        }

        if (!Pth.fileExists(backupPath)) {
            FxAlert.error("备份没有找到");
            return;
        }

        if (!Pth.getOrCreateDir(savingPath)) {
            FxAlert.error("存档目录无法创建");
            return;
        }

        try {
            Pth.copyDir(backupPath, savingPath);
            FxAlert.info("存档已还原。");
        } catch (IOException e) {
            FxAlert.error("还原失败：" + e);
        }
    }
}
