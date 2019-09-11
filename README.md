# mp-lra-demo
Demo for MicroProfile LRA Presentation

This repository contain the demos used in the presentation "Transactions in your micro-services architecture".

:heavy_exclamation_mark: For the moment (September 2019), Payara Server and Payara Micro doesn't support MicroProfile LRA yet. That is also the reason why there is an additionally dependency added into the projects. that Payara implementation code is not yet available on Github or any other public place.

The projects can be used to learn how LRA works and give feedback on the Release Candidate which is released for MicroProfile LRA. Please go the the [project page](https://github.com/eclipse/microprofile-lra) if you have any questions or remarks.

## Directory _intro_

Maven project which shows the basic concepts.

### Hello World

Shows you how you can start a transaction and get feedback when transaction is closed or cancelled.

url : **/rest/hello**
url : **/rest/hello/cancel**

Have a look in the Server log for the output done by the callback methods.

code : **HelloLRAResource**

### Automatic timeout

You can limit the lifetime of the transaction. After that time, the transactions is automatically cancelled.

url : **/rest/timeout**

Have a look in the Server log for the output done by the callback methods and **/rest/lra** for an overview and the status of the LRAs known by the implementation.

code : **TimeoutLRAResource**

## Directory _distributed_

Maven project which shows that the management of the transactions works in a distributed environment.

There are 3 JAX-RS resources which work together. The **MainResource** is a class which represents a regular endpoint, not LRA related. It calls the endpoint defined in **ServiceB**. This service B endpoint, starts an LRA and keeps it open (by specifying `@LRA(end=false)`). The result is that the response from the service B contains a header indicating the unique ID assigned to the transaction. This id is passed on to the **ServiceC** class by the main resource. It only can be called when a transaction is active (`@LRA(value = LRA.Type.MANDATORY)`) and ends the transaction with an error. This results in the execution of the @Compensate callback methods of both **ServiceB** and **ServiceC**.

Startup of the 3 services through Payara Micro : **./startDemo.sh** (which expects the Payara Micro jar in the directory which is not in the repository)

url : **localhost:8080/servicea/rest/main**

## Directory _compensating_

Shows the different ways how you can return the status from the callback methods to the implementation. This is shown for the @Compensate methods but can also be used for the @Complete callback method.

### Multiple calls to callback

When the @Compensate method is idempotent, it can be called multiple times by the implementation. It will provide the system feedback about the status of the compensating action which can be 'in progress' (`compensating`), done (`Compensated`) or failed (`FailedToCompensate`).

url : **/rest/repeat**

Have a look in the Server log for the output done by the callback method and **/rest/lra** for an overview and the status of the LRAs known by the implementation.

code : **RepeatResource**

### Using status method

Calling the same method just for getting the status is not really object oriented (but is a common practice for JAX-RS calls). You can also implement a specific @Status callback method which return the status of the LRA. The @Status method is called when the implementation wants to know the final outcome of the @Complete and @Compensate methods.

url : **/rest/status**

Have a look in the Server log for the output done by the callback method and **/rest/lra** for an overview and the status of the LRAs known by the implementation.

code : **StatusResource**

### Using asynchronous result

Instead of returning the resulting status of the LRA when the implementation calls the @Compensate callback method, we can also return a `CompletionStage` which will pass the outcome at some point in the future.  The example just wait a certain amount of time before it returns the result. You can see in the example how the future value is picked up by the implementation and the correct status is assigned to the participant and the LRA.

url : **/rest/async**

Have a look in the Server log for the output done by the callback method and **/rest/lra** for an overview and the status of the LRAs known by the implementation.

code : **AsyncResource**

## Directory _final_

This example shows how you can get notified about the final outcome of an LRA. For example, an individual participant might be interested to know that other participants failed the completion although they could complete successfully. For an endpoint which has started the LRA but did not participant in it, it might want to know the final outcome. The example shows the usage of @AfterLRA

There are 2 JAX-RS endpoints in the example. The first one just starts an LRA and calls a second endpoint which is defied as participant. Both will receive the final outcome of the LRA.

url : **/rest/serviceC**

Have a look in the Server log for the output done by the callback methods.

code : **ServiceCResource** and **ServiceDResource**

## Directory _leave_

The example shows that a participant also can leave a transactions it was enlisted with. The first call to the endpoint of **Leave2Resource** registers the class with the LRA. But the second call, annotated with @Leave, makes sure the participant doesn't get any callbacks anymore when the LRA ends.

url : **/rest/leave1**

Have a look in the Server log for the missing output of the callback methods.

code : **Leave1Resource** and **Leave2DResource**

## Directory _track_

This demonstrates an extension of Payara for the MicroProfile LRA. In the future, the API will provide means to store also some LRA or participant specific data at the implementation. That way, the developer or JAX-RS resource itself is no longer responsible to keep, for example, a mapping between the LRA id and the business id for the application. The Payara implementation foresees already the types **fish.payara.microprofile.lra.api.LRAData** and **fish.payara.microprofile.lra.api.ParticipantData** to perform that functionality. You can inject an instance of these into your class and call the _store_ and _read_.

url : **/rest/hello**

Have a look in the Server log for the output done by the methods.

code : **ExtensionLRAResource**
