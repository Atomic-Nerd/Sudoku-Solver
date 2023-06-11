import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.Arrays;
public class Sudoku {

    Box[][] grid = new Box[9][9];

    public Sudoku(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Box();
            }
        }
    }
    public void display(){
        for(int y=0;y<9;y++) {
            for(int x=0;x<9;x++) {
                if(x==3||x==6){
                    System.out.print("| ");
                }
                System.out.print(grid[y][x].value+" ");
            }
            System.out.println();
            if(y==2||y==5){
                System.out.println("---------------------");
            }
        }
    }
    public void input(){
        Scanner scanner = new Scanner(System.in);
        for (int y=0;y<9;y++){
            String input = "";

            boolean valid = false;

            while (!valid) {
                System.out.print("Input this row, using 0 as blanks, no spaces: ");
                input = scanner.nextLine().replaceAll("\\s", "");

                if (input.length() == 9 && input.matches("[0-9]+") && hasUniqueDigits(input)) {
                    valid = true;
                } else {
                    System.out.println("Invalid input!");
                    System.out.println("Please enter exactly 9 digits (0-9) without any spaces and with unique digits (excluding 0).");
                }
            }

            for (int x = 0; x < 9; x++) {
                char c = input.charAt(x);
                if(c != '0'){
                    grid[y][x].value = c;
                }
            }
        }
        scanner.close();
    }

    private static boolean hasUniqueDigits(String str) {
        Set<Character> digits = new HashSet<>();

        for (char ch : str.toCharArray()) {
            if (ch != '0' && digits.contains(ch)) {
                return false;
            }
            digits.add(ch);
        }

        return true;
    }

    public void copyGrid(Box[][] copyGrid){
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                Box newBox = new Box();

                newBox.value = copyGrid[y][x].value;
                newBox.possibleValues = Arrays.copyOf(copyGrid[y][x].possibleValues, copyGrid[y][x].possibleValues.length);

                grid[y][x] = newBox;
            }
        }
    }

    public void crossSection(char value){
        Sudoku crossSectionGrid = new Sudoku();
        crossSectionGrid.copyGrid(grid);
        boolean changes = false;

        while (!changes){
            changes = true;

            for(int y=0;y<9;y++) {
                for (int x = 0; x < 9; x++) {
                    if(crossSectionGrid.grid[y][x].value==value){

                    }
                }
            }
        }
        crossSectionGrid.display();
    }
}
