package api.testcases;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.PetEndPoints;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import api.utilities.HttpRetryUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("PetStore-End to end  Everything about your Pets")
@Feature("Create Get, Update Delete Pet")

public class PetTest {
	
	
	Faker faker;
	Pet petDataPayload;
	public static Logger logger;
	
	
	
	@BeforeClass	
	public void generatePetTestData()
	{		
		faker = new Faker();			
		petDataPayload = new Pet();
		String[] statuses = {"available", "pending", "sold"};
		
		// Set ID
		petDataPayload.setId(faker.number().randomDigitNotZero());
		
		// Set Category
		Category category = new Category();
		category.setId(faker.number().numberBetween(1, 100));
		category.setName(faker.animal().name());
		petDataPayload.setCategory(category);
		
		// Set Name
		petDataPayload.setName(faker.dog().name());
		
		// Set Photo URLs
		List<String> photoUrls = Arrays.asList(faker.internet().url());
		petDataPayload.setPhotoUrls(photoUrls);
		
		// Set Tags
		Tag tag = new Tag();
		tag.setId(faker.number().numberBetween(1, 1000));
		tag.setName(faker.color().name());
		List<Tag> tags = Collections.singletonList(tag);
		petDataPayload.setTags(tags);
		
		// Set Status
		petDataPayload.setStatus(faker.options().option(statuses));
		
		// Set Status
		petDataPayload.setStatus(faker.options().option(statuses));
		
		//Obtain logger		
		logger =  LogManager.getLogger("RestAssuredAutomationFramework");
	}

	
	@Story("Create Pet")
	@Test(priority=1, description= "End to end Everything about your Pets")
	@Description("Create PET POST API")
	@Severity(SeverityLevel.NORMAL)
	
	public void testCreatePetData()
	{
		//Response response =  PetEndPoints.createPet(petDataPayload);	
		
		Response response = HttpRetryUtil.retryRequest(
			    () -> PetEndPoints.createPet(petDataPayload), 
			    200, 3, 1
			);
	
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("Create Pet executed !!");		
	}
	
	@Story("Get Pet")
	@Test(priority=2, description= "End to end  Get user by Pet Id", dependsOnMethods = {"testCreatePetData"})
	@Description("Get Pet API")
	@Severity(SeverityLevel.NORMAL)
	public void testGetPetData()
	{
		//Response response =  PetEndPoints.GetPetData(this.petDataPayload.getId());
		
		Response response = HttpRetryUtil.retryRequest(
			    () -> PetEndPoints.GetPetData(this.petDataPayload.getId()), 
			    200, 3, 1
			);
		
		System.out.println("Get Pet Data");
				
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);		
		
		logger.info("Get Pet executed !!");		
	}
	
	
	@Story("Update Pet")
	@Test(priority=3, description= "End to end Updated existing pet", dependsOnMethods = {"testCreatePetData"})
	@Description("Update Pet API")
	@Severity(SeverityLevel.NORMAL)
	public void testUpdatePetData()	{
		
		//Response response =  PetEndPoints.UpdatePetData(petDataPayload);
		
		Response response = HttpRetryUtil.retryRequest(
			    () -> PetEndPoints.UpdatePetData(petDataPayload), 
			    200, 3, 1
			);
		
		System.out.println("Update Pet Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		//Read data is updated or not
		Response responsePostUpdate = PetEndPoints.GetPetData(this.petDataPayload.getId());
		
		System.out.println("After Update Pet Data");
		
		responsePostUpdate.then().log().all();
		
		logger.info("Update Pet executed !!");		
		
	}
	
	@Story("Delete Pet")
	@Test(priority=4, description= "End to end  Delete user by petId", dependsOnMethods = {"testCreatePetData"})
	@Description("Delete Pet API")
	@Severity(SeverityLevel.NORMAL)
	public void testDeletePetData()
	{
		//Response response =  PetEndPoints.DeletePetData(this.petDataPayload.getId());
		
		Response response = HttpRetryUtil.retryRequest(
			    () -> PetEndPoints.DeletePetData(this.petDataPayload.getId()), 
			    200, 3, 1
			);
		
		System.out.println("Delete Pet Data");
		//log response
		response.then().log().all();
		
		//validation
		Assert.assertEquals(response.getStatusCode(),200);	
		
		logger.info("Delete Pet executed !!");
		
	}
}
