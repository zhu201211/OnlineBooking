package com.cqut.OnlineMealBooking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.service.MerchantService;

@Controller
@RequestMapping("/merchants")
public class MerchantController {
    @Resource
    private MerchantService merchantService;
    
    //查询人气商家 （按人气量从高到低排序）
    @RequestMapping("/getMerchantLists")
    private ModelAndView getMerchantLists(HttpServletRequest request) {
	System.out.println("人气商家：");
	ModelAndView mav = new ModelAndView();
	    List<Merchant> merchants=new ArrayList<>();
	    merchants =  merchantService.getMerchantLists();
	    for (Merchant merchant : merchants) {
		System.out.println(merchant.getMerchantPop());
	    }
	    mav.addObject("merchantss",merchants);
	    mav.setViewName("/index.jsp");
	    return mav;
    }
    
}
