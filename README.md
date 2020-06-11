# Operating Systems Assignment 2: Synchronization
# Social Distance Shop Simulation
Using synchronization to enforce lockdown level 3 in South Africa

This application is designed to run using the command line terminal on a UNIX operating system. 

## Compilation Procedure:
Run the keyword "make" from your terminal in the directory containing the 
program makefile as well as bin and src folders:

	- This execution compiles all files with the aid of makefile in the 
	  program and redirects them in the bin folder and needs to be 
	  executed before the program can run. 

An example of how to compile the program on command line:
```
        --> make
```

## How to run the program:
On command line, Run: 'make run n="arg0" l="arg1" b="arg2" m="arg3"' 

Where the arguments: 
	
	• arg0 represents the number of customers who will arrive 
	• arg1 represents the length of the shop in grid squares
	• arg2 represents the breadth of the shop in grid squares
	• arg3 represents the max number of people allowed into the shop at any point
	
An example of how to run the program:
```
        --> make run n="50" l="10" b="10" m="15"
```

## Explanation of program
This program is a multithreaded Java simulation of customers entering a shop under Covid19 pandemic level three regulations, using synchronization mechanisms to ensure that it operates within the specified synchronization constraints.

## Other commands
use "make clean" to clean all the class files from the bin directory.
```
	--> make clean
```

## Author
> Zukiswa Lobola (LBLZUK002)
