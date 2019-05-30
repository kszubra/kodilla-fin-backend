# 1. PROJECT DESCRIPTION

Simple backend mechanics for a portal informing about flight offers for the upcoming weekend due to given criteria and sending notification emails.
User provides departure and destination city, maximum total price for the trip and minimum temperature expected at destination city during the weekend.

Allows adding users, notification preferences and simple base for creating reservations and payments, but without any business
implementation. 

# 2. DEMO

REST mechanics are presented via front-end project: https://floating-lake-32865.herokuapp.com/
Scheduled notifications are disabled in demo to avoid spamming emails.

# 3. FRONT-END REPOSITORY

https://github.com/kszubra/kodilla-fin-frontend

# 4. ENDPOINTS

Endpoints are described with Swagger: https://arcane-beach-81614.herokuapp.com/weekendflights/swagger-ui.html

# 5. RUNNING PROJECT LOCALLY

Add your email and password in application.properties of main and tests folder. Due to repository being public, this
data was removed for security reasons.  


# 6. BUGS AND ISSUES  

1. Idea for the project, design and execution of the project was performed within a month and was driven by fulfilling
quantity requirements. As a consequence project is only a demo and contains issues which proper solving would require
re-designing whole project. 

2. Project uses free API's that have very limited amount of requests allowed. Handling too many notification
requests can cause errors.

