package com.bekos.genootics.gui.widgets;

import com.bekos.genootics.gui.helpers.Renderer;

import java.awt.*;

public class WidgetRectangle extends Widget {
    public WidgetRectangle(int width, int height) {
        super(width, height);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        Renderer.drawRect(scaledLocation.x, scaledLocation.y, width, height, Color.CYAN);
    }
}
