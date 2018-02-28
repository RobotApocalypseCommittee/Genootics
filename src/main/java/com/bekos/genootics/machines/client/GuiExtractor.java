package com.bekos.genootics.machines.client;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.machines.common.ContainerExtractor;
import com.bekos.genootics.tile.TileExtractor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiExtractor extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation background = new ResourceLocation(GenooticsMod.MODID, "textures/gui/extractor.png");

    public GuiExtractor(TileExtractor tileEntity, ContainerExtractor container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
