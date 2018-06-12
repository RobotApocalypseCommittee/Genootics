package com.bekos.genootics.gui.widgets;


import com.bekos.genootics.gui.helpers.Renderer;

import java.awt.*;
import java.util.HashMap;

public class WidgetGeneticViewer extends Widget {

    private HashMap<String, Double> genes;
    private HashMap<String, Double> genesDominance;

    public WidgetGeneticViewer(int width, int height) {
        super(width, height);
    }

    public void updateGenetics(HashMap<String, Double> genes, HashMap<String, Double> genesDominance) {
        this.genes = genes;
        this.genesDominance = genesDominance;
    }

    private Color calculateColour(Double number) {
        int r = (int)(Math.random() * ((255) + 1));
        int g = (int)(Math.random() * ((255) + 1));
        int b = (int)(Math.random() * ((255) + 1));
        return new Color(0xFF000000 | r << 16 | g << 8 | b, true);

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
                Renderer.drawRect(x, y+(i*pixels), x+width, y+((i+1)*pixels), calculateColour(genesDominance.get(gene)));
                System.out.print(x);
                System.out.print(y+(i*pixels));
                System.out.print(x+width);
                System.out.println(y+((i+1)*pixels));
            }
        }
    }

    @Override
    public void drawTooltip(int mouseX, int mouseY) {

    }
}
