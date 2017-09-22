package com.hyd.steamgss.service;

import com.hyd.steamgss.fx.Fx;
import com.hyd.steamgss.fx.FxAlert;
import com.hyd.steamgss.items.GameConfiguration;
import com.hyd.steamgss.utils.Pth;
import com.hyd.steamgss.utils.Str;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * @author yiding_he
 */
public class GameSavingService {

    private static ImageView loadingImageView;

    private static Button backupButton;

    private static Button restoreButton;

    public static void setBackupButton(Button backupButton) {
        GameSavingService.backupButton = backupButton;
    }

    public static void setRestoreButton(Button restoreButton) {
        GameSavingService.restoreButton = restoreButton;
    }

    public static void setLoadingImageView(ImageView loadingImageView) {
        GameSavingService.loadingImageView = loadingImageView;
    }

    private static void disableButtons(boolean disable) {
        backupButton.setDisable(disable);
        restoreButton.setDisable(disable);
    }

    private static void serviceRunning(boolean running) {
        Fx.ui(() -> {
            loadingImageView.setVisible(running);
            disableButtons(running);
        });
    }

    public static void backupSaving(GameConfiguration configuration) {
        if (configuration == null) {
            FxAlert.error("没有选择要备份的游戏");
        }

        String backupPath = configuration.getBackupPath();
        String savingPath = configuration.getLocalSavingPath();

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

        Runnable task = () -> {

            serviceRunning(true);

            try {
                Pth.copyDir(savingPath, backupPath);
                FxAlert.info("备份已完成。");
            } catch (IOException e) {
                FxAlert.error("备份失败：" + e);
            } finally {
                serviceRunning(false);
            }
        };

        new Thread(task).start();
    }

    public static void restoreSaving(GameConfiguration configuration) {
        if (configuration == null) {
            FxAlert.error("没有选择要备份的游戏");
        }

        String backupPath = configuration.getBackupPath();
        String savingPath = configuration.getLocalSavingPath();

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

        Runnable task = () -> {

            serviceRunning(true);

            try {
                Pth.copyDir(backupPath, savingPath);
                FxAlert.info("存档已还原。");
            } catch (IOException e) {
                FxAlert.error("还原失败：" + e);
            } finally {
                serviceRunning(false);
            }
        };

        new Thread(task).start();
    }

}
