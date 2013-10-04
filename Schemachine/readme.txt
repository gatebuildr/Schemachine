Authors: Matt Spain

Schemachine is a command line interpreter that parses user input and creates a
model of a world containing physical objects, which may have positional
relationships with each other.  It can also answer questions about the state
of the world according to its model.

Execution:
The main program is interpreter.Interpreter.java.  Run it from a command line
and type statements accepted by the CFG below.  A name can be any string as
long as it does not contain any of the keywords listed below.  Whitespace in a
name will be converted into single spaces.

Acceptance test checklist:

[x] "The book." : Creates an object, "the book".
[x] "the book?" : States whether an object called "the book" exists.
[] "The book is in the bookcase" : Creates "the book" and "the bookcase", if
                  either does not exist. The book is contained within the book-
                  case. The bookcase is a container, if it was not previously.


List of reserved keywords:
"." (PERIOD)
"?" (QUERY)
"period"
"query"
"name" (using this will result in weird behavior, probably)

Grammar:

S            -> DECLARATION
DECLARATION  -> NAME PERIOD    | Creates an object with named NAME
PRESENTQUERY -> NAME QUERY     | Answers the question "Does NAME exist?"