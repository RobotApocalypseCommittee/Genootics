package com.bekos.genootics.gui.widgets;

import com.bekos.genootics.gui.helpers.Point;

import java.util.LinkedHashSet;
import java.util.Set;

public interface IWidget {
    IWidget addWidget(IWidget widget, Point location);

    void setDimensions(int width, int height);

    void layout(Point location, float scale);

    void draw(int mouseX, int mouseY);

    void drawTooltip(int mouseX, int mouseY);
}
