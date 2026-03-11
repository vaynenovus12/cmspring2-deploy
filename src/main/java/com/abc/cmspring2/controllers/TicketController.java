package com.abc.cmspring2.controllers;

import com.abc.cmspring2.entities.Customer;
import com.abc.cmspring2.entities.Employee;
import com.abc.cmspring2.entities.Ticket;
import com.abc.cmspring2.entities.TicketStatus;
import com.abc.cmspring2.repositories.CustomerRepository;
import com.abc.cmspring2.repositories.EmployeeRepository;
import com.abc.cmspring2.repositories.TicketRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	private final TicketRepository ticketRepository;
	private final CustomerRepository customerRepository;

	// 1. Add EmployeeRepository to the constructor
	private final EmployeeRepository employeeRepository;

	public TicketController(TicketRepository ticketRepository, CustomerRepository customerRepository,
			EmployeeRepository employeeRepository) {
		this.ticketRepository = ticketRepository;
		this.customerRepository = customerRepository;
		this.employeeRepository = employeeRepository;
	}

	@GetMapping
	public String index(@RequestParam(value = "q", required = false) String q, Model model) {

		List<Ticket> tickets;

		// SIMPLE version (no custom JPQL required)
		// Just load everything for now
		tickets = ticketRepository.findAll();

		model.addAttribute("tickets", tickets);
		model.addAttribute("q", q);

		model.addAttribute("content", "/WEB-INF/views/tickets/index.jsp");
		return "layouts/main";
	}

	// 2. Update createForm to include employees
	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("customers", customerRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll()); // Add this
		model.addAttribute("statuses", TicketStatus.values());
		model.addAttribute("content", "/WEB-INF/views/tickets/form.jsp");
		return "layouts/main";
	}

	// 3. Update save method to handle employee assignment
	@PostMapping("/save")
	public String save(@ModelAttribute Ticket ticket, @RequestParam("customerId") Integer customerId,
			@RequestParam(value = "employeeId", required = false) Integer employeeId) {

		// Set Customer
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		ticket.setCustomer(customer);

		// Set Employee (if provided)
		if (employeeId != null) {
			Employee emp = employeeRepository.findById(employeeId).orElse(null);
			ticket.setAssignedEmployee(emp);
		}

		// Status logic for closedAt remains the same...
		if (ticket.getStatus() == TicketStatus.CLOSED) {
			if (ticket.getClosedAt() == null)
				ticket.setClosedAt(LocalDateTime.now());
		} else {
			ticket.setClosedAt(null);
		}

		ticketRepository.save(ticket);
		return "redirect:/tickets"; // Redirect to index after save
	}

	// Add to TicketController.java

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Integer id, Model model) {
	    Ticket ticket = ticketRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Ticket not found"));

	    model.addAttribute("ticket", ticket);
	    model.addAttribute("customers", customerRepository.findAll());
	    model.addAttribute("employees", employeeRepository.findAll());
	    model.addAttribute("statuses", TicketStatus.values());
	    
	    model.addAttribute("content", "/WEB-INF/views/tickets/form.jsp");
	    return "layouts/main";
	}

}
