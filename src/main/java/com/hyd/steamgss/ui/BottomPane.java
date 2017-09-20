package com.hyd.steamgss.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

/**
 * @author yidin
 */
public class BottomPane extends HBox {

    public BottomPane() {
        super(10);
        setPadding(new Insets(5,5,5,10));
        getChildren().add(aboutLink());
    }

    private Hyperlink aboutLink() {
        Hyperlink hyperlink = new Hyperlink("关于...");
        hyperlink.setOnAction(e -> {
            AboutStage aboutStage = new AboutStage();
            aboutStage.initModality(Modality.WINDOW_MODAL);
            aboutStage.initOwner(getScene().getWindow());
            aboutStage.show();
        });
        return hyperlink;
    }
}
