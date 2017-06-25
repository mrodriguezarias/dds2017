package tp1.model.repositories;

import java.util.List;

import tp1.model.Company;
import tp1.model.repositories.sources.CompanySource;

public class CompanyRepository {

	CompanySource source;
	
	List<Company> companies;
	
	public CompanyRepository(CompanySource source){
		
		this.source = source;
		companies = this.source.load();
	}
	
	public List<Company> all() {
		return companies;
	}
	
	public Company find(String name) {
		return companies.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
	}
	
}
