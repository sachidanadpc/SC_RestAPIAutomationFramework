package api.testcases;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.userEndpoints;
import api.payload.user;
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
	
	
	@Story("Create User")
	@Test(priority=1, description= "End to end  Operations about user")
	@Description("Create User POST API")
	@Severity(SeverityLevel.CRITICAL)
	
	public void testCreateUserData()
	{
		Response response =  userEndpoints.createUser(userPayload);					
	
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("Create User executed !!");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Story("Get User")
	@Test(priority=2, description= "End to end  Get user by user name", dependsOnMethods = {"testCreateUserData"})
	@Description("Get User POST API")
	@Severity(SeverityLevel.CRITICAL)
	public void testGetUserData()
	{
		Response response =  userEndpoints.GetUser(this.userPayload.getUsername());
		
		System.out.println("Get User Data");
				
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);		
		
		logger.info("Get User executed !!");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Story("Update User")
	@Test(priority=3, description= "End to end  Updated user by user name", dependsOnMethods = {"testCreateUserData"})
	@Description("Update User POST API")
	@Severity(SeverityLevel.CRITICAL)
	public void testUpdateUserData()
	{
		userPayload.setFirstName(faker.name().firstName());
		Response response =  userEndpoints.UpdateUser(this.userPayload.getUsername(), userPayload);
		
		System.out.println("Update User Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		//Read data is updated or not
		Response responsePostUpdate = userEndpoints.GetUser(this.userPayload.getUsername());
		
		System.out.println("After Update User Data");
		
		responsePostUpdate.then().log().all();
		
		logger.info("Update User executed !!");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Story("Delete User")
	@Test(priority=4, description= "End to end  Delete user by user name", dependsOnMethods = {"testCreateUserData"})
	@Description("Delete User POST API")
	@Severity(SeverityLevel.CRITICAL)
	public void testDeleteUserData()
	{
		Response response =  userEndpoints.DeleteUser(this.userPayload.getUsername());
		
		System.out.println("Delete User Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		logger.info("Delete User executed !!");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
