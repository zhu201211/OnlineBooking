package com.cqut.OnlineMealBooking.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.service.CartService;

@Controller
@RequestMapping("/carts")
public class CartController {
    // ����service�Ķ���
    @Resource
    private CartService cartService;
    
    @RequestMapping("/addCartToUser")
    public void addCartToUser(String userId) {
	System.out.println("�����û��ͳ�");
	Cart cart=new Cart();
	String id = UUID.randomUUID().toString().substring(0, 4);
	cart.setId(id);
	cart.setTotalNum(0);
	cart.setTotalPrice(0);
	cart.setCartFoods(null);
	try {
	    cartService.addCart(cart);
	} catch (Exception e) {
	    // TODO: handle exception
	}
    }
    
    //��ղͳ�
    @RequestMapping("/emptyUserCart")
    public void emptyUserCart(String userId,HttpServletRequest request) {
	
    }
    
    //����ͳ�
    @RequestMapping("/addFoodToUserCart")
    public void addFoodToUserCart(String userId,String foodId,HttpServletRequest request) {
	
    }
   
}
