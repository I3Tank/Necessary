package com.kevus.necessary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.kevus.necessary.viemodels.DataStoreViewModel
val DarkColorPalette = darkColors(
    background = Black,
    surface = DavysGrey,
    primary = OxfordBlue,
    secondary = Fulvous,
    onPrimary = Fulvous,
    onSecondary = DavysGrey,
    onBackground = Snow,
    onSurface = Snow,
)

val LightColorPalette = lightColors(
    background = White,
    surface = KeyLime,
    primary = MayGreen,
    secondary = YellowGreen,
    onPrimary = White,
    onSecondary = KeyLime,
    onBackground = Black,
    onSurface = Black,
)

val RedColorPalette = lightColors(
    background = Timberwolf,
    surface = RosyBrown,
    primary = DarkRed,
    secondary = LightRed,
    onPrimary = Timberwolf,
    onSecondary = RosyBrown,
    onBackground = Black,
    onSurface = Black,
)

val TerraCottaColorPalette = lightColors(
    background = White,
    surface = TerraCotta,
    primary = DarkCyan,
    secondary = Jet,
    onPrimary = White,
    onSecondary = TerraCotta,
    onBackground = Black,
    onSurface = Black,
)

@Composable
fun NecessaryTheme(darkTheme: Boolean = isSystemInDarkTheme(), dataStoreViewModel: DataStoreViewModel, content: @Composable () -> Unit) {
    val activeTheme = dataStoreViewModel.activeTheme.observeAsState().value

    val colors = if (darkTheme) {
        when (activeTheme) {
            "Dark" -> DarkColorPalette
            "Light" -> LightColorPalette
            "Red" -> RedColorPalette
            "Terra Cotta" -> TerraCottaColorPalette
            else -> LightColorPalette
        }
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
/* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
