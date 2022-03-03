package hu.novin.greenfox.service;

import hu.novin.greenfox.enums.Status;
import hu.novin.greenfox.domain.Customer;
import hu.novin.greenfox.domain.Item;

import java.util.List;

public interface HomeService {

    List<Customer> getCustomers();
    void addCustomer(Customer customer);
    void deleteCustomer(Long customerId);
    List<Item> getItems();
    void addItem(Item item);
    void deleteItem(Long itemId);
    void updateItemStatus(Long itemId, Status status);
    void updateCustomerItem(Long itemId, Long fromCustomerId, Long toCustomerId);
}
