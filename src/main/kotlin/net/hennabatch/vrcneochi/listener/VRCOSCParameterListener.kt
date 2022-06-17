package net.hennabatch.vrcneochi.listener

import com.illposed.osc.OSCMessageEvent
import net.hennabatch.vrcneochi.parameter.VRCParameter

abstract class VRCOSCParameterListener<T> : VRCOSCListener(){

    override fun acceptMessage(event: OSCMessageEvent?) {
        val exParameter = event?.let { VRCParameter.byMessage<T>(it.message) } ?: return
        recieveParameter(exParameter)
    }

    abstract fun recieveParameter(exParameter: VRCParameter<T>)
}