package me.zach;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.h2.util.IOUtils;
import org.spongepowered.api.Sponge;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static ConfigManager instance = new ConfigManager();

    public static ConfigManager getInstance() {
        return instance;
    }

    private YAMLConfigurationLoader configLoader;
    private Path configFile;
    private CLPixelmon plugin;
    private ConfigurationNode rootNode;

    public void setup(Path configFile, CLPixelmon plugin) {
        this.configFile = configFile;
        this.plugin = plugin;
        try {
            File file = new File(configFile.toAbsolutePath().toString());
            if(!file.exists()) {
                Files.copy(getClass().getResourceAsStream("/default.yml"), configFile);
                System.out.println("Successfully wrote default configurations to config");
            }
            configLoader = YAMLConfigurationLoader.builder().setPath(configFile).build();
            rootNode = configLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public YAMLConfigurationLoader getLoader() {
        return configLoader;
    }

    public ConfigurationNode getNode() {
        return rootNode;
    }

    public void saveNode() {
        try {
            configLoader.save(rootNode);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }



}