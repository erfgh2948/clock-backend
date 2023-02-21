package com.ex.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.backend.entity.Items;
import com.ex.backend.repository.ItemRepository;

@RestController
public class ItemController {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	@GetMapping("/api/items")
	public List<Items> getItems(){
		List<Items> items = itemRepository.findAll();
		
		return items;
	}
}
