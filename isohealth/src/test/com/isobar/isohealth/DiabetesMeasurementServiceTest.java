package test.com.isobar.isohealth;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.DiabetesMeasurement;
import com.isobar.isohealth.models.DiabetesMeasurementFeed;
import com.isobar.isohealth.models.DiabetesMeasurementFeed.Item;
import com.isobar.isohealth.models.NewDiabetesMeasurement;
import com.isobar.isohealth.wrappers.DiabetesMeasurementWrapper;
import com.isobar.isohealth.wrappers.RunkeeperService;

public class DiabetesMeasurementServiceTest extends TestCase {

	DiabetesMeasurementWrapper diabetesMeasurementWrapper;
	DiabetesMeasurementFeed diabetesMeasurementFeed;
	
	protected void setUp() {
    	RunkeeperService runkeeperService = new RunkeeperService(GraphConstants.AUTH_CODE);
    	diabetesMeasurementWrapper = runkeeperService.diabetesMeasurementWrapper;
    	try {
    		diabetesMeasurementFeed = diabetesMeasurementWrapper.getDiabetesMeasurementFeed();
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    }
	
	public void testGetDiabetesMeasurementFeed() {
		try {
//			DiabetesMeasurementFeed diabetesMeasurementFeed = diabetesMeasurementWrapper.getDiabetesMeasurementFeed();
			System.out.println("DiabetesMeasurementService: " + diabetesMeasurementFeed.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testGetDiabetesMeasurement() {
		try {
//			DiabetesMeasurementFeed diabetesMeasurementFeed = DiabetesMeasurementService.getDiabetesMeasurementFeed(GraphConstants.AUTH_CODE);
			List<DiabetesMeasurement> diabetesMeasurementList =  new ArrayList<DiabetesMeasurement>();
			for (Item item : diabetesMeasurementFeed.getItems()) {
				DiabetesMeasurement diabetesMeasurement = diabetesMeasurementWrapper.getDiabetesMeasurement(item.getUri());
				System.out.println("DiabetesMeasurement: " + diabetesMeasurement.toString());
				diabetesMeasurementList.add(diabetesMeasurement);
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
		  
		  diabetesMeasurementWrapper.createDiabetesMeasurement(diabetesMeasurement);
		  System.out.println("DiabetesMeasurement created: ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testUpdateDiabetesMeasurement() {
		try {
			for (Item item : diabetesMeasurementFeed.getItems()) {
				DiabetesMeasurement diabetesMeasurement = diabetesMeasurementWrapper.getDiabetesMeasurement(item.getUri());
				System.out.println("DiabetesMeasurement: " + diabetesMeasurement);
				diabetesMeasurement.setInsulin(1.0);
				diabetesMeasurement = diabetesMeasurementWrapper.updateDiabetesMeasurement(diabetesMeasurement);
				System.out.println("Updated DiabetesMeasurement: " + diabetesMeasurement);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
