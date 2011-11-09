//---------------------------------------------------------------------------

#ifndef SortingFormH
#define SortingFormH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <Dialogs.hpp>

#include "SortingAlg.h"
#include <string>
#include <fstream>
using namespace std;
//---------------------------------------------------------------------------
class TForm1 : public TForm
{
__published:	// IDE-managed Components
        TButton *OpenFileButton;
        TButton *SortButton;
        TOpenDialog *OpenDialog1;
        TButton *GenInputFileButton;
        void __fastcall SortButtonClick(TObject *Sender);
        void __fastcall OpenFileButtonClick(TObject *Sender);
        void __fastcall GenInputFileButtonClick(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TForm1(TComponent* Owner);
        int writeArrayToFile(string filepath, long* array, long& size);
        void readArrayFromFile(string filepath, long* array,long& size);
        long getArraySizeFromFile(string filename);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm1 *Form1;
//---------------------------------------------------------------------------
#endif
