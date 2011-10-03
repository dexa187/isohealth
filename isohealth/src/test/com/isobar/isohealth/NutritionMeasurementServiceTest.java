package test.com.isobar.isohealth;

import java.util.ArrayList;
import java.util.List;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.NewNutritionMeasurement;
import com.isobar.isohealth.models.NutritionMeasurement;
import com.isobar.isohealth.models.NutritionMeasurementFeed;
import com.isobar.isohealth.models.NutritionMeasurementFeed.Item;
import com.isobar.isohealth.wrappers.NutritionMeasurementWrapper;
import com.isobar.isohealth.wrappers.RunkeeperService;

public class NutritionMeasurementServiceTest {
	
	NutritionMeasurementWrapper nutritionMeasurementWrapper;
	NutritionMeasurementFeed nutritionMeasurementFeed;
	
	protected void setUp() {
    	RunkeeperService runkeeperService = new RunkeeperService(GraphConstants.AUTH_CODE);
    	nutritionMeasurementWrapper = runkeeperService.nutritionMeasurementWrapper;
    	try {
    		nutritionMeasurementFeed = nutritionMeasurementWrapper.getNutritionMeasurementFeed();
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    }
	public void testNutritionMeasurementFeed() {
		try {
//			NutritionMeasurementFeed nutritionMeasurementFeed = NutritionMeasurementService.getNutritionMeasurementFeed(GraphConstants.AUTH_CODE);
			System.out.println("NutritionMeasurementFeed: " + nutritionMeasurementFeed.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testNutritionMeasurement() {
		try {
//			NutritionMeasurementFeed nutritionMeasurementFeed = NutritionMeasurementService.getNutritionMeasurementFeed(GraphConstants.AUTH_CODE);
			List<NutritionMeasurement> nutritionMeasurementList =  new ArrayList<NutritionMeasurement>();
			for (Item item : nutritionMeasurementFeed.getItems()) {
				NutritionMeasurement nutritionMeasurement = nutritionMeasurementWrapper.getNutritionMeasurement(item.getUri());
				System.out.println("NutritionMeasurement: " + nutritionMeasurement.toString());
				nutritionMeasurementList.add(nutritionMeasurement);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testUpdateNutritionMeasurement() {
		try {
//			NutritionMeasurementFeed nutritionMeasurementFeed = NutritionMeasurementService.getNutritionMeasurementFeed(GraphConstants.AUTH_CODE);
			for (Item item : nutritionMeasurementFeed.getItems()) {
				NutritionMeasurement nutritionMeasurement = nutritionMeasurementWrapper.getNutritionMeasurement(item.getUri());
				nutritionMeasurement.setCarbohydrates(0.02);
				NutritionMeasurement updatedNutritionMeasurement = nutritionMeasurementWrapper.updateNutritionMeasurement(nutritionMeasurement);
				System.out.println("Updated NutritionMeasurement: " + updatedNutritionMeasurement);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	
	public void testCreateNewNutritionMeasurement() {
		try {
		  NewNutritionMeasurement nutritionMeasurement = new NewNutritionMeasurement();
		  nutritionMeasurement.setCarbohydrates(0.015);
		  nutritionMeasurement.setTimestamp("Wed, 5 Jan 2011 07:03:00");
		  
		  nutritionMeasurementWrapper.createNutritionMeasurement(nutritionMeasurement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
