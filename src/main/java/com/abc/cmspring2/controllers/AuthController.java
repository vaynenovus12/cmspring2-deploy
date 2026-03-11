package com.abc.cmspring2.controllers;

import com.abc.cmspring2.entities.UserAccount;
import com.abc.cmspring2.repositories.UserAccountRepository;
import com.abc.cmspring2.securities.PasswordUtil;
import com.abc.cmspring2.securities.SessionUser;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController 
{

    public static final String SESSION_USER = "LOGGED_IN_USER";

    private final UserAccountRepository userAccountRepository;

    public AuthController(UserAccountRepository userAccountRepository) 
    {
        this.userAccountRepository = userAccountRepository;
    }

    @GetMapping({"/","/login"})
    public String loginForm(Model model) 
    {
        return "auth/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpSession session,
                          Model model) 
    {

        String u = username == null ? "" : username.trim().toLowerCase();
        UserAccount account = userAccountRepository.findByUsername(u).orElse(null);
        if (account == null || account.getIsActive() == null || !account.getIsActive()) 
        {
            model.addAttribute("error", "Invalid login.");
            model.addAttribute("content", "/WEB-INF/views/auth/login.jsp");
            return "layouts/main";
        }
        if (!PasswordUtil.matches(password, account.getPasswordHash())) 
        {
            model.addAttribute("error", "Invalid login.");
            return "auth/login";
        }

        SessionUser su = new SessionUser();
        su.setUserId(account.getUserId());
        su.setUsername(account.getUsername());
        su.setRole(account.getRole());
        su.setMustChangePassword(Boolean.TRUE.equals(account.getMustChangePassword()));
        if (account.getEmployee() != null) su.setEmployeeId(account.getEmployee().getEmployeeId());
        if (account.getCustomer() != null) su.setCustomerId(account.getCustomer().getCustomerId());
        session.setAttribute(SESSION_USER, su);
        if (su.isMustChangePassword()) 
        {
            return "redirect:/change-password";
        }
        return "redirect:/tickets";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) 
    {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/change-password")
    public String changePasswordForm(HttpSession session, Model model) 
    {

        SessionUser su = (SessionUser) session.getAttribute(SESSION_USER);
        if (su == null) return "redirect:/login";
        model.addAttribute("content", "/WEB-INF/views/auth/change_password.jsp");
        return "layouts/main";
    }

    @PostMapping("/change-password")
    public String doChangePassword(@RequestParam("currentPassword") String currentPassword,
                                   @RequestParam("newPassword") String newPassword,
                                   @RequestParam("confirmPassword") String confirmPassword,
                                   HttpSession session,
                                   Model model) 
    {

        SessionUser su = (SessionUser) session.getAttribute(SESSION_USER);
        if (su == null) return "redirect:/login";
        if (newPassword == null || newPassword.length() < 8) 
        {
            model.addAttribute("error", "New password must be at least 8 characters.");
            model.addAttribute("content", "/WEB-INF/views/auth/change_password.jsp");
            return "layouts/main";
        }

        if (!newPassword.equals(confirmPassword)) 
        {
            model.addAttribute("error", "New password and Confirm password do not match.");
            model.addAttribute("content", "/WEB-INF/views/auth/change_password.jsp");
            return "layouts/main";
        }

        UserAccount account = userAccountRepository.findById(su.getUserId())
                .orElse(null);
        if (account == null) 
        {
            session.invalidate();
            return "redirect:/login";
        }

        if (!PasswordUtil.matches(currentPassword, account.getPasswordHash())) 
        {
            model.addAttribute("error", "Current password is incorrect.");
            model.addAttribute("content", "/WEB-INF/views/auth/change_password.jsp");
            return "layouts/main";
        }

        account.setPasswordHash(PasswordUtil.hash(newPassword));
        account.setMustChangePassword(false);
        userAccountRepository.save(account);
        su.setMustChangePassword(false);
        session.setAttribute(SESSION_USER, su);
        return "redirect:/tickets";
    }
}

