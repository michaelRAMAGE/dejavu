Updating is commented out for now.
If the board is updated during testing,
then the xml will need to be refreshed with
initial data set  by running the xmltest file
in src tests. 

All functionality is here with the exception of 
using Description object and Label objects. 
These are not supported.
Also, updates are local to the session so logging out
will not save general changes. This is because I am not
sure when we actually are supposed to save to disk.

A note in mind is that it appears tests run non-determinstically, 
so on my end, all testing passes, but there are cases where it may
not run ith success on another initialization. With this in mind,
it is not functionality failure but rather node not found exceptions
because robot.clickon is clicking on a button or node expecting it to 
be there because that scene is expected to be in the foreground, however
it might not be in the foreground due to another test doing something.
In non gui tests, we just use before each and set the scene each time,
but I am not sure @Start does this. 

On boardview test, remove board works but sometimes the robot mixes
up where to click because there are two windows. i tried specifying
the window and it worked...but still only sometimes.

On a last note, custom connection was not tested. Our client does construct rmi ooup for custom but 
this has not been tested for obvious reasons. 

All implemented functionality is also tested, with the exception of what has been mentioned and perhaps some other very small thing that never made it back on my mind. 


--------------------

In terms of correction for sprint 2, 
my server testing did already test updates and such.
It shows that a change on client side can be reflected on server side
and that the client will be given a new self.


I did add more testing to create board test in server testing. Instead of just checking if new board
on server, I checked if the board had the right owner.