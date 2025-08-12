package com.back.domain.repository

import com.back.domain.entity.WiseSaying
import com.back.standard.app.AppConfig
import com.back.standard.util.JsonParser.jsonToWiseSaying
import com.back.standard.util.JsonParser.wiseSayingToJson
import java.io.File
import java.nio.file.Files
import java.util.Comparator

class WiseSayingFileRepository : WiseSayingRepository {
    private val dbPath = AppConfig.getDbDirPath().toString()
    private val dbDir = File(dbPath).apply { mkdirs() }
    private val lastIdFile = File("$dbPath/lastId.txt")

    private var lastId = if (lastIdFile.exists()) {
        lastIdFile.readText().toInt()
    } else {
        0
    }

    private fun ensureDirExists(file: File) {
        file.parentFile?.mkdirs()
    }

    override fun write(content: String, author: String): Int {
        val id = ++lastId
        val wiseSaying = WiseSaying(id, content, author)
        val jsonContent = wiseSayingToJson(wiseSaying)

        val file = File("$dbPath/$id.json")
        ensureDirExists(file)
        file.writeText(jsonContent)

        return id
    }

    override fun findAll(): List<WiseSaying> {
        return dbDir.listFiles { file ->
            file.name.endsWith(".json")
        }?.sortedByDescending { it.nameWithoutExtension.toInt() }?.mapNotNull { file ->
            jsonToWiseSaying(file.readText())
        } ?: emptyList()
    }

    override fun remove(id: Int): Boolean {
        val fileToDelete = File("$dbPath/$id.json")
        return if (fileToDelete.exists()) {
            fileToDelete.delete()
            true
        } else {
            false
        }
    }

    override fun findById(id: Int): WiseSaying? {
        val file = File("$dbPath/$id.json")
        return if (file.exists()) {
            jsonToWiseSaying(file.readText(), id)
        } else {
            null
        }
    }

    override fun modify(wiseSaying: WiseSaying, newContent: String, newAuthor: String) {
        val updatedWiseSaying = wiseSaying.copy(content = newContent, author = newAuthor)
        val updatedJson = wiseSayingToJson(updatedWiseSaying)
        File("$dbPath/${wiseSaying.id}.json").writeText(updatedJson)
    }

    override fun buildDataJson() {
        val jsonContents = dbDir.listFiles { file ->
            file.name.endsWith(".json")
        }?.sortedBy { it.nameWithoutExtension.toInt() }?.map { file ->
            file.readText()
        } ?: emptyList()

        val finalJson = "[${jsonContents.joinToString(",\n")}]"
        File("db/data.json").writeText(finalJson)
    }

    override fun saveLastId() {
        ensureDirExists(lastIdFile)
        lastIdFile.writeText(lastId.toString())
    }

    fun clear() {
        val path = AppConfig.getDbDirPath()
        if (Files.exists(path)) {
            Files.walk(path).sorted(Comparator.reverseOrder()).forEach { Files.delete(it) }
        }
        lastId = 0
    }
}