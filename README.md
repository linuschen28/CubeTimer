# Cube Timer

## Proposal

For my personal project, I would like to design a speed-cubing timer application. 
Primarily, this application gives a random scramble consisting of a random set of 
moves on each face of the cube. A user would scramble their cube according to the 
given scramble and time their solve either using the built-in timer, or their own
external separate timer. If the built-in timer is used, their times will be 
automatically inputted to the list of times after each solve. If the user times 
themselves with an external timer, they can input their times into the list 
manually. This application will mainly be used by speed-cubers who wish to keep a 
record of their solves. Personally, I chose this idea because I am a speed-cuber 
myself and could use this application whenever I cube.

## User Stories

- As a user, I want to be given a random scramble.
- As a user, I want to be able to add a new time to the list.
- As a user, I want to be able to delete my most recent time from the list.
- As a user, I want to be able to find my last average of 5 solves.
- As a user, I want to be able to time my solves.
- As a user, I want my timed solves to be automatically added to the list.
- As a user, I want to be able to save my time list to file.
- As a user, I want to be able to load my time list from file.

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by entering your time (as a double with 2 
decimals) in the text box and clicking the "Add time" button.
- You can generate the second required event related to adding Xs to a Y by clicking the "Delete last time" button and 
checking the list to ensure that the most recent time was deleted.
- You can locate my visual component as an image of a cube on the right display panel.
- You can save the state of my application by clicking the "Save time list" button.
- You can reload the state of my application by clicking the "Load time list" button.

# Phase 4: Task 2 

Mon Nov 28 19:51:54 PST 2022  
Generated current ao5: N/A  
Mon Nov 28 19:51:54 PST 2022  
Generated new scramble: B D' B2 F L R2 L U2 L' D2 L' F' R2 U2 L2 U' F L' D  
Mon Nov 28 19:52:05 PST 2022  
Added time: 12.23  
Mon Nov 28 19:52:05 PST 2022  
Generated current ao5: N/A  
Mon Nov 28 19:52:09 PST 2022  
Added time: 23.21  
Mon Nov 28 19:52:09 PST 2022  
Generated current ao5: N/A  
Mon Nov 28 19:52:12 PST 2022  
Added time: 11.23  
Mon Nov 28 19:52:12 PST 2022  
Generated current ao5: N/A  
Mon Nov 28 19:52:16 PST 2022  
Added time: 14.12  
Mon Nov 28 19:52:16 PST 2022  
Generated current ao5: N/A  
Mon Nov 28 19:52:20 PST 2022  
Added time: 34.32  
Mon Nov 28 19:52:20 PST 2022  
Generated current ao5: 19.02  
Mon Nov 28 19:52:23 PST 2022  
Deleted most recent time  
Mon Nov 28 19:52:23 PST 2022  
Generated current ao5: N/A  
Mon Nov 28 19:52:25 PST 2022  
Generated new scramble: F2 B L2 D' R B F U2 F U' L2 D' F' D L2 U2 F D F' U'  

# Phase 4: Task 3

- Refactor CubeTimerUI by eliminating its association with SolveTime so that it only uses the association of SolveTime
  found in CubeTimer.
- Refactor accept() method in CubeTimer to reduce duplicated code and be more efficient.
- Refactor CubeTimerUI to only use constants instead of integers for panel/label dimensions.
- Refactor textBox, ao5 label, and time-list in CubeTimerUI to be added to buttonPanel so that refactoring overall 
dimensions can be more efficient.
- Refactor CubeTimerUI to extend an abstract class that has a method to set the appearance of a label/button 
(i.e. setText, setBounds, setFont, etc).
- Refactor the timer mechanism in CubeTimerApp to resemble an actual timer that can be run with a key press.
- Refactor the timer mechanism in CubeTimerApp to only produce times with two decimal places.