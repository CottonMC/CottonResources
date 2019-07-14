package io.github.cottonmc.resources.oregen;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.Set;

public class CottonOreFeature extends Feature<DefaultFeatureConfig> {
	public static final CottonOreFeature COTTON_ORE = Registry.register(Registry.FEATURE, "cotton:ore", new CottonOreFeature());

	public CottonOreFeature() {
		super(DefaultFeatureConfig::deserialize);
	}

	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random rand, BlockPos pos, DefaultFeatureConfig uselessConfig) {
		OreVoteConfig config = OregenResourceListener.getConfig();
		if (config.ores.isEmpty()) return true; // We didn't generate anything, but yes, don't retry.
		
		
		Chunk toGenerateIn = world.getChunk(pos);
		Biome biome = toGenerateIn.getBiome(pos);
		System.out.println("Generating into "+toGenerateIn.getPos()+" <- "+config.ores);
		for(String s : config.ores) {
			OreGenerationSettings settings = config.generators.get(s);
			if (settings==null) continue;
			
			if (settings.dimensions.test(world.getDimension()) && settings.biomes.test(biome)) {
			
				//For now, spit debug info
				if (settings.ores.isEmpty()) {
					continue;
				}
				int clusters = settings.cluster_count;
				if (clusters<1) clusters=1;
				
				int blocksGenerated = 0;
				for(int i=0; i<clusters; i++) {
					//Pick an epicenter
					int maxCluster = 7; //MaxCluster can't go past 7 without adding some overbleed
					int overbleed = 0; //Increase to allow ore deposits to overlap South/East chunks by this amount
					int clusterSize = Math.max(1, Math.min(maxCluster, settings.cluster_size)); //Clamp to 1..maxCluster
					
					int radius = (int) Math.log(clusterSize) + 2;
					
					int clusterX = rand.nextInt(16 + overbleed - (radius*2))+radius;
					int clusterZ = rand.nextInt(16 + overbleed - (radius*2))+radius;
					int heightRange = settings.max_height-settings.min_height; if (heightRange<1) heightRange=1;
					int clusterY = rand.nextInt(heightRange)+settings.min_height;
					
					clusterX += toGenerateIn.getPos().getStartX();
					clusterZ += toGenerateIn.getPos().getStartZ();
					
					int generatedThisCluster = generateVeinPart(world, clusterX, clusterY, clusterZ, clusterSize, radius, settings.ores, 85, rand);
					blocksGenerated += generatedThisCluster;
				}
				
				System.out.println("    Generated "+blocksGenerated+" in "+clusters+" clusters.");
			} else {
				System.out.println("    skipping "+s+" here.");
			}
		}

		return false;
	}
	
	protected boolean generateVein(IWorld world, int x, int z, OreGenerationSettings settings) {
		// x/z is the northwest corner of the chunk; generation is offset into these values
		
		
		return false;
	}
	
	protected int generateVeinPart(IWorld world, int x, int y, int z, int clumpSize, int radius, Set<BlockState> states, int density, Random rand) {
		int rad2 = radius * radius;
		BlockState[] blocks = states.toArray(new BlockState[states.size()]);
		int replaced = 0;
		
		for(int zi = (int)(z - radius); zi<= (int)(z + radius); zi++) {
			for(int xi = (int)(x - radius); xi<= (int)(x + radius); xi++) {
				for(int yi = (int)(y - radius); yi<= (int)(y + radius); yi++) {
					if (yi<0 || yi>255) continue;
					if (rand.nextInt(100)>density) continue;
					
					int dx = xi-x;
					int dy = yi-y;
					int dz = zi-z;
					int dist2 = dx*dx+dy*dy+dz*dz;
					if (dist2 > rad2) continue;
					
					BlockPos pos = new BlockPos(xi, yi, zi);
					BlockState toReplace = world.getBlockState(pos);
					
					if (toReplace.isAir() || !toReplace.isSimpleFullBlock(world, pos)) continue; //TODO: real replacement criteria
					
					BlockState replaceWith = blocks[rand.nextInt(blocks.length)];
					world.setBlockState(new BlockPos(xi,yi,zi), replaceWith, 3);
					replaced++;
					if (replaced>=clumpSize) return replaced;
				}
			}
		}
		
		return replaced;
	}
}
