package net.hennabatch.vrcneochi.parameter

import com.illposed.osc.OSCMessage

class EXParameter<T>(val address: String, val parameter: T){

    companion object{
        fun <T> byMessage(message: OSCMessage):EXParameter<T>? {
          val address = message.address
          val parameter = message.arguments[0] as? T  ?: return null
          return EXParameter(address, parameter)
        }
    }

    fun toMessage():OSCMessage{
        return OSCMessage(address, listOf(parameter))
    }

    fun update(newParam: T):EXParameter<T> {
        return EXParameter(address, newParam)
    }

}