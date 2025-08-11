package com.back

fun main() {
    println("== 명언 앱 ==")
    while(true){
        print("명령) ")
        val input = readlnOrNull()!!.trim()
        if(input == "종료"){
            break
        }
    }
}