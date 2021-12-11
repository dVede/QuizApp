package com.example.quizapp

import org.apache.commons.codec.binary.Base64


class Utils {
    companion object {
        const val SHARED_PREF_NAME = "user_info"
        const val DOT_REGEX = "\\."
        const val RIGHT_BRACE = "}"
        const val EMPTY_CHARACTER = ""
        const val COLON_CHARACTER = ":"
        
        fun decodeJWT(jwt: String): String {
            val tokens: Array<String> = jwt.split(DOT_REGEX).toTypedArray()
            val base64EncodedBody = tokens[1]
            val base64Url = Base64(true)
            return String(base64Url.decode(base64EncodedBody))
        }
    }
}