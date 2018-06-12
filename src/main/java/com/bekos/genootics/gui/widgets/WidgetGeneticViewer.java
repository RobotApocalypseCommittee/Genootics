package com.bekos.genootics.gui.widgets;


import com.bekos.genootics.gui.helpers.Renderer;

import java.awt.*;
import java.util.HashMap;

public class WidgetGeneticViewer extends Widget {

    private HashMap<String, Double> genes;
    private HashMap<String, Double> genesDominance;

    public WidgetGeneticViewer(int width, int height, HashMap<String, Double> genes, HashMap<String, Double> genesDominance) {
        super(width, height);
        updateGenetics(genes, genesDominance);
    }

    public void updateGenetics(HashMap<String, Double> genes, HashMap<String, Double> genesDominance) {
        this.genes = genes;
        this.genesDominance = genesDominance;
    }

    private Color calculateColour(Double number) {
        return Color.getHSBColor(number.floatValue(), 1, 1);

    }

    @Override
    public void draw(int mouseX, int mouseY) {
        int x = scaledLocation.x;
        int y = scaledLocation.y;

        int len = this.genes.size();
        if (len > 0) {
            // This will lose some space at the bottom, naturally
            int pixels = (int) ((float) height / (float) len);
            int i = 0;
            for (String gene: this.genes.keySet()) {
                Renderer.drawRect(x, y+(i*pixels), width, ((i+1)*pixels), calculateColour(genesDominance.get(gene)));
                System.out.println(x);
                System.out.println(y+(i*pixels));
                System.out.println(x+width);
                System.out.println(y+((i+1)*pixels));
            }
        }
    }

    @Override
    public void drawTooltip(int mouseX, int mouseY) {

    }
}
