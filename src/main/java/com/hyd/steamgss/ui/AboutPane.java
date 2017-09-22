package com.hyd.steamgss.ui;

import com.hyd.steamgss.Icons;
import com.hyd.steamgss.fx.Fx;
import com.hyd.steamgss.fx.FxAlert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * @author yidin
 */
public class AboutPane extends BorderPane {

    public AboutPane() {
        setCenter(Fx.label("About..."));
        setBottom(
                Fx.hbox(
                        Fx.button("Test Error", this::textShowError),
                        new ImageView(Icons.LOADING)
                )
        );
    }

    private void textShowError() {
        FxAlert.error("A test error message.");
    }
}
