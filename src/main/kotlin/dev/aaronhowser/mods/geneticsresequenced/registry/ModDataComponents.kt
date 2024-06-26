package dev.aaronhowser.mods.geneticsresequenced.registry

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.item.components.*
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModDataComponents {

    val DATA_COMPONENT_REGISTRY: DeferredRegister<DataComponentType<*>> =
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, GeneticsResequenced.ID)

    val ENTITY_TYPE_COMPONENT: DeferredHolder<DataComponentType<*>, DataComponentType<EntityTypeItemComponent>> =
        DATA_COMPONENT_REGISTRY.register("entity_type", Supplier {
            DataComponentType.builder<EntityTypeItemComponent>()
                .persistent(EntityTypeItemComponent.CODEC)
                .networkSynchronized(EntityTypeItemComponent.STREAM_CODEC)
                .build()
        })

    val SPECIFIC_ENTITY_COMPONENT: DeferredHolder<DataComponentType<*>, DataComponentType<SpecificEntityItemComponent>> =
        DATA_COMPONENT_REGISTRY.register("specific_entity", Supplier {
            DataComponentType.builder<SpecificEntityItemComponent>()
                .persistent(SpecificEntityItemComponent.CODEC)
                .networkSynchronized(SpecificEntityItemComponent.STREAM_CODEC)
                .build()
        })

    val IS_ACTIVE_COMPONENT: DeferredHolder<DataComponentType<*>, DataComponentType<BooleanItemComponent>> =
        DATA_COMPONENT_REGISTRY.register("active", Supplier {
            DataComponentType.builder<BooleanItemComponent>()
                .persistent(BooleanItemComponent.CODEC)
                .networkSynchronized(BooleanItemComponent.STREAM_CODEC)
                .build()
        })

    val IS_CONTAMINATED_COMPONENT: DeferredHolder<DataComponentType<*>, DataComponentType<BooleanItemComponent>> =
        DATA_COMPONENT_REGISTRY.register("contaminated", Supplier {
            DataComponentType.builder<BooleanItemComponent>()
                .persistent(BooleanItemComponent.CODEC)
                .networkSynchronized(BooleanItemComponent.STREAM_CODEC)
                .build()
        })

    val GENES_COMPONENT: DeferredHolder<DataComponentType<*>, DataComponentType<GenesItemComponent>> =
        DATA_COMPONENT_REGISTRY.register("genes", Supplier {
            DataComponentType.builder<GenesItemComponent>()
                .persistent(GenesItemComponent.CODEC)
                .networkSynchronized(GenesItemComponent.STREAM_CODEC)
                .build()
        })

    val ANTIGENES_COMPONENT: DeferredHolder<DataComponentType<*>, DataComponentType<GenesItemComponent>> =
        DATA_COMPONENT_REGISTRY.register("antigenes", Supplier {
            DataComponentType.builder<GenesItemComponent>()
                .persistent(GenesItemComponent.CODEC)
                .networkSynchronized(GenesItemComponent.STREAM_CODEC)
                .build()
        })

    val PLASMID_PROGRESS_COMPONENT: DeferredHolder<DataComponentType<*>, DataComponentType<PlasmidProgressItemComponent>> =
        DATA_COMPONENT_REGISTRY.register("plasmid_progress", Supplier {
            DataComponentType.builder<PlasmidProgressItemComponent>()
                .persistent(PlasmidProgressItemComponent.CODEC)
                .networkSynchronized(PlasmidProgressItemComponent.STREAM_CODEC)
                .build()
        })

    val GENE_COMPONENT: DeferredHolder<DataComponentType<*>, DataComponentType<GeneItemComponent>> =
        DATA_COMPONENT_REGISTRY.register("gene", Supplier {
            DataComponentType.builder<GeneItemComponent>()
                .persistent(GeneItemComponent.CODEC)
                .networkSynchronized(GeneItemComponent.STREAM_CODEC)
                .build()
        })

}