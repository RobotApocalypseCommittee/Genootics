package com.bekos.genootics.machines.common;

import com.bekos.genootics.tile.TileExtractor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerExtractor extends ContainerMachine<TileExtractor> {
    public ContainerExtractor(IInventory playerInventory, TileExtractor tileExtractor) {
        super(playerInventory, tileExtractor);
    }

    @Override
    protected void addOwnSlots() {
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int slot = 0; slot < TileExtractor.SIZE; slot++) {
            this.addSlotToContainer(new SlotItemHandler(itemHandler, slot, slot*18+9, 6));
        }
    }
}
