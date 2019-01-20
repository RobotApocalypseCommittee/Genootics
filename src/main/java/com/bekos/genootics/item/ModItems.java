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

package com.bekos.genootics.item;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static ItemSyringe itemSyringe = new ItemSyringe();

    private static ItemManual itemManual = new ItemManual();
    private static ItemBase itemPetriWooden = new ItemPetriDishWooden();
    private static ItemBase itemPetriIron = new ItemPetriDishIron();
    private static ItemBase itemPetriStone = new ItemPetriDishStone();
    private static ItemBase itemPetriDiamond = new ItemPetriDishDiamond();
    private static ItemBase itemRestrictionEnzyme = new ItemBase("restriction_enzyme");
    private static ItemBase itemLigaseEnzyme = new ItemBase("ligase_enzyme");

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                itemSyringe,
                itemManual,
                itemPetriWooden,
                itemPetriIron,
                itemPetriStone,
                itemPetriDiamond,
                itemLigaseEnzyme,
                itemRestrictionEnzyme
        );
    }

    public static void registerModels() {
        itemSyringe.registerItemModel();
        itemManual.registerItemModel();
        itemPetriWooden.registerItemModel();
        itemPetriIron.registerItemModel();
        itemPetriStone.registerItemModel();
        itemPetriDiamond.registerItemModel();
        itemLigaseEnzyme.registerItemModel();
        itemRestrictionEnzyme.registerItemModel();
    }

    //Item registerer
    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItems(ModelRegistryEvent event) {
            ModItems.registerModels();
        }
    }
}
