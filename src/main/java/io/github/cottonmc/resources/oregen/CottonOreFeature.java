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
	
	public static Clump[] SPHERES = {
			Clump.of(1), Clump.of(2), Clump.of(3), Clump.of(4), Clump.of(5), Clump.of(6), Clump.of(7), Clump.of(8), Clump.of(9)
	};

	public CottonOreFeature() {
		super(DefaultFeatureConfig::deserialize);
	}

	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random rand, BlockPos pos, DefaultFeatureConfig uselessConfig) {
		OreVoteConfig config = OregenResourceListener.getConfig();
		if (config.ores.isEmpty()) return true; // We didn't generate anything, but yes, don't retry.
		
		
		Chunk toGenerateIn = world.getChunk(pos);
		Biome biome = toGenerateIn.getBiome(pos);
		//System.out.println("Generating into "+toGenerateIn.getPos()+" <- "+config.ores);
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
				
				if (settings.cluster_size<=0) settings.cluster_size = 1;
				
				int blocksGenerated = 0;
				for(int i=0; i<clusters; i++) {
					//Pick an epicenter
					//int maxCluster = 7;
					int overbleed = 0; //Increase to allow ore deposits to overlap South/East chunks by this amount
					
					int radius = (int) Math.log(settings.cluster_size) + 1;
					if (radius>7) radius=7; //radius can't go past 7 without adding some overbleed
					for(int j=0; j<SPHERES.length; j++) { //find the smallest clump in our vocabulary which expresses the number of ores
						Clump clump = SPHERES[j];
						if (clump.size()>=settings.cluster_size) {
							System.out.println("Cluster size "+settings.cluster_size+" matched against clump #"+j);
							radius = j+1;
							break;
						}
					}
					
					int clusterX = rand.nextInt(16 + overbleed - (radius*2))+radius;
					int clusterZ = rand.nextInt(16 + overbleed - (radius*2))+radius;
					int heightRange = settings.max_height-settings.min_height; if (heightRange<1) heightRange=1;
					int clusterY = rand.nextInt(heightRange)+settings.min_height;
					
					clusterX += toGenerateIn.getPos().getStartX();
					clusterZ += toGenerateIn.getPos().getStartZ();
					
					int generatedThisCluster = generateVeinPartGaussianClump(world, clusterX, clusterY, clusterZ, settings.cluster_size, radius, settings.ores, 85, rand);
					blocksGenerated += generatedThisCluster;
					System.out.println("    Generated "+generatedThisCluster+" out of "+settings.cluster_size+" expected.");
				}
				
				System.out.println("    Generated "+blocksGenerated+" in "+clusters+" clusters out of "+settings.cluster_size+"*"+clusters+"="+(settings.cluster_size*clusters));
			} else {
				//System.out.println("    skipping "+s+" here.");
			}
		}
		
		return false;
	}
	
	protected int generateVeinPart(IWorld world, int x, int y, int z, int clumpSize, int radius, Set<BlockState> states, int density, Random rand) {
		int rad2 = radius * radius;
		BlockState[] blocks = states.toArray(new BlockState[states.size()]);
		int replaced = 0;
		
		//TODO: Maybe add these into a set and scramble them so that ores distribute more evenly into the sphere
		//      Alternatively, we could spray ores *at random* using a gaussian distribution. Not sure which is better.
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
					
					/*
					BlockPos pos = new BlockPos(xi, yi, zi);
					BlockState toReplace = world.getBlockState(pos);
					
					if (toReplace.isAir() || !toReplace.isSimpleFullBlock(world, pos)) continue; //TODO: real replacement criteria
					
					BlockState replaceWith = blocks[rand.nextInt(blocks.length)];
					world.setBlockState(new BlockPos(xi,yi,zi), replaceWith, 3);*/
					if (replace(world, xi, yi, zi, blocks, rand)) {
						replaced++;
						if (replaced>=clumpSize) return replaced;
					}
				}
			}
		}
		
		return replaced;
	}
	
	protected int generateVeinPartGaussian(IWorld world, int x, int y, int z, int clumpSize, int radius, Set<BlockState> states, int density, Random rand) {
		int rad2 = radius * radius;
		BlockState[] blocks = states.toArray(new BlockState[states.size()]);
		int replaced = 0;
		for(int i=0; i<200; i++) {
			int xi = (int)(x + (rand.nextGaussian()*radius));
			int yi = (int)(y + (rand.nextGaussian()*radius));
			int zi = (int)(z + (rand.nextGaussian()*radius));
			
			int dx = xi-x;
			int dy = yi-y;
			int dz = zi-z;
			int dist2 = dx*dx+dy*dy+dz*dz;
			if (dist2 > rad2) continue;
			
			if (replace(world, xi, yi, zi, blocks, rand)) {
				replaced++;
				if (replaced>=clumpSize) return replaced;
			}
		}
		
		return replaced;
	}
	
	protected int generateVeinPartGaussianClump(IWorld world, int x, int y, int z, int clumpSize, int radius, Set<BlockState> states, int density, Random rand) {
		int radIndex = radius-1;
		Clump clump = (radIndex<SPHERES.length) ? SPHERES[radIndex].copy() : Clump.of(radius);
		
		//int rad2 = radius * radius;
		BlockState[] blocks = states.toArray(new BlockState[states.size()]);
		int replaced = 0;
		for(int i=0; i<clump.size(); i++) {
			if (clump.isEmpty()) break;
			BlockPos pos = clump.removeGaussian(rand, x, y, z);
			if (replace(world, pos.getX(), pos.getY(), pos.getZ(), blocks, rand)) {
				replaced++;
				if (replaced>=clumpSize) return replaced;
			}
		}
		
		return replaced;
	}
	
	public boolean replace(IWorld world, int x, int y, int z, BlockState[] states, Random rand) {
		BlockPos pos = new BlockPos(x, y, z);
		BlockState toReplace = world.getBlockState(pos);
		
		if (toReplace.isAir() || !toReplace.isSimpleFullBlock(world, pos)) return false; //TODO: real replacement criteria
		
		BlockState replaceWith = states[rand.nextInt(states.length)];
		world.setBlockState(pos, replaceWith, 3);
		return true;
	}
}
