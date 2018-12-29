package io.github.cottonmc.resources;

import java.util.HashSet;
import java.util.Set;

import io.github.cottonmc.cotton.Cotton;
import io.github.cottonmc.cotton.registry.CommonItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemResourceType implements ResourceType {
    protected final String name;
    protected final Set<String> affixes = new HashSet<>();

    public ItemResourceType(String name) {
        this.name = name;
    }

    public ItemResourceType(String name, String... affixes) {
        this.name = name;
        withAffixes(affixes);
    }

    @Override
    public String getDomain() {
        return name;
    }

    @Override
    public boolean governs(String itemName) {
        if (itemName.equals(name) && affixes.contains("")) return true;
        return ResourceType.super.governs(itemName);
    }

    public void withAffixes(String... affixes) {
        for(String affix : affixes) this.affixes.add(affix);
    }
    @Override
    public Item getItem(String itemName) {
        Identifier id = new Identifier("cotton", itemName);
        if (Registry.ITEM.contains(id)) {
            //It exists, get it from the registry
            return Registry.ITEM.get(id);
        } else {
            //It's in our court.
            if (!governs(itemName)) return null; //One last sanity check.
            boolean shouldCreate = false;
            
            if (!shouldCreate) {
                for(String affix : affixes) {
                    if (affix.equals("") && itemName.equals(name)) { //if e.g. our domain is "coal_coke" and the itemName is "coal_coke"
                        shouldCreate = true;
                        break;
                    } else if (itemName.equals(name+"_"+affix)) {
                        shouldCreate = true;
                        break;
                    }
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
        Identifier id = new Identifier("cotton", blockName);
        if (Registry.ITEM.contains(id)) {
            //It exists, get it from the registry
            return Registry.BLOCK.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void registerAllItems() {
        for(String affix : affixes) {
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
