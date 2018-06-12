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

    public void drawTooltip(int mouseX, int mouseY){
        children.forEach(e -> e.drawTooltip(mouseX, mouseY));
    }
}
