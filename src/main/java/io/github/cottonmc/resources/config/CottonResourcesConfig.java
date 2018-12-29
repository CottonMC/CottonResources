package io.github.cottonmc.resources.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;

import java.util.ArrayList;

@ConfigFile(name="CottonResources")
public class CottonResourcesConfig {

    //TODO: add comment
    public ArrayList<String> enabledItems = new ArrayList<>();

    //TODO: add comment
    public ArrayList<String> enabledBlocks = new ArrayList<>();
}
