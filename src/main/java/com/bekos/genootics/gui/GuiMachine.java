package com.bekos.genootics.gui;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.gui.helpers.Point;
import com.bekos.genootics.gui.widgets.WidgetPowerBar;
import com.bekos.genootics.gui.widgets.WidgetTexture;
import com.bekos.genootics.machines.common.ContainerMachine;
import com.bekos.genootics.tile.TileMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class GuiMachine extends GuiModularContainer {

    public TileMachine tile;


    public GuiMachine(int width, int height, TileMachine tileEntity, ContainerMachine container) {
        super(width, height, container);
        this.tile = tileEntity;
        this.addWidget(new WidgetTexture(width, height, get_background(), 0, 0), new Point(0, 0));
        this.addWidget(new WidgetPowerBar(10, 59, this.tile), new Point(157, 14));
    }
    public abstract ResourceLocation get_background();
}
