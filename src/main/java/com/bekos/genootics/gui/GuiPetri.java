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

package com.bekos.genootics.gui;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.genetics.Gene;
import com.bekos.genootics.gui.helpers.Point;
import com.bekos.genootics.gui.widgets.WidgetGeneticViewer;
import com.bekos.genootics.gui.widgets.WidgetTexture;
import com.bekos.genootics.item.BasePetriDish;
import com.bekos.genootics.util.NBTParser;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class GuiPetri extends GuiModularScreen {

    private static final ResourceLocation background = new ResourceLocation(GenooticsMod.MODID,
            "textures/gui/petri.png");

    private List<Map<Gene, Double>> genes = new ArrayList<>(Arrays.asList(new HashMap<>(), new HashMap<>()));
    private List<Map<Gene, Double>> genesDom = new ArrayList<>(Arrays.asList(new HashMap<>(), new HashMap<>()));

    public GuiPetri(ItemStack petriDish) {
        super(175, 166);
        addWidget(new WidgetTexture(175, 166, background, 0, 0), new Point(0, 0));
        List<NBTTagList> geneInfo = BasePetriDish.getGenes(petriDish);
        if (!geneInfo.get(0).hasNoTags()) {
            genes = NBTParser.convertNBTToMapList(geneInfo.get(0));
            genesDom = NBTParser.convertNBTToMapList(geneInfo.get(1));
        }
        addWidget(new WidgetGeneticViewer(20, 100, (HashMap<Gene, Double>) genes.get(0),
                (HashMap<Gene, Double>) genesDom.get(0), BasePetriDish.getLevel(petriDish)), new Point(10, 10));
        addWidget(new WidgetGeneticViewer(20, 100, (HashMap<Gene, Double>) genes.get(1),
                (HashMap<Gene, Double>) genesDom.get(1), BasePetriDish.getLevel(petriDish)), new Point(100, 10));

    }


}
