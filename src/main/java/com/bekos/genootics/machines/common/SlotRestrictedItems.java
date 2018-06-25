package com.bekos.genootics.machines.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Arrays;
import java.util.List;

public class SlotRestrictedItems extends SlotItemHandler {
    private List<Item> allowedItems;

    public SlotRestrictedItems(IItemHandler itemHandler, int index, int xPosition, int yPosition, List<Item> allowedItems) {
        super(itemHandler, index, xPosition, yPosition);
        this.allowedItems = allowedItems;
    }

    public SlotRestrictedItems(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item allowedItem) {
        this(itemHandler, index, xPosition, yPosition, Arrays.asList(allowedItem));
    }


    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return allowedItems.contains(itemStack.getItem());
    }
}
