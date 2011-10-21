#!/bin/bash

####################Variables######################
file="2011_09_05.staticmaps.tsapp01.030101354304000.log.gz"
datestr="2011_09_05"
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
dateline=$(gunzip -c $file | grep -o '^"[^#][^"]*",' | sed 's/"//' | sed 's/",//' | gzip -c > 1.gz) 
datestr=$(gunzip -c 1.gz | head -n 1 | grep -o '^[0-9]...-[0-9].-[0-9].')
dateline=$(gunzip -c 1.gz | grep -o '^[0-9]...-[0-9].-[0-9]. [0-9].:[0-9].:[0-9].,[0-9]..'| grep -o "$datestr" | wc -l)
let dateline=linenum-dateline 

status=$(gunzip -c $file | sed 's/,,/, ,/g' | sed 's/,,/, ,/g' | sed 's/, $/,format/g' | sed 's/,[a-z0-9]\{0,7\}$/,format/g' | sed 's/, ,format/,size,format/g' | sed 's/,[0-9]\{1,4\}x[0-9]\{1,4\},format$/,size,format/g' | sed 's/, ,size/,zoom,size/g' | sed 's/,[0-9]\{1,2\},size,format$/,zoom,size,format/g' | sed 's/, ,zoom/,zoom/g' | sed 's/, ,bbox/,coordinates,bbox/g' | sed 's/"[0-9].\.[0-9].*"/coordinates/g' | grep -o ,[0-2].,coordinates | sed 's/,coordinates//g' | egrep -o [^\,]+ | gzip -c > 2.gz)
status10=$(gunzip -c 2.gz | grep -o 10 | wc -l)
status11=$(gunzip -c 2.gz | grep -o 11 | wc -l)
status12=$(gunzip -c 2.gz | grep -o 12 | wc -l) 

status=$(gunzip -c $file | sed 's/,,/, ,/g' | sed 's/,,/, ,/g' | sed 's/"[0-9]...-[0-9].-[0-9]. [0-9].:[0-9].:[0-9].,[0-9].."/date/g' | sed 's/, $/,format/g' | sed 's/,[a-z0-9]\{0,7\}$/,format/g' | sed 's/, ,format/,size,format/g' | sed 's/,[0-9]\{1,4\}x[0-9]\{1,4\},format$/,size,format/g' | sed 's/, ,size/,zoom,size/g' | sed 's/,[0-9]\{1,2\},size,format$/,zoom,size,format/g' | sed 's/, ,zoom/,zoom/g' | sed 's/, ,zoom/,coordinates,zoom/g' | sed 's/"[0-9\-].*"/coordinates/g' | sed 's/, ,coordinates/,coordinates/g' | sed 's/0,coordinates,zoom,size,format$/valid_tail/g' | sed 's/, ,valid_tail/,valid_tail/g' | sed 's/,[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\},valid_tail$/,valid_tail/g' | sed 's/^date, ,/valid_head/g' | sed 's/^date,[a-z0-9]\{32\},/valid_head,/g' | sed 's/^valid_head, ,/valid_head,/g' | sed 's/^valid_head,[a-z0-9]\{32\},/valid_head,/g' | sed 's/^valid_head, ,/valid_head,/g' | sed 's/^valid_head,[a-z0-9]\{32\},/valid_head,/g' | sed 's/^valid_head, ,\/staticmap/valid_head,/g' | sed 's/^valid_head,[0-9]\{1,6\}\.[0-9]\{1,15\},\/staticmap/valid_head,/g' | grep valid_head | grep valid_tail | wc -l)
statusAPI=$(gunzip -c $file | sed 's/,,/, ,/g' | sed 's/,,/, ,/g' | sed 's/"[0-9]...-[0-9].-[0-9]. [0-9].:[0-9].:[0-9].,[0-9].."/date/g' | sed 's/, $/,format/g' | sed 's/,[a-z0-9]\{0,7\}$/,format/g' | sed 's/, ,format/,size,format/g' | sed 's/,[0-9]\{1,4\}x[0-9]\{1,4\},format$/,size,format/g' | sed 's/, ,size/,zoom,size/g' | sed 's/,[0-9]\{1,2\},size,format$/,zoom,size,format/g' | sed 's/, ,zoom/,zoom/g' | sed 's/, ,zoom/,coordinates,zoom/g' | sed 's/"[0-9\-].*"/coordinates/g' | sed 's/, ,coordinates/,coordinates/g' | sed 's/0,coordinates,zoom,size,format$/valid_tail/g' | sed 's/, ,valid_tail/,valid_tail/g' | sed 's/,[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\},valid_tail$/,valid_tail/g' | sed 's/^date,[a-z0-9]\{32\},/valid_heading,/g' | sed 's/^valid_heading, ,/valid_head,/g' | sed 's/^valid_heading,[a-z0-9]\{32\},/valid_head,/g' | sed 's/^valid_head, ,/valid_head,/g' | sed 's/^valid_head,[a-z0-9]\{32\},/valid_head,/g' | sed 's/^valid_head, ,\/staticmap/valid_head,/g' | sed 's/^valid_head,[0-9]\{1,6\}\.[0-9]\{1,15\},\/staticmap/valid_head,/g' | grep -v valid_head | grep valid_tail | wc -l)
let statusN=linenum-status-dateline-status10-status11-status12
let statusGO=status-statusAPI
let statusAPI=statusAPI-monitor

