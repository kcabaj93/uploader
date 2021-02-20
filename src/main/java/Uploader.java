import controller.ReadCsv;
import controller.ReadXml;
import model.Customers;
import model.CustomersDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Uploader {
    private static boolean repeat = false;
    private static String url;
    private static List<Customers> customers = new ArrayList<Customers>();

    public static void main(String[] args) {
        do {
            readFile();
            selectMethodReadFile();
            saveInDB();
            checkRepeatUpload();
        } while (repeat);
    }

    /**
     * The method to read the path to the file
     */
    private static void readFile() {
        System.out.println("Enter the path to the file to be loaded into the database in csv or xml format (absolute path required, e.g. C:\\my_file.xml):");
        Scanner scanner = new Scanner(System.in);
        url = scanner.nextLine();
    }

    /**
     * Method for selecting the appropriate file reading type
     */
    private static void selectMethodReadFile() {
        String[] cutUrl = url.split("\\.");
        if (cutUrl.length > 1) {
            switch (cutUrl[cutUrl.length - 1]) {
                case "csv":
                case "txt":
                    customers = ReadCsv.readCsv(url);
                    break;
                case "xml":
                    customers = ReadXml.readXml(url);
                    break;
                default:
                    System.out.println("You entered a file with the wrong extension");
                    break;
            }
        } else {
            System.out.println("You entered the wrong file path");
        }
    }

    /**
     * Method to write to the database
     */
    private static void saveInDB() {
        if (customers.isEmpty()) {
            System.out.println("No customers were found for import");
        } else {
            boolean isSaved = CustomersDAO.insertCustomers(customers);
            if (isSaved) {
                System.out.println("Saved new customers");
            }
        }
    }

    /**
     * The method asks whether to add a new file
     */
    private static void checkRepeatUpload() {
        System.out.println("Do you want to upload a new file? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        System.out.print("N ");
        String answer = scanner.nextLine();
        switch (answer) {
            case "y":
            case "Y":
                repeat = true;
                break;
            case "N":
            case "n":
            case "":
                repeat = false;
                break;
        }
    }
}
