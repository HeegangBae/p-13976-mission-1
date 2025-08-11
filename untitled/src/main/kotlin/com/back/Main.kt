import com.back.WiseSaying
import com.back.Rq

fun main() {
    println("== 명언 앱 ==")
    var lastid = 0
    val wiseSayings = mutableListOf<WiseSaying>()

    while(true){
        print("명령) ")
        val input = readlnOrNull()!!.trim()

        val rq = Rq.of(input)

        when(rq.command) {
            "종료" -> {
                println("종료합니다.")
                break
            }
            "등록" -> {
                print("명언: ")
                val content = readlnOrNull()!!.trim()
                print("작가: ")
                val author = readlnOrNull()!!.trim()
                val id = ++lastid
                wiseSayings.add(WiseSaying(id, content, author))
                println("${id}번 명언이 등록되었습니다.")
            }
            "목록" -> {
                if(wiseSayings.isEmpty()){
                    println("등록된 명언이 없습니다.")
                }else{
                    println("번호 / 작가 / 명언")
                    println("----------------------")
                    for(wiseSaying in wiseSayings.reversed()){
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

                val wiseSayingToRemove = wiseSayings.find { it.id == id }
                if (wiseSayingToRemove != null) {
                    wiseSayings.remove(wiseSayingToRemove)
                    println("${id}번 명언이 삭제되었습니다.")
                } else {
                    println("${id}번 명언은 존재하지 않습니다.")
                }
            }
        }
    }
}