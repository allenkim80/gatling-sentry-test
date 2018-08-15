# gatling-sentry-test

This project is for testing gatling-sentry-extension that I made.
This gatling simulation run under sbt environment. 
So this would be the good example if you want to run gatling under sbt environment.

This project is too simple to use all useful functions, but this is good starting project for newbie of gatling. 
Let's try to run the this project.

## Configurations you need to change

### Test server address

It needs to add server address for gatling simulation. Please change the address that you want to test.

```aidl
// /src/test/scala/com/github/allenkim80/gatling/TestSimulation.scala
def createHttpProtocol() : HttpProtocolBuilder = {
    http
      .baseURL("http://server-address")  // Please change server address
      .acceptHeader("*/*")
      .headers(Map(
        "Content-Type" -> "application/json"
      ))
      .disableWarmUp
  }
```  

### Endpoint 

If you want to test Restful APIs, yon need to add valid endporint to HttpRequestBuilder in gatling.

```aidl
val scn = scenario("Sentry")
    .exec(
      http("/events")               // httpReqeustBuilder - Need to change endporint and CRUD functions that you want.
        .get("/events")
        .transformResponse {case response if response.isReceived => sendSentryLog(response, List("Error1", "Error2"), "message")}
    ).exec()
    .exec(sentry("").sendStringLogByAction("test2", "message"))
    .pause(3)
```

### Run command 

If you want to run the gatling, need to input the command in your sbt console.

```aidl
> clean gatling:test
```

`clean` will clean your target folder that has artifacts of test.
`gatling:test` means it will run the gatling simulation in `src/test` folder.


