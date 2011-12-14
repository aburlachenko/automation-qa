#Sort and remove dublicates for random count of integers.

echo -n "Enter maximum count of random integers: "
read MAXCOUNT
echo -n "Enter the range for each integer: "
read RANGE

randoms="./list.txt"
sorted="./sorted_list.txt"
counter=1

rm -f $randoms
rm -f $sorted
	while [ "$counter" -le $MAXCOUNT ]
	do
	number=$RANDOM
	let "number %= $RANGE"
	echo "$number" >> $randoms
	let "counter += 1"  # i++
	done
echo "-----------------"

sort -u -g $randoms > $sorted

echo "Done... See list.txt and sorted_list.txt"
echo "Press any key..."
read