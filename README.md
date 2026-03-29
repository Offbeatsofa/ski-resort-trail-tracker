# Ski Resort Trail Tracker

## Personal Project

A program in which you can keep track of trails at different ski resorts. The program will record of each resort and run, storing characteristics like run difficulty and notes. You will be able to look through a resort's catalogue, and (hopefully) the graphical implementation will have a map that you can click to select which run you would like to view. You will also be able to generate a run starting from one trail/lift, with random branches onto other runs. 

It will be used by anyone who wants to record and/or look at ski runs at different resorts. People can use it to plan their day out, with random run generations to keep it fresh and interesting. Of course, online and physical maps do exist, but the benefit of this program is a) that it has run specific details and conditions, and b) that you can record your own notes for each run. I personally love to snowboard, and I would enjoy a trail recording tool so that I could record which trails were my favorites and which ones to avoid.

*Resorts*:
 - Name
 - List of trails
 - List of lifts
 - List of shops/restaurants
 - Areas of resort
 - Geographical region
 - Map image
 - etc.

*Trails*:
 - Name
 - Difficulty
 - Mountain location (could be general region or more specific)
 - Features (Cliffs, trees, moguls etc.)
 - Notes
 - Unridden/ridden
 - Open/Closed
 - Favorite status
 - Trails that branch off of it
 - etc.
 
 ## User Stories

**As a user, I want to be able to :**
 -  Add a trail to a resort.
 -  Look at a list of trails in a given resort.
 -  Add notes to a trail. 
 -  Edit notes that have been added.
 -  Mark trails as open/closed and ridden/unridden.
 -  Have a main menu option to save all trail and resort data.
 -  Have a main menu option to load a previously saved resort/trail configuration.

## Instructions for End User

 - You can view the panel that displays the Trails added to a resort by selecting a file and clicking open
 - You can filter trails that have been added to a resort by clicking the filter button on the trail screen
 - You can generate downhill trails by clicking on the downhill trails button while a trail is selected on the trail screen
 - You can locate my visual component by clicking the map button on the file screen
 - You can save the state of my application by clicking the save button on the trail screen
 - You can load the state of my application by opening a file from the file select screen

## Phase 4: task 2

Sat Mar 28 20:22:17 PDT 2026
Trails obtained

Sat Mar 28 20:22:23 PDT 2026
Filtered trails obtained

Sat Mar 28 20:22:31 PDT 2026
Filtered trails obtained

Sat Mar 28 20:22:37 PDT 2026
Run generated

## Phase 4: task 3

If I had had more time to improve my design, I likely would have refactored my code by redesigning the ResortTracker class to be more like the ResortAppConsole class. I started off by having a class to test out various Swing features, but that ended up turning into my main application, which made it so that many of the helper methods were written in very inconsistent ways. For example, some of them depend on getting a resort from the currently selected file, while some of them use a stored field to access resort data. By doing this refactoring, the project would have decreased coupling, as it wouldn't have to depend on the JsonReader and JsonWriter class as much. 


