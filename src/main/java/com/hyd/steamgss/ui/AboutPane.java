package com.hyd.steamgss.ui;

import com.hyd.steamgss.fx.Fx;
import com.hyd.steamgss.fx.FxAlert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * @author yidin
 */
public class AboutPane extends BorderPane {

    public AboutPane() {
        setCenter(Fx.label("About..."));
        setBottom(Fx.button("Test Error", this::textShowError));
    }

    private void textShowError() {
        FxAlert.error("A test error message.");
    }
}
