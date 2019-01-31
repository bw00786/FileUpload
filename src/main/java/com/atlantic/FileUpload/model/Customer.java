package com.atlantic.FileUpload.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;


@Document(collection = "customer")
public class Customer {
    @Id
    private int customerId;
    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String zip;

    //*Customer(int customerId, int productId,String firstName,String lastName,String city,String state,String zip) {
     //   this.customerId=customerId;
     //   this.productId=productId;
      //  this.firstName=firstName;
      //  this.lastName=lastName;
     //   this.city=city;
     //   this.state=state;
     //   this.zip=zip;
  //  }
//*
    public int getCustomerId(int i) {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
