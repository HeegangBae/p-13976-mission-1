import com.back.App
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

object TestRunner {
    private val originalIn: InputStream = System.`in`
    private val originalOut: PrintStream = System.out

    fun run(input: String): String {
        val formattedInput = input.trimIndent().lines().joinToString("\n").plus("\n종료")

        return ByteArrayOutputStream().use { outputStream ->
            PrintStream(outputStream).use { printStream ->
                try {
                    System.setIn(
                        ByteArrayInputStream(
                            formattedInput.toByteArray()
                        )
                    )
                    System.setOut(printStream)
                    App().run()
                } finally {
                    System.setIn(originalIn)
                    System.setOut(originalOut)
                }
            }
            outputStream
                .toString()
                .trim()
                .replace("\r\n", "\n")
        }
    }
}