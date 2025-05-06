package api.endpoints;

import static io.restassured.RestAssured.given;
import api.payload.User;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndpoints {
	
	public static Response createUser(User payload)	
	{
		Response response  = given().filter(new AllureRestAssured())
		 .accept(ContentType.JSON)
		 .contentType(ContentType.JSON)
		 .body(payload)
		 
		 .when()
		 .post(Routes.post_url);		
		
		
		return response;
		 
	}
	
	
	public static Response GetUser(String userName)
	{
		
		Response response  = given().filter(new AllureRestAssured())
				 .accept(ContentType.JSON)
				 .pathParam("username", userName)
				 
				 .when()
				 .get(Routes.get_url);	
						
				return response;
	}
	
	public static Response UpdateUser(String userName, User payload)	
	{
		
		Response response  = given().filter(new AllureRestAssured())
				 .accept(ContentType.JSON)
				 .contentType(ContentType.JSON)
				 .pathParam("username", userName)
				 .body(payload)
				 
				 .when()
				 .put(Routes.update_url);
						
				return response;			
	}
	
	public static Response DeleteUser(String userName)	
	{
		
		Response response  = given().filter(new AllureRestAssured())
				 .accept(ContentType.JSON)
				 .pathParam("username", userName)
				 
				 .when()
				 .delete(Routes.delete_url);
						
				return response;			
	}

}
