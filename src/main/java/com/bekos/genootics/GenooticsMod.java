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

package com.bekos.genootics;

import com.bekos.genootics.client.GenooticsTab;
import com.bekos.genootics.commands.CommandSummonGeneticallyModified;
import com.bekos.genootics.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(name = GenooticsMod.NAME, version = GenooticsMod.VERSION, modid = GenooticsMod.MODID)
public class GenooticsMod {
    public static final String MODID = "genootics";
    public static final String NAME = "Genootics";
    public static final String VERSION = "1.0";

    @SidedProxy(serverSide = "com.bekos.genootics.proxy.CommonProxy", clientSide = "com.bekos.genootics.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static GenooticsMod instance;

    public static final GenooticsTab creativeTab = new GenooticsTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSummonGeneticallyModified());
    }
}
