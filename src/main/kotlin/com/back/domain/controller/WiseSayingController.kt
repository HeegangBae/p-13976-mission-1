package com.back.domain.controller

import com.back.global.rq.Rq
import com.back.domain.service.WiseSayingService
import java.util.Scanner

class WiseSayingController(private val wiseSayingService: WiseSayingService) {

    fun write(scanner: Scanner) {
        print("명언 : ")
        val content = scanner.nextLine().trim()
        print("작가 : ")
        val author = scanner.nextLine().trim()

        val id = wiseSayingService.write(content, author)
        println("${id}번 명언이 등록되었습니다.")
    }

    fun list() {
        val wiseSayings = wiseSayingService.findAll()
        if (wiseSayings.isEmpty()) {
            println("등록된 명언이 없습니다.")
            return
        }

        println("번호 / 작가 / 명언")
        println("----------------------")
        wiseSayings.forEach { wiseSaying ->
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }
    }

    fun remove(rq: Rq) {
        val id = rq.getIntParam("id", -1)
        if (id == -1) {
            println("삭제?id=[번호] 와 같이 입력해주세요.")
            return
        }

        val deleted = wiseSayingService.remove(id)
        if (deleted) {
            println("${id}번 명언이 삭제되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }

    fun modify(rq: Rq, scanner: Scanner) {
        val id = rq.getIntParam("id", -1)
        if (id == -1) {
            println("수정?id=[번호] 와 같이 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.findById(id)
        if (wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return
        }

        println("명언(기존) : ${wiseSaying.content}")
        print("명언 : ")
        val newContent = scanner.nextLine().trim()

        println("작가(기존) : ${wiseSaying.author}")
        print("작가 : ")
        val newAuthor = scanner.nextLine().trim()

        wiseSayingService.modify(wiseSaying, newContent, newAuthor)
        println("${id}번 명언이 수정되었습니다.")
    }

    fun exit() {
        wiseSayingService.saveLastId()
        wiseSayingService.buildDataJson()
    }

    fun build() {
        wiseSayingService.buildDataJson()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }
}