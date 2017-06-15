package tp1.model;

import java.util.List;

public interface IndicatorSource {

	public List<Indicator> load();
	
	public void save(List<Indicator> indicators);
}
