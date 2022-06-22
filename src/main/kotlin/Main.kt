import com.illposed.osc.transport.OSCPortInBuilder
import com.illposed.osc.transport.OSCPortOut
import net.hennabatch.vrcneochi.neochi.NeochiDetector
import net.hennabatch.vrcneochi.listener.AngularYListener
import net.hennabatch.vrcneochi.listener.MuteSelfListener
import net.hennabatch.vrcneochi.listener.ResetListener
import net.hennabatch.vrcneochi.neochi.NeochiPacket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.concurrent.LinkedTransferQueue

fun main(args: Array<String>) {
    println("start")

    val inPort = 9001
    val outPort = 9000

    val ip = InetAddress.getLoopbackAddress()

    //受信の生成
    val inBuilder = OSCPortInBuilder()
    inBuilder.setSocketAddress(InetSocketAddress(ip, inPort))
    val receiver = inBuilder.build()

    //送信の生成
    val sender = OSCPortOut(InetSocketAddress(ip, outPort))

    //タイマー準備
    val queue = LinkedTransferQueue<NeochiPacket>()
    val timer = NeochiDetector(sender, queue)

    //リスナーの生成
    val angularYListener = AngularYListener(queue)
    receiver.dispatcher.addListener(angularYListener.selector, angularYListener)
    val resetListener = ResetListener(queue)
    receiver.dispatcher.addListener(resetListener.selector, resetListener)
    val muteSelfListener = MuteSelfListener(queue)
    receiver.dispatcher.addListener(muteSelfListener.selector, muteSelfListener)

    //受信の開始
    receiver.startListening()
    val timerThread = Thread(timer)
    //タイマーの開始
    timerThread.start()
    timerThread.join()
}