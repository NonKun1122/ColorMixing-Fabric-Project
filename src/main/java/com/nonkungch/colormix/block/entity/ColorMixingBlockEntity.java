package com.nonkungch.colormix.block.entity;

import com.nonkungch.colormix.screen.ColorMixingScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ColorMixingBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

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
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, inventory, registries);
    }

    public void tick() {
        if (world == null || world.isClient) return;

        ItemStack input1 = inventory.get(0);
        ItemStack input2 = inventory.get(1);
        ItemStack output = inventory.get(2);

        if (!input1.isEmpty() && !input2.isEmpty()) {
            ItemStack result = getMixResult(input1, input2);
            if (!result.isEmpty()) {
                if (output.isEmpty() || (ItemStack.areItemsEqual(output, result) && output.getCount() < output.getMaxCount())) {
                    if (output.isEmpty()) {
                        inventory.set(2, result.copy());
                    } else {
                        output.increment(1);
                    }
                    input1.decrement(1);
                    input2.decrement(1);
                    markDirty();
                }
            }
        }
    }

    private ItemStack getMixResult(ItemStack stack1, ItemStack stack2) {
        // Simple dye mixing logic
        String item1 = Registries.ITEM.getId(stack1.getItem()).toString();
        String item2 = Registries.ITEM.getId(stack2.getItem()).toString();

        if (isDye(item1, "red") && isDye(item2, "yellow") || isDye(item1, "yellow") && isDye(item2, "red")) {
            return new ItemStack(net.minecraft.item.Items.ORANGE_DYE);
        }
        if (isDye(item1, "blue") && isDye(item2, "red") || isDye(item1, "red") && isDye(item2, "blue")) {
            return new ItemStack(net.minecraft.item.Items.PURPLE_DYE);
        }
        if (isDye(item1, "blue") && isDye(item2, "yellow") || isDye(item1, "yellow") && isDye(item2, "blue")) {
            return new ItemStack(net.minecraft.item.Items.GREEN_DYE);
        }
        if (isDye(item1, "white") && isDye(item2, "red") || isDye(item1, "red") && isDye(item2, "white")) {
            return new ItemStack(net.minecraft.item.Items.PINK_DYE);
        }
        // Add more as needed...
        return ItemStack.EMPTY;
    }

    private boolean isDye(String itemId, String color) {
        return itemId.equals("minecraft:" + color + "_dye");
    }
}
