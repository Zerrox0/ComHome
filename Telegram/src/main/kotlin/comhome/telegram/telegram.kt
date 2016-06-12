package comhome.telegram

import com.mashape.unirest.http.Unirest
import org.json.JSONObject
import java.nio.file.Paths
import java.util.concurrent.Executors

class TelegramException(message: String, data: Any? = null) : Exception(message)

class TelegramBot(
    val token: String = try {
        Paths.get("token").toFile().readText().trim()
    } catch (e: Exception) {
        throw TelegramException("No token specified and no token file found.", e)
    }
) {
    val updateThread = Executors.newSingleThreadExecutor()

    //TODO: classes
    val me by lazy { post("getMe") }

    init {
        Unirest.setDefaultHeader("Content-Type", "application/json")

        //setup update thread
        updateThread.submit {
            var lastUpdate = 0

            while (true) {
                val resp = Unirest.post("$BASEURL/getUpdates")
                    .routeParam("token", token)
                    .body(json {
                        "offset" to lastUpdate++
                    })
                    .asJson().body.`object`.getJSONArray("result")

                if (resp.isNull(0)) continue
                else
                    lastUpdate = (resp.last() as JSONObject).getInt("update_id") + 1

                for (_u in resp) {
                    val update = _u as JSONObject
                    updateHandlers.forEach {
                        it((null
                            ?: update["message"]
                            ?: update["edited_message"]
                            ?: update["inline_query"]
                            ?: update["chosen_inline_result"]
                            ?: update["callback_query"]) as JSONObject
                        )
                    }
                }
            }
        }
    }

    fun sendMessage(chatID: String, text: String) =
        post("sendMessage", json {
            "chat_id" to chatID
            "text" to text
            "parse_mode" to "Markdown"
        })

    val updateHandlers = mutableListOf<(JSONObject) -> Unit>()

    fun registerHandler(handler: (JSONObject) -> Unit) {
        updateHandlers += handler
    }

    fun post(method: String, json: JSONObject? = null): JSONObject {
        val req = Unirest.post("$BASEURL/$method")
            .routeParam("token", token)

        if (json != null) req.body(json)

        val resp = req.asJson().body.`object`

        if (!resp.getBoolean("ok")) {
            throw TelegramException("${resp.getInt("error_code")} | ${resp.getString("description")}", resp)
        }

        return resp.getJSONObject("result")
    }
}