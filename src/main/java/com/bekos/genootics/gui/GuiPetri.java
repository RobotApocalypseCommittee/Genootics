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
