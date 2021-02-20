package model;

public class Contacts {
    private int id;
    private int idCustomer;
    private int type;
    private String contact;
    private Customers customers;

    public static final int UNKNOWN_TYPE = 0;
    public static final int EMAIL_TYPE = 1;
    public static final int PHONE_TYPE = 2;
    public static final int JABBER_TYPE = 3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", idCustomer=" + idCustomer +
                ", type=" + type +
                ", contact='" + contact + '\'' +
                '}';
    }
}
