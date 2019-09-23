//+-------------------------------+
//| Cadilnis Chau 		  |	      |		
//| Fibonacci -- Encryption       |
//|  				  |
//+-------------------------------+

#include <stdio.h> 
#include <stdlib.h> 
#include <string.h>
#include <ctype.h> 
#include <time.h>  
#include <math.h> 
#include "kw26.h"

//Return a pointer to a new, dynamically allocated Int40 struct that contains the
//result of adding the 40 digit integers represented by p and q
Int40 *kw26Add(Int40 *p, Int40 *q)
{
	int carryOver = 0, sum = 0; 
	if (p == NULL || q == NULL) //if either statement becomes true, return null 
		return NULL;			//back to main and don't waste time allocating space 
	 		
	Int40 *result = calloc(1, sizeof(Int40));  
	if (result == NULL) 
		return NULL;
	
	result->digits = calloc(MAX40, sizeof(Int40));
	if (result->digits == NULL) 
		return NULL;
		

	for (int v = 0; v < MAX40; v++)
	{
		sum = p->digits[v] + q->digits[v] + carryOver; 

		if(sum < 16)
		{
			carryOver = 0;
			result->digits[v] = sum; 
		}

		if(sum >= 16 && sum <= 31) 
		{
			carryOver = 1; //set carry to 1 
			result->digits[v] = sum % 16; 
		}

		if(sum >= 32) 
		{
			carryOver = 2; //set carry to 2 
			result->digits[v] = sum % 16; 
		}
	}		

	return result;  	
}

//if you love your RAM then set it free(); 
Int40 *kw26Destroyer(Int40 *p)
{
	if (p == NULL)
		return NULL; 

	free(p->digits);  
	free(p);

	return NULL; 
}


Int40 *parseString(char *str)
{ 
	// if nothing in the str, just send null back 
	// won't have to go through all the steps below 
	if (str == NULL)
		return NULL; 
	
	// padding with a char "0" before any conversion takes places
	// strcmp has a unique feature that returns a valid integer
	// if it does indeed return an int 0 in the comparison
	// we know to just insert the "0" and add the padding  
	if(strcmp(str, "") == 0)
		return parseString("0");  

	int len;
	int x, j, i = 0; 
	int *temp = calloc(MAX40, sizeof(int)); 
	if(temp == NULL)
		return NULL; 

	Int40 *result = calloc(1, sizeof(Int40)); 
	if (result == NULL) 
		return NULL; 

	len = strlen(str);
	
	result->digits = calloc(MAX40, sizeof(int)); 
	if (result->digits == NULL)
		return NULL; 

	// if the length of the string is great than or equal to 40
	// scan through the string and convert accordingly 
	if(len >= MAX40)
	{
		for(i = 0; i < 40; i++)
		{
			if(i == 40)
				break;
	
			if(isdigit(str[i]))
				temp[i] = str[i] - '0';
	
			if(str[i] >= 'a' && str[i] <= 'z' || str[i] >= 'A' && str[i] <= 'Z') 
				temp[i] = (str[i]) - 'a' + 10;
		}

		for(j = 0, x = 39 ; j < 40; j++, x--)
			result->digits[j] = temp[x];
	}

	// set the condition in case it ever reaches less than 40 characters 
	// perform the same process as before 
	if(len < 40)
	{
		fprintf(stderr, "Less than 40 characters! Padding zeroes.\n");
		for(i = 0; i < len; i++)
		{
			if(isdigit(str[i]))
				temp[i] = str[i] - '0';

			if(str[i] >= 'a' && str[i] <= 'z' || str[i] >= 'A' && str[i] <= 'Z')
				temp[i] = (str[i]) - 'a' + 10;
		}
	
		//reverse the storing 
		for(j = 0, x = len - 1; j < len, x >= 0; j++, x--)
			result->digits[j] = temp[x];

		for(; j < 40; j++)
			result->digits[j] = 0;
	}

	free(temp);
	return result;
}

//make iterative solution that runs O(n) times and return a pointer to Int40 struct 
//pointer must contain F(n). prevent mem leaks b4 returning from this function
Int40 *fibKw26(int n, Int40 *first, Int40 *second)
{
	Int40 *sum;
	if(sum == NULL)
		return NULL; 

	if(n < 2)
		return fibKw26(n, first, second);

	if(n > 2)
	{
		//loop through the sequence 'n' times
		for (int counter = 1; counter < n; counter++)  
		{
			sum = kw26Add(first, second); //add first and second using the function  

			first = second; //*swap*
			second = sum; //*swap*
		}
	}

	kw26Destroyer(first);
	return sum; 
}


Int40 *encrypt(Int40 *key, Int40 *inputText)
{
	int i = 0, j, x, num = 0;
	Int40 *result = calloc(1, sizeof(Int40));
	if (result == NULL)
		return NULL; 

	result->digits = calloc(MAX40, sizeof(int));
	if (result->digits == NULL)
		return NULL; 

	for(i = 0; i < MAX40; i++)
	{
		result->digits[i] = key->digits[i] ^ inputText->digits[i];
	}

	return result;
}

