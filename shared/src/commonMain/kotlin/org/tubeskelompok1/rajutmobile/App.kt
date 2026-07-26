package org.tubeskelompok1.rajutmobile

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.tubeskelompok1.rajutmobile.navigation.RajutNavGraph

@Composable
fun App() {
    MaterialTheme {
        Surface(modifier = Modifier) {
            RajutNavGraph()
        }
    }
}