package tp1.modelo.repositorios.fuentes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tp1.modelo.Empresa;
import tp1.modelo.CodificadorJson;
import tp1.modelo.Cuenta;

public class FuenteJsonDeLaEmpresa implements FuenteDeEmpresa{
	
	public CodificadorJson codificador;
	
	public FuenteJsonDeLaEmpresa(String nombreDeArchivo) {
		codificador = new CodificadorJson(nombreDeArchivo, Cuenta.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> cargar() {
		List<Cuenta> cuentas = (List<Cuenta>) codificador.leer();
		
		List<String> nombresDeLasEmpresas = cuentas.stream().map(m -> m.obtenerNombreDeLaEmpresa()).distinct().collect(Collectors.toList());
		List<Empresa> empresas = new ArrayList<>();
		
		nombresDeLasEmpresas.forEach(nombreDeLaEmpresa -> {
			List<Cuenta> cuentasDeLaEmpresa = cuentas.stream().filter(m -> m.obtenerNombreDeLaEmpresa().equals(nombreDeLaEmpresa))
					.collect(Collectors.toList());
			empresas.add(new Empresa(nombreDeLaEmpresa, cuentasDeLaEmpresa));
		});
		
		return empresas;
	}

}
