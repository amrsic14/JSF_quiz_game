/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author acamr
 */
@Entity
@Table(name="user")
public class User implements Serializable {
    
    public static enum UserType {User, Supervisor, Administrator};
    public static enum Gender {Male, Female};
    
    @Id
    @Column(name="username")
    private String username;
    
    @Column(name="password")
    private byte[] password;
    
    @Column(name="firstName")
    private String firstName;
    
    @Column(name="lastName")
    private String lastName;
       
    @Column(name="email")
    private String email;
    
    @Column(name="profession")
    private String profession;
    
    @Column(name="gender")
    private Gender gender;
    
    @Column(name="idnumber")
    private String idNumber;
    
    @Column(name="hasImage")
    private boolean hasImage;
    
    @Column(name="securityquestion")
    private String securityQuestion;
    
    @Column(name="answer")
    private String answer;
    
    @Column(name="type")
    private UserType type;

    public User() {};
    
    public User(RegistrationRequest request, UserType type){
        this.type = type;
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.profession = request.getProfession();
        this.gender = request.getGender();
        this.idNumber = request.getIdNumber();
        this.email = request.getEmail();
        this.hasImage = request.isHasImage();
        this.securityQuestion = request.getSecurityQuestion();
        this.answer = request.getAnswer();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
    
}
