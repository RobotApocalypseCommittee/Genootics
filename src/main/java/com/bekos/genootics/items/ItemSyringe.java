package com.bekos.genootics.items;

import com.bekos.genootics.GenooticsMod;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSyringe extends ItemBase {

    public ItemSyringe() {
        super("itemSyringe");
        this.setCreativeTab(CreativeTabs.BREWING); //TEMPORARY
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemModel() {
        ModelResourceLocation bloodModel = new ModelResourceLocation(GenooticsMod.MODID + ":" + name + "_bloody", "inventory");
        ModelResourceLocation cleanModel = new ModelResourceLocation(GenooticsMod.MODID + ":" + name + "_clean", "inventory");

        ModelBakery.registerItemVariants(this, bloodModel, cleanModel);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (isBloody(stack)) {
                    return bloodModel;
                } else {
                    return cleanModel;
                }
            }
        });
    }

    private boolean isBloody(ItemStack stack) {
        return getTagCompoundSafe(stack).hasKey("bloody");
    }

    private NBTTagCompound getTagCompoundSafe(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        return tagCompound;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!world.isRemote) {
            if (isBloody(stack)) {
                getTagCompoundSafe(stack).removeTag("bloody");
            } else {
                getTagCompoundSafe(stack).setBoolean("bloody", true);
                playerIn.attackEntityFrom(DamageSource.GENERIC, 10);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

}
