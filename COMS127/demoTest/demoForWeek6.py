# Jared Krug          9/26/2023
# Strings demo Week 6 Day 1

str1 = "Hello, world!"
print(str1)

str2 = input("Please enter a string: ")
print(str2)

str3 = str1 + str2
print(str3)

str4 = str3 * 2
print(str4)


# str6 is a string and cannot be used with '*' (creates an error). Can be used with "+", though.
# str5 = "12"
# str6 = "13"
# str7 = str1 * str2
# print("Expected value: {0}. Actual value: {1}".format("1213",str7))

str8 = "Hello, World!"
str = str8 =str8.upper()
for i in range(0, len(str8)):
    print(str8[i])