package com.hyd.steamgss;

import com.hyd.steamgss.ui.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author yidin
 */
public class SteamGSSMain extends Application {

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new MainPane(), 800, 600));
        primaryStage.getIcons().add(Icons.LOGO);
        primaryStage.setTitle(App.NAME);
        primaryStage.show();
    }
}
