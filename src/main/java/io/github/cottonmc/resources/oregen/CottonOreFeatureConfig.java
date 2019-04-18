package io.github.cottonmc.resources.oregen;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CottonOreFeatureConfig implements FeatureConfig {

	public final OreFeatureConfig.Target target;
	public final int size;
	public final BlockState state;
	public final List<String> blacklistedDimensions;

	public CottonOreFeatureConfig(OreFeatureConfig.Target target, BlockState state, int size, List<String> blacklistedDimensions) {
		this.size = size;
		this.state = state;
		this.target = target;
		this.blacklistedDimensions = blacklistedDimensions;
	}

	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
				ops.createString("size"), ops.createInt(this.size),
				ops.createString("target"), ops.createString(this.target.getName()),
				ops.createString("state"), BlockState.serialize(ops, this.state).getValue(),
				ops.createString("blacklist"), ops.createString(createIdString(blacklistedDimensions)))));
	}

	public static <T> CottonOreFeatureConfig deserialize(Dynamic<T> dynamic) {
		int size = dynamic.get("size").asInt(0);
		OreFeatureConfig.Target target = OreFeatureConfig.Target.byName(dynamic.get("target").asString(""));
		BlockState state = dynamic.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		List<String> blacklist;
		if (dynamic.get("blacklist").asString().isPresent()) {
			blacklist = createIdList(dynamic.get("blacklist").asString().get());
		} else {
			blacklist = new ArrayList<>();
		}
		return new CottonOreFeatureConfig(target, state, size, blacklist);
	}

	private static String createIdString(List<String> ids) {
		StringBuilder ret = new StringBuilder();
		for (int i  = 0; i < ids.size(); i++) {
			String id = ids.get(0);
			ret.append(id);
			if (i != ids.size() - 1) ret.append(",");
		}
		return ret.toString();
	}

	private static List<String> createIdList(String ids) {
		String[] split = ids.split(",");
		return new ArrayList<>(Arrays.asList(split));
	}


}
