from passlib.hash import sha512_crypt

# Provided below is a bruteforce method of finding out what the password is!

# The hash provided 
hash_string = "$6$xgLS35S6$2UjEq.dUhICPw9zgDVJXcQYQp/9ilLPQt/8Zgu0uwngI5mVvB1eKQG9SnVLjmOOfkB4Jjb5VSAXGXjY4Cf5k90"

hash_parts = hash_string.split("$")
if len(hash_parts) < 4:
    exit()

salt = hash_parts[2]
target_hash = "$6$" + salt + "$" + hash_parts[3]

# Path to the password list file
password_list_file = "100k-most-used-passwords-NIST.txt" 

with open(password_list_file, "r", encoding="utf-8") as file: 
        for password in file: 
            # Remove extra characters like newline
            password = password.strip() 
            if sha512_crypt.using(salt=salt, rounds=5000).hash(password) == target_hash:
                 print(f"Password: {password}")
                 break
        else:
             print("Where is the password?!")