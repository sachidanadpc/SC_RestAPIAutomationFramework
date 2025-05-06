package api.testcases;

import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.HttpRetryUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("PetStore-End to end  Operations about user")
@Feature("Create Get, Update Delete Users")
public class UserTest {	
	
	Faker faker;
	User userPayload;
	public static Logger logger;
	
	
	
	@BeforeClass	
	public void generateTestData()
	{		
		faker = new Faker();			
		userPayload = new User();
		
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
	
	
	@Story("Create User")
	@Test(priority=1, description= "End to end  Operations about user")
	@Description("Create User POST API")
	@Severity(SeverityLevel.CRITICAL)
	
	public void testCreateUserData()
	{
		
		Response response = HttpRetryUtil.retryRequest(
			    () -> UserEndpoints.createUser(userPayload), 
			    200, 3, 1
			);
	
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("Create User executed !!");		
		
	}
	
	
	@Story("Get User")
	@Test(priority=2, description= "End to end  Get user by user name", dependsOnMethods = {"testCreateUserData"})
	@Description("Get User API")
	@Severity(SeverityLevel.CRITICAL)
	public void testGetUserData()
	{
		Response response = HttpRetryUtil.retryRequest(
			    () -> UserEndpoints.GetUser(this.userPayload.getUsername()), 
			    200, 3, 1
			);
		
		System.out.println("Get User Data");
				
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);		
		
		logger.info("Get User executed !!");		
		
	}
	
	@Story("Update User")
	@Test(priority=3, description= "End to end  Updated user by user name", dependsOnMethods = {"testCreateUserData"})
	@Description("Update User PUT API")
	@Severity(SeverityLevel.CRITICAL)
	public void testUpdateUserData()
	{
		userPayload.setFirstName(faker.name().firstName());
		
		Response response = HttpRetryUtil.retryRequest(
			    () -> UserEndpoints.UpdateUser(this.userPayload.getUsername(), userPayload), 
			    200, 3, 1
			);
		
		System.out.println("Update User Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		//Read data is updated or not
		Response responsePostUpdate = UserEndpoints.GetUser(this.userPayload.getUsername());
		
		System.out.println("After Update User Data");
		
		responsePostUpdate.then().log().all();
		
		logger.info("Update User executed !!");		
	
		
	}
	
	@Story("Delete User")
	@Test(priority=4, description= "End to end  Delete user by user name", dependsOnMethods = {"testCreateUserData"})
	@Description("Delete User API")
	@Severity(SeverityLevel.CRITICAL)
	public void testDeleteUserData()
	{
		Response response = HttpRetryUtil.retryRequest(
			    () -> UserEndpoints.DeleteUser(this.userPayload.getUsername()), 
			    200, 3, 1
			);
		
		System.out.println("Delete User Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		logger.info("Delete User executed !!");
	}
	
}
