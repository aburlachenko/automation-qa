MAXCOUNT=10000
count=1
RANGE=1000000

echo
echo "$MAXCOUNT случайных чисел:"
echo "-----------------"
while [ "$count" -le $MAXCOUNT ]      # Генерация 10 ($MAXCOUNT) случайных чисел.
do
  number=$RANDOM
  let "number %= $RANGE"
#  echo "Случайное число меньше $RANGE  ---  $number"
   echo "$number" >> list
  let "count += 1"  # Нарастить счетчик.
done
echo "-----------------"

sort -u -g list