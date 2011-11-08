//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "SortingForm.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm1 *Form1;
SortingAlg *Alg;
//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
        : TForm(Owner)
{
}
int TForm1::writeArrayToFile(string filename, long* array, long& size)
{
  time_t st,et;
  st=time(NULL);

  ofstream ofile;
  ofile.open(filename.c_str());
  ofile<<size<<endl;
  for(long i=0;i<size;i++) ofile<<*(array+i)<<endl;
  ofile.close();

  et=time(NULL);
  return (int)(et-st);
}
void TForm1::readArrayFromFile(string filepath, long* array, long& size)
{
  ifstream ifile(filepath.begin());
  ifile>>*array;
  for(long i=0;i<size;i++) ifile>>*(array+i);
}

long TForm1::getArraySizeFromFile(string filename)
{
  ifstream ifile(filename.begin());
  long size;
  ifile>>size;
  return size;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::SortButtonClick(TObject *Sender)
{
  string filename=OpenDialog1->FileName.c_str();
  long size=getArraySizeFromFile(filename);
  long* arr = (long*)malloc(size*sizeof(long));
  readArrayFromFile(filename, arr, size);
  long eltime=Alg->sortArray(arr, size);
  int wrtime=writeArrayToFile("sorted.txt", arr, size);
  Application->MessageBox(("Sorting: "+IntToStr(eltime)+" sec\n"+"Writing to file: "+IntToStr(wrtime)+" sec").c_str(),"Time elapsed",0);
  free(arr);
}
//---------------------------------------------------------------------------

void __fastcall TForm1::OpenFileButtonClick(TObject *Sender)
{
  OpenDialog1->Execute();
  if (OpenDialog1->FileName!="") SortButton->Enabled=true;
}
//---------------------------------------------------------------------------


void __fastcall TForm1::GenInputFileButtonClick(TObject *Sender)
{
  long size=1000000;
  long* array = (long*)malloc(size*sizeof(long));
  Alg->generateArray(array, size);
  writeArrayToFile("unsorted.txt",array,size);
  Application->MessageBoxA("File is generated","OK",0);
  free(array);
}
//---------------------------------------------------------------------------

