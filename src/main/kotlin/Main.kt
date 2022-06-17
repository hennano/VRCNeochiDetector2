import com.illposed.osc.transport.OSCPortInBuilder
import com.illposed.osc.transport.OSCPortOut
import net.hennabatch.vrcneochi.listener.AngularYListener
import net.hennabatch.vrcneochi.parameter.VRCParameter
import java.net.InetAddress
import java.net.InetSocketAddress

fun main(args: Array<String>) {

    val inPort = 9001
    val outPort = 9000

    val ip = InetAddress.getLoopbackAddress()

    val inBuilder = OSCPortInBuilder()

    inBuilder.setSocketAddress(InetSocketAddress(ip, inPort))

    val sender = OSCPortOut(InetSocketAddress(ip, outPort))
    val receiver = inBuilder.build()

    val angularYListener = AngularYListener(sender)
    receiver.dispatcher.addListener(angularYListener.selector, angularYListener)

    receiver.startListening()
    angularYListener.runTimer()

    Thread.sleep(10000000)

}