package com.bekos.genootics.commands;

import com.bekos.genootics.GenooticsMod;
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
        return "summon_gm <LivingEntity> [<x> <y> <z>] [<Genes>]";
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
            throw new WrongUsageException("commands.genootics.summon_gm.usage", new Object[0]);
        }

        String entityName = args[0];

        World world = sender.getEntityWorld();

        if (!world.isBlockLoaded(sender.getPosition())) {
            throw new CommandException("commands.genootics.summon_gm.outOfWorld", new Object[0]);
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
            throw new CommandException("commands.genootics.summon_gm.failed", new Object[0]);
        } else {
            entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);

            GeneticsBase entityGenetics = (GeneticsBase) entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);
            entityGenetics.setGM(true);

            if (args.length == 2 || args.length == 5) {
                Map<String, Double> geneMap;

                try {
                    int argPos;
                    if (args.length == 2) {
                        argPos = 1;
                    } else {
                        argPos = 4;
                    }
                    NBTTagCompound geneCompound = JsonToNBT.getTagFromJson(args[argPos]);
                    geneMap = NBTParser.convertNBTToMap(geneCompound.getTagList("Genes", Constants.NBT.TAG_COMPOUND));

                } catch (NBTException e) {
                    throw new CommandException("commands.genootics.summon_gm.tagError", new Object[] {e.getMessage()});
                }

                List<Map<String, Double>> geneList = new ArrayList<>();
                geneList.add(geneMap);
                geneList.add(geneMap);
                geneList.add(geneMap);

                Map<String, Double> dominanceMap = new HashMap<>();
                for (String key : geneMap.keySet()) {
                    dominanceMap.put(key, 2.0);
                }
                List<Map<String, Double>> geneDomList = new ArrayList<>();
                geneDomList.add(dominanceMap);
                geneDomList.add(dominanceMap);
                geneDomList.add(dominanceMap);

                entityGenetics.setAllGenes(geneList, geneDomList);
            }

            world.spawnEntity(entity);

            ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);

            notifyCommandListener(sender, this, "commands.genootics.summon_gm.success", new Object[0]);
        }
    }
}
