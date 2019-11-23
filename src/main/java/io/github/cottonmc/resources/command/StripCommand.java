package io.github.cottonmc.resources.command;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class StripCommand implements Command<ServerCommandSource>{
	
	private static final Set<Block> BUILTIN_STRIPS = ImmutableSet.<Block>of();
	
	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity caller = context.getSource().getPlayer();
		Chunk chunk = caller.getEntityWorld().getChunk(caller.getBlockPos());
		context.getSource().sendFeedback(new LiteralText("Stripping " + chunk.getPos() + "..."), true);
		
		Tag<Block> stripTag = BlockTags.getContainer().get(new Identifier("c", "strip_command"));
		if (stripTag == null) {
			return -1;
		}
		
		for (int z = 0; z < 16; z++) {
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < 256; y++) {
					BlockPos pos = chunk.getPos().toBlockPos(x, y, z);
					BlockState toReplace = caller.getEntityWorld().getBlockState(pos);
					if (toReplace.isAir()) {
						continue;
					}
					
					boolean strip;
					strip = stripTag.contains(toReplace.getBlock());

					if (strip) {
						caller.getEntityWorld().setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
					}
				}
			}
		}
		
		
		context.getSource().sendFeedback(new LiteralText("Chunk stripped."), true);
		return 1;
	}

}
