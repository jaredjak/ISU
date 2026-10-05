# Jared Krug         9/7/2023
# 10/10 ice cream shop

import icecreamShop as ics

print("Hello! Welcome to", ics.shop_name, "! It's nice to see you")
print("Your options are:")
for i in range(0,len(ics.items)):
    print(ics.items[i], "-", ics.prices[i])