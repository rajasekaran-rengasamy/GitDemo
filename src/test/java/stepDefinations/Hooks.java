package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		StepDefination m = new StepDefination();
		if(m.place_id==null)
		{
		m.add_Place_Payload_with("Shetty", "French", "Asia");
		m.user_calls_with_Post_http_request("AddPlaceAPI", "POST");
		m.verify_place_id_is_mapped_to_using("Shetty", "GetPlaceAPI");
		}
				
	}
	

}
