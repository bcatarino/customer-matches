# Customer-Matches

Allows customers to get a list of their licensed matches

## Building

`gradlew build`

## Running the tests

`gradlew test`

## Running the server

`gradlew bootRun`

Server will be started on http://localhost:8080/

### Test Data

Because the service is not connecting to a database, a set of mock data is provided.

You can test the service using

`http://localhost:8080/customer/f411c4e7-1278-4fa3-8ab9-e8c264e12952/matches`

or

`http://localhost:8080/customer/cfa89770-72df-4673-b7d7-abe5aaa3c308/matches`

## Assumptions

- The goal is to model the problem and present a solution, not including the data layer, so I'm not implementing any type of persistence. But for this particular problem, I'd probably choose a relational database.
- A customer can have many licenses, so querying the database for each match is not performant. Will use a form of batch querying, trying to get every match for the customer. 
- Assuming each customer won't have a lot of licenses, so we don't need pagination, and can simply get a list.
- When summaryType is not provided, it assumes AvB as the default.

## Potential Improvements

- Could verify if the customer exists and returns 404 if it doesn't.
- Could return a proper http code when the customer exists but has no license.
- Implement authorization so that only allowed users could access their data.
- Could probably create some sort of inheritance model for content that could be reused, but considering we only have 2 types, it felt over-engineering to do it now.
- Getting the current data is using now(), but this will lead to tests failing occasionally. Should use instead some class that can allow us to mock the current data in tests, to guarantee it works every time.
