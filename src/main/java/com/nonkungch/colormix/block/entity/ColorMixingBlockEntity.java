package com.nonkungch.colormix.block.entity;

import com.nonkungch.colormix.screen.ColorMixingScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ColorMixingBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final Map<String, Item> MIXING_RECIPES = new HashMap<>();

    static {
        // Basic Dye Mixing Recipes
        addRecipe(Items.RED_DYE, Items.YELLOW_DYE, Items.ORANGE_DYE);
        addRecipe(Items.RED_DYE, Items.WHITE_DYE, Items.PINK_DYE);
        addRecipe(Items.BLUE_DYE, Items.RED_DYE, Items.PURPLE_DYE);
        addRecipe(Items.BLUE_DYE, Items.WHITE_DYE, Items.LIGHT_BLUE_DYE);
        addRecipe(Items.BLUE_DYE, Items.GREEN_DYE, Items.CYAN_DYE);
        addRecipe(Items.YELLOW_DYE, Items.BLUE_DYE, Items.GREEN_DYE);
        addRecipe(Items.BLACK_DYE, Items.WHITE_DYE, Items.GRAY_DYE);
        addRecipe(Items.GRAY_DYE, Items.WHITE_DYE, Items.LIGHT_GRAY_DYE);
    }

    private static void addRecipe(Item input1, Item input2, Item output) {
        String key1 = Registries.ITEM.getId(input1).toString() + "+" + Registries.ITEM.getId(input2).toString();
        String key2 = Registries.ITEM.getId(input2).toString() + "+" + Registries.ITEM.getId(input1).toString();
        MIXING_RECIPES.put(key1, output);
        MIXING_RECIPES.put(key2, output);
    }

    public ColorMixingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COLOR_MIXING_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.colormix.color_mixing_block");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ColorMixingScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    public void tick() {
        if (this.world == null || this.world.isClient) return;

        ItemStack stack1 = inventory.get(0);
        ItemStack stack2 = inventory.get(1);
        ItemStack resultStack = inventory.get(2);

        if (!stack1.isEmpty() && !stack2.isEmpty()) {
            Item outputItem = getOutput(stack1, stack2);
            if (outputItem != null) {
                if (resultStack.isEmpty() || (resultStack.isOf(outputItem) && resultStack.getCount() < resultStack.getMaxCount())) {
                    if (resultStack.isEmpty()) {
                        inventory.set(2, new ItemStack(outputItem, 1));
                    } else {
                        resultStack.increment(1);
                    }
                    stack1.decrement(1);
                    stack2.decrement(1);
                    markDirty();
                }
            }
        }
    }

    private Item getOutput(ItemStack stack1, ItemStack stack2) {
        String key = Registries.ITEM.getId(stack1.getItem()).toString() + "+" + Registries.ITEM.getId(stack2.getItem()).toString();
        return MIXING_RECIPES.get(key);
    }
}
