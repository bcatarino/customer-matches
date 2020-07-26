# Customer-Matches

Allows customers to get a list of their licensed matches

## Building

`gradlew build`

## Running the tests

`gradlew test`

## Running the server

`gradlew bootRun`

Server will be started on http://localhost:8080/

## Assumptions

- The goal is to model the problem and present a solution, not including the data layer, so I'm not implementing any type of persistence. But for this particular problem, I'd probably choose a relational database.
- A customer can have many licenses, so querying the database for each match is not performant. Will use a form of batch querying, trying to get every match for the customer. 
- Assuming each customer won't have a lot of licenses, so we don't need pagination, and can simply get a list.

## Potential Improvements

- Could verify if the customer exists and returns 404 if it doesn't.
- Could return a proper http code when the customer exists but has no license.
- Implement authorization so that only allowed users could access their data.
- Could probably create some sort of inheritance model for content that could be reused, but considering we only have 2 types, it felt over-engineering to do it now.
