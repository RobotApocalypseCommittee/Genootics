package com.bekos.genootics.item;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.util.NBTParser;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public abstract class BasePetriDish extends ItemBase {

    public BasePetriDish(String name, int slots, Double degradation) {
        super(name);
        //TODO implement the extra args
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemModel() {
        ModelResourceLocation emptyModel = new ModelResourceLocation(GenooticsMod.MODID + ":" + name + "_empty", "inventory");

        ModelBakery.registerItemVariants(this, emptyModel);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return emptyModel;
            }
        });
    }

    public boolean isEmpty(ItemStack stack) {
        return !getTagCompoundSafe(stack).hasKey("Genes");
    }

    public NBTTagList getGenes(ItemStack stack) {
        return isEmpty(stack) ? (NBTTagList) getTagCompoundSafe(stack).getTag("Genes") : new NBTTagList();
    }

    public void setEmpty(ItemStack stack) {
        getTagCompoundSafe(stack).removeTag("Genes");
    }

    public void setGenes(ItemStack stack, NBTTagList tagList) {
        getTagCompoundSafe(stack).setTag("Genes", tagList);
    }

    public void setGenes(ItemStack stack, Map<String, Double> mapIn) {
        getTagCompoundSafe(stack).setTag("Genes", NBTParser.convertMapToNBT(mapIn));
    }
}
