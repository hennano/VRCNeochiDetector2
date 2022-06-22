package net.hennabatch.vrcneochi.neochi

import com.illposed.osc.transport.OSCPortOut
import net.hennabatch.vrcneochi.logger.logger
import net.hennabatch.vrcneochi.neochi.NeochiPacket
import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.util.concurrent.TimeUnit
import java.util.concurrent.TransferQueue

/**
 * 寝るまでの時間を計測などする
 */
class NeochiDetector(val portOut: OSCPortOut, private val queue: TransferQueue<NeochiPacket>, private val min: Int = -300, private val max: Int = (1000 / 50) * 60 * 5 * 10) : Runnable{

    private var remaining = max

    private var isAutoMute = false

    var isSleeping = false

    override fun run() {
        while (true){
            val packet = queue.poll(50, TimeUnit.MILLISECONDS)
            if(packet != null){
                when (packet.type){
                    NeochiPacket.Type.RESET ->{
                        //残り時間のリセット
                        remaining = max
                        turnOFFReset()
                    }
                    NeochiPacket.Type.MUTE ->{
                        //MuteSelfがtrue
                        if(!isSleeping && isAutoMute) toggleMute() else isAutoMute = false
                    }
                    NeochiPacket.Type.UNMUTE ->{
                        //MuteSelfがfalse
                        if(isSleeping && isAutoMute) toggleMute() else isAutoMute = false
                    }
                    else -> remaining += Math.min(packet.param / 10, 50)
                }
            }

            remaining = Math.min(max, Math.max(min, remaining - 5))

            if(remaining < 0 && !isSleeping){
                //寝たとき
                isSleeping = true
                sendSleeping()
            }else if(remaining > 0 && isSleeping) {
                //起きたとき
                isSleeping = false
                sendWakeUp()
            }
        }
    }

    //リセットしたので値をOFFにする
    fun turnOFFReset(){
        logger.info("turnOFF reset")
        portOut.send(VRCParameter("/avatar/parameters/reset_sleep", false).toMessage())
    }

    //睡眠状態にする
    fun sendSleeping(){
        logger.info("sleeping")
        portOut.send(VRCParameter("/avatar/parameters/isSleeping", true).toMessage())
        toggleMute()
    }

    //起きた状態にする
    fun sendWakeUp(){
        logger.info("wake up")
        portOut.send(VRCParameter("/avatar/parameters/isSleeping", false).toMessage())
        toggleMute()
    }

    //ボイスのボタンを押す
    fun toggleMute(){
        logger.info("toggleMute")
        val voiceAddress = "/input/Voice"
        isAutoMute = true
        portOut.send(VRCParameter(voiceAddress, 0).toMessage())
        Thread.sleep(10)
        portOut.send(VRCParameter(voiceAddress, 1).toMessage())
        Thread.sleep(10)
        portOut.send(VRCParameter(voiceAddress, 0).toMessage())
    }
}