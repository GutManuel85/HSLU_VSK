/**
 * Interface to Game of Life Grid. The CellGridCanvas can deal with any grid,
 * not only the Game of Life. Copyright 1996-2004 Edwin Martin
 * <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 */
package org.bitstorm.gameoflife;

import java.awt.Dimension;
import java.util.Enumeration;

/**
 * Interface between GameOfLifeCanvas and GameOfLife. This way GameOfLifeCanvas
 * is generic, independent of GameOfLife. It contains generic methods to operate
 * on a cell grid.
 *
 * @author Edwin Martin
 */
public interface CellGrid {

    /**
     * Get status of cell (alive or dead).
     *
     * @param col x-position
     * @param row y-position
     * @return living or not
     */
    boolean getCell(int col, int row);

    /**
     * Set status of cell (alive or dead).
     *
     * @param col x-position
     * @param row y-position
     * @param cell living or not
     */
    void setCell(int col, int row, boolean cell);

    /**
     * Get dimension of cellgrid.
     *
     * @return dimension
     */
    Dimension getDimension();

    /**
     * Resize the cell grid.
     *
     * @param col new number of columns.
     * @param row new number of rows.
     */
    void resize(int col, int row);

    /**
     * Get cell-enumerator. Enumerates over all living cells (type Cell).
     *
     * @return Enumerator over Cell.
     * @see Cell
     */
    Enumeration getEnum();

    /**
     * Clears grid.
     */
    void clear();
}
