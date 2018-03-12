package com.bekos.genootics.machines.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotOneItem extends SlotItemHandler {
    private final Item allowedItem;

    public SlotOneItem(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item allowedItem) {
        super(itemHandler, index, xPosition, yPosition);
        this.allowedItem = allowedItem;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack){
        return itemStack.getItem() == allowedItem;
    }
}
