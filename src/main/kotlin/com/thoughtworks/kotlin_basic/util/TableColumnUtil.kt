package com.thoughtworks.kotlin_basic.util

class TableColumnUtil {
    fun convertToColumnLabels(start: Int, count: Int): List<String> {
        require(start > 0 && count > 0) { "Both start and count must be greater than zero." }

        val result = mutableListOf<String>()
        for (i in start..<start + count) {
            val label = numberToColumnLabel(i)
            if (label.length > 3) {
                throw IllegalArgumentException("Sequence exceeds 'ZZZ'.")
            }
            result.add(label)
        }
        return result
    }

    private fun numberToColumnLabel(number: Int): String {
        if (number <= 0) throw IllegalArgumentException("Number must be greater than zero.")
        var num = number
        val label = StringBuilder()
        while (num > 0) {
            num-- // Adjust for 0-based index
            label.append(('A' + (num % 26)))
            num /= 26
        }
        return label.reverse().toString()
    }
}
