package io.github.riiimc.riiilib.bus.test

import io.github.riiimc.riiilib.bus.annotations.RiiiLibBusSubscribe
import io.github.riiimc.riiilib.bus.annotations.RiiiLibBusSubscriber

@RiiiLibBusSubscriber
object TestListener {
    @RiiiLibBusSubscribe
    fun onTest(event: TestEvent) {
        println(event.message)
    }
}