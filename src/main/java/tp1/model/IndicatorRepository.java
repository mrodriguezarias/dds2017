package tp1.model;

import java.util.List;

public class IndicatorRepository {
	
	private IndicatorSource source;
	
	public List<Indicator> indicators;
	
	public IndicatorRepository(IndicatorSource source){
		
		this.source = source;
		indicators = this.source.load();
		
	}
	
	public List<Indicator> all(){
		
		return indicators;
	}
	
	public void add(Indicator indicator){
		
		indicators.add(indicator);
		this.save();
	}
	
	public void replace(Indicator viejo, Indicator nuevo){
		
		indicators.remove(viejo);
		indicators.add(nuevo);	
		this.save();
	}
	
	public void remove(Indicator indicator){
		
		indicators.remove(indicator);
		this.save();
	}
	
	private void save(){
		
		source.save(indicators);
	}

}
