package com.example.loanapp.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.loanapp.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	@Query("SELECT item FROM Item item WHERE item.itemCategory=?1 AND item.itemMake=?2")
	public Item findByMakeCategory(String itemCategory, String itemMake);
	
	@Query("SELECT DISTINCT i.itemMake FROM Item as i WHERE i.itemCategory = :category")
	List<String> getDistinctMakesByCategory(String category);
	
	@Query("SELECT DISTINCT i.itemDescription FROM Item as i WHERE i.itemCategory = :category AND i.itemMake = :make")
	List<String> getDistinctDescriptionByMakeAndCategory(String category, String make);
	
	@Query("SELECT i FROM Item as i WHERE i.itemCategory = :category AND i.itemMake = :make AND i.itemDescription = :description")
	Item getItemByMakeAndCategoryAndDescription(String category, String make, String description);
	
	@Query("SELECT iss.issueId, it.itemDescription, it.itemMake, it.itemCategory, it.itemValue FROM UserIssue iss INNER JOIN Item it ON it.itemId = iss.item.itemId WHERE iss.id=?1")
	public List<Map<String,Object>> getAllItemsByEmpId(String emp_id);
}