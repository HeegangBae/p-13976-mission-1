package com.back.standard.util

import com.back.domain.entity.WiseSaying

object JsonParser {
    fun wiseSayingToJson(wiseSaying: WiseSaying): String {
        return """
            {
              "id": ${wiseSaying.id},
              "content": "${wiseSaying.content}",
              "author": "${wiseSaying.author}"
            }
        """.trimIndent()
    }

    fun jsonToWiseSaying(jsonString: String, id: Int? = null): WiseSaying? {
        return try {
            val wiseContent = jsonString.substringAfter("\"content\": \"").substringBefore("\"").trim()
            val author = jsonString.substringAfter("\"author\": \"").substringBefore("\"").trim()
            val wiseId = id ?: jsonString.substringAfter("\"id\": ").substringBefore(",").trim().toInt()
            WiseSaying(wiseId, wiseContent, author)
        } catch (e: Exception) {
            null
        }
    }
}