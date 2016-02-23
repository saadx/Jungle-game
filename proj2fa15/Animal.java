package proj2fa15;

import java.util.*;

/**
 * <p>Title: Project#2 </p>
 * <p>Description: Animals class contains three instance variables to store type, gender
 * 					and strength. Contains a parameterized constructor. Also has
 * 					accessor and mutator methods and a toString method. </p>
 * @author Saad Ahmad
 */

public class Animal 
{
	//instance variables
	private String type; 			//stores if the animal is a bear or a fish
	private boolean gender;			//stores the gender of the animal
	private float strength;			//stores the strength of the animal
	
	
	/**
	 * parameterized constructor -
	 */
	public Animal (String aType)
	{
		type = aType;
		
		//generates 0 or 1 randomly and gender of animal is decided on the generated number
		Random generator = new Random();
		int gen = generator.nextInt(2);
		if (gen == 0)
			gender = true; //male
		else
			gender = false; //female
		
		//strength randomly decide between 1 to 10
		float st =(int)(generator.nextFloat()*10)+1;
		strength = st;
	}
	
	public String getType()
	{
		return type;
	}
	
	public boolean getGender()
	{
		return gender;
	}
	
	public float getStrength()
	{
		return strength;
	}
	
	public void setType(String aType)
	{
		type = aType;
	}
	
	public void setGender(boolean gen)
	{
		gender = gen;
	}
	
	public void setStrength(int st)
	{
		strength = st;
	}
	
	public String toString()
	{
		String gen = "";
		if (gender == true)
			gen = "Male";
		else
			gen = "Female";
		
		return " " + type + "   " + gen + "   " + strength;
	}
}
