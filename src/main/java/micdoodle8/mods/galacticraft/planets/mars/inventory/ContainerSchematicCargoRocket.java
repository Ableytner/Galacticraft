package micdoodle8.mods.galacticraft.planets.mars.inventory;

import micdoodle8.mods.galacticraft.core.inventory.SlotRocketBenchResult;
import micdoodle8.mods.galacticraft.planets.mars.util.RecipeUtilMars;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerSchematicCargoRocket extends Container
{
    public InventorySchematicCargoRocket craftMatrix = new InventorySchematicCargoRocket(this);
    public IInventory craftResult = new InventoryCraftResult();
    private final World worldObj;

    public ContainerSchematicCargoRocket(InventoryPlayer inventory, int x, int y, int z)
    {
        this.worldObj = inventory.player.worldObj;

        //OUT
        addSlotToContainer(new SlotRocketBenchResult(inventory.player, craftMatrix, craftResult, 0, 143, 64));

        //GEAR
        for(int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                addSlotToContainer(new SlotSchematicCargoRocket(craftMatrix, 1 + i * 3 + j, 116 + j * 18, 19 + i * 18, x, y, z, inventory.player));
            }
        }

        //ROCKET
        //nose cone
        addSlotToContainer(new SlotSchematicCargoRocket(craftMatrix, 7, 53, 19, x, y, z, inventory.player));
        //body
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 2; j++) {
                addSlotToContainer(new SlotSchematicCargoRocket(craftMatrix, 8 + i * 2 + j, 44 + j * 18, 37 + i * 18, x, y, z, inventory.player));
            }
        }
        //engine
        addSlotToContainer(new SlotSchematicCargoRocket(craftMatrix, 16, 53, 109, x, y, z, inventory.player));
        //fins
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                addSlotToContainer(new SlotSchematicCargoRocket(craftMatrix, 17 + i * 2 + j, 26 + j * 54, 91 + i * 18, x, y, z, inventory.player));
            }
        }

        //PLAYER INV
        for(int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 196));
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventory, 9 + j + i * 9, 8 + j * 18, 138 + i * 18));
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
                final ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);

                if (var3 != null)
                {
                    par1EntityPlayer.entityDropItem(var3, 0.0F);
                }
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.craftResult.setInventorySlotContents(0, RecipeUtilMars.findMatchingCargoRocketRecipe(this.craftMatrix));
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack stack = null;
        final Slot currentSlot = (Slot) this.inventorySlots.get(slotIndex);

        if (currentSlot != null && currentSlot.getHasStack()) {
            ItemStack currentStack = currentSlot.getStack();
            stack = currentStack.copy();

            if(!mergeOneItem(currentStack, 1, 20)) {
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
            for (int i = startIndex; i <= endIndex; ++i) {
                Slot slot = (Slot) inventorySlots.get(i);
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
