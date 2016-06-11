package comhome.telegram

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import org.json.JSONObject

const val TOKEN = "216511865:AAEI9-NxYuQnYVC9zDGxoXsSi3lxTKBA9Yw"
const val BASEURL = "https://api.telegram.org/bot{token}"


fun main(args: Array<String>) {
    val bot = TelegramBot(TOKEN)
    println(bot.me)
}

class TelegramBot(val token: String) {
    val me by lazy { post("getMe") }

    fun sendMessage(chatID: String, text: String) {

    }

    fun post(method: String, json: JSONObject? = null): JSONObject {
        val req = Unirest.post("$BASEURL/$method")
                .routeParam("token", token)

        if (json != null) {
            req.header("Content-Type", "application/json").body(json)
        }

        val resp = req.asJson()

        if (resp.body.isArray) {  }

        return resp.body.`object`.getJSONObject("result")
    }
}

class TelegramException(message: String): Exception(message)

//json util
fun json(init: JSONBuilder.() -> Unit) = JSONBuilder().apply(init).obj

class JSONBuilder {
    val obj = JSONObject()
    fun String.to(value: Any) = obj.put(this, value)
    fun String.to(init: JSONBuilder.() -> Unit) = obj.put(this, json { init() })
}