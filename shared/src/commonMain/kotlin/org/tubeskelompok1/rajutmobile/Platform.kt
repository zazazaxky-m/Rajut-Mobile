package org.tubeskelompok1.rajutmobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform