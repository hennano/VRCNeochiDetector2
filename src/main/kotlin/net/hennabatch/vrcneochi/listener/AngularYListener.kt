package net.hennabatch.vrcneochi.listener

import net.hennabatch.vrcneochi.logger.logger
import net.hennabatch.vrcneochi.parameter.EXParameter


class AngularYListener : VRCOSCParameterListener<Float>(){

    override fun exAddress(): String {
        return "/avatar/parameters/AngularY"
    }

    override fun recieveParameter(exParameter: EXParameter<Float>) {
        logger.info("${exParameter.address}: ${exParameter.parameter}")
    }


}