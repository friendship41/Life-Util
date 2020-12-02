package com.friendship41.life_util.data

import java.util.*

data class Restaurant(
    val name: String,
    var checkedPreference: Preference,
    var count: Int = 0,
    var history: History? = null
) {
    data class History(
        var visitDate: Date
    )
    enum class Preference(kor: String) {
        LOW("낮음"), MIDDLE("중간"), HIGH("높음"), NOT_DEFINE("")
    }
}
