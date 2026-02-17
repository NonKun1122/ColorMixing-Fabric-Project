package com.nonkungch.colormix.block.entity;

import com.nonkungch.colormix.ColorMix;
import com.nonkungch.colormix.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<ColorMixingBlockEntity> COLOR_MIXING_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ColorMix.MODID, "color_mixing_be"),
                    BlockEntityType.Builder.create(ColorMixingBlockEntity::new,
                            ModBlocks.COLOR_MIXING_BLOCK).build());

    public static void registerBlockEntities() {
        ColorMix.LOGGER.info("Registering Block Entities for " + ColorMix.MODID);
    }
}
