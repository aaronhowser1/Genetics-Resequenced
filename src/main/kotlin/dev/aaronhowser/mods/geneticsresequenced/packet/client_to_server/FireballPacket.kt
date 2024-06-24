package dev.aaronhowser.mods.geneticsresequenced.packet.client_to_server

import dev.aaronhowser.mods.geneticsresequenced.packet.ModPacket
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class FireballPacket : ModPacket {

    override fun receiveMessage(context: IPayloadContext) {
        val sender = context.player()
        PacketGenes.dragonBreath(serverPlayer)
    }

    override fun type(): CustomPacketPayload.Type<FireballPacket> = TYPE

    companion object {
        val TYPE: CustomPacketPayload.Type<FireballPacket> =
            CustomPacketPayload.Type<FireballPacket>(OtherUtil.modResource("fireball"))

        val STREAM_CODEC: StreamCodec<ByteBuf, FireballPacket> = StreamCodec.unit(FireballPacket())
    }

}