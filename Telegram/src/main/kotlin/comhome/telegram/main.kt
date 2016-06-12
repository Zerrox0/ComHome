package comhome.telegram

const val BASEURL = "https://api.telegram.org/bot{token}"

fun main(args: Array<String>) {
    val bot = TelegramBot()

    println(bot.me)

    bot.registerHandler {
        println(it)
        try {
            bot.sendMessage(it.getJSONObject("from").getInt("id").toString(), "TEST: ${it["text"]}")
        } catch (e: Exception) { e.printStackTrace() }
    }
}