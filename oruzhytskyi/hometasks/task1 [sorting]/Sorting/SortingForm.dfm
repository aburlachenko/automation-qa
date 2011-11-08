object Form1: TForm1
  Left = 192
  Top = 114
  Width = 335
  Height = 92
  Caption = 'Sorting'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object OpenFileButton: TButton
    Left = 120
    Top = 16
    Width = 105
    Height = 25
    Caption = 'Open file for sorting'
    TabOrder = 0
    OnClick = OpenFileButtonClick
  end
  object SortButton: TButton
    Left = 240
    Top = 16
    Width = 75
    Height = 25
    Caption = 'Sort'
    Enabled = False
    TabOrder = 1
    OnClick = SortButtonClick
  end
  object GenInputFileButton: TButton
    Left = 8
    Top = 16
    Width = 97
    Height = 25
    Caption = 'Generate input file'
    TabOrder = 2
    OnClick = GenInputFileButtonClick
  end
  object OpenDialog1: TOpenDialog
    Left = 288
    Top = 48
  end
end
