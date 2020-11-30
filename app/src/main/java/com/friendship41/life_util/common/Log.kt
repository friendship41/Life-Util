package com.friendship41.life_util.common

import java.util.logging.Logger

inline fun <reified T> T.log(): Logger = Logger.getLogger(T::class.java.name)
