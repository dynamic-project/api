package product.domain;

public class Product {
	
	public String company;
	public String composition;
	public String mrp;
	public String name;
	public String pack;
	public String sideEffect;
	public String substitute;
	
	public Product() {
		
	}
    
    public Product(String company, String composition, String mrp, String name, 
    		String pack, String sideEffect, String substitute) {
    	this.company = company;
    	this.composition = composition;
    	this.mrp = mrp;
    	this.name = name;
    	this.pack = pack;
    	this.sideEffect = sideEffect;
    	this.substitute = substitute;
    }
    
}
