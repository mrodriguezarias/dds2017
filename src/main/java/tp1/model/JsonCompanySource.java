package tp1.model;

import java.util.List;

public class JsonCompanySource implements CompanySource{
	
	public JsonCoder coder;
	
	
	public void init(String fileName){
		
		coder = new JsonCoder(fileName, Company.class );
	}
	
	@Override
	public List<Company> load() {
		
		return coder.read();
	}

}
