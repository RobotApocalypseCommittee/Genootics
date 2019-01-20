/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2018  Robot Apocalypse Committee
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

package com.bekos.genootics.genetics;

import com.bekos.genootics.GenooticsMod;
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
        if (event.getEntity() instanceof EntityLiving && !(event.getEntity() instanceof EntityPlayer)) {
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
        compound = gene.onAppliedAnimalSpawned(value, dominance, priorCompound);
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
