package com.bekos.genootics.machines.common;

import com.bekos.genootics.tile.TileMachine;
import com.bekos.genootics.tile.energy.EnergyHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class ContainerMachine<TE extends TileMachine> extends Container {
    protected TE te;

    protected int cachedEnergyStored;

    public ContainerMachine(IInventory playerInventory, TE te) {
        this.te = te;
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    protected void addPlayerSlots(IInventory playerInventory) {

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    protected abstract void addOwnSlots();

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TE.SIZE) {
                // From machine to inventory
                if (!this.mergeItemStack(itemstack1, TE.SIZE, this.inventorySlots.size(), true)) {
                    return null;
                }
                // From inventory to machine
            } else if (!this.mergeItemStack(itemstack1, 0, TE.SIZE, false)) {
                return null;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        boolean energyHasChanged = false;

        int newEnergy = te.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored();
        if (newEnergy != cachedEnergyStored) {
            this.cachedEnergyStored = newEnergy;
            energyHasChanged = true;
        }
        // go through the list of listeners (players using this container) and update them if necessary
        for (IContainerListener listener : this.listeners) {
            if (energyHasChanged) {
                listener.sendWindowProperty(this, 0, this.cachedEnergyStored);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        switch (id) {
            case 0: ((EnergyHandler) te.getCapability(CapabilityEnergy.ENERGY, null)).setEnergyStored(data);
        }
    }
}
