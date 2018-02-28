package com.bekos.genootics.genetics;

import com.bekos.genootics.GenooticsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GeneticsHandler {
    public static final ResourceLocation GENETICS_CAPABILITY = new ResourceLocation(GenooticsMod.MODID, "genetics");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityLiving) {
            event.addCapability(GENETICS_CAPABILITY, new GeneticsProvider());
        }
    }
}
