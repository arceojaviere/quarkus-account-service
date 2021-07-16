package org.test.model.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Embeddable
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "person_id"))
})
public class Person extends PanacheEntityBase{
    @NotBlank(message= "The ID of the person associated with the account is required and may not be blank")
    private String id;
    
    @Transient
    private String name;
    
    @Transient
    private String lastName;
    
    @Transient
    private List<String> emailAddresses;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<String> getEmailAddresses() {
        return emailAddresses;
    }
    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    
}
