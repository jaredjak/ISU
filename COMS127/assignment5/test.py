d1 = {}
d1["One"] = "Uno"
d1["Two"] = "Two"

d2 = {}
d2["One"] = 1
d2["Two"] = 2
d2["Three"] = 3

for x in d1.keys():
    for y in d2.keys():
        print(d2[x])
        if x != y:
            d1[x] = d2[x] + d2[x]

print(d1)
