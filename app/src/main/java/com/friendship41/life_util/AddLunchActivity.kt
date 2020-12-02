package com.friendship41.life_util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.friendship41.life_util.common.getRoomFileMap
import com.friendship41.life_util.common.log
import com.friendship41.life_util.common.saveFile
import com.friendship41.life_util.data.Restaurant
import kotlinx.android.synthetic.main.activity_add_lunch.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.lang.Exception

class AddLunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lunch)

        tv_add_lunch_ok.setOnClickListener {
            val restaurantName: String = edt_restaurant_name.text.toString()
            if (restaurantName.isEmpty()) {
                log().info("식당명이 없음...")
                Toast.makeText(this.applicationContext, "식당명을 입력해야 합니다.", Toast.LENGTH_SHORT).show()
                edt_restaurant_name.requestFocus()
                return@setOnClickListener
            }
            val checkedPreference = findViewById<RadioButton>(rdg_preference.checkedRadioButtonId)
            log().info("add lunch restaurant name=$restaurantName, checkedPreference=${checkedPreference.text}")

            try {
                saveLunchFile(
                    this.applicationContext,
                    "temp",
                    restaurantName,
                    Restaurant.Preference.getByKor(checkedPreference.text.toString()))
            } catch (e: DuplicateRestaurantNameException) {
                log().info("식당명이 중복...")
                Toast.makeText(this.applicationContext, e.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            finish()
        }

        tv_add_lunch_cancel.setOnClickListener {
            finish()
        }
    }

    private fun saveLunchFile(context: Context, roomName: String, restaurantName: String, checkedPreference: Restaurant.Preference) {
        var roomFile = getRoomFileMap(context)["room_$roomName"]
        if (roomFile == null) {
            saveFile(context, "room_$roomName", "{}")
            roomFile = File(context.filesDir, "room_$roomName")
        }
        val roomMap = Json.decodeFromString<HashMap<String, Restaurant>>(roomFile.bufferedReader().use { it.readText() })
        if (roomMap.containsKey(restaurantName)) {
            throw DuplicateRestaurantNameException("[$restaurantName] 식당명이 중복됩니다.")
        }
        roomMap[restaurantName] = Restaurant(restaurantName, checkedPreference, 0, null)
        saveFile(context, "room_$roomName", Json.encodeToString(roomMap))
        log().info("write to file >> ${Json.encodeToString(roomMap)}")
    }
}

class DuplicateRestaurantNameException(message: String): Exception(message)
