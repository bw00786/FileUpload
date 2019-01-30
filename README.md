# FileUpload

This application uploads multippart files to tghe ACME Shipping company website. 
The application uses mongo db as he backend daabase with RESTful API on the backend and Angular JS on the front.

To run this, just load the project into IntelliJ and run the maven command:

mvn clean install 
that will build an executable springboot jar

Just execute by typing the command:

javva -jar FileUpload.jar

go to http://localhost:8080/upload.html

Future enhancements is to authentiate using Spring Security.
