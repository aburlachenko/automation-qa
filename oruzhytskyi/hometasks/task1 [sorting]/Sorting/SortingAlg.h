//---------------------------------------------------------------------------

#ifndef SortingAlgH
#define SortingAlgH

#include <string>
#include <fstream>
#include "stdlib.h"
#include <time.h>

using namespace std;
//---------------------------------------------------------------------------

class SortingAlg
{
  public:
    long sortArray(long* arr, long& size);
    void generateArray(long* arr,long& size);
    void quickSort(long* arr, long low, long high);
  private:
};

#endif

