package tp1.model.repositories.sources;

import java.util.List;

import tp1.model.JsonCoder;
import tp1.model.indicator.Indicator;

public class JsonIndicatorSource implements IndicatorSource{
	
	JsonCoder coder;
	
	public JsonIndicatorSource(String fileName){
		coder = new JsonCoder(fileName, Indicator.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Indicator> load() {
		
		return (List<Indicator>) coder.read();
	}

	@Override
	public void save(List<Indicator> indicators) {
		
		coder.write(indicators);		
	}
	

}
