package tp1.model.repositories.sources;

import java.util.List;

import tp1.model.indicator.Indicator;

public interface IndicatorSource {

	public List<Indicator> load();
	
	public void save(List<Indicator> indicators);
}
