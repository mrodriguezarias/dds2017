package tp1.model.repositories;

import java.util.List;

import tp1.model.Empresa;
import tp1.model.repositories.sources.CompanySource;

public class CompanyRepository {

	CompanySource source;
	
	List<Empresa> companies;
	
	public CompanyRepository(CompanySource source){
		
		this.source = source;
		companies = this.source.load();
	}
	
	public List<Empresa> all() {
		return companies;
	}
	
	public Empresa find(String name) {
		return companies.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
	}
	
}
