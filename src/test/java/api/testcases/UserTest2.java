package api.testcases;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndpoints2;
import api.payload.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("PetStore-End to end  Everything about your User with User")
@Feature("Create Get, Update Delete User")
public class UserTest2 {	
	
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
	
	
	@Story("Create User 2")
	@Test(priority=1, description= "End to end  Operations about user")
	@Description("Create User POST API 2")
	@Severity(SeverityLevel.MINOR)
	public void testCreateUserData()
	{
		Response response =  UserEndpoints2.createUser(userPayload);					
	
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("Create User executed !!");
	}
	
	@Story("Get User 2")
	@Test(priority=2, description= "End to end  Operations about user")
	@Description("Get User API 2")
	@Severity(SeverityLevel.MINOR)
	public void testGetUserData()
	{
		Response response =  UserEndpoints2.GetUser(this.userPayload.getUsername());
		
		System.out.println("Get User Data");
				
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);		
		
		logger.info("Get User executed !!");
	}
	
	@Story("Update User 3")
	@Test(priority=2, description= "End to end  Operations about user")
	@Description("Update User API 2")
	@Severity(SeverityLevel.MINOR)
	public void testUpdateUserData()
	{
		userPayload.setFirstName(faker.name().firstName());
		Response response =  UserEndpoints2.UpdateUser(this.userPayload.getUsername(), userPayload);
		
		System.out.println("Update User Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		//Read data is updated or not
		Response responsePostUpdate = UserEndpoints2.GetUser(this.userPayload.getUsername());
		
		System.out.println("After Update User Data");
		
		responsePostUpdate.then().log().all();
		
		logger.info("Update User executed !!");
		
	}
	
	@Story("Delete User 3")
	@Test(priority=4, description= "End to end  Operations about user")
	@Description("Delete User API 2")
	@Severity(SeverityLevel.MINOR)
	public void testDeleteUserData()
	{
		Response response =  UserEndpoints2.DeleteUser(this.userPayload.getUsername());
		
		System.out.println("Delete User Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		logger.info("Delete User executed !!");
	}
	
}
