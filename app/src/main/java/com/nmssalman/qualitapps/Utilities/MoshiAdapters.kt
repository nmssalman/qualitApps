package com.nmssalman.qualitapps.Utilities

import androidx.annotation.Nullable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader

class NullToStringAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}

class NullToDoubleAdapter {
    @FromJson
    fun fromJson(@Nullable jsonDouble: Double?): Double {
        return jsonDouble ?: 0.0
    }
}

class NullToIntAdapter {
    @FromJson
    fun fromJson(@Nullable jsonInt: Int?): Int {
        return jsonInt ?: 0
    }
}