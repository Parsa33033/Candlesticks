# Candlesticks

### Table of Contents

1. [Overview](#Overview)
2. [Used_Tech](#Used_Tech)
3. [requirements](#Requirements)
4. [How_to_run](#How_to_run)
5. [Models](#Models)
6. [Endpoints](#Endpoints)
7. [My second title](#my-second-title)

## Overview

Some text.
![Candlestick system architecture](candlestick.png)
## Used_Tech


The tech used for this API are:

1) Java 11
2) Spring boot
3) Redis
4) MongoDB
5) RabbitMQ
6) Hibernate

## Requirements


1) Java 11
2) Docker
3) Maven

These requirements should be installed on PATH


## How_to_run

For running the API, the steps below must be followed:

1) Run `docker.sh` to start the docker-compose.yml services or use `docker-compose up` to get the same result.
   ```
   Project_Folder_Path> docker.sh
   ```
   Wait until all dependencies on [Used Tech](#Used Tech) are up and running.
   

2) Run `run-candlestick-provider.sh` to start the candlestick-provider service
   ```
   Project_Folder_Path> run-candlestick-provider.sh
   ```


3) Run `run-candlestick-builder.sh` to start the candlestick-builder service
   ```
   Project_Folder_Path> run-candlestick-builder.sh
   ```

Now you're ready to start...


## Models


**Candlestick**

|          Name | Json Ignore |  Type   | Description                                                                                                                                                           |
| -------------:|:--------:|:-------:| --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|     `isin` | true | string  | instrument isin. |
|     `openTimestamp` | false | Instant  | the timestamp of the first quote within the minute |
|     `currentTimestamp` | true | Instant  | the current received quote timestamp |
|     `openPrice` | false | double  | the time stamp of the price of the first quote within the minute |
|     `highPrice` | false | double  | the highest price of the instrument's quotes within the minute they were sent |
|     `lowPrice` | false | double  | the lowest price of the instrument's quotes within the minute they were sent |
|     `closingPrice` | false | double  | the price of the last quote within the minute |
|     `closeTimestamp` | false | Instant  | the timestamp of the next minute's first quote |


**Instrument**

|          Name | Json Ignore |  Type   | Description                                                                                                                                                           |
| -------------:|:--------:|:-------:| --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|     `isin` | false | string  | instrument isin. |
|     `description` | false | string  | the timestamp of the first quote within the minute |
|     `type` | false | Type  | status of the instrument <br><br> can be ADD or DELETE indicating whether the instrument is added to the catalogue or deleted and not shown to users |
|     `candlesticks` | true | List<Candlestick>  | List of all candlesticks |

## Endpoints


Users can access candlestick-provider through the url and enpoints below:

### Candlestick-provider url:    
`http://localhost:9000`

---
### GET
`get isin candlesticks` [http://localhost:9000/candlesticks?isin={isin}](#get-candlesticks) <br/>
`get all added instruments` [http://localhost:9000/instruments/get-all-added](#get-instrumentsget-all-added) <br/>
`get all instruments` [http://localhost:9000/instruments/get-all](#get-instrumentsget-all) <br/>

---
### GET /candlesticks
Gets a list of size at most 30 containing maximum 30 most recent candles sticks of an instrument with isin number provided as the parameter

**Parameters**

|          Name | Required |  Type   | Description                                                                                                                                                           |
| -------------:|:--------:|:-------:| --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|     `isin` | required | string  | the available (ADDED) instrument isin. |

**Response**

List< Candlestick >

```

// If instrument isin does not exist with message "Instrument with id:{isin} does not exist!"
{
    "timestamp": "2022-04-26T23:13:44.289+00:00",
    "status": 404,
    "error": "Not Found",
    "message": "Instrument with id:{isin} does not exist!",
    "path": "/candlesticks"
}

// No value present
[]

// a list of 30 or less of the last candlesticks
[
    {
        "openTimestamp": "2022-04-26T21:36:00.893051700Z",
        "openPrice": 361.7534,
        "highPrice": 388.7215,
        "lowPrice": 354.2511,
        "closingPrice": 388.7215,
        "closeTimestamp": "2022-04-26T21:37:03.522518300Z"
    },
    {
        "openTimestamp": "2022-04-26T21:35:04.917360200Z",
        "openPrice": 333.2877,
        "highPrice": 361.2557,
        "lowPrice": 333.2877,
        "closingPrice": 361.2557,
        "closeTimestamp": "2022-04-26T21:36:00.893051700Z"
    },
    {
        "openTimestamp": "2022-04-26T21:34:03.145736800Z",
        "openPrice": 328.8082,
        "highPrice": 338.2922,
        "lowPrice": 323.3014,
        "closingPrice": 330.79,
        "closeTimestamp": "2022-04-26T21:35:04.917360200Z"
    },
...    
]
```
---
### GET /instruments/get-all-added
Get all ADDED instruments

**Parameters**
None

**Response**

List< Instrument >

```

// a list of ADDED instruments
[
    {
        "isin": "LJY205120718",
        "description": "ancillae turpis semper sed",
        "type": "ADD"
    },
    {
        "isin": "GN8335463462",
        "description": "imperdiet lectus dui vehicula animal",
        "type": "ADD"
    },
    {
        "isin": "DN7I2P211058",
        "description": "maximus platea tale",
        "type": "DELETE"
    },
    {
        "isin": "MR44L3T03650",
        "description": "veniam platea dicunt discere",
        "type": "DELETE"
    },
    {
        "isin": "OT1364645503",
        "description": "deterruisset purus accommodare commune imperdiet voluptaria",
        "type": "ADD"
    },
    {
        "isin": "RM5375036800",
        "description": "aliquet alia ludus fabulas definiebas gloriatur",
        "type": "DELETE"
    },
    ...
]
```

---
### GET /instruments/get-all
Get all ADDED instruments

**Parameters**
None

**Response**

List< Instrument >

```

// a list of ADDED instruments
[
    {
        "isin": "LJY205120718",
        "description": "ancillae turpis semper sed",
        "type": "ADD"
    },
    {
        "isin": "GN8335463462",
        "description": "imperdiet lectus dui vehicula animal",
        "type": "ADD"
    },
    {
        "isin": "OT1364645503",
        "description": "deterruisset purus accommodare commune imperdiet voluptaria",
        "type": "ADD"
    },
    ...
]
```



