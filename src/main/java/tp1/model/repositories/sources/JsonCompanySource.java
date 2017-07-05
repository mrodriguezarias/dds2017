package tp1.model.repositories.sources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import tp1.model.Empresa;
import tp1.model.JsonCoder;
import tp1.model.Cuenta;

public class JsonCompanySource implements CompanySource{
	
	public JsonCoder coder;
	
	public JsonCompanySource(String fileName) {
		coder = new JsonCoder(fileName, Cuenta.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> load() {
		List<Cuenta> metrics = (List<Cuenta>) coder.read();
		
		List<String> companyNames = metrics.stream().map(m -> m.getCompanyName()).distinct().collect(Collectors.toList());
		List<Empresa> companies = new ArrayList<>();
		
		companyNames.forEach(companyName -> {
			List<Cuenta> companyMetrics = metrics.stream().filter(m -> m.getCompanyName().equals(companyName))
					.collect(Collectors.toList());
			companies.add(new Empresa(companyName, companyMetrics));
		});
		return companies;
	}

}
