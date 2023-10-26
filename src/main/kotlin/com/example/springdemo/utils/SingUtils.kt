package com.example.springdemo.utils

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.util.*

class SingUtils {
    companion object {
        fun sign(values: MutableList<String>, ticket: String): String { //values传ticket外的其他参数
            values.add(ticket)
            values.sort()
            val sb = StringBuilder()
            for (s in values) {
                sb.append(s)
            }
            return Hashing.sha1().hashString(
                sb,
                StandardCharsets.UTF_8
            ).toString().uppercase(Locale.getDefault())
        }
    }
}