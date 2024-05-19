package dev.aaronhowser.mods.geneticsresequenced.default_genes.behavior

import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.GenesCapability.Companion.getGenes
import dev.aaronhowser.mods.geneticsresequenced.configs.ServerConfig
import dev.aaronhowser.mods.geneticsresequenced.default_genes.DefaultGenes
import dev.aaronhowser.mods.geneticsresequenced.items.DragonHealthCrystal
import dev.aaronhowser.mods.geneticsresequenced.items.ModItems
import dev.aaronhowser.mods.geneticsresequenced.potions.mob_effects.ModEffects
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.IndirectEntityDamageSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import kotlin.random.Random

object DamageGenes {

    fun handleNoFallDamage(event: LivingDamageEvent) {
        if (!DefaultGenes.NO_FALL_DAMAGE.isActive) return

        if (event.source != DamageSource.FALL) return

        val genes = event.entity.getGenes() ?: return
        if (!genes.hasGene(DefaultGenes.NO_FALL_DAMAGE) && !genes.hasGene(DefaultGenes.FLIGHT)) return

        event.isCanceled = true
    }

    fun handleWitherProof(event: LivingDamageEvent) {
        if (!DefaultGenes.WITHER_PROOF.isActive) return

        if (event.source != DamageSource.WITHER) return

        val genes = event.entity.getGenes() ?: return
        if (!genes.hasGene(DefaultGenes.WITHER_PROOF)) return

        event.entity.removeEffect(MobEffects.WITHER)
        event.isCanceled = true
    }

    fun handleWitherHit(event: LivingAttackEvent) {
        if (!DefaultGenes.WITHER_HIT.isActive) return

        // Makes it not proc if it's an arrow or whatever
        if (event.source is IndirectEntityDamageSource) return

        val witherEffect = MobEffectInstance(
            MobEffects.WITHER,
            100
        )

        if (!event.entity.canBeAffected(witherEffect)) return

        val attacker = event.source.entity as? LivingEntity ?: return
        val genes = attacker.getGenes() ?: return
        if (!genes.hasGene(DefaultGenes.WITHER_HIT)) return

        event.entity.addEffect(witherEffect)
    }

    fun handleFireProof(event: LivingDamageEvent) {
        if (!DefaultGenes.FIRE_PROOF.isActive) return

        val source = event.source

        if (!source.isFire) return

        val genes = event.entity.getGenes() ?: return
        if (!genes.hasGene(DefaultGenes.FIRE_PROOF)) return

        event.entity.clearFire()
        event.isCanceled = true
    }

    fun handlePoisonProof(event: LivingDamageEvent) {
        if (!DefaultGenes.POISON_IMMUNITY.isActive) return

        val source = event.source

        if (!source.isMagic) return
        if (!event.entity.hasEffect(MobEffects.POISON)) return

        val genes = event.entity.getGenes() ?: return
        if (!genes.hasGene(DefaultGenes.POISON_IMMUNITY)) return

        event.entity.removeEffect(MobEffects.POISON)
        event.isCanceled = true
    }

    fun handleThorns(event: LivingDamageEvent) {
        if (!DefaultGenes.THORNS.isActive) return

        val attacker = event.source.entity as? LivingEntity ?: return

        val target = event.entity as? Mob ?: event.entity as? Player ?: return
        if (target == attacker) return

        val chestPlate = target.getItemBySlot(EquipmentSlot.CHEST)
        val targetChestplateMissingOrLeather = chestPlate.isEmpty || chestPlate.`is`(Items.LEATHER_CHESTPLATE)
        if (!targetChestplateMissingOrLeather) return

        val genes = target.getGenes() ?: return
        if (!genes.hasGene(DefaultGenes.THORNS)) return

        if (Random.nextDouble() > ServerConfig.thornsChance.get()) return

        attacker.hurt(DamageSource.thorns(target), ServerConfig.thornsDamage.get().toFloat())

        if (target is Player) {
            target.causeFoodExhaustion(ServerConfig.thornsHungerDrain.get().toFloat())
        }
    }

    fun handleClaws(event: LivingDamageEvent) {
        if (!DefaultGenes.CLAWS.isActive) return

        val attacker = event.source.entity as? LivingEntity ?: return

        if (!attacker.mainHandItem.isEmpty) return

        val genes = attacker.getGenes() ?: return

        val clawsLevel: Int = if (DefaultGenes.CLAWS_2.isActive && genes.hasGene(DefaultGenes.CLAWS_2)) {
            2
        } else if (genes.hasGene(DefaultGenes.CLAWS)) {
            1
        } else {
            return
        }


        val chanceOfHappening = ServerConfig.clawsChance.get() * clawsLevel

        if (Random.nextDouble() > chanceOfHappening) return

        event.entity.addEffect(
            MobEffectInstance(
                ModEffects.BLEED,
                6000,
                0,
                false,
                true
            )
        )
    }

    fun handleDragonHealth(event: LivingDamageEvent) {
        if (!DefaultGenes.ENDER_DRAGON_HEALTH.isActive) return

        if (event.isCanceled) return
        val entity = event.entity

        if (entity.level.isClientSide) return

        val genes = entity.getGenes() ?: return
        if (!genes.hasGene(DefaultGenes.ENDER_DRAGON_HEALTH)) return

        val items = entity.handSlots.toMutableList()
        if (entity is Player) items += entity.inventory.items

        val healthCrystal = items.find { it.item == ModItems.DRAGON_HEALTH_CRYSTAL } ?: return

        val amountDamaged = event.amount.toInt()
        val crystalDurabilityRemaining = healthCrystal.maxDamage - healthCrystal.damageValue
        val amountToBlock = minOf(amountDamaged, crystalDurabilityRemaining)

        healthCrystal.hurtAndBreak(amountToBlock, entity) {
            DragonHealthCrystal.playBreakSound(it.level, it.x, it.y, it.z)
        }

        event.amount -= amountToBlock
        if (event.amount == 0f) event.isCanceled = true
    }

}