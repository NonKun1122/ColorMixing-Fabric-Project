package com.nonkungch.colormix;

import com.nonkungch.colormix.screen.ColorMixingScreen;
import com.nonkungch.colormix.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ColorMixClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.COLOR_MIXING_SCREEN_HANDLER, ColorMixingScreen::new);
    }
}
