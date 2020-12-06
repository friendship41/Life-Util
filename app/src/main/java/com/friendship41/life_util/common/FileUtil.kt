package com.friendship41.life_util.common

import android.content.Context
import com.friendship41.life_util.data.Person
import com.friendship41.life_util.data.Restaurant
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

fun saveFile(context: Context, filename: String, jsonContent: String) = context
    .openFileOutput(filename, Context.MODE_PRIVATE)
    .use { it.write(jsonContent.toByteArray()) }

fun getFile(context: Context, filename: String): File = File(context.filesDir, filename)

fun getFileNameList(context: Context): Array<String> = context.fileList()

fun getFileNameSet(context: Context): Set<String> = getFileNameList(context).toSet()

fun getRoomFileMap(context: Context): Map<String, File> = getFileNameList(context)
    .filter { it.contains("room") }
    .map { it to File(context.filesDir, it) }
    .toMap()

fun getRestaurantMapFromFile(context: Context, roomName: String): HashMap<String, Restaurant> = Json.decodeFromString(
    getFile(context.applicationContext, roomName)
        .bufferedReader()
        .use { it.readText() })

fun getPeopleListFromFile(context: Context, peopleFileName: String): ArrayList<Person> = (Json.decodeFromString(
    getFile(context.applicationContext, peopleFileName)
        .bufferedReader()
        .use { it.readText() }) as HashMap<String, ArrayList<Person>>)["peopleList"] ?: throw FileNotFoundException()
