package com.back

import com.back.global.bean.SingletonScope
import com.back.global.bean.SingletonScope.scanner
import com.back.global.rq.Rq

class App {
    private val wiseSayingController = SingletonScope.wiseSayingController

    fun run() {
        println("== 명언 앱 ==")
        while (true) {
            print("명령) ")
            val input = scanner.nextLine().trim()
            val rq = Rq.of(input)

            when (rq.command) {
                "종료" -> {
                    wiseSayingController.exit()
                    break
                }
                "등록" -> wiseSayingController.write()
                "목록" -> wiseSayingController.list()
                "삭제" -> wiseSayingController.remove(rq)
                "수정" -> wiseSayingController.modify(rq)
                "빌드" -> wiseSayingController.build()
                else -> println("유효하지 않은 명령어입니다.")
            }
        }
    }
}