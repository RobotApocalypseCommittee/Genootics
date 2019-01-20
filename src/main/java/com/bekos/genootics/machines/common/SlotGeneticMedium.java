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

import com.bekos.genootics.item.BasePetriDish;
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
    public boolean isItemValid(ItemStack stack) {
        return (stack.getItem() instanceof BasePetriDish && !(!allowsEmpty && ((BasePetriDish) stack.getItem()).isEmpty(stack)));
    }


}
