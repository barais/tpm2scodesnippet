#include <stdio.h>
#include <stdlib.h>


#include <jni.h>
#include "p1_TestJNI1.h"



JNIEXPORT void JNICALL Java_TestJNI1_afficherBonjour(JNIEnv *env, jobject obj)
{
	printf(" Bonjour\n ");
	return;
}
__attribute__ ((noinline))  char* getCpuUsage() {


		//FILE * fp;

        //char* res = malloc(128*sizeof(char));

        //fp = popen("ps aux|awk 'NR > 0 { s +=$3 }; END {print s}'","r");

        //fread(res, 1, sizeof(res)-1, fp);

        //fclose(fp);
	
        return "toto";

}

int main(){
	long long i;
	for (i = 0 ; i<10000000;i++)
	{
		getCpuUsage();
	}	
	return 0;

}


