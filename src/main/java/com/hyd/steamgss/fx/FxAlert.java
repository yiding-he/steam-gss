package com.hyd.steamgss.fx;

import com.hyd.steamgss.Icons;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * @author yiding_he
 */
public class FxAlert {

    private static ButtonType alert(
            AlertType type, String title, String message, ButtonType... buttons) {

        ButtonType[] result = {null};

        Fx.ui(() -> {
            Alert alert = new Alert(type);
            alert.setContentText(message);
            alert.setHeaderText(null);
            alert.setTitle(title);

            if (buttons != null && buttons.length > 0) {
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(buttons);
            }

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(Icons.LOGO);
            result[0] = alert.showAndWait().orElse(ButtonType.CANCEL);
        });

        return result[0];
    }

    public static void error(String message) {
        alert(AlertType.ERROR, "错误", message, ButtonType.OK);
    }

    public static boolean confirm(String confirm) {
        return alert(AlertType.CONFIRMATION,
                "确认", confirm, ButtonType.YES, ButtonType.NO) == ButtonType.YES;
    }

    public static void info(String message) {
        alert(AlertType.INFORMATION, "消息", message, ButtonType.OK);
    }
}
