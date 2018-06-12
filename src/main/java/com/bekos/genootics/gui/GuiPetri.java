package com.bekos.genootics.gui;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.gui.helpers.Point;
import com.bekos.genootics.gui.widgets.WidgetGeneticViewer;
import com.bekos.genootics.gui.widgets.WidgetRectangle;
import com.bekos.genootics.gui.widgets.WidgetTexture;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class GuiPetri extends GuiModularScreen {

    private static final ResourceLocation background = new ResourceLocation(GenooticsMod.MODID, "textures/gui/petri.png");

    public GuiPetri() {
        super(175, 166);
        addWidget(new WidgetTexture(175, 166, background, 0, 0), new Point(0,0));
        addWidget(new WidgetRectangle(50, 50), new Point(50, 50));
    }



    @Override
    public void initGui() {
        super.initGui();
        HashMap<String , Double> genes = new HashMap<String, Double>();
        genes.put("Magic", 30d);
        HashMap<String , Double> genesDom = new HashMap<>();
        genesDom.put("Magic", 30d);

    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }
}
