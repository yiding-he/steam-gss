package com.hyd.steamgss;

import com.hyd.steamgss.service.GameConfigurationService;

/**
 * @author yidin
 */
public class App {

    public static final String NAME = "Steam 存档管家";

    public static final String USER_HOME = System.getProperty("user.home");

    public static void init() {
        GameConfigurationService.loadConfiguration();
    }
}
