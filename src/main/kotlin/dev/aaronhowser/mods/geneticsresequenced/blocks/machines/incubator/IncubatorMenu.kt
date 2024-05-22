package dev.aaronhowser.mods.geneticsresequenced.blocks.machines.incubator

import dev.aaronhowser.mods.geneticsresequenced.blocks.ModBlocks
import dev.aaronhowser.mods.geneticsresequenced.screens.ModMenuTypes
import dev.aaronhowser.mods.geneticsresequenced.screens.base.MachineMenu
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler

class IncubatorMenu(
    id: Int,
    inventory: Inventory,
    blockEntity: IncubatorBlockEntity,
    private val containerData: ContainerData
) : MachineMenu(
    ModMenuTypes.INCUBATOR.get(),
    blockEntity,
    id,
    inventory
) {

    constructor(id: Int, inventory: Inventory, extraData: FriendlyByteBuf) :
            this(
                id,
                inventory,
                inventory.player.level.getBlockEntity(extraData.readBlockPos()) as IncubatorBlockEntity,
                SimpleContainerData(IncubatorBlockEntity.SIMPLE_CONTAINER_SIZE)
            )

    init {
        checkContainerSize(inventory, IncubatorBlockEntity.SIMPLE_CONTAINER_SIZE)

        addPlayerInventory(inventory)
        addPlayerHotbar(inventory)

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent { itemHandler ->
            this.addSlot(SlotItemHandler(itemHandler, IncubatorBlockEntity.TOP_SLOT_INDEX, 83, 15))
            this.addSlot(SlotItemHandler(itemHandler, IncubatorBlockEntity.LEFT_BOTTLE_SLOT_INDEX, 60, 49))
            this.addSlot(SlotItemHandler(itemHandler, IncubatorBlockEntity.MIDDLE_BOTTLE_SLOT_INDEX, 83, 56))
            this.addSlot(SlotItemHandler(itemHandler, IncubatorBlockEntity.RIGHT_BOTTLE_SLOT_INDEX, 106, 49))
            this.addSlot(SlotItemHandler(itemHandler, IncubatorBlockEntity.CHORUS_SLOT_INDEX, 141, 32))
            this.addSlot(SlotItemHandler(itemHandler, IncubatorBlockEntity.OVERCLOCKER_SLOT_INDEX, 146, 67))
        }

        addDataSlots(containerData)
    }

    override fun stillValid(pPlayer: Player): Boolean {
        return stillValid(
            ContainerLevelAccess.create(level, blockEntity.blockPos),
            pPlayer,
            ModBlocks.INCUBATOR.get()
        )
    }

    private var progress: Int
        get() = containerData.get(DATA_PROGRESS_INDEX)
        set(value) {
            containerData.set(DATA_PROGRESS_INDEX, value)
        }

    val isCrafting
        get() = progress > 0

    fun getScaledProgress(): Int {
        val progressArrowSize = IncubatorScreen.ARROW_HEIGHT

        return if (progress == 0) {
            0
        } else {
            progress * progressArrowSize / IncubatorBlockEntity.TICKS_PER
        }
    }

    companion object {
        private const val DATA_PROGRESS_INDEX = 0
    }

}