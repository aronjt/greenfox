package hu.novin.greenfox.controller;

import hu.novin.greenfox.domain.Customer;
import hu.novin.greenfox.domain.Item;
import hu.novin.greenfox.enums.Status;
import hu.novin.greenfox.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("customers", homeService.getCustomers());
        model.addAttribute("items", homeService.getItems());
        model.addAttribute("customer", new Customer());
        model.addAttribute("item", new Item());
        return "home";
    }

    @PostMapping(path = "addCustomer")
    public String addCustomer(@ModelAttribute(value = "customer") Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        homeService.addCustomer(customer);
        return "redirect:/";
    }

    @GetMapping(path = "deleteCustomer")
    public String deleteCustomer(@RequestParam(value = "id") Long customerId) {
        homeService.deleteCustomer(customerId);
        return "redirect:/";
    }

    @PostMapping(path = "addItem")
    public String addItem(@ModelAttribute("newItem") Item item) {
        homeService.addItem(item);
        return "redirect:/";
    }

    @GetMapping(path = "deleteItem")
    public String deleteItem(@RequestParam(value = "id") Long itemId) {
        homeService.deleteItem(itemId);
        return "redirect:/";
    }

    @GetMapping(path = "updateItemStatus")
    public String updateItemStatus(@RequestParam(value = "id") Long itemId,
                                   @RequestParam(value = "status") Status status) {
        homeService.updateItemStatus(itemId, status);
        return "redirect:/";
    }

    @GetMapping(path = "updateCustomerItem")
    public String updateCustomerItem(@RequestParam(value = "itemId") Long itemId,
                                     @RequestParam(value = "fromCustomerId") Long fromCustomerId,
                                     @RequestParam(value = "toCustomerId") Long toCustomerId) {
        homeService.updateCustomerItem(itemId, fromCustomerId, toCustomerId);
        return "redirect:/";
    }
}
