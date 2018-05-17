package com.bekos.genootics.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.HashMap;

public class WidgetGeneticViewer extends Widget {

    private HashMap<String, Double> genes;
    private HashMap<String, Double> genesDominance;
    public WidgetGeneticViewer(Minecraft mc, Gui gui) {
        super(mc, gui);

    }

    public void updateGenetics(HashMap<String, Double> genes, HashMap<String, Double> genesDominance) {
        this.genes = genes;
        this.genesDominance = genesDominance;
    }

    private int calculateColour(Double dominance) {
        return 0xFF0000;
    }


    @Override
    public void renderForeground(int x, int y, int h, int w) {
        int len = this.genes.size();

        if (len == 0) {
            return;
        }

        // This will lose some space at the bottom, naturally
        int pixels = (int) ((float) h / (float) len);
        int i = 0;
        System.out.println(len);
        for (String gene: this.genes.keySet()) {
            Gui.drawRect(x, y+(i*pixels), x+w, y+((i+1)*pixels), calculateColour(genesDominance.get(gene)));
            System.out.println("Draw Rect");
        }
    }

    @Override
    public void renderBackground(int x, int y, int h, int w) {

    }
}
