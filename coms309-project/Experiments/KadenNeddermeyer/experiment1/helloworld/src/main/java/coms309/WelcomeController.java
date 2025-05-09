package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "I love this class!!";
    }

    @GetMapping("/Hello")
    public String greeting() {
        return "Hey!";
    }

    @GetMapping("/Barry/{name}")
    public String greeting(@PathVariable String name) {
        return name + " HATES Barry";
    }

    @GetMapping("/{name}/j{age}")
    public String greeting(@PathVariable String name, @PathVariable int age) {
        return name + " is " + age*2 + " years old.";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }
    @GetMapping("/{boggy}/{word}")
    public String welcome(@PathVariable String word, @PathVariable String boggy) {
        return boggy + " must " + word;
    }



}
