package net.hennabatch.vrcneochi.parameter

abstract class EXParameter(val name:String, val parameter: Any) {

    fun type():String{
        return parameter::class.simpleName ?: ""
    }
}