package net.hennabatch.vrcneochi.listener

import com.illposed.osc.OSCBadDataEvent
import com.illposed.osc.OSCMessage
import com.illposed.osc.OSCPacketEvent
import com.illposed.osc.OSCPacketListener
import net.hennabatch.vrcneochi.logger.logger

class TestListener: OSCPacketListener {
    override fun handlePacket(event: OSCPacketEvent?) {
        val msg = event?.packet as? OSCMessage ?: return
        logger.info(msg.address)
        msg.arguments.forEach{logger.info(it.toString())}
    }

    override fun handleBadData(event: OSCBadDataEvent?) {}
}