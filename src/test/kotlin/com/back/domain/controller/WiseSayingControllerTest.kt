package com.back.domain.controller

import com.back.standard.app.AppConfig
import com.back.global.bean.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import TestRunner

@DisplayName("WiseSayingController 통합 테스트")
class WiseSayingControllerTest {

    @BeforeEach
    fun setUp() {
        AppConfig.setModeToTest()
        SingletonScope.wiseSayingFileRepository.clear()
    }

    @Test
    @DisplayName("등록")
    fun t1() {
        val input = """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
        """.trimIndent()

        val result = TestRunner.run(input)

        assertThat(result).contains("1번 명언이 등록되었습니다.")
    }

    @Test
    @DisplayName("목록")
    fun t2() {
        val input = """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            목록
        """.trimIndent()

        val result = TestRunner.run(input)

        assertThat(result).contains("번호 / 작가 / 명언")
        assertThat(result).contains("----------------------")
        // findAll()이 역순으로 반환하므로, 최근에 등록된 것이 먼저 출력됩니다.
        assertThat(result).contains("2 / 에디슨 / 천재는 99%의 노력과 1%의 영감이다.")
        assertThat(result).contains("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
    }

    @Test
    @DisplayName("삭제")
    fun t3() {
        val input = """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            삭제?id=1
            목록
        """.trimIndent()

        val result = TestRunner.run(input)

        assertThat(result).contains("1번 명언이 삭제되었습니다.")
        assertThat(result).doesNotContain("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("2 / 에디슨 / 천재는 99%의 노력과 1%의 영감이다.")
    }

    @Test
    @DisplayName("수정")
    fun t4() {
        val input = """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            수정?id=1
            나의 죽음을 적들에게 알리지 말라. 그리고 나의 삶을 적들에게 알리라.
            이순신 장군
            목록
        """.trimIndent()

        val result = TestRunner.run(input)

        assertThat(result).contains("1번 명언이 수정되었습니다.")
        assertThat(result).doesNotContain("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("1 / 이순신 장군 / 나의 죽음을 적들에게 알리지 말라. 그리고 나의 삶을 적들에게 알리라.")
    }

    @Test
    @DisplayName("빌드")
    fun t5() {
        val input = """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            빌드
        """.trimIndent()

        val result = TestRunner.run(input)

        assertThat(result).contains("data.json 파일의 내용이 갱신되었습니다.")
    }
}