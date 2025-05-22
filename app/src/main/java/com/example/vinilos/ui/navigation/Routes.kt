package com.example.vinilos.ui.navigation

enum class Screen {
    HOME,
    ALBUM_DETAIL,
    COLLECTOR_DETAIL,
    PERFORMER_DETAIL
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object AlbumDetail : NavigationItem(Screen.ALBUM_DETAIL.name)
    object CollectorDetail : NavigationItem(Screen.COLLECTOR_DETAIL.name)
    object PerformerDetail : NavigationItem(Screen.PERFORMER_DETAIL.name)
}