package net.hennabatch.vrcneochi.listener

import net.hennabatch.vrcneochi.neochi.NeochiPacket
import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.util.concurrent.TransferQueue

class MuteSelfListener(val queue: TransferQueue<NeochiPacket>) : VRCOSCParameterListener<Boolean>() {

    override fun exAddress(): String {
        return "/avatar/parameters/MuteSelf"
    }

    override fun recieveParameter(exParameter: VRCParameter<Boolean>) {
        //mute状態を送出
        //true:-2
        //false :-3
        queue.add(NeochiPacket(type = if(exParameter.parameter) NeochiPacket.Type.MUTE else NeochiPacket.Type.UNMUTE))
    }
}