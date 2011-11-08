#Replace all runs of consecutive spaces with a single space. Thus "a.b" is unchanged and "a..b" becomes "a.b", using dot to make the space visible.

echo -n "Enter the string to remove space-dublicates: "
read var
echo $var | sed 's/\.\./\./g' | sed 's/\.\./\./g'
echo "Done... Press any key"
read