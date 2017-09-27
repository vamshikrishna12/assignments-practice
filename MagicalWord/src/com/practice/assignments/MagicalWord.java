package com.practice.assignments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MagicalWord {

	public static void main(String[] args) {
		String input = "";
		List<Integer> asciiNumbers = new ArrayList<Integer>();
		int nearestPrimeAscii = 0;
		String output = "";

		System.out.println("Enter the input magical Word : ");
		Scanner inputReader = new Scanner(System.in);
		input = inputReader.nextLine();
		System.out.println("-----------***********------------- ");
		System.out.println("Input string provided : "+input);

		asciiNumbers = MagicalWord.findaAsciiNumbers(input);
		for (Iterator<Integer> iterator = asciiNumbers.iterator(); iterator.hasNext();) {
			Integer asciiInt = (Integer) iterator.next();
			nearestPrimeAscii = MagicalWord.findNearestPrime(asciiInt);
			output = output + Character.toString((char)nearestPrimeAscii);			
		}
		System.out.println("Magical output word : "+output);
		
	}

	private static List<Integer> findaAsciiNumbers(String input) {
		Integer asciiInput = 0;
		List<Integer> asciiList = new ArrayList<Integer>();

		for (int i = 0; i < input.length(); i++) {
			asciiInput = (int)input.charAt(i);
			asciiList.add(asciiInput);
		}	
		return asciiList;
	}

	private static int findNearestPrime(int asciiNumber) {
		int lowerPrime = asciiNumber;
		int upperPrime = asciiNumber;
		int flag_upperPrime = 0;
		int flag_lowerPrime = 0;
		int lowerPrimediff = 0;
		int upperPrimediff = 0;
		

		//calculate nearest lower prime
		while(lowerPrime > 0){
			flag_lowerPrime = 0;
			for(int i = 2;i < lowerPrime ; i++){
				if(lowerPrime % i == 0){
					flag_lowerPrime = 1;
					break;
				}	
			}
			if(flag_lowerPrime == 0){
				break;
			}
			lowerPrime--;
		}
		
		//calculate nearest upper prime
		while(upperPrime > 0){
			flag_upperPrime = 0;
			for(int j = 2;j < upperPrime ; j++){
				if (upperPrime % j == 0){
					flag_upperPrime = 1;
					break;
				}
			}
			if(flag_upperPrime == 0){
				break;
			}
			upperPrime++;
		}
		
		lowerPrimediff = asciiNumber - lowerPrime;
		
		upperPrimediff = upperPrime - asciiNumber;
		
		if((lowerPrimediff < upperPrimediff) || (lowerPrimediff == upperPrimediff)){
			//System.out.println(lowerPrime);
			return lowerPrime;
		}
		if(upperPrimediff < lowerPrimediff){
			//System.out.println(upperPrime);
			return upperPrime;
		}
		return 0;
	}
}

