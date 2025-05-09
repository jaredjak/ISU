package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class PeopleController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, Person> peopleList = new  HashMap<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/people")
    public  HashMap<String,Person> getAllPersons() {
        return peopleList;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public  String createPerson(@RequestBody Person person) {
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
    }

    @PostMapping("/people/generic/{name}")
    public String createPerson(@PathVariable String name) {
        Person p = new Person();
        p.setFirstName(name);
        p.setLastName("Jefferson");
        p.setAge(27);
        p.setAddress("400 Bakers St.");
        p.setTelephone("200-200-2400");
        peopleList.put(name, p);
        return "New person " + p.getFirstName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/people/{firstName}")
    public Person getPerson(@PathVariable String firstName) {
        Person p = peopleList.get(firstName);
        return p;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{firstName}")
    public Person updatePerson(@PathVariable String firstName, @RequestBody Person p) {
        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/people/{firstName}")
    public HashMap<String, Person> deletePerson(@PathVariable String firstName) {
        peopleList.remove(firstName);
        return peopleList;
    }

    @DeleteMapping("/people/delete/{letter}")
    public String deletePeople(@PathVariable String letter) {
        String rem = "The following have been removed: ";
        for (HashMap.Entry<String, Person> s: peopleList.entrySet()) {
            if (s!=null) {
                if (s.getKey().compareTo(letter) > 0) {
                    String name = s.getKey();
                    peopleList.remove(name);
                    rem += name + " ";
                }
            }
        }
        return rem;
    }

    @PutMapping("/people/edit/name/{newName}")
    public String editName(@RequestBody String oldName, @PathVariable String newName) {
        Person p = peopleList.get(oldName);
        p.setFirstName(newName);
        peopleList.remove(oldName);
        peopleList.put(newName, p);
        return "Person name changed to " + newName;
    }

    @PutMapping("/people/age/{name}")
    public String age(@PathVariable String name) {
        Person p = peopleList.get(name);
        p.setAge(p.getAge() + 1);
        return "Happy Birthday, " + name + "! They are now " + p.getAge() + " years old!";
    }
}

