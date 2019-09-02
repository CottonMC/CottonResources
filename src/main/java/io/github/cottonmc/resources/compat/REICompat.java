package io.github.cottonmc.resources.compat;

import com.google.common.collect.ImmutableSet;
import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.oregen.OregenResourceListener;
import io.github.cottonmc.resources.type.ResourceType;
import me.shedaniel.rei.api.Entry;
import me.shedaniel.rei.api.EntryRegistry;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.util.version.VersionParsingException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Set;

public class REICompat implements REIPluginV0 {
	public static final Identifier ID = new Identifier(CottonResources.MODID, "disable_items");
	
	private static final Set<String> IMMUNE_TO_HIDING = ImmutableSet.<String>of(
			"wood", "stone", "iron", "gold", "diamond"
			);
	
	@Override
	public Identifier getPluginIdentifier() {
		return ID;
	}
	
	@Override
	public void registerEntries(EntryRegistry entryRegistry) {
		CottonResources.LOGGER.info("Configuring resource visibility");
		recheckItemHiding(entryRegistry.getModifiableEntryList());
	}
	
	public void recheckItemHiding(List<Entry> list) {
		for (ResourceType rsrc : CottonResources.BUILTINS.values()) {
			if (IMMUNE_TO_HIDING.contains(rsrc.getBaseResource())) continue;
			
			System.out.println("Not hiding: "+OregenResourceListener.getConfig().ores);
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
					Entry entry = list.get(i);
					if (entry.getEntryType() == Entry.Type.ITEM) {
						ItemStack listItem = entry.getItemStack();
						if (listItem.getItem() == item) {
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
				}
				
				//if (add) {
					//System.out.println("Re-adding "+item.getTranslationKey());
				//	list.add(Entry.create(new ItemStack(item)));
				//}
			}
		}
	}
	
	@Override
	public SemanticVersion getMinimumVersion() throws VersionParsingException {
		return SemanticVersion.parse("3.0-pre");
	}
}
