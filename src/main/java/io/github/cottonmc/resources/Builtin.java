/*
 * Copyright 2020 The Cotton project
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
package io.github.cottonmc.resources;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class Builtin {
    public static final Identifier COPPER_ID = new Identifier("c", "copper");
    public static final Identifier TIN_ID = new Identifier("c", "tin");
    public static final Identifier ZINC_ID = new Identifier("c", "zinc");
    public static final Identifier SILVER_ID = new Identifier("c", "silver");
    public static final Identifier LEAD_ID = new Identifier("c", "lead");
    public static final Identifier ALUMINUM_ID = new Identifier("c", "aluminum");
    public static final Identifier PLATINUM_ID = new Identifier("c", "platinum");
    public static final Identifier PALLADIUM_ID = new Identifier("c", "palladium");
    public static final Identifier OSMIUM_ID = new Identifier("c", "osmium");
    public static final Identifier IRIDIUM_ID = new Identifier("c", "iridum");
    public static final Identifier TUNGSTEN_ID = new Identifier("c", "tungsten");
    public static final Identifier COBALT_ID = new Identifier("c", "cobalt");

    public static final Identifier STEEL_ID = new Identifier("c", "steel");
    public static final Identifier TITANIUM_ID = new Identifier("c", "titanium");
    public static final Identifier BRASS_ID = new Identifier("c", "brass");
    public static final Identifier ELECTRUM_ID = new Identifier("c", "electrum");
    public static final Identifier BRONZE_ID = new Identifier("c", "bronze");

    public static final Identifier COAL_ID = new Identifier("c", "coal");
    public static final Identifier COAL_COKE_ID = new Identifier("c", "coal_coke");
    public static final Identifier URANIUM_ID = new Identifier("c", "uranium");
    public static final Identifier MERCURY_ID = new Identifier("c", "mercury");
    public static final Identifier THORIUM_ID = new Identifier("c", "thorium");
    public static final Identifier PLUTONIUM_ID =  new Identifier("c", "plutonium");

    public static final Identifier RUBY_ID = new Identifier("c", "ruby");
    public static final Identifier TOPAZ_ID = new Identifier("c", "topaz");
    public static final Identifier AMETHYST_ID = new Identifier("c", "amethyst");
    public static final Identifier PERIDOT_ID = new Identifier("c", "peridot");
    public static final Identifier SAPPHIRE_ID = new Identifier("c", "sapphire");

    public static final Identifier WOOD_ID = new Identifier("c", "wood");
    public static final Identifier STONE_ID = new Identifier("c", "stone");
    public static final Identifier IRON_ID = new Identifier("c", "iron");
    public static final Identifier GOLD_ID = new Identifier("c", "gold");
    public static final Identifier DIAMOND_ID = new Identifier("c", "diamond");
    public static final Identifier EMERALD_ID = new Identifier("c", "emerald");
    public static final Identifier NETHERITE_ID = new Identifier("c", "netherite");

    private static final Color URANIUM_PARTICLE_COLOR = Color.decode("#74D94D");


    public static final Resource COPPER =
            CottonResources.ResourceArbiter.addOrGetResource(COPPER_ID, Resource.fullMetalType(1).build());
    public static final Resource TIN =
            CottonResources.ResourceArbiter.addOrGetResource(TIN_ID, Resource.fullMetalType(1).build());
    public static final Resource ZINC =
            CottonResources.ResourceArbiter.addOrGetResource(ZINC_ID, Resource.fullMetalType(1).build());

    public static final Resource SILVER =
            CottonResources.ResourceArbiter.addOrGetResource(SILVER_ID, Resource.fullMetalType(2).build());
    public static final Resource LEAD =
            CottonResources.ResourceArbiter.addOrGetResource(LEAD_ID, Resource.fullMetalType(2).build());
    public static final Resource ALUMINUM =
            CottonResources.ResourceArbiter.addOrGetResource(ALUMINUM_ID, Resource.fullMetalType(2).build());
    public static final Resource PLATINUM =
            CottonResources.ResourceArbiter.addOrGetResource(PLATINUM_ID, Resource.fullMetalType(2).build());
    public static final Resource PALLADIUM =
            CottonResources.ResourceArbiter.addOrGetResource(PALLADIUM_ID, Resource.fullMetalType(2).build());
    public static final Resource OSMIUM =
            CottonResources.ResourceArbiter.addOrGetResource(OSMIUM_ID, Resource.fullMetalType(2).build());
    public static final Resource IRIDIUM =
            CottonResources.ResourceArbiter.addOrGetResource(IRIDIUM_ID, Resource.fullMetalType(2).build());
    public static final Resource TUNGSTEN =
            CottonResources.ResourceArbiter.addOrGetResource(TUNGSTEN_ID, Resource.fullMetalType(2).build());

    public static final Resource COBALT =
            CottonResources.ResourceArbiter.addOrGetResource(COBALT_ID, Resource.fullMetalType(3).build());
    public static final Resource TITANIUM =
            CottonResources.ResourceArbiter.addOrGetResource(TITANIUM_ID, Resource.fullMetalType(3).build());

    public static final Resource STEEL =
            CottonResources.ResourceArbiter.addOrGetResource(STEEL_ID, Resource.metalTypeNoOre().build());
    public static final Resource BRASS =
            CottonResources.ResourceArbiter.addOrGetResource(BRASS_ID, Resource.metalTypeNoOre().build());
    public static final Resource ELECTRUM =
            CottonResources.ResourceArbiter.addOrGetResource(ELECTRUM_ID, Resource.metalTypeNoOre().build());
    public static final Resource BRONZE =
            CottonResources.ResourceArbiter.addOrGetResource(BRONZE_ID, Resource.metalTypeNoOre().build());

    public static final Resource COAL =
            CottonResources.ResourceArbiter.addOrGetResource(COAL_ID, 
                    new Resource.Builder()
                            .hasItem(Parts.GENERIC, Items.COAL, NameTransformers.NO_CHANGE, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.COAL_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.COAL_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasBlock(Parts.ORE, Blocks.COAL_ORE, NameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.COAL_ORE, NameTransformers.ORE, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, Suppliers.makeTieredOre(0),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, Suppliers.makeTieredOre(0),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.END_ORE)
                            .hasItem(Parts.DUST, Suppliers.MISC_SUPP.get(), NameTransformers.DUST)
                            .build());
    public static final Resource COAL_COKE =
            CottonResources.ResourceArbiter.addOrGetResource(COAL_COKE_ID, 
                    new Resource.Builder()
                            .hasItem(Parts.GENERIC, Suppliers.MISC_SUPP.get(), NameTransformers.NO_CHANGE)
                            .hasBlockWithItem(Parts.STORAGE_BLOCK, Suppliers.STONY_STORAGE_BLOCK_SUPP.get(),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.STORAGE_BLOCK)
                            .build());
    public static final Resource URANIUM =
            CottonResources.ResourceArbiter.addOrGetResource(URANIUM_ID, Resource.fullMetalGlowingType(3,
                    URANIUM_PARTICLE_COLOR).build());
    public static final Resource MERCURY =
            CottonResources.ResourceArbiter.addOrGetResource(MERCURY_ID,
                    new Resource.Builder()
                            .hasItem(Parts.GENERIC, Suppliers.MISC_SUPP.get(), NameTransformers.NO_CHANGE)
                            .build());
    public static final Resource THORIUM =
            CottonResources.ResourceArbiter.addOrGetResource(THORIUM_ID, Resource.metalTypeNoOre().build());
    public static final Resource PLUTONIUM =
            CottonResources.ResourceArbiter.addOrGetResource(PLUTONIUM_ID, Resource.metalTypeNoOre().build());

    public static final Resource RUBY =
            CottonResources.ResourceArbiter.addOrGetResource(RUBY_ID, Resource.fullGemType(1).build());
    public static final Resource TOPAZ =
            CottonResources.ResourceArbiter.addOrGetResource(TOPAZ_ID, Resource.fullGemType(1).build());
    public static final Resource AMETHYST =
            CottonResources.ResourceArbiter.addOrGetResource(AMETHYST_ID, Resource.fullGemType(1).build());
    public static final Resource PERIDOT =
            CottonResources.ResourceArbiter.addOrGetResource(PERIDOT_ID, Resource.fullGemType(1).build());
    public static final Resource SAPPHIRE =
            CottonResources.ResourceArbiter.addOrGetResource(SAPPHIRE_ID, Resource.fullGemType(1).build());

    public static final Resource WOOD =
            CottonResources.ResourceArbiter.addOrGetResource(WOOD_ID, 
                    new Resource.Builder()
                            .hasItem(Parts.DUST, Suppliers.MISC_SUPP.get(), NameTransformers.DUST)
                            .hasItem(Parts.GEAR, Suppliers.MISC_SUPP.get(), NameTransformers.GEAR)
                            .hasItem(Parts.PLATE, Suppliers.MISC_SUPP.get(), NameTransformers.PLATE)
                            .build());
    public static final Resource STONE =
            CottonResources.ResourceArbiter.addOrGetResource(STONE_ID, 
                    new Resource.Builder()
                            .hasItem(Parts.DUST, Suppliers.MISC_SUPP.get(), NameTransformers.DUST)
                            .hasItem(Parts.GEAR, Suppliers.MISC_SUPP.get(), NameTransformers.GEAR)
                            .hasItem(Parts.PLATE, Suppliers.MISC_SUPP.get(), NameTransformers.PLATE)
                            .build());
    public static final Resource IRON =
            CottonResources.ResourceArbiter.addOrGetResource(IRON_ID, 
                    new Resource.Builder()
                            .hasBlock(Parts.ORE, Blocks.IRON_ORE, NameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.IRON_ORE, NameTransformers.ORE, true)
                            .hasItem(Parts.INGOT, Items.IRON_INGOT, NameTransformers.INGOT, true)
                            .hasItem(Parts.NUGGET, Items.IRON_NUGGET, NameTransformers.NUGGET, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.IRON_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.IRON_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, Suppliers.makeTieredOre(1), 
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, Suppliers.makeTieredOre(1),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.END_ORE)
                            .hasItem(Parts.DUST, Suppliers.MISC_SUPP.get(), NameTransformers.DUST)
                            .hasItem(Parts.GEAR, Suppliers.MISC_SUPP.get(), NameTransformers.GEAR)
                            .hasItem(Parts.PLATE, Suppliers.MISC_SUPP.get(), NameTransformers.PLATE)
                            .build());
    public static final Resource GOLD =
            CottonResources.ResourceArbiter.addOrGetResource(GOLD_ID,
                    CottonResources.ResourceArbiter.addOrGetResource(DIAMOND_ID, 
                            new Resource.Builder()
                            .hasBlock(Parts.ORE, Blocks.GOLD_ORE, NameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.GOLD_ORE, NameTransformers.ORE, true)
                            .hasItem(Parts.INGOT, Items.GOLD_INGOT, NameTransformers.INGOT, true)
                            .hasItem(Parts.NUGGET, Items.GOLD_NUGGET, NameTransformers.NUGGET, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.GOLD_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.GOLD_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, Suppliers.makeTieredOre(1),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, Suppliers.makeTieredOre(1),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.END_ORE)
                            .hasItem(Parts.DUST, Suppliers.MISC_SUPP.get(), NameTransformers.DUST)
                            .hasItem(Parts.GEAR, Suppliers.MISC_SUPP.get(), NameTransformers.GEAR)
                            .hasItem(Parts.PLATE, Suppliers.MISC_SUPP.get(), NameTransformers.PLATE)
                            .build())
            );
    public static final Resource DIAMOND =
            CottonResources.ResourceArbiter.addOrGetResource(DIAMOND_ID, 
                    new Resource.Builder()
                            .hasBlock(Parts.ORE, Blocks.DIAMOND_ORE, NameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.DIAMOND_ORE, NameTransformers.ORE, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.DIAMOND_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.DIAMOND_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.GEM, Items.DIAMOND, NameTransformers.NO_CHANGE, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, Suppliers.makeTieredOre(1),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, Suppliers.makeTieredOre(1),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.END_ORE)
                            .hasItem(Parts.DUST, Suppliers.MISC_SUPP.get(), NameTransformers.DUST)
                            .hasItem(Parts.GEAR, Suppliers.MISC_SUPP.get(), NameTransformers.GEAR)
                            .hasItem(Parts.PLATE, Suppliers.MISC_SUPP.get(), NameTransformers.PLATE)
                            .build());
    public static final Resource EMERALD =
            CottonResources.ResourceArbiter.addOrGetResource(EMERALD_ID,
                    new Resource.Builder()
                            .hasBlock(Parts.ORE, Blocks.EMERALD_ORE, NameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.EMERALD_ORE, NameTransformers.ORE, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.EMERALD_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.EMERALD_BLOCK, NameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.GEM, Items.EMERALD, NameTransformers.NO_CHANGE, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, Suppliers.makeTieredOre(1),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, Suppliers.makeTieredOre(1),
                                    Suppliers.BUILDING_BLOCK.get(), NameTransformers.END_ORE)
                            .hasItem(Parts.DUST, Suppliers.MISC_SUPP.get(), NameTransformers.DUST)
                            .hasItem(Parts.GEAR, Suppliers.MISC_SUPP.get(), NameTransformers.GEAR)
                            .hasItem(Parts.PLATE, Suppliers.MISC_SUPP.get(), NameTransformers.PLATE)
                            .build());
    public static final Resource NETHERITE =
            CottonResources.ResourceArbiter.addOrGetResource(NETHERITE_ID,
                    new Resource.Builder()
                            .hasItem(Parts.GENERIC, Items.NETHERITE_SCRAP,
                                    (id) -> new Identifier(id.getNamespace(), id.getPath().concat("_scrap")), true)
                            .hasBlock(Parts.ORE, Blocks.ANCIENT_DEBRIS,
                                    (id) -> new Identifier("minecraft", "ancient_debris"), true)
                            .hasItem(Parts.ORE_ITEM, Items.ANCIENT_DEBRIS,
                                    (id) -> new Identifier("minecraft", "ancient_debris"), true)
                            .hasItem(Parts.INGOT, Items.NETHERITE_INGOT, NameTransformers.INGOT, true)
                            .hasItem(Parts.DUST, Suppliers.MISC_SUPP.get(), NameTransformers.DUST)
                            .hasItem(Parts.GEAR, Suppliers.MISC_SUPP.get(), NameTransformers.GEAR)
                            .hasItem(Parts.PLATE, Suppliers.MISC_SUPP.get(), NameTransformers.PLATE)
                            .build());

    public static class Suppliers {
        public static Block makeTieredOre(int tier) {
            return new MippedOreBlock(FabricBlockSettings.of(Material.STONE)
                    .hardness(3.0f)
                    .resistance(3.0f)
                    .breakByTool(FabricToolTags.PICKAXES, tier)
            );
        }
        public static Block makeGlowingTieredOre(int tier, Color color, int litLightLevel) {
            return new GlowingMippedOreBlock(FabricBlockSettings.of(Material.STONE)
                    .hardness(3.0f)
                    .resistance(3.0f)
                    .breakByTool(FabricToolTags.PICKAXES, tier)
                    .luminance(createLightLevelFromBlockState(litLightLevel)),
                    color
            );
        }
        public static final Supplier<Block> METAL_STORAGE_BLOCK_SUPP = () ->
                new Block(FabricBlockSettings.of(Material.METAL)
                        .sounds(BlockSoundGroup.METAL)
                        .hardness(5.0f)
                        .resistance(6.0f)
                );
        public static final Supplier<Block> STONY_STORAGE_BLOCK_SUPP = () ->
                new Block(FabricBlockSettings.of(Material.STONE)
                        .hardness(5.0f)
                        .resistance(6.0f)
                );
        public static final Supplier<Item> MISC_SUPP = () ->
                new Item(new FabricItemSettings().group(ItemGroup.MISC));
        public static final Supplier<Item.Settings> BUILDING_BLOCK = () ->
                new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS);

        public static ToIntFunction<BlockState> createLightLevelFromBlockState(int litLevel) {
            return (blockState) -> {
                return (Boolean)blockState.get(Properties.LIT) ? litLevel : 0;
            };
        }
    }
    public static class Parts {
        public static final Identifier GENERIC = new Identifier("c", "generic");
        public static final Identifier GEM = new Identifier("c", "gem");
        public static final Identifier INGOT = new Identifier("c", "ingot");
        public static final Identifier ORE = new Identifier("c", "ore");
        public static final Identifier ORE_ITEM = new Identifier("c", "ore_item");
        public static final Identifier NUGGET = new Identifier("c", "nugget");
        public static final Identifier STORAGE_BLOCK = new Identifier("c", "storage_block");
        public static final Identifier STORAGE_BLOCK_ITEM = new Identifier("c", "storage_block_item");
        public static final Identifier DUST = new Identifier("c", "dust");
        public static final Identifier END_ORE = new Identifier("c", "end_ore");
        public static final Identifier END_ORE_ITEM = new Identifier("c", "end_ore_item");
        public static final Identifier NETHER_ORE = new Identifier("c", "nether_ore");
        public static final Identifier NETHER_ORE_ITEM = new Identifier("c", "nether_ore_item");
        public static final Identifier GEAR = new Identifier("c", "gear");
        public static final Identifier PLATE = new Identifier("c", "plate");
    }
    public static class NameTransformers {
        public static final Function<Identifier, Identifier> NO_CHANGE = (Identifier id) -> id;
        public static final Function<Identifier, Identifier> INGOT = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_ingot"));
        public static final Function<Identifier, Identifier> ORE = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_ore"));
        public static final Function<Identifier, Identifier> STORAGE_BLOCK = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_block"));
        public static final Function<Identifier, Identifier> NUGGET = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_nugget"));
        public static final Function<Identifier, Identifier> DUST = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_dust"));
        public static final Function<Identifier, Identifier> END_ORE = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_end_ore"));
        public static final Function<Identifier, Identifier> NETHER_ORE = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_nether_ore"));
        public static final Function<Identifier, Identifier> GEAR = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_gear"));
        public static final Function<Identifier, Identifier> PLATE = (Identifier id) ->
                new Identifier(id.getNamespace(), id.getPath().concat("_plate"));
    }
    public static class MippedOreBlock extends OreBlock {
        public MippedOreBlock(Settings settings) {
            super(settings);
        }
    }
    public static class GlowingMippedOreBlock extends MippedOreBlock {
        public static final BooleanProperty LIT = Properties.LIT;
        private final Color particleColor;


        public GlowingMippedOreBlock(Settings settings, Color particleColor) {
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
