package org.test.model.domain;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Account extends PanacheEntityBase{
    
    // Changing the property name when marshalling/unmarshalling to/from JSON
    @JsonbProperty("accountId")
    @Id
    @GenericGenerator(name = "datePlusSequence", strategy = "org.test.generator.DatePlusSequenceGenerator", parameters = {@Parameter(name="sequence", value="account_sequence")})
    @GeneratedValue(generator = "datePlusSequence")  
    private String id;
    
    @Column(name = "user_id", unique = true)
    @NotBlank(message = "The user ID is required for the Account")
    private String userId;
    private String password;

    @Embedded
    @NotNull(message = "The person ID associated with this account is required and cannot be null.")
    private Person person;

    public String getAccountId(){
        return this.id;
    }

    public void setAccountId(String id){
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
