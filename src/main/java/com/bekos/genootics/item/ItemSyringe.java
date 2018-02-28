package com.bekos.genootics.item;

import com.bekos.genootics.GenooticsMod;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
        return getTagCompoundSafe(stack).hasKey("Bloody");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        boolean isPlayer = !getTagCompoundSafe(stack).hasKey("NonPlayer");
        if (!world.isRemote && isPlayer) {
            if (isBloody(stack)) {
                setClean(stack);
            } else {
                if (!(playerIn.getHealth() <= 10) && !playerIn.isCreative()) {
                    setBloody(stack);
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

    private void setBloody(ItemStack stack) {
        getTagCompoundSafe(stack).setBoolean("Bloody", true);
    }

    private void setClean(ItemStack stack) {
        getTagCompoundSafe(stack).removeTag("Bloody");
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
            getTagCompoundSafe(stack).setBoolean("NonPlayer", true);
        } else {
            return false;
        }

        return true;
    }

}
