package org.tubeskelompok1.rajutmobile.model

import org.jetbrains.compose.resources.DrawableResource
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.workshop_beginner
import org.tubeskelompok1.rajutmobile.generated.resources.workshop_group

data class Workshop(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val image: DrawableResource
)

object WorkshopData {
    val items = listOf(
        Workshop(
            id = 1,
            title = "Beginner Crochet Bag Workshop",
            date = "Sabtu, 30 Sept 2023",
            time = "10.00 - Your Bag Ready",
            location = "Bandung Creative Hub",
            image = Res.drawable.workshop_beginner
        ),
        Workshop(
            id = 2,
            title = "Art & Craft Knitting Edition",
            date = "Minggu, 06 Juni 2021",
            time = "13.00 - Selesai",
            location = "Zoom Meeting",
            image = Res.drawable.workshop_group
        )
    )
}
