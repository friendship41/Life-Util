package com.friendship41.life_util.service

import com.friendship41.life_util.data.Restaurant
import java.lang.Exception

fun getRandomRestaurant(restaurantMap: Map<String, Restaurant>): Restaurant {
    if (restaurantMap.isEmpty()) {
        throw EmptyRestaurantMapException("등록된 식당이 없습니다.")
    }
    val selectedRestaurantEntryList = filterRandomRestaurantMap(restaurantMap).entries.toList()
        .sortedBy { it.value.count }
    val topRestaurantList = selectedRestaurantEntryList
        .subList(0,
            if (selectedRestaurantEntryList.size <= 5) selectedRestaurantEntryList.size
            else 5)

    return topRestaurantList[(Math.random() * topRestaurantList.size).toInt()].value
}

fun filterRandomRestaurantMap(restaurantMap: Map<String, Restaurant>,
                        preference: Restaurant.Preference = getRandomPreference()): Map<String, Restaurant> = restaurantMap
        .ifEmpty {
            throw EmptyRestaurantMapException("등록된 식당이 없습니다.")
        }
        .filter { it.value.checkedPreference == preference }
        .ifEmpty {
            filterRandomRestaurantMap(restaurantMap)
        }

fun getRandomPreference(): Restaurant.Preference = when ((Math.random()*10).toInt()) {
    0 -> Restaurant.Preference.LOW
    in 1..3 -> Restaurant.Preference.MIDDLE
    in 4..10 -> Restaurant.Preference.HIGH
    else -> Restaurant.Preference.NOT_DEFINE
}

class EmptyRestaurantMapException(message: String): Exception(message)
