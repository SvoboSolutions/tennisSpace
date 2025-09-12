import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.tennis.space.di.appModule
import org.tennis.space.presentation.TennisApp

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        TennisApp()
    }
}