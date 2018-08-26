package com.hyd.steamgss.service;

import com.hyd.steamgss.fx.Fx;
import com.hyd.steamgss.fx.FxAlert;
import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.utils.Pth;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yiding_he
 */
public class GameSavingService {

    private static ImageView loadingImageView;

    private static ImageView loadingAllImageView;

    private static Button backupButton;

    private static Button restoreButton;

    public static void setBackupButton(Button backupButton) {
        GameSavingService.backupButton = backupButton;
    }

    public static void setRestoreButton(Button restoreButton) {
        GameSavingService.restoreButton = restoreButton;
    }

    public static void setLoadingAllImageView(ImageView loadingAllImageView) {
        GameSavingService.loadingAllImageView = loadingAllImageView;
    }

    public static void setLoadingImageView(ImageView loadingImageView) {
        GameSavingService.loadingImageView = loadingImageView;
    }

    private static void disableButtons(boolean disable) {
        backupButton.setDisable(disable);
        restoreButton.setDisable(disable);
    }

    private static void serviceRunning(boolean running, ImageView loadingImageView) {
        Fx.ui(() -> {
            loadingImageView.setVisible(running);
            disableButtons(running);
        });
    }

    public static void backupSaving(List<GameConfiguration> configurations) {

        if (configurations == null || configurations.isEmpty()) {
            FxAlert.error("没有选择要备份的游戏");
            return;
        }

        List<String> errors = new ArrayList<>();
        List<GameConfiguration> availables = new ArrayList<>();
        checkConfigurations(configurations, errors, availables);

        ImageView imageView = availables.size() > 1 ? loadingAllImageView : loadingImageView;
        Runnable task = () -> {

            serviceRunning(true, imageView);

            try {
                for (GameConfiguration configuration : availables) {
                    String backupPath = configuration.getBackupPath();
                    String savingPath = configuration.getLocalSavingPath();
                    Pth.copyDir(savingPath, backupPath);
                }
                errors.add(0, "备份已完成。");
                FxAlert.info(String.join("\n", errors));
            } catch (IOException e) {
                FxAlert.error("备份失败：" + e);
            } finally {
                serviceRunning(false, imageView);
            }
        };

        new Thread(task).start();
    }

    private static void checkConfigurations(List<GameConfiguration> configurations, List<String> errors, List<GameConfiguration> availables) {
        for (GameConfiguration configuration : configurations) {
            String validate = configuration.validate();
            if (validate == null) {
                availables.add(configuration);
            } else {
                errors.add(validate);
            }
        }
    }

    public static void restoreSaving(List<GameConfiguration> configurations) {
        if (configurations == null || configurations.isEmpty()) {
            FxAlert.error("没有选择要备份的游戏");
            return;
        }

        List<String> errors = new ArrayList<>();
        List<GameConfiguration> availables = new ArrayList<>();
        checkConfigurations(configurations, errors, availables);

        ImageView imageView = configurations.size() > 1 ? loadingAllImageView : loadingImageView;
        Runnable task = () -> {

            serviceRunning(true, imageView);

            try {
                for (GameConfiguration configuration : availables) {
                    String backupPath = configuration.getBackupPath();
                    String savingPath = configuration.getLocalSavingPath();
                    Pth.copyDir(backupPath, savingPath);
                }
                errors.add(0, "存档已还原。");
                FxAlert.info(String.join("\n", errors));
            } catch (IOException e) {
                FxAlert.error("还原失败：" + e);
            } finally {
                serviceRunning(false, imageView);
            }
        };

        new Thread(task).start();
    }

}
