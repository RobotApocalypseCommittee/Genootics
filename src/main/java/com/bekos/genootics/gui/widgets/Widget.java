package com.bekos.genootics.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class Widget {
    protected Minecraft mc;
    protected Gui gui;

    public Widget(Minecraft mc, Gui gui) {
        this.gui = gui;
        this.mc = mc;
    }
    public abstract void renderForeground(int x, int y, int w, int h);
    public abstract void renderBackground(int x, int y, int w, int h);
}
