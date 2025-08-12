package com.back.standard.app

import java.nio.file.Path
import java.nio.file.Paths

object AppConfig {
    private var mode = "dev"

    fun setModeToTest() {
        mode = "test"
    }

    fun getMode(): String {
        return mode
    }

    val dbDirPath: Path = Paths.get("db", "wiseSaying", mode)
}