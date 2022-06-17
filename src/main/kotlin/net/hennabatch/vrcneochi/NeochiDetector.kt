package net.hennabatch.vrcneochi

import com.illposed.osc.transport.OSCPortOut
import net.hennabatch.vrcneochi.logger.logger
import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.util.concurrent.TimeUnit
import java.util.concurrent.TransferQueue

/**
 * 寝るまでの時間を計測などする
 */
class NeochiDetector(val portOut: OSCPortOut, private val queue: TransferQueue<Float>, private val min: Int = -(1000 / 50) * 60 * 5 * 2, private val max: Int = (1000 / 50) * 60 * 5 * 1) : Runnable{

    private var remaining = max

    var isSleeping = false

    override fun run() {
        while (true){
            val time = queue.poll(50, TimeUnit.MILLISECONDS)
            if(time != null){
                if(time < 0){
                    //-以下が送られたときはリセット
                    remaining = max
                    turnOFFReset()
                }else{
                    //+は追加
                    remaining += Math.min(time.toInt() / 10, 50)
                }
            }
            remaining = Math.min(max, Math.max(min, remaining - 5))
            logger.debug("$remaining")

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
    }

    //起きた状態にする
    fun sendWakeUp(){
        logger.info("wake up")
        portOut.send(VRCParameter("/avatar/parameters/isSleeping", false).toMessage())
    }
}