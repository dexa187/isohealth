package test.com.isobar.isohealth;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.DiabetesMeasurement;
import com.isobar.isohealth.models.DiabetesMeasurementFeed;
import com.isobar.isohealth.models.DiabetesMeasurementFeed.Item;
import com.isobar.isohealth.models.NewDiabetesMeasurement;
import com.isobar.isohealth.services.DiabetesMeasurementService;

public class DiabetesMeasurementServiceTest extends TestCase {
	
	public void testGetDiabetesMeasurementFeed() {
		try {
			DiabetesMeasurementFeed diabetesMeasurementFeed = DiabetesMeasurementService.getDiabetesMeasurementFeed(GraphConstants.AUTH_CODE);
			System.out.println("DiabetesMeasurementService: " + diabetesMeasurementFeed.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testGetDiabetesMeasurement() {
		try {
			DiabetesMeasurementFeed diabetesMeasurementFeed = DiabetesMeasurementService.getDiabetesMeasurementFeed(GraphConstants.AUTH_CODE);
			List<DiabetesMeasurement> diabetesMeasurement =  new ArrayList<DiabetesMeasurement>();
			for (Item item : diabetesMeasurementFeed.getItems()) {
				DiabetesMeasurement activity = DiabetesMeasurementService.getDiabetesMeasurement(item.getUri(),GraphConstants.AUTH_CODE);
				System.out.println("DiabetesMeasurement: " + activity.toString());
				diabetesMeasurement.add(activity);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testCreateDiabetesMeasurement() {
		try {
		  NewDiabetesMeasurement diabetesMeasurement = new NewDiabetesMeasurement();
		  diabetesMeasurement.setInsulin(2.0);
		  diabetesMeasurement.setTimestamp("Wed, 5 Jan 2011 07:03:00");
		  
		  DiabetesMeasurementService.createDiabetesMeasurement(diabetesMeasurement, GraphConstants.AUTH_CODE);
		  System.out.println("DiabetesMeasurement created: ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testUpdateDiabetesMeasurement() {
		try {
			DiabetesMeasurementFeed diabetesMeasurementFeed = DiabetesMeasurementService.getDiabetesMeasurementFeed(GraphConstants.AUTH_CODE);
			for (Item item : diabetesMeasurementFeed.getItems()) {
				DiabetesMeasurement diabetesMeasurement = DiabetesMeasurementService.getDiabetesMeasurement(item.getUri(),GraphConstants.AUTH_CODE);
				System.out.println("DiabetesMeasurement: " + diabetesMeasurement);
				diabetesMeasurement.setInsulin(1.0);
				diabetesMeasurement = DiabetesMeasurementService.updateDiabetesMeasurement(diabetesMeasurement, GraphConstants.AUTH_CODE);
				System.out.println("Updated DiabetesMeasurement: " + diabetesMeasurement);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
