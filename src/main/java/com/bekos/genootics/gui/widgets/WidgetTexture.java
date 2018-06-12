package com.bekos.genootics.gui.widgets;

import com.bekos.genootics.gui.helpers.Renderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

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
