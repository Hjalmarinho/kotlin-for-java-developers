
import board.Cell
import board.Direction
import board.GameBoard

class GameBoardImpl<T>(override val width: Int) : GameBoard<T> {

    private val cellToValue = mutableMapOf<Cell, T?>()

    init {
        getAllCells().forEach{ cellToValue[it] = null}
    }


    override fun getCellOrNull(i: Int, j: Int): Cell? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCell(i: Int, j: Int): Cell {
        return cellToValue.keys.find { it.i === i && it.j === j } ?: createCell(i, j)
    }

    private fun createCell(i: Int, j: Int): Cell {
        return Cell(i, j)
    }

    override fun getAllCells(): Collection<Cell> {
        val cells = mutableListOf<Cell>()
        for (i in 1..width)
            for (j in 1..width)
                cells.add(createCell(i, j))
        return cells    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(cell: Cell): T? {
        return cellToValue[cell]
    }

    override fun set(cell: Cell, value: T?) {
        cellToValue[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellToValue.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellToValue.filterValues(predicate).keys.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellToValue.values.any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellToValue.filterValues(predicate).count() == width * width
    }
}
