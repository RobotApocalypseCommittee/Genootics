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

package com.bekos.genootics.block;

import com.bekos.genootics.tile.TileEnzymeHousing;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnzymeHousing extends BlockMachine {
    public BlockEnzymeHousing() {
        super("machineEnzymeHousing");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEnzymeHousing();
    }

    @Override
    public boolean checkTileEntity(TileEntity te) {
        return te instanceof TileEnzymeHousing;
    }
}