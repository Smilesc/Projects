
#include <stdio.h>
int main( int argc, char *argv[] ) {
    char * mystring = "this,string,is,great";
    char my_delim = ",";

    printf(mystring);
    strtok(mystring, my_delim);

    printf("%s\n", mystring);
}

