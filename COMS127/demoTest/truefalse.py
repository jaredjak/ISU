# jared krug          9/12/2023
# True flase demo

for value in ["", 0, None, [], (), {}, False]:
    if value:
        print(value, "True")
    else:
        print(value, "False")

print()

for value in ["Hello", 1, not None, [1], (1), {"1":1}, not False]:
    if value:
        print(value, "True")
    else:
        print(value, "False")

print()
#wrong way to do this:
print("Starting Tests...")
if 3 == 5:
    print("True")
if 3 < 5:
    print("True")
if 3 > 5:
    print("True")
print("Tests Complete...")

#right way to do this:
print("Starting Tests...")
if 3 == 5:
    print("True 1")
elif 3 < 5:
    print("True 2")
elif 3 <= 5:
    print("True 3")
print("Tests Complete...")


#other right way to do this:
print("Starting Tests...")
if 3 == 5:
    print("True 1")
elif 3 > 5:
    print("True 2")
elif 3 >= 5:
    print("True 3")
else:
    print("True 4")
print("Tests Complete...")

