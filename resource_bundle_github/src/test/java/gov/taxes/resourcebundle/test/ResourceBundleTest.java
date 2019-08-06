package gov.taxes.resourcebundle.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import gov.taxes.infra.github.service.impl.MockCacheServiceImp;
import gov.taxes.resourcebundle.service.impl.ResourceServiceImpl;

/**
 * Unit test for resource bundle logic functions.
 * @author Eitan Nosraty
 *
 */

@SpringBootTest
public class ResourceBundleTest {
	private Map<String, String> heMap;
	private Map<String, String> enMap;
	private ResourceServiceImpl resourceService;
	private MockCacheServiceImp regularCacheServiceImp;
	private final String SYSTEM = "TEST";

	@Before
	public void init() {
		resourceService = new ResourceServiceImpl();
		regularCacheServiceImp = new MockCacheServiceImp();
		resourceService.setCacheService(regularCacheServiceImp);
		regularCacheServiceImp.init();
		heMap = new HashMap<String, String>();
		heMap.put("test", "בדיקה");
		heMap.put("welcomeMsg", "ברוכים הבאים");
		heMap.put("cases", "תיקים");
		enMap = new HashMap<String, String>();
		enMap.put("test", "Test");
		enMap.put("welcomeMsg", "Welcome");
		enMap.put("cases", "Cases");
	}
	
	/**
	 * Checks {@link ResourceServiceImpl#findResourcesByLocalesAndIdentsAndSystem(locales,idents,system)} function. 
	 */
	@Test
	public void checkGetAllResourcesByIdents() {
		Map<String, Map<String, String>> allResourcesByIdentsJsonComparison = new HashMap<String, Map<String, String>>();
		Map<String, String> heMap2 = new HashMap<String, String>();
		heMap2.put("test", "בדיקה");
		allResourcesByIdentsJsonComparison.put("he", heMap2);
		Map<String, String> enMap2 = new HashMap<String, String>();
		enMap2.put("test", "Test");
		allResourcesByIdentsJsonComparison.put("en", enMap2);
		
		ArrayList<String> idents = new ArrayList<String>();
		idents.add("test");
		
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "he"), heMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "en"), enMap);
		List<String> locales = new ArrayList<>();
		locales.add("he");
		locales.add("en");
		Map<String, Map<String, String>> allResourcesByIdentsJson = resourceService.findResourcesByLocalesAndIdentsAndSystem
				(locales, idents, SYSTEM);
		assertTrue(allResourcesByIdentsJson.equals(allResourcesByIdentsJsonComparison));
	}
	
	/**
	 * Checks {@link ResourceServiceImpl#getAllResourcesBySystemAndLocales(system,locales)} function. 
	 */
	@Test
	public void checkGetResourcesByLocale() {
		Map<String, String> allResourcesByLocaleJsonComparison1 = new HashMap<String, String>();
		allResourcesByLocaleJsonComparison1.put("test", "בדיקה");
		allResourcesByLocaleJsonComparison1.put("welcomeMsg", "ברוכים הבאים");
		allResourcesByLocaleJsonComparison1.put("cases", "תיקים");
		
		Map<String, String> allResourcesByLocaleJsonComparison2 = new HashMap<String, String>();
		allResourcesByLocaleJsonComparison2.put("test", "בדיקה");
		allResourcesByLocaleJsonComparison2.put("cases", "תיקים");
			
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "he"), heMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "en"), enMap);
		
		List<String> locales = new ArrayList<>();
		locales.add("he");
		
		Map<String, String> allResourcesByIdentsJson = resourceService.getAllResourcesBySystemAndLocales(SYSTEM, locales).get("he");
		assertTrue(allResourcesByIdentsJson.equals(allResourcesByLocaleJsonComparison1));
		assertFalse(allResourcesByIdentsJson.equals(allResourcesByLocaleJsonComparison2));
	}

}
