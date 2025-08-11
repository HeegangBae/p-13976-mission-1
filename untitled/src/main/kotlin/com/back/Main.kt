import com.back.WiseSaying
import com.back.Rq
import java.io.File

fun main() {
    println("== 명언 앱 ==")

    val dbPath = "db/wiseSaying"
    val dbDir = File(dbPath)

    if (!dbDir.exists()) {
        dbDir.mkdirs()
    }

    val lastIdFile = File("$dbPath/lastId.txt")

    var lastId = if (lastIdFile.exists()) {
        lastIdFile.readText().toInt()
    } else {
        0
    }

    while(true){
        print("명령) ")
        val input = readlnOrNull()!!.trim()
        val rq = Rq.of(input)

        when(rq.command) {
            "종료" -> {
                lastIdFile.writeText(lastId.toString())
                println("종료합니다.")
                break
            }
            "등록" -> {
                print("명언: ")
                val content = readlnOrNull()!!.trim()
                print("작가: ")
                val author = readlnOrNull()!!.trim()
                val id = ++lastId

                val wiseSaying = WiseSaying(id, content, author)

                val jsonContent = """
                {
                  "id": $id,
                  "content": "$content",
                  "author": "$author"
                }
                """.trimIndent()

                File("$dbPath/$id.json").writeText(jsonContent)

                println("${id}번 명언이 등록되었습니다.")
            }
            "목록" -> {
                val wiseSayings = dbDir.listFiles { file ->
                    file.name.endsWith(".json")
                }?.sortedByDescending { it.nameWithoutExtension.toInt() }?.map { file ->
                    val content = file.readText()
                    val id = content.substringAfter("\"id\": ").substringBefore(",").trim().toInt()
                    val wiseContent = content.substringAfter("\"content\": \"").substringBefore("\"").trim()
                    val author = content.substringAfter("\"author\": \"").substringBefore("\"").trim()
                    WiseSaying(id, wiseContent, author)
                } ?: emptyList()

                if (wiseSayings.isEmpty()) {
                    println("등록된 명언이 없습니다.")
                } else {
                    println("번호 / 작가 / 명언")
                    println("----------------------")
                    for(wiseSaying in wiseSayings){
                        println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
                    }
                }
            }
            "삭제" -> {
                val id = rq.getIntParam("id", -1)
                if (id == -1) {
                    println("삭제?id=[번호] 와 같이 입력해주세요.")
                    continue
                }
                val fileToDelete = File("$dbPath/$id.json")
                if (fileToDelete.exists()) {
                    fileToDelete.delete()
                    println("${id}번 명언이 삭제되었습니다.")
                } else {
                    println("${id}번 명언은 존재하지 않습니다.")
                }
            }
            "수정" -> {
                val id = rq.getIntParam("id", -1)
                if (id == -1) {
                    println("수정?id=[번호] 와 같이 입력해주세요.")
                    continue
                }
                val fileToModify = File("$dbPath/$id.json")
                if (fileToModify.exists()) {
                    val existingContent = fileToModify.readText()
                    val existingAuthor = existingContent.substringAfter("\"author\": \"").substringBefore("\"").trim()
                    val existingWise = existingContent.substringAfter("\"content\": \"").substringBefore("\"").trim()

                    println("명언(기존) : $existingWise")
                    print("명언 : ")
                    val newContent = readlnOrNull()!!.trim()

                    println("작가(기존) : $existingAuthor")
                    print("작가 : ")
                    val newAuthor = readlnOrNull()!!.trim()

                    val updatedJson = """
                    {
                      "id": $id,
                      "content": "$newContent",
                      "author": "$newAuthor"
                    }
                    """.trimIndent()

                    fileToModify.writeText(updatedJson)
                    println("${id}번 명언이 수정되었습니다.")
                } else {
                    println("${id}번 명언은 존재하지 않습니다.")
                }
            }
        }
    }
}