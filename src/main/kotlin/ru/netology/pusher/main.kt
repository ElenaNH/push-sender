package ru.netology.pusher

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.io.FileInputStream


fun main() {
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
        .build()

    FirebaseApp.initializeApp(options)

    // Токен должен быть тот, который мы скопируем в момент генерации, но его не надо выкладывать на github, а надо написать тут получение из базы или файла
    val token =
        "скопированный токен приложения получателя"

    pushMessage(token, "LIKE")
    pushMessage(token, "NEW_POST")
    pushMessage(token, "QQ")

    /*val message = Message.builder()
        .putData("action", "LIKE")
        .putData("content", """{
          "userId": 1,
          "userName": "Vasiliy",
          "postId": 2,
          "postAuthor": "Netology"
        }""".trimIndent())
        .setToken(token)
        .build()

    FirebaseMessaging.getInstance().send(message)*/
}

private fun pushMessage(token: String, action: String) {
    val users = listOf("Vasiliy", "Anna", "Nina", "Olga", "Alex", "Peter")
    val userId = (1..users.size).random()

    val content = when (action) {
        "LIKE" -> """{
          "userId": $userId,
          "userName": ${users[userId - 1]},
          "postId": 2,
          "postAuthor": "Netology"
        }""".trimIndent()

        "NEW_POST" -> """{
          "userId": $userId,
          "userName": ${users[userId - 1]},
          "postId": 101,
          "content": "Погода сегодня отличная. Летают бабочки, поют птицы, солнце греет вовсю!"
        }""".trimIndent()

        else -> "{}"
    }

    println(action)

    val message = Message.builder()
        .putData("action", action)
        .putData("content", content)
        .setToken(token)
        .build()

    FirebaseMessaging.getInstance().send(message)

}