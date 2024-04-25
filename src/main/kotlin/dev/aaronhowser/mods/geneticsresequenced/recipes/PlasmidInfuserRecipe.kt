package dev.aaronhowser.mods.geneticsresequenced.recipes

import com.google.gson.JsonObject
import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.Gene
import dev.aaronhowser.mods.geneticsresequenced.items.DnaHelixItem.setGene
import dev.aaronhowser.mods.geneticsresequenced.items.DnaHelixItem.setBasic
import dev.aaronhowser.mods.geneticsresequenced.items.ModItems
import dev.aaronhowser.mods.geneticsresequenced.items.PlasmidItem.setAmount
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import kotlin.math.ceil

class PlasmidInfuserRecipe(
    val gene: Gene,
    val isBasic: Boolean
) : Recipe<Container> {

    private val inputItem: ItemStack
    private val outputItem: ItemStack

    init {
        @Suppress("LiftReturnOrAssignment")
        if (isBasic) {
            val stack = ItemStack(ModItems.DNA_HELIX, gene.amountNeeded).setBasic()
            inputItem = stack
        } else {
            val amount = ceil(gene.amountNeeded.toDouble() / 2).toInt()
            val stack = ItemStack(ModItems.DNA_HELIX, amount).setGene(gene)
            inputItem = stack
        }

        val plasmidItem = ItemStack(ModItems.PLASMID).setGene(gene).setAmount(gene.amountNeeded)
        outputItem = plasmidItem
    }

    companion object {
        fun getAllRecipes(): List<PlasmidInfuserRecipe> {
            val list = mutableListOf<PlasmidInfuserRecipe>()
            for (gene in Gene.getRegistry()) {
                list.add(PlasmidInfuserRecipe(gene, false))
                list.add(PlasmidInfuserRecipe(gene, true))
            }
            return list
        }

        const val RECIPE_TYPE_NAME = "plasmid_infuser"

        val RECIPE_TYPE = object : RecipeType<PlasmidInfuserRecipe> {}

        val SERIALIZER = object : RecipeSerializer<PlasmidInfuserRecipe> {
            override fun fromJson(pRecipeId: ResourceLocation, pSerializedRecipe: JsonObject): PlasmidInfuserRecipe {
                throw IllegalStateException("This recipe type does not support JSON serialization")
            }

            override fun fromNetwork(pRecipeId: ResourceLocation, pBuffer: FriendlyByteBuf): PlasmidInfuserRecipe? {
                throw IllegalStateException("This recipe type does not support JSON serialization")
            }

            override fun toNetwork(pBuffer: FriendlyByteBuf, pRecipe: PlasmidInfuserRecipe) {
                throw IllegalStateException("This recipe type does not support JSON serialization")
            }
        }

    }

    override fun getId(): ResourceLocation {
        return if (isBasic) {
            ResourceLocation(
                GeneticsResequenced.ID,
                "$RECIPE_TYPE_NAME/${gene.id.toString().replace(":", "/")}_basic"
            )
        } else {
            ResourceLocation(
                GeneticsResequenced.ID,
                "$RECIPE_TYPE_NAME/${gene.id.toString().replace(":", "/")}"
            )
        }
    }

    override fun getSerializer(): RecipeSerializer<*> = SERIALIZER

    override fun getType(): RecipeType<*> = RECIPE_TYPE

    override fun matches(pContainer: Container, pLevel: Level): Boolean {
        return !pLevel.isClientSide
    }

    override fun assemble(pContainer: Container): ItemStack = outputItem.copy()

    override fun canCraftInDimensions(pWidth: Int, pHeight: Int): Boolean = true

    override fun getResultItem(): ItemStack = outputItem.copy()

    override fun getIngredients(): NonNullList<Ingredient> {
        val i = Ingredient.of(inputItem)
        return NonNullList.of(i, i)
    }

}