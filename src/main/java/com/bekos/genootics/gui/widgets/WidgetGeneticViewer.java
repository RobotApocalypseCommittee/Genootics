package com.bekos.genootics.gui.widgets;


import com.bekos.genootics.genetics.Gene;
import com.bekos.genootics.gui.helpers.Renderer;

import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WidgetGeneticViewer extends Widget {

    private HashMap<Gene, Double> genes;
    private HashMap<Gene, Double> genesDominance;
    private List<Gene> geneOrder;
    private int level;

    public WidgetGeneticViewer(int width, int height, HashMap<Gene, Double> genes, HashMap<Gene, Double> genesDominance, int level) {
        super(width, height);
        geneOrder = new ArrayList<>();
        this.level = level;
        updateGenetics(genes, genesDominance);
    }

    public void updateGenetics(HashMap<Gene, Double> genes, HashMap<Gene, Double> genesDominance) {
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
            geneOrder.clear();
            int i;
            for (Gene gene: this.genes.keySet()) {
                geneOrder.add(gene);
                i = geneOrder.size()-1;
                Renderer.drawRect(x, y+(i*pixels), width, pixels, gene.getColor());
            }
        }
    }

    @Override
    public void drawTooltip(int mouseX, int mouseY) {
        if (Renderer.isWithinBounds(mouseX, mouseY, this.scaledLocation.x, this.scaledLocation.y,
                this.scaledLocation.x+ width, this.scaledLocation.y+height)) {
            int i = (int) (((float)(mouseY - this.scaledLocation.y) / (float) height) * (float) this.genes.size());
            ArrayList<String> message = new ArrayList<>();
            message.add(geneOrder.get(i).getLocalisedName());
            if (level > 0){
                message.add(TextFormatting.RED + genesDominance.get(geneOrder.get(i)).toString());
            }
            if (level > 1) {
                message.add(TextFormatting.GOLD + genes.get(geneOrder.get(i)).toString());
            }
            Renderer.drawHoveringText(message, mouseX, mouseY);
        }

    }
}
