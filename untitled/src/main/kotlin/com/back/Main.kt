import com.back.WiseSaying

fun main() {
    println("== 명언 앱 ==")
    var lastid = 0
    val wiseSayings = mutableListOf<WiseSaying>()

    while(true){
        print("명령) ")
        val input = readlnOrNull()!!.trim()
        if(input == "종료"){
            break
        }else if(input == "등록"){
            print("명언: ")
            val content = readlnOrNull()!!.trim()
            print("작가: ")
            val author = readlnOrNull()!!.trim()
            val id = ++lastid
            wiseSayings.add(WiseSaying(id, content, author))
            println("${id}번 명언이 등록되었습니다.")
        }else if(input == "목록"){
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
    }
}