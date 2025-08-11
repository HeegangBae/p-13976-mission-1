package com.back

fun main() {
    println("== 명언 앱 ==")
    while(true){
        print("명령) ")
        val input = readlnOrNull()!!.trim()
        if(input == "종료"){
            break
        }else if(input == "등록"){
            print("명언: ")
            val content = readlnOrNull()!!.trim()
            print("작가: ")
            val author = readlnOrNull()!!.trim()
        }
    }
}