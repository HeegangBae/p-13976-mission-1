package com.back.global.bean

import com.back.domain.controller.WiseSayingController
import com.back.domain.repository.WiseSayingFileRepository
import com.back.domain.repository.WiseSayingRepository
import com.back.domain.service.WiseSayingService
import com.back.standard.app.AppConfig

object SingletonScope {
    val wiseSayingFileRepository by lazy { WiseSayingFileRepository() }

    val wiseSayingRepository: WiseSayingRepository by lazy {
        if (AppConfig.getMode() == "test") {
            wiseSayingFileRepository
        } else {
            WiseSayingFileRepository()
        }
    }

    val wiseSayingService: WiseSayingService by lazy { WiseSayingService(wiseSayingRepository) }
    val wiseSayingController: WiseSayingController by lazy { WiseSayingController(wiseSayingService) }
}