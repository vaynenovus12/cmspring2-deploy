package com.abc.cmspring2.controllers;

import com.abc.cmspring2.entities.Customer;
import com.abc.cmspring2.entities.UserAccount;
import com.abc.cmspring2.entities.UserRole;
import com.abc.cmspring2.repositories.CustomerRepository;
import com.abc.cmspring2.repositories.UserAccountRepository;
import com.abc.cmspring2.securities.PasswordUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerRepository repo;
	private final UserAccountRepository userRepo;
	public CustomerController(CustomerRepository repo, UserAccountRepository userRepo) {
		this.repo = repo;
		this.userRepo = userRepo;
	}

	@GetMapping
	public String index(@RequestParam(name = "q", required = false) String q, Model model) {

		if (q != null && !q.trim().isEmpty()) {
			// Updated method call to match new Repository method
			model.addAttribute("customers", repo.findByNameContainingIgnoreCase(q.trim()));
			model.addAttribute("q", q.trim());
		} else {
			model.addAttribute("customers", repo.findAll());
			model.addAttribute("q", "");
		}

		model.addAttribute("content", "/WEB-INF/views/customers/index.jsp");
		return "layouts/main";
	}

	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("customer", new Customer());
		model.addAttribute("content", "/WEB-INF/views/customers/form.jsp");
		return "layouts/main";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute Customer customer) {

		boolean isNew = (customer.getCustomerId() == null);

		Customer saved = repo.save(customer);

		if (isNew) {
			String username = saved.getEmail().trim().toLowerCase();

			if (!userRepo.existsByUsername(username)) {

				UserAccount ua = new UserAccount();
				ua.setUsername(username);
				ua.setPasswordHash(PasswordUtil.hash("ChangeMe@123"));
				ua.setRole(UserRole.CUSTOMER);
				ua.setCustomer(saved);
				ua.setEmployee(null);

				userRepo.save(ua);
			}
		}

		return "redirect:/customers";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Customer customer = repo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
		model.addAttribute("customer", customer);
		model.addAttribute("content", "/WEB-INF/views/customers/form.jsp");
		return "layouts/main";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
		return "redirect:/customers";
	}

}
