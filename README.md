# B2B Platform 
B2B business operations management platform that provides business workflow automation, Onboarding, Order management, 
Inventory management services from businesses to businesses. From this understanding from the problem statement, 
the problem to solution space description will be described as a business Ops Manager.

## Problem Statement
Duplo is planning to work with businesses by registering them and creating their accounts on
the Duplo platform.In turn, a business owner whose business is registered on the Duplo
platform, can go ahead and create user accounts for their businesses’ department heads(for
example soft drinks department, toiletries department, etc), who are the actual active users of
the platform.

In each business, the department heads are responsible for monitoring the status of the
inventory, and are required by the business owner to make orders using the Duplo platform
each time before the inventory in their department runs out.

**Note that Duplo does not plan to sell the actual items, but is just a platform that is used to place
these orders

On a typical day from its many businesses, the Duplo platform sometimes gets up to 1,000
requests per minute from different department heads of multiple businesses, all making
orders through the platform. Duplo stores this order data in a Postgres database.

However, in its future product plan, Duplo plans to start offering a buy now pay later service
to its registered businesses, where businesses will be able to make orders for inventory, and can
pay for it later.

To achieve this, the Duplo platform has started logging transaction data into MongoDB.
Duplo logs the following data of a transaction/order: businessID, amount, date, status
The platform intends to later use this data to calculate the credit score of a business, to
determine how much credit a business can access, using the following formulae:

Total transactions or order amount / ( Number of transactions * 100)

Also, the government tax authority has introduced a new law, which requires the Duplo
platform to make POST requests to an API it has provided for each order the Duplo platform
receives, so that it can log this data for tax purposes.The government tax authority STRICTLY
dictates that the details of each order must be passed to its API.

However, the API can be slow sometimes, and as such responses can take up to 15-35 seconds
to be returned by the government API.

The API URL is: https://taxes.free.beeceptor.com/log-tax

Sample expected JSON request payload is:
{
"order_id": "dac3549d-aea2-4957-91dc-618f2e2c77f7",
"platform_code": "022",
"order_amount": 40000
}


## Functional Requirement
1. Curate orders from each businesses’ department lead
2. Process and store the order details as described above
3. Provide an API endpoint for a business to get their credit score
4. Provide an API endpoint for a business to fetch the order details (total number of orders, total amount of orders, total number of orders today, total amount of orders today)
5. Create a React App to display the orders of each business in a table. When one record is clicked a modal should pop up to show the order details
6. Deploy the backend solution as a docker image
7. Bonus points if you can setup a simple CI/CD pipeline to deploy the working frontend app on netlify and provide a preview link
8. Commit your code to github and add jask-vmi and kamaukelvin as collaborators


## System Requirements
* Java 20
* Mysql 8
* Maven
* Springboot 3
* Spring Web MVC
* Spring Data JPA
* Docker
* Docker Compose

## Getting Started with Deployment
Application is a monolith maven project with no internal sub modules. It inherits from springboot-parent 

### Database Setup
Hibernate ddl.auto flag is set to update, therefore, b2bdb database is to be created. Run below code
from any postgres client terminal
```properties
CREATE DATABASE b2bdb;;
```

#### Run locally
Start server using
```bash
./mvnw spring-boot:run
```

To view specs, visit http://localhost:8585/b2b/swagger-ui/index.html#/

#### Run locally via docker compose
##### Requirement: Ensure your Docker-daemon is up and running. Docker Desktop.

##### Run Docker login
```bash
    docker login -u <username> docker
```

##### Spawn up the containers
```bash
    TAG=1.0.0 docker-compose up
```

Where tag is the docker tag you wish to run locally, you can build and push your local branch using
```bash
bash .mvn/publish.sh1
```

You can build and publish and run locally using
```bash
bash run.sh
```

#### Run tests
```bash
./mvnw clean test
```

#### Build and publish image locally
```bash
bash .mvn/publish.sh1
```
