package com.practice.assignments;

import java.util.Scanner;

public class PalendromeString {

	public static void main(String[] args) {
		String input = "";
		String output = "";
		
				
		//Read input from user
		System.out.println("Enter the string that needs to checked");
		Scanner inputReader = new Scanner(System.in);
		input = inputReader.nextLine();
		System.out.println("Input provided : " + input);
		
		//Swap the first and last characters
		for(int i = input.length()-1; i>=0; i--){
			output = output + input.charAt(i);
		}
		System.out.println("Output generated is : "+ output);
		
		if(input.equals(output)){
			System.out.println("String is palendrome");
		}
		else{
			System.out.println("String is not palendrome");
		}
	}

}

