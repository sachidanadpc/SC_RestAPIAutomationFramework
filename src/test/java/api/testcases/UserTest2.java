package api.testcases;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.userEndpoints2;
import api.payload.user;
import io.restassured.response.Response;

public class UserTest2 {	
	
	Faker faker;
	user userPayload;
	public static Logger logger;
	
	@BeforeClass	
	public void generateTestData()
	{		
		faker = new Faker();			
		userPayload = new user();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());		
		
		//Obtain logger		
		logger =  LogManager.getLogger("RestAssuredAutomationFramework");
	}
	
	
	@Test(priority=1)
	public void testCreateUserData()
	{
		Response response =  userEndpoints2.createUser(userPayload);					
	
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("Create User executed !!");
	}
	
	@Test(priority=2)
	public void testGetUserData()
	{
		Response response =  userEndpoints2.GetUser(this.userPayload.getUsername());
		
		System.out.println("Get User Data");
				
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);		
		
		logger.info("Get User executed !!");
	}
	
	@Test(priority=3)
	public void testUpdateUserData()
	{
		userPayload.setFirstName(faker.name().firstName());
		Response response =  userEndpoints2.UpdateUser(this.userPayload.getUsername(), userPayload);
		
		System.out.println("Update User Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		//Read data is updated or not
		Response responsePostUpdate = userEndpoints2.GetUser(this.userPayload.getUsername());
		
		System.out.println("After Update User Data");
		
		responsePostUpdate.then().log().all();
		
		logger.info("Update User executed !!");
		
	}
	
	@Test(priority=4)
	public void testDeleteUserData()
	{
		Response response =  userEndpoints2.DeleteUser(this.userPayload.getUsername());
		
		System.out.println("Delete User Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		logger.info("Delete User executed !!");
	}
	
}
