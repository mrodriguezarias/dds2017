package tp1;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import tp1.model.Database;
import tp1.model.Metric;

public class TestMetrics {
	
	private static List<Metric> metrics;
	
	final private static int NUM_COMPANIES = 5;
	final private static int NUM_METRIC_TYPES = 2;
	final private static int NUM_PERIODS = 5;
	
	@BeforeClass
	public static void setUp() {
		metrics = Database.getInstance().getMetrics();
	}
	
	@Test
	public void testList() {
		assertFalse(metrics.isEmpty());
		assertEquals(NUM_COMPANIES * NUM_METRIC_TYPES * NUM_PERIODS, metrics.size());
		metrics.forEach(metric -> assertNotNull(metric));
	}
	
	@Test
	public void testApple() {
		List<Metric> apple = Util.filterList(metrics, metric -> metric.getCompany().equals("AAPL"));
		assertEquals(NUM_METRIC_TYPES * NUM_PERIODS, apple.size());
		
		List<Metric> ebitda = Util.filterList(apple, metric -> metric.getName().equals("EBITDA"));
		assertEquals(NUM_PERIODS, ebitda.size());
		assertEquals(2016, ebitda.get(0).getPeriod());
		assertEquals(71880000000l, ebitda.get(0).getValue());
		
		List<Metric> fcf = Util.filterList(apple, metric -> metric.getName().equals("FCF"));
		assertEquals(NUM_PERIODS, fcf.size());
		assertEquals(2012, fcf.get(NUM_PERIODS-1).getPeriod());
		assertEquals(42560000000l, fcf.get(NUM_PERIODS-1).getValue());
	}
	
	@Test
	public void testAmazon() {
		List<Metric> amazon = Util.filterList(metrics, metric -> metric.getCompany().equals("AMZN"));
		assertEquals(NUM_METRIC_TYPES * NUM_PERIODS, amazon.size());
		
		List<Metric> ebitda = Util.filterList(amazon, metric -> metric.getName().equals("EBITDA"));
		assertEquals(NUM_PERIODS, ebitda.size());
		assertEquals(2016, ebitda.get(0).getPeriod());
		assertEquals(12400000000l, ebitda.get(0).getValue());
		
		List<Metric> fcf = Util.filterList(amazon, metric -> metric.getName().equals("FCF"));
		assertEquals(NUM_PERIODS, fcf.size());
		assertEquals(2012, fcf.get(NUM_PERIODS-1).getPeriod());
		assertEquals(395000000l, fcf.get(NUM_PERIODS-1).getValue());
	}
	
	@Test
	public void testFacebook() {
		List<Metric> facebook = Util.filterList(metrics, metric -> metric.getCompany().equals("FB"));
		assertEquals(NUM_METRIC_TYPES * NUM_PERIODS, facebook.size());
		
		List<Metric> ebitda = Util.filterList(facebook, metric -> metric.getName().equals("EBITDA"));
		assertEquals(NUM_PERIODS, ebitda.size());
		assertEquals(2016, ebitda.get(0).getPeriod());
		assertEquals(14860000000l, ebitda.get(0).getValue());
		
		List<Metric> fcf = Util.filterList(facebook, metric -> metric.getName().equals("FCF"));
		assertEquals(NUM_PERIODS, fcf.size());
		assertEquals(2012, fcf.get(NUM_PERIODS-1).getPeriod());
		assertEquals(377000000l, fcf.get(NUM_PERIODS-1).getValue());
	}
	
	@Test
	public void testAlphabet() {
		List<Metric> alphabet = Util.filterList(metrics, metric -> metric.getCompany().equals("GOOGL"));
		assertEquals(NUM_METRIC_TYPES * NUM_PERIODS, alphabet.size());
		
		List<Metric> ebitda = Util.filterList(alphabet, metric -> metric.getName().equals("EBITDA"));
		assertEquals(NUM_PERIODS, ebitda.size());
		assertEquals(2016, ebitda.get(0).getPeriod());
		assertEquals(30290000000l, ebitda.get(0).getValue());
		
		List<Metric> fcf = Util.filterList(alphabet, metric -> metric.getName().equals("FCF"));
		assertEquals(NUM_PERIODS, fcf.size());
		assertEquals(2012, fcf.get(NUM_PERIODS-1).getPeriod());
		assertEquals(2780000000l, fcf.get(NUM_PERIODS-1).getValue());
	}
	
	@Test
	public void testTwitter() {
		List<Metric> twitter = Util.filterList(metrics, metric -> metric.getCompany().equals("TWTR"));
		assertEquals(NUM_METRIC_TYPES * NUM_PERIODS, twitter.size());
		
		List<Metric> ebitda = Util.filterList(twitter, metric -> metric.getName().equals("EBITDA"));
		assertEquals(NUM_PERIODS, ebitda.size());
		assertEquals(2016, ebitda.get(0).getPeriod());
		assertEquals(61310000l, ebitda.get(0).getValue());
		
		List<Metric> fcf = Util.filterList(twitter, metric -> metric.getName().equals("FCF"));
		assertEquals(NUM_PERIODS, fcf.size());
		assertEquals(2012, fcf.get(NUM_PERIODS-1).getPeriod());
		assertEquals(-78530000l, fcf.get(NUM_PERIODS-1).getValue());
	}

}
