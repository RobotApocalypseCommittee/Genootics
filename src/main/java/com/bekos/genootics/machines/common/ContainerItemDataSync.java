/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2018  Robot Apocalypse Committee
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.bekos.genootics.machines.common;

import com.bekos.genootics.tile.TileItemDataSync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Arrays;

public abstract class ContainerItemDataSync<TE extends TileItemDataSync> extends Container {
    protected TE te;

    private short[] cachedValues;

    public ContainerItemDataSync(IInventory playerInventory, TE te) {
        this.te = te;
        System.out.println(this.te);
        addOwnSlots();
        addPlayerSlots(playerInventory);
        cachedValues = new short[te.getFieldsLength()];
        Arrays.fill(cachedValues, (short) -1);
    }

    protected void addPlayerSlots(IInventory playerInventory) {

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    protected abstract void addOwnSlots();

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        boolean[] changedValues = new boolean[te.getFieldsLength()];
        for (int i = 0; i < te.getFieldsLength(); i++) {
            if (cachedValues[i] != te.getField((byte) i)) {
                changedValues[i] = true;
                cachedValues[i] = te.getField((byte) i);
            }
        }
        for (IContainerListener listener : listeners) {
            for (int i = 0; i < te.getFieldsLength(); i++) {
                if (changedValues[i]) {
                    listener.sendWindowProperty(this, i, cachedValues[i]);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        te.setField((byte) id, (short) data);
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < te.SIZE) {
                // From machine to inventory
                if (!this.mergeItemStack(itemstack1, te.SIZE, this.inventorySlots.size(), true)) {
                    return null;
                }
                // From inventory to machine
            } else if (!this.mergeItemStack(itemstack1, 0, te.SIZE, false)) {
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
}
