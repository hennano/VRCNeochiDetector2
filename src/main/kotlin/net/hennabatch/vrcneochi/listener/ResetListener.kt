package net.hennabatch.vrcneochi.listener

import net.hennabatch.vrcneochi.logger.logger
import net.hennabatch.vrcneochi.neochi.NeochiPacket
import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.util.concurrent.TransferQueue

class ResetListener(private val queue: TransferQueue<NeochiPacket>) : VRCOSCParameterListener<Boolean>() {

    override fun exAddress(): String {
        return "/avatar/parameters/reset_sleep"
    }

    override fun recieveParameter(exParameter: VRCParameter<Boolean>) {
        if(exParameter.parameter) {
            //リセットを送出
            queue.add(NeochiPacket(type = NeochiPacket.Type.RESET))
            logger.info("reset timer")
        }
    }
}