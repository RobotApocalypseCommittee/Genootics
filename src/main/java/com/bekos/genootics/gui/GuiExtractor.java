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

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.machines.common.ContainerExtractor;
import com.bekos.genootics.tile.TileExtractor;
import net.minecraft.util.ResourceLocation;


public class GuiExtractor extends GuiMachine {

    public GuiExtractor(TileExtractor tileEntity, ContainerExtractor container) {
        super(176, 166, tileEntity, container);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int l = this.tile.getTicksRemaining() == 0 ? 0 : 24 - this.tile.getTicksRemaining() * 24 / 200;

        drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);

    }

    @Override
    public ResourceLocation get_background() {
        return new ResourceLocation(GenooticsMod.MODID, "textures/gui/extractor.png");
    }
}
