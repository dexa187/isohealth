package test.com.isobar.isohealth;

import junit.framework.TestCase;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.Settings;
import com.isobar.isohealth.wrappers.RunkeeperService;
import com.isobar.isohealth.wrappers.SettingsWrapper;

public class SettingsServiceTest extends TestCase {

	SettingsWrapper settingsWrapper;
	
	protected void setUp() {
    	RunkeeperService runkeeperService = new RunkeeperService(GraphConstants.AUTH_CODE);
    	settingsWrapper = runkeeperService.settingsWrapper;
    }

	public void testGetSettings() {
		try {
			Settings settings = settingsWrapper.getSettings();
			System.out.println("Settings: " + settings.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testUpdateSettings() {
		try {
			Settings settings = settingsWrapper.getSettings();
			System.out.println("Settings: " + settings.toString());
			settings.setShare_map("Everyone");
			settings = settingsWrapper.updateProfile(settings);
			System.out.println("Updated Settings: " + settings.toString());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
