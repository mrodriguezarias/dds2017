package tp1.model;

import java.util.ArrayList;
import java.util.List;

public class IndicatorRepository {

	private IndicatorSource source;

	public List<Indicator> indicators;

	public IndicatorRepository(IndicatorSource source) {
		this.source = source;
		indicators = new ArrayList<>(source.load());
	}

	public List<Indicator> all() {

		return indicators;
	}
	
	public Indicator find(String name) {
		return indicators.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
	}

	public void add(Indicator indicator) {
		indicators.add(indicator);
		save();
	}

	public void replace(Indicator oldIndicator, Indicator newIndicator) {
		indicators.remove(oldIndicator);
		indicators.add(newIndicator);
		save();
	}

	public void remove(Indicator indicator) {
		indicators.remove(indicator);
		save();
	}

	private void save() {
		source.save(indicators);
	}

}
