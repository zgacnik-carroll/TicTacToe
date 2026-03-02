import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicTacToe {
    private static final int DEFAULT_SQUARES = 9;

    private enum Symbol {
        INVALID,
        X,
        O;

        Symbol nextMove() {
            switch (this) {
                case X:
                    return O;

                case O:
                    return X;

                case INVALID:
                default:
                    return INVALID;
            }
        }
    }

    private static class Move {
        final Symbol symbol;
        final int location;

        Move(Symbol symbol, int location) {
            this.symbol = symbol;
            this.location = location;
        }
    }

    public static void main(String[] args) {
        List<Move> moves = null;
        Symbol first = Symbol.X;
        int numSquares = DEFAULT_SQUARES;
        for (int i = 0; i < args.length; i++) {
            switch (i) {
                case 0:
                    moves = loadMoves(args[i]);
                    break;

                case 1:
                    first = parseSymbol(args[i]);
                    break;

                case 2:
                    numSquares = parseInt(args[i]);
                    break;
            }
        }
        if (!validateData(moves, first, numSquares)) {
            System.err.println("Failed");
            System.exit(-1);
        }
        // Teach the AI program using the data
    }

    private static List<Move> loadMoves(String filename) {
        final List<Move> moves = new ArrayList<>();
        final File file = new File(filename);
        if (file.exists()) {
            try (FileReader fileReader = new FileReader(file);
                 BufferedReader br = new BufferedReader(fileReader)) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    final Move m = parseMove(line);
                    if (m != null) {
                        moves.add(m);
                    } else {
                        System.err.println("Error: Invalid move '" + line + "'");
                    }
                }
            } catch (IOException ignored) {}
        } else {
            System.err.println("File '" + filename + "' not found");
        }
        return moves;
    }

    private static int parseInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException ignored) {}
        return 0;
    }

    private static Move parseMove(String line) {
        final String[] fields = line.split("\\s+");
        if (fields.length == 2) {
            return new Move(parseSymbol(fields[0]), parseInt(fields[1]));
        }
        return null;
    }

    private static Symbol parseSymbol(String symbol) {
        // If you give me garbage, I'll give it back
        if (symbol == null) {
            return Symbol.INVALID;
        }

        switch (symbol.toUpperCase().trim()) {
            case "X":
                return Symbol.X;

            case "O":
                return Symbol.O;

            default:
                return Symbol.INVALID;
        }
    }

    private static boolean validateData(List<Move> moves, Symbol first, int numSquares) {
        // Validate the number of squares
        boolean success = true;
        double squareOfNumSquares = Math.sqrt(numSquares);
        double remainder = squareOfNumSquares - (int)squareOfNumSquares;
        if (numSquares < DEFAULT_SQUARES || remainder > 0) {
            System.err.println("Error: Invalid number of squares (" + numSquares + ")");
            success = false;
        }

        // Validate the first move
        if (first == Symbol.INVALID) {
            System.err.println("Error: Invalid start Symbol");
            success = false;
        }

        // Validate the number of Moves. Bail early because all the rest
        // of the moves validation relies on having a non-null moves list.
        if (moves == null) {
            System.err.println("Error: No moves loaded");
            return false;
        }

        // Continue validation on number of moves
        if (moves.size() != numSquares) {
            System.err.println("Error: Number of moves (" + moves.size() + ") does not match number of squares (" + numSquares + ")");
            success = false;
        }

        // Validate the actual Moves
        final Set<Integer> usedLocations = new HashSet<>();
        Symbol nextMove = first;
        for (Move m : moves) {
            if (m.location < 1 || m.location > numSquares) {
                System.err.println("Error: Invalid location '" + m.location + "'");
                success = false;
            }
            if (m.symbol == Symbol.INVALID) {
                System.err.println("Error: At least one of the moves has an invalid Symbol");
                success = false;
            }
            if (usedLocations.contains(m.location)) {
                System.err.println("Error: The same location (" + m.location + ") was used multiple times");
                success = false;
            } else {
                usedLocations.add(m.location);
            }
            if (m.symbol != nextMove) {
                System.err.println("Error: Moves did not alternate");
                success = false;
            }
            nextMove = m.symbol.nextMove();
        }

        return success;
    }
}