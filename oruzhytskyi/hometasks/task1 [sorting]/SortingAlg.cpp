//---------------------------------------------------------------------------


#pragma hdrstop

#include "SortingAlg.h"

//---------------------------------------------------------------------------

#pragma package(smart_init)

void SortingAlg::quickSort(long* a, long low, long high)
{
  long i = low;
  long j = high;
  long x = a[(low + (high-low)/2)];
  do
  {
    while (a[i] < x) ++i;
    while (x < a[j]) --j;
    if ( i <= j )
    {
      if( i < j )
      {
        long t = a[i];
        a[i] = a[j];
        a[j] = t;
      }
      ++i;
      --j;
    }
  } while (i <= j);
  if (low < j) quickSort(a, low, j);
  if (i < high) quickSort(a, i, high);
};

long SortingAlg::sortArray(long* arr, long& size)
{
  time_t srttime, endtime;
  srttime=time(NULL);

  quickSort(arr, 0, size-1);

  endtime=time(NULL);
  return (long)(endtime-srttime);
};

void SortingAlg::generateArray(long* arr, long& size)
{
  for(long i=0;i<size;i++) *(arr+i)=random(10000001);
};
