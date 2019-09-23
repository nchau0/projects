//Cadilnis Chau
//HW 3 

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// this program is entirely your own work and that you have neither developed
// your code together with any another person, nor copied program code
// from any other person, nor permitted your code to be copied or otherwise used
// by any other person, nor have you copied, modified, or otherwise used program
// code that you have found in any external source, including but not limited to,
// online sources”


/*
 * This code is supplied for HW3 as a baseline for solving the
 * problems uniq to HW3 - sorting decimal latitude and airports.
 * It will likely use the code from the AVL labs to expedite the
 * sorting. MMc 11182017
 */

typedef struct airPdata{

  char *LocID; //Airports's "Short Name".
  char *fieldName; // Airport Name
  char *city; //Associated City
  float latitude; //latitude
  float longitude; // Longitude

}airPdata;

void parseLine(char *line, airPdata *apd);
void printData(int length, airPdata *apd);
void deleteStruct(airPdata *apd);

float sexag2decimal_long(char *degreeString);
float sexag2decimal_lat(char *degreeString);
int tsaCheckPoint(char *shortName);


#define BUFFER_SIZE 500

struct node
{
  airPdata key;
    struct node *left;
    struct node *right;
    int height;
};


float max(float a, float b);

int height(struct node *N)
{
    if (N == NULL)
        return 0;
    return N->height;
}


float max(float a, float b)
{
    return (a > b) ? a : b;
}


struct node* newNode(airPdata key)
{
    struct node* node = (struct node*)
                        malloc(sizeof(struct node));
    node->key  = key;
    node->left   = NULL;
    node->right  = NULL;
    node->height = 1;  // new node is initially added at leaf
    return(node);
}


struct node *rightRotate(struct node *y)
{
    struct node *x = y->left;
    struct node *T2 = x->right;

    // Perform rotation
    x->right = y;
    y->left = T2;

    // Update heights
    y->height = max(height(y->left), height(y->right))+1;
    x->height = max(height(x->left), height(x->right))+1;

    // Return new root
    return x;
}


struct node *leftRotate(struct node *x
){
    struct node *y = x->right;
    struct node *T2 = y->left;


    y->left = x;
    x->right = T2;


    x->height = max(height(x->left), height(x->right))+1;
    y->height = max(height(y->left), height(y->right))+1;


    return y;
}


float getBalance(struct node *N)
{
    if (N == NULL)
        return 0;
    return height(N->left) - height(N->right);
}

struct node* insert_latitude(struct node* node, airPdata key)
{
  {

    if (node == NULL)
        return(newNode(key));

    if (key.latitude < node->key.latitude)
        node->left  = insert_latitude(node->left, key);
    else
        node->right = insert_latitude(node->right, key);

    float balance = getBalance(node);


    if (balance > 1 && key.latitude < node->left->key.latitude)
        return rightRotate(node);


    if (balance < -1 && key.latitude > node->right->key.latitude)
        return leftRotate(node);

    if (balance > 1 && key.latitude > node->left->key.latitude)
    {
        node->left = leftRotate(node->left);
        return rightRotate(node);
    }

    if (balance < -1 && key.latitude < node->right->key.latitude)
     {
        node->right = rightRotate(node->right);
        return leftRotate(node);
     }
       return node; 
  }
}  

struct node* insert_location(struct node* node, airPdata key)
{
  {

    if (node == NULL)
        return(newNode(key));

    if (key.LocID < node->key.LocID)
        node->left  = insert_location(node->left, key);
    else
        node->right = insert_location(node->right, key);

    float balance = getBalance(node);


    if (balance > 1 && key.LocID < node->left->key.LocID)
        return rightRotate(node);


    if (balance < -1 && key.LocID > node->right->key.LocID)
        return leftRotate(node);

    if (balance > 1 && key.LocID > node->left->key.LocID)
    {
        node->left = leftRotate(node->left);
        return rightRotate(node);
    }

    if (balance < -1 && key.LocID < node->right->key.LocID)
     {
        node->right = rightRotate(node->right);
        return leftRotate(node);
     }
       return node; 
  }
}  
  



