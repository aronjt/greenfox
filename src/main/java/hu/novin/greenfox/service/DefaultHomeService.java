package hu.novin.greenfox.service;

import hu.novin.greenfox.enums.Status;
import hu.novin.greenfox.domain.Customer;
import hu.novin.greenfox.domain.Item;
import hu.novin.greenfox.repository.CustomerRepository;
import hu.novin.greenfox.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DefaultHomeService implements HomeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHomeService.class);

    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    public DefaultHomeService(CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public List<Customer> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        LOGGER.info("all customers found: {} from database", customers.size());
        return customers;
    }

    @Override
    @Transactional
    public void addCustomer(Customer customer) {
        customer.setCreatedAt(LocalDateTime.now());
        Customer save = customerRepository.save(customer);
        LOGGER.info("{} id customer added to database", save.getId());
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
        LOGGER.info("{} id customer deleted from database", customerId);
    }

    @Override
    @Transactional
    public List<Item> getItems() {
        List<Item> items = itemRepository.findAll();
        LOGGER.info("all items found: {} from database", items.size());
        return items;
    }

    @Override
    @Transactional
    public void addItem(Item item) {
        item.setCreatedAt(LocalDateTime.now());
        Item save = itemRepository.save(item);
        LOGGER.info("{} id item added to database", save.getId());
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            customer.getItems().removeIf(item -> Objects.equals(item.getId(), itemId));
        }
        itemRepository.deleteById(itemId);
        LOGGER.info("{} id item deleted from database", itemId);
    }

    @Override
    @Transactional
    public void updateItemStatus(Long itemId, Status status) {
        Item item = itemRepository.findItemById(itemId);
        item.setStatus(status);
        LOGGER.info("{} id item status updated", itemId);
    }

    @Override
    @Transactional
    public void updateCustomerItem(Long itemId, Long fromCustomerId, Long toCustomerId) {
        Customer fromCustomer = customerRepository.findCustomerById(fromCustomerId);
        Customer toCustomer = customerRepository.findCustomerById(toCustomerId);
        Item item = itemRepository.findItemById(itemId);
        if (fromCustomer != null) {
            fromCustomer.getItems().remove(item);
        }
        if (toCustomer != null) {
            toCustomer.getItems().add(item);
        }
        LOGGER.info("{} id item from {} id customer added to {} id customer", itemId, fromCustomerId, toCustomerId);
    }
}
