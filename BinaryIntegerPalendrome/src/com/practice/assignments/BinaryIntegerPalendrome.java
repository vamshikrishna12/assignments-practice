package com.practice.assignments;

import java.util.Scanner;

public class BinaryIntegerPalendrome {

	public static void main(String[] args) {
		Integer input = 0;
		String binaryInput = "";
		String binaryOutput = "";

		Scanner inputReader = new Scanner(System.in);
		System.out.println("Enter the input number : ");
		input = inputReader.nextInt();

		System.out.println("****************----------------**************");
		System.out.println("Input provided : "+input);

		binaryInput = input.toBinaryString(input);
		System.out.println("Binary format of input is : "+binaryInput);

		for (int i = binaryInput.length()-1; i>=0; i--) {
			char c = binaryInput.charAt(i);
			binaryOutput = binaryOutput + c;
		}

		System.out.println("Binary format of output generated  is : "+binaryOutput);
		
		if(binaryInput.equals(binaryOutput)){
			System.out.println("YES - Binary format of intege is palendrome");
		}
		else{
			System.out.println("NO  - Binary format of intege is palendrome");
		}
	}

}

