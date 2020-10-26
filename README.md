What was left out?
I left out complete implementations for checklist and attachments (but have skeletons with some getters and setters for them, each with some basic fields).
Because these implementations were not completely refined (not sure how checklist is supposed to function and also not sure what kind of files to support for attachments),
testing was not done on these files, and they are not accounted for in xml storage.
Description is accounted for, however. 

What changed from design to implementation?
The main change is that i created a singleton Users class
to track users. It works quite well; it allows me to easily
store all users to disk for recovery. There are tons of small
changes involving overloaded adders, removers, and more.

Running tests:
There are several test files, which test the core functionality of the core
components. They are all located in src/main/java/Rello.
-- userTest.java
-- listTest.java
-- labelTest.java (label is not complete)
-- cardTest.java (card is not complete)
-- boardTest.java
-- xmlTest.java
Tests should be run using JUnit5.
Select one of the files and click the play button on the 
top bar panel. 

