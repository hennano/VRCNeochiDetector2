package net.hennabatch.vrcneochi.listener

import com.illposed.osc.MessageSelector
import com.illposed.osc.OSCMessageEvent
import com.illposed.osc.OSCMessageListener

abstract class VRCOSCListener : OSCMessageListener {
    private val address = "/avatar/parameters/"

    private val selector = object : MessageSelector {
        override fun isInfoRequired(): Boolean {
            return false
        }


        override fun matches(messageEvent: OSCMessageEvent?): Boolean {
            val msgParam = messageEvent?.message?.address ?: return false
            val exParamName = msgParam.replace(address, "")
            val regex = Regex(exParam())
            return regex.matches(exParamName)
        }
    }

    /**
     * expressionパラメータ名を指定
     * 正規表現の使用可
     */
    abstract fun exParam():String

    /**
     * Listener登録時に利用
     */
    fun selector(): MessageSelector {
        return selector
    }
}