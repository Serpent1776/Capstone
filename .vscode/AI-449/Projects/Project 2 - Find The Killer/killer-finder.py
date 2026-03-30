import itertools
import copy
from domain import Domain
def main():
    print("""All possible statements:
\tA: I am innocent.
\tB: The killer has an odd number.
\tC: I am standing next to the killer.
\tD: The killer is telling the truth.
\tE: My number is smaller than the killer's number.
\tF: What that person said. (See below for more on this one.)
\tG: Both the first and last person are lying.
\tH: The people next to me are telling the truth.""")
    
    people_str = str(input("All of the suspects' statements (put the letters A-H here): ")).upper()
    #print(people_str)
    n = impossible_check(people_str)
    if(n == -1):
         print("No solution possible.")
         quit()
    combos = list()
    for i in itertools.permutations(["ti", "tk","fi","fk"], 4):
            combos.append(i)
    print(combos)
    print(len(combos))
    general = list()
    for c in combos:
            for i in itertools.combinations_with_replacement(c, n):
                kill_counter = 0
                for u in i:
                    if(u == "tk" or u == "fk"):
                        kill_counter += 1
                if(kill_counter == 1 and not is_dupe(general, i) and halfTrue(i, n)):
                    general.append(i)
    #print(general)
    #print(len(general))
    domains = Domain(people_str, general)
    #arc concistency, domain splitting
    #find the killer

def impossible_check(people_str):
    if(len(people_str) % 2 == 1 or people_str[0] == 'F'):
          return -1 
    return len(people_str)
    
def is_dupe(arr, element):
    for i in arr:
          if(element == i):
               return True
    return False
def halfTrue(tup, arr_len):
    true_counter = 0
    for str in tup:
          if('t' in str):
               true_counter += 1
    return true_counter == arr_len/2

main()
"""
Test cases: 
HECA: Person 1 is the killer.
DAGB
GACG
ACFFEFFG
ACEG
BEAD
CA
AC
AF
AAAGCE
AFGFCA
HFFFCA
ABCDEFGH
Known Issues: 
None
"""