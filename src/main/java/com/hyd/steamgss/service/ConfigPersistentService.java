package com.hyd.steamgss.service;

import com.alibaba.fastjson.JSON;
import com.hyd.steamgss.fx.FxAlert;
import com.hyd.steamgss.items.GameConfiguration;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * @author yiding_he
 */
public class ConfigPersistentService {

    public static List<GameConfiguration> load() {
        Path configPath = Paths.get("config.json");
        if (!Files.exists(configPath)) {
            return Collections.emptyList();
        }

        try {
            String json = new String(Files.readAllBytes(configPath), "UTF-8");
            return JSON.parseArray(json, GameConfiguration.class);
        } catch (Exception e) {
            FxAlert.error("无法读取配置：" + e);
            return Collections.emptyList();
        }
    }

    public static void save() {
        try {
            List<GameConfiguration> configurationList = GameConfigurationService.getConfigurations();
            Path configPath = Paths.get("config.json");
            if (!Files.exists(configPath)) {
                Files.createFile(configPath);
            }
            String json = JSON.toJSONString(configurationList);
            Files.write(configPath, json.getBytes("UTF-8"));
        } catch (Exception e) {
            FxAlert.error("无法保存配置：" + e);
        }
    }
}
