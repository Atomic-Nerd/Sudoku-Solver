import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.Arrays;
public class Sudoku {

    Box[][] grid = new Box[9][9];

    public Sudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Box();
            }
        }
    }

    public void display() {
        System.out.println("\n---------------------\n");
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (x == 3 || x == 6) {
                    System.out.print("| ");
                }
                System.out.print(grid[y][x].value + " ");
            }
            System.out.println();
            if (y == 2 || y == 5) {
                System.out.println("---------------------");
            }
        }
        System.out.println("\n---------------------\n");
    }

    public void input() {
        Scanner scanner = new Scanner(System.in);

        String input = "";
        boolean isValid = false;
        while(!isValid){
            isValid = true;
            System.out.print("Would you like to input per line (1) or as a whole grid (2): ");
            input = scanner.nextLine().replaceAll("\\s", "");

            if (!input.matches("[1,2]")){
                isValid = false;
                System.out.print("Invalid Input! 1 or 2.");
            }

        }

        if(input.equals("2")){
            boolean valid = false;
            String[] lines = new String[9];
            while (!valid) {
                System.out.println("Please paste the entire grid followed by one # (9x9): ");
                input = scanner.useDelimiter("#").next().replaceAll("\\s", "");

                if (input.length() == 81) {
                    valid = true;

                    for (int i = 0; i < 9; i++) {
                        lines[i] = input.substring(i * 9, (i * 9) + 9);
                    }

                    for (String line : lines) {
                        if (line.length() != 9) {
                            valid = false;
                            System.out.println("This is not a valid 9x9 grid of characters!");
                            break;
                        }
                    }
                } else {
                    System.out.println("This is not a valid 9x9 grid of characters!");
                }
            }
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    char c = lines[y].toCharArray()[x];
                    if (c != '0') {
                        grid[y][x].value = c;
                    }
                }
            }

            } else {
            for (int y = 0; y < 9; y++) {
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
                    if (c != '0') {
                        grid[y][x].value = c;
                    }
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

    public void copyGrid(Box[][] copyGrid) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Box newBox = new Box();

                newBox.value = copyGrid[y][x].value;
                newBox.possibleValues = Arrays.copyOf(copyGrid[y][x].possibleValues, copyGrid[y][x].possibleValues.length);

                grid[y][x] = newBox;
            }
        }
    }

    public boolean crossSection(char value) {
        Sudoku crossSectionGrid = new Sudoku();
        boolean changes = true;
        boolean anythingChanged = false;

        while (changes) {
            changes = false;
            crossSectionGrid.copyGrid(grid);

            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    if (crossSectionGrid.grid[y][x].value == value) {
                        for (int y2 = 0; y2 < 9; y2++) {
                            if (crossSectionGrid.grid[y2][x].value == '0') {
                                crossSectionGrid.grid[y2][x].value = 'X';
                            }
                        }
                        for (int x2 = 0; x2 < 9; x2++) {
                            if (crossSectionGrid.grid[y][x2].value == '0') {
                                crossSectionGrid.grid[y][x2].value = 'X';
                            }
                        }
                    }
                }
            }
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (subGridContainsOnlyOne0(crossSectionGrid.grid, x, y)) {
                        if (!subGridContainsX(crossSectionGrid.grid, x, y, value)){
                            int[] coordsTuple = findEmptyPositionInSubGrid(crossSectionGrid.grid, x, y);
                            System.out.println("a location is found for the number "+value+" at the box location: X:"+x+" Y:"+y+" the coords are: X:"+coordsTuple[0]+" Y:"+coordsTuple[1]);
                            grid[coordsTuple[1]][coordsTuple[0]].value = value;
                            changes = true;
                            anythingChanged = true;
                        }
                    }
                }
            }
        }
        return anythingChanged;
    }

    public boolean subGridContainsOnlyOne0(Box[][] grid, int subGridX, int subGridY){
        int count = 0;
        for (int y = subGridY*3; y < subGridY*3+3; y++) {
            for (int x = subGridX*3; x < subGridX*3+3; x++) {
                if(grid[y][x].value=='0'){
                    count++;
                }
            }
        }
        if (count==1){
            return true;
        } else{
            return false;
        }
    }
    public boolean subGridContainsX(Box[][] grid, int subGridX, int subGridY, char c) {
        for (int y = subGridY*3; y < subGridY*3+3; y++) {
            for (int x = subGridX*3; x < subGridX*3+3; x++) {
                if(grid[y][x].value==c){
                    return true;
                }
            }
        }
        return false;
    }
    public int[] findEmptyPositionInSubGrid(Box[][] grid, int subGridX, int subGridY) {
        for (int y = subGridY*3; y < subGridY*3+3; y++) {
            for (int x = subGridX*3; x < subGridX*3+3; x++) {
                if(grid[y][x].value=='0'){
                    return new int[] {x,y};
                }
            }
        }
        return null;
    }

    public void runAllCrosssections(){
        boolean changed = true;

        while (changed){
            System.out.println("\nRunning a new crossSection\n");
            changed = false;

            changed |= crossSection('1');
            changed |= crossSection('2');
            changed |= crossSection('3');
            changed |= crossSection('4');
            changed |= crossSection('5');
            changed |= crossSection('6');
            changed |= crossSection('7');
            changed |= crossSection('8');
            changed |= crossSection('9');
        }
    }
}
