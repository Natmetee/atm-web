package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.atm.model.Customer;
import th.ac.ku.atm.service.BankAccountService;
import th.ac.ku.atm.service.CustomerService;

@Controller
@RequestMapping("/login")
public class LoginController {
    private CustomerService customerService;
    private BankAccountService bankAccountService;

    public LoginController(CustomerService customerService,BankAccountService bankAccountService) {
        this.customerService = customerService;
        this.bankAccountService = bankAccountService;
    }


    @GetMapping
    public String getLoginPage() {
        return "login";   // return login.html
    }
    @PostMapping
    public String login(@ModelAttribute Customer customer, Model model){
        Customer matchingCustomer = customerService.checkPin(customer);
        if (matchingCustomer != null) {
            model.addAttribute("customerTitle",
                    matchingCustomer.getName() + " Bank Accounts");
            model.addAttribute("bankAccounts",
                    bankAccountService.getCustomerBankAccount(customer.getId()));
            return "customerAccount";

        } else {
            // 3. ถ้าไม่ตรง แจ้งว่าไม่มีข้อมูล customer นี้
            model.addAttribute("greeting", "Can't find customer");
            return "home";
        }

    }
}




