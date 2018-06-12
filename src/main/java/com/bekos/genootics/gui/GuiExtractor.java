package com.bekos.genootics.gui;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.machines.common.ContainerExtractor;
import com.bekos.genootics.tile.TileExtractor;
import net.minecraft.util.ResourceLocation;

public class GuiExtractor extends GuiMachine {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 166;


    private static final ResourceLocation background = new ResourceLocation(GenooticsMod.MODID, "textures/gui/extractor.png");

    public GuiExtractor(TileExtractor tileEntity, ContainerExtractor container) {
        super(tileEntity, container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int l = this.tile.getTicksRemaining() == 0 ? 0 : 24-this.tile.getTicksRemaining()*24/200;

        drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);

        drawPowerBar(guiLeft+157, guiTop+14);
    }
}