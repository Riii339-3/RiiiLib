package io.github.riiimc.riiilib.nativelib

import com.mojang.logging.LogUtils

object NativeLogger {
    external fun init()

    private val logger = LogUtils.getLogger()
    fun error(msg: String) {
        logger.error(msg)
    }
    fun info(msg: String) {
        logger.info(msg)
    }
    fun warn(msg: String) {
        logger.warn(msg)
    }
    fun debug(msg: String) {
        logger.debug(msg)
    }
    fun trace(msg: String) {
        logger.trace(msg)
    }
}