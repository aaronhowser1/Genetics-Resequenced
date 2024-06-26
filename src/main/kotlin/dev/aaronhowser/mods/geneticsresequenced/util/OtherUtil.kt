package dev.aaronhowser.mods.geneticsresequenced.util

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import net.minecraft.ChatFormatting
import net.minecraft.core.Holder
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.targeting.TargetingConditions
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionContents
import java.util.*

object OtherUtil {

    fun modResource(path: String): ResourceLocation =
        ResourceLocation.fromNamespaceAndPath(GeneticsResequenced.ID, path)

    val Item.itemStack: ItemStack
        get() = this.defaultInstance

    fun MutableComponent.withColor(color: ChatFormatting): MutableComponent = this.withStyle { it.withColor(color) }

    private val entityUuidMap: MutableMap<UUID, LivingEntity> = mutableMapOf()
    fun getNearbyEntityFromUuid(uuid: UUID, searchAroundEntity: LivingEntity): LivingEntity? {
        val mappedValue = entityUuidMap[uuid]
        if (mappedValue != null) return mappedValue

        val nearbyEntities = searchAroundEntity.level().getNearbyEntities(
            LivingEntity::class.java,
            TargetingConditions.DEFAULT,
            searchAroundEntity,
            searchAroundEntity.boundingBox.inflate(50.0)
        )

        for (entity in nearbyEntities) {
            if (entity.uuid == uuid) {
                entityUuidMap[uuid] = entity
                return entity
            }
        }

        return null
    }

    fun CompoundTag.getUuidOrNull(key: String): UUID? {
        if (!this.hasUUID(key)) return null
        return this.getUUID(key)
    }

    fun getEntityType(resourceLocation: ResourceLocation): EntityType<*> {
        val entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation)

        if (entityType === EntityType.PIG && resourceLocation != BuiltInRegistries.ENTITY_TYPE.defaultKey) {
            throw IllegalArgumentException("Unknown entity type: $resourceLocation")
        }

        return entityType
    }

    fun getPotionContents(itemStack: ItemStack): PotionContents? = itemStack.get(DataComponents.POTION_CONTENTS)
    fun getPotion(itemStack: ItemStack): Holder<Potion>? = getPotionContents(itemStack)?.potion?.get()

    fun getPotionStack(potion: Holder<Potion>): ItemStack {
        return PotionContents.createItemStack(Items.POTION, potion)
    }

}