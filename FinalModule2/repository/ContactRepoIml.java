package FinalModule2.repository;

import FinalModule2.model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactRepoIml implements IContactRepository {
    private static final String FILE_PATH = "FinalModule2/data/contacts.csv";
    private static final String DELIMITER = ",";

    @Override
    public Contact add(String phoneNumber, String group, String fullName, String gender, String address, String dateOfBirth, String email) throws IOException {
        Contact existingContact = findByPhone(phoneNumber);
        if (existingContact != null) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
        }

        Contact newContact = new Contact(phoneNumber, group, fullName, gender, address, dateOfBirth, email);

        List<Contact> contacts = getAll();
        contacts.add(newContact);
        saveToFile(contacts);

        return newContact;
    }

    @Override
    public List<Contact> getAll() throws IOException {
        List<Contact> contacts = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return contacts;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Contact contact = parseContactFromCsv(line);
                    if (contact != null) {
                        contacts.add(contact);
                    }
                }
            }
        }
        return contacts;
    }

    @Override
    public void updateByPhone(String phoneNumber, String group, String fullName, String gender, String address, String dateOfBirth, String email) throws IOException {
        List<Contact> contacts = getAll();
        boolean found = false;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getPhoneNumber().equals(phoneNumber)) {
                Contact updatedContact = new Contact(phoneNumber, group, fullName, gender, address, dateOfBirth, email);
                contacts.set(i, updatedContact);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Không tìm thấy liên hệ với số điện thoại này!");
        }

        saveToFile(contacts);
    }

    @Override
    public Contact findByPhone(String phone) throws IOException {
        List<Contact> contacts = getAll();
        return contacts.stream()
                .filter(contact -> contact.getPhoneNumber().equals(phone))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Contact> findByName(String fullName) throws IOException {
        List<Contact> contacts = getAll();
        List<Contact> result = new ArrayList<>();

        for (Contact contact : contacts) {
            if (contact.getFullName().toLowerCase().contains(fullName.toLowerCase())) {
                result.add(contact);
            }
        }
        return result;
    }

    @Override
    public void delete(String phoneNumber) throws IOException {
        List<Contact> contacts = getAll();
        boolean removed = contacts.removeIf(contact -> contact.getPhoneNumber().equals(phoneNumber));

        if (!removed) {
            throw new IllegalArgumentException("Không tìm thấy liên hệ với số điện thoại này!");
        }

        saveToFile(contacts);
    }

    private Contact parseContactFromCsv(String csvLine) {
        try {
            String[] fields = csvLine.split(DELIMITER, -1);
            if (fields.length >= 7) {
                return new Contact(
                        fields[0].trim(),
                        fields[1].trim(),
                        fields[2].trim(),
                        fields[3].trim(),
                        fields[4].trim(),
                        fields[5].trim(),
                        fields[6].trim()
                );
            }
        } catch (Exception e) {
            System.err.println("Error parsing CSV line: " + csvLine + " - " + e.getMessage());
        }
        return null;
    }

    private String contactToCsv(Contact contact) {
        return String.join(DELIMITER,

                contact.getPhoneNumber() != null ? contact.getPhoneNumber() : "",
                contact.getFullName() != null ? contact.getFullName() : "",
                contact.getAddress() != null ? contact.getAddress() : "",
                contact.getEmail() != null ? contact.getEmail() : "",
                contact.getGender() != null ? contact.getGender() : "",
                contact.getGroup() != null ? contact.getGroup() : "",
                contact.getDateOfbirth() != null ? contact.getDateOfbirth() : ""
        );
    }

    private void saveToFile(List<Contact> contacts) throws IOException {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Contact contact : contacts) {
                writer.write(contactToCsv(contact));
                writer.newLine();
            }
        }
    }
}
