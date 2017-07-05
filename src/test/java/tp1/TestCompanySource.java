package tp1;

import java.util.ArrayList;
import java.util.List;

import tp1.model.Empresa;
import tp1.model.repositories.sources.CompanySource;

public class TestCompanySource implements CompanySource {

	@Override
	public List<Empresa> load() {
		List<Empresa> companies = new ArrayList<>();
		companies.add(new Empresa("TestCompany"));
		return companies;
	}
}