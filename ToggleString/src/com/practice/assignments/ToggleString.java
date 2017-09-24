package com.practice.assignments;

import java.util.Scanner;

public class ToggleString {

	public static void main(String[] args) {
		String input = "";
		String output = "";
		
		//Read the input from user
		System.out.println("Enter the number to be toggled");
		Scanner inputRead = new Scanner(System.in);
		input = inputRead.nextLine();
		System.out.println("Input provided : "+ input);
		
		//Perform toggle action
		for(int i = 0; i < input.length(); i++){
			Character toggleChar = input.charAt(i);
			boolean caseCheck = Character.isUpperCase(toggleChar);
			if(caseCheck){
				output = output + Character.toLowerCase(toggleChar);
			}
			else{
				output = output + Character.toUpperCase(toggleChar);
			}
		}
		System.out.println("Toggled Output is : "+ output);

	}

}
