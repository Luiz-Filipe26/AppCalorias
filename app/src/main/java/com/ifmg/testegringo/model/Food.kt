package com.ifmg.testegringo.model

import com.ifmg.testegringo.serializers.DateSerializer
import java.io.Serializable
import java.time.LocalDateTime
import java.util.Date

@kotlinx.serialization.Serializable
data class Food(var name:String,
                var calories:Float,
                @kotlinx.serialization.Serializable(with = DateSerializer::class) var date:Date):Serializable {
}