package net.hennabatch.vrcneochi.neochi

data class NeochiPacket(val param: Int = -1, val type: Type = Type.PARAMETER) {

    enum class Type{
        PARAMETER(),
        RESET(),
        MUTE(),
        UNMUTE();
    }
}