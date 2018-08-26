package com.hyd.steamgss.service;

import com.hyd.steamgss.fx.Fx;
import com.hyd.steamgss.fx.FxAlert;
import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.utils.Pth;
import com.hyd.steamgss.utils.Str;
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

        for (GameConfiguration configuration : configurations) {
            String backupPath = configuration.getBackupPath();
            String savingPath = configuration.getLocalSavingPath();

            if (Str.isBlank(backupPath)) {
                errors.add("'" + configuration.getName() + "'没有选择备份目录");
                continue;
            }

            if (Str.isBlank(savingPath)) {
                errors.add("'" + configuration.getName() + "'没有选择存档目录");
                continue;
            }

            if (!Pth.fileExists(savingPath)) {
                errors.add("'" + configuration.getName() + "'存档没有找到");
                continue;
            }

            if (!Pth.getOrCreateDir(backupPath)) {
                errors.add("'" + configuration.getName() + "'备份目录无法创建");
                continue;
            }

            availables.add(configuration);
        }

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

    public static void restoreSaving(List<GameConfiguration> configurations) {
        if (configurations == null || configurations.isEmpty()) {
            FxAlert.error("没有选择要备份的游戏");
            return;
        }

        for (GameConfiguration configuration : configurations) {
            String backupPath = configuration.getBackupPath();
            String savingPath = configuration.getLocalSavingPath();

            if (Str.isBlank(backupPath)) {
                FxAlert.error("'" + configuration.getName() + "'没有选择备份目录");
                return;
            }

            if (Str.isBlank(savingPath)) {
                FxAlert.error("'" + configuration.getName() + "'没有选择存档目录");
                return;
            }

            if (!Pth.fileExists(backupPath)) {
                FxAlert.error("'" + configuration.getName() + "'备份没有找到");
                return;
            }

            if (!Pth.getOrCreateDir(savingPath)) {
                FxAlert.error("'" + configuration.getName() + "'存档目录无法创建");
                return;
            }
        }

        ImageView imageView = configurations.size() > 1 ? loadingAllImageView : loadingImageView;
        Runnable task = () -> {

            serviceRunning(true, imageView);

            try {
                for (GameConfiguration configuration : configurations) {
                    String backupPath = configuration.getBackupPath();
                    String savingPath = configuration.getLocalSavingPath();
                    Pth.copyDir(backupPath, savingPath);
                }
                FxAlert.info("存档已还原。");
            } catch (IOException e) {
                FxAlert.error("还原失败：" + e);
            } finally {
                serviceRunning(false, imageView);
            }
        };

        new Thread(task).start();
    }

}
