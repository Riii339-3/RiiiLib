package io.github.riiimc.riiilib.storage

import io.github.riiimc.riiilib.RiiiLib
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

object StorageAPI {
    private val riiilibDir =  Files.createDirectories(Path.of(".${RiiiLib.MODID}"))

    fun filePush(path: String): Path? {
        val caller = Throwable().stackTrace
            .drop(1)
            .firstOrNull {
                it.className != StorageAPI::class.java.name
            } ?: return null
        val className = caller.className
        val packageName = className.substringBeforeLast(".")

        val callerClass = Class.forName(className)
        val callerLoader = callerClass.classLoader

        val resource = callerLoader.getResourceAsStream(path)
            ?: return null

        var output: Path? = null
        resource.use {
            val outputDir = riiilibDir.resolve(packageName)
            output = outputDir.resolve(path)
            Files.createDirectories(output.parent)
            Files.copy(
                it,
                output,
                StandardCopyOption.REPLACE_EXISTING
            )
        }

        return output
    }

    fun nativeFilePush(path: Path, fileName: String): Path? {
        val os = System.getProperty("os.name").lowercase()
        val arch = System.getProperty("os.arch").lowercase()

        val (platform, extension) = when {
            os.contains("windows") && arch in setOf("amd64", "x86_64") ->
                "windows_64" to ".dll"

            os.contains("linux") && arch in setOf("amd64", "x86_64") ->
                "linux_64" to ".so"

            os.contains("mac") && arch in setOf("amd64", "x86_64") ->
                "macos_64" to ".dylib"

            os.contains("mac") && arch in setOf("aarch64", "arm64") ->
                "macos_arm64" to ".dylib"

            os.contains("linux") && arch in setOf("aarch64", "arm64") ->
                "linux_arm64" to ".so"

            else ->
                return null
        }

        val nativeFileName = if (os.contains("windows")) {
            "$fileName$extension"
        } else {
            "lib$fileName$extension"
        }

        val resourcePath = path
            .resolve(platform)
            .resolve(nativeFileName)

        return filePush(resourcePath.toString().replace('\\', '/'))
    }

    fun getOutputPath(clazz: Class<*>): Path {
        val packageName = clazz.packageName
        return riiilibDir.resolve(packageName)
    }

    fun getOutputPath(className: String): Path {
        val packageName = className.substringBeforeLast(".")
        return riiilibDir.resolve(packageName)
    }

    fun getFilePath(className: String, fileName: String): Path {
        val packageName = className.substringBeforeLast(".")
        return riiilibDir.resolve(packageName).resolve(fileName)
    }

    fun getFilePath(clazz: Class<*>, fileName: String): Path {
        return getOutputPath(clazz).resolve(fileName)
    }

    fun loadFile(filePath: Path) {

    }

    fun debug() {
        println("StorageAPI debug");
        println("Caller: ${Throwable().stackTrace[1]}")
        println("Caller Class: ${Throwable().stackTrace[1].className}")
        val clazz = Class.forName(Throwable().stackTrace[1].className)
        println("Caller Class: $clazz")
        val classLoader = clazz.classLoader
        println("Caller Class Loader: $classLoader")
        val thisLoader = this.javaClass.classLoader
        println("This Class Loader: $thisLoader")
        println("Caller Class Loader == This Class Loader: ${classLoader == thisLoader}")
    }
}