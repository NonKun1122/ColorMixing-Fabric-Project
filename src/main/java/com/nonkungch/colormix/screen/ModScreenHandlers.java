package com.nonkungch.colormix.screen;

import com.nonkungch.colormix.ColorMix;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<ColorMixingScreenHandler> COLOR_MIXING_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(ColorMix.MODID, "color_mixing"),
                    new ScreenHandlerType<>(ColorMixingScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerScreenHandlers() {
        ColorMix.LOGGER.info("Registering Screen Handlers for " + ColorMix.MODID);
    }
}
