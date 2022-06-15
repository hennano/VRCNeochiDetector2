package net.hennabatch.vrcneochi.sender

import com.illposed.osc.OSCMessage
import net.hennabatch.vrcneochi.parameter.EXParameter

class VRCOSCSender(private val exParameter: EXParameter){

    private val address = "/avatar/parameters/"

    fun toMessage():OSCMessage{
        val args: List<Any> = listOf(
            parameter(),
            parameterType()
        )
        return  OSCMessage(parameterName(), args)
    }


    private fun parameterName():String{
        return address + exParameter.name
    }

    private fun parameter(): String{
        return exParameter.parameter.toString()
    }

    private fun parameterType():String{
        return exParameter.type()
    }


}