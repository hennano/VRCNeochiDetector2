package net.hennabatch.vrcneochi.parameter

import com.illposed.osc.OSCMessage

open class VRCParameter<T>(val address: String, val parameter: T){

    companion object{
        fun <T> byMessage(message: OSCMessage):VRCParameter<T>? {
          val address = message.address
          val parameter = message.arguments[0] as? T  ?: return null
          return VRCParameter(address, parameter)
        }
    }

    fun toMessage():OSCMessage{
        return OSCMessage(address, listOf(parameter))
    }

    fun update(newParam: T):VRCParameter<T> {
        return VRCParameter(address, newParam)
    }

}