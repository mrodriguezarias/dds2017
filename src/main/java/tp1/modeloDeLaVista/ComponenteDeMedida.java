package tp1.modeloDeLaVista;

import org.uqbar.commons.utils.Observable;

import tp1.modelo.Empresa;
import tp1.modelo.Medida;
import tp1.modelo.Cuenta;
import tp1.modelo.indicador.Indicador;

@Observable
public class ComponenteDeMedida {

	private Medida medida;
	private Empresa company;
	private short período;
	private String tipo;
	
	private ComponenteDeMedida(Medida medida, Empresa empresa, short período) {
		this.medida = medida;
		this.company = empresa;
		this.período = período;
	}
	
	public ComponenteDeMedida(Cuenta cuenta, Empresa empresa, short período) {
		this((Medida) cuenta, empresa, período);
		this.tipo = "Cuenta";
	}

	public ComponenteDeMedida(Indicador indicador, Empresa empresa, short período) {
		this((Medida) indicador, empresa, período);
		this.tipo = "Indicador";
	}
	
	public Medida getMedida() {
		return medida;
	}

	public short getPeríodo() {
		return período;
	}

	public String getTipo() {
		return tipo;
	}
	
	public String getNombreDeLaEmpresa() {
		return this.company.obtenerNombre();
	}
	
	public String getNombre() {
		return this.medida.obtenerNombre();
	}
	
	public String getDescripción() {
		return this.medida.obtenerDescripción();
	}
	
	public String getValor() {
		return cifrasSignificativas(this.medida.obtenerValor(company, período));
	}
	
	public String getValorCompleto() {
		return darFormatoANúmero(this.medida.obtenerValor(company, período));
	}
	
	public double getValorDoble() {
		return this.medida.obtenerValor(company, período);
	}
	
	private String cifrasSignificativas(double número) {
		String[] unidades = {"m", "M", "mM", "B", "mB", "T", "mT"};
		int índice = -1;
		while(Math.abs(número) > 1000) {
			índice++;
			número /= 1000;
		}
		String unidad = índice < 0 ? "" : " " + unidades[índice]; 
		return darFormatoANúmero(número) + unidad;
	}
	
	private String darFormatoANúmero(double número) {
		long valorEntero = (long) número;
		String formatoDeEntero = valorEntero / 1000 < 10 ? "%d" : "%,d";
		String entero = String.format(formatoDeEntero, valorEntero).replaceAll(",", "\u2009");
		
		String decimal = String.format("%f", Math.abs(número)).replaceFirst("[0-9]+\\.", ",")
				.replaceFirst(",([0-9]*?)0+$", ",$1").replaceFirst(",0*$", "");
		return entero + decimal;
	}
	
}
