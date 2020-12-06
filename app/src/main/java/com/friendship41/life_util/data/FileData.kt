package com.friendship41.life_util.data

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val name: String,
    var checkedPreference: Preference,
    var count: Int = 0,
    var history: History? = null
) {
    @Serializable
    data class History(
        var visitDateMilliSec: Long
    )

    @Serializable
    enum class Preference(val kor: String) {
        LOW("낮음"), MIDDLE("중간"), HIGH("높음"), NOT_DEFINE("");

        companion object {
            fun getByKor(kor: String): Preference = when (kor) {
                LOW.kor -> LOW
                MIDDLE.kor -> MIDDLE
                HIGH.kor -> HIGH
                else -> NOT_DEFINE
            }
        }

    }
}

@Serializable
data class PeopleFile(
    val peopleList: List<Person>
)

@Serializable
data class Person(
    val name: String
)
