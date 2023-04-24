package com.mycompany;

import com.mycompany.customer.Customer;
import com.mycompany.customer.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {
    @Autowired private CustomerRepository repo;

    @Test
    public void testAddNew() {
        Customer customer = new Customer();
        customer.setEmail("alex.stevenson@gmail.com");
        customer.setPassword("alex123456");
        customer.setFirstName("Alex");
        customer.setLastName("Stevenson");

        Customer savedCustomer = repo.save(customer);

        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<Customer> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (Customer customer : users) {
            System.out.println(customer);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<Customer> optionalUser = repo.findById(userId);
        Customer customer = optionalUser.get();
        customer.setPassword("hello2000");
        repo.save(customer);

        Customer updatedCustomer = repo.findById(userId).get();
        Assertions.assertThat(updatedCustomer.getPassword()).isEqualTo("hello2000");
    }

    @Test
    public void testGet() {
        Integer userId = 2;
        Optional<Customer> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer userId = 2;
        repo.deleteById(userId);

        Optional<Customer> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
