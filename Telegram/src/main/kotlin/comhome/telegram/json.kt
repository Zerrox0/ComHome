package comhome.telegram

import org.json.JSONObject

//json util
fun json(init: JSONBuilder.() -> Unit): JSONObject = JSONBuilder().apply { init() }.obj

class JSONBuilder {
    val obj = JSONObject()
    infix fun String.to(value: Any) = obj.put(this, value)
    infix fun String.to(init: JSONBuilder.() -> Unit) = obj.put(this, json { init() })
}

@Suppress("UNCHECKED_CAST")
operator fun JSONObject.get(key: String): Any? =
    if (isNull(key)) null
    else get(key)