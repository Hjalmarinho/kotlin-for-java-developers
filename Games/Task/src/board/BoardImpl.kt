package board

import GameBoardImpl


fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)


class SquareBoardImpl(override val width: Int) : SquareBoard {
    private val cellCache = mutableListOf<Cell>()

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (i <= width && j <= width) getCell(i, j) else null

    }

    override fun getCell(i: Int, j: Int): Cell {
        return cellCache.find { it.i === i && it.j === j } ?: createCell(i, j)
    }

    override fun getAllCells(): Collection<Cell> {
        val cells = mutableListOf<Cell>()
        for (i in 1..width)
            for (j in 1..width)
                cells.add(createCell(i, j))
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.takeWhile { it <= width }.map { getCell(i, it) }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.takeWhile { it <= width }.map { getCell(it, j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val neighbour = when(direction) {
            Direction.UP -> getCell(i-1, j)
            Direction.DOWN -> getCell(i+1, j)
            Direction.RIGHT -> getCell(i, j+1)
            Direction.LEFT -> getCell(i, j-1)
        }
        return if (neighbour.i in 1..width && neighbour.j in 1..width) neighbour else null
    }

    private fun createCell(i: Int, j: Int): Cell {
        val cell = Cell(i, j)
        cellCache.add(cell)
        return cell
    }
}
