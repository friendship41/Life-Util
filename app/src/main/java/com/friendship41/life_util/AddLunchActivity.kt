package com.friendship41.life_util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.friendship41.life_util.common.getRoomFileMap
import com.friendship41.life_util.common.log
import com.friendship41.life_util.common.saveFile
import com.jayway.jsonpath.JsonPath
import kotlinx.android.synthetic.main.activity_add_lunch.*
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
                saveLunchFile(this.applicationContext, "temp", restaurantName, checkedPreference.text.toString())
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

    private fun saveLunchFile(context: Context, roomName: String, restaurantName: String, checkedPreference: String) {
        var roomFile = getRoomFileMap(context)["room_$roomName"]
        if (roomFile == null) {
            saveFile(context, "room_$roomName", "{}")
            roomFile = File(context.filesDir, "room_$roomName")
        }
        val roomMap = JsonPath.parse(roomFile).json<HashMap<String, Any>>()
        if (roomMap.containsKey(restaurantName)) {
            throw DuplicateRestaurantNameException("[$restaurantName] 식당명이 중복됩니다.")
        }
        roomMap[restaurantName] = mapOf(
            Pair("checkedPreference", checkedPreference),
            Pair("count", 0),
            Pair("history", HashMap<Any, Any>())
        )
        saveFile(context, "room_$roomName", JsonPath.parse(roomMap).jsonString())
        log().info(JsonPath.parse(roomMap).jsonString())
    }
}

class DuplicateRestaurantNameException(message: String): Exception(message)
