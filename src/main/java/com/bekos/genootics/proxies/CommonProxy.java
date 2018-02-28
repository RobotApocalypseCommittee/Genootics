package com.bekos.genootics.proxies;

import com.bekos.genootics.genetics.GeneticsBase;
import com.bekos.genootics.genetics.GeneticsHandler;
import com.bekos.genootics.genetics.GeneticsStorage;
import com.bekos.genootics.genetics.IGenetics;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CommonProxy {

    public void preInit() {
        CapabilityManager.INSTANCE.register(IGenetics.class, new GeneticsStorage(), GeneticsBase.class);
        MinecraftForge.EVENT_BUS.register(new GeneticsHandler());
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }
}
