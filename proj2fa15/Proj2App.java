package proj2fa15;

import java.io.*;
import java.util.*;

import javax.swing.*;

/**
 * <p>Title: Project#2 </p>
 * <p>Description: Creates a array of size given by user, populates half of it randomly
 * 				 	with random animal objects. Then selects one animal randomly to move one step
 * 					forward, backward or not move at all and then performs actions like if
 * 					there's a battle between same animals then stronger survives, if battle with
 * 					different animal then bear survives or if two different gender animals of same 
 * 					type come together then a new animal object is created and placed randomly
 * 					at an empty location</p>
 * @author Saad Ahmad.
 */

public class Proj2App 
{
	public static void main (String args[]) throws IOException
	{
		int rSize = 0;
		int c =0;
		boolean legitInput = false;

		while (!legitInput)
		{
			//to terminate the program after 3 invalid entries.
			if (c == 3)
			{
				JOptionPane.showMessageDialog(null, "3 unsuccessful attemps. Program terminating");
				System.exit(0);
			}

			String input = JOptionPane.showInputDialog("Please enter the size of the river"
					+ " in the range of 10 to 50.");

			if (input == null)
			{
				System.exit(0);
			}

			if (input.isEmpty())
			{
				JOptionPane.showMessageDialog(null, "No size entered.");
			}

			else
			{
				try
				{
					rSize = Integer.parseInt(input);
					if ((rSize >= 10) && (rSize <=50))
						legitInput = true;
					else
					{
						JOptionPane.showMessageDialog(null, "Invalid size entered. Try again.");
					}
				}

				catch (NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, "Only integer inputs accepted for size of river.");
				}
			}
			c++;
		}

		Animal[] river = new Animal[rSize];

		Random generator = new Random();
		for (int i=0; i<(rSize/2); i++)
		{	
			Animal a1 = null;
			int type = generator.nextInt(2);
			if (type == 0)
				a1 = new Bear();
			else
				a1 = new Fish();

			int location = generator.nextInt(rSize);
			while (river[location] != null)
				location = generator.nextInt(rSize);

			river[location] = a1;
		}

		//prints the initial state of the river
		String str = "";
		for (int i = 0; i < rSize; i++)
		{
			if (river[i] == null)
				str += (i+1) + " No Animal\n\n";
			else
				str += (i+1) + river[i].toString() + "\n\n";
		}
		System.out.println("Current state of the river: \n\n" + str);

