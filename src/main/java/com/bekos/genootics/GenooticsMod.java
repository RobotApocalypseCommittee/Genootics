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

package com.bekos.genootics;

import com.bekos.genootics.client.GenooticsTab;
import com.bekos.genootics.items.ItemSyringe;
import com.bekos.genootics.items.ModItems;
import com.bekos.genootics.proxies.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(name = GenooticsMod.NAME, version = GenooticsMod.VERSION, modid = GenooticsMod.MODID)
public class GenooticsMod {
    public static final String MODID = "genootics";
    public static final String NAME = "Genootics";
    public static final String VERSION = "1.0";

    @SidedProxy(serverSide = "com.bekos.genootics.proxies.CommonProxy", clientSide = "com.bekos.genootics.proxies.ClientProxy")
    public static CommonProxy proxy;

    public static final GenooticsTab creativeTab = new GenooticsTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }
}
