package pro.midev.juttermap.server

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import pro.midev.juttermap.models.map_objects.JTMap
import java.lang.reflect.Type

class JTMapDeserializer : JsonDeserializer<JTMap?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): JTMap? {
        return if (json != null) JTMap(json) else null
    }
}