package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.Pet;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndPoints {
	
	
	static ResourceBundle getURL()	
	{
		ResourceBundle routes = ResourceBundle.getBundle("Routes"); //load Routes properties		
		return routes;	
		
	} 
	
	public static Response createPet(Pet petPayload)	
	{
		
		String pet_post_url = getURL().getString("pet_post_url");
		Response response  = given().filter(new AllureRestAssured())
		 .accept(ContentType.JSON)
		 .contentType(ContentType.JSON)
		 .body(petPayload)
		 
		 .when()
		 .post(pet_post_url);		
		
		return response;
		 
	}
	
	public static Response GetPetData(int petId)	
	{
		
		String pet_get_url = getURL().getString("pet_get_url");
		Response response  = given().filter(new AllureRestAssured())
		 .accept(ContentType.JSON)
		 .pathParam("petId", petId)
		 
		 .when()
		 .get(pet_get_url);		
		
		return response;
		 
	}
	
	public static Response UpdatePetData(Pet petPayload)	
	{
		
		String update_post_url = getURL().getString("pet_update_url");
		Response response  = given().filter(new AllureRestAssured())
		 .accept(ContentType.JSON)
		 .contentType(ContentType.JSON)
		 .body(petPayload)
		 
		 .when()
		 .put(update_post_url);		
		
		return response;
		 
	}
	
	public static Response DeletePetData(int petId)	
	{
		
		String delete_post_url = getURL().getString("pet_delete_url");
		Response response  = given().filter(new AllureRestAssured())
		 .accept(ContentType.JSON)
		 .pathParam("petId", petId)
		 
		 .when()
		 .put(delete_post_url);		
		
		return response;
		 
	}

}
