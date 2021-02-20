package controller;

import model.Contacts;
import model.Customers;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;

public class ReadXml {

    /**
     * Uploading the xml file
     *
     * @param url
     * @return
     */
    public static ArrayList<Customers> readXml(String url) {
        ArrayList<Customers> customersArray = new ArrayList<Customers>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean bPerson = false;
                boolean bName = false;
                boolean bSurname = false;
                boolean bAge = false;
                boolean city = false;
                boolean bContacts = false;
                boolean bPhone = false;
                boolean bEmail = false;
                boolean bJabber = false;
                int typeContact = Contacts.UNKNOWN_TYPE;
                ArrayList<Contacts> contactsArray;

                /**
                 * Selecting an item from a file
                 * @param uri
                 * @param localName
                 * @param qName
                 * @param attributes
                 * @throws SAXException
                 */
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {
                    if (qName.equalsIgnoreCase("person")) {
                        bPerson = true;
                    }
                    if (qName.equalsIgnoreCase("name")) {
                        bName = true;
                    }
                    if (qName.equalsIgnoreCase("surname")) {
                        bSurname = true;
                    }
                    if (qName.equalsIgnoreCase("age")) {
                        bAge = true;
                    }
                    if (qName.equalsIgnoreCase("city")) {
                        city = true;
                    }
                    if (qName.equalsIgnoreCase("phone")) {
                        typeContact = Contacts.PHONE_TYPE;
                        bPhone = true;
                    }
                    if (qName.equalsIgnoreCase("email")) {
                        typeContact = Contacts.EMAIL_TYPE;
                        bEmail = true;
                    }
                    if (qName.equalsIgnoreCase("jabber")) {
                        typeContact = Contacts.JABBER_TYPE;
                        bJabber = true;
                    }
                    if (qName.equalsIgnoreCase("contacts")) {
                        bContacts = true;
                        contactsArray = new ArrayList<Contacts>();
                    }
                }

                /**
                 * End element from file
                 * @param uri
                 * @param localName
                 * @param qName
                 * @throws SAXException
                 */
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (bContacts) {
                        if (qName.equalsIgnoreCase("contacts")) {
                            bContacts = false;
                        }
                    }
                }

                /**
                 * Create customer object
                 * @param ch
                 * @param start
                 * @param length
                 * @throws SAXException
                 */
                public void characters(char ch[], int start, int length) throws SAXException {
                    if (bPerson) {
                        Customers customers = new Customers();
                        customersArray.add(customers);
                        bPerson = false;
                    }
                    if (!customersArray.isEmpty()) {
                        Customers customer = customersArray.get(customersArray.size() - 1);
                        if (bName) {
                            customer.setName(new String(ch, start, length));
                            bName = false;
                        }
                        if (bSurname) {
                            customer.setSurname(new String(ch, start, length));
                            bSurname = false;
                        }
                        if (bAge) {
                            String ageString = new String(ch, start, length);
                            customer.setAge(Integer.parseInt(ageString));
                            bAge = false;
                        }
                        if (bContacts) {
                            String newContactTxt = new String(ch, start, length);
                            if (!newContactTxt.isBlank()) {
                                Contacts contact = new Contacts();
                                contact.setCustomers(customer);
                                contact.setType(typeContact);
                                contact.setContact(newContactTxt);
                                contactsArray.add(contact);
                                customer.setContacts(contactsArray);
                                typeContact = Contacts.UNKNOWN_TYPE;
                            }
                        }
                    }
                }
            };

            saxParser.parse(url, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customersArray;
    }
}
