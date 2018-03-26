package com.hyd.steamgss.service;

import com.alibaba.fastjson.JSON;
import com.hyd.steamgss.fx.FxAlert;
import com.hyd.steamgss.items.GameConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yiding_he
 */
public class ConfigPersistentService {

    public static final String CONFIG_PATH =
            System.getProperty("config") != null ? System.getProperty("config") : "config.json";

    public static List<GameConfiguration> load() {
        Path configPath = Paths.get(CONFIG_PATH);
        if (!Files.exists(configPath)) {
            return Collections.emptyList();
        }

        try {
            String json = new String(Files.readAllBytes(configPath), "UTF-8");
            List<GameConfiguration> list = JSON.parseArray(json, GameConfiguration.class);
            list.forEach(GameConfiguration::unescapePath);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            FxAlert.error("无法读取配置：" + e);
            return Collections.emptyList();
        }
    }

    public static void save() {
        try {
            List<GameConfiguration> configurationList = GameConfigurationService.getConfigurations();
            List<GameConfiguration> configurationListClone = cloneAndFix(configurationList);
            saveToFile(configurationListClone);
        } catch (Exception e) {
            e.printStackTrace();
            FxAlert.error("无法保存配置：" + e);
        }
    }

    private static List<GameConfiguration> cloneAndFix(List<GameConfiguration> list) {
        return list.stream()
                .map(GameConfiguration::clone)
                .peek(GameConfiguration::escapePath)
                .collect(Collectors.toList());
    }

    private static void saveToFile(List<GameConfiguration> configurationList) throws IOException {
        Path configPath = Paths.get("config.json");
        if (!Files.exists(configPath)) {
            Files.createFile(configPath);
        }
        String json = JSON.toJSONString(configurationList);
        Files.write(configPath, json.getBytes("UTF-8"));
    }
}
