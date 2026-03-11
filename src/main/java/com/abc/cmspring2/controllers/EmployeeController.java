package com.abc.cmspring2.controllers;

import com.abc.cmspring2.entities.Employee;
import com.abc.cmspring2.entities.UserAccount;
import com.abc.cmspring2.entities.UserRole;
import com.abc.cmspring2.repositories.EmployeeRepository;
import com.abc.cmspring2.repositories.UserAccountRepository;
import com.abc.cmspring2.securities.PasswordUtil;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repo;
    private final UserAccountRepository userRepo;

    public EmployeeController(EmployeeRepository repo, UserAccountRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String index(@RequestParam(name = "q", required = false) String q,
                        Model model) {

        if (q != null && !q.trim().isEmpty()) {
            model.addAttribute("employees", repo.findByNameContainingIgnoreCase(q.trim()));
            model.addAttribute("q", q.trim());
        } else {
            model.addAttribute("employees", repo.findAll());
            model.addAttribute("q", "");
        }
        
        model.addAttribute("content", "/WEB-INF/views/employees/index.jsp");
        return "layouts/main";
    }
    
    @GetMapping("/new")
    public String createForm(Model model) {
        // 1. Create a blank employee object
        model.addAttribute("employee", new Employee());
        
        // 2. Set the view content
        model.addAttribute("content", "/WEB-INF/views/employees/form.jsp");
        
        // 3. Return the main layout
        return "layouts/main";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee) {

        boolean isNew = (employee.getEmployeeId() == null);

        // save employee first to get generated ID
        Employee saved = repo.save(employee);

        if (isNew) {
            String username = saved.getEmail().trim().toLowerCase();

            // Only create if doesn't exist already
            if (!userRepo.existsByUsername(username)) {

                UserAccount ua = new UserAccount();
                ua.setUsername(username);

                // temporary password (Phase 1)
                ua.setPasswordHash(PasswordUtil.hash("ChangeMe@123"));
                ua.setRole(UserRole.EMPLOYEE);
                ua.setEmployee(saved);
                ua.setCustomer(null);

                userRepo.save(ua);
            }
        }

        return "redirect:/employees";
    }
    
 // 1. Display the Edit Form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Employee employee = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        model.addAttribute("employee", employee);
        model.addAttribute("content", "/WEB-INF/views/employees/form.jsp");
        return "layouts/main";
    }

    @Transactional // Import from org.springframework.transaction.annotation.Transactional
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        // 1. Find the linked UserAccount
        UserAccount user = userRepo.findByEmployeeEmployeeId(id);
        
        // 2. If it exists, delete it first
        if (user != null) {
            userRepo.delete(user);
        }
        
        // 3. Now it is safe to delete the Employee
        repo.deleteById(id);
        
        return "redirect:/employees";
    }
}