package com.back.domain.repository

import com.back.domain.entity.WiseSaying

class WiseSayingMemoryRepository : WiseSayingRepository {
    private var lastId = 0
    private val wiseSayings = mutableListOf<WiseSaying>()

    override fun write(content: String, author: String): Int {
        val id = ++lastId
        val wiseSaying = WiseSaying(id, content, author)
        wiseSayings.add(wiseSaying)
        return id
    }

    override fun findAll(): List<WiseSaying> {
        return wiseSayings.reversed()
    }

    override fun remove(id: Int): Boolean {
        val wiseSayingToRemove = findById(id)
        return if (wiseSayingToRemove != null) {
            wiseSayings.remove(wiseSayingToRemove)
            true
        } else {
            false
        }
    }

    override fun findById(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    override fun modify(wiseSaying: WiseSaying, newContent: String, newAuthor: String) {
        val index = wiseSayings.indexOf(wiseSaying)
        wiseSayings[index] = wiseSaying.copy(content = newContent, author = newAuthor)
    }

    override fun buildDataJson() {
    }

    override fun saveLastId() {
    }
}