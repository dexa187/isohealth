package test.com.isobar.isohealth;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.NewSleepMeasurement;
import com.isobar.isohealth.models.SleepMeasurement;
import com.isobar.isohealth.models.SleepMeasurementFeed;
import com.isobar.isohealth.models.SleepMeasurementFeed.Item;
import com.isobar.isohealth.wrappers.RunkeeperService;
import com.isobar.isohealth.wrappers.SleepMeasurementWrapper;

public class SleepMeasurementServiceTest extends TestCase {

	SleepMeasurementWrapper sleepMeasurementWrapper;
	SleepMeasurementFeed sleepMeasurementFeed;
	
	protected void setUp() {
    	RunkeeperService runkeeperService = new RunkeeperService(GraphConstants.AUTH_CODE);
    	sleepMeasurementWrapper = runkeeperService.sleepMeasurementWrapper;
    	try {
    		sleepMeasurementFeed = sleepMeasurementWrapper.getSleepMeasurementFeed();
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    }
	
	public void testSleepMeasurementFeed() {
		try {
			System.out.println("SleepMeasurementFeed: " + sleepMeasurementFeed.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSleepMeasurement() {
		try {
			List<SleepMeasurement> sleepMeasurementList =  new ArrayList<SleepMeasurement>();
			for (Item item : sleepMeasurementFeed.getItems()) {
				SleepMeasurement sleepMeasurement = sleepMeasurementWrapper.getSleepMeasurement(item.getUri());
				System.out.println("SleepMeasurement: " + sleepMeasurement.toString());
				sleepMeasurementList.add(sleepMeasurement);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testUpdateSleepMeasurement() {
		try {
			for (Item item : sleepMeasurementFeed.getItems()) {
				SleepMeasurement sleepMeasurement = sleepMeasurementWrapper.getSleepMeasurement(item.getUri());
				sleepMeasurement.setTotal_sleep(500.0);
				SleepMeasurement updatedSleepMeasurement = sleepMeasurementWrapper.updateSleepMeasurement(sleepMeasurement);
				System.out.println("Updated SleepMeasurement: " + updatedSleepMeasurement);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	
	public void testCreateNewSleepMeasurement() {
		try {
		  NewSleepMeasurement sleepMeasurement = new NewSleepMeasurement();
		  sleepMeasurement.setTotal_sleep(460.0);
		  sleepMeasurement.setTimestamp("Wed, 5 Jan 2011 07:03:00");
		  
		  sleepMeasurementWrapper.createSleepMeasurement(sleepMeasurement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
