package gov.taxes.resourcebundle.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import gov.taxes.infra.github.service.impl.MockCacheServiceImp;
import gov.taxes.resourcebundle.enums.LocaleEnum;
import gov.taxes.resourcebundle.service.impl.ResourceServiceImpl;

/**
 * Performance Unit test for Resource Bundle.
 * @author Eitan Nosraty
 *
 */

@SpringBootTest
public class ResourceBundlePerformanceTest {
	private final String SYSTEM = "TEST";
	private ResourceServiceImpl resourceService;
	private MockCacheServiceImp regularCacheServiceImp;
	
	private Map<String, String> heMap = new HashMap<String, String>();
	private Map<String, String> enMap = new HashMap<String, String>();
	private Map<String, String> arMap = new HashMap<String, String>();
	private Map<String, String> frMap = new HashMap<String, String>();
	private Map<String, String> itMap = new HashMap<String, String>();
	private Map<String, String> aeMap = new HashMap<String, String>();
	private Map<String, String> amMap = new HashMap<String, String>();
	private Map<String, String> akMap = new HashMap<String, String>();
	private Map<String, String> anMap = new HashMap<String, String>();
	private Map<String, String> bsMap = new HashMap<String, String>();
	
	private List<String> locales = new ArrayList<>();


	@Before
	public void init() {
		for (int i = 0 ; i < 100000 ; i++) {
			heMap.put("test" + i, LocaleEnum.he.toString() + i);
			enMap.put("test" + i, LocaleEnum.en.toString() + i);
			arMap.put("test" + i, LocaleEnum.ar.toString() + i);
			frMap.put("test" + i, LocaleEnum.fr.toString() + i);
			itMap.put("test" + i, LocaleEnum.it.toString() + i);
			bsMap.put("test" + i, LocaleEnum.bs.toString() + i);
			aeMap.put("test" + i, LocaleEnum.ae.toString() + i);
			akMap.put("test" + i, LocaleEnum.ak.toString() + i);
			amMap.put("test" + i, LocaleEnum.am.toString() + i);
			anMap.put("test" + i, LocaleEnum.an.toString() + i);
		}

		resourceService = new ResourceServiceImpl();
		regularCacheServiceImp = new MockCacheServiceImp();
		resourceService.setCacheService(regularCacheServiceImp);
		regularCacheServiceImp.init();
		
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "he"), heMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "en"), enMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "ar"), arMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "fr"), frMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "it"), itMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "bs"), bsMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "ae"), aeMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "ak"), akMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "am"), amMap);
		regularCacheServiceImp.setMapValues(resourceService.getKey(SYSTEM, "an"), anMap);
		
		initLocales();
	}

	private void initLocales() {
		locales.add("he");
		locales.add("en");
		locales.add("ar");
		locales.add("fr");
		locales.add("it");
		locales.add("ae");
		locales.add("ak");
		locales.add("am");
		locales.add("an");
		locales.add("bs");
	}
	
	/**
	 * The Json should be created only in the first time or if the table was updated (Cache)
	 */
	@Test
	public void checkGetAllResources() {
		long startTime = System.nanoTime();
		resourceService.getAllResourcesBySystemAndLocales(SYSTEM, locales);
		long duration = System.nanoTime() - startTime;
		double d = duration / 1000000;
		System.out.println("Get all resources By system and locales, only in the first time or if the table was updated (Regular Cache). Timing: " + d + "ms. Count of resources: 1000000.");
	}
	
	/**
	 * "Get all resources by idents (resources were taken from the cache.
	 */
	@Test
	public void checkGetAllResourcesByIdents() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(Arrays.asList("test0", "test9", "test4"));
		long startTime = System.nanoTime();
		resourceService.findResourcesByLocalesAndIdentsAndSystem(locales, idents, SYSTEM);
		long duration = System.nanoTime() - startTime;
		double d = duration / 1000000;
		System.out.println("Get all resources by idents (resources were taken from the regular cache). Timing:" + d + "ms. Count of resources: 1000000.");
	}

}
