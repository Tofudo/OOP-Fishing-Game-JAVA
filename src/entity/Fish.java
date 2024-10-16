package entity;


public class Fish{
	
	private int max = 100;
	private int min = 1;
	private int num = (int)Math.floor(Math.random() * (max - min + 1) + min); //generates number from minimum to max
	
	//fishes used
	String fish1 = "Sea Bass!";
	private int fish1Value = 5;
	
	String fish2 = "Tuna!";
	private int fish2Value = 10; 
	
	String fish3 = "Black Marlin!";
	private int fish3Value = 20;
	//
	
	public String fishName;
	
	private int passValue = 0;
	
	public void genFish() { //generates fish equivalent to the number generated
		
		if(num < 70) {
			
			fishName = fish1;
			passValue = fish1Value;
			
		}
		
		if(num >= 70 && num < 90) {
			
			fishName = fish2;
			passValue = fish2Value;
		}
		
		if(num >= 90) {
			
			fishName = fish3;
			passValue = fish3Value;
		}
		
	}
	
	public int passFishValue() { //passes fish value
		
		int value = 0;
		
		value = passValue;
		
		return value;
		
	}
	
	public String passFishName() { //passes fish name
		
		String name = fishName;
		
		return name;
		
	}
	

}
