package controller;

import model.Contacts;
import model.Customers;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ReadCsv {
    private static final int NAME = 1;
    private static final int SURNAME = 2;
    private static final int AGE = 3;
    private static final int CITY = 4;
    private static ArrayList<Contacts> contactsArray = new ArrayList<Contacts>();
    private static ArrayList<Customers> customersArray;
    private static int typeContact = Contacts.UNKNOWN_TYPE;
    static Customers customers;

    /**
     * Uploading the csv file
     *
     * @param url
     * @return
     */
    public static ArrayList<Customers> readCsv(String url) {
        customersArray = new ArrayList<Customers>();
        try (Scanner scanner = new Scanner(new File(url))) {
            while (scanner.hasNextLine()) {
                contactsArray = new ArrayList<Contacts>();
                getRecordFromLine(scanner.nextLine());
                customers.setContacts(contactsArray);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return customersArray;
    }

    /**
     * Create customer object
     *
     * @param line
     * @return
     */
    private static void getRecordFromLine(String line) {
        int i = 0;
        try (Scanner rowScanner = new Scanner(line)) {
            customers = new Customers();
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                String values = rowScanner.next();
                i++;
                switch (i) {
                    case NAME:
                        customers.setName(values);
                        break;
                    case SURNAME:
                        customers.setSurname(values);
                        break;
                    case AGE:
                        if (values.length() > 0) {
                            customers.setAge(Integer.parseInt(values));
                        }
                        break;
                    case CITY:
                        break;
                    default:
                        contactsArray.add(setContact(values));
                        break;
                }
            }
            customersArray.add(customers);
        }
    }

    /**
     * Create a contact object
     *
     * @param value
     * @return
     */
    private static Contacts setContact(String value) {
        Contacts contact = new Contacts();
        value = value.replaceAll(" ", "");
        if (isNumeric(value)) {
            if (value.length() == 9) {
                typeContact = Contacts.PHONE_TYPE;
            }
        } else if (isValidEmail(value)) {
            typeContact = Contacts.EMAIL_TYPE;
        } else if (value.equals("jbr")) {
            typeContact = Contacts.JABBER_TYPE;
        }
        contact.setType(typeContact);
        contact.setContact(value);
        typeContact = Contacts.UNKNOWN_TYPE;
        return contact;
    }

    /**
     * Checks if the string is a number
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the string is an e-mail
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