//sort using this function 
void inOrder(struct node *root)
{
    if(root != NULL)
    {
        inOrder(root->left);
        printf("%s,%s,%s,%.4f,%.4f\n",root->key.LocID, root->key.fieldName, root->key.city, root->key.latitude, root->key.longitude);
        inOrder(root->right);
    }
}



int main (int argc, char *argv[]){

	FILE *fid;

	char buffer[BUFFER_SIZE];
  char selection = *argv[2];
	int count = 0;
	int i = 0, n = 0, box = 0;
  int length;

  float outOfBounds = 31; 

struct node *root = NULL;


		if(argc==3){
			fid = fopen(argv[1], "r");
			if(fid==NULL){
				printf("File %s not found.\n", argv[1]);
				return 2;
			}
		}
		else{
      printf("Syntax: ./hw3Sort [input file] [sortParameter]\n");
      printf("Valid [sortParameter] are a for alphabetical or n for North Bound Exit.\n");
			return 1;
		}


	// Determine length of the file.
	while(fgets(buffer, BUFFER_SIZE, fid) != NULL){
		count++;
	}
	rewind(fid);


	// Declare a struct array and allocate memory.
	airPdata *data;

	data = malloc(sizeof(airPdata)*count);
	if(data == NULL){
		printf("Memory allocation for airPdata array failed. Aborting.\n");
		return 2;
	}

	// Read and parse each line of the inputt file.
	for(int i = 0; i < count; i++){
		fgets(buffer, BUFFER_SIZE, fid);

		// fgets() includes the New Line delimiter in the output string.
		// i.e. "This is my string.\n\0"
		// We will truncate the string to drop the '\n' if it is there.
		// Note: There will be no '\n' if the line is longer than the buffer.
		if(buffer[strlen(buffer) - 1] == '\n') buffer[strlen(buffer)-1] = '\0';

		parseLine(buffer, data+i);
	}


  printf("code,name,city,lat,lon");
  printf("\n");
  for (int i = 0; i < count; i++)
  {
    if (selection == 'a' || selection == 'A')
    {
      if(tsaCheckPoint((data+i)->LocID) == 1)     
      root = insert_location(root, *(data+i));
    }
    if (selection == 'n' || selection == 'N')
    {
      if(tsaCheckPoint((data+i)->LocID) == 1) 
      root = insert_latitude(root, *(data+i));
    }

  }
  
  inOrder(root);



  // close the input file.
  fclose(fid);

	// Free the memory used for fields of the structs.
	for(int i = 0; i < count; i++){
		deleteStruct(data+i);
	}

	// Free the memory for the struct array.
	free(data);

	return 0;

}


void parseLine(char *line, airPdata *apd)
{


	int i=0, j=0, commas=0, group=0, b=0;

	while(commas<15){
		while(*(line+i)!=','){
			i++;
		}

		// strncpy does not append a '\0' to the end of the copied sub-string, so we will
		// replace the comma with '\0'.
		*(line+i) = '\0';

		switch (commas){

		case 1:   //Grab the second "field" - Location ID


			apd->LocID = malloc(sizeof(char)*(i-j+1));
			if(apd->LocID==NULL)
      {
        printf("malloc failed to initialize airPdata.LocID.\n");
        exit(-1);
      }
				strncpy(apd->LocID, line+j, i-j+1);
        break;

		case 2:   //Grab the third "field" - Field Name
			apd->fieldName = malloc(sizeof(char)*(i-j+1));
			if(apd->fieldName==NULL){
				printf("malloc failed to initialize airPdata.fieldName.\n");
				exit(-1);
			}

			strncpy(apd->fieldName, line+j, i-j+1);
			break;

		case 3:   //Grab the fourth "field" - City
			apd->city = malloc(sizeof(char)*(i-j+1));
			if(apd->city==NULL){
				printf("malloc failed to initialize airPdata.city.\n");
				exit(-1);
			}

			strncpy(apd->city, line+j, i-j+1);
			break;

		case 8:   //Grab the ninth "field" - Latitude (sexagesimal string)
			apd->latitude = sexag2decimal_lat(line+j);
			break;

		case 9:   //Grab the tenth "field" - Longitude (sexagesimal string)
			apd->longitude = sexag2decimal_lat(line+j);
			break;
}

		j=++i;
		commas++;
	}

}




