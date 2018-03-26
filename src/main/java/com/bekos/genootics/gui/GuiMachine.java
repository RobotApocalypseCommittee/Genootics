package com.bekos.genootics.gui;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.machines.common.ContainerMachine;
import com.bekos.genootics.tile.TileMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class GuiMachine extends GuiContainer {

    public TileMachine tile;
    private static final ResourceLocation widgetTexture = new ResourceLocation(GenooticsMod.MODID, "textures/gui/guiwidgets.png");


    public GuiMachine(TileMachine tileEntity, ContainerMachine container) {
        super(container);
        this.tile = tileEntity;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

    protected void drawPowerBar(int x, int y) {
        IEnergyStorage capability = this.tile.getCapability(CapabilityEnergy.ENERGY, null);
        int h = Math.round(
                ((float) capability.getEnergyStored()/(float) capability.getMaxEnergyStored()) * (float) 59
                );
        mc.getTextureManager().bindTexture(widgetTexture);
        drawTexturedModalRect(x, y+(59-h), 13, 95-h, 10, h);
    }
}
