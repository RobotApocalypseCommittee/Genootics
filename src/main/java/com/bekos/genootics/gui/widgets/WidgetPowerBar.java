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

package com.bekos.genootics.gui.widgets;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.gui.helpers.Renderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.awt.*;

public class WidgetPowerBar extends Widget {
    TileEntity tile;
    private static final ResourceLocation widgetTexture = new ResourceLocation(GenooticsMod.MODID, "textures/gui/guiwidgets.png");

    public WidgetPowerBar(int width, int height, TileEntity tileEntity) {
        super(width, height);
        this.tile = tileEntity;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        IEnergyStorage capability = this.tile.getCapability(CapabilityEnergy.ENERGY, null);
        int bar_height = Math.round(
                ((float) capability.getEnergyStored() / (float) capability.getMaxEnergyStored()) * (float) height
        );
        Renderer.drawBoundingEmbeddedBox(this.scaledLocation.x, this.scaledLocation.y, width, height);
        Renderer.drawGradientRect(0, this.scaledLocation.x, this.scaledLocation.y, this.scaledLocation.x + width, this.scaledLocation.y + height, Renderer.toARGB(Color.GREEN), Renderer.toARGB(Color.RED));
        Renderer.drawRect(scaledLocation.x, scaledLocation.y, width, (height - bar_height), Color.decode("#8B8B8B"));
    }
}
