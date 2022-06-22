package net.hennabatch.vrcneochi.listener

import net.hennabatch.vrcneochi.logger.logger
import net.hennabatch.vrcneochi.neochi.NeochiPacket
import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.util.concurrent.TransferQueue

class VelocityListener(private val queue: TransferQueue<NeochiPacket>) : VRCOSCParameterListener<Float>(){

    override fun exAddress(): String {
        return "/avatar/parameters/Velocity."
    }

    override fun recieveParameter(exParameter: VRCParameter<Float>) {
        queue.add(NeochiPacket(param = 50))
    }
}