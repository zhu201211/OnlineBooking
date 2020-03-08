package com.cqut.OnlineMealBooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/foods")
public class FoodController {
    
    //查询热销菜品（按销量多少排序）
    @RequestMapping("/getFoodLists")
    private ModelAndView getFoodLists() {
	return null;
    }
}