		boolean allSame = false;
		while (!allSame)
		{
			//selects a random animal from the river[] to move
			int animalIndex = generator.nextInt(rSize);
			while (river[animalIndex] == null)
				animalIndex = generator.nextInt(rSize);

			//variables to store the animal object adjacent to the selected animal
			//and to store the selected adjacent animal'a index
			Animal adjAnimal = null;
			int aIndex = -1;

			//random no. to decide in which direction the selected animal should move
			//0 for left movement and 1 for right movement and 2 for no movement. 
			int direction = generator.nextInt(3);
			if (direction == 0)
			{
				aIndex = animalIndex-1;
				System.out.println(river[animalIndex].getType() + " on location " + (animalIndex+1) + 
						" has chosen to move up one step.\n");
				if (aIndex == -1)
					System.out.println("Animal cannot move up. It is already on first position in the river."
							+ " All animals remain at their places.\n");
				else
				{
					adjAnimal = river [animalIndex-1];
				}
			}

			else if (direction == 1)
			{
				aIndex = animalIndex+1;
				System.out.println(river[animalIndex].getType() + " on location " + (animalIndex+1) + 
						" has chosen to move down one step.\n");
				if (aIndex == river.length)
					System.out.println("Animal cannot move down. It is already on last position in the river."
							+ " All animals remain at their places.\n");
				else
				{
					adjAnimal = river [animalIndex+1];
				}
			}

			else
				System.out.println(river[animalIndex].getType() + " on location " + (animalIndex+1) + 
						" is selected to move.\n" + "Animal chose not to move. All animals stay at their places.\n");

			if ((direction != 2) && (aIndex >= 0) && (aIndex != river.length))
			{
				//if the index adjacent to the selected animal has no animal
				//then the selected animal simply moves to that index
				if (river[aIndex] == null)
				{
					river[aIndex] = river[animalIndex];
					river[animalIndex] = null;
					System.out.println("Successfully moved to location " + (aIndex+1) + "\n\n");
				}

				//if the adjacent index has an animal then other movement conditions apply
				//if both animals are of same type and same gender then the animal with higher
				//strength survives.
				else if ((adjAnimal.getType()).equals(river[animalIndex].getType()))
				{
					System.out.print("Animal on location " + (aIndex+1) + " is of same type ");

					if (adjAnimal.getGender() == river[animalIndex].getGender())
					{
						String aInd = river[aIndex].getType();			//for printing 
						String anInd = river[animalIndex].getType();	//for printing
						System.out.print("and gender.\n");
						boolean st = (river[animalIndex].getStrength() > adjAnimal.getStrength());
						if (st == true)
						{
							river[aIndex] = river[animalIndex];
							river[animalIndex] = null;
							System.out.println("But" + anInd + " on location " + (animalIndex+1)
									+ " is stronger than " + aInd + " on location " + (aIndex+1)
									+ " therefore stronger animal moved and replaced the weaker one.");
						}

						else if (st == false)
						{
							river[animalIndex] = null;
							System.out.println(aInd + " on location " + (aIndex+1)
									+ " is stronnger than " + anInd + " on location " + (animalIndex+1)
									+ " therefore stronger animal moved and replaced the weaker one.");
						}

						else
						{
							System.out.println("Both animals have same strengths too, therefore no one moved"
									+ "as all attributes of both animals are same.");
						}
					}

					//if animals are of same type but different gender then a new instance
					//of the same animal is created and stored at a random empty position
					//in the river array.
					else
					{
						Animal a2;
						if (river[animalIndex].getType().equals("Bear"))
							a2 = new Bear();
						else
							a2 = new Fish();

						boolean anyNull = false;
						for (int i = 0; i < rSize; i++)
						{
							if (river[i] == null)
							{
								anyNull = true;
							}
						}

						//to check if there is any null location to place the new animal

						if (anyNull == true)
						{
							int location = generator.nextInt(rSize);
							while (river[location] != null)
								location = generator.nextInt(rSize);

							river[location] = a2;

							System.out.println("but different gender, therefore a new " + river[location].getType()
									+ " is born and placed at location: " + (location+1));
						}

						else
						{
							System.out.println("but different gender, but new animal cannot be born as there is"
									+ "no empty place in the river to place it.");
						}
					}
				}

				//if animals are of different type then the bear replaces the fish
				else
				{
					if(river[animalIndex].getType().equals("Bear"))
					{
						river[aIndex] = river[animalIndex];
						river[animalIndex] = null;
					}
					else
					{
						river[animalIndex] = river[aIndex];
						river[aIndex] = null;
					}

					System.out.println("Bear on location " + (animalIndex+1) + " moved to location "
							+ (aIndex+1) + " by replacing the fish.\n");
				}

			}

			//prints the current state of the river
			String str2 = "";
			for (int i = 0; i < rSize; i++)
			{
				if (river[i] == null)
					str2 += (i+1) + " No Animal\n\n";
				else
					str2 += (i+1) + river[i].toString() + "\n\n";
			}
			System.out.println("Current state of the river: \n\n" + str2);
			System.out.println("\n------------------------------------------------------------------------------\n");

			int totAnimals = 0;
			for (int j = 0; j < rSize; j++)
			{
				if (river[j] != null)
					totAnimals++;
			}

			//checks if all the remaining animals are complete identical or if river is full
			//if any of the condition is true then reverts the value of iteration test variable
			int noOfSameAnimals = 1;
			int k = 0;
			while (river[k] == null)
			{
				k++;
			}
			for (int m = (k+1); m < rSize; m++)
			{
				if (river[m] != null)
				{
					if (river[k].getType().equals(river[m].getType()))
					{
						if (river[k].getGender() == river[m].getGender())
						{
							if (river[k].getStrength() == river[m].getStrength())
							{
								noOfSameAnimals++;
							}
						}
					}
				}
			}

			if ((noOfSameAnimals == totAnimals) || (totAnimals == river.length))
			{
				allSame = true;
				System.out.println("Game over");
			}
		}
	}
}

