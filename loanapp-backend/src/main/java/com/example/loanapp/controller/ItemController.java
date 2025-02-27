package com.example.loanapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.loanapp.model.Item;
import com.example.loanapp.service.ItemService;

@RestController
@CrossOrigin("*")
class ItemController {
	
	@Autowired
	ItemService itemService;
	
	
	@PostMapping("/saveItem")
	public String itemType(@RequestBody Item i) {
		
		String result = "";
		result = itemService.saveItem(i);
		
		return result;
		
	}

	@GetMapping("/allItems")
	public List<Item> getAllItems() {
		return itemService.getAllItems();
	}
	
	@GetMapping("/itemById/{itemno}")
	public Item getItemById(@PathVariable("itemno")  int ino)
	{
		return itemService.getItemById(ino);
	}
	
	@GetMapping("/allItemCategories")
	public List<String> getAllTypes() {
		return itemService.getItemBytype();
	}
	
	// Get List of Item Make as per Item Category selected
	@GetMapping("/{category}/getAllMake") 
	public List<String> getDistinctMakesByCategory(@PathVariable String category) 
	{
		return itemService.getDistinctMakesByCategory(category);
	}
	
	// Get Item Descriptions as per Item Category and Item Make
	@GetMapping("/{category}/{make}/getAllDescriptions")
	public List<String> getDistinctDescriptionByMakeAndCategory(@PathVariable String category, @PathVariable String make)
	{
		return itemService.getDistinctDescriptionByMakeAndCategory(category, make);
	}
	
	// Get Item by Category, Make and Description
	@GetMapping("/{category}/{make}/{description}/getItem")
	public Item getItemByMakeAndCategoryAndDescription(@PathVariable String category, @PathVariable String make, @PathVariable String description) 	
	{
		Item item = itemService.getItemByMakeAndCategoryAndDescription(category, make, description);
		
		return item;
	}
	
	@GetMapping("/allItemMakes")
	public List<String> getAllMakes() {
		return itemService.getItemByMake();
	}
	
	@GetMapping("/viewItems/{emp_id}")
	public List<Map<String,Object>> getAllItemsByEmpId(@PathVariable("emp_id") String emp_id)
	{
		return itemService.getAllItemsByEmpId(emp_id);
	}
	
}