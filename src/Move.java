
public class Move<E> {
 int move;
 int row;
 int col;

 /***
  * Hold a move
  * @param row
  * @param col
  * @param move - value of the cell
  */
	public Move(int row, int col, int move) {
		this.move = move;
		this.row = row;
		this.col = col;
	}

}
