package org.example;

import com.google.gson.Gson;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddressBookDictionary {
    public static final int CITY_WISE_COLLECTOR = 0;
    public static final int STATE_WISE_COLLECTOR = 1;
    public static final int ZIP_WISE_COLLECTOR = 2;

    HashMap<String, AddressBook> addressBookDictionary;

    public AddressBookDictionary() {
        addressBookDictionary = new HashMap<>();
    }

    public HashMap<String, AddressBook> getAddressBookDictionary() {
        return addressBookDictionary;
    }

    public void setAddressBookDictionary(HashMap<String, AddressBook> addressBookDictionary) {
        this.addressBookDictionary = addressBookDictionary;
    }

    void addAddressBooks() {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many address book do you want to create ? ");
        int numberOfAddressBooks = sc.nextInt();
        for (int i = 0; i < numberOfAddressBooks; i++) {
            System.out.println("Enter address book name");
            String addressBookName = sc.next();
            if (addressBookDictionary.containsKey(addressBookName)) {
                System.out.println("Already exist . Try with another name");
                i--;
            } else
                addressBookDictionary.put(addressBookName, new AddressBook());
        }
    }

    AddressBook selectAddressBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("In which of the following do you want to store contact? : ");
        ArrayList<String> addressBookNames = new ArrayList<String>(addressBookDictionary.keySet());
        for (int i = 0; i < addressBookNames.size(); i++) {
            System.out.println(i + 1 + " : " + addressBookNames.get(i));
        }
        int addressBookNameIndex = sc.nextInt() - 1;
        if (addressBookNameIndex >= addressBookNames.size()) {
            System.out.println("Address book not available. ");
            return null;
        }
        return addressBookDictionary.get(addressBookNames.get(addressBookNameIndex));
    }

    AddressBook getAddressBook(String firstName, String lastName) {
        for (AddressBook addressBook : addressBookDictionary.values()) {
            boolean isContainContact = addressBook
                    .getContactList()
                    .stream()
                    .anyMatch(contact -> firstName.equals(contact.getFirstName()) && lastName.equals(contact.getLastName()));
            if (isContainContact)
                return addressBook;
        }
        return null;
    }

    public boolean isExistContact(Contact contact) {
        for (AddressBook addressBook : addressBookDictionary.values()) {
            boolean isContainContact = addressBook
                    .getContactList()
                    .stream()
                    .anyMatch(contactEntry -> contactEntry.equals(contact));
            if (isContainContact)
                return true;
        }
        return false;
    }

    public void getContactFromCityOrState(String cityOrState, boolean isSearchByCity) {
        List<Contact> contacts = new ArrayList<>();
        for (AddressBook addressBook : addressBookDictionary.values()) {
            addressBook.getContactList()
                    .stream()
                    .filter(getContactByCityOrStatePredicate(cityOrState, isSearchByCity))
                    .forEach(contacts::add);
        }
        contacts.forEach(System.out::println);
    }

    private static Predicate<Contact> getContactByCityOrStatePredicate(String cityOrState, boolean isSearchByCity) {
        if (isSearchByCity)
            return contactEntry -> contactEntry.getCity().equals(cityOrState);
        else
            return contactEntry -> contactEntry.getState().equals(cityOrState);
    }

    public Map<String, List<Contact>> getCityWiseOrStateWiseContacts(int sortingFlag) {
        Map<String, List<Contact>> map = addressBookDictionary.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .collect(getStateOrCityOrZipWiseContactMapCollector(sortingFlag));
        map.forEach((key, value) -> System.out.println("City = " + key + "\nContacts = " + value));
        return map;
    }


    private static Collector<Contact, ?, Map<String, List<Contact>>> getStateOrCityOrZipWiseContactMapCollector(int sortingFlag) {
        if (sortingFlag == CITY_WISE_COLLECTOR)
            return Collectors.groupingBy(Contact::getCity);
        else if (sortingFlag == STATE_WISE_COLLECTOR)
            return Collectors.groupingBy(Contact::getState);
        else
            return Collectors.groupingBy(Contact::getZipString);
    }

    public void getCityWiseContactsCount() {
        getCityWiseOrStateWiseContacts(CITY_WISE_COLLECTOR)
                .forEach((key, value) -> System.out.println("State = " + key + "\nContacts = " + value.size()));
    }

    public void getStateWiseContactsCount() {
        getCityWiseOrStateWiseContacts(STATE_WISE_COLLECTOR)
                .forEach((key, value) -> System.out.println("State = " + key + "\nContacts = " + value.size()));
    }

    public void sortAddressBookEntries() {
        addressBookDictionary.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .sorted(Contact::compareTo)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public void sortByCityOrStateOrZip(int shouldSortByCityOrStateOrZip) {
        addressBookDictionary.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .sorted(getComparator(shouldSortByCityOrStateOrZip))
                .forEach(System.out::println);
    }

    private Comparator<Contact> getComparator(int option) {
        return (contact1, contact2) -> {
            if (option == STATE_WISE_COLLECTOR)
                return (contact1.getState().toLowerCase()).compareTo(contact2.getState().toLowerCase());
            if (option == CITY_WISE_COLLECTOR)
                return (contact1.getCity().toLowerCase()).compareTo(contact2.getCity().toLowerCase());
            else
                return (Integer.valueOf(contact1.getZip()).compareTo(Integer.valueOf(contact2.getZip())));
        };
    }

    public void readDataFromTextFile() throws CustomException {
        try {
            Path path = Paths.get("src/main/resources/AddressBookDictionary.txt");
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            System.out.println(data);
        } catch (IOException e) {
            throw new CustomException(ExceptionType.IO_EXCEPTION);
        }
    }

    public void writeDataToTextFile() throws CustomException {
        Path path = Paths.get("src/main/resources/AddressBookDictionary.txt");
        StringBuilder contentToWriteFile = new StringBuilder();
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            addressBookDictionary.forEach((s, addressBook) -> {
                contentToWriteFile.append(s + " : " + addressBook.getContactList() + "\n\n");
            });
            bw.write(contentToWriteFile.toString());
            System.out.println("Data write to file successfully");
        } catch (IOException e) {
            throw new CustomException(ExceptionType.IO_EXCEPTION);
        }
    }

    public void readFromCsvFile() throws CustomException {
        try {
            CsvToBeanBuilder<Contact> builder = new CsvToBeanBuilder<>(new FileReader("src/main/resources/AddressBookDictionary.csv"));
            List<Contact> beans = builder
                    .withType(Contact.class).build().parse();
            beans.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            throw new CustomException(ExceptionType.IO_EXCEPTION);
        }
    }

    public void writeToCsvFile() throws CustomException {
        StatefulBeanToCsvBuilder<Contact> builder;
        try (Writer writer = new FileWriter("src/main/resources/AddressBookDictionary.csv")) {
            builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<Contact> beanWriter = builder.build();
            List<Contact> listData = addressBookDictionary.values().stream()
                    .flatMap(addressBook -> addressBook.getContactList().stream())
                    .collect(Collectors.toList());
            beanWriter.write(listData);
            System.out.println("Data written to file successfully");
        } catch (IOException e) {
            throw new CustomException(ExceptionType.IO_EXCEPTION);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromJsonFile() throws CustomException {
        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/AddressBookDictionary.json"))) {
            HashMap<?,?> map = gson.fromJson(reader, HashMap.class);
            map.forEach((s, addressBook) -> System.out.println(s + "=" + addressBook));
        } catch (IOException e) {
            throw new CustomException(ExceptionType.IO_EXCEPTION);
        }
    }

    public void writeToJsonFile() throws CustomException {
        try (Writer writer = new FileWriter("src/main/resources/AddressBookDictionary.json")) {
            new Gson().toJson(addressBookDictionary, writer);
            System.out.println("Data written to file successfully");
        } catch (IOException e) {
            throw new CustomException(ExceptionType.IO_EXCEPTION);
        }
    }
}