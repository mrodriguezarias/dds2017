package tp1;

import java.util.ArrayList;
import java.util.List;

import tp1.modelo.Empresa;
import tp1.modelo.repositorios.fuentes.FuenteDeLaEmpresa;

public class TestCompanySource implements FuenteDeLaEmpresa {

	@Override
	public List<Empresa> cargar() {
		List<Empresa> companies = new ArrayList<>();
		companies.add(new Empresa("TestCompany"));
		return companies;
	}
}