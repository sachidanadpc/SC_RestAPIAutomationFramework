package api.testcases;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.StoreEndPoints;
import api.payload.Store;
import api.utilities.HttpRetryUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Epic("PetStore-End to end Access to Petstore orders")
@Feature("Create Get, Delete Store")
public class StoreTest {
	
	Faker faker;
	Store storePayload;
	public static Logger logger;
	
	
	@BeforeClass	
	public void generateStoreTestData()
	{		
		faker = new Faker();			
		storePayload = new Store();
		
		// Generate a unique order ID
		storePayload.setId(faker.number().numberBetween(1000, 9999));
		
		// Set pet ID 
		storePayload.setPetId(faker.number().numberBetween(1, 1000));
		
		// Quantity of items
		storePayload.setQuantity(faker.number().numberBetween(1, 10));
		
		// Ship Date (ISO 8601 format)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String shipDate = sdf.format(new Date());
        storePayload.setShipDate(shipDate);
        
        // Status (randomly from allowed values)
        String[] statuses = {"placed", "approved", "delivered"};
        storePayload.setStatus(faker.options().option(statuses));
        
     // Complete flag
        storePayload.setComplete(faker.bool().bool());
        
      //Obtain logger		
      logger =  LogManager.getLogger("RestAssuredAutomationFramework");
		
	}
	
	@Story("Create An Order")
	@Test(priority=1, description= "Place an order for Pet")
	@Description("Create An Order POST API")
	@Severity(SeverityLevel.NORMAL)
	
	public void testCreateAnOrderData()
	{
		Response response = HttpRetryUtil.retryRequest(
			    () -> StoreEndPoints.createOrder(storePayload), 
			    200, 3, 1
			);

			
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);
		
		// Extract fields from response
		int id = storePayload.getId();
	    
		System.out.println("Created User ID: " + id);
		
		// Wait until user is available
		boolean userId = HttpRetryUtil.waitUntil(() -> StoreEndPoints.GetOrder(id), 10, 200);
		
		Assert.assertTrue(userId, "Id was not available after creation");
		
		logger.info("Create Order for Pet executed !!");		

	}
	
	
	@Story("Get An Order")
	@Test(priority=2, description= "Find purchase order by ID", dependsOnMethods = {"testCreateAnOrderData"})
	@Description("Get An Order API")
	@Severity(SeverityLevel.NORMAL)
	public void testGetAnOrderData()
	{
		
		Response response = HttpRetryUtil.retryRequest(
			    () -> StoreEndPoints.GetOrder(this.storePayload.getId()), 
			    200, 3, 1
			);
		
		System.out.println("Get Order Data");
				
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);		
		
		logger.info("Get Order executed !!");

	}
	
	@Story("Delete An Order")
	@Test(priority=3, description= "Delete purchase order by ID", dependsOnMethods = {"testCreateAnOrderData"})
	@Description("Delete An Order API")
	@Severity(SeverityLevel.NORMAL)
	public void testDeleteAnOrderData()
	{
	
		Response response = HttpRetryUtil.retryRequest(
			    () -> StoreEndPoints.DeleteOrder(this.storePayload.getId()), 
			    200, 3, 1
			);
		
		System.out.println("Delete Order Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		logger.info("Delete Order executed !!");

	}
}
