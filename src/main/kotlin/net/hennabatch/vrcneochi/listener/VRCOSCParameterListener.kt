package net.hennabatch.vrcneochi.listener

import com.illposed.osc.OSCMessageEvent
import net.hennabatch.vrcneochi.parameter.EXParameter

abstract class VRCOSCParameterListener<T> : VRCOSCListener(){

    override fun acceptMessage(event: OSCMessageEvent?) {
        val exParameter = event?.let { EXParameter.byMessage<T>(it.message) } ?: return
        recieveParameter(exParameter)
    }

    abstract fun recieveParameter(exParameter: EXParameter<T>)
}