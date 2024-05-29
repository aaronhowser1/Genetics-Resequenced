package dev.aaronhowser.mods.geneticsresequenced.blocks.machines.dna_extractor

import dev.aaronhowser.mods.geneticsresequenced.blocks.ModBlockEntities
import dev.aaronhowser.mods.geneticsresequenced.blocks.base.CraftingMachineBlockEntity
import dev.aaronhowser.mods.geneticsresequenced.items.DnaHelixItem.Companion.getGene
import dev.aaronhowser.mods.geneticsresequenced.items.EntityDnaItem
import dev.aaronhowser.mods.geneticsresequenced.items.EntityDnaItem.Companion.setMob
import dev.aaronhowser.mods.geneticsresequenced.items.GmoItem
import dev.aaronhowser.mods.geneticsresequenced.items.GmoItem.Companion.getGeneChance
import dev.aaronhowser.mods.geneticsresequenced.items.ModItems
import dev.aaronhowser.mods.geneticsresequenced.screens.base.MachineMenu
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.items.ItemStackHandler

class DnaExtractorBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : CraftingMachineBlockEntity(
    ModBlockEntities.DNA_EXTRACTOR.get(),
    pPos,
    pBlockState
), MenuProvider {

    override val machineName: String = "dna_extractor"

    override val energyMaximum: Int = 60_000
    override val energyTransferMaximum: Int = 256
    override val energyCostPerTick: Int = 32

    override val amountOfItemSlots: Int = 3
    override val itemHandler: ItemStackHandler = object : ItemStackHandler(amountOfItemSlots) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return when (slot) {
                INPUT_SLOT_INDEX -> stack.item == ModItems.CELL.get() || stack.item == ModItems.GMO_CELL.get()
                OVERCLOCK_SLOT_INDEX -> stack.item == ModItems.OVERCLOCKER.get()
                OUTPUT_SLOT_INDEX -> false
                else -> false
            }
        }
    }

    override fun createMenu(pContainerId: Int, pPlayerInventory: Inventory, pPlayer: Player): AbstractContainerMenu {
        return DnaExtractorMenu(pContainerId, pPlayerInventory, this, this.containerData)
    }

    override fun getDisplayName(): Component {
        return Component.translatable("block.geneticsresequenced.dna_extractor")
    }


    override fun craftItem() {
        if (!hasRecipe()) return

        val inputItem = itemHandler.getStackInSlot(INPUT_SLOT_INDEX)
        val outputItem = getOutputFromInput(inputItem) ?: return

        val amountAlreadyInOutput = itemHandler.getStackInSlot(OUTPUT_SLOT_INDEX).count
        outputItem.count = amountAlreadyInOutput + 1

        itemHandler.extractItem(INPUT_SLOT_INDEX, 1, false)
        itemHandler.setStackInSlot(OUTPUT_SLOT_INDEX, outputItem)

        resetProgress()
    }

    override fun hasRecipe(): Boolean {
        val inventory = SimpleContainer(itemHandler.slots)
        for (i in 0 until itemHandler.slots) {
            inventory.setItem(i, itemHandler.getStackInSlot(i))
        }

        val inputItemStack = inventory.getItem(INPUT_SLOT_INDEX)

        val inputItem = inputItemStack.item
        if (inputItem != ModItems.CELL.get() && inputItem != ModItems.GMO_CELL.get()) return false

        val outputItem = getOutputFromInput(inputItemStack) ?: return false

        return outputSlotHasRoom(inventory, outputItem)
    }

    private fun getOutputFromInput(input: ItemStack): ItemStack? {
        val mobType = EntityDnaItem.getEntityType(input) ?: return null

        if (input.item == ModItems.CELL.get()) {
            return ItemStack(ModItems.DNA_HELIX.get()).setMob(mobType)
        } else {
            val gmoItem = ItemStack(ModItems.GMO_DNA_HELIX.get())
            val gene = input.getGene() ?: return null
            val chance = input.getGeneChance()

            GmoItem.setDetails(gmoItem, mobType, gene, chance)
            return gmoItem
        }
    }

    private fun outputSlotHasRoom(inventory: SimpleContainer, potentialOutput: ItemStack): Boolean {
        val outputSlot = inventory.getItem(OUTPUT_SLOT_INDEX)

        if (outputSlot.isEmpty) return true

        if (outputSlot.count + potentialOutput.count > outputSlot.maxStackSize) return false

        val mobAlreadyInSlot = EntityDnaItem.getEntityType(outputSlot) ?: return false
        val mobToInsert = EntityDnaItem.getEntityType(potentialOutput) ?: return false

        return mobAlreadyInSlot == mobToInsert
    }

    companion object {
        fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: DnaExtractorBlockEntity
        ) {
            if (level.isClientSide) return
            blockEntity.tick()
        }
    }

}