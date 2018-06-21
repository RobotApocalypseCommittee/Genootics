package com.bekos.genootics.proxy;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.gui.GuiPetri;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(GenooticsMod.MODID + ":" + id, "inventory"));
    }

    @Override
    public void openPetriGui(ItemStack petriDish) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiPetri(petriDish));
    }
}
