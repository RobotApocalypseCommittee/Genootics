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
