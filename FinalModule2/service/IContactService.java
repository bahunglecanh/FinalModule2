package FinalModule2.service;

import FinalModule2.model.Contact;

import java.io.IOException;
import java.util.List;

public interface IContactService {
    Contact add(String phoneNumber, String group, String fullName, String gender, String address, String dateOfBirth, String email) throws IOException;
    List<Contact> getAll() throws IOException;
    void updateByPhone(String phoneNumber, String group, String fullName, String gender, String address, String dateOfBirth, String email) throws IOException;
    Contact findByPhone(String phone) throws IOException;
    List<Contact> findByName(String fullName) throws IOException;
    void delete(String phoneNumber) throws IOException;
}
