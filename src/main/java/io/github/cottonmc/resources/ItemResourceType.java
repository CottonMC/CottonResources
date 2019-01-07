package io.github.cottonmc.resources;

import io.github.cottonmc.cotton.Cotton;
import io.github.cottonmc.cotton.registry.CommonBlocks;
import io.github.cottonmc.cotton.registry.CommonItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ItemResourceType implements ResourceType {
    protected final String name;
    protected final Set<String> itemAffixes = new HashSet<>();

    public ItemResourceType(String name) {
        this.name = name;
    }

    public ItemResourceType(String name, String... affixes) {
        this.name = name;
        withItemAffixes(affixes);
    }

    @Override
    public String getBaseResource() {
        return name;
    }

    protected String getAffix(String name) {
        //Empty affix
        if (name.equals(getBaseResource())) {
            return "";
        }
        //Invalid affix
        if (!name.startsWith(getBaseResource()+"_")) {
            return "";
        }
        return name.substring(getBaseResource().length()+1); //Consume our domain prefix and the underscore
    }

    @Override
    public boolean contains(String itemName) {
        //matches empty affix
        if (itemName.equals(name) && itemAffixes.contains("")) {
            return true;
        }
        if (!itemName.startsWith(getBaseResource()+"_")) {
            return false; //not even our prefix
        }
        String affix = getAffix(itemName);
        return (itemAffixes.contains(affix));
    }

    public ItemResourceType withItemAffixes(String... affixes) {
        this.itemAffixes.addAll(Arrays.asList(affixes));
        return this;
    }
    @Override
    public Item getItem(String itemName) {
        Item existing = CommonItems.getItem(itemName);
        if (existing!=null) {
            return existing;
        } else {
            //It's in our court.
            if (!contains(itemName)) return null; //One last sanity check.
            boolean shouldCreate = false;

            for(String affix : itemAffixes) {
                if (affix.equals("") && itemName.equals(name)) { //if e.g. our domain is "coal_coke" and the itemName is "coal_coke"
                    shouldCreate = true;
                    break;
                } else if (itemName.equals(name+"_"+affix)) {
                    shouldCreate = true;
                    break;
                }
            }

            if (shouldCreate) {
                return CommonItems.register(itemName, new Item((new Item.Settings()).itemGroup(Cotton.commonGroup)));
            } else {
                return null;
            }
        }
    }

    @Override
    public Block getBlock(String blockName) {
        return CommonBlocks.getBlock(blockName);
    }

    @Override
    public void registerAllItems() {
        for(String affix : itemAffixes) {
            if (affix.isEmpty()) {
                getItem(name);
            } else {
                getItem(name+"_"+affix);
            }
        }
    }

    @Override
    public void registerAllBlocks() {
        //Do nothing
    }

}
