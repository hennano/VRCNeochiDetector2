import com.illposed.osc.transport.OSCPortIn
import com.illposed.osc.transport.OSCPortInBuilder
import net.hennabatch.vrcneochi.listener.AngularYListener
import net.hennabatch.vrcneochi.listener.TestListener
import java.net.InetAddress
import java.net.InetSocketAddress

fun main(args: Array<String>) {

    val port = 9001
    val ip = InetAddress.getLoopbackAddress()

    val builder = OSCPortInBuilder()

    builder.setSocketAddress(InetSocketAddress(ip, port))

    val receiver = builder.build()
    receiver.dispatcher.addListener(AngularYListener().selector(), AngularYListener())

    receiver.startListening()
    Thread.sleep(1000000)
}