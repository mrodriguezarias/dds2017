package tp1.modelo.repositorios.fuentes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tp1.modelo.Cuenta;
import tp1.modelo.Empresa;
import tp1.modelo.CodificadorJson;

public class FuenteJsonDeEmpresa implements FuenteDeEmpresa{
	
	public CodificadorJson coder;
	
	public FuenteJsonDeEmpresa(String fileName) {
		coder = new CodificadorJson(fileName, Cuenta.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> cargar() {
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
