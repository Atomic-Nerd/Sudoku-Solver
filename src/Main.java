public class Main {
    public static void main(String[] args) {

        Sudoku sudoku = new Sudoku();

        sudoku.input();
        sudoku.display();

        sudoku.crossSection('1');
        sudoku.crossSection('2');
        sudoku.crossSection('3');
        sudoku.crossSection('4');
        sudoku.crossSection('5');
        sudoku.crossSection('6');
        sudoku.crossSection('7');
        sudoku.crossSection('8');
        sudoku.crossSection('9');

        sudoku.display();
    }
}