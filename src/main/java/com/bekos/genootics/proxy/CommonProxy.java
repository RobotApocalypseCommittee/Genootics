/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2019  Robot Apocalypse Committee
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

package com.bekos.genootics.proxy;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.genetics.GeneticsBase;
import com.bekos.genootics.genetics.GeneticsHandler;
import com.bekos.genootics.genetics.GeneticsStorage;
import com.bekos.genootics.genetics.IGenetics;
import com.bekos.genootics.machines.GuiProxy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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

    public void openPetriGui(ItemStack petriDish) {

    }
}
