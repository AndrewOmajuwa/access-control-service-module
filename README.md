# Access-Control-Service 

## Table of Contents
### Introduction 
### Technologies
### Set-up

## Introduction
This application serves as an access-control module which allows for the management of users, permissions, business functions, profiles and authentication/authorization services. The access-control service makes use of Keycloak to create users, enable authentication and allow for token management. The Eureka server is used for service discovery, load balancing and service registry and a Gateway service is used as an entry/exit point for the network. The application is furthermore designed in such a way which allows it to be easily integrated into other services which require authentication and authorization services.

## Technologies
#### Keycloak - 18.0.0
#### Eureka - 2.0
#### MySql - 8.0.29
#### Docker - 20.10.17
#### Java - 17
#### SpringDoc - 1.6.8

## Installation
To run this project, Follow these steps:

1. Clone the repository. ```git clone https://github.com/williamsuane/project-valhalla-andrew.git```
2. Install the aforementioned Docker version on your device.
3. Set the environmental variables namely;
- MYSQL_DATABASE: "Any name you prefer to call your database"
- MYSQL_USER: "A username for the user of MySql database"
- MYSQL_PASSWORD: "A password for the user of MySql database"
- MYSQL_ROOT_PASSWORD: "Any root password you would prefer for the super root user of MySql database"
- KEYCLOAK_ADMIN: "A name for your keycloak admin account"
- KEYCLOAK_ADMIN_PASSWORD: "a password for your keycloak admin account"
- KC_DB_URL_DATABASE: "Should match your input for MYSQL_DATABASE"
- KC_DB_USERNAME: "Should match your input for MYSQL_USER"
- KC_DB_PASSWORD: "Should match your input for MYSQL_PASSWORD"
4. Open your terminal and run ```docker-compose up``` to download the relevant containers and activate the keycloak server.

## Keycloak Configuration
Upon running the docker compose file, you will have access to the keycloak server on ```http://localhost:8180/``` Then go through the following steps;
1. Go to the ```http://localhost:8180/```
2. Click on the "administration console" and login using the set credentials (i.e. KEYCLOAK_ADMIN, KEYCLOAK_ADMIN_PASSWORD) 
3. Create a new Realm on keycloak by clicking on master realm -> add realm. Input a realm name and click on create.
4. 
