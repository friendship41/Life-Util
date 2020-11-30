package com.friendship41.life_util.common

import android.content.Context
import java.io.File

fun saveFile(context: Context, filename: String, jsonContent: String) {
    context.openFileOutput(filename, Context.MODE_PRIVATE)
        .use { it.write(jsonContent.toByteArray()) }
}

fun getFile(context: Context, filename: String): File = File(context.filesDir, filename)
