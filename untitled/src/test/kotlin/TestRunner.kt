import com.back.App
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

object TestRunner {
    private val originalIn: InputStream = System.`in`
    private val originalOut: PrintStream = System.out

    fun run(input: String): String {
        // 입력 문자열을 정리하고, 끝에 명시적으로 "\n종료"를 추가합니다.
        // 이렇게 하면 Scanner가 마지막 "종료" 명령어를 반드시 읽을 수 있게 됩니다.
        val formattedInput = input.trimIndent().lines().joinToString("\n").plus("\n종료")

        return ByteArrayOutputStream().use { outputStream ->
            PrintStream(outputStream).use { printStream ->
                try {
                    // System.in에 테스트용 입력 스트림을 설정합니다.
                    System.setIn(
                        ByteArrayInputStream(
                            formattedInput.toByteArray()
                        )
                    )

                    // System.out에 테스트용 출력 스트림을 설정합니다.
                    System.setOut(printStream)

                    // 애플리케이션을 실행합니다.
                    App().run()
                } finally {
                    // 테스트가 끝난 후 System.in과 System.out을 원래 상태로 되돌립니다.
                    System.setIn(originalIn)
                    System.setOut(originalOut)
                }
            }
            // 출력 스트림의 내용을 문자열로 변환하고 정리하여 반환합니다.
            outputStream
                .toString()
                .trim()
                .replace("\r\n", "\n")
        }
    }
}