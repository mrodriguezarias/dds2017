package tp1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonCompanySource implements CompanySource{
	
	public JsonCoder coder;
	
	public JsonCompanySource(String fileName) {
		coder = new JsonCoder(fileName, Metric.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> load() {
		List<Metric> metrics = (List<Metric>) coder.read();
		
		List<String> companyNames = metrics.stream().map(m -> m.getCompanyName()).distinct().collect(Collectors.toList());
		List<Company> companies = new ArrayList<>();
		
		companyNames.forEach(companyName -> {
			List<Metric> companyMetrics = metrics.stream().filter(m -> m.getCompanyName().equals(companyName))
					.collect(Collectors.toList());
			companies.add(new Company(companyName, companyMetrics));
		});
		
		return companies;
	}

}
