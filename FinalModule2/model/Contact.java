package FinalModule2.model;

public class Contact {
    protected String fullName;
    protected String phoneNumber;
    protected String address;
    protected String email;
    protected String gender;
    protected String group;
    protected String dateOfbirth;

    public Contact() {
    }



    public Contact(String phoneNumber, String group, String fullName, String gender, String address) {
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
    }

    public Contact(String phoneNumber, String group, String fullName, String gender, String address, String dateOfbirth, String email) {
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.dateOfbirth = dateOfbirth;
        this.email = email;
    }

    public Contact(String group, String fullName, String gender, String address, String dateOfbirth, String email) {
        this.group = group;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.dateOfbirth = dateOfbirth;
        this.email = email;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDateOfbirth() {
        return dateOfbirth;
    }

    public void setDateOfbirth(String dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", group='" + group + '\'' +
                ", dateOfbirth='" + dateOfbirth + '\'' +
                '}';
    }
}
