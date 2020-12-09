/*
 * Copyright 2020 Jade Krabbe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.cottonmc.resources.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class MippedOreBlock extends OreBlock {
    public MippedOreBlock(Settings settings) {
        super(settings);
    }
    public static class Glowing extends MippedOreBlock {
        public static final BooleanProperty LIT = Properties.LIT;
        private final Color particleColor;


        public Glowing(Settings settings, Color particleColor) {
            super(settings);
            this.setDefaultState(getStateManager().getDefaultState().with(LIT, false));
            this.particleColor = particleColor;
        }

        @Override
        public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
            light(state, world, pos);
            super.onBlockBreakStart(state, world, pos, player);
        }

        @Override
        public void onSteppedOn(World world, BlockPos pos, Entity entity) {
            light(world.getBlockState(pos), world, pos);
            super.onSteppedOn(world, pos, entity);
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if (world.isClient) {
                return ActionResult.SUCCESS;
            } else {
                light(state, world, pos);
                return ActionResult.PASS;
            }
        }

        private static void light(BlockState state, World world, BlockPos pos) {
            if (!state.get(LIT)) {
                world.setBlockState(pos, state.with(LIT, true), 3);
            }
        }

        @Override
        public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            if (state.get(LIT)) {
                world.setBlockState(pos, state.with(LIT, false), 3);
            }
        }

        @Override
        protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
            builder.add(LIT);
        }

        @Environment(EnvType.CLIENT)
        public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
            if ((Boolean)state.get(LIT)) {
                spawnParticles(world, pos, this.particleColor);
            }

        }

        private static void spawnParticles(World world, BlockPos pos, Color color) {
            double d = 0.5625D;
            Random random = world.random;
            Direction[] var5 = Direction.values();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Direction direction = var5[var7];
                BlockPos blockPos = pos.offset(direction);
                if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                    Direction.Axis axis = direction.getAxis();
                    double e = axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getOffsetX() : (double)random.nextFloat();
                    double f = axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getOffsetY() : (double)random.nextFloat();
                    double g = axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getOffsetZ() : (double)random.nextFloat();
                    world.addParticle(new DustParticleEffect(color.getRed()/255.0f, color.getGreen()/255.0f,color.getBlue()/255.0f, 1.0f),
                            (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0D,
                            0.0D, 0.0D);
                }
            }

        }

    }
}
