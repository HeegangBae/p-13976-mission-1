package com.back.domain.service

import com.back.domain.entity.WiseSaying
import com.back.domain.repository.WiseSayingRepository

class WiseSayingService(private val wiseSayingRepository: WiseSayingRepository) {

    fun write(content: String, author: String): Int {
        return wiseSayingRepository.write(content, author)
    }

    fun findAll():List<WiseSaying>

    {
        return wiseSayingRepository.findAll()
    }

    fun remove(id: Int): Boolean {
        return wiseSayingRepository.remove(id)
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayingRepository.findById(id)
    }

    fun modify(wiseSaying: WiseSaying, newContent: String, newAuthor: String) {
        wiseSayingRepository.modify(wiseSaying, newContent, newAuthor)
    }

    fun buildDataJson() {
        wiseSayingRepository.buildDataJson()
    }

    fun saveLastId() {
        wiseSayingRepository.saveLastId()
    }
}