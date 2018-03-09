package com.bekos.genootics.machines.common;

import com.bekos.genootics.tile.TileExtractor;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerExtractor extends ContainerMachine<TileExtractor> {
    public ContainerExtractor(IInventory playerInventory, TileExtractor tileExtractor) {
        super(playerInventory, tileExtractor);
        System.out.print(this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getSlots());
    }

    @Override
    protected void addOwnSlots() {
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        // Redstone
        this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, 25, 37));
        // Syringe
        this.addSlotToContainer(new SlotItemHandler(itemHandler, 1, 56, 17));
        // Empty Storage
        this.addSlotToContainer(new SlotGeneticMedium(itemHandler, 2, 56, 53, true));
        // Filled Storage
        this.addSlotToContainer(new SlotGeneticMedium(itemHandler, 3, 116, 35, false));
    }
}
