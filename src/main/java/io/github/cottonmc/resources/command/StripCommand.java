/*
 * MIT License
 *
 * Copyright (c) 2018-2020 The Cotton Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.cottonmc.resources.command;

import java.util.Set;
import com.google.common.collect.ImmutableSet;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.resources.tag.CottonResourcesTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class StripCommand implements Command<ServerCommandSource> {
	private static final Set<Block> BUILTIN_STRIPS = ImmutableSet.<Block>of();

	@Override
	public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity caller = context.getSource().getPlayer();
		Chunk chunk = caller.getEntityWorld().getChunk(caller.getBlockPos());
		context.getSource().sendFeedback(new LiteralText("Stripping " + chunk.getPos() + "..."), true);

		for (int z = 0; z < 16; z++) {
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < 256; y++) {
					BlockPos pos = chunk.getPos().getStartPos().add(x, y, z);
					BlockState toReplace = caller.getEntityWorld().getBlockState(pos);

					if (toReplace.isAir()) {
						continue;
					}

					boolean strip;
					strip = CottonResourcesTags.STRIP_COMMAND.contains(toReplace.getBlock());

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
