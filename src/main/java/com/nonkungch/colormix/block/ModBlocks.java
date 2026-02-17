package com.nonkungch.colormix.block;

import com.nonkungch.colormix.ColorMix;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block COLOR_MIXING_BLOCK = registerBlock("color_mixing_block",
            new ColorMixingBlock(AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ColorMix.MODID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(ColorMix.MODID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        ColorMix.LOGGER.info("Registering ModBlocks for " + ColorMix.MODID);
    }
}
