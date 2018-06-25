package com.bekos.genootics.genetics;

import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.awt.*;
import java.util.Objects;

public class Gene extends IForgeRegistryEntry.Impl<Gene> {
    private String unlocalizedName;
    private Color color;
    private AnimalSpawnedCallback callback;

    public Gene(String name, Color color) {
        this(name);
        setColor(color);

    }

    public Gene(String name) {
        setRegistryName(name);
        setUnlocalizedName(getRegistryName().getResourceDomain() + "." + getRegistryName().getResourcePath());
    }

    public Gene setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public String getUnlocalizedName() {
        return "gene." + unlocalizedName;
    }

    public String getLocalisedName() {
        return I18n.format(getUnlocalizedName() + ".name");
    }

    public Gene setColor(Color color) {
        this.color = color;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public Gene setAnimalSpawnedCallback(AnimalSpawnedCallback callback) {
        this.callback = callback;
        return this;
    }

    public NBTTagCompound onAppliedAnimalSpawned(Double value, Double dominance, NBTTagCompound priorCompound) {
        return callback.run(value, dominance, priorCompound);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gene gene = (Gene) o;
        return Objects.equals(unlocalizedName, gene.unlocalizedName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(unlocalizedName);
    }

    interface AnimalSpawnedCallback {
        NBTTagCompound run(Double value, Double dominance, NBTTagCompound priorCompound);
    }
}
