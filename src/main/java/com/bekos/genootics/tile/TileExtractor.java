/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2019  Robot Apocalypse Committee
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

package com.bekos.genootics.tile;

import com.bekos.genootics.item.BasePetriDish;
import com.bekos.genootics.item.ItemSyringe;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

public class TileExtractor extends TileMachine {
    // Number of itemstacks stored.
    public static final int SIZE = 4;

    public TileExtractor() {
        super(SIZE, 10000, 500, 100);
    }



    @Override
    public boolean canWork() {
        ItemStack syringeStack = this.itemStackHandler.getStackInSlot(1);
        return super.canWork() && (!syringeStack.isEmpty() &&
                ((ItemSyringe) syringeStack.getItem()).isBloody(syringeStack) &&
                !this.itemStackHandler.getStackInSlot(2).isEmpty() &&
                this.itemStackHandler.getStackInSlot(3).isEmpty()
        );
    }

    @Override
    public void doWork() {

        if (this.canWork()) {
            super.doWork();
            if (this.ticksRemaining == 0) {

                System.out.println("COMPLETED");
                ItemStack syringe = this.itemStackHandler.getStackInSlot(1);
                ItemSyringe sitem = (ItemSyringe) syringe.getItem();
                ItemStack petri = this.itemStackHandler.getStackInSlot(2);
                List<NBTTagList> genesinfo = sitem.getBloodGeneInformation(syringe);
                if (genesinfo.size() >= 2) {
                    BasePetriDish.setGenes(petri, genesinfo.get(0), genesinfo.get(1));
                    this.itemStackHandler.setStackInSlot(3, petri.copy());
                    this.itemStackHandler.extractItem(2, 1, false);
                    this.itemStackHandler.extractItem(1, 1, false);

                }
            } else {
                this.ticksRemaining--;
            }
        }
    }

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (this.canWork()) {
                if (!this.isWorking) {
                    this.ticksRemaining = 200;
                    this.isWorking = true;
                }
                this.doWork();
            } else {
                this.isWorking = false;
                this.ticksRemaining = 0;
            }

            if (!itemStackHandler.getStackInSlot(0).isEmpty() && itemStackHandler.getStackInSlot(0).getItem() instanceof ItemRedstone) {
                System.out.println("Charge with redstone");
                if (energyStorage.receiveEnergy(500, true) == 500) {
                    energyStorage.receiveEnergy(500, false);
                    itemStackHandler.extractItem(0, 1, false);
                }
            }
        }
    }
}
