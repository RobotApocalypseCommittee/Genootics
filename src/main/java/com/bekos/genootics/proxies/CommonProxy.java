package com.bekos.genootics.proxies;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.machines.GuiProxy;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        System.out.print("Registered GUI Proxy");
        NetworkRegistry.INSTANCE.registerGuiHandler(GenooticsMod.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }
}
