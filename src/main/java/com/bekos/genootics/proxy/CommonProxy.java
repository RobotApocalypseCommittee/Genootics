package com.bekos.genootics.proxy;

import com.bekos.genootics.genetics.GeneticsBase;
import com.bekos.genootics.genetics.GeneticsHandler;
import com.bekos.genootics.genetics.GeneticsStorage;
import com.bekos.genootics.genetics.IGenetics;
import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.machines.GuiProxy;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        CapabilityManager.INSTANCE.register(IGenetics.class, new GeneticsStorage(), GeneticsBase::new);
        MinecraftForge.EVENT_BUS.register(new GeneticsHandler());
    }

    public void init(FMLInitializationEvent e) {
        System.out.print("Registered GUI Proxy");
        NetworkRegistry.INSTANCE.registerGuiHandler(GenooticsMod.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void openPetriGui() {

    }
}
