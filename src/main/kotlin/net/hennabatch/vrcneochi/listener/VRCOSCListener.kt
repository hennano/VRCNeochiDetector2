package net.hennabatch.vrcneochi.listener

import com.illposed.osc.MessageSelector
import com.illposed.osc.OSCMessageEvent
import com.illposed.osc.OSCMessageListener

abstract class VRCOSCListener: OSCMessageListener {

    val selector = object : MessageSelector {
        override fun isInfoRequired(): Boolean {
            return false
        }

        override fun matches(messageEvent: OSCMessageEvent?): Boolean {
            val msgAddress = messageEvent?.message?.address ?: return false
            return msgAddress.matches(Regex(exAddress()))
        }
    }

    abstract fun exAddress():String
}