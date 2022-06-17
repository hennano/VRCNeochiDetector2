import com.illposed.osc.transport.OSCPortInBuilder
import net.hennabatch.vrcneochi.listener.AngularYListener
import java.net.InetAddress
import java.net.InetSocketAddress

fun main(args: Array<String>) {

    val inPort = 9001

    val ip = InetAddress.getLoopbackAddress()

    val inBuilder = OSCPortInBuilder()

    inBuilder.setSocketAddress(InetSocketAddress(ip, inPort))

    val receiver = inBuilder.build()
    receiver.dispatcher.addListener(AngularYListener().selector, AngularYListener())

    receiver.startListening()
    Thread.sleep(10000)
}