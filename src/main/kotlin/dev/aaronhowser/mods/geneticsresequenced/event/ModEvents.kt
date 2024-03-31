package dev.aaronhowser.mods.geneticsresequenced.event

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.api.capability.CapabilityHandler
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.GeneCapabilityProvider
import dev.aaronhowser.mods.geneticsresequenced.commands.ModCommands
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(
    modid = GeneticsResequenced.ID
)
object ModEvents {

    @SubscribeEvent
    fun onAttachCapability(event: AttachCapabilitiesEvent<Entity>) {
        val player = event.`object`
        if (player !is Player) return

        val playerHasCapability = player.getCapability(GeneCapabilityProvider.GENE_CAPABILITY).isPresent
        if (playerHasCapability) return

        event.addCapability(CapabilityHandler.GENE_CAPABILITY_RL, GeneCapabilityProvider)
    }

    @SubscribeEvent
    fun onPlayerCloned(event: PlayerEvent.Clone) {
        if (!event.isWasDeath) return

        event.original.getCapability(GeneCapabilityProvider.GENE_CAPABILITY).ifPresent { oldGenes ->
            event.original.getCapability(GeneCapabilityProvider.GENE_CAPABILITY).ifPresent { newGenes ->
                newGenes.setGeneList(oldGenes.getGeneList())
            }
        }
    }

    @SubscribeEvent
    fun onRegisterCommandsEvent(event: RegisterCommandsEvent) {
        ModCommands.register(event.dispatcher)
    }
}