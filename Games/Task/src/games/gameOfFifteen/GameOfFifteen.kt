package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val initialPermutation = initializer.initialPermutation
        board.getAllCells().take(initialPermutation.size).forEachIndexed { index, cell -> board[cell] = initialPermutation[index] }

    }

    override fun canMove() = board.any { it == null }

    override fun hasWon(): Boolean {
        val values = this.board.getAllCells().map { get(it.i, it.j) }
        return values == values.sortedWith(compareBy(nullsLast<Int>(), { it }) )
    }

    override fun processMove(direction: Direction) {
        board.moveValues(direction)
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

    private fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
        return when (direction) {
            Direction.DOWN -> (1..width).toList().map {  moveValuesInRowOrColumn(getColumn(4 downTo 1, it)) }.any { it }
            Direction.UP -> (1..width).toList().map { moveValuesInRowOrColumn(getColumn(1..4, it)) }.any{ it }
            Direction.RIGHT -> (1..width).toList().map { moveValuesInRowOrColumn(getRow(it, 4 downTo 1)) }.any{ it }
            Direction.LEFT -> (1..width).toList().map { moveValuesInRowOrColumn(getRow(it, 1..4)) }.any{ it }
        }
    }

    private fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
        val oldValues = rowOrColumn.map { get(it) }
        val newValues = oldValues
                .move()
        rowOrColumn.forEachIndexed { index, cell ->
            set(cell, if (newValues.size > index) newValues[index] else null)
        }
        return oldValues.filterNotNull().any() && newValues != oldValues
    }

    private fun <T : Any> List<T?>.move(): List<T?> {
        var moved = mutableListOf<T?>()
        for ((index, element) in this.withIndex()) {
            if ( index > 0 && this[index - 1] == null) {
                moved[index - 1] = element
                moved.add(null)
            }
            else {
                moved.add(element)
            }
        }
        return moved
    }
}


