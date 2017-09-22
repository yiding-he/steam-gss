package com.hyd.steamgss.fx;


import com.hyd.steamgss.ui.FileField;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author yidin
 */
public class Fx {

    public static final Insets PADDING = new Insets(10);

    public static Button button(String text) {
        return button(text, null, null);
    }

    public static Button button(String text, Runnable action) {
        return button(text, null, action);
    }

    public static Button button(String text, String styleClass, Runnable action) {
        Button button = new Button(text);
        if (action != null) {
            button.setOnAction(e -> action.run());
        }
        if (styleClass != null) {
            button.getStyleClass().add(styleClass);
        }
        return button;
    }

    public static Label label(String text) {
        return label(text, false);
    }

    public static Label label(String text, boolean bold) {
        Label label = new Label(text);
        label.setMinWidth(Region.USE_PREF_SIZE);
        if (bold) {
            label.setFont(Font.font(
                    label.getFont().getFamily(),
                    FontWeight.BOLD,
                    label.getFont().getSize()
            ));
        }
        return label;
    }

    public static TextField textField() {
        return new TextField();
    }

    public static HBox hbox(Node... children) {
        HBox hBox = new HBox(10, children);
        hBox.setPadding(PADDING);
        return hBox;
    }

    public static VBox vbox(Node... nodes) {
        VBox vBox = new VBox(10, nodes);
        vBox.setPadding(PADDING);
        return vBox;
    }

    public static FileField file() {
        return new FileField();
    }

    public static void ui(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
