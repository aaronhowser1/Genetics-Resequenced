package dev.aaronhowser.mods.geneticsresequenced.event

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.control.ModKeyMappings
import dev.aaronhowser.mods.geneticsresequenced.util.ClientUtil
import net.minecraft.world.inventory.AbstractContainerMenu
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent
import net.neoforged.neoforge.client.event.InputEvent
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent

@EventBusSubscriber(
    modid = GeneticsResequenced.ID,
    value = [Dist.CLIENT]
)
object ClientEvents {

    //TODO
    @SubscribeEvent
    fun onKeyInputEvent(event: InputEvent.Key) {
        if (ModKeyMappings.TELEPORT.consumeClick()) {
//            ModPacketHandler.messageServer(TeleportPlayerPacket())
        }

        if (ModKeyMappings.DRAGONS_BREATH.consumeClick()) {
//            ModPacketHandler.messageServer(FireballPacket())
        }
    }

    @SubscribeEvent
    fun tooltip(event: ItemTooltipEvent) {
        handleScreens(event)
//        BrewingRecipes.tooltip(event)
    }

    private fun handleScreens(event: ItemTooltipEvent) {
        val screen: AbstractContainerMenu = event.entity?.containerMenu ?: return

        when (screen) {
//            is CoalGeneratorMenu -> CoalGeneratorMenu.showFuelTooltip(event)
//            is PlasmidInfuserMenu -> PlasmidInfuserMenu.showTooltip(event)
//            is PlasmidInjectorMenu -> PlasmidInjectorMenu.showTooltip(event)
            else -> return
        }
    }

    @SubscribeEvent
    fun onLeaveServer(event: ClientPlayerNetworkEvent.LoggingOut) {
//        ClientUtil.addSkinLayersBack()
//        ClientUtil.handleCringe(false, 0)
    }

}