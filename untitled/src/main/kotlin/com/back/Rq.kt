package com.back

class Rq(val command: String, val paramMap: Map<String, String>) {

    companion object {
        fun of(input: String): Rq {
            val parts = input.split("?", limit = 2)
            val command = parts[0]

            val paramMap = mutableMapOf<String, String>()
            if (parts.size > 1) {
                val params = parts[1].split("&")
                for (param in params) {
                    val keyAndValue = param.split("=", limit = 2)
                    if (keyAndValue.size == 2) {
                        paramMap[keyAndValue[0]] = keyAndValue[1]
                    }
                }
            }
            return Rq(command, paramMap)
        }
    }

    fun getIntParam(key: String, defaultValue: Int): Int {
        return paramMap[key]?.toIntOrNull() ?: defaultValue
    }
}