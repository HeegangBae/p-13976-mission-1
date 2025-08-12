package com.back.domain.controller

import com.back.global.rq.Rq
import com.back.domain.service.WiseSayingService
import java.util.Scanner

class WiseSayingController(private val wiseSayingService: WiseSayingService) {

    fun write(content: String, author: String): Int {
        return wiseSayingService.write(content, author)
    }

    fun write(scanner: Scanner) {
        print("명언 : ")
        val content = scanner.nextLine().trim()
        print("작가 : ")
        val author = scanner.nextLine().trim()

        val id = wiseSayingService.write(content, author)
        println("${id}번 명언이 등록되었습니다.")
    }

    fun list(rq: Rq) {
        val wiseSayings = wiseSayingService.findAll()
        if (wiseSayings.isEmpty()) {
            println("등록된 명언이 없습니다.")
            return
        }

        val page = rq.getIntParam("page", 1)
        val pageSize = 5 // 페이지당 명언 개수
        val totalCount = wiseSayings.size
        val totalPages = (totalCount + pageSize - 1) / pageSize

        val startIndex = (page - 1) * pageSize
        val endIndex = (startIndex + pageSize).coerceAtMost(totalCount)

        val pagedWiseSayings = wiseSayings.subList(startIndex, endIndex)

        println("번호 / 작가 / 명언")
        println("----------------------")
        pagedWiseSayings.forEach { wiseSaying ->
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }
        println("----------------------")

        // 페이지 네비게이션 출력
        print("페이지 : ")
        for (i in 1..totalPages) {
            if (i == page) {
                print("[$i] ")
            } else {
                print("$i ")
            }
        }
        println()
    }

    fun search(rq: Rq) {
        val keywordType = rq.getStringParam("keywordType", "")
        val keyword = rq.getStringParam("keyword", "")

        val allWiseSayings = wiseSayingService.findAll()

        val filteredWiseSayings = allWiseSayings.filter {
            when (keywordType) {
                "content" -> it.content.contains(keyword)
                "author" -> it.author.contains(keyword)
                else -> false
            }
        }

        if (filteredWiseSayings.isEmpty()) {
            println("검색 결과가 없습니다.")
            return
        }

        // 페이지 파라미터 가져오기 (기본값 1)
        val page = rq.getIntParam("page", 1)
        val pageSize = 5 // 페이지당 명언 개수
        val totalCount = filteredWiseSayings.size
        val totalPages = (totalCount + pageSize - 1) / pageSize

        val startIndex = (page - 1) * pageSize
        val endIndex = (startIndex + pageSize).coerceAtMost(totalCount)

        val pagedWiseSayings = filteredWiseSayings.subList(startIndex, endIndex)

        println("----------------------")
        println("검색타입 : ${keywordType}")
        println("검색어 : ${keyword}")
        println("----------------------")
        println("번호 / 작가 / 명언")
        println("----------------------")
        pagedWiseSayings.forEach { wiseSaying ->
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }
        println("----------------------")

        // 페이지 네비게이션 출력
        print("페이지 : ")
        for (i in 1..totalPages) {
            if (i == page) {
                print("[$i] ")
            } else {
                print("$i ")
            }
        }
        println()
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