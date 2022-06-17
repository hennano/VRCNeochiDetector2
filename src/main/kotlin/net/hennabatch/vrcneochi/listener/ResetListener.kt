package net.hennabatch.vrcneochi.listener

import net.hennabatch.vrcneochi.logger.logger
import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.util.concurrent.TransferQueue

class ResetListener(private val queue: TransferQueue<Int>) : VRCOSCParameterListener<Boolean>() {

    override fun exAddress(): String {
        return "/avatar/parameters/reset_sleep"
    }

    override fun recieveParameter(exParameter: VRCParameter<Boolean>) {
        if(exParameter.parameter) {
            //リセットを送出
            queue.add(-1)
            logger.info("reset timer")
        }
    }
}