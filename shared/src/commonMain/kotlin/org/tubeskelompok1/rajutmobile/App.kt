package org.tubeskelompok1.rajutmobile

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.tubeskelompok1.rajutmobile.navigation.RajutNavGraph
import org.tubeskelompok1.rajutmobile.ui.AppColors
import org.koin.compose.koinInject
import org.tubeskelompok1.rajutmobile.data.AppRepository
import org.tubeskelompok1.rajutmobile.screen.OnboardingScreen

@Composable
fun App() {
    val repository = koinInject<AppRepository>()
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = AppColors.Primary,
            onPrimary = AppColors.White,
            background = AppColors.Background,
            surface = AppColors.White,
            onBackground = AppColors.TextPrimary,
            onSurface = AppColors.TextPrimary
        )
    ) {
        Surface(modifier = Modifier, color = AppColors.Background) {
            RajutNavGraph(repository)
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    MaterialTheme { OnboardingScreen(onSelesai = {}) }
}
