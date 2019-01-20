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

package com.bekos.genootics.genetics;

import com.bekos.genootics.genetics.entityhandler.EntityHandlerGeneric;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.awt.*;

public class ModGenes {
    private static Gene geneHealth = new Gene("health", new Color(255, 0, 0))
            .setAnimalSpawnedCallback(EntityHandlerGeneric::changeEntityHealth);

    private static Gene geneMovementSpeed = new Gene("movementSpeed", new Color(0, 255, 0))
            .setAnimalSpawnedCallback(EntityHandlerGeneric::changeEntityMovementSpeed);

    public static void register(IForgeRegistry<Gene> registry) {
        registry.registerAll(
                geneHealth,
                geneMovementSpeed
        );
        System.out.println(geneHealth.getRegistryName());
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Gene> event) {
            ModGenes.register(event.getRegistry());
        }

    }

}
