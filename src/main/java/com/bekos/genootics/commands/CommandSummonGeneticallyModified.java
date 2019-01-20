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

package com.bekos.genootics.commands;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.genetics.Gene;
import com.bekos.genootics.genetics.GeneticsBase;
import com.bekos.genootics.genetics.GeneticsProvider;
import com.bekos.genootics.util.NBTParser;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.common.util.Constants;

import java.util.*;

// Example usage /summon_gm Sheep {"Genes":[{"Gene":"genootics:health","Value":2.0},{"Gene":"genootics:movementSpeed","Value":1.0}]}

public class CommandSummonGeneticallyModified extends CommandBase {

    public CommandSummonGeneticallyModified() {
        aliases = Lists.newArrayList(GenooticsMod.MODID, "summon_gm");
    }

    private final List<String> aliases;

    @Override
    public String getName() {
        return "summon_gm";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/summon_gm <LivingEntity> [<x> <y> <z>] [<Genes>]";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(server.getOpPermissionLevel(), "summon_gm");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList());
        }

        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1 || args.length > 5 || args.length == 3) {
            throw new WrongUsageException("commands.genootics.summon_gm.usage");
        }

        String entityName = args[0];

        World world = sender.getEntityWorld();

        if (!world.isBlockLoaded(sender.getPosition())) {
            throw new CommandException("commands.genootics.summon_gm.outOfWorld");
        }

        double x = sender.getPosition().getX();
        double y = sender.getPosition().getY();
        double z = sender.getPosition().getZ();


        if (args.length >= 4) { //Then they will be with coords
            x = parseDouble(x, args[1], true);
            y = parseDouble(y, args[2], false);
            z = parseDouble(z, args[3], true);
        }

        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("id", entityName);

        Entity entity = AnvilChunkLoader.readWorldEntityPos(compound, world, x, y, z, false);

        if (entity == null)
        {
            throw new CommandException("commands.genootics.summon_gm.failed");
        } else {
            entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);

            GeneticsBase entityGenetics = (GeneticsBase) entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);
            entityGenetics.setGM(true);

            if (args.length == 2 || args.length == 5) {
                Map<Gene, Double> geneMap;

                try {
                    int argPos;
                    if (args.length == 2) {
                        argPos = 1;
                    } else {
                        argPos = 4;
                    }
                    NBTTagCompound geneCompound = JsonToNBT.getTagFromJson(args[argPos]);
                    System.out.println(geneCompound);
                    geneMap = NBTParser.convertNBTToMap(geneCompound.getTagList("Genes", Constants.NBT.TAG_COMPOUND));
                    System.out.println(geneMap);

                } catch (NBTException e) {
                    throw new CommandException("commands.genootics.summon_gm.tagError", e.getMessage());
                }

                List<Map<Gene, Double>> geneList = new ArrayList<>();
                geneList.add(geneMap);
                geneList.add(geneMap);
                geneList.add(geneMap);

                Map<Gene, Double> dominanceMap = new HashMap<>();
                for (Gene key : geneMap.keySet()) {
                    dominanceMap.put(key, 2.0);
                }
                List<Map<Gene, Double>> geneDomList = new ArrayList<>();
                geneDomList.add(dominanceMap);
                geneDomList.add(dominanceMap);
                geneDomList.add(dominanceMap);

                entityGenetics.setAllGenes(geneList, geneDomList);
            }

            world.spawnEntity(entity);

            ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);

            notifyCommandListener(sender, this, "commands.genootics.summon_gm.success");
        }
    }
}
