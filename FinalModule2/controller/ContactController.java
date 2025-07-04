package FinalModule2.controller;

import FinalModule2.model.Contact;
import FinalModule2.service.ContactServiceIml;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ContactController {
    private ContactServiceIml contactServiceIml=new ContactServiceIml();
    private Scanner scanner=new Scanner(System.in);


    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ DANH BẠ ===");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Tìm kiếm liên hệ theo số điện thoại");
            System.out.println("6. Tìm kiếm liên hệ theo tên");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    displayAllContacts();
                    break;
                case 2:
                    addNewContact();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    searchByPhone();
                    break;
                case 6:
                    searchByName();
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng ứng dụng!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    public void displayAllContacts() {
        try {
            List<Contact> contacts = contactServiceIml.getAll();
            if (contacts.isEmpty()) {
                System.out.println("Danh sách liên hệ trống!");
                return;
            }

            System.out.println("\n=== DANH SÁCH LIÊN HỆ ===");
            System.out.printf("%-15s %-10s %-20s %-10s %-30s%n", 
                "SỐ ĐIỆN THOẠI", "NHÓM", "HỌ TÊN", "GIỚI TÍNH", "ĐỊA CHỈ");
            System.out.println("-".repeat(90));
            
            for (Contact contact : contacts) {
                System.out.printf("%-15s %-10s %-20s %-10s %-30s%n",
                    contact.getPhoneNumber(),
                    contact.getGroup(),
                    contact.getFullName(),
                    contact.getGender(),
                    contact.getAddress());
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc dữ liệu: " + e.getMessage());
        }
    }

    public void addNewContact() {
        System.out.println("\n=== THÊM LIÊN HỆ MỚI ===");
        
        System.out.print("Số điện thoại: ");
        String phoneNumber = scanner.nextLine().trim();

        System.out.print("Nhóm: ");
        String group = scanner.nextLine().trim();
        
        System.out.print("Họ tên: ");
        String fullName = scanner.nextLine().trim();
        
        System.out.print("Giới tính: ");
        String gender = scanner.nextLine().trim();
        
        System.out.print("Địa chỉ: ");
        String address = scanner.nextLine().trim();
        
        System.out.print("Ngày sinh: ");
        String dateOfBirth = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        try {
            Contact newContact = contactServiceIml.add(
                phoneNumber, group, fullName, gender, address, dateOfBirth, email);
            System.out.println("Thêm liên hệ thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi khi thêm liên hệ: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateContact() {
        System.out.println("\n=== CẬP NHẬT LIÊN HỆ ===");
        System.out.print("Nhập số điện thoại cần cập nhật: ");
        String phoneNumber = scanner.nextLine().trim();

        try {
            Contact existingContact = contactServiceIml.findByPhone(phoneNumber);
            if (existingContact == null) {
                System.out.println("Không tìm thấy liên hệ với số điện thoại này!");
                return;
            }

            System.out.println("Thông tin hiện tại:");
            displayContactDetails(existingContact);

            System.out.println("\nNhập thông tin mới (Enter để giữ nguyên):");
            
            System.out.print("Nhóm [" + existingContact.getGroup() + "]: ");
            String group = getInputOrDefault(existingContact.getGroup());
            
            System.out.print("Họ tên [" + existingContact.getFullName() + "]: ");
            String fullName = getInputOrDefault(existingContact.getFullName());
            
            System.out.print("Giới tính [" + existingContact.getGender() + "]: ");
            String gender = getInputOrDefault(existingContact.getGender());
            
            System.out.print("Địa chỉ [" + existingContact.getAddress() + "]: ");
            String address = getInputOrDefault(existingContact.getAddress());
            
            System.out.print("Ngày sinh [" + existingContact.getDateOfbirth() + "]: ");
            String dateOfBirth = getInputOrDefault(existingContact.getDateOfbirth());
            
            System.out.print("Email [" + existingContact.getEmail() + "]: ");
            String email = getInputOrDefault(existingContact.getEmail());

            contactServiceIml.updateByPhone(phoneNumber, group, fullName, gender, address, dateOfBirth, email);
            System.out.println("Cập nhật liên hệ thành công!");

        } catch (IOException e) {
            System.out.println("Lỗi cập nhật liên hệ: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Xóa contact
    public void deleteContact() {
        System.out.println("\n=== XÓA LIÊN HỆ ===");
        System.out.print("Nhập số điện thoại cần xóa: ");
        String phoneNumber = scanner.nextLine().trim();

        try {
            Contact existingContact = contactServiceIml.findByPhone(phoneNumber);
            if (existingContact == null) {
                System.out.println("Không tìm thấy liên hệ với số điện thoại này!");
                return;
            }

            System.out.println("Thông tin liên hệ sẽ bị xóa:");
            displayContactDetails(existingContact);
            
            System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y") || confirm.equals("yes")) {
                contactServiceIml.delete(phoneNumber);
                System.out.println("Xóa liên hệ thành công!");
            } else {
                System.out.println("Hủy thao tác xóa.");
            }

        } catch (IOException e) {
            System.out.println("Lỗi xóa liên hệ: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchByPhone() {
        System.out.println("\n=== TÌM KIẾM THEO SỐ ĐIỆN THOẠI ===");
        System.out.print("Nhập số điện thoại: ");
        String phoneNumber = scanner.nextLine().trim();

        try {
            Contact contact = contactServiceIml.findByPhone(phoneNumber);
            if (contact == null) {
                System.out.println("Không tìm thấy liên hệ với số điện thoại này!");
            } else {
                System.out.println("Kết quả tìm kiếm:");
                displayContactDetails(contact);
            }
        } catch (IOException e) {
            System.out.println("Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    public void searchByName() {
        System.out.println("\n=== TÌM KIẾM THEO TÊN ===");
        System.out.print("Nhập tên (có thể nhập một phần): ");
        String name = scanner.nextLine().trim();

        try {
            List<Contact> contacts = contactServiceIml.findByName(name);
            if (contacts.isEmpty()) {
                System.out.println("Không tìm thấy liên hệ nào với tên này!");
            } else {
                System.out.println("Tìm thấy " + contacts.size() + " liên hệ:");
                System.out.printf("%-15s %-10s %-20s %-10s %-30s%n", 
                    "SỐ ĐIỆN THOẠI", "NHÓM", "HỌ TÊN", "GIỚI TÍNH", "ĐỊA CHỈ");
                System.out.println("-".repeat(90));
                
                for (Contact contact : contacts) {
                    System.out.printf("%-15s %-10s %-20s %-10s %-30s%n",
                        contact.getPhoneNumber(),
                        contact.getGroup(),
                        contact.getFullName(),
                        contact.getGender(),
                        contact.getAddress());
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    private void displayContactDetails(Contact contact) {
        System.out.println("------------------------");
        System.out.println("Số điện thoại: " + contact.getPhoneNumber());
        System.out.println("Nhóm: " + contact.getGroup());
        System.out.println("Họ tên: " + contact.getFullName());
        System.out.println("Giới tính: " + contact.getGender());
        System.out.println("Địa chỉ: " + contact.getAddress());
        System.out.println("Ngày sinh: " + contact.getDateOfbirth());
        System.out.println("Email: " + contact.getEmail());
        System.out.println("------------------------");
    }

    private String getInputOrDefault(String defaultValue) {
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? (defaultValue != null ? defaultValue : "") : input;
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public ContactServiceIml getContactService() {
        return contactServiceIml;
    }
}
