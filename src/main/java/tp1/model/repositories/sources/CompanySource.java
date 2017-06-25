package tp1.model.repositories.sources;

import java.util.List;

import tp1.model.Company;

public interface CompanySource {

	public List<Company> load();
}