# Access-Control-Service 

## Table of Contents
- ### Introduction 
- ### Technologies
- ### Installation
- ### Keycloak Configuration
- ### Launch

## Introduction
This application serves as an access-control module which allows for the management of users, permissions, business functions, profiles and authentication/authorization services. The access-control service makes use of Keycloak to create users, enable authentication and allow for token management. The Eureka server is used for service discovery, load balancing and service registry and a Gateway service is used as an entry/exit point for the network. The application is furthermore designed in such a way which allows it to be easily integrated into other services which require authentication and authorization services.

## Technologies
#### Keycloak: 18.0.0
#### Eureka: 2.0
#### MySql: 8.0.29
#### Docker: 20.10.17
#### Java: 17
#### SpringDoc: 1.6.8

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
4. Open your terminal and run ```docker-compose up``` to download the relevant container images and start the keycloak server.

## Keycloak Configuration
Upon running the docker compose file, you will have access to the keycloak server on ```http://localhost:8180/``` Then go through the following steps;
1. Go to ```http://localhost:8180/```
2. Click on the "administration console" and login using the set credentials (i.e. username = KEYCLOAK_ADMIN, password = KEYCLOAK_ADMIN_PASSWORD) 
3. Create a new Realm on keycloak by clicking on master realm -> add realm. Input a realm name, click on create and switch to the created realm.
4. Go to clients and click on create to add a new client. Input a client name and click on create. Set the "access type" for the client to confidential, add ```http://localhost:8180/*``` to "valid redirect URI's" and save.
5. Click on the newly created client, go to service account roles and under client roles, select realm-management. Assign the roles "manage users", "query users" and "view users" to the client, you will now be able to create users on the keycloak server.
6. Set the following environmental variables;
- CLIENT_ID = "The name of the client in keycloak"
- CLIENT_SECRET = "The password of the client". To see this go to the keycloak server click on clients -> the relevant Client Id -> Credentials -> Secret and copy the key shown. 
- REALM = "The name of the realm"

## Launch
Now that the keycloak configuration is set up, you can proceed to launch the services. 
1. Start the EurekaServerApplication
2. Start the GatewayServiceApplication
3. Start the AccessControlServiceApplication
4. Go to ```http://localhost:8090/swagger-ui/index.html```
5. Click on the post endpoint ```/api/v1/users``` and input the relevant information to create a user. This will return a UUID if successfully executed.
6. Copy the UUID and go to access-control-service/src/main/resources/create_admin_user.sql. Insert the uuid into the relevant field (insert uuid here) and run the script to create an admin user.
7. Go to Postman and execute a POST request to ```http://localhost:8180/realms/devoteam/protocol/openid-connect/token``` to generate an access token. Send the following details with the request; client_id, client_secret, grant_type (password), username, password and scope (openid).
8. Copy the generated access-token and insert it into the authentication box (Authorize) in swagger. All endpoints will now be accessible by the created admin user.
