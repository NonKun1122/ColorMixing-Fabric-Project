package com.nonkungch.colormix;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorMix implements ModInitializer {
    public static final String MODID = "colormix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("ColorMix initializing for Fabric");
        // ลงทะเบียน recipe serializer, event listeners หรือโค้ดอื่น ๆ ที่ต้องการ
    }
}
