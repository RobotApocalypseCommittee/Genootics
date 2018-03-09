package com.bekos.genootics.item;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.genetics.GeneticsBase;
import com.bekos.genootics.genetics.GeneticsProvider;
import com.bekos.genootics.util.NBTParser;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSyringe extends ItemBase {

    public ItemSyringe() {
        super("itemSyringe");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemModel() {
        ModelResourceLocation bloodModel = new ModelResourceLocation(GenooticsMod.MODID + ":" + name + "_bloody", "inventory");
        ModelResourceLocation cleanModel = new ModelResourceLocation(GenooticsMod.MODID + ":" + name + "_clean", "inventory");

        ModelBakery.registerItemVariants(this, bloodModel, cleanModel);

        ModelLoader.setCustomMeshDefinition(this, stack -> {
            if (isBloody(stack)) {
                return bloodModel;
            } else {
                return cleanModel;
            }
        });
    }

    private boolean isBloody(ItemStack stack) {
        return getTagCompoundSafe(stack).hasKey("Bloody");
    }

    private void setBloody(ItemStack stack) {
        getTagCompoundSafe(stack).setBoolean("Bloody", true);
    }

    private void setClean(ItemStack stack) {
        getTagCompoundSafe(stack).removeTag("Bloody");
    }

    private void setBloodEntity(ItemStack stack, EntityLivingBase entity) {
        ResourceLocation entityResource = EntityList.getKey(entity);
        getTagCompoundSafe(stack).setString("Entity", entityResource == null ? "player:"+entity.getName() : entityResource.toString());
    }

    private void setBloodGeneInformation(ItemStack stack, EntityLivingBase entity) {
        GeneticsBase entityGenetics = (GeneticsBase) entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

        NBTTagCompound compound = getTagCompoundSafe(stack);

        NBTTagList allGenes = NBTParser.convertMapListToNBT(entityGenetics.getAllGenes());
        compound.setTag("AllGenes", allGenes);

        NBTTagList allGenesDom = NBTParser.convertMapListToNBT(entityGenetics.getAllGenesDominances());
        compound.setTag("AllGenesDom", allGenesDom);
    }

    private void clearBloodEntity(ItemStack stack) {
        getTagCompoundSafe(stack).removeTag("Entity");
    }

    private void clearBloodGeneInformation(ItemStack stack) {
        getTagCompoundSafe(stack).removeTag("AllGenes");
        getTagCompoundSafe(stack).removeTag("AllGenesDom");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        boolean isPlayer = !getTagCompoundSafe(stack).hasKey("NonPlayer");
        if (!world.isRemote && isPlayer) {
            if (isBloody(stack)) {
                setClean(stack);
                clearBloodEntity(stack);
                clearBloodGeneInformation(stack);
            } else {
                if (!(playerIn.getHealth() <= 10) && !playerIn.isCreative()) {
                    setBloody(stack);
                    setBloodEntity(stack, playerIn);
                    setBloodGeneInformation(stack, playerIn);
                    playerIn.attackEntityFrom(DamageSource.GENERIC, 10);
                } else {
                    return new ActionResult<>(EnumActionResult.FAIL, stack);
                }
            }
        } else if (!isPlayer) {
            getTagCompoundSafe(stack).removeTag("NonPlayer");
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer playerIn, EntityLivingBase entityIn, EnumHand hand) {
        if (entityIn.world.isRemote)
        {
            return false;
        }

        //BlockPos pos = new BlockPos(entityIn.posX, entityIn.posY, entityIn.posZ);
        ItemStack stack = playerIn.getHeldItem(hand);

        if (!isBloody(stack)) {
            entityIn.attackEntityFrom(DamageSource.GENERIC, 5);
            setBloody(stack);
            setBloodEntity(stack, entityIn);
            setBloodGeneInformation(stack, entityIn);
            getTagCompoundSafe(stack).setBoolean("NonPlayer", true);
        } else {
            return false;
        }

        return true;
    }

}
