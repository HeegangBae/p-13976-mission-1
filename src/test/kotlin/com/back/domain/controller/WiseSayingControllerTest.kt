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
            삭제?id=11
            목록
        """.trimIndent()
        val result = TestRunner.run(input)
        assertThat(result).contains("11번 명언이 삭제되었습니다.")
        assertThat(result).doesNotContain("11 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("12 / 에디슨 / 천재는 99%의 노력과 1%의 영감이다.")
    }

    @Test
    @DisplayName("수정")
    fun t4() {
        val input = """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            수정?id=11
            나의 죽음을 적들에게 알리지 말라. 그리고 나의 삶을 적들에게 알리라.
            이순신 장군
            목록
        """.trimIndent()
        val result = TestRunner.run(input)
        assertThat(result).contains("11번 명언이 수정되었습니다.")
        assertThat(result).doesNotContain("11 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("11 / 이순신 장군 / 나의 죽음을 적들에게 알리지 말라. 그리고 나의 삶을 적들에게 알리라.")
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

    @Test
    @DisplayName("검색")
    fun t6() {
        val input = """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            목록?keywordType=author&keyword=이순신
        """.trimIndent()

        val result = TestRunner.run(input)
        assertThat(result).contains("검색타입 : author")
        assertThat(result).contains("검색어 : 이순신")
        assertThat(result).contains("11 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).doesNotContain("12 / 에디슨 / 천재는 99%의 노력과 1%의 영감이다.")
    }

    @Test
    @DisplayName("페이징")
    fun t7() {
        val input = """
            등록
            명언 1
            작가 1
            등록
            명언 2
            작가 2
            등록
            명언 3
            작가 3
            등록
            명언 4
            작가 4
            등록
            명언 5
            작가 5
            등록
            명언 6
            작가 6
            목록?page=2
        """.trimIndent()
        val result = TestRunner.run(input)
        assertThat(result).contains("11 / 작가 1 / 명언 1")
        assertThat(result).contains("10 / 작자미상 10 / 명언 10")
        assertThat(result).contains("9 / 작자미상 9 / 명언 9")
        assertThat(result).contains("8 / 작자미상 8 / 명언 8")
        assertThat(result).contains("7 / 작자미상 7 / 명언 7")
        assertThat(result).doesNotContain("6 / 작가 6 / 명언 6")
        assertThat(result).contains("페이지 : 1 [2] 3 4 ")
    }
}