package com.bekos.genootics.gui;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.gui.helpers.Point;
import com.bekos.genootics.gui.widgets.WidgetGeneticViewer;
import com.bekos.genootics.gui.widgets.WidgetRectangle;
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

    private List<Map<String, Double>> genes = new ArrayList<>(Arrays.asList(new HashMap<>(), new HashMap<>()));
    private List<Map<String , Double>> genesDom = new ArrayList<>(Arrays.asList(new HashMap<>(), new HashMap<>()));

    public GuiPetri(ItemStack petriDish) {
        super(175, 166);
        addWidget(new WidgetTexture(175, 166, background, 0, 0), new Point(0,0));
        addWidget(new WidgetRectangle(50, 50), new Point(50, 50));

        List<NBTTagList> geneInfo = BasePetriDish.getGenes(petriDish);
        if (!geneInfo.get(0).hasNoTags()) {
            genes = NBTParser.convertNBTToMapList(geneInfo.get(0));
            genesDom = NBTParser.convertNBTToMapList(geneInfo.get(1));
        }
    }



    @Override
    public void initGui() {
        super.initGui();
        genes.get(0).put("Magic", 30d);

        genesDom.get(0).put("Magic", 30d);

    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }
}
