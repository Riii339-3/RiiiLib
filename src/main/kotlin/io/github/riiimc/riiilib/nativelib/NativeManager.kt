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
            "native_example"
        ) ?: return


        load(file)

        NativeLogger.init()
    }

    fun load(path: Path) {
        println("NativeManager load: $path")
        System.load(path.toAbsolutePath().toString())
    }
}