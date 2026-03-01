# TicTacToe AI Data Loader

---

## Overview

This is a simple Java program that loads and validates a sequence of TicTacToe moves from a file. The program ensures that the move sequence is valid, alternating properly between X and O, and that all locations are unique and within the board size. It can serve as the starting point for teaching an AI program to play TicTacToe.

---

## Requirements

- Java 21.0.7 
- Apache Ant 1.10.15

---

## Features

- Loads moves from a plain text file.
- Validates moves for:
    - Correct number of squares (default 9).
    - Alternating symbols (X, O).
    - Unique board locations.
    - Valid symbols (X or O).
- Handles optional command-line arguments:
    1. Moves file (required)
    2. First symbol to move (optional, default X)
    3. Number of squares (optional, default 9)

---

## File Format

The moves file should be a plain text file with one move per line in the format:


- `SYMBOL` is `X` or `O` (case-insensitive).
- `LOCATION` is an integer from 1 to the number of squares.

Example: (`moves.txt`)

---

## How to Run

1. Compile and run using the following command:

```bash
ant run clean
```

This will run the program using Ant, which will create a temporary `build`
directory to do its thing, then delete that directory after it has finished running.

You should see output similar to the following:

```bash
init:
    [mkdir] Created dir: C:\Users\zackg\TicTacToe\build\classes

compile:
    [javac] Compiling 1 source file to C:\Users\zackg\TicTacToe\build\classes

run:
     [java] Loaded 9 moves successfully.

clean:
   [delete] Deleting directory C:\Users\zackg\TicTacToe\build

BUILD SUCCESSFUL
Total time: 1 second
```

---

## Closing Remarks

This program provides a simple foundation for loading and validating TicTacToe moves. It’s designed to be easily extended for AI training, simulations, or game analysis. Feel free to modify the code to add features, improve validation, or integrate it with other projects.