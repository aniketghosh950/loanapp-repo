package com.example.loanapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loanapp.model.ApplyLoan;
import com.example.loanapp.model.Item;
import com.example.loanapp.model.Loan;
import com.example.loanapp.model.User;
import com.example.loanapp.model.UserCard;
import com.example.loanapp.model.UserIssue;
import com.example.loanapp.model.UserLogin;
import com.example.loanapp.repository.ItemRepository;
import com.example.loanapp.repository.LoanRepository;
import com.example.loanapp.repository.UserCardRepository;
import com.example.loanapp.repository.UserIssueRepository;
import com.example.loanapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	LoanRepository loanRepo;
	@Autowired
	UserCardRepository userCardRepo;
	@Autowired
	ItemRepository itemRepo;
	@Autowired
	UserIssueRepository userIssueRepo;
	
	public Optional<User> fetchUser(String empId) {
		Optional<User> user = userRepo.findById(empId);
		return user;
	}
	
	public List<User> fetchUsers() {
		return userRepo.fetchAllUsers();
	}
	
	public String saveUser(User u) {
		String result="";
		
		User obj = null;
		Optional<User>optional = userRepo.findById(u.getId());
		
		if(optional.isPresent()) {
			result="User already exists.";
		}
		else {
			obj = userRepo.save(u);
			if(obj!=null)
				result = "User saved successfuly.";
			else
				result = "Registration failed!";
		}
		
		return result;
	}
	
	public String loginUser(UserLogin u) {
		String result="";
		User user = null;

		Optional<User>optional = userRepo.findById(u.getId());
		
		if(optional.isEmpty()) {
			result = "Invalid Employee";
		}
		else {
			user = optional.get();
			if(user.getPassword().equals(u.getPassword())) {
				result = "Login Success";
			}
			else {
				result = "Login Failed";
			}
		}
		
		return result;
	}
	
	public List<User> findAllCards(String emp_id){
		return userRepo.findAllCards(emp_id);
	}
	
	@Transactional
	public String applyLoan(ApplyLoan applyLoan) {
		String res="";
		User user = null;
		Optional<User> opt = userRepo.findById(applyLoan.getId());
		if (opt.isPresent()) user = opt.get();
		int lid = loanRepo.findByLoanType(applyLoan.getItemCategory());
		Loan loan = loanRepo.findById(lid).get();
		String cid = UUID.randomUUID().toString().replace("-", "");
		UserCard usercard = new UserCard();
		usercard.setRow_id(cid);
		usercard.setUser(user);
		usercard.setLoan(loan);
		usercard.setIssueDate(LocalDate.now());	
		Optional<UserCard> optional = userCardRepo.findById(cid);
		if(optional.isEmpty()) {
			usercard=userCardRepo.save(usercard);
			res = "User Card Details added Successfully\n";
		}
		else {
			res="Card Already Exists";
		}
		
		//inserting into issue table
		Random r = new Random();
		int iid = r.nextInt(100000-500+1)+500;
		UserIssue userIssue=new UserIssue();
		LocalDate i_dt = LocalDate.now();
		Item item = itemRepo.findByMakeCategory(applyLoan.getItemCategory(), applyLoan.getItemMake());
		System.out.println("item id"+item.getItemId());
		userIssue.setUser(user);
		userIssue.setIssueDate(i_dt);
		userIssue.setIssueId(iid);
		userIssue.setItem(item);
		
		Optional<UserIssue> opt2 = userIssueRepo.findById(iid);
		if(opt2.isEmpty()) {
			userIssue = userIssueRepo.save(userIssue);
			res = res + "User issue details added succesfully";
			}
		else {
			res = res + "Card already exists";
		}
		return res;
	}
}
