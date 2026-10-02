package io.github.riiimc.riiilib.bus

data class CustomBusData(
    val func: (CustomBus) -> Unit,
    val bus: CustomBus
)
