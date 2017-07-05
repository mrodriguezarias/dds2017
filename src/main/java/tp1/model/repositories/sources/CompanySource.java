package tp1.model.repositories.sources;

import java.util.List;

import tp1.model.Empresa;

public interface CompanySource {

	public List<Empresa> load();
}