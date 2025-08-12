package com.back.global.bean

import com.back.domain.controller.WiseSayingController
import com.back.domain.repository.WiseSayingFileRepository
import com.back.domain.repository.WiseSayingRepository
import com.back.domain.service.WiseSayingService
import java.util.Scanner

object SingletonScope {
    // FileRepository와 MemoryRepository 중 선택할 수 있습니다.
    val wiseSayingRepository: WiseSayingRepository by lazy { WiseSayingFileRepository() }
    // val wiseSayingRepository: WiseSayingRepository by lazy { WiseSayingMemoryRepository() }
    val wiseSayingService: WiseSayingService by lazy { WiseSayingService(wiseSayingRepository) }
    val wiseSayingController: WiseSayingController by lazy { WiseSayingController(wiseSayingService) }
    val scanner by lazy { Scanner(System.`in`) }
}