package net.hennabatch.vrcneochi.listener

import com.illposed.osc.OSCMessageEvent
import net.hennabatch.vrcneochi.logger.logger

class AngularYListener : VRCOSCListener(){

    override fun exParam(): String {
        return "AngularY"
    }

    override fun acceptMessage(event: OSCMessageEvent?) {
        val message = event?.message ?: return
        logger.info(message.address)
        message.arguments.forEach{logger.info(it.toString())}
    }
}