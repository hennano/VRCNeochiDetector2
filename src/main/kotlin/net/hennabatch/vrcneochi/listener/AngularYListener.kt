package net.hennabatch.vrcneochi.listener

import com.illposed.osc.transport.OSCPortOut
import net.hennabatch.vrcneochi.NeochiDetector
import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.util.concurrent.LinkedTransferQueue


class AngularYListener(val portOut: OSCPortOut) : VRCOSCParameterListener<Float>(){

    val queue = LinkedTransferQueue<Float>()
    val timer = NeochiDetector(portOut, queue)

    override fun exAddress(): String {
        return "/avatar/parameters/AngularY"
    }

    override fun recieveParameter(exParameter: VRCParameter<Float>) {
        queue.add(exParameter.parameter)
    }

    fun runTimer(){
        timer.run()
    }
}