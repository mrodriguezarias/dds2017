package tp1.model;

import java.util.List;
import tp1.model.Indicator;

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
