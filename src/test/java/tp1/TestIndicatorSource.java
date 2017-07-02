package tp1;

import java.util.ArrayList;
import java.util.List;

import tp1.model.indicator.Indicator;
import tp1.model.repositories.sources.IndicatorSource;

public class TestIndicatorSource implements IndicatorSource {

	@Override
	public List<Indicator> load() {
		return new ArrayList<>();
	}

	@Override
	public void save(List<Indicator> indicators) {
		
	}

	@Override
	public List<String> obtenerNombres() {
		// TODO Auto-generated method stub
		return null;
	}
}