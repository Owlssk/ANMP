package com.nmp.anmp

/**
 * Dipakai sebagai variable untuk TWO WAY BINDING di halaman Edit Habit.
 * Semua field String supaya EditText tidak perlu converter dan tidak
 * crash kalau field goal dikosongkan user.
 */
class HabitForm {
    var name: String = ""
    var description: String = ""
    var goal: String = ""
    var unit: String = ""
}
