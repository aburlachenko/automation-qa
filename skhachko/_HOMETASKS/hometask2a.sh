#Remove all duplicate characters from a string. Thus "aaabbb" becomes "ab" and "abcbd" becomes "abcd".
echo -n "Enter the string to remove dublicates: "
read var
echo $var | egrep -o . | sort -u
echo "Done... Press any key"
read