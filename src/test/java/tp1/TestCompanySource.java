package tp1;

import java.util.ArrayList;
import java.util.List;

import tp1.model.Company;
import tp1.model.repositories.sources.CompanySource;

public class TestCompanySource implements CompanySource {

	@Override
	public List<Company> load() {
		List<Company> companies = new ArrayList<>();
		companies.add(new Company("TestCompany"));
		return companies;
	}
}