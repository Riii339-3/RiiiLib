package io.github.riiimc.riiilib.nativelib

import com.mojang.logging.LogUtils
import io.github.riiimc.riiilib.nativelib.example.NativeExample
import io.github.riiimc.riiilib.storage.StorageAPI
import org.jline.utils.Log
import java.nio.file.Path


object NativeManager {
    val LOGGER = LogUtils.getLogger()

    fun init() {
        val file = StorageAPI.nativeFilePush(
            Path.of("natives"),
            "native"
        )
        if (file == null) {
            LOGGER.error("Failed to push native library")
            return
        }


        load(file)

        NativeLogger.init()
    }

    fun load(path: Path) {
        LOGGER.debug("Loading native library from: {}", path.toAbsolutePath())
        System.load(path.toAbsolutePath().toString())
        LOGGER.info("Loaded native library from: {}", path.toAbsolutePath())
    }
}