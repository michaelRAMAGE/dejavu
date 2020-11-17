Updating is commented out for now.
If the board is updated during testing,
then the xml will need to be refreshed with
initial data set  by running the xmltest file
in src tests. 

All functionality is here with the exception of 
using Description object and Label objects. 

A note in mind is that it appears tests run non-determinstically, 
so on my end, all testing passes, but there are cases where it may
not run ith success on another initialization. With this in mind,
it is not functionality failure but rather node not found exceptions
because robot.clickon is clicking on a button or node expecting it to 
be there because that scene is expected to be in the foreground, however
it might not be in the foreground due to another test doing something.
In non gui tests, we just use before each and set the scene each time,
but I am not sure @Start does this.
