package com.bekos.genootics.genetics;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.genetics.entityhandler.EntityHandlerGeneric;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Map;

@Mod.EventBusSubscriber
public class GeneEvents {
    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityLiving && !(event.getEntity() instanceof EntityPlayer)) {
            EntityLiving entity = (EntityLiving) event.getEntity();

            if (!entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null).isGM()) {
                return;
            }

            if (entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null).hasBeenEdited()) {
                return;
            }

            NBTTagCompound compound = handleGeneSwitchboard(entity);

            entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null).setHasBeenEdited(true);

            System.out.println(compound);

            entity.readEntityFromNBT(compound);
        }
    }

    private static NBTTagCompound handleGeneSwitchboard(EntityLivingBase entity) {
        NBTTagCompound compound = new NBTTagCompound();
        entity.writeEntityToNBT(compound);

        IGenetics entityGenetics = entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

        System.out.println(entityGenetics.getAllGenes());

        Map<Gene, Double> expressedGenes = entityGenetics.getGenes(GeneticsSide.EXPRESSED);
        Map<Gene, Double> expressedGenesDom = entityGenetics.getGenesDominance(GeneticsSide.EXPRESSED);

        for (Map.Entry<Gene, Double> gene : expressedGenes.entrySet()) {
            compound = chooseGeneFunction(gene.getKey(), gene.getValue(), expressedGenesDom.get(gene.getKey()), compound);
        }

        return compound;
    }

    private static NBTTagCompound chooseGeneFunction(Gene gene, Double value, Double dominance, NBTTagCompound priorCompound) {
        NBTTagCompound compound;

        /*switch (gene) {
            case "Health": compound = EntityHandlerGeneric.changeEntityHealth(value, dominance, priorCompound); break;
            case "MovementSpeed": compound = EntityHandlerGeneric.changeEntityMovementSpeed(value, dominance, priorCompound); break;
        }*/
        compound = gene.onAppliedAnimalSpawned(priorCompound, value, dominance);
        return compound;
    }

    @SubscribeEvent
    public static void onRegistryRegister(RegistryEvent.NewRegistry event) {
        RegistryBuilder<Gene> builder = new RegistryBuilder<>();
        builder.setType(Gene.class);
        ResourceLocation key = new ResourceLocation(GenooticsMod.MODID, "gene");
        builder.setName(key);
        builder.setDefaultKey(key);
        builder.create();
    }
}
