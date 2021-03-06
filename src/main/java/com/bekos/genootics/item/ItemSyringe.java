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

package com.bekos.genootics.item;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.genetics.GeneticsBase;
import com.bekos.genootics.genetics.GeneticsProvider;
import com.bekos.genootics.util.NBTParser;
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
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

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

    public boolean isBloody(ItemStack stack) {
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
        getTagCompoundSafe(stack).setString("Entity", entityResource == null ? "player:" + entity.getName() : entityResource.toString());
    }

    private void setBloodGeneInformation(ItemStack stack, EntityLivingBase entity) {
        GeneticsBase entityGenetics = (GeneticsBase) entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

        NBTTagCompound compound = getTagCompoundSafe(stack);

        NBTTagList allGenes = NBTParser.convertMapListToNBT(entityGenetics.getAllGenes());
        compound.setTag("AllGenes", allGenes);

        NBTTagList allGenesDom = NBTParser.convertMapListToNBT(entityGenetics.getAllGenesDominances());
        compound.setTag("AllGenesDom", allGenesDom);
    }

    public List<NBTTagList> getBloodGeneInformation(ItemStack stack) {
        if (!isBloody(stack)) {
            return new ArrayList<>();
        } else {
            List<NBTTagList> returnList = new ArrayList<>();
            returnList.add(getTagCompoundSafe(stack).getTagList("AllGenes", Constants.NBT.TAG_LIST));
            returnList.add(getTagCompoundSafe(stack).getTagList("AllGenesDom", Constants.NBT.TAG_LIST));
            return returnList;
        }
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
        if (entityIn.world.isRemote) {
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
