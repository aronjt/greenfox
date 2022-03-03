package hu.novin.greenfox.configuration;

import hu.novin.greenfox.enums.Status;
import hu.novin.greenfox.domain.Customer;
import hu.novin.greenfox.domain.Item;
import hu.novin.greenfox.domain.User;
import hu.novin.greenfox.repository.CustomerRepository;
import hu.novin.greenfox.repository.ItemRepository;
import hu.novin.greenfox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    private final Set<Item> itemList1 = new HashSet<>();
    private final Set<Item> itemList2 = new HashSet<>();

    @Autowired
    public DataLoader(UserRepository userRepository, ItemRepository itemRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        createUser();
        createItems();
        createCustomers();
    }

    private void createUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("password"));
        user.setName("Admin");
        user.setEmail("admin@admin.com");
        userRepository.save(user);
    }

    private void createItems() {
        Item item1 = new Item();
        item1.setName("Item1");
        item1.setCreatedAt(LocalDateTime.now());
        item1.setComment("Comment1");
        item1.setPrice(new BigDecimal(100));
        item1.setStatus(Status.OPEN);

        Item item2 = new Item();
        item2.setName("Item2");
        item2.setCreatedAt(LocalDateTime.now());
        item2.setComment("Comment2");
        item2.setPrice(new BigDecimal(200));
        item2.setStatus(Status.IN_PROGRESS);

        Item item3 = new Item();
        item3.setName("Item3");
        item3.setCreatedAt(LocalDateTime.now());
        item3.setComment("Comment3");
        item3.setPrice(new BigDecimal(300));
        item3.setStatus(Status.CLOSED);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        itemList1.add(item1);
        itemList1.add(item2);
        itemList2.add(item3);
    }

    private void createCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("Customer1");
        customer1.setCreatedAt(LocalDateTime.now());
        customer1.setItems(itemList1);

        Customer customer2 = new Customer();
        customer2.setName("Customer2");
        customer2.setCreatedAt(LocalDateTime.now());
        customer2.setItems(itemList2);

        customerRepository.save(customer1);
        customerRepository.save(customer2);

    }
}
