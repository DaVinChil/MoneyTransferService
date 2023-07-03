# **Money Transfer Service**
This is a simple Spring Boot application called Money Transfer Service that allow users to transfer money between accounts. It provides simple API for performing transfers and then confirming them.

## **Getting Started**
To run the Money Transfer Service locally, follow these steps:
1. Clone this repository to your local machine using the following command:
   ```git
   git clone https://github.com/DaVinChil/MoneyTransferService.git
    ```
2. Navigate to the project directory:
   ```git 
   cd MoneyTransferService
   ```
3. Build the application using Gradle:
   ```gradle
   gradle clean build
   ```
4. Run the application:
    ```cmd
    java -jar build/libs/MoneyTransferService-1.0.0.jar
    ```
5. The application will start running on *'http://localhost:5500'*

## **API Documentation**
### **Transfer money**
#### **Request**
- URL: ***'Post /transfer'***
- Body:
  ```json
  {
    "cardFromNumber": "1234123412341234",
    "cardFromValidTill": "1223",
    "cardFromCVV": "123",
    "cardToNumber": "0987098709870987",
    "amount": {
        "value": 123123,
        "currency": "rubbles"
    }
  }
  ```
- Example Response.
  ```json
  {
    "operationId": "0"
  }
  ```
### **Confirm operation**
#### **Request**
In field *'operationId'* use id, you recieved from transfer request.
- URL: ***'Post /confirmOperation'***
- Body:
  ```json
  {
    "operationId": "0",
    "code": "0000"
  }
  ```
- Example Response
  ```json
  {
    "operationId": "0"
  }
  ```

## **Logging**
Each operation is logging into *[logs.log](/logs.log)* file using *[log4j2](https://logging.apache.org/log4j/2.x/)*. Logging format:
```
<yyyy-MM-dd HH:mm:ss> <operation-id> <card-from-number> <card-to-Number> <amount> <commission> <currency> <status>
```

## **Error Handling**
The Money Transfer Service handles the following errors:
- **'400 Bad Request'**: Invalid request or request body.
- **'500 Internal Server Error'**: Server error.