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

package com.bekos.genootics.machines.common;

import com.bekos.genootics.tile.TileExtractor;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Arrays;

public class ContainerExtractor extends ContainerMachine<TileExtractor> {
    public ContainerExtractor(IInventory playerInventory, TileExtractor tileExtractor) {
        super(playerInventory, tileExtractor);
    }

    @Override
    protected void addOwnSlots() {
        System.out.println(this.te);
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        // Redstone
        this.addSlotToContainer(new SlotRestrictedItems(itemHandler, 0, 25, 37, Arrays.asList(
                Item.REGISTRY.getObject(new ResourceLocation("minecraft:redstone")),
                Item.REGISTRY.getObject(new ResourceLocation("minecraft:redstone_block"))
        )));
        // Syringe
        this.addSlotToContainer(new SlotItemHandler(itemHandler, 1, 56, 17));
        // Empty Storage
        this.addSlotToContainer(new SlotGeneticMedium(itemHandler, 2, 56, 53, true));
        // Filled Storage
        this.addSlotToContainer(new SlotGeneticMedium(itemHandler, 3, 116, 35, false));
    }
}
