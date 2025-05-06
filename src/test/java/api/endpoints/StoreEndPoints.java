package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.Store;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StoreEndPoints {
	
	static ResourceBundle getURL()	
	{
		ResourceBundle routes = ResourceBundle.getBundle("Routes"); //load Routes properties		
		return routes;			
	} 
	
	
	public static Response createOrder(Store storePayload)	
	{
		String store_post_url = getURL().getString("store_post_url");
		Response response  = given()
		 .accept(ContentType.JSON)
		 .contentType(ContentType.JSON)
		 .body(storePayload)
		 
		 .when()
		 .post(store_post_url);			
		
		return response;		 
	}
	
	public static Response GetOrder(int Id)
	{
		
		String store_get_url = getURL().getString("store_get_url");
		Response response  = given()
				 .accept(ContentType.JSON)
				 .pathParam("orderId", Id)
				 
				 .when()
				 .get(store_get_url);	
		
				return response;
	}
	
	public static Response DeleteOrder(int Id)	
	{
		String store_delete_url = getURL().getString("store_delete_url");
		Response response  = given()
				 .accept(ContentType.JSON)
				 .pathParam("orderId", Id)
				 
				 .when()
				 .delete(store_delete_url);
						
				return response;			
	}
	

}
