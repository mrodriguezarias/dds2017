package tp1;

import java.util.ArrayList;
import java.util.List;

import tp1.model.Indicator;
import tp1.model.IndicatorSource;

public class TestIndicatorSource implements IndicatorSource {

	@Override
	public List<Indicator> load() {
		return new ArrayList<>();
	}

	@Override
	public void save(List<Indicator> indicators) {
		
	}
}