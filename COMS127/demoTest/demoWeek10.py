# Jared Krug       10/24/2023
# Dictionaries demo 

# shapes = {}
# shapes["Octogon"] = "Purple"
# shapes["Tridecagon"] = "Turtle"
# shapes["Hexagon"] = "Chad"
# print(shapes) # {'Octogon': 'Purple', 'Tridecagon': 'Turtle', 'Hexagon': 'Chad'}
# --------------------------
# colors = {}
# colors["Orange"] = "Warm"
# colors["Purple"] = "T-Rex"
# colors["Cyan"] = "Underrated"
# print(colors) # {'Orange': 'Warm', 'Purple': 'T-Rex', 'Cyan': 'Underrated'}
# print(colors["Cyan"]) # Underrated
# ---
# colors["Warm"] = "Orange"
# colors["Cool"] = "Purple"
# colors["Cool"] = "Cyan"
# print(colors) # {'Warm': 'Orange', 'Cool': 'Cyan'}
# ------------------------------------
# listDict = {}

# listDict["Human Organs"] = []
# listDict["Bones"] = []

# listDict["Human Organs"].append("Brain")
# listDict["Human Organs"].append("Skin")
# listDict["Human Organs"].append("Heart")
# listDict["Human Organs"].append("Pancreas")

# listDict["Bones"].append("Femur")
# listDict["Bones"].append("Skull")
# listDict["Bones"].append("Metacarpals")

# print(listDict) # {'Human Organs': ['Brain', 'Skin', 'Heart', 'Pancreas'], 'Bones': ['Femur', 'Skull', 'Metacarpals']}
# print(listDict["Human Organs"]) # ['Brain', 'Skin', 'Heart', 'Pancreas']
# print(listDict["Human Organs"][3]) # Pancreas
# --------------------------------------
# d1 = {1: "One", 2: "Two"}
# d2 = {2: "Two", 1: "One"}
# print(d1)
# print(d2)
# print(d1 == d2) # True
# Does not have to be in order to be equal to each other
# ---------------------------------------------
# d1 = {1: "One", 2: "Two"}
# d2 = {2: "Two", 1: "1"}
# print(d1)
# print(d2)
# print(d1 == d2) # False
# The values of the keys do not equal each other
# -----------------------------------------
cars = {}
cars["Trucks"] = "Big"
cars["Race cars"] = "Fast"
cars["SUVs"] = "Soccer Mom"

del cars["Race cars"]
print(cars) # {'Trucks': 'Big', 'SUVs': 'Soccer Mom'}

for k, v in cars.items():
    print(cars[k] == v) # True 
                        # True

print("Trucks" in cars.items()) # False
print("Big" in cars.items()) # False
print(("Trucks", "Big") in cars.items()) # True

# print("Trucks" in cars) # True
# print("Big" in cars) # False
# print("Trucks" in cars.keys()) # True

# print("Big" in cars) # False
# print("Big" in cars.values()) # True
# print("Trucks" in cars.values()) #False

