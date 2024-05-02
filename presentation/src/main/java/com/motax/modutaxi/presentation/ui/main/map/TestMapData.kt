package com.motax.modutaxi.presentation.ui.main.map

data class TestMapData(
    val code: Int,
    val currentDateTime: String,
    val message: String,
    val route: Route
)

data class Traoptimal(
    val guide: List<Guide>,
    val path: List<List<Double>>,
    val section: List<Section>,
    val summary: Summary
)

data class Summary(
    val bbox: List<List<Double>>,
    val departureTime: String,
    val distance: Int,
    val duration: Int,
    val etaServiceType: Int,
    val fuelPrice: Int,
    val goal: Goal,
    val start: Start,
    val taxiFare: Int,
    val tollFare: Int
)

data class Start(
    val location: List<Double>
)

data class Section(
    val congestion: Int,
    val distance: Int,
    val name: String,
    val pointCount: Int,
    val pointIndex: Int,
    val speed: Int
)

data class Route(
    val traoptimal: List<Traoptimal>
)

data class Guide(
    val distance: Int,
    val duration: Int,
    val instructions: String,
    val pointIndex: Int,
    val type: Int
)

data class Goal(
    val dir: Int,
    val location: List<Double>
)