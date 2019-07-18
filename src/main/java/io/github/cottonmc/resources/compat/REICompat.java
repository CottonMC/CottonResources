package io.github.cottonmc.resources.compat;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.oregen.OregenResourceListener;
import io.github.cottonmc.resources.type.ResourceType;
import me.shedaniel.rei.api.ItemRegistry;
import me.shedaniel.rei.api.REIPluginEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class REICompat implements REIPluginEntry {
	public static final Identifier ID = new Identifier(CottonResources.MODID, "disable_items");
	
	private static final Set<String> IMMUNE_TO_HIDING = ImmutableSet.<String>of(
			"wood", "stone", "iron", "gold", "diamond"
			);
	
	@Override
	public Identifier getPluginIdentifier() {
		return ID;
	}
	
	@Override
	public void registerItems(ItemRegistry itemRegistry) {
		CottonResources.LOGGER.info("Configuring resource visibility");
		recheckItemHiding(itemRegistry.getModifiableItemList());
	}
	
	public void recheckItemHiding(List<ItemStack> list) {
		for (ResourceType rsrc : CottonResources.BUILTINS.values()) {
			if (IMMUNE_TO_HIDING.contains(rsrc.getBaseResource())) continue;
			
			boolean enabled = OregenResourceListener.getConfig().ores.contains(rsrc.getBaseResource());
			for(String affix : rsrc.getAffixes()) {
				Item item = rsrc.getItem(affix);
				if (item==null || item.equals(Items.AIR)) {
					System.out.println(rsrc.getBaseResource()+"_"+affix+" does not exist?!");
					continue;
				}
				ItemStack stack = new ItemStack(item);
				
				boolean add = enabled;
				for(int i=0; i<list.size(); i++) {
					ItemStack listItem = list.get(i);
					if (listItem.getItem()==item) {
						add = false;
						if (!enabled) {
							//System.out.println("Removing "+item.getTranslationKey());
							list.remove(i);
							break;
						} else {
							//System.out.println("Letting stay "+item.getTranslationKey());
							break;
						}
					}
				}
				
				if (add) {
					//System.out.println("Re-adding "+item.getTranslationKey());
					list.add(new ItemStack(item));
				}
			}
		}
	}
}
