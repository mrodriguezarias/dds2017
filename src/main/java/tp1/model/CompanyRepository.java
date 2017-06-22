package tp1.model;

import java.util.List;

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
