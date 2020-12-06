package com.friendship41.life_util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.friendship41.life_util.common.*
import com.friendship41.life_util.data.PeopleFile
import com.friendship41.life_util.data.Person
import kotlinx.android.synthetic.main.activity_add_people.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.Exception

const val PEOPLE_FILE_NAME = "people_file"

class AddPeopleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_people)

        tv_add_people_cancel.setOnClickListener {
            finish()
        }

        tv_add_people_ok.setOnClickListener {
            val name = edt_name.text.toString()
            if (name.isEmpty()) {
                log().info("입력된 이름이 없음...")
                Toast.makeText(this.applicationContext, "이름을 입력해야 합니다.", Toast.LENGTH_SHORT).show()
                edt_name.requestFocus()
                return@setOnClickListener
            }

            try {
                this.savePeopleToFile(this.applicationContext, name)
            } catch (e: DuplicatePersonNameException) {
                log().info("이름이 중복...")
                Toast.makeText(this.applicationContext, e.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            finish()
        }
    }

    private fun savePeopleToFile(context: Context, name: String) {
        val fileNameSet = getFileNameSet(context)
        log().info(fileNameSet.toString())
        if (!fileNameSet.contains(PEOPLE_FILE_NAME)) {
            saveFile(context, PEOPLE_FILE_NAME, "{\"peopleList\":[]}")
        }

        val peopleList = getPeopleListFromFile(context, PEOPLE_FILE_NAME)
        log().info(peopleList.toString())
        for (person in peopleList) {
            if (person.name == name) {
                throw DuplicatePersonNameException("중복된 이름입니다.")
            }
        }
        peopleList.add(Person(name))
        saveFile(context, PEOPLE_FILE_NAME, Json.encodeToString(PeopleFile(peopleList)))
    }
}

class DuplicatePersonNameException(message: String): Exception(message)
