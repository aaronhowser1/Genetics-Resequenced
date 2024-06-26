package dev.aaronhowser.mods.geneticsresequenced.registry

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.api.genes.Gene
import dev.aaronhowser.mods.geneticsresequenced.api.genes.GeneProperties
import dev.aaronhowser.mods.geneticsresequenced.api.genes.GeneRegistry
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.ai.attributes.Attributes
import net.neoforged.neoforge.common.NeoForgeMod
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModGenes {

    val GENE_REGISTRY: DeferredRegister<Gene> =
        DeferredRegister.create(GeneRegistry.GENE_REGISTRY, GeneticsResequenced.ID)

    private fun registerGene(name: String, geneProperties: () -> GeneProperties): DeferredHolder<Gene, Gene> {
        return GENE_REGISTRY.register(name, Supplier {
            Gene(geneProperties())
        })
    }

    val BASIC = registerGene("basic") {
        GeneProperties(
            id = OtherUtil.modResource("basic"),
            isHidden = true
        )
    }

    // Mutations (must be initialized first because they're used in arguments in ones below)

    val CLAWS_TWO = registerGene("claws_2") {
        GeneProperties(
            id = OtherUtil.modResource("claws_2"),
            dnaPointsRequired = 50,
            canMobsHave = true
        )
    }

    val EFFICIENCY_FOUR = registerGene("efficiency_4") {
        GeneProperties(
            id = OtherUtil.modResource("efficiency_4"),
            dnaPointsRequired = 50,
            attributeModifiers = mapOf(ModAttributes.EFFICIENCY to listOf(ModAttributes.efficiencyFourAttributeModifier))
        )
    }

    val FLIGHT = registerGene("flight") {
        GeneProperties(
            id = OtherUtil.modResource("flight"),
            dnaPointsRequired = 50,
            attributeModifiers = mapOf(NeoForgeMod.CREATIVE_FLIGHT to listOf(ModAttributes.flightAttributeModifier))
        )
    }

    val HASTE_TWO = registerGene("haste_2") {
        GeneProperties(
            id = OtherUtil.modResource("haste_2"),
            dnaPointsRequired = 50,
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.DIG_SPEED,
                level = 2
            )
        )
    }

    val MEATY_TWO = registerGene("meaty_2") {
        GeneProperties(
            id = OtherUtil.modResource("meaty_2"),
            dnaPointsRequired = 50,
            canMobsHave = true
        )
    }

    val MORE_HEARTS_TWO = registerGene("more_hearts_2") {
        GeneProperties(
            id = OtherUtil.modResource("more_hearts_2"),
            dnaPointsRequired = 50,
            canMobsHave = true,
            attributeModifiers = mapOf(
                Attributes.MAX_HEALTH to listOf(ModAttributes.moreHealthTwoAttributeModifier)
            )
        )
    }

    val PHOTOSYNTHESIS = registerGene("photosynthesis") {
        GeneProperties(
            id = OtherUtil.modResource("photosynthesis"),
            dnaPointsRequired = 40
        )
    }

    val REGENERATION_FOUR = registerGene("regeneration_4") {
        GeneProperties(
            id = OtherUtil.modResource("regeneration_4"),
            dnaPointsRequired = 50,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.REGENERATION,
                level = 4
            )
        )
    }

    val RESISTANCE_TWO = registerGene("resistance_2") {
        GeneProperties(
            id = OtherUtil.modResource("resistance_2"),
            dnaPointsRequired = 50,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.DAMAGE_RESISTANCE,
                level = 2
            )
        )
    }

    val SPEED_FOUR = registerGene("speed_4") {
        GeneProperties(
            id = OtherUtil.modResource("speed_4"),
            dnaPointsRequired = 50,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.MOVEMENT_SPEED,
                level = 4
            )
        )
    }

    val SPEED_TWO = registerGene("speed_2") {
        GeneProperties(
            id = OtherUtil.modResource("speed_2"),
            dnaPointsRequired = 50,
            canMobsHave = true,
            mutatesInto = SPEED_FOUR.get(),
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.MOVEMENT_SPEED,
                level = 2
            )
        )
    }

    val STRENGTH_TWO = registerGene("strength_2") {
        GeneProperties(
            id = OtherUtil.modResource("strength_2"),
            dnaPointsRequired = 50,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.DAMAGE_BOOST,
                level = 2
            )
        )
    }

    val SCARE_ZOMBIES = registerGene("scare_zombies") {
        GeneProperties(
            id = OtherUtil.modResource("scare_zombies"),
            dnaPointsRequired = 50
        )
    }

    val SCARE_SPIDERS = registerGene("scare_spiders") {
        GeneProperties(
            id = OtherUtil.modResource("scare_spiders"),
            dnaPointsRequired = 50
        )
    }

    //Standard list

    val BIOLUMINESCENCE = registerGene("bioluminescence") {
        GeneProperties(
            id = OtherUtil.modResource("bioluminescence"),
            dnaPointsRequired = 16,
            canMobsHave = true
        )
    }

    val CHATTERBOX = registerGene("chatterbox") {
        GeneProperties(
            id = OtherUtil.modResource("chatterbox"),
            dnaPointsRequired = 20
        )
    }

    val CHILLING = registerGene("chilling") {
        GeneProperties(
            id = OtherUtil.modResource("chilling"),
            dnaPointsRequired = 20,
            canMobsHave = true
        )
    }

    val CLAWS = registerGene("claws") {
        GeneProperties(
            id = OtherUtil.modResource("claws"),
            dnaPointsRequired = 20,
            canMobsHave = true,
            mutatesInto = CLAWS_TWO.get()
        )
    }

    val DRAGON_BREATH = registerGene("dragons_breath") {
        GeneProperties(
            id = OtherUtil.modResource("dragons_breath"),
            dnaPointsRequired = 20
        )
    }

    val EAT_GRASS = registerGene("eat_grass") {
        GeneProperties(
            id = OtherUtil.modResource("eat_grass"),
            dnaPointsRequired = 16,
        )
    }

    val EFFICIENCY = registerGene("efficiency") {
        GeneProperties(
            id = OtherUtil.modResource("efficiency"),
            dnaPointsRequired = 30,
            mutatesInto = EFFICIENCY_FOUR.get(),
            attributeModifiers = mapOf(
                ModAttributes.EFFICIENCY to listOf(ModAttributes.efficiencyAttributeModifier)
            )
        )
    }

    val EMERALD_HEART = registerGene("emerald_heart") {
        GeneProperties(
            id = OtherUtil.modResource("emerald_heart"),
            dnaPointsRequired = 30,
            canMobsHave = true
        )
    }

    val ENDER_DRAGON_HEALTH = registerGene("ender_dragon_health") {
        GeneProperties(
            id = OtherUtil.modResource("ender_dragon_health"),
            dnaPointsRequired = 60
        )
    }

    val EXPLOSIVE_EXIT = registerGene("explosive_exit") {
        GeneProperties(
            id = OtherUtil.modResource("explosive_exit"),
            dnaPointsRequired = 20,
            canMobsHave = true
        )
    }

    val FIRE_PROOF = registerGene("fire_proof") {
        GeneProperties(
            id = OtherUtil.modResource("fire_proof"),
            dnaPointsRequired = 24,
            canMobsHave = true
        )
    }

    val HASTE = registerGene("haste") {
        GeneProperties(
            id = OtherUtil.modResource("haste"),
            dnaPointsRequired = 30,
            potionDetails = GeneProperties.PotionDetails(MobEffects.DIG_SPEED),
            mutatesInto = HASTE_TWO.get()
        )
    }

    val INFINITY = registerGene("infinity") {
        GeneProperties(
            id = OtherUtil.modResource("infinity"),
            dnaPointsRequired = 30
        )
    }

    val INVISIBLE = registerGene("invisible") {
        GeneProperties(
            id = OtherUtil.modResource("invisible"),
            dnaPointsRequired = 50,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.INVISIBILITY)
        )
    }

    val ITEM_MAGNET = registerGene("item_magnet") {
        GeneProperties(
            id = OtherUtil.modResource("item_magnet"),
            dnaPointsRequired = 30
        )
    }

    val JUMP_BOOST = registerGene("jump_boost") {
        GeneProperties(
            id = OtherUtil.modResource("jump_boost"),
            dnaPointsRequired = 10,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.JUMP),
            mutatesInto = FLIGHT.get()
        )
    }

    val JOHNNY = registerGene("johnny") {
        GeneProperties(
            id = OtherUtil.modResource("johnny"),
            dnaPointsRequired = 20,
            canMobsHave = true
        )
    }

    val KEEP_INVENTORY = registerGene("keep_inventory") {
        GeneProperties(
            id = OtherUtil.modResource("keep_inventory"),
            dnaPointsRequired = 40
        )
    }

    val KNOCKBACK = registerGene("knockback") {
        GeneProperties(
            id = OtherUtil.modResource("knockback"),
            dnaPointsRequired = 20,
            canMobsHave = true,
            attributeModifiers = mapOf(
                Attributes.ATTACK_KNOCKBACK to listOf(ModAttributes.knockbackAttributeModifier)
            )
        )
    }

    val LAY_EGG = registerGene("lay_egg") {
        GeneProperties(
            id = OtherUtil.modResource("lay_egg"),
            dnaPointsRequired = 12,
            canMobsHave = true
        )
    }

    val LUCK = registerGene("luck") {
        GeneProperties(
            id = OtherUtil.modResource("luck"),
            dnaPointsRequired = 50,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.LUCK)
        )
    }

    val MEATY = registerGene("meaty") {
        GeneProperties(
            id = OtherUtil.modResource("meaty"),
            dnaPointsRequired = 12,
            canMobsHave = true,
            mutatesInto = MEATY_TWO.get()
        )
    }

    val MILKY = registerGene("milky") {
        GeneProperties(
            id = OtherUtil.modResource("milky"),
            dnaPointsRequired = 12,
            canMobsHave = true
        )
    }

    val MOB_SIGHT = registerGene("mob_sight") {
        GeneProperties(
            id = OtherUtil.modResource("mob_sight"),
            dnaPointsRequired = 16
        )
    }

    val MORE_HEARTS = registerGene("more_hearts") {
        GeneProperties(
            id = OtherUtil.modResource("more_hearts"),
            dnaPointsRequired = 40,
            canMobsHave = true,
            mutatesInto = MORE_HEARTS_TWO.get(),
            attributeModifiers = mapOf(Attributes.MAX_HEALTH to listOf(ModAttributes.moreHealthOneAttributeModifier))
        )
    }

    val NIGHT_VISION = registerGene("night_vision") {
        GeneProperties(
            id = OtherUtil.modResource("night_vision"),
            dnaPointsRequired = 16,
            potionDetails = GeneProperties.PotionDetails(MobEffects.NIGHT_VISION)
        )
    }

    val NO_FALL_DAMAGE = registerGene("no_fall_damage") {
        GeneProperties(
            id = OtherUtil.modResource("no_fall_damage"),
            dnaPointsRequired = 30,
            canMobsHave = true
        )
    }

    val NO_HUNGER = registerGene("no_hunger") {
        GeneProperties(
            id = OtherUtil.modResource("no_hunger"),
            dnaPointsRequired = 30
        )
    }

    val POISON_IMMUNITY = registerGene("poison_immunity") {
        GeneProperties(
            id = OtherUtil.modResource("poison_immunity"),
            dnaPointsRequired = 24,
            canMobsHave = true
        )
    }

    val REGENERATION = registerGene("regeneration") {
        GeneProperties(
            id = OtherUtil.modResource("regeneration"),
            dnaPointsRequired = 60,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.REGENERATION),
            mutatesInto = REGENERATION_FOUR.get()
        )
    }

    val RESISTANCE = registerGene("resistance") {
        GeneProperties(
            id = OtherUtil.modResource("resistance"),
            dnaPointsRequired = 30,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.DAMAGE_RESISTANCE),
            mutatesInto = RESISTANCE_TWO.get()
        )
    }

    val SCARE_CREEPERS = registerGene("scare_creepers") {
        GeneProperties(
            id = OtherUtil.modResource("scare_creepers"),
            dnaPointsRequired = 20,
            mutatesInto = SCARE_ZOMBIES.get()
        )
    }

    val SCARE_SKELETONS = registerGene("scare_skeletons") {
        GeneProperties(
            id = OtherUtil.modResource("scare_skeletons"),
            dnaPointsRequired = 20,
            mutatesInto = SCARE_SPIDERS.get()
        )
    }

    val SHOOT_FIREBALLS = registerGene("shoot_fireballs") {
        GeneProperties(
            id = OtherUtil.modResource("shoot_fireballs"),
            dnaPointsRequired = 24
        )
    }

    val SLIMY_DEATH = registerGene("slimy_death") {
        GeneProperties(
            id = OtherUtil.modResource("slimy_death"),
            dnaPointsRequired = 60
        )
    }

    val SPEED = registerGene("speed") {
        GeneProperties(
            id = OtherUtil.modResource("speed"),
            dnaPointsRequired = 20,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.MOVEMENT_SPEED),
            mutatesInto = SPEED_TWO.get()
        )
    }

    val STEP_ASSIST = registerGene("step_assist") {
        GeneProperties(
            id = OtherUtil.modResource("step_assist"),
            dnaPointsRequired = 10,
            attributeModifiers = mapOf(
                Attributes.STEP_HEIGHT to listOf(ModAttributes.stepAssistAttributeModifier)
            )
        )
    }

    val STRENGTH = registerGene("strength") {
        GeneProperties(
            id = OtherUtil.modResource("strength"),
            dnaPointsRequired = 20,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.DAMAGE_BOOST),
            mutatesInto = STRENGTH_TWO.get()
        )
    }

    val TELEPORT = registerGene("teleport") {
        GeneProperties(
            id = OtherUtil.modResource("teleport"),
            dnaPointsRequired = 24,
            mutatesInto = FLIGHT.get()
        )
    }

    val THORNS = registerGene("thorns") {
        GeneProperties(
            id = OtherUtil.modResource("thorns"),
            dnaPointsRequired = 12,
            canMobsHave = true
        )
    }

    val WALL_CLIMBING = registerGene("wall_climbing") {
        GeneProperties(
            id = OtherUtil.modResource("wall_climbing"),
            dnaPointsRequired = 40
        )
    }

    val WATER_BREATHING = registerGene("water_breathing") {
        GeneProperties(
            id = OtherUtil.modResource("water_breathing"),
            dnaPointsRequired = 16,
            canMobsHave = true
        )
    }

    val WITHER_HIT = registerGene("wither_hit") {
        GeneProperties(
            id = OtherUtil.modResource("wither_hit"),
            dnaPointsRequired = 20,
            canMobsHave = true
        )
    }

    val WITHER_PROOF = registerGene("wither_proof") {
        GeneProperties(
            id = OtherUtil.modResource("wither_proof"),
            dnaPointsRequired = 40,
            canMobsHave = true
        )
    }

    val WOOLY = registerGene("wooly") {
        GeneProperties(
            id = OtherUtil.modResource("wooly"),
            dnaPointsRequired = 12,
            canMobsHave = true
        )
    }

    val XP_MAGNET = registerGene("xp_magnet") {
        GeneProperties(
            id = OtherUtil.modResource("xp_magnet"),
            dnaPointsRequired = 30
        )
    }

    //Negative effects

    //FIXME: This effect apparently has a sound on add now, probably want to remove that
    val BAD_OMEN = registerGene("bad_omen") {
        GeneProperties(
            id = OtherUtil.modResource("bad_omen"),
            dnaPointsRequired = 20,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.BAD_OMEN)
        )
    }

    val BLINDNESS = registerGene("blindness") {
        GeneProperties(
            id = OtherUtil.modResource("blindness"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.BLINDNESS)
        )
    }

    val CRINGE = registerGene("cringe") {
        GeneProperties(
            id = OtherUtil.modResource("cringe"),
            dnaPointsRequired = 20,
            isNegative = true
        )
    }

    val CURSED = registerGene("cursed") {
        GeneProperties(
            id = OtherUtil.modResource("cursed"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.UNLUCK)
        )
    }

    val FLAMBE = registerGene("flambe") {
        GeneProperties(
            id = OtherUtil.modResource("flambe"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true
        )
    }

    val HUNGER = registerGene("hunger") {
        GeneProperties(
            id = OtherUtil.modResource("hunger"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.HUNGER)
        )
    }

    val LEVITATION = registerGene("levitation") {
        GeneProperties(
            id = OtherUtil.modResource("levitation"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.LEVITATION)
        )
    }

    val MINING_FATIGUE = registerGene("mining_fatigue") {
        GeneProperties(
            id = OtherUtil.modResource("mining_fatigue"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.DIG_SLOWDOWN)
        )
    }

    val NAUSEA = registerGene("nausea") {
        GeneProperties(
            id = OtherUtil.modResource("nausea"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.CONFUSION)
        )
    }

    val POISON = registerGene("poison") {
        GeneProperties(
            id = OtherUtil.modResource("poison"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.POISON)
        )
    }

    val POISON_FOUR = registerGene("poison_4") {
        GeneProperties(
            id = OtherUtil.modResource("poison_4"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.POISON,
                level = 4
            )
        )
    }

    val SLOWNESS = registerGene("slowness") {
        GeneProperties(
            id = OtherUtil.modResource("slowness"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.MOVEMENT_SLOWDOWN)
        )
    }

    val SLOWNESS_FOUR = registerGene("slowness_4") {
        GeneProperties(
            id = OtherUtil.modResource("slowness_4"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.MOVEMENT_SLOWDOWN,
                level = 4
            )
        )
    }

    val SLOWNESS_SIX = registerGene("slowness_6") {
        GeneProperties(
            id = OtherUtil.modResource("slowness_6"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(
                effect = MobEffects.MOVEMENT_SLOWDOWN,
                level = 6
            )
        )
    }

    val WEAKNESS = registerGene("weakness") {
        GeneProperties(
            id = OtherUtil.modResource("weakness"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.WEAKNESS)
        )
    }

    val WITHER = registerGene("wither") {
        GeneProperties(
            id = OtherUtil.modResource("wither"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true,
            potionDetails = GeneProperties.PotionDetails(MobEffects.WITHER)
        )
    }

    val BLACK_DEATH = registerGene("black_death") {
        GeneProperties(
            id = OtherUtil.modResource("black_death"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true
        )
    }

    val GREEN_DEATH = registerGene("green_death") {
        GeneProperties(
            id = OtherUtil.modResource("green_death"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true
        )
    }

    val WHITE_DEATH = registerGene("white_death") {
        GeneProperties(
            id = OtherUtil.modResource("white_death"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true
        )
    }

    val GRAY_DEATH = registerGene("gray_death") {
        GeneProperties(
            id = OtherUtil.modResource("gray_death"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true
        )
    }

    val UN_UNDEATH = registerGene("un_undeath") {
        GeneProperties(
            id = OtherUtil.modResource("un_undeath"),
            dnaPointsRequired = 1,
            isNegative = true,
            canMobsHave = true
        )
    }


}