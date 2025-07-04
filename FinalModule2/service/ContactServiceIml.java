package FinalModule2.service;

import FinalModule2.model.Contact;
import FinalModule2.repository.ContactRepoIml;
import FinalModule2.repository.IContactRepository;

import java.io.IOException;
import java.util.List;

public class ContactServiceIml implements IContactService{
    IContactRepository iContactRepository=new ContactRepoIml();
    
    @Override
    public Contact add(String phoneNumber, String group, String fullName, String gender, String address, String dateOfBirth, String email) throws IOException {
        return iContactRepository.add(phoneNumber, group, fullName, gender, address, dateOfBirth, email);
    }

    @Override
    public List<Contact> getAll() throws IOException {
        return iContactRepository.getAll();
    }

    @Override
    public void updateByPhone(String phoneNumber, String group, String fullName, String gender, String address, String dateOfBirth, String email) throws IOException {
        iContactRepository.updateByPhone(phoneNumber, group, fullName, gender, address, dateOfBirth, email);
    }

    @Override
    public Contact findByPhone(String phone) throws IOException {
        return iContactRepository.findByPhone(phone);
    }

    @Override
    public List<Contact> findByName(String fullName) throws IOException {
        return iContactRepository.findByName(fullName);
    }

    @Override
    public void delete(String phoneNumber) throws IOException {
        iContactRepository.delete(phoneNumber);
    }
}
