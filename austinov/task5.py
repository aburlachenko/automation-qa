import sys

def fctrl(n):
  if n == 0:
    return 1
  else:
    return n * fctrl(n - 1)

def sum_nums(f):
  s = 0
  while f > 0:
    s, f = s + f % 10, f // 10
  return s

def main():
  if len(sys.argv) < 2 or int(sys.argv[1]) > 997:
    print 'Wrong argument passed'
    return
  factorial = fctrl(int(sys.argv[1]))
  print 'factorial is', factorial
  print 'Num by num sum is', sum_nums(factorial)

if __name__ == "__main__": main()
