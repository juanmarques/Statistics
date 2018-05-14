# N26 - Code Challenge

The main use case for our API is to calculate realtime statistic from the last 60 seconds


## How to Build the Project
```
mvn clean install
```

## How to Run the Project

```
java -jar target/N26-statistics-1.0.1-SNAPSHOT.jar
```

## Tasks

    (1) Creating the transactions

    POST /transactions
    
    Body :
    {
        "amount" : 12.3,
        "timestamp" : 1478192204000
    }
    
    * amount is double value
    * timestamp is a long specifying unix time format in milliseconds
    * Returns : Empty body with 201 response or 204 - if transaction is older than 60 seconds
    
    
    ---------------------------------------------------
    
    (2) Get the transaction statistics
    
    GET /statistics
    
    Body :
    {
        "sum" : 1000,
        "avg" : 100,
        "max" : 200,
        "min" : 10,
        "count" : 10
    }
    
    * sum is double value (sum of transaction)
    * avg is double value (average amount)
    * max is double value (highest transaction value)
    * min is double value (lowest transaction value)
    * count is long value (number of transactions)
    
    You can use Swagger http://localhost:8080/swagger-ui.html#/N26