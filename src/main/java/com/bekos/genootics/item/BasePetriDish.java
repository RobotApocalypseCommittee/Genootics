package com.bekos.genootics.item;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.util.NBTParser;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
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

        ModelLoader.setCustomMeshDefinition(this, stack -> emptyModel);
    }

    public boolean isEmpty(ItemStack stack) {
        return !getTagCompoundSafe(stack).hasKey("AllGenes");
    }

    public List<NBTTagList> getGenes(ItemStack stack) {
        if (isEmpty(stack)) {
            return new ArrayList<>();
        } else {
            List<NBTTagList> returnList = new ArrayList<>();
            returnList.add(getTagCompoundSafe(stack).getTagList("AllGenes", Constants.NBT.TAG_LIST));
            returnList.add(getTagCompoundSafe(stack).getTagList("AllGenesDom", Constants.NBT.TAG_LIST));
            return returnList;
        }
    }

    public void setEmpty(ItemStack stack) {
        getTagCompoundSafe(stack).removeTag("AllGenes");
        getTagCompoundSafe(stack).removeTag("AllGenesDom");
    }

    public void setGenes(ItemStack stack, NBTTagList allGenes, NBTTagList allGenesDom) {
        getTagCompoundSafe(stack).setTag("AllGenes", allGenes);
        getTagCompoundSafe(stack).setTag("AllGenesDom", allGenesDom);
    }

    public void setGenes(ItemStack stack, List<Map<String, Double>> geneMap, List<Map<String, Double>> geneDomMap) {
        getTagCompoundSafe(stack).setTag("AllGenes", NBTParser.convertMapListToNBT(geneMap));
        getTagCompoundSafe(stack).setTag("AllGenesDom", NBTParser.convertMapListToNBT(geneDomMap));
    }
}
