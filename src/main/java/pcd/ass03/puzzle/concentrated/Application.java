package pcd.ass03.puzzle.concentrated;

public class
Application {

	public static void main(final String[] args) {
		final int n = 3;
		final int m = 5;
		
		final String imagePath = "src/main/java/pcd/ass03/puzzle/concentrated/bletchley-park-mansion.jpg";
		
		final PuzzleBoard puzzle = new PuzzleBoard(n, m, imagePath);
        puzzle.setVisible(true);
	}
}
