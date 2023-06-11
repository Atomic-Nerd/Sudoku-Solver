public class Main {
    public static void main(String[] args) {

        Sudoku sudoku = new Sudoku();

        sudoku.input();
        sudoku.display();

        System.out.println("");
        sudoku.crossSection('1');
        System.out.println("");
        sudoku.crossSection('7');
    }
}