float sexag2decimal_lat(char *degreeString)
{
  float DD = 0.0, MM = 0.0, SS = 0.0, MRS = 0.0, num = 0.0; 

  char *dd = calloc(10, sizeof(char));
  char *mm = calloc(10, sizeof(char));
  char *ss = calloc(10, sizeof(char));
  char *afterdot = calloc(10, sizeof(char));

  int len = strlen(degreeString);

  int counter = 0, num1_flag = 0, i = 0;

  int ddLen = 0; 

  for(i = 0; i < len; i++)
  {
    if(degreeString[i] >= 'a' && degreeString[i] <= 'z')
      return 0.0; 

    if(degreeString[i] == '-' && num1_flag == 0)
    {
      strncpy(dd, degreeString, i);
      DD = atof(dd);
      counter = i + 1;
      num1_flag = 1;
      continue;
      

    }



    if(degreeString[i] == '-' && num1_flag == 1)
    {
      strncpy(mm, degreeString + counter, i - counter);
      MM = atof(mm); 
      counter = i + 1;
      num1_flag = 2;
    }

    if(degreeString[i] == '.')
    {
      strncpy(ss, degreeString + counter, i - counter);
      SS = atof(ss); 
      strncpy(afterdot, degreeString + i, len - 2 - i ); //1
      MRS = atof(afterdot); 
    }
  }


  SS = SS + MRS; 

  num = DD + (MM / 60) + (SS / (60 * 60));

  if(num > 99)
    return 0.0; 
  
  if(degreeString[len - 1] == 'S' || degreeString[len - 1] == 'W')
  {
    num = num * (-1); 
  }

  free(afterdot);
  free(ss);
  free(mm);
  free(dd);


    return num; 

}


float sexag2decimal_long(char *degreeString)
{

  float DD = 0.0, MM = 0.0, SS = 0.0, MRS = 0.0, num = 0.0; 

  char *dd = calloc(10, sizeof(char));
  char *mm = calloc(10, sizeof(char));
  char *ss = calloc(10, sizeof(char));
  char *afterdot = calloc(10, sizeof(char));

  int len = strlen(degreeString);

  int counter = 0, num1_flag = 0, i = 0;

  for(i = 0; i < len; i++)
  {
      if(degreeString[i] >= 'a' && degreeString[i] <= 'z')
      return 0.0; 
    if(degreeString[i] == '-' && num1_flag == 0)
    {
      strncpy(dd, degreeString, i);
      DD = atof(dd); 
      counter = i + 1;
      num1_flag = 1;
      continue;
    }

    if(degreeString[i] == '-' && num1_flag == 1)
    {
      strncpy(mm, degreeString + counter, i - counter);
      MM = atof(mm); 
      counter = i + 1;
      num1_flag = 2;
    }

    if(degreeString[i] == '.')
    {
      strncpy(ss, degreeString + counter, i - counter);
      SS = atof(ss); 
      strncpy(afterdot, degreeString + i, len - 2 - i );
      MRS = atof(afterdot); 
    }
  }


  SS = SS + MRS; 

  num = DD + (MM / 60) + (SS / (60 * 60));
  
  if(degreeString[len - 1] == 'S' || degreeString[len - 1] == 'W')
  {
    num = num * (-1); 
  }

    return num; 
}

int tsaCheckPoint(char *shortName)
{
  int value = 0; 
  char num; 

  if(shortName == NULL)
    return -1;

  do
  {
    if(shortName[value] == '\0')
      return 1; 

    //if you find the char values 0 - 9, flag as 0
    for(num = '0'; num <= '9'; num++)
    {
      if(shortName[value] == num)
        return 0; 
    }

  value++;
  }while(1);


}


void deleteStruct(airPdata *apd){
	free(apd->city);
	free(apd->fieldName);
	free(apd->LocID);

}

