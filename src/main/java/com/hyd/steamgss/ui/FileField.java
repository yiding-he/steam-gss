package com.hyd.steamgss.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * @author yiding_he
 */
public class FileField extends HBox {

    private File chosenFile;

    private TextField textField = new TextField();

    private Button button = new Button("...");

    private Runnable onChosenFileChanged;

    public void setOnChosenFileChanged(Runnable onChosenFileChanged) {
        this.onChosenFileChanged = onChosenFileChanged;
    }

    public FileField() {
        super(10);
        setAlignment(Pos.BASELINE_LEFT);
        getChildren().addAll(textField, button);

        setHgrow(textField, Priority.ALWAYS);

        button.setOnAction(event -> {
            DirectoryChooser dc = new DirectoryChooser();
            if (chosenFile != null) {
                dc.setInitialDirectory(chosenFile);
            }
            dc.setTitle("请选择一个文件夹");
            File file = dc.showDialog(getScene().getWindow());
            if (file != null) {
                updateChosenFile(file);
            }
        });
    }

    private void updateChosenFile(File file) {
        chosenFile = file;
        textField.setText(file.getAbsolutePath());
        if (onChosenFileChanged != null) {
            onChosenFileChanged.run();
        }
    }

    public File getChosenFile() {
        return chosenFile;
    }
}
