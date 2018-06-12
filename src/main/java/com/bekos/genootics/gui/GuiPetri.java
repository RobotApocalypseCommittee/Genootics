package com.bekos.genootics.gui;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.gui.helpers.Point;
import com.bekos.genootics.gui.widgets.WidgetGeneticViewer;
import com.bekos.genootics.gui.widgets.WidgetRectangle;
import com.bekos.genootics.gui.widgets.WidgetTexture;
import com.bekos.genootics.item.BasePetriDish;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class GuiPetri extends GuiModularScreen {

    private static final ResourceLocation background = new ResourceLocation(GenooticsMod.MODID, "textures/gui/petri.png");
    HashMap<String, Double> genes;
    HashMap<String, Double> genesDominance;


    public GuiPetri(ItemStack dish) {
        super(175, 166);
        // TODO: Replace with itemsss
        genes = new HashMap<>();
        genesDominance.put("Magic", 30d);
        genesDominance = new HashMap<>();
        genesDominance.put("Magic", 30d);
        addWidget(new WidgetTexture(175, 166, background, 0, 0), new Point(0,0));
        addWidget(new WidgetGeneticViewer(20, 70, genes, genesDominance), new Point(10, 10));
    }



    @Override
    public void initGui() {
        super.initGui();

    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }
}
