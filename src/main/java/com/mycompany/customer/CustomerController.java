package com.mycompany.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired private CustomerService service;
    
    @GetMapping("/users")
    public String showUserList(Model model) {
        List<Customer> listCustomers = service.listAll();
        model.addAttribute("listUsers", listCustomers);

        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new Customer());
        model.addAttribute("pageTitle", "Add New Customer");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(Customer customer, RedirectAttributes ra) {
        service.save(customer);
        ra.addFlashAttribute("message", "The customer has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Customer customer = service.get(id);
            model.addAttribute("user", customer);
            model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");

            return "user_form";
        } catch (CustomerNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The user ID " + id + " has been deleted.");
        } catch (CustomerNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }
}
