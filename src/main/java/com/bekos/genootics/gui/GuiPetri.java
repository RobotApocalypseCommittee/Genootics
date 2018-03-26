package com.bekos.genootics.gui;

import com.bekos.genootics.gui.widgets.WidgetGeneticViewer;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.HashMap;

public class GuiPetri extends GuiScreen {

    private int xSize = 175;
    private int ySize = 166;

    public WidgetGeneticViewer geneticViewerLeft;
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.geneticViewerLeft.renderForeground(i, j, 30, 50);
        System.out.println("RENDER SCREEN");
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.geneticViewerLeft = new WidgetGeneticViewer(this.mc, this);
        HashMap<String , Double> genes = new HashMap<String, Double>();
        genes.put("Magic", 30d);
        HashMap<String , Double> genesDom = new HashMap<String, Double>();
        genesDom.put("Magic", 30d);

        this.geneticViewerLeft.updateGenetics(genes, genesDom);
        System.out.println("INIT GUI");
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }
}