#statusWR=$(gunzip -c $file | sed 's/,,/, ,/g' | sed 's/,,/, ,/g' | sed 's/"[0-9]...-[0-9].-[0-9]. [0-9].:[0-9].:[0-9].,[0-9].."/date/g' | sed 's/, $/,format/g' | sed 's/,[a-z0-9]\{0,7\}$/,format/g' | sed 's/, ,format/,size,format/g' | sed 's/,[0-9]\{1,4\}x[0-9]\{1,4\},format$/,size,format/g' | sed 's/, ,size/,zoom,size/g' | sed 's/,[0-9]\{1,2\},size,format$/,zoom,size,format/g' | sed 's/, ,zoom/,zoom/g' | sed 's/, ,zoom/,coordinates,zoom/g' | sed 's/"[0-9\-].*"/coordinates/g' | sed 's/, ,coordinates/,coordinates/g' | sed 's/0,coordinates,zoom,size,format$/valid_tail/g' | sed 's/, ,valid_tail/,valid_tail/g' | sed 's/,[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\}.[0-9]\{1,3\},valid_tail$/,valid_tail/g' | sed 's/^date, ,/valid_head/g' | sed 's/^date,[a-z0-9]\{32\},/valid_head,/g' | sed 's/^valid_head, ,/valid_head,/g' | sed 's/^valid_head,[a-z0-9]\{32\},/valid_head,/g' | sed 's/^valid_head, ,/valid_head,/g' | sed 's/^valid_head,[a-z0-9]\{32\},/valid_head,/g' | sed 's/^valid_head, ,\/staticmap/valid_head,/g' | sed 's/^valid_head,[0-9]\{1,6\}\.[0-9]\{1,15\},\/staticmap/valid_head,/g' | grep valid_head | grep -v valid_tail )
#echo -e "$yellow" "$statusWR"

echo -e "$green" "file has $linenum lines"
echo -e "$green" "         $status lines are valid. they match the pattern and have status=0"
echo -e "$green" "         $statusGO lines have valid api-key and go to billing statistic"
echo -e "$cyan" "     $monitor lines are test requests and were made from HTTP-Monitor"
echo -e "$cyan" "     $statusAPI lines are valid but do not have api-key"
echo -e "$red" "     $dateline lines have incorrect date format"
echo -e "$red" "     $status10 lines lead to bad request error (status=10) and match log pattern"
echo -e "$red" "     $status11 lines lead to timeout error (status=11) and match log pattern"
echo -e "$red" "     $status12 lines lead to other errors (status=12) and match log pattern"
echo -e "$red" "     $statusN lines do not match log pattern"
echo -e "$green" "All operations complete. Press any key to continue"
read
