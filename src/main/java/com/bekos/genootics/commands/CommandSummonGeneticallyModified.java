package com.bekos.genootics.commands;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.genetics.GeneticsBase;
import com.bekos.genootics.genetics.GeneticsProvider;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
        return "summon_gm <LivingEntity> [<Genes>]";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (!sender.canUseCommand(server.getOpPermissionLevel(), "summon_gm")) {
            return false;
        }
        return true;
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
        if (args.length < 1 || args.length > 2) {
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

            if (args.length == 2) {
                Map<String, Double> geneMap = new HashMap<>();

                try {
                    NBTTagCompound geneCompound = JsonToNBT.getTagFromJson(args[1]);
                    Iterator iterator = geneCompound.getTagList("Genes", Constants.NBT.TAG_COMPOUND).iterator();

                    while (iterator.hasNext()) {
                        NBTTagCompound singleGeneCompound = (NBTTagCompound) iterator.next();
                        geneMap.put(singleGeneCompound.getString("Gene"), singleGeneCompound.getDouble("Value"));
                    }

                } catch (NBTException e) {
                    throw new CommandException("commands.genootics.summon_gm.tagError", new Object[] {e.getMessage()});
                }

                System.out.println(geneMap);

                entityGenetics.setGenes(geneMap);
            }

            world.spawnEntity(entity);

            ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);

            notifyCommandListener(sender, this, "commands.genootics.summon_gm.success", new Object[0]);
        }
    }
}