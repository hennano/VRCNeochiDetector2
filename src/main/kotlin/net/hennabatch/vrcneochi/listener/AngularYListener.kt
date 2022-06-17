package net.hennabatch.vrcneochi.listener


import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.util.concurrent.TransferQueue
import kotlin.math.abs


class AngularYListener(val queue: TransferQueue<Int>) : VRCOSCParameterListener<Float>(){

    override fun exAddress(): String {
        return "/avatar/parameters/AngularY"
    }

    override fun recieveParameter(exParameter: VRCParameter<Float>) {
        //+のみ送出
        queue.add(abs(exParameter.parameter.toInt()))
    }
}