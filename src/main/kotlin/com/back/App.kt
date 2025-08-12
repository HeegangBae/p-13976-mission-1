package com.back

import com.back.domain.controller.WiseSayingController
import com.back.global.bean.SingletonScope
import com.back.global.rq.Rq
import java.util.Scanner

class App {
    private val wiseSayingController = SingletonScope.wiseSayingController

    fun run() {
        println("== 명언 앱 ==")

        // 샘플 데이터 10개 생성
        for (i in 1..10) {
            wiseSayingController.write(content = "명언 $i", author = "작자미상 $i")
        }

        val scanner = Scanner(System.`in`)
        while (true) {
            print("명령) ")
            val input = scanner.nextLine().trim()
            val rq = Rq.of(input)

            when (rq.command) {
                "종료" -> {
                    wiseSayingController.exit()
                    break
                }
                "등록" -> wiseSayingController.write(scanner)
                "목록" -> {
                    if (rq.hasParam("keyword")) {
                        wiseSayingController.search(rq)
                    } else {
                        wiseSayingController.list(rq)
                    }
                }
                "삭제" -> wiseSayingController.remove(rq)
                "수정" -> wiseSayingController.modify(rq, scanner)
                "빌드" -> wiseSayingController.build()
                else -> println("유효하지 않은 명령어입니다.")
            }
        }
    }
}