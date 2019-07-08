package io.github.cottonmc.resources.oregen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import blue.endless.jankson.Comment;
import io.github.cottonmc.resources.config.OreGenerationSettings;

public class OreVoteConfig {
	@Comment("A set of ResourceType names (without affixes) that this mod needs, whose generation\n will be enabled unless explicitly blocked from the cotton-resources config.\nExample: ['nickel', 'copper', 'coal_coke']")
	public final Set<String> requests = new HashSet<String>();
	
	@Comment("A set of ore feature configurations to offer up as options if cotton-resources\n doesn't already have builtin configs for them. Multiple offered configurations will be picked from at random.\nExample: 'nickel': { target:'NATURAL_STONE', size: 4, state: 'c:nickel_ore', dimensionBlocklist: [] }")
	public final HashMap<String, OreGenerationSettings> offers = new HashMap<>();
}
