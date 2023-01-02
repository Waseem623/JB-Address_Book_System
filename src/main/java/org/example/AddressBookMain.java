package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class AddressBookMain {
    public static final int ADD_CONTACT = 1;
    public static final int EXIT = 0;
    public static final int EDIT_CONTACT = 2;
    public static final int DELETE_CONTACT = 3;
    public static final int CREATE_ADDRESS_BOOK = 4;
    public static final int PRINT_ADDRESS_BOOK_DICTIONARY = 5;
    public static final int SEARCH_CONTACT_BY_CITY_OR_STATE = 6;
    public static final int GET_CONTACT_BY_CITY = 7;
    public static final int GET_CONTACT_BY_STATE = 8;
    public static final int GET_CITY_WISE_CONTACT_COUNT = 9;
    public static final int GET_STATE_WISE_CONTACT_COUNT = 10;
    public static final int SORT_ADDRESS_BOOK_ENTRIES = 11;
    public static final int SORT_BY_CITY = 12;
    public static final int SORT_BY_STATE = 13;
    public static final int SORT_BY_ZIP = 14;
    public static final int WRITE_TO_TEXT_FILE = 15;
    public static final int READ_FROM_TEXT_FILE = 16;
    public static final int READ_FROM_CSV_FILE = 17;
    public static final int WRITE_TO_CSV_FILE = 18;
    public static final int READ_FROM_JSON_FILE = 19;
    public static final int WRITE_TO_JSON_FILE = 20;

    public static void main(String[] args) throws CustomException, FileNotFoundException {
        AddressBookDictionary addressBookDictionary = new AddressBookDictionary();
        Scanner sc = new Scanner(System.in);
        String firstName;
        String lastName;
        addressBookDictionary.addAddressBooks();
        AddressBook addressBook;
        while (true) {
            System.out.println("Enter your choice : " +
                    "\n1 : Add new contact to address book " +
                    "\n2 : Edit contact " +
                    "\n3 : Delete contact" +
                    "\n4 : Create new address book " +
                    "\n5 : Print address book dictionary " +
                    "\n6 : Search person by city or state " +
                    "\n7 : Get contacts by city" +
                    "\n8 : Get contacts by state" +
                    "\n9 : Get contact count by city" +
                    "\n10 : Get contact count by state" +
                    "\n11 : Sort address book entries" +
                    "\n12 : Sort by city" +
                    "\n13 : Sort by state" +
                    "\n14 : Sort by Zip" +
                    "\n15 : Write to file" +
                    "\n16 : Read from file" +
                    "\n17 : Read from CSV file" +
                    "\n18 : Write to csv file" +
                    "\n19 : Read from json file" +
                    "\n20 : Write to json file" +
                    "\n0 : Exit");

            int choice = sc.nextInt();
            switch (choice) {
                case ADD_CONTACT:
                    System.out.println("How many contacts do you want to create ? ");
                    int numberOfContacts = sc.nextInt();
                    for (int i = 0; i < numberOfContacts; i++) {
                        addressBook = addressBookDictionary.selectAddressBook();
                        Contact contact = new Contact();
                        contact.createContact();
                        if (addressBookDictionary.isExistContact(contact)) {
                            System.out.println("Already exist. Try with another name");
                            i--;
                        } else {
                            addressBook.addContact(contact);
                            System.out.println(addressBookDictionary.getAddressBookDictionary());
                        }
                    }
                    break;

                case EDIT_CONTACT:
                    System.out.println("Enter first name ");
                    firstName = sc.next().toLowerCase();
                    System.out.println("Enter last name ");
                    lastName = sc.next().toLowerCase();
                    addressBook = addressBookDictionary.getAddressBook(firstName, lastName);
                    if (addressBook != null)
                        addressBook.editContact(firstName, lastName);
                    else System.out.println("No Match Found");
                    break;

                case DELETE_CONTACT:
                    System.out.println("Enter first name ");
                    firstName = sc.next().toLowerCase();
                    System.out.println("Enter last name ");
                    lastName = sc.next().toLowerCase();
                    addressBook = addressBookDictionary.getAddressBook(firstName, lastName);
                    if (addressBook != null)
                        addressBook.deleteContact(firstName, lastName);
                    else System.out.println("No Match Found");
                    break;

                case CREATE_ADDRESS_BOOK:
                    addressBookDictionary.addAddressBooks();
                    break;

                case PRINT_ADDRESS_BOOK_DICTIONARY:
                    System.out.println(addressBookDictionary.addressBookDictionary);
                    break;

                case SEARCH_CONTACT_BY_CITY_OR_STATE:
                    System.out.println("Select option : " +
                            "\n1 : search by city " +
                            "\n2 : search by state");
                    int option = sc.nextInt();
                    boolean isSearchByCity = false;
                    String searchKey = null;
                    if (option == 1) {
                        isSearchByCity = true;
                        System.out.println("Enter city");
                        searchKey = sc.next();
                    } else if (option == 2) {
                        System.out.println("Enter state ");
                        searchKey = sc.next();
                    } else
                        System.out.println("Invalid option selected");
                    if (searchKey != null) {
                        addressBookDictionary.getContactFromCityOrState(searchKey, isSearchByCity);
                    }
                    break;

                case GET_CONTACT_BY_CITY:
                    addressBookDictionary.getCityWiseOrStateWiseContacts(AddressBookDictionary.CITY_WISE_COLLECTOR);
                    break;

                case GET_CONTACT_BY_STATE:
                    addressBookDictionary.getCityWiseOrStateWiseContacts(AddressBookDictionary.STATE_WISE_COLLECTOR);
                    break;

                case GET_CITY_WISE_CONTACT_COUNT:
                    addressBookDictionary.getCityWiseContactsCount();
                    break;

                case GET_STATE_WISE_CONTACT_COUNT:
                    addressBookDictionary.getStateWiseContactsCount();
                    break;

                case SORT_ADDRESS_BOOK_ENTRIES:
                    addressBookDictionary.sortAddressBookEntries();
                    break;

                case SORT_BY_CITY:
                    addressBookDictionary.sortByCityOrStateOrZip(AddressBookDictionary.CITY_WISE_COLLECTOR);
                    break;

                case SORT_BY_STATE:
                    addressBookDictionary.sortByCityOrStateOrZip(AddressBookDictionary.STATE_WISE_COLLECTOR);
                    break;

                case SORT_BY_ZIP:
                    addressBookDictionary.sortByCityOrStateOrZip(AddressBookDictionary.ZIP_WISE_COLLECTOR);
                    break;

                case WRITE_TO_TEXT_FILE:
                    addressBookDictionary.writeDataToTextFile();
                    break;

                case READ_FROM_TEXT_FILE:
                    addressBookDictionary.readDataFromTextFile();
                    break;

                case READ_FROM_CSV_FILE:
                    addressBookDictionary.readFromCsvFile();
                    break;

                case WRITE_TO_CSV_FILE:
                    addressBookDictionary.writeToCsvFile();
                    break;

                case READ_FROM_JSON_FILE:
                    addressBookDictionary.readFromJsonFile();
                    break;

                case WRITE_TO_JSON_FILE:
                    addressBookDictionary.writeToJsonFile();
                    break;

                case EXIT:
                    return;

                default:
                    System.out.println("Invalid Input");
            }
        }
    }
}