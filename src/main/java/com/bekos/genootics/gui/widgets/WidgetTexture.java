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

package com.bekos.genootics.gui.widgets;

import com.bekos.genootics.gui.helpers.Renderer;
import net.minecraft.util.ResourceLocation;

public class WidgetTexture extends Widget {
    private ResourceLocation texture;
    private int texX, texY;

    public WidgetTexture(int width, int height, ResourceLocation texture, int texX, int texY) {
        super(width, height);
        this.texture = texture;
        this.texX = texX;
        this.texY = texY;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Renderer.bindTexture(texture);
        Renderer.drawTexturedRect(this.scaledLocation.x, this.scaledLocation.y, texX, texY, width, height);
    }
}
