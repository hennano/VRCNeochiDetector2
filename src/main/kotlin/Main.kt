import com.illposed.osc.OSCMessage
import com.illposed.osc.transport.OSCPortIn
import com.illposed.osc.transport.OSCPortInBuilder
import com.illposed.osc.transport.OSCPortOut
import com.illposed.osc.transport.OSCPortOutBuilder
import net.hennabatch.vrcneochi.listener.AngularYListener
import net.hennabatch.vrcneochi.listener.TestListener
import net.hennabatch.vrcneochi.parameter.IntParameter
import net.hennabatch.vrcneochi.sender.VRCOSCSender
import java.net.InetAddress
import java.net.InetSocketAddress
import kotlin.math.log

fun main(args: Array<String>) {

    val inPort = 9001
    val outPort = 9000
    val ip = InetAddress.getLoopbackAddress()

    val inBuilder = OSCPortInBuilder()

    inBuilder.setSocketAddress(InetSocketAddress(ip, inPort))

    val receiver = inBuilder.build()
    receiver.dispatcher.addListener(AngularYListener().selector(), AngularYListener())

    receiver.startListening()
    Thread.sleep(1000)

    val sender = OSCPortOut(InetSocketAddress(ip, outPort))
    val bagActivate = VRCOSCSender(IntParameter("bag_State", 0))

    sender.send(bagActivate.toMessage())
}