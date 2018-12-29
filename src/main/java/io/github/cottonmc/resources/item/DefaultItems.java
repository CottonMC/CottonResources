package io.github.cottonmc.resources.item;

import com.google.common.collect.ImmutableSet;

import io.github.cottonmc.cotton.Cotton;
import io.github.cottonmc.cotton.registry.CommonItems;
import net.minecraft.item.Item;

public class DefaultItems {
    /* This list exists primarily so we can gatekeep items that we have builtin resources for, versus items we don't */
    private static final ImmutableSet<String> BUILTINS = ImmutableSet.of(
            /*** Pure Metals ***/
            //Copper's a great low-tier conductor for both heat and electricity. Its high ductility is the reason we think of wires as flexible. Corrodes easily.
            "copper_ingot", "copper_nugget", "copper_dust", "copper_gear", "copper_plate",
            //Silver is corrosion resistant, and is a better conductor - both heat and electricity - than copper and gold. Rare, and loaded with magical lore.
            "silver_ingot", "silver_nugget", "silver_dust", "silver_gear", "silver_plate",
            //Lead is synonymous with heavy and slow. Used for irrigation pipes in the ancient world, also used to sink fishing bait, to plumb quality architecture, and for drywall or concrete anchors.
            "lead_ingot",   "lead_nugget",   "lead_dust",   "lead_gear",   "lead_plate",
            //Zinc is a secondary product of many ores, and is a key ingredient in brass. Zinc is insanely easy to corrode, to the point where they attach zinc blocks to oil rigs to keep them from rusting.
            "zinc_ingot",   "zinc_nugget",   "zinc_dust",
            
            /*** Alloys and Steels ***/
            //Steel has excellent tensile strength, good for mid- to high-tier tools, structures, and machines
            //Typically I see around 1x iron + 4x coal dust -> 1x steel, but this should probably be a blast furnace thing.
            "steel_ingot",  "steel_nugget",  "steel_dust",  "steel_gear",  "steel_plate",
            //Electrum is a soft metal, not appropriate for heavy machinery, but is a corrosion-resistant conductor. It was also used to top obelisks and pyramids in ancient Egypt.
            //Reccommended 1x gold + 1x silver -> 2x electrum
            "electrum_ingot","electrum_nugget","electrum_dust",
            //Brass has excellent corrosion resistance and low friction, making it great for clockwork and pneumatics, instruments, and naval applications
            //Reccommended 3x copper + 1x zinc -> 4x brass
            "brass_ingot",  "brass_nugget",  "brass_dust",  "brass_gear",  "brass_plate",
            
            /*** Misc ***/
            "coal_dust", //needed for graphite and possibly steel
            "coal_coke", //good second-tier power gen option
            "mercury",   //great for magical applications, and in the ancient world was incorrectly thought to keep one healthy when drank - see also the Pythagorean Cup
            "uranium_dust", "plutonium_dust", "thorium_dust" //don't breathe this - uranium and its byproducts can make a really great high-risk/high-reward/high-tier energy system
            );

    /**Registers an Item based on its name if it is supported, or returns it if it already exists.
     * @param name The name of the item
     * @return The Item associated with the given name or null if the name is not supported.
     */
    public static Item provide(String name){
        /*//Having this function would let us duck out early and provide the item even if another mod provided it.
        Item item = CommonItems.get(name);
        if (item!=null) return item; */

        if (BUILTINS.contains(name)) {
            return CommonItems.register(name, new Item((new Item.Settings()).itemGroup(Cotton.commonGroup)));
        } else {
            return null;
        }
    }
}