//output to STDERR, delimited by semicolon (;)
//output the NID
//output the difficulty raiting scale 1.0 to 5.0 
//hours spent on assignment 
void kw26Rating(void)
{
	kw26RatingStruct *rating = calloc(1, sizeof(kw26RatingStruct)); 

	rating->NID = calloc(10, sizeof(char));

	strcpy(rating->NID, "ca166584;");  		  //pointer to a malloc'ed buffer for the NID
	rating->degreeOfDifficulty = 4.2; 	      //1.0 for super easy to 5.0 for insanity++
	rating->duration = 42.00;                 // Hours spent writing, reading,

	fprintf(stderr, "%s;", rating->NID);
	fprintf(stderr, "%f;", rating->degreeOfDifficulty);
	fprintf(stderr, "%f;", rating->duration);

	return; 
}


//pointer to an Int40 array of 40 digits 
Int40 *loadHWConfigVariable(int doSeed)
{	
	time_t seconds; 
	seconds = time(NULL);

	srand(seconds/3600);
	int i = 0, j = 0, ranNum = 0; 

	hwConfigVariable = calloc(1, sizeof(Int40));

	hwConfigVariable->digits = calloc(MAX40, sizeof(int));

	if(doSeed == 0)
	{
		for(i = 0; i < MAX40; i++)
			hwConfigVariable->digits[i] = 1;
	}
	
	if(doSeed != 0)	
	{
		for(i = 0 ; i < 5; i++)
			hwConfigVariable->digits[i] = rand() % 10;
		// 0: 5 10 15 20 25 30 35 // stop at 40
		// 1: 6 11 16 21 26 31 36
		// 2: 7 12 17 22 27 32 37 
		// 3: 8 13 ....		   38
		// 4: .. 			   39	
		for(i = 0; i < 5; i++)
		{
			j = 5 + i;
			while(j < MAX40)
			{
				hwConfigVariable->digits[j] = hwConfigVariable->digits[i];
				j += 5; //skip every 5 indexes of the array and repeat 
			}
		}
	}

	return hwConfigVariable;
}


Int40 *loadCryptoVariable(char *inputFilename)
{
	FILE *fp = fopen(inputFilename, "r"); 
	if (fp == NULL)
		return NULL; 
	
	// Create a struct to return 
	Int40 *cryptoVariable = calloc(1, sizeof(Int40)); 
	if(cryptoVariable == NULL) 
		return NULL; 

	// Create a string pointer in char type to get the valid char. in the file
	// then pass the string to the parseString function.
	int i = 0;
	char *strHex = calloc(MAX40, sizeof(char));
	if(strHex == NULL)
		return NULL; 
	
	char inputChar;
	
	inputChar = fgetc(fp);
	while (inputChar != EOF)
	{	
		// to prevent overflowing
		// use if(i < strlen(strHex)) in the future 
		// to avoid hard coding or make changes in future
		if(i == 40)
			break;

		// only take valid characters, we do not care if it's a digit
		// or an alphalbet (upper + lower) because parseString will 
		// handle that 
		if(isdigit(inputChar) || isalpha(inputChar))
			strHex[i++] = inputChar;

		// read the next character until EOF char. and stop the loop
		inputChar = fgetc(fp);
	}

	cryptoVariable = parseString(strHex);
	
	free(strHex);
	fclose(fp);
	return cryptoVariable; 
}


Int40 *loadPlainText(char *inputFilename)
{
	FILE *fileP = fopen(inputFilename, "r"); 
	char c;
	int i = 0, len, j, x;

	if (fileP == NULL)
		return NULL; 

	Int40 *inputText = calloc(1 , sizeof(Int40));	
	if(inputText == NULL) 							
		return NULL; 
	
	inputText->digits = calloc(MAX40, sizeof(int));
	if(inputText->digits == NULL)
		return NULL; 

	Int40 *temp = calloc(40, sizeof(Int40));
	if (temp == NULL)
		return NULL; 

	temp->digits = calloc(MAX40, sizeof(int));
	if (temp->digits == NULL)
		return NULL; 

	//len = strlen(fileP);
	
	c = fgetc(fileP); 

	while(c != EOF)
	{
		if(i == 40) 
			break;

		if(isdigit(c))
		{
			inputText->digits[i++] = c;
		}

		if(c >= 'a' && c <= 'z')
		{
			inputText->digits[i++] = c - 'a' + 97; 
		}

		if(c >= 'A' && c <= 'Z')
		{
			inputText->digits[i++] = c - 'A' + 65; 
		}

		c = fgetc(fileP);
	}

	if(i < 40)
	{
		for( ; i < 40; i++)
		{
			//add (blank) spaces if less than 40 
			inputText->digits[i] = 32; 
		}
	}

	//store in reverse order
	for(j = 0, x = 39; j < 40; j++, x--)
	 	temp->digits[j] = inputText->digits[x]; 

	kw26Destroyer(inputText);
	fclose(fileP); 
	return temp;
}
