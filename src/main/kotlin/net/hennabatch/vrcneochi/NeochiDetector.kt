package net.hennabatch.vrcneochi

import com.illposed.osc.transport.OSCPortOut
import net.hennabatch.vrcneochi.logger.logger
import java.util.concurrent.TimeUnit
import java.util.concurrent.TransferQueue
import javax.print.attribute.standard.NumberUp
import kotlin.math.abs

/**
 * 寝るまでの時間を計測などする
 */
class NeochiDetector(val portOut: OSCPortOut, private val queue: TransferQueue<Float>, private val min: Int = -(1000 / 50) * 60 * 5 * 2, private val max: Int = (1000 / 50) * 60 * 5 * 5) : Runnable{

    private var remaining = max

    var isSleeping = false

    override fun run() {
        while (true){
            val time = queue.poll(50, TimeUnit.MILLISECONDS)
            if(time != null){
                remaining += Math.min(Math.abs(time.toInt()) / 10, 50)
            }
            remaining = Math.min(max, Math.max(min, remaining - 5))
            //logger.debug("$remaining")

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

    fun reset(){
        remaining = max
    }

    fun sendSleeping(){
        logger.info("sleeping")
    }

    fun sendWakeUp(){
        logger.info("wake up")
    }
}