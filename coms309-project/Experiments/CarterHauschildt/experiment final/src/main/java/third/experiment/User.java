package third.experiment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;

  protected User() {}

  public User(String fName, String lName)
  {
    firstName = fName;
    lastName = lName;
  }

  public Long getId()
  {
    return id;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void updateFirstName(String name)
  {
    firstName = name;
  }
  public void updateLastName(String name)
  {
    lastName = name;
  }

  @Override
  public String toString()
  {
    return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id , firstName, lastName);
  }
}
