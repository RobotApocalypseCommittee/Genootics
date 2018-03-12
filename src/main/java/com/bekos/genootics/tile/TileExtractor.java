package com.bekos.genootics.tile;

import com.bekos.genootics.item.BasePetriDish;
import com.bekos.genootics.item.ItemSyringe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

public class TileExtractor extends TileMachine {
    // Number of itemstacks stored.
    public static final int SIZE = 4;
    private int ticksRemaining;
    private boolean isWorking;

    public TileExtractor() {
        super(SIZE, 10000, 500, 100);
    }


    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (this.canWork()) {
                System.out.println("CANWORK");
                if (!this.isWorking) {
                    this.ticksRemaining = 200;
                    this.isWorking = true;
                }
                this.doWork();
            } else {
                this.isWorking = false;
                this.ticksRemaining = 0;
            }
            System.out.println(this.ticksRemaining);
        }
    }

    @Override
    public boolean canWork(){
        ItemStack syringeStack = this.itemStackHandler.getStackInSlot(1);
        return super.canWork() && ( !syringeStack.isEmpty() &&
                ((ItemSyringe) syringeStack.getItem()).isBloody(syringeStack) &&
                !this.itemStackHandler.getStackInSlot(2).isEmpty() &&
                this.itemStackHandler.getStackInSlot(3).isEmpty()
        );
    }

    @Override
    public void doWork() {

        if (this.canWork()) {
            super.doWork();
            if (this.ticksRemaining == 0) {

                System.out.println("COMPLETED");
                ItemStack syringe = this.itemStackHandler.getStackInSlot(1);
                ItemSyringe sitem = (ItemSyringe) syringe.getItem();
                ItemStack petri = this.itemStackHandler.getStackInSlot(2);
                List<NBTTagList> genesinfo = sitem.getBloodGeneInformation(syringe);
                if (genesinfo.size() >= 2) {
                    ((BasePetriDish) petri.getItem()).setGenes(petri, genesinfo.get(0), genesinfo.get(1));
                    this.itemStackHandler.setStackInSlot(3, petri.copy());
                    this.itemStackHandler.extractItem(2, 1, false);
                    this.itemStackHandler.extractItem(1, 1, false);

                }
            } else {
                this.ticksRemaining--;
            }
        }
    }
}
