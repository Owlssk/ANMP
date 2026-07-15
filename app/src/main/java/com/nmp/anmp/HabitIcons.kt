package com.nmp.anmp

object HabitIcons {

    val names = listOf(
        "Fitness",
        "Study",
        "Water",
        "Meditation",
        "Food",
        "Sleep"
    )

    private val nameToEmoji = mapOf(
        "Fitness" to "\uD83D\uDCAA",
        "Study" to "\uD83D\uDCDA",
        "Water" to "\uD83D\uDCA7",
        "Meditation" to "\uD83E\uDDD8",
        "Food" to "\uD83C\uDF4E",
        "Sleep" to "\uD83D\uDE34"
    )

    fun toEmoji(name: String): String {
        return nameToEmoji[name] ?: "\u2B50"
    }

    fun toName(emoji: String): String {
        for ((name, value) in nameToEmoji) {
            if (value == emoji) return name
        }
        return names[0]
    }
}
