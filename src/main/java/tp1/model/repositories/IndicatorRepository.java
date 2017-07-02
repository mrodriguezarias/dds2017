package tp1.model.repositories;

import java.util.ArrayList;
import java.util.List;

import tp1.model.indicator.Indicator;
import tp1.model.repositories.sources.IndicatorSource;

public class IndicatorRepository {

	private IndicatorSource source;

	public List<String> nombres;
	public List<Indicator> indicators;

	public IndicatorRepository(IndicatorSource source) {
		this.source = source;
		nombres = source.obtenerNombres();
//		indicators = new ArrayList<>(source.load());
	}

	public void createIndicators(){ //perdon por esto, no se me ocurre otra cosa de momento
		indicators = new ArrayList<>(source.load());
	}
	public List<Indicator> all() {

		return indicators;
	}
	
	public Indicator find(String name) {
		return indicators.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
	}

	public void add(Indicator indicator) {
		nombres.add(indicator.getName());
		indicators.add(indicator);
		save();
	}

	public boolean existeIndicador(String nombre){
		return nombres.stream().anyMatch(n -> n.equals(nombre));
	}
	public void replace(Indicator oldIndicator, Indicator newIndicator) {
		indicators.remove(oldIndicator);
		nombres.remove(oldIndicator.getName());
		indicators.add(newIndicator);
		nombres.add(newIndicator.getName());
		save();
	}

	public void remove(Indicator indicator) {
		nombres.remove(indicator.getName());
		indicators.remove(indicator);
		save();
	}

	private void save() {
		source.save(indicators);
	}

}
