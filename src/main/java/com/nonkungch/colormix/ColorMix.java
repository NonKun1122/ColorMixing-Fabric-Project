package com.nonkungch.colormix;

import com.nonkungch.colormix.block.ModBlocks;
import com.nonkungch.colormix.block.entity.ModBlockEntities;
import com.nonkungch.colormix.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorMix implements ModInitializer {
    public static final String MODID = "colormix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("ColorMix initializing for Fabric");
        
        ModBlocks.registerModBlocks();
        ModBlockEntities.registerBlockEntities();
        ModScreenHandlers.registerScreenHandlers();
    }
}
