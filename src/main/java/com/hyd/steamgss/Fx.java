package com.hyd.steamgss;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author yidin
 */
public class Fx {

    public static Button button(String text) {
        return button(text, null);
    }

    public static Button button(String text, Runnable action) {
        Button button = new Button(text);
        if (action != null) {
            button.setOnAction(e -> action.run());
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
}
