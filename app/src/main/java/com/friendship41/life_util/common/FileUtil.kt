package com.friendship41.life_util.common

import android.content.Context
import java.io.File

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
