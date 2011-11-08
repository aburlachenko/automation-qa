#Sort and remove dublicates for random count of integers.

echo -n "Enter maximum count of random integers: "
read MAXCOUNT
echo -n "Enter the range for each integer: "
read RANGE

count=1

rm -f list
rm -f sorted_list.txt
	while [ "$count" -le $MAXCOUNT ]
	do
	number=$RANDOM
	let "number %= $RANGE"
	echo "$number" >> list
	let "count += 1"  # i++
	done
echo "-----------------"

sort -u -g list > sorted_list.txt

echo "Done... See sorted_list.txt"
echo "Press any key..."
read