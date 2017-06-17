package tp1.model;

import java.util.List;
import java.util.stream.Collectors;

public class JsonCompanySource implements CompanySource{
	
	public JsonCoder coder;
	
	
	public JsonCompanySource(String fileName){
		coder = new JsonCoder(fileName, Metric.class );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> load() {
		
		List<Metric> metrics = (List<Metric>) coder.read();
		
		List<Company> companies = metrics.stream().map(m -> m.getCompanyName()).distinct()
		.map(name -> new Company(name)).collect(Collectors.toList());
		
		metrics.forEach(metric -> {
			Company company = companies.stream().filter(c -> c.getName().equals(metric.getCompanyName())).findFirst().get();
			company.addMetric(metric);
		});
		
		return companies;
	}

}
