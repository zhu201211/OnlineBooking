package com.cqut.OnlineMealBooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/foods")
public class FoodController {
    
    //��ѯ������Ʒ����������������
    @RequestMapping("/getFoodLists")
    private ModelAndView getFoodLists() {
	return null;
    }
}
