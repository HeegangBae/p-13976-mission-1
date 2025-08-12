package com.back.domain.repository

import com.back.global.bean.SingletonScope
import com.back.standard.app.AppConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class WiseSayingFileRepositoryTest {
    private val wiseSayingFileRepository = SingletonScope.wiseSayingFileRepository

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll(): Unit {
            AppConfig.setModeToTest()
        }
    }

    @BeforeEach
    fun setUp() {
        wiseSayingFileRepository.clear()
    }

    @Test
    fun `write`() {
        val content = "나의 죽음을 적들에게 알리지 말라."
        val author = "충무공 이순신"
        val id = wiseSayingFileRepository.write(content, author)
        val foundWiseSaying = wiseSayingFileRepository.findById(id)
        assertThat(foundWiseSaying).isNotNull
        assertThat(foundWiseSaying?.content).isEqualTo(content)
        assertThat(foundWiseSaying?.author).isEqualTo(author)
    }

    @Test
    fun `findById`() {
        val id = wiseSayingFileRepository.write("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신")
        val foundWiseSaying = wiseSayingFileRepository.findById(id)
        assertThat(foundWiseSaying).isNotNull
        assertThat(foundWiseSaying?.content).isEqualTo("나의 죽음을 적들에게 알리지 말라.")
        assertThat(foundWiseSaying?.author).isEqualTo("충무공 이순신")
    }

    @Test
    fun `remove`() {
        val id = wiseSayingFileRepository.write("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신")
        val removed = wiseSayingFileRepository.remove(id)
        assertThat(removed).isTrue
        assertThat(wiseSayingFileRepository.findById(id)).isNull()
    }

    @Test
    fun `findAll`() {
        wiseSayingFileRepository.write("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신")
        wiseSayingFileRepository.write("나를 파괴할 수 있는 사람이 없다.", "바토르")
        val foundWiseSayings = wiseSayingFileRepository.findAll()
        assertThat(foundWiseSayings.size).isEqualTo(2)
        assertThat(foundWiseSayings[0].content).isEqualTo("나를 파괴할 수 있는 사람이 없다.")
        assertThat(foundWiseSayings[1].content).isEqualTo("나의 죽음을 적들에게 알리지 말라.")
    }

}