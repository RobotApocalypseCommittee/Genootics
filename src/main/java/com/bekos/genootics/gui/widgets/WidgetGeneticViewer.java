/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2019  Robot Apocalypse Committee
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.bekos.genootics.gui.widgets;


import com.bekos.genootics.genetics.Gene;
import com.bekos.genootics.gui.helpers.Renderer;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.round;

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
        Renderer.drawBoundingEmbeddedBox(x, y, width, height);

        int len = this.genes.size();
        if (len > 0) {
            // This will lose some space at the bottom, naturally
            int pixels = (int) ((float) height / (float) len);
            geneOrder.clear();
            int i;
            for (Gene gene : this.genes.keySet()) {
                geneOrder.add(gene);
                i = geneOrder.size() - 1;
                Renderer.drawRect(x, y + (i * pixels), width, pixels, gene.getColor());
            }
        }
    }

    @Override
    public void drawTooltip(int mouseX, int mouseY) {
        if (Renderer.isWithinBounds(mouseX, mouseY, this.scaledLocation.x, this.scaledLocation.y,
                this.scaledLocation.x + width, this.scaledLocation.y + height) && this.genes.size() > 0) {
            System.out.println(((float) (mouseY - this.scaledLocation.y) / (float) height));
            int i = round(((float) (mouseY - this.scaledLocation.y) / (float) height) * (float) (this.genes.size() - 1));
            ArrayList<String> message = new ArrayList<>();
            message.add(geneOrder.get(i).getLocalisedName());
            if (level > 0) {
                message.add(TextFormatting.RED + genesDominance.get(geneOrder.get(i)).toString());
            }
            if (level > 1) {
                message.add(TextFormatting.GOLD + genes.get(geneOrder.get(i)).toString());
            }
            Renderer.drawHoveringText(message, mouseX, mouseY);
        }

    }
}
