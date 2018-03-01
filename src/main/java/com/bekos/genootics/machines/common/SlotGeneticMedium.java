package com.bekos.genootics.machines.common;

import com.bekos.genootics.item.BasePetriDish;
import com.bekos.genootics.item.ItemSyringe;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotGeneticMedium extends SlotItemHandler {

    private boolean allowsEmpty;
    public SlotGeneticMedium(IItemHandler itemHandler, int index, int xPosition, int yPosition, boolean allowsEmpty) {
        super(itemHandler, index, xPosition, yPosition);
        this.allowsEmpty = allowsEmpty;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return (stack.getItem() instanceof BasePetriDish && !(!allowsEmpty && ((BasePetriDish) stack.getItem()).isEmpty(stack)));
    }


}
