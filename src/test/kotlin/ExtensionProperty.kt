package com.dietmate.dietmate

fun ExtensionProperty.getTwo() = getOne() + 1

class ExtensionProperty {

    fun getOne() : Int{
        return 1
    }
}