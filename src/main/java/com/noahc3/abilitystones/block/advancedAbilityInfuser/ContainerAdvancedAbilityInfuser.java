package com.noahc3.abilitystones.block.advancedAbilityInfuser;

import com.noahc3.abilitystones.guisupport.slot.BasicSlot;
import com.noahc3.abilitystones.guisupport.slot.OutputSlot;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.ParametersAreNonnullByDefault;

public class ContainerAdvancedAbilityInfuser extends Container {

    public ContainerAdvancedAbilityInfuser(InventoryPlayer playerInv, final TileEntityAdvancedAbilityInfuser infuser) {

        IItemHandler inventory = infuser.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStackHandler inv = infuser.inventory;

        // add slots to gui interface, incl. x/y positions
        addSlotToContainer(new BasicSlot(inventory, 0, 17, 24, infuser));
        addSlotToContainer(new BasicSlot(inventory, 1, 46, 30, infuser));
        addSlotToContainer(new BasicSlot(inventory, 2, 81, 24, infuser));
        addSlotToContainer(new BasicSlot(inventory, 3, 114, 30, infuser));
        addSlotToContainer(new BasicSlot(inventory, 4, 143, 24, infuser));
        addSlotToContainer(new BasicSlot(inventory, 5, 81, 62, infuser));
        addSlotToContainer(new OutputSlot(inventory, 6, 81, 108, infuser, inv));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 132 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 190));
        }

    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    @MethodsReturnNonnullByDefault
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

}


