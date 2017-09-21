package com.hyd.steamgss.items;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author yiding_he
 */
public class GameConfiguration {

    private SimpleStringProperty name = new SimpleStringProperty();

    private SimpleStringProperty localSavingPath = new SimpleStringProperty();

    private SimpleStringProperty backupPath = new SimpleStringProperty();

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty localSavingPathProperty() {
        return localSavingPath;
    }

    public SimpleStringProperty backupPathProperty() {
        return backupPath;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLocalSavingPath() {
        return localSavingPath.get();
    }

    public void setLocalSavingPath(String localSavingPath) {
        this.localSavingPath.set(localSavingPath);
    }

    public String getBackupPath() {
        return backupPath.get();
    }

    public void setBackupPath(String backupPath) {
        this.backupPath.set(backupPath);
    }
}
