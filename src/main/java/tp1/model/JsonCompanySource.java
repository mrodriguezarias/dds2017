package tp1.model;

import java.util.List;

public class JsonCompanySource implements CompanySource{
	
	public JsonCoder coder;
	
	
	public JsonCompanySource(String fileName){
		coder = new JsonCoder(fileName, Company.class );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> load() {
		
		return (List<Company>) coder.read();
	}

}
