package dev.aaronhowser.mods.geneticsresequenced.compatibility.jei.categories.incubator

import dev.aaronhowser.mods.geneticsresequenced.compatibility.jei.GeneticsResequencedJeiPlugin
import dev.aaronhowser.mods.geneticsresequenced.potions.ModPotions
import dev.aaronhowser.mods.geneticsresequenced.recipes.brewing.jei.SubstrateCellRecipePage
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil.withColor
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionUtils

class SubstrateCellRecipeCategory(
    helper: IGuiHelper
) : IRecipeCategory<SubstrateCellRecipePage> {

    companion object {

        val recipeType: RecipeType<SubstrateCellRecipePage> =
            RecipeType(
                OtherUtil.modResource(SubstrateCellRecipePage.RECIPE_TYPE_NAME),
                SubstrateCellRecipePage::class.java
            )

        val UID = OtherUtil.modResource(SubstrateCellRecipePage.RECIPE_TYPE_NAME)
        private val TEXTURE = OtherUtil.modResource("textures/gui/two_to_one.png")
    }

    private val background: IDrawable = helper.createDrawable(TEXTURE, 0, 0, 100, 28)
    private val icon: IDrawable =
        helper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            PotionUtils.setPotion(ItemStack(Items.POTION), ModPotions.SUBSTRATE)
        )

    override fun getRecipeType(): RecipeType<SubstrateCellRecipePage> =
        GeneticsResequencedJeiPlugin.SUBSTRATE_CELL_RECIPE_TYPE

    override fun getTitle(): Component = Component.translatable("recipe.geneticsresequenced.substrate")

    override fun getBackground(): IDrawable = background

    override fun getIcon(): IDrawable = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: SubstrateCellRecipePage, focuses: IFocusGroup) {

        val firstIngredient = recipe.ingredients[0]
        val secondIngredient = recipe.ingredients[1]

        builder.addSlot(
            RecipeIngredientRole.INPUT,
            6,
            6
        ).addIngredients(firstIngredient)

        builder.addSlot(
            RecipeIngredientRole.INPUT,
            31,
            6
        ).addIngredients(secondIngredient)

        builder.addSlot(
            RecipeIngredientRole.OUTPUT,
            78,
            6
        ).addItemStack(recipe.resultItem)
    }

    override fun getTooltipStrings(
        recipe: SubstrateCellRecipePage,
        recipeSlotsView: IRecipeSlotsView,
        mouseX: Double,
        mouseY: Double
    ): MutableList<Component> {
        val component = Component.translatable("tooltip.geneticsresequenced.substrate_recipe")
            .withColor(ChatFormatting.GRAY)

        return mutableListOf(component)
    }

}