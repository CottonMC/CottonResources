package io.github.cottonmc.resources.oregen;

import com.mojang.datafixers.Dynamic;

import io.github.cottonmc.resources.CottonResources;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class CottonOreFeature extends Feature<DefaultFeatureConfig> {
	public static final CottonOreFeature COTTON_ORE = Registry.register(Registry.FEATURE, "cotton:ore", new CottonOreFeature());

	public CottonOreFeature() {
		super(DefaultFeatureConfig::deserialize);
	}

	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random rand, BlockPos pos, DefaultFeatureConfig uselessConfig) {
		//if (!config.dimensions.test(world.getDimension())) return false;
		//if (!config.biomes.test(world.getBiome(pos))) return false;
		
		//if (config.dimensionBlocklist.contains(Registry.DIMENSION.getId(world.getDimension().getType()).toString())) return false;
		//float diameter = rand.nextFloat() * 3.1415927F; //this makes a quantity in degrees through half a circle, so that we can pick a random rectangular X/Z spread whose elongation follows a gaussian distribution.
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
				
				int blocksGenerated = generateVeinPart(world, clusterX, clusterY, clusterZ, clusterSize, radius, settings.ores, 85, rand);
				System.out.println("    Generated "+blocksGenerated+" at "+clusterX+","+clusterY+","+clusterZ);
				
				//Identifier blockId = Registry.BLOCK.getId(settings.ores.iterator().next().getBlock());
				//System.out.println("    generating "+s+"="+blockId+" at base "+toGenerateIn.getPos());
			} else {
				System.out.println("    skipping "+s+" here.");
			}
		}
		
		//TODO: Throw away most of this hellishly overcomplicated vanilla code and just splat ores into one or more intersecting gaussian spheres
		/*
		float veinSize = (float)config.size / 8.0F;
		int blockCount = MathHelper.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
		double maxXDist = (double)((float)pos.getX() + MathHelper.sin(diameter) * veinSize);
		double minXDist = (double)((float)pos.getX() - MathHelper.sin(diameter) * veinSize);
		double maxZDist = (double)((float)pos.getZ() + MathHelper.cos(diameter) * veinSize);
		double minZDist = (double)((float)pos.getZ() - MathHelper.cos(diameter) * veinSize);
		double maxYDist = (double)(pos.getY() + rand.nextInt(3) - 2);
		double minYDist = (double)(pos.getY() + rand.nextInt(3) - 2);
		int startX = pos.getX() - MathHelper.ceil(veinSize) - blockCount;
		int startY = pos.getY() - 2 - blockCount;
		int startZ = pos.getZ() - MathHelper.ceil(veinSize) - blockCount;
		int int_6 = 2 * (MathHelper.ceil(veinSize) + blockCount);
		int int_7 = 2 * (2 + blockCount);

		for(int x = startX; x <= startX + int_6; ++x) {
			for(int z = startZ; z <= startZ + int_6; ++z) {
				if (startY <= world.getTop(Heightmap.Type.OCEAN_FLOOR_WG, x, z)) {
					return this.generateVeinPart(world, rand, config, maxXDist, minXDist, maxZDist, minZDist, maxYDist, minYDist, startX, startY, startZ, int_6, int_7);
				}
			}
		}*/

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
