package com.hyd.steamgss.items;

import com.hyd.steamgss.App;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author yiding_he
 */
public class GameConfiguration {

    private SimpleStringProperty name = new SimpleStringProperty("");

    private SimpleStringProperty localSavingPath = new SimpleStringProperty("");

    private SimpleStringProperty backupPath = new SimpleStringProperty("");

    public GameConfiguration() {
    }

    private GameConfiguration(String name, String localSavingPath, String backupPath) {
        this.name.set(name);
        this.localSavingPath.set(localSavingPath);
        this.backupPath.set(backupPath);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public GameConfiguration clone(){
        return new GameConfiguration(this.name.get(), this.localSavingPath.get(), this.backupPath.get());
    }

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

    ////////////////////////////////////////////////////////////////////////////////

    public void escapePath() {
        this.localSavingPath.set(this.localSavingPath.get().replace(App.USER_HOME, "%USERPROFILE%"));
        this.backupPath.set(this.backupPath.get().replace(App.USER_HOME, "%USERPROFILE%"));
    }

    public void unescapePath() {
        this.localSavingPath.set(this.localSavingPath.get().replace("%USERPROFILE%", App.USER_HOME));
        this.backupPath.set(this.backupPath.get().replace("%USERPROFILE%", App.USER_HOME));
    }
}
