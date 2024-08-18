package com.thoughtworks.kotlin_basic.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TableColumnUtilTest {
    private val util = TableColumnUtil()

    @Test
    fun `test single label`() {
        assertEquals(listOf("A"), util.convertToColumnLabels(1, 1))
    }

    @Test
    fun `test multiple labels`() {
        assertEquals(listOf("A", "B"), util.convertToColumnLabels(1, 2))
        assertEquals(listOf("Z", "AA", "AB"), util.convertToColumnLabels(26, 3))
    }

    @Test
    fun `test beyond ZZZ throws error`() {
        val exception = assertThrows<IllegalArgumentException> {
            util.convertToColumnLabels(18279, 1) // 18279 is beyond "ZZZ"
        }

        assertEquals("Sequence exceeds 'ZZZ'.", exception.message)
    }

    @Test
    fun `test invalid parameters`() {
        assertThrows<IllegalArgumentException> { util.convertToColumnLabels(0, 1) }
        assertThrows<IllegalArgumentException> { util.convertToColumnLabels(1, 0) }
    }
}
