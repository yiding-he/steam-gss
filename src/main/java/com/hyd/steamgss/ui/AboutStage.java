package com.hyd.steamgss.ui;

import com.hyd.steamgss.App;
import com.hyd.steamgss.Icons;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author yidin
 */
public class AboutStage extends Stage {

    public AboutStage() {
        getIcons().add(Icons.LOGO);
        setTitle("关于 " + App.NAME);
        setScene(new Scene(new AboutPane(), 400, 300));
        setResizable(false);
    }
}
