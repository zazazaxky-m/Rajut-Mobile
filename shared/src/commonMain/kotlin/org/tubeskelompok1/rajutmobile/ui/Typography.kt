package org.tubeskelompok1.rajutmobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.BeVietnamPro_Regular
import org.tubeskelompok1.rajutmobile.generated.resources.BeVietnamPro_SemiBold
import org.tubeskelompok1.rajutmobile.generated.resources.Quicksand_Medium
import org.tubeskelompok1.rajutmobile.generated.resources.Quicksand_Regular

@Composable
fun poppinsFontFamily() = FontFamily(
    Font(Res.font.BeVietnamPro_Regular),
    Font(Res.font.BeVietnamPro_SemiBold),
    Font(Res.font.Quicksand_Regular),
    Font(Res.font.Quicksand_Medium),
)