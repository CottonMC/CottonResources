package io.github.cottonmc.resources.type;

import io.github.cottonmc.cotton.commons.CommonBlocks;
import io.github.cottonmc.cotton.commons.CommonItems;
import io.github.cottonmc.cotton.commons.CottonCommons;
import io.github.cottonmc.resources.CottonResources;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Supplier;

public class GenericResourceType implements ResourceType {
    protected String name;
    protected HashMap<String, Supplier<Block>> blockAffixes = new HashMap<>();
    protected HashSet<String> itemAffixes = new HashSet<>();

    public GenericResourceType(String name) {
        this.name = name;
    }

    public GenericResourceType withBlockAffix(String affix, Supplier<Block> supplier) {
        blockAffixes.put(affix, supplier);
        return this;
    }

    public String getAffixFor(String itemName) {
        return itemName.substring(getBaseResource().length() + 1);
    }

    @Override
    public String getBaseResource() {
        return name;
    }

    public GenericResourceType withItemAffixes(String... affixes) {
        this.itemAffixes.addAll(Arrays.asList(affixes));
        return this;
    }

    @Override
    public boolean contains(String itemName) {
        if (itemName.equals(name) && itemAffixes.contains("")) {
            return true; //matches empty affix
        }
        if (!itemName.startsWith(getBaseResource()+"_")) {
            return false; //not even our prefix
        }
        String affix = getAffixFor(itemName);
        
        return (itemAffixes.contains(affix) || blockAffixes.keySet().contains(affix));
    }

    @Override
    public Item getItem(String itemName) {
        Item existing = CommonItems.getItem(itemName);
        if (existing != null) {
            return existing;
        }
        else {
            CottonResources.LOGGER.warn("No item found with name " + itemName + "!");
            return null;
        }
    }

    public Item registerItem(String itemName) {
        return CommonItems.register(itemName, new Item((new Item.Settings()).group(CottonCommons.commonGroup)));
    }

    @Override
    public Block getBlock(String blockName) {
        Block existing = CommonBlocks.getBlock(blockName);
        if (existing != null) {
            return existing;
        }
        else {
            CottonResources.LOGGER.warn("No block found with name " + blockName + "!");
            return null;
        }
    }

    public Block registerBlock(String blockName) {
        String affix = getAffixFor(blockName);
        Supplier<Block> blockSupplier = blockAffixes.get(affix);
        if (blockSupplier == null) {
            return null;
        }
        
        Block resultBlock = blockSupplier.get();
        BlockItem resultItem =  new BlockItem(resultBlock, new Item.Settings().group(CottonCommons.commonGroup)); //Shouldn't be necessary, but is?
        
        return CommonBlocks.register(blockName, resultBlock, resultItem);
    }

    @Override
    public void registerAll() {
        registerBlocks();
        registerItems();
    }

    public void registerBlocks() {
        for (String affix : blockAffixes.keySet()) {
            registerBlock(getFullNameForAffix(affix));
            //CottonResources.LOGGER.devInfo("Registered block " + getFullNameForAffix(affix) + "!");
        }
    }

    public void registerItems() {
        for (String affix : itemAffixes) {
            registerItem(getFullNameForAffix(affix));
            //CottonResources.LOGGER.devInfo("Registered item " + getFullNameForAffix(affix) + "!");
        }
    }
}
