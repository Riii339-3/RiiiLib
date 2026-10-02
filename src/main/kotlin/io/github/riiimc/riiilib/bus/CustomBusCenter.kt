package io.github.riiimc.riiilib.bus

import io.github.riiimc.riiilib.bus.annotations.RiiiLibBusSubscribe
import io.github.riiimc.riiilib.bus.annotations.RiiiLibBusSubscriber
import net.neoforged.fml.ModList
import net.neoforged.neoforgespi.language.ModFileScanData
import org.objectweb.asm.Type
import java.lang.annotation.ElementType

object CustomBusCenter {
    private val subscriberType =
        Type.getType(RiiiLibBusSubscriber::class.java)

    private val subscribeType =
        Type.getType(RiiiLibBusSubscribe::class.java)

    val subscribers = ModList.get()
        .getAllScanData()
        .asSequence()
        .flatMap { it.annotations.asSequence() }
        .filter { it.annotationType() == subscriberType }
        .filter { it.targetType() == ElementType.TYPE }
        .toList()

    val subscriberClasses =
        subscribers.map { it.clazz().className }.toSet()

    val methods = ModList.get()
        .getAllScanData()
        .asSequence()
        .flatMap { it.annotations.asSequence() }
        .filter { it.annotationType() == subscribeType }
        .filter { it.targetType() == ElementType.METHOD }
        .filter { it.clazz().className in subscriberClasses }
        .toList()

    private data class ListenerEntry(
        val original: Any,
        val handler: (CustomBus) -> Unit
    )

    fun registerSubscribers() {
        for (data in methods) {
            val clazz = Class.forName(data.clazz().className)

            // Kotlin object のインスタンスを取得
            val instance = clazz.getField("INSTANCE").get(null)

            val method = clazz.declaredMethods.firstOrNull {
                it.name == data.memberName() &&
                        it.isAnnotationPresent(RiiiLibBusSubscribe::class.java)
            } ?: continue

            val parameters = method.parameterTypes

            // イベント型の引数が1つだけか検証
            if (parameters.size != 1) continue

            val eventClass = parameters[0]
            if (!CustomBus::class.java.isAssignableFrom(eventClass)) continue

            method.isAccessible = true

            listeners.add(
                ListenerEntry(
                    original = method,
                    handler = { event ->
                        if (eventClass.isInstance(event)) {
                            method.invoke(instance, event)
                        }
                    }
                )
            )
        }
    }

    private val listeners = mutableListOf<ListenerEntry>()

    fun <T : CustomBus> register(
        eventClass: Class<T>,
        listener: (T) -> Unit
    ) {
        listeners.add(
            ListenerEntry(
                original = listener,
                handler = { event ->
                    if (eventClass.isInstance(event)) {
                        listener(eventClass.cast(event))
                    }
                }
            )
        )
    }

    @Deprecated("This method is not used and will be removed in a future version.")
    fun <T : CustomBus> unregister(listener: (T) -> Unit) {
        listeners.removeIf { it.original === listener }
    }

    fun post(event: CustomBus) {
        listeners.toList().forEach { it.handler(event) }
    }
}