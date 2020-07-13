Feature: Validating place API's

@AddPlace @Regression
Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
Given Add Place Payload with "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "POST" http request
Then the API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify place_id is mapped to "<name>" using "GetPlaceAPI"
Examples:
	|name|language|address|
	|AA house|English|World Cross Center|
#	|BB house|Spanish|Sea cross center|

@DeletePlace @Regression
Scenario: Verify delete place api
Given DeletePlace Payload
When user calls "deletePlaceAPI" with "POST" http request
Then the API call got success with status code 200
And "status" in response body is "OK"