# Ticket Reservation
This application lets a user hold and reserve seats for an event venue. Customers can hold the seats for a specified amount of time by providing their intended number of seats and their email. Before the specified time expires, customers can reserve those seats. The system tries to determine the best possible adjacent sets that are available for groups. 

##Prerequisites
* Java SDK 7+
* Maven

## Assumptions:
* Disk based data persistence mechanisms are not used to store and access the data-structures used to store the assets.
* System owner has the option of specifying rows and seats per row upon system start-up. If none is specified, default 10 rows and 10 seats per row are used.
* Default threshold time to hold the seats is set to 1 minute.
* This sample application is not built to handle concurrent users, scalability issues
* As there are no requirements around user input validations, basic validations around customer email, email matching between reserve and hold requests etc are assumed
* 


## Class Diagram
![Class Diagram](https://cloud.githubusercontent.com/assets/24487341/21111784/2cf67c46-c071-11e6-9857-13f2266482da.png)

## Clone/Download git repo locally
* git clone https://github.com/murthy-kakarlamudi/ticketreservation.git

## Steps to build the project
 ..* At the project root level, execute command ./mnvw.cmd clean package. This will create an executable jar 
target\ticketreservation-0.0.1-SNAPSHOT.jar

## Steps to run the project 
You can run the project using either of the below options:
..* Based on the jar from the build in previous step, execute java -jar target\ticketreservation-0.0.1-SNAPSHOT.jar
..* At the project root folder, execute ./mvnw spring-boot:run

## Steps to test the application from Swagger
..* Run the project based on the steps listed in **"Steps to run the project"**
..* Open the link *http://localhost:8080/swagger-ui.html* from a browser
..* Click _List Operations_ for _Ticket Reservation Controller_
..* There are 3 operations for **getAvailableSeats, hold, reserve**

## Steps to test the application from Command Line
..* Run the project based on the steps listed in **"Steps to run the project"**
..* This brings a series of interactive command prompts detailing the next steps around initializing the venue and displaying the Usage menu for SEARCH/HOLD/RESERVE operations.


## See the results

