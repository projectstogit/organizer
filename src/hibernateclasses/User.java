package hibernateclasses;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Home on 02.03.15.
 */


@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "login")
    protected String login;
    @Column(name = "password")
    protected String password;
    @Column(name = "first_name")
    protected String firstName;
    @Column(name = "last_name")
    protected String lastName;
    @Column(name = "age")
    protected Integer age;
    @Column(name = "sex")
    protected Boolean sex;
    @Column(name = "email")
    protected String email;

    @OneToMany
    @JoinColumn(name = "login")
    protected List<Event> event;

    public User(String login, String password, String firstName, String lastName, Integer age, Boolean sex, String email) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.email = email;
    }

    protected User(){}




    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
