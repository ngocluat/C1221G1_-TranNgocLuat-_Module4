package com.furama.controller;

import com.furama.dto.CustomerDto;
import com.furama.model.Customer;
import com.furama.service.ICustomerService;
import com.furama.service.ICustomerTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller

@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private ICustomerTypeService iCustomerTypeService;

    @GetMapping("")
    public ModelAndView goListCusstomer(
            Model model,
            @RequestParam Optional<String> nameCustomerSeach,
            @RequestParam Optional<String> emailCustomerSearch,
            @RequestParam Optional<String> typeCustomer,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer pageSize,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> dir
    ) {

        Pageable pageable;
        String name = nameCustomerSeach.orElse("");
        String email = emailCustomerSearch.orElse("");
        String type = typeCustomer.orElse("%");
        String sortVal = sort.orElse("");
        String dirVal = dir.orElse("");

        if ("".equals(sortVal)) {
            pageable = PageRequest.of(page, pageSize);
        } else {
            if (dir.equals("asc")) {
                pageable = PageRequest.of(page, pageSize, Sort.by(sortVal).ascending());
            } else {
                pageable = PageRequest.of(page, pageSize, Sort.by(sortVal).descending());
            }
        }
        model.addAttribute("customerType", iCustomerTypeService.findAll());
        ModelAndView modelAndView = new ModelAndView("customer/home");
        Page<Customer> customer = iCustomerService.findAllAndSearch(name, email, type, pageable);
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("sort", sortVal);
        modelAndView.addObject("dir", dirVal);
        return modelAndView;
    }

    @GetMapping("/create-customer")
    public String goCreateCustomer(Model model) {

        model.addAttribute("customerDto", new CustomerDto());
        model.addAttribute("customerType", iCustomerTypeService.findAll());
        return "customer/create";
    }

    @PostMapping("/create-customer")
    public String createCustomer(@Validated @ModelAttribute CustomerDto customerDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, Model model
    ) {
        // kh??ng tr??ng l???p
        customerDto.setListPhone(iCustomerService.getListPhone());
        customerDto.setListEmail(iCustomerService.getListEmail());

        new CustomerDto().validate(customerDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerType", iCustomerTypeService.findAll());
            return "customer/create";
        } else {
            Customer customer = new Customer();
            BeanUtils.copyProperties(customerDto, customer);
            customer.setFlag(1);
            iCustomerService.save(customer);
            redirectAttributes.addFlashAttribute("message", "successfully added new");
            return "redirect:/customer";
        }
    }


    @GetMapping("/{id}/edit")
    public String goEditCustomer(@PathVariable String id, Model model) {
        Customer customer = iCustomerService.findById(id);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);

        model.addAttribute("customerDto", customerDto);


        return "customer/edit";
    }


    @PostMapping("/update-customer")
    public String editCustomer(@Validated @ModelAttribute CustomerDto customerDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasFieldErrors()) {
//            model.addAttribute("customerDto", customerDto);
            model.addAttribute("customerType", iCustomerTypeService.findAll());
            return "customer/edit";
        } else {
            Customer customer = new Customer();
            BeanUtils.copyProperties(customerDto, customer);
            iCustomerService.update(customer);
            redirectAttributes.addFlashAttribute("message", "ch???nh s???a th??nh c??ng");
            return "redirect:/customer";
        }

    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(@RequestParam String id, RedirectAttributes redirectAttributes) {
        Customer customerDelete = iCustomerService.findById(id);
        iCustomerService.remove(customerDelete);
        redirectAttributes.addFlashAttribute("message", "Delete successfully");
        return "redirect:/customer";
    }

    @GetMapping("/deleteMany")
    public String deleteCustomerMany(@RequestParam("cb") Integer[] cb, RedirectAttributes redirectAttributes) {
        iCustomerService.deleteByIdIn(cb);
        redirectAttributes.addFlashAttribute("message", "Delete successfully");
        return "redirect:/customer";
    }
}
