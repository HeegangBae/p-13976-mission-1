package com.back.domain.repository

import com.back.domain.entity.WiseSaying

interface WiseSayingRepository {
    fun write(content: String, author: String): Int
    fun findAll(): List<WiseSaying>
    fun remove(id: Int): Boolean
    fun findById(id: Int): WiseSaying?
    fun modify(wiseSaying: WiseSaying, newContent: String, newAuthor: String)
    fun buildDataJson()
    fun saveLastId()
}
