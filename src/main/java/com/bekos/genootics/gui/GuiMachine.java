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

package com.bekos.genootics.gui;

import com.bekos.genootics.gui.helpers.Point;
import com.bekos.genootics.gui.widgets.WidgetPowerBar;
import com.bekos.genootics.gui.widgets.WidgetTexture;
import com.bekos.genootics.machines.common.ContainerMachine;
import com.bekos.genootics.tile.TileMachine;
import net.minecraft.util.ResourceLocation;

public abstract class GuiMachine extends GuiModularContainer {

    public TileMachine tile;


    public GuiMachine(int width, int height, TileMachine tileEntity, ContainerMachine container) {
        super(width, height, container);
        this.tile = tileEntity;
        this.addWidget(new WidgetTexture(width, height, get_background(), 0, 0), new Point(0, 0));
        this.addWidget(new WidgetPowerBar(10, 59, this.tile), new Point(157, 14));
    }

    public abstract ResourceLocation get_background();
}
