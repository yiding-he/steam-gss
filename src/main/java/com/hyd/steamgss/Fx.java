package com.hyd.steamgss;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * @author yidin
 */
public class Fx {

    public static Button button(String text) {
        return new Button(text);
    }

    public static Label label(String text) {
        Label label = new Label(text);
        label.setMinWidth(Region.USE_PREF_SIZE);
        return label;
    }

    public static TextField textField() {
        return new TextField();
    }
}
