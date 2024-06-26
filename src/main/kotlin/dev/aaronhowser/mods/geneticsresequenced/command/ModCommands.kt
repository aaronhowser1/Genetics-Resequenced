package dev.aaronhowser.mods.geneticsresequenced.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.api.genes.Gene
import dev.aaronhowser.mods.geneticsresequenced.api.genes.GeneRegistry
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider

object ModCommands {

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {

        val modCommands = dispatcher.register(
            Commands.literal(GeneticsResequenced.ID)
                .then(AddGeneCommand.register())
                .then(AddAllGenesCommand.register())
                .then(RemoveGeneCommand.register())
                .then(RemoveAllGenesCommand.register())
                .then(ListGenesCommand.register())
                .then(ListAllGenesCommand.register())
                .then(RemoveNearbyLightsCommand.register())
        )

    }

    val SUGGEST_GENE_RLS: SuggestionProvider<CommandSourceStack> =
        SuggestionProvider { _: CommandContext<CommandSourceStack>, suggestionsBuilder: SuggestionsBuilder ->
            val allGeneResourceLocations = GeneRegistry.GENE_REGISTRY.filter { !it.isHidden }.map { it.id }
            SharedSuggestionProvider.suggestResource(allGeneResourceLocations, suggestionsBuilder)
        }

    val SUGGEST_GENE_STRINGS: SuggestionProvider<CommandSourceStack> =
        SuggestionProvider { _: CommandContext<CommandSourceStack>, suggestionsBuilder: SuggestionsBuilder ->
            val allGeneStrings = GeneRegistry.GENE_REGISTRY.filter { !it.isHidden }.map { it.id.path }.toSet()
            SharedSuggestionProvider.suggest(allGeneStrings, suggestionsBuilder)
        }

}