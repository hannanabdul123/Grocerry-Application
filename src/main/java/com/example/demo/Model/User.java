package com.example.demo.Model;


import javax.persistence.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Email;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
@Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    @JsonProperty("email")
private String userEmail;

    @Column(nullable = false)
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;
    private String address;
     @NotBlank
    private String city;
    @Pattern(regexp = "^[0-9]{6}$")
    private String pincode;
@Enumerated(EnumType.STRING)
@Column(nullable = false)
private Role role= Role.USER; ;

@Column(nullable = false)
private boolean active = true;
// ADMIN / USER
    public String getPhone() {
    return phone;
}

   public void setPhone(String phone) {
    this.phone = phone;
   }

   public String getAddress() {
    return address;
   }

   public void setAddress(String address) {
    this.address = address;
   }

   public String getCity() {
    return city;
   }

   public void setCity(String city) {
    this.city = city;
   }

   public String getPincode() {
    return pincode;
   }

   public void setPincode(String pincode) {
    this.pincode = pincode;
   }

   public void setRole(Role role) {
    this.role = role;
   }

   
    

    public Role getRole() { 
    return role;
}

   public boolean isActive() {
    return active;
   }

   public void setActive(boolean active) {
    this.active = active;
   }

    public User(){

    }

    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.userEmail = email;
        this.password = password;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    

}
