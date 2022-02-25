package com.android.movieapp.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import coil.compose.LocalImageLoader
import com.android.movieapp.App

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MovieAppTheme(content: @Composable() () -> Unit) {
    val colors = LightColorPalette
    CompositionLocalProvider(LocalContentColor provides Color.White) {
        CompositionLocalProvider(LocalImageLoader provides App.imageLoader) {
            CompositionLocalProvider(LocalContentAlpha provides 1f) {
                MaterialTheme(
                    colors = colors,
                    typography = Typography,
                    shapes = Shapes,
                    content = content
                )
            }
        }
    }
}