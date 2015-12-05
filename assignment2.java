import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.NullPointerException;

import java.io.File;


public class assignment2 {

	/* initial vars for comparisons and swaps */
	private static int comparisons = 0;
	private static int swaps = 0;

	private static boolean progressions = true;

	/* ******************** 

		HELPER FUNCTIONS FOR SORTS AND SEARCHES

					****************** */

	/* 
		* formats string array;
			* eliminates duplicates
			* eliminates punctuation 
			* making everything lower case to solve the issue of 'A' vs 'a' */

	public static String[] format(ArrayList<String> input) {

		String[] inputArr = new String[input.size()];
		inputArr = input.toArray(inputArr);	

		for (int i = 0; i < inputArr.length; i++) {
			inputArr[i].replaceAll("[^a-zA-Z ]", "");
			inputArr[i] = inputArr[i].toLowerCase();
			
		}
		return uniq(inputArr);
	}

	public static String format(String input) {
		input.replaceAll("[^a-zA-Z ]", "");
		return input;
	}

	/* 
		* converts an arraylist of Integer objects into an array of primative integers
		* used to convert ArrayList used to gather indexes in search functions to a primative array 
		*/
	
	public static int[] intConvert(ArrayList<Integer> arr) {
		int[] output = new int[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			output[i] = arr.get(i);	
		}
		return output;
	}

	/* 
		* stringify() - used to print an array in a single line to stdout
		* this is where progression lines in sorts are created 
		*/
	public static void stringify(String[] arr) {
		for (int g = 0; g < arr.length; g++) {
			System.out.print(arr[g] + " ");
		}
		System.out.println("");
	}

	/*
		* swap() - swaps elements arr[index1] and arr[index2]
		* used in selection, quick, bubble sorts
		*/
	public static void swap(String[] arr, int index1, int index2) {
		swaps++;
		String temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}

	/*
		* uniq() - eliminates duplicates from array 
		* converts to HashSet then back to array type
		* returns a String[] instead of a void so the new array won't have any null values to deal with
		*/
	public static String[] uniq(String[] arr) {
		HashSet<String> uniques = new HashSet<String>();
		for (int i = 0; i < arr.length; i++) {
			uniques.add(arr[i]);
		}	
		return uniques.toArray(arr);
	}


	/* ********************
		
		SORTS:
			selection(), insertion(), bubble(), merge(), quick()

							*********************** */


	/*
		* selection() - sorts Strings by finding the correct value iteratively for each spot
		*/

	public static void selection(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int smallest = i;
			for (int j = i + 1; j < arr.length; j++) {
				comparisons++;	
				if (arr[smallest].compareTo(arr[j]) > 0) {
					smallest = j;
				}
			}

			swap(arr, smallest, i);

			if (progressions) 
				stringify(arr);
		}

	}

	/* 
		* insertion() - sorts Strings by finding the correct spot iteratively for each value 
		*/

	public static void insertion(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			String temp  = arr[i];
			int j = i;
			while (j > 0 && temp.compareTo(arr[j - 1]) < 0) {
				arr[j] = arr[j - 1];
				j--;
			}
			arr[j] = temp;
			if (progressions) 
				stringify(arr);
		}
	}

		
	/*
		* mergesort() - sorts Strings by sorting arrays within the main array 
		*/			

	//user-facing method

	/* mergesort derived from 'Cracking the Coding Interview' vol 6 by Gayle McDowell */
	public static void mergesort(String[] arr) {
		String[] helper = new String[arr.length];
		mergesort(arr, helper, 0, arr.length - 1);
	}

	/* breaks down arr[] along midpoint, then recursively sorts the new blocks */ 
	public static void mergesort(String[] arr, String[] helper, int low, int high) {

		if (low < high) {
			int mid = (low + high) / 2;
			mergesort(arr, helper, low, mid); //sorting left half
			mergesort(arr, helper, mid+1, high); //sorting right half
			merge(arr, helper, low, mid, high);
		}
	}

	/* combines two blocks together */
	public static void merge(String[] arr, String[] helper, int low, int mid, int high) {
		//copying all of arr[] into helper
		for (int i = low; i <= high; i++) {
			helper[i] = arr[i];
		}

		int helperLeft = low;
		int helperRight = mid + 1;
		int current = low;

		//looping through helper array to copy into original arr
		while (helperLeft <= mid && helperRight <= high) {
			comparisons++;
			if (helper[helperRight].compareTo(helper[helperLeft]) > -1) {
				arr[current] = helper[helperLeft];
				helperLeft++;
			}

			else {
				arr[current] = helper[helperRight];
				helperRight++;
			}

			current++;
		}

		/* copying rest of left side into the target array */
		int remaining = mid - helperLeft;
		for (int i = 0; i <= remaining; i++) {
			arr[current + i] = helper[helperLeft + i];
		}

		if (progressions) 
			stringify(arr);
			
	}	

	//user facing array
	public static void quicksort(String[] arr) {
		quicksort(arr, 0, arr.length - 1);
	}


	/* based off of http://ppd.fnal.gov/experiments/cdms/old_files/software/net/dist/sort.java 

		* i used a government site because that means it's efficient and functional */
	private static void quicksort(String[] arr, int low, int high) {

		if (progressions)
			stringify(arr);
		
		if (low < high) {
			//part as in 'partition'
			int part = partition(arr, low, high);
			if (part == high) {
				part--;
			}
			quicksort(arr, low, part);
			quicksort(arr, part+1, high);
		}
  	}

	/* breaking down arr[] and re-organizing it around a pivot point, in this case the first value */
	private static int partition (String[] arr, int low, int high) {
    		String pivot = arr[low];
   
		/* i'm just begging for an infinite loop here aren't I */ 
    		while (true) {
      			while (arr[high].compareTo(pivot) >= 0 && low < high) {
				comparisons++;
				high--;
      			}
      			while (arr[low].compareTo(pivot) < 0 && low < high) {
				comparisons++;
				low++;
      			}
      			if (low < high) {
				swap(arr, low, high);
      			}
      			else {
				return high;
			}
    		}
  	} 

	/* got help from http://www.kriblog.com/j2se/util/various-bubble-sort-example-in-java-using-string-array-arraylist-linked-list-recursive.html */

	public static void bubble(String[] arr) {
		for(int i = arr.length-1; i >= 0; i--) {
    			for(int j = 0; j < i; j++) {
				comparisons++;
        			if(arr[j].compareTo(arr[j + 1]) > -1) {
					swap(arr, j, i);
					stringify(arr);
        			}
    			}
		}
	}

	/* two linear() methods so you can search a specific section of a sorted array or the whole thing */
	public static int[] linear(String[] arr, String key) {
		return linear(arr, key, 0, arr.length - 1);
	}

	public static int[] linear(String[] arr, String key, int start, int end) {
		/* 
		   * array of indexes for words that appear
		   * if the word 'sandwich' appears in spots 3, 6, and 9, indexes[] will be {3, 6, 9}
		*/

		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (int i = start; i <= end; i++) {
			comparisons++;
			if (arr[i].compareTo(key) == 0) {
				indexes.add(i);
			}
		}

		return intConvert(indexes); 

	}

	public static int[] quadratic(String[] arr, String key) {
		int jumpSize = (int) Math.sqrt(arr.length);

		int current = 0;
		comparisons++;

		//while the current arr[spot] is alphabetically less than the key
		while (key.compareTo(arr[current]) > -1 && (current + jumpSize) < arr.length) {
			comparisons++;

			//looking ahead: if the arr[next jump spot] is alphabetically greater than the key, doing linear search
			if (key.compareTo(arr[jumpSize + current]) < 1) {
				return linear(arr, key, current, arr.length - 1);
			}

			//if looking ahead keeps us alphabetically less than the key, iterate again	
			else {
				current += jumpSize;
			}

		}
	
		//if nothing has been returned yet, then return an array with -1, or not found
		int[] output = new int[1];
		output[0] = -1;
		return output;

	}

	/* user-facing function */
	public static int[] binary (String[] arr, String key) {
		return binary(arr, key, 0, arr.length - 1);
	}

	public static int[] binary(String[] arr, String key, int low, int high) {
		int mid = (low + high) / 2;
		ArrayList<Integer> indexes = new ArrayList<Integer>();

		comparisons++;
		int comparison = arr[mid].compareTo(key);	

		if (high < low) {
			indexes.add(-1);
			return intConvert(indexes);
		}

		if (comparison == 0) {
			while (comparison == 0 && mid < arr.length) {
				indexes.add(mid);
				comparison = arr[mid].compareTo(key);	
				mid++;
			}
			
			return intConvert(indexes);
		}

		if (comparison > 0) { //then arr[mid] is less than arr[key]
			return binary(arr, key, low, mid - 1);
		}

		else { //then arr[mid] is higher
			return binary(arr, key, mid + 1, high);
		}
					
	}
			
	public static String inputFilePath;

	public static void main(String args[]) throws FileNotFoundException, NoSuchElementException {

		/* this is the maximum number of words that can be sorted or searched */
		final int MAX_WORDS = 300000;

		Scanner input = new Scanner(System.in);
		boolean quit = false;

		/* using a regular expression to test for As2Small or As2Large, accounts for some mispellings or miscapitalizations */
		Pattern fileReg = Pattern.compile("large", Pattern.CASE_INSENSITIVE);

		ArrayList<String> rawWords = new ArrayList<String>();

		while (quit != true) {
			System.out.print("Quit? [y/N] ");
			String ynResponse = input.next();
			if (ynResponse.compareTo("y") == 0) {
				quit = true;
			}
			
			else {
				System.out.print("As2Small.txt or As2Large.txt? ");
				String inputFile = input.next();
				Matcher m = fileReg.matcher(inputFile);

				/* setting filePath to small file unless regex match compiles */
				String filePath = "As2Small.txt";
				if (m.find( )) {
					filePath = "As2Large.txt";
					progressions = false;
				}

				System.out.println("Reading from " + filePath);
				
				Scanner wordsFile = new Scanner(new File(filePath));

				/* reading from words file into words array */	
				for (int i = 0; wordsFile.hasNext(); i++) {
					rawWords.add( wordsFile.next() );
				}

				wordsFile.close();

				System.out.println("Read in " + rawWords.size() + " words");
				String[] words = format(rawWords);

				System.out.println("Word list created with " + words.length + " words");
			
				System.out.print("1 for sorting, 2 for searching: ");
				int sortOrSearch = input.nextInt();
		
				/* accounting for inputs that aren't 1 or 2 */	
				while (sortOrSearch < 1 || sortOrSearch > 2) {
					System.out.println("Unknown input found: " + sortOrSearch);
					System.out.print("1 for sorting, 2 for searching: ");
					sortOrSearch = input.nextInt();
				}

				/* if sorting */
				if (sortOrSearch == 1) {
					System.out.print("1 for quick, 2 for merge, 3 for bubble, 4 for insertion, 5 for selection: ");
					int sortType = input.nextInt();
					while (sortType > 5 || sortType < 1) {
						System.out.println("Unknown input received: " + sortType);
						System.out.print("1 for quick, 2 for merge, 3 for bubble, 4 for insertion, 5 for selection: ");
						sortType = input.nextInt();
					}

					if (sortType == 1) {
						quicksort(words);
					}

					else if (sortType == 2) {
						mergesort(words);
					}

					else if (sortType == 3) {
						bubble(words);
					}

					else if (sortType == 4) {
						insertion(words);
					}

					else if (sortType == 5) {
						selection(words);
					}
	
					if (progressions)
						stringify(words);
						
				}

				else { //searching
					System.out.print("Word to search for: ");
					String key = input.next();
					key = format(key);	
					
					System.out.print("1 for linear, 2 for binary, 3 for quadratic search: ");
					int searchType = input.nextInt();
					while (searchType > 3 || searchType < 1) {
						System.out.println("Unknown search type received: " + searchType);
						System.out.print("1 for linear, 2 for binary, 3 for quadratic search: ");
						searchType = input.nextInt();		
					}

					int[] results;
	

					long startTime = 0;
					long endTime = 0;

					//searches[seachType] gives the type
					String[] searches = {"", "linear", "binary", "quadratic"};

					if (searchType == 1) {
						comparisons = 0;
						swaps = 0;
						startTime = System.nanoTime();
						results = linear(words, key);
						endTime = System.nanoTime();
					}

					else if (searchType == 2) {
						mergesort(words);
						comparisons = 0;
						swaps = 0;
						startTime = System.nanoTime();
						results = binary(words, key);
						endTime = System.nanoTime();
					}
				
					else if (searchType == 3) {
						mergesort(words);
						comparisons = 0;
						swaps = 0;
						startTime = System.nanoTime();
						results = quadratic(words, key);
						endTime = System.nanoTime();
			 		}
					
					double milliseconds = (endTime - startTime) / 1e6;

					System.out.println(searches[searchType] + " finished in " + milliseconds + " milliseconds");
					System.out.println(comparisons + " comparisons");
					
				}	
			}
						
		
		}

	}
}

/* sources used:

http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJFileChooser.htm
http://stackoverflow.com/questions/16351875/jfilechooser-on-a-button-click

*/
