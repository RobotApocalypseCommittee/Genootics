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
import com.bekos.genootics.gui.widgets.IWidgetKeyInteractable;
import com.bekos.genootics.gui.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;


public class GuiModularScreen extends GuiScreen implements IGuiModular {
    protected int scale = 1;
    protected Set<Widget> children = new LinkedHashSet<>();
    public int xSize;
    public int ySize;

    public GuiModularScreen(int width, int height) {
        super();
        this.xSize = width;
        this.ySize = height;
    }


    @Override
    public GuiModularScreen addWidget(Widget widget, Point location) {
        children.add(widget);
        widget.rawLocation = location;
        layout();
        return this;
    }

    @Override
    public GuiModularScreen layout() {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int scaledHeight = resolution.getScaledHeight();
        int scaledWidth = resolution.getScaledWidth();
        Point origin = new Point((scaledWidth - xSize) / 2, (scaledHeight - ySize) / 2);
        System.out.print("Origin: ");
        System.out.print(origin.x);
        System.out.print(", ");
        System.out.println(origin.y);

        for (Widget w : children) {
            w.layout(new Point(w.rawLocation).translate(origin));
        }
        return this;

    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);

        layout();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.mc == null) {
            this.mc = Minecraft.getMinecraft();
        }
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        children.forEach(e -> e.draw(mouseX, mouseY));
        children.forEach(e -> e.drawTooltip(mouseX, mouseY));
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        children.stream()
                .filter(e -> e instanceof IWidgetKeyInteractable)
                .map(e -> (IWidgetKeyInteractable) e)
                .forEach(e -> e.keyPress(keyCode, typedChar));
    }
}
