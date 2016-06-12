package comhome.telegram

import java.nio.file.Paths

const val BASEURL = "https://api.telegram.org/bot{token}"

val token = try {
    Paths.get("token").toFile().readLines()[0]
} catch (e: Exception) {
    e.printStackTrace()
    System.exit(1234); ""
}

fun main(args: Array<String>) {
    val bot = TelegramBot(token)
    println(bot.me)

    bot.registerHandler {
        println(it)
        try {
            bot.sendMessage(it.getJSONObject("from").getInt("id").toString(), "TEST: ${it["text"]}")
        } catch (e: Exception) { e.printStackTrace() }
    }
}