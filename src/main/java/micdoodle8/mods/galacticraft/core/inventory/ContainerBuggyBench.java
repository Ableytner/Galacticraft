package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.RecipeUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerBuggyBench extends Container
{
    public InventoryBuggyBench craftMatrix = new InventoryBuggyBench(this);
    public IInventory craftResult = new InventoryCraftResult();
    private final World worldObj;

    public ContainerBuggyBench(InventoryPlayer inventory, int x, int y, int z)
    {
        this.worldObj = inventory.player.worldObj;

        //OUTPUT
        addSlotToContainer(new SlotRocketBenchResult(inventory.player, craftMatrix, craftResult, 0, 143, 64));

        //BUGGY
        //gear
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 1, 62, 19, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 2, 62, 55, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 3, 62, 73, x, y, z, inventory.player));
        //wheels
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 4, 8, 19, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 5, 116, 19, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 6, 8, 109, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 7, 116, 109, x, y, z, inventory.player));
        //rods
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 8, 26, 19, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 9, 98, 19, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 10, 26, 109, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 11, 98, 109, x, y, z, inventory.player));
        //plates
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 12, 44, 19, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 13, 80, 19, x, y, z, inventory.player));
        for(int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotBuggyBench(craftMatrix, 14 + i, 44 + i * 18, 109, x, y, z, inventory.player));
        }
        //screws
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 17, 8, 37, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 18, 26, 37, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 19, 98, 37, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 20, 116, 37, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 21, 8, 91, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 22, 26, 91, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 23, 98, 91, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 24, 116, 91, x, y, z, inventory.player));
        //body
        for(int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotBuggyBench(craftMatrix, 25 + i, 44 + i * 18, 37, x, y, z, inventory.player));
        }
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 28, 44, 55, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 29, 80, 55, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 30, 44, 73, x, y, z, inventory.player));
        addSlotToContainer(new SlotBuggyBench(craftMatrix, 31, 80, 73, x, y, z, inventory.player));
        for(int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotBuggyBench(craftMatrix, 32 + i, 44 + i * 18, 91, x, y, z, inventory.player));
        }

        //PLAYER INV
        for(int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 196));
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, 9 + j + i * 9, 8 + j * 18, 138 + i * 18));
            }
        }

        onCraftMatrixChanged(craftMatrix);
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote)
        {
            for (int var2 = 1; var2 < this.craftMatrix.getSizeInventory(); ++var2)
            {
                final ItemStack slot = this.craftMatrix.getStackInSlotOnClosing(var2);

                if (slot != null)
                {
                    par1EntityPlayer.entityDropItem(slot, 0.0F);
                }
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.craftResult.setInventorySlotContents(0, RecipeUtil.findMatchingBuggy(this.craftMatrix));
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift
     * clicking.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack stack = null;
        Slot currentSlot = (Slot) this.inventorySlots.get(slotIndex);

        if (currentSlot != null && currentSlot.getHasStack()) {
            ItemStack currentStack = currentSlot.getStack();
            stack = currentStack.copy();

            if(!this.mergeOneItem(currentStack, 1, 34)) {
                return null;
            }

            if (currentStack.stackSize == 0) {
                if (slotIndex == 0) {
                    currentSlot.onPickupFromSlot(player, currentStack);
                }
                currentSlot.putStack(null);
                return stack;
            }
            if (currentStack.stackSize == stack.stackSize) {
                return null;
            }
            currentSlot.onPickupFromSlot(player, currentStack);
            if (slotIndex == 0) {
                currentSlot.onSlotChanged();
            }
        }
        return stack;
    }

    protected boolean mergeOneItem(ItemStack itemStack, int startIndex, int endIndex) {
        boolean nothingLeft = false;
        if (itemStack.stackSize > 0) {
            for (int i = startIndex; i <= endIndex; i++) {
                Slot slot = (Slot) this.inventorySlots.get(i);
                ItemStack slotStack = slot.getStack();
                if (slotStack == null && slot.isItemValid(itemStack)) {
                    ItemStack stackOneItem = itemStack.copy();
                    stackOneItem.stackSize = 1;
                    itemStack.stackSize--;
                    slot.putStack(stackOneItem);
                    slot.onSlotChanged();
                    nothingLeft = true;
                    break;
                }
            }
        }
        return nothingLeft;
    }
}
