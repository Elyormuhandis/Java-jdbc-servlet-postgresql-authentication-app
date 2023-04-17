package uz.muhandis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(Integer id, String firstName, String lastName, String username, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public User(String firstName, String lastName, String username, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
