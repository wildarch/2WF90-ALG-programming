package group14

/**
 * Table with row and column headers that can be pretty printed to the console.
 * @author Daan de Graaf
 */
open class Table<T> {
    /**
     * Headers of each column
     */
    var columnHeaders: MutableList<String> = ArrayList(mutableListOf(""))

    /**
     * Headers of each row
     */
    var rowHeaders: MutableList<String> = ArrayList()

    /**
     * Rows of the table. Each row should have equal length
     */
    var rows: MutableList<MutableList<T>> = ArrayList()

    /**
     * Adds a column header
     */
    fun addColumnHeader(title: String) {
        columnHeaders.add(title)
    }

    /**
     * Adds all column headers in the given sequence
     */
    fun addColumnHeaders(vararg titles: String) {
        titles.forEach { addColumnHeader(it) }
    }

    /**
     * Adds a row header
     */
    fun addRowHeader(title: String) {
        rowHeaders.add(title)
    }

    /**
     * Adds all row headers in the given sequence
     */
    fun addRowHeaders(vararg titles: String) {
        titles.forEach { addRowHeader(it) }
    }

    /**
     * Adds a row to the table with the given contents
     */
    fun addRow(elements: Iterable<T>) {
        rows.add(elements.toMutableList())
    }

    /**
     * Adds a row to the table with the given contents
     */
    fun addRow(vararg elements: T) {
        addRow(elements.asList())
    }

    private fun columnWidth(c: Int): Int {
        if (columnHeaders.size < c) {
            throw IndexOutOfBoundsException("Requested header of column $c, but table has only ${columnHeaders.size} columnHeaders")
        }
        if (c == -1) {
            return rowHeaders.map { it.length }.max() ?: 0
        }
        return rows.fold(columnHeaders[c+1].length) {
            w, row -> Math.max(w, row[c].toString().length)
        }
    }

    /**
     * Format the table as a multi-line String.
     * @param columnPadding the number of characters of padding to add between each column
     * @param elemFormatter the function to map each element to a string, uses `toString` by default
     * @return Table with contents as a human-readable String
     */
    protected fun format(columnPadding: Int, elemFormatter: (e: T) -> String = { it.toString() }) : String {
        check(rows.all { it.size == columnHeaders.size - 1 },
                {"All rows (including the header) must have an equal number of columns"})
        check(rows.size == rowHeaders.size,
                { "There are ${rows.size} rows, but ${rowHeaders.size} row headers" })

        val headers = formatHeaders(columnPadding)
        val rows = rows.zip(rowHeaders).map {(row, header) ->
            formatRow(row, header, columnPadding, elemFormatter)
        }.toList()
        val width = Math.max(headers.length, rows.map { it.length }.max() ?: 0)
        return listOf(
                headers,
                "–".repeat(width),
                rows.joinToString(System.lineSeparator())
        ).joinToString(System.lineSeparator())
    }

    private fun formatHeaders(columnPadding: Int) =
            columnHeaders.mapIndexed {
                index, s -> s.padEnd(columnWidth(index-1)) + ( if (index == 0) " |" else "")
            }.joinToString(" ".repeat(columnPadding))

    private fun formatRow(row: MutableList<T>, header: String, columnPadding: Int,
                          elemFormatter: (e: T) -> String): String {
        val l = mutableListOf(header.padEnd(columnWidth(-1)) + " |")
        l.addAll(
            row.mapIndexed { index, t ->
                elemFormatter(t).padEnd(columnWidth(index))
            }
        )
        return l.joinToString(" ".repeat(columnPadding))
    }

    /**
     * Format the table with column padding 1 character
     */
    override fun toString() = format(1)

    /**
     * Get the value of a specific cell
     */
    operator fun get(r: Int, c: Int) : T {
        if (rows.size <= r) {
            throw IndexOutOfBoundsException("Requested row $r, but table has only ${rows.size} rows")
        }
        val row = rows[r]
        if (row.size <= c) {
            throw IndexOutOfBoundsException("Requested column $r, but row has only ${row.size} columns")
        }
        return row[c]
    }
}