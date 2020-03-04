package io.github.cottonmc.resources;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlowingLayeredOreBlock extends LayeredOreBlock {
    public static final BooleanProperty LIT = Properties.LIT;

    public GlowingLayeredOreBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(LIT, false));
    }

    public int getLuminance(BlockState state) {
        return state.get(LIT) ? super.getLuminance(state) : 0;
    }

    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        light(state, world, pos);
        super.onBlockBreakStart(state, world, pos, player);
    }

    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        light(world.getBlockState(pos), world, pos);
        super.onSteppedOn(world, pos, entity);
    }

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

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, false), 3);
        }

    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
