#!/bin/bash

####################Variables######################
file="2011_09_06_routing02n.log.gz"
datestr="2011-09-06"
###################################################
red='\E[31;40m'
green='\E[32;40m'
yellow='\E[33;40m'
blue='\E[34;40m'
magenta='\E[35;40m'
cyan='\E[36;40m'
white='\E[37;40m'
###################################################
echo -n -e "$yellow" "Are you sure? 'Y/N': "
read var
if [[ $var == "Y" || $var == "y" ]]
    then echo "Starting processing $file"
        else if [[ $var == "N" || $var == "n" ]]
	    then
    		exit 0
    else
        echo "Incorrect input"
	exit 0
    fi
fi
tput sgr0
###################################################

linenum=$(gunzip -c $file | wc -l)
monitor=$(gunzip -c $file | grep Monitor | wc -l)
dateline=$(gunzip -c $file | grep -o '^[^,]*,' | sed 's/,//' | gzip -c > 1.gz)
#datestr=$(gunzip -c 1.gz | head -n 1 | grep -o '^[0-9]...-[0-9].-[0-9].')
dateline=$(gunzip -c 1.gz | grep -o '^[0-9]...-[0-9].-[0-9].T[0-9].:[0-9].:[0-9].\.[0-9]..'| grep -o "$datestr" | wc -l)
let dateline=linenum-dateline 

status=$(gunzip -c $file | grep -o '[0-2].,[0-9\-]\{1,3\}.[0-9]*,[0-9\-]\{1,3\}.[0-9]*,[0-9\-]\{1,3\}.[0-9]*,[0-9\-]\{1,3\}.[0-9]*,[0-9]\{0,4\},[a-z\_]*,[0-9]*,[0-9]*$' | grep -o '^[0-2].' | gzip -c > 2.gz)
status10=$(gunzip -c 2.gz | grep -o 10 | wc -l)
status11=$(gunzip -c 2.gz | grep -o 11 | wc -l)
status12=$(gunzip -c 2.gz | grep -o 12 | wc -l)
statusOTH=$(gunzip -c 2.gz | wc -l)
let statusOTH=statusOTH-status10-status11-status12

status=$(gunzip -c $file | grep -o '^[0-9]...-[0-9].-[0-9].T[0-9].:[0-9].:[0-9].\.[0-9]..,[a-z0-9]\{32\},[a-z0-9]\{0,32\},[a-z0-9]\{0,32\},[0-9]*,[^,]*,[^,]*,[^,]*,[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\},0,[0-9\-]\{1,4\}.[0-9\E-]*,[0-9\-]\{1,4\}.[0-9\E-]*,[0-9\-]\{1,4\}.[0-9\E-]*,[0-9\-]\{1,4\}.[0-9\E-]*,[0-9]\{0,4\},[a-z\_]*,[0-9]*,[0-9]*$' | grep -v Monitor | wc -l)
statusAPI=$(gunzip -c $file | grep -o '^[0-9]...-[0-9].-[0-9].T[0-9].:[0-9].:[0-9].\.[0-9]..,,[a-z0-9]\{0,32\},[a-z0-9]\{0,32\},[0-9]*,[^,]*,[^,]*,[^,]*,[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\},0,[0-9\-]\{1,4\}.[0-9\E-]*,[0-9\-]\{1,4\}.[0-9\E-]*,[0-9\-]\{1,4\}.[0-9\E-]*,[0-9\-]\{1,4\}.[0-9\E-]*,[0-9]\{0,4\},[a-z\_]*,[0-9]*,[0-9]*$' | grep -v Monitor | wc -l)


echo -e "$green" "file has $linenum lines"
echo -e "$green" "         $status lines are valid. they match the pattern, have status=0 and valid api-key (no monitor)"
echo -e "$cyan" "     $monitor lines are test requests and were made from HTTP-Monitor"
echo -e "$cyan" "     $statusAPI lines are valid but do not have api-key"
echo -e "$red" "     $dateline lines have incorrect date format"
echo -e "$red" "     $status10 lines lead to bad request error (status=10) and match log pattern"
echo -e "$red" "     $status11 lines lead to timeout error (status=11) and match log pattern"
echo -e "$red" "     $status12 lines lead to other errors (status=12) and match log pattern"
echo -e "$red" "     $statusOTH lines lead to other errors"
#echo -e "$red" "     $statusN lines do not match log pattern"
echo -e "$green" "All operations complete. Press any key to continue"
read
