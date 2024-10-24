# Basic Command Line Interpreter (CLI)

## Overview

This project implements a basic Command Line Interpreter (CLI) that mimics the functionality of a Unix/Linux shell. The CLI supports a variety of system commands as well as internal commands to enhance user interaction.

## Features

### Command Execution

The CLI can execute the following system commands:

- `pwd`: Print the current working directory.
- `cd <directory>`: Change the current directory to `<directory>`.
- `ls`: List files in the current directory.
- `ls -a`: List all files, including hidden files.
- `ls -r`: List files in reverse order.
- `mkdir <directory>`: Create a new directory.
- `rmdir <directory>`: Remove an empty directory.
- `touch <file>`: Create a new file or update the timestamp of an existing file.
- `mv <source> <destination>`: Move or rename files or directories.
- `rm <file>`: Remove a file.
- `cat <file>`: Display the contents of a file.
- Output redirection:
    - `>`: Redirect output to a file, overwriting if it exists.
    - `>>`: Redirect output to a file, appending to it.
- `|`: Pipe output from one command to another.

### Internal Commands

The CLI includes the following internal commands:

- `exit`: Terminate the CLI.
- `help`: Display a list of available commands and their usage.
