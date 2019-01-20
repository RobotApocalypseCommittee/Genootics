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


import com.bekos.genootics.gui.helpers.Point;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Widget {
    public Point rawLocation;
    protected Point scaledLocation;
    int width;
    int height;
    protected Set<Widget> children = new LinkedHashSet<>();

    public Widget(int width, int height) {
        setDimensions(width, height);
    }

    public Widget addWidget(Widget widget, Point location) {
        children.add(widget);
        widget.rawLocation = location;
        return this;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Widget layout(Point location) {
        this.scaledLocation = location;

        for (Widget w : children) {
            w.layout(new Point(w.rawLocation).translate(location));
        }
        return this;
    }

    public void draw(int mouseX, int mouseY) {
        children.forEach(e -> e.draw(mouseX, mouseY));
    }

    public void drawTooltip(int mouseX, int mouseY) {
        children.forEach(e -> e.drawTooltip(mouseX, mouseY));
    }
}
