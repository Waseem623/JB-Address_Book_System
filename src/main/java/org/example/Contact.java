package org.example;

import com.opencsv.bean.CsvBindByName;

import java.util.Locale;
import java.util.Scanner;

public class Contact implements Comparable<Contact> {
    public static final int FIRST_NAME = 1;
    public static final int LAST_NAME = 2;
    public static final int PHONE_NUMBER = 3;
    public static final int EMAIL_ID = 4;
    public static final int AREA = 5;
    public static final int CITY = 7;
    public static final int STATE = 8;
    public static final int ZIP = 9;
    @CsvBindByName
    private String firstName;
    @CsvBindByName
    private String lastName;
    @CsvBindByName
    private Long phoneNumber;
    @CsvBindByName

    private String emailId;
    @CsvBindByName

    private String area;
    @CsvBindByName

    private String city;
    @CsvBindByName

    private String state;
    @CsvBindByName
    private int zip;

    public Contact(String firstName, String lastName, Long phoneNumber, String emailId, String area, String city, String state, int zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.area = area;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Contact() {

    }

    @Override
    public boolean equals(Object obj) {
        Contact c = (Contact) obj;
        return firstName.equals(c.firstName) && lastName.equals(c.lastName);
    }

    public void createContact() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your details to create contact : ");
        System.out.print(" First name = ");
        firstName = sc.next().toLowerCase();
        System.out.print("\n Last name = ");
        lastName = sc.next().toLowerCase();
        System.out.print("\n Phone no = ");
        phoneNumber = Long.valueOf(sc.next());
        System.out.print("\n Email Id = ");
        emailId = sc.next();
        System.out.print("\n Area = ");
        area = sc.next();
        System.out.print("\n City = ");
        city = sc.next();
        System.out.print("\n State = ");
        state = sc.next();
        System.out.print("\n Zip = ");
        zip = sc.nextInt();
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public int getZip() {
        return zip;
    }

    public String getZipString() {
        return String.valueOf(zip);
    }

    public void setZip(int zip) {
        this.zip = zip;
    }


    @Override
    public String toString() {
        return "{ First name = " + firstName + " | Lastname = " + lastName + " | Phone number = " + phoneNumber + " | Email Id = " + emailId + " | Address = " + area + " , " + city + " , " + state + " , " + zip + "}";
    }

    public void edit() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter which field do you want to edit : ");
        System.out.println("1 : First name \n2 : Last name \n3 : Phone no \n4 : Email Id \n5 : Area \n6 : City \n7 : State \n8 : Zip");
        int choice = sc.nextInt();
        switch (choice) {
            case FIRST_NAME:
                System.out.println("Enter new first name ");
                setFirstName(sc.next());
                break;
            case LAST_NAME:
                System.out.println("Enter Last name = ");
                setLastName(sc.next());
                break;
            case PHONE_NUMBER:
                System.out.println("Enter Phone no = ");
                setPhoneNumber(Long.parseLong(sc.next()));
                break;
            case EMAIL_ID:
                System.out.println("Enter Email Id = ");
                setEmailId(sc.next());
                break;
            case AREA:
                System.out.println("Enter Area = ");
                setArea(sc.next());
                break;
            case CITY:
                System.out.println("Enter city = ");
                setArea(sc.next());
                break;
            case STATE:
                System.out.println("Enter state = ");
                setState(sc.next());
                break;
            case ZIP:
                System.out.println("Enter Zip = ");
                setZip(Integer.parseInt(sc.next()));
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    @Override
    public int compareTo(Contact contact) {
        int result = this.firstName.compareTo(contact.getFirstName());
        if (result != 0)
            return result;
        else
            return this.lastName.compareTo(contact.getLastName());
    }
}