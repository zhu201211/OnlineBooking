package com.cqut.OnlineMealBooking.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cqut.OnlineMealBooking.pojo.*;
import com.cqut.OnlineMealBooking.service.CartService;
import com.cqut.OnlineMealBooking.service.FoodService;
import com.cqut.OnlineMealBooking.service.IntentService;
import com.cqut.OnlineMealBooking.service.MerchantService;
import com.cqut.OnlineMealBooking.service.MyFoodsService;
import com.cqut.OnlineMealBooking.service.UserService;
import com.cqut.OnlineMealBooking.utils.FoodUtil;
import com.cqut.OnlineMealBooking.utils.RecommendUserCf;
import com.cqut.OnlineMealBooking.utils.SendCode;

@Controller
@RequestMapping("/users")
public class UserController {
    
    // ����service�Ķ���
    @Resource
    private UserService userService;
    @Resource
    private CartService cartService;
    @Resource
    private MerchantService merchantService;
    @Resource
    private FoodService foodService;
    @Resource
    private IntentService intentService;

    @Resource
    private MyFoodsService myfoodsService;
    
    @RequestMapping("/login_1")
    public ModelAndView login_1(String  userPhone,String userPassword,HttpServletRequest request) {
	System.out.println("�û���¼��"+userPhone+"+"+userPassword);
	ModelAndView mav = new ModelAndView();
	Message msg=new Message();
	User user1=userService.loginByPhone(userPhone,userPassword);
	if (user1==null||user1.getUserName().equals("admin")) {
	    System.out.println("��¼ʧ��");
	    msg.setMsg("�û��������������");
	    mav.addObject("msg", msg);
	    mav.setViewName("/login.jsp");
	    return mav; 
	    
	} else {
	    
	    System.out.println("��¼�ɹ�");
	   
	    //��ȡ��ҳ�������̼Һ�������Ʒ
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("merchantss",merchants);
	    mav.addObject("foods",foods);
	    //��ʼ���ͳ�
	    Cart cart=user1.getCart();
	    cart.setTotalNum(0);
	    cart.setTotalPrice(0);
	    cart.setCartFoods(null);
	    cartService.updateCart(cart);
	    cartService.deleteCartFoodByCart(cart);
	    mav.addObject("cart",cart);
//	    request.getSession().setAttribute("cart", cart);
	    
	   
	    mav.addObject("msg", "��¼�ɹ�");
	    request.getSession().setAttribute("u", user1);//sesiion�����û�
	    mav.setViewName("/index.jsp");
	    
	    //��ȡ�����û�����
	    List<User> users=userService.getUserList();
	    List<MyFoods> myFoods=myfoodsService.getMyFoodsList(user1);
	    user1.setMyFoodList(myFoods);
	    List<Favorite> fs=userService.findAllFavorites(user1);
	    user1.setFavorites(fs);
	    List<Food> RecommendFoodsByBuy=new ArrayList<>();
	    List<Food> RecommendFoodsByFavorite=new ArrayList<>();
	    System.out.println("RecommendFoodsByBuy:"+RecommendFoodsByBuy.size());
	    System.out.println("RecommendFoodsByFavorite:"+RecommendFoodsByFavorite.size());
	    if(user1.getFavorites().size()>0) {
		//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
		List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user1);
		//��ȡ�Ƽ��û�����
		List<User> uList2=RecommendUserCf.getUserList(s2);
		//��ȡ�Ƽ���Ʒ����
		RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user1);
	    }
	    if (user1.getMyFoodList().size()>0) {
		//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
		List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user1);
		//��ȡ�Ƽ��û�����
		List<User> uList1=RecommendUserCf.getUserList(s1);
		//��ȡ�Ƽ���Ʒ����
		RecommendFoodsByBuy=RecommendUserCf.getFoodsListByBuy(uList1, user1);
	    }
	    int j=0;
	    if (RecommendFoodsByBuy.size()<4) {
		j=RecommendFoodsByBuy.size();
		for (Food food : foods) {
		    RecommendFoodsByBuy.add(food);
		    j++;
		    if (j>=4) {
			break;
		    }
		}
	    }
	    if (RecommendFoodsByFavorite.size()<4) {
		int k=RecommendFoodsByFavorite.size();
		for (Food food : foods) {
		    RecommendFoodsByFavorite.add(foods.get(j++));
		    k++;
		    if (k>4) {
			break;
		    }
		}
	    }
	    System.out.println("���ݹ�����ʷ�Ƽ��б�");
	    for (Food food : RecommendFoodsByBuy) {
		System.out.println("f:"+food.getFoodName());
	    }
	    System.out.println("�����ղ��Ƽ��б�");
	    for (Food food : RecommendFoodsByFavorite) {
		System.out.println("f:"+food.getFoodName());
	    }
	    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoodsΪfood�Ƽ�����
	    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoodsΪfood�Ƽ�����
	    return mav;
	}
    }
    
    //��¼������֤��
    @RequestMapping("/sendCode")
    @ResponseBody
    public Message sendCode(String userPhone,HttpServletRequest request) {
	System.out.println(userPhone);
	User u=userService.findByPhone(userPhone);
	Message msg = new Message();
	JSONObject jsonObject=new JSONObject();
	if (u==null) {
	    msg.setMsg("�û�������");
	    return msg;
	} else {
	    jsonObject=SendCode.getJson(userPhone);
	    if (jsonObject.getString("msg")==null) {
		// ����֤�����SESSION
		System.out.println("��Ϣ��֤�룺"+jsonObject.getString("verifyCode"));
		request.getSession().setAttribute("verifyCode", jsonObject);	
		msg.setMsg("���Ͷ��ųɹ�����Ч��Ϊ������");
		return msg;
	    } else {
		System.out.println(jsonObject.getString("msg"));
		msg.setMsg(jsonObject.getString("msg"));
		return msg;
	    }
	}
    }
    
  //ע�ᷢ����֤��2
    @RequestMapping("/sendCode2")
    @ResponseBody
    public Message sendCode2(String userPhone,HttpServletRequest request) {
	System.out.println(userPhone);
	User u=userService.findByPhone(userPhone);
	Message msg = new Message();
	JSONObject jsonObject=new JSONObject();
	if (u!=null) {
	    msg.setMsg("�û��Ѵ��ڣ�������¼����ֱ�ӵ�¼");
	    return msg;
	} else {
	    jsonObject=SendCode.getJson(userPhone);//���Ͷ���
	    if (jsonObject.getString("msg")==null) {
		// ����֤�����SESSION
		System.out.println("��Ϣ��֤�룺"+jsonObject.getString("verifyCode"));
		request.getSession().setAttribute("verifyCode", jsonObject);	
		msg.setMsg("���Ͷ��ųɹ�����Ч��Ϊ������");
		return msg;
	    } else {
		System.out.println(jsonObject.getString("msg"));
		msg.setMsg(jsonObject.getString("msg"));
		return msg;
	    }
	}
	
    }
    
    //��¼������֤��
    @RequestMapping("/sendCode3")
    @ResponseBody
    public Message sendCode3(String userPhone,HttpServletRequest request) {
	System.out.println(userPhone);
	User u=userService.findByPhone(userPhone);
	Message msg = new Message();
	JSONObject jsonObject=new JSONObject();
	if (u!=null) {
	    msg.setMsg("�ֻ������Ѵ��ڻ�ú���Ϊ���˺ŵ�¼�ֻ�����");
	    return msg;
	} else {
	    jsonObject=SendCode.getJson(userPhone);
	    if (jsonObject.getString("msg")==null) {
		// ����֤�����SESSION
		System.out.println("��Ϣ��֤�룺"+jsonObject.getString("verifyCode"));
		request.getSession().setAttribute("verifyCode", jsonObject);	
		msg.setMsg("���Ͷ��ųɹ�����Ч��Ϊ������");
		return msg;
	    } else {
		System.out.println(jsonObject.getString("msg"));
		msg.setMsg(jsonObject.getString("msg"));
		return msg;
	    }
	}
    }
    
    
    @RequestMapping("/login_2")
    private ModelAndView login_2(String  userPhone,String code,HttpServletRequest request) {
	Message msg=new Message();
	System.out.println(userPhone);
	System.out.println(code);
	ModelAndView mav = new ModelAndView();
	User u=userService.findByPhone(userPhone);
	if (u==null) {
	    msg.setMsg("�û�������");
	    mav.addObject("msg", msg);
	    mav.setViewName("/login0.jsp");
	    return mav;
	}else {
	    JSONObject json = (JSONObject)request.getSession().getAttribute("verifyCode");
	    if(!json.getString("verifyCode").equals(code)){
		
		msg.setMsg("��֤�����");
		mav.addObject("msg", msg);
		mav.setViewName("/login0.jsp");
		return mav;
	    }else if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 2){
		msg.setMsg("��֤����ڣ������·��ͣ�");
		mav.addObject("msg", msg);
		mav.setViewName("/login0.jsp");
		return mav ;
	    }else {
		System.out.println("��¼�ɹ�");
		//��ȡ��ҳ�������̼Һ�������Ʒ
		List<Merchant> merchants=merchantService.getMerchantLists();
		List<Food> foods=foodService.getFoodList();
		mav.addObject("merchantss",merchants);
		mav.addObject("foods",foods);
		//��ʼ���ͳ�
		Cart cart=u.getCart();
		cart.setTotalNum(0);
		cart.setTotalPrice(0);
		cart.setCartFoods(null);
		cartService.updateCart(cart);
		cartService.deleteCartFoodByCart(cart);
		mav.addObject("cart",cart);
		
		mav.addObject("msg", "��¼�ɹ�");
		request.getSession().setAttribute("u", u);
		mav.setViewName("/index.jsp");
		
		 //��ȡ�����û�����
		    List<User> users=userService.getUserList();
		    List<MyFoods> myFoods=myfoodsService.getMyFoodsList(u);
		    u.setMyFoodList(myFoods);
		    List<Favorite> fs=userService.findAllFavorites(u);
		    u.setFavorites(fs);
		    List<Food> RecommendFoodsByBuy=new ArrayList<>();
		    List<Food> RecommendFoodsByFavorite=new ArrayList<>();
		    System.out.println("RecommendFoodsByBuy:"+RecommendFoodsByBuy.size());
		    System.out.println("RecommendFoodsByFavorite:"+RecommendFoodsByFavorite.size());
		    if(u.getFavorites().size()>0) {
			//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
			List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, u);
			//��ȡ�Ƽ��û�����
			List<User> uList2=RecommendUserCf.getUserList(s2);
			//��ȡ�Ƽ���Ʒ����
			RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, u);
		    }
		    if (u.getMyFoodList().size()>0) {
			//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
			List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, u);
			//��ȡ�Ƽ��û�����
			List<User> uList1=RecommendUserCf.getUserList(s1);
			//��ȡ�Ƽ���Ʒ����
			RecommendFoodsByBuy=RecommendUserCf.getFoodsListByBuy(uList1, u);
		    }
		    int j=0;
		    if (RecommendFoodsByBuy.size()<4) {
			j=RecommendFoodsByBuy.size();
			for (Food food : foods) {
			    RecommendFoodsByBuy.add(food);
			    j++;
			    if (j>=4) {
				break;
			    }
			}
		    }
		    if (RecommendFoodsByFavorite.size()<4) {
			int k=RecommendFoodsByFavorite.size();
			for (Food food : foods) {
			    RecommendFoodsByFavorite.add(foods.get(j++));
			    k++;
			    if (k>4) {
				break;
			    }
			}
		    }
		    System.out.println("���ݹ�����ʷ�Ƽ��б�");
		    for (Food food : RecommendFoodsByBuy) {
			System.out.println("f:"+food.getFoodName());
		    }
		    System.out.println("�����ղ��Ƽ��б�");
		    for (Food food : RecommendFoodsByFavorite) {
			System.out.println("f:"+food.getFoodName());
		    }
		    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoodsΪfood�Ƽ�����
		    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoodsΪfood�Ƽ�����
		return mav;
	    }	    
	}
    }
    
    @RequestMapping("/register")
    @ResponseBody
    public Message register(String userPhone,String code,String userPassword,HttpServletRequest request) {
	System.out.println(userPhone);
	Message msg = new Message();
	User u=userService.findByPhone(userPhone);
	if (u!=null) {
	    msg.setMsg("�û��Ѵ���");
	    return msg;
	}else {
	    JSONObject json = (JSONObject)request.getSession().getAttribute("verifyCode");
	    if(!json.getString("verifyCode").equals(code)){
		 msg.setMsg("��֤�����");
		 return msg;
		 
	    }else if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 2) {
		msg.setMsg("��֤����ڣ������·��ͣ�");
		return msg;
	    }else {
		User user =new User();
		user.setUserPhone(userPhone);
		user.setUserPassword(userPassword);
		//Ϊ�û�����ͳ�
		Cart cart=new Cart();
		String id = UUID.randomUUID().toString().substring(0, 4);
		cart.setId(id);
		cart.setTotalNum(0);
		cart.setTotalPrice(0);
		cart.setCartFoods(null);
		cartService.addCart(cart);
		user.setCart(cart);
		try {
		    msg=userService.addUser(user);
		    return msg;
		} catch (Exception e) {
		    // TODO: handle exception
		    msg.setMsg("ϵͳ�쳣");
		    return msg;
		}
	    }
	    
	}
	
    }
    //��Ʒ�������ͳ�
    @RequestMapping("/addFoodToCart")
    @ResponseBody
    public ModelAndView addFoodToCart(String foodId,String flag,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=new Message();	
	User u=(User) request.getSession().getAttribute("u");
	User user=userService.findByPhone(u.getUserPhone());
	if (user==null) {
	    msg.setMsg("���ȵ�¼");
	    mav.addObject("msg", msg);
	    
	    mav.setViewName("/login.jsp");
	    return mav;
	} else {
	    Food food=foodService.findFoodById(foodId);
            if (food==null) {
        	mav.addObject("msg", "����Ʒ�����ڻ����¼ܣ�������ѡ��");
        	mav.setViewName("/index.jsp");
        	return mav;
            }else{
        	CartFood cartFood=cartService.addCartFood(user.getCart().getId(),food);
        	if (cartFood==null) {
        	    System.out.println("���ʧ��");
	    	    mav.addObject("msg", "���ʧ��");
	    	    mav.setViewName("/index.jsp");
	    	    return mav;
		} else {
		    System.out.println("��ӳɹ�");	
		    mav.addObject("msg", "��ӳɹ�");
		    List<CartFood> cartFoods=cartService.findCartFoodListByCartId(user.getCart().getId());
		    Cart cart=cartService.findCartById(user.getCart().getId());
		    cart.setCartFoods(cartFoods);
		    user.setCart(cart);
		    mav.addObject("cart",cart);
		    switch (flag) {
			case "1":
			    //��ȡ��ҳ�������̼Һ�������Ʒ
			    List<Merchant> merchants=merchantService.getMerchantLists();
			    List<Food> foods=foodService.getFoodList();
			    mav.addObject("merchantss",merchants);
			    mav.addObject("foods",foods);
			    //��ȡ�����û�����
			    List<User> users=userService.getUserList();
			    List<MyFoods> myFoods=myfoodsService.getMyFoodsList(user);
			    user.setMyFoodList(myFoods);
			    List<Favorite> fs=userService.findAllFavorites(user);
			    user.setFavorites(fs);
			    List<Food> RecommendFoodsByBuy=new ArrayList<>();
			    List<Food> RecommendFoodsByFavorite=new ArrayList<>();
			    System.out.println("RecommendFoodsByBuy:"+RecommendFoodsByBuy.size());
			    System.out.println("RecommendFoodsByFavorite:"+RecommendFoodsByFavorite.size());
			    if(user.getFavorites().size()>0) {
				//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
				List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user);
				//��ȡ�Ƽ��û�����
				List<User> uList2=RecommendUserCf.getUserList(s2);
				//��ȡ�Ƽ���Ʒ����
				RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user);
			    }
			    if (user.getMyFoodList().size()>0) {
				//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
				List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user);
				//��ȡ�Ƽ��û�����
				List<User> uList1=RecommendUserCf.getUserList(s1);
				//��ȡ�Ƽ���Ʒ����
				RecommendFoodsByBuy=RecommendUserCf.getFoodsListByBuy(uList1, user);
			    }
			    int j=0;
			    if (RecommendFoodsByBuy.size()<4) {
				j=RecommendFoodsByBuy.size();
				for (Food f : foods) {
				    RecommendFoodsByBuy.add(f);
				    j++;
				    if (j>=4) {
					break;
				    }
				}
			    }
			    if (RecommendFoodsByFavorite.size()<4) {
				int k=RecommendFoodsByFavorite.size();
				for (Food f : foods) {
				    RecommendFoodsByFavorite.add(foods.get(j++));
				    k++;
				    if (k>4) {
					break;
				    }
				}
			    }
			    System.out.println("���ݹ�����ʷ�Ƽ��б�");
			    for (Food f : RecommendFoodsByBuy) {
				System.out.println("f:"+f.getFoodName());
			    }
			    System.out.println("�����ղ��Ƽ��б�");
			    for (Food f : RecommendFoodsByFavorite) {
				System.out.println("f:"+f.getFoodName());
			    }
			    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoodsΪfood�Ƽ�����
			    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoodsΪfood�Ƽ�����
			    mav.setViewName("/index.jsp");
			    break;
			case "2":
			    Merchant merchant=food.getMerchant();
			    List<Food> foods1=merchantService.findFoodsByMerchant(merchant);
			    merchant.setFoods(foods1);
			    mav.addObject("merchant",merchant);
			    mav.setViewName("/shop.jsp");
			    break;
			case "3":

			    List<Food> foods2=foodService.getFoodList();
			    mav.addObject("foods",foods2);
			    
			    List<Merchant> merchants1=merchantService.getMerchantLists();
			    mav.addObject("merchants",merchants1);
			    mav.setViewName("/foods.jsp");
			    break;
			    
			case "4":
			    List<Food> foods3=foodService.getFoodList();
			    mav.addObject("foods",foods3);
			    
			    List<Merchant> merchants2=merchantService.getMerchantLists();
			    mav.addObject("merchants",merchants2);
			    mav.setViewName("/merchants.jsp");
			    break;
			default:
			    break;
		    }
		    System.out.println("��ӳɹ�");
		    return mav;
		}
            }
	}
    }
    
    //ɾ���ͳ��ڵĲ�Ʒ
    @RequestMapping("/deleteCartFoodById")
    @ResponseBody
    public ModelAndView deleteCartFoodById(String cartFoodId,String flag, HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	CartFood cartFood=cartService.findCartFoodById(cartFoodId);
	cartService.deleteCartFoodById(cartFoodId);
	
	Cart cart=cartFood.getCart();
	cart.setTotalNum(cart.getTotalNum()-cartFood.getFoodNum());
	cart.setTotalPrice(cart.getTotalPrice()-cartFood.getPrice());
	List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	cart.setCartFoods(cartFoods);
	cartService.updateCart(cart);
	mav.addObject("cart",cart);
	mav.addObject("msg", "ɾ���ɹ�");
	switch (flag) {
	case "1":
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("merchantss",merchants);
	    mav.addObject("foods",foods);
		
	  //��ȡ�����û�����
	    List<User> users=userService.getUserList();
	    List<MyFoods> myFoods=myfoodsService.getMyFoodsList(user);
	    user.setMyFoodList(myFoods);
	    List<Favorite> fs=userService.findAllFavorites(user);
	    user.setFavorites(fs);
	    List<Food> RecommendFoodsByBuy=new ArrayList<>();
	    List<Food> RecommendFoodsByFavorite=new ArrayList<>();
	    System.out.println("RecommendFoodsByBuy:"+RecommendFoodsByBuy.size());
	    System.out.println("RecommendFoodsByFavorite:"+RecommendFoodsByFavorite.size());
	    if(user.getFavorites().size()>0) {
		//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
		List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user);
		//��ȡ�Ƽ��û�����
		List<User> uList2=RecommendUserCf.getUserList(s2);
		//��ȡ�Ƽ���Ʒ����
		RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user);
	    }
	    if (user.getMyFoodList().size()>0) {
		//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
		List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user);
		//��ȡ�Ƽ��û�����
		List<User> uList1=RecommendUserCf.getUserList(s1);
		//��ȡ�Ƽ���Ʒ����
		RecommendFoodsByBuy=RecommendUserCf.getFoodsListByBuy(uList1, user);
	    }
	    int j=0;
	    if (RecommendFoodsByBuy.size()<4) {
		j=RecommendFoodsByBuy.size();
		for (Food f : foods) {
		    RecommendFoodsByBuy.add(f);
		    j++;
		    if (j>=4) {
			break;
		    }
		}
	    }
	    if (RecommendFoodsByFavorite.size()<4) {
		int k=RecommendFoodsByFavorite.size();
		for (Food f : foods) {
		    RecommendFoodsByFavorite.add(foods.get(j++));
		    k++;
		    if (k>4) {
			break;
		    }
		}
	    }
	    System.out.println("���ݹ�����ʷ�Ƽ��б�");
	    for (Food f : RecommendFoodsByBuy) {
		System.out.println("f:"+f.getFoodName());
	    }
	    System.out.println("�����ղ��Ƽ��б�");
	    for (Food f : RecommendFoodsByFavorite) {
		System.out.println("f:"+f.getFoodName());
	    }
	    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoodsΪfood�Ƽ�����
	    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoodsΪfood�Ƽ�����
	    mav.setViewName("/index.jsp");
	    break;
	case "2":
	    Food food=foodService.findFoodById(cartFood.getFood().getId());
	    Merchant merchant=food.getMerchant();
	    List<Food> foods1=merchantService.findFoodsByMerchant(merchant);
	    merchant.setFoods(foods1);
	    mav.addObject("merchant",merchant);
	    mav.setViewName("/shop.jsp");
	    break;
	case "3":
	    List<UserAddress> uAddresses=userService.findAddressList(user);
	    mav.addObject("uAddresses",uAddresses);
	    mav.setViewName("/cart.jsp");
	    break;
	case "4":
	    List<Food> foods2=foodService.getFoodList();
	    mav.addObject("foods",foods2);
	    
	    List<Merchant> merchants1=merchantService.getMerchantLists();
	    mav.addObject("merchants",merchants1);
	    mav.setViewName("/foods.jsp");
	    break;
	case "5":
	    List<Food> foods3=foodService.getFoodList();
	    mav.addObject("foods",foods3);
	    
	    List<Merchant> merchants2=merchantService.getMerchantLists();
	    mav.addObject("merchants",merchants2);
	    mav.setViewName("/merchants.jsp");
	    break;
	default:
	    break;
	}
	
	return mav;
	
    }
    //�򿪹��ﳵ
    @RequestMapping("/openCart")
    @ResponseBody
    public ModelAndView MyCart(HttpServletRequest request) {
	
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	if (cartFoods.size()>0) {
	    cart.setCartFoods(cartFoods);
	   
	} else {
	    cart.setCartFoods(null);
	    
	}
	mav.addObject("cart",cart);
	
	List<UserAddress> uAddresses=userService.findAddressList(user);
	mav.addObject("uAddresses",uAddresses);
	mav.setViewName("/cart.jsp");
	return mav;
	
    }
    //�ص���ҳ
    @RequestMapping("/backToIndex")
    public ModelAndView backtoindex(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	
	if (cartFoods.size()>0) {
	    cart.setCartFoods(cartFoods);
	   
	} else {
	    cart.setCartFoods(null);

	}
	
	 mav.addObject("cart",cart);
	
	//��ȡ��ҳ�������̼Һ�������Ʒ
    	List<Merchant> merchants=merchantService.getMerchantLists();
    	List<Food> foods=foodService.getFoodList();
    	mav.addObject("merchantss",merchants);
    	mav.addObject("foods",foods);
	
	mav.setViewName("/index.jsp");
	
	//��ȡ�����û�����
	    List<User> users=userService.getUserList();
	    List<MyFoods> myFoods=myfoodsService.getMyFoodsList(user);
	    user.setMyFoodList(myFoods);
	    List<Favorite> fs=userService.findAllFavorites(user);
	    user.setFavorites(fs);
	    List<Food> RecommendFoodsByBuy=new ArrayList<>();
	    List<Food> RecommendFoodsByFavorite=new ArrayList<>();
	    System.out.println("RecommendFoodsByBuy:"+RecommendFoodsByBuy.size());
	    System.out.println("RecommendFoodsByFavorite:"+RecommendFoodsByFavorite.size());
	    if(user.getFavorites().size()>0) {
		//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
		List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user);
		//��ȡ�Ƽ��û�����
		List<User> uList2=RecommendUserCf.getUserList(s2);
		//��ȡ�Ƽ���Ʒ����
		RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user);
	    }
	    if (user.getMyFoodList().size()>0) {
		//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
		List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user);
		//��ȡ�Ƽ��û�����
		List<User> uList1=RecommendUserCf.getUserList(s1);
		//��ȡ�Ƽ���Ʒ����
		RecommendFoodsByBuy=RecommendUserCf.getFoodsListByBuy(uList1, user);
	    }
	    int j=0;
	    if (RecommendFoodsByBuy.size()<4) {
		j=RecommendFoodsByBuy.size();
		for (Food f : foods) {
		    RecommendFoodsByBuy.add(f);
		    j++;
		    if (j>=4) {
			break;
		    }
		}
	    }
	    if (RecommendFoodsByFavorite.size()<4) {
		int k=RecommendFoodsByFavorite.size();
		for (Food f : foods) {
		    RecommendFoodsByFavorite.add(foods.get(j++));
		    k++;
		    if (k>4) {
			break;
		    }
		}
	    }
	    System.out.println("���ݹ�����ʷ�Ƽ��б�");
	    for (Food f : RecommendFoodsByBuy) {
		System.out.println("f:"+f.getFoodName());
	    }
	    System.out.println("�����ղ��Ƽ��б�");
	    for (Food f : RecommendFoodsByFavorite) {
		System.out.println("f:"+f.getFoodName());
	    }
	    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoodsΪfood�Ƽ�����
	    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoodsΪfood�Ƽ�����
	
	return mav;
	
    }
    //�����ﳵ��Ʒ���仯
    @RequestMapping("/updateCartFoodNum")
    public void updateCartFoodNum(String foodId,String num,HttpServletRequest request) {
	int num1=Integer.parseInt(num);
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	CartFood cf=cartService.findCartFoodById(foodId);
	System.out.println(cf.getId());
	List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	for (CartFood cartFood : cartFoods) {
	    System.out.println(cartFood.getId());
	    if (cartFood.getId().equals(cf.getId())) {
		System.out.println("jll");
		int i=cartFood.getFoodNum();
		double p=cartFood.getPrice();
		Food food=cartFood.getFood();
		cartFood.setFoodNum(num1);
		cartFood.setPrice(num1*food.getPrice());
		cartFood.setFood(food);
		cartFood.setCart(cart);
		cartService.updateCartFood(cartFood);
		
		cart.setTotalNum(cart.getTotalNum()+num1-i);
		cart.setTotalPrice(cart.getTotalPrice()-p+num1*food.getPrice());
		cartService.updateCart(cart);
		System.out.println("ok");
		break;
	    }
	}
	
    }
    
    //���̼�ҳ��
    @RequestMapping("/openMerchant")
    @ResponseBody
    public ModelAndView openMerchant(String merchantId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	
	User user=(User)request.getSession().getAttribute("u");
	
	if (user==null) {
	    mav.addObject("msg","���ȵ�¼");
	    mav.setViewName("/login.jsp");
	} else {
	    Cart cart=cartService.findCartById(user.getCart().getId());
	    List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	    if (cartFoods.size()>0) {
		cart.setCartFoods(cartFoods);   
	    } else {
		cart.setCartFoods(null);
	    }
	    Merchant merchant=merchantService.findMerchantById(merchantId);
	    List<Food> foods=merchantService.findFoodsByMerchant(merchant);
	    merchant.setFoods(foods);
	    
	    mav.addObject("cart",cart);
	    mav.addObject("merchant",merchant);
	    mav.setViewName("/shop.jsp");
	}
	return mav;
	
    }
    //������в�Ʒ����������
    @RequestMapping("/allFoods")
    @ResponseBody
    public ModelAndView allFoods(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	if (user==null) {
	    mav.addObject("msg","���ȵ�¼");
	    mav.setViewName("/login.jsp");
	} else {
	    Cart cart=cartService.findCartById(user.getCart().getId());
	    List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	    if (cartFoods.size()>0) {
		cart.setCartFoods(cartFoods);   
	    } else {
		cart.setCartFoods(null);
	    }
	    mav.addObject("cart",cart);
	    
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("foods",foods);
	    
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    mav.addObject("merchants",merchants);
	    mav.setViewName("/foods.jsp");
	}
	return mav;
	
    }
    //����̼��б�
    @RequestMapping("/allMerchants")
    @ResponseBody
    public ModelAndView allMerchants(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	if (user==null) {
	    mav.addObject("msg","���ȵ�¼");
	    mav.setViewName("/login.jsp");
	} else {
	    Cart cart=cartService.findCartById(user.getCart().getId());
	    List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	    if (cartFoods.size()>0) {
		cart.setCartFoods(cartFoods);   
	    } else {
		cart.setCartFoods(null);
	    }
	    mav.addObject("cart",cart);
	    
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("foods",foods);
	    
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    mav.addObject("merchants",merchants);
	    mav.setViewName("/merchants.jsp");
	}
	return mav;
    }
    //������в�Ʒ���۸�����
    @RequestMapping("/findFoodsByPrice")
    @ResponseBody
    public ModelAndView findFoodsByPrice(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	if (user==null) {
	    mav.addObject("msg","���ȵ�¼");
	    mav.setViewName("/login.jsp");
	} else {
	    Cart cart=cartService.findCartById(user.getCart().getId());
	    List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	    if (cartFoods.size()>0) {
		cart.setCartFoods(cartFoods);   
	    } else {
		cart.setCartFoods(null);
	    }
	    mav.addObject("cart",cart);
	    
	    List<Food> foods=foodService.getFoodListByPrice();
	    mav.addObject("foods",foods);
	    
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    mav.addObject("merchants",merchants);
	    mav.setViewName("/foods.jsp");
	}
	return mav;
	
    }
    
    //����µ�ַ
    @RequestMapping("/addUserAddress")
    @ResponseBody
    public ModelAndView addUserAddress(String frontAddress ,String detailAddress,String userName,String userPhone,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	UserAddress userAddress=new UserAddress();
	userAddress.setFrontAddress(frontAddress);
	userAddress.setDetailAddress(detailAddress);
	userAddress.setUserName(userName);
	userAddress.setUserPhone(userPhone);
	System.out.println(frontAddress);
	System.out.println(detailAddress);
	System.out.println(userName);
	System.out.println(userPhone);
	try {
	    userService.addUserAddress(userAddress,user);
	    mav.addObject("msg","��ӳɹ���");
	    mav.setViewName("/users/openCart");
	    return mav;
	} catch (Exception e) {
	    // TODO: handle exception
	    mav.addObject("msg","ϵͳ�쳣");
	    mav.setViewName("/users/openCart");
	    return mav;
	}
	
    } 
    
    //��������������֧��
    @RequestMapping("/onlinePlay")
    @ResponseBody
    public ModelAndView onlinePlay(String addressId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	UserAddress uAddress=userService.findAddressById(addressId);
	Cart cart=cartService.findCartById(user.getCart().getId());
	List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	if (cartFoods.size()>0) {
	    cart.setCartFoods(cartFoods);   
	} else {
	    cart.setCartFoods(null);
	}
	Intent intent =intentService.createIntent(cart,cartFoods,uAddress,user);
	
	//��չ��ﳵ
	Cart cart1=cartService.clearCart(cart);
	mav.addObject("cart",cart1);
	mav.addObject("intent",intent);
	
	mav.setViewName("/onlineplay.jsp");
	return mav;
	
    }
    //���֧�������붩������
    @RequestMapping("/successPlay")
    @ResponseBody
    public ModelAndView successPlay(String intentId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	
	Intent intent = intentService.findIntentById(intentId);
	if (intent!=null) {
	    if(intent.getOrderStatus().equals("δ����")||intent.getOrderStatus()=="δ����") {
		intent.setOrderStatus("���ջ�");
		
	    }
	    intent=intentService.updateIntent(intent);
	    List<Intent_foods> intent_foodss=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(intent_foodss);
	    mav.addObject("intent",intent);
	    mav.addObject("msg","֧�����");
	} else {
	    mav.addObject("intent",null);
	}
	
	mav.addObject("cart",cart);
	
	mav.setViewName("/intent.jsp");
	return mav;
	
    }
    
    //ȡ��֧��
    @RequestMapping("/cancelPlay")
    @ResponseBody
    public ModelAndView cancelPlay(String intentId,String flag,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	System.out.println(flag);
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	
	if (cartFoods.size()>0) {
	    cart.setCartFoods(cartFoods);
	   
	} else {
	    cart.setCartFoods(null);

	}
	mav.addObject("cart",cart);
	Intent intent = intentService.findIntentById(intentId);
	if (intent!=null) {
	    List<Intent_foods> intent_foodss=intentService.findIntentFoodsListByIntent(intent);
	    for (Intent_foods i : intent_foodss) {
		intentService.deleteIntentFoods(i);
	    }
    	    intentService.deleteIntent(intent);
    	    mav.addObject("msg","ȡ���ɹ�");
    	    
	}else {
	    mav.addObject("msg","����������");
    	   
	}
	
	switch (flag) {
	case "1":
	  //��ȡ��ҳ�������̼Һ�������Ʒ
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("merchantss",merchants);
	    mav.addObject("foods",foods);
	    //��ȡ�����û�����
	    List<User> users=userService.getUserList();
	    List<MyFoods> myFoods=myfoodsService.getMyFoodsList(user);
	    user.setMyFoodList(myFoods);
	    List<Favorite> fs=userService.findAllFavorites(user);
	    user.setFavorites(fs);
	    List<Food> RecommendFoodsByBuy=new ArrayList<>();
	    List<Food> RecommendFoodsByFavorite=new ArrayList<>();
	    System.out.println("RecommendFoodsByBuy:"+RecommendFoodsByBuy.size());
	    System.out.println("RecommendFoodsByFavorite:"+RecommendFoodsByFavorite.size());
	    if(user.getFavorites().size()>0) {
		//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
		List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user);
		//��ȡ�Ƽ��û�����
		List<User> uList2=RecommendUserCf.getUserList(s2);
		//��ȡ�Ƽ���Ʒ����
		RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user);
	    }
	    if (user.getMyFoodList().size()>0) {
		//��ȡ���ƶȾ����Ӧ���û�������ֵ��ļ���
		List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user);
		//��ȡ�Ƽ��û�����
		List<User> uList1=RecommendUserCf.getUserList(s1);
		//��ȡ�Ƽ���Ʒ����
		RecommendFoodsByBuy=RecommendUserCf.getFoodsListByBuy(uList1, user);
	    }
	    int j=0;
	    if (RecommendFoodsByBuy.size()<4) {
		j=RecommendFoodsByBuy.size();
		for (Food f : foods) {
		    RecommendFoodsByBuy.add(f);
		    j++;
		    if (j>=4) {
			break;
		    }
		}
	    }
	    if (RecommendFoodsByFavorite.size()<4) {
		int k=RecommendFoodsByFavorite.size();
		for (Food f : foods) {
		    RecommendFoodsByFavorite.add(foods.get(j++));
		    k++;
		    if (k>4) {
			break;
		    }
		}
	    }
	    System.out.println("���ݹ�����ʷ�Ƽ��б�");
	    for (Food f : RecommendFoodsByBuy) {
		System.out.println("f:"+f.getFoodName());
	    }
	    System.out.println("�����ղ��Ƽ��б�");
	    for (Food f : RecommendFoodsByFavorite) {
		System.out.println("f:"+f.getFoodName());
	    }
	    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoodsΪfood�Ƽ�����
	    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoodsΪfood�Ƽ�����
	    mav.setViewName("/index.jsp");
	    break;
	case "2":
	    List<Intent> iList1=intentService.findIntent1ByUser(user);
		List<Intent> iList2=intentService.findIntent2ByUser(user);
		List<Intent> iList=intentService.findIntentByUser(user);
		for (Intent i : iList1) {
		    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
		    i.setIntent_foodss(iFoods);
		}
		
		for (Intent i : iList2) {
		    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
		    i.setIntent_foodss(iFoods);
		}
		for (Intent i : iList) {
		    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
		    i.setIntent_foodss(iFoods);
		}
		mav.addObject("iList1",iList1);
		mav.addObject("iList2",iList2);
		mav.addObject("iList",iList);
		mav.setViewName("/userIntent.jsp");
	    break;
	  default:break;
	}
	return mav;
	
    }
    
    //ȷ���ջ�
    @RequestMapping("/confirmIntent")
    @ResponseBody
    public ModelAndView confirmIntent(String intentId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	Intent intent = intentService.findIntentById(intentId);
	intent.setOrderStatus("���ջ�");
	intent.setFinishTime(new Date());
	intent=intentService.updateIntent(intent);
	List<Intent_foods> intent_foodss=intentService.findIntentFoodsListByIntent(intent);
	
	List<Food> foods=FoodUtil.getFoodsFormIntentFoods(intent_foodss);
	myfoodsService.addFoodToMyFoodsList(user,foods);
	
	intent.setIntent_foodss(intent_foodss);
	
	mav.addObject("msg","���ջ����뷵�ؼ�������");
	mav.addObject("cart",cart);
	mav.addObject("intent",intent);
	mav.setViewName("/intent.jsp");
	return mav;
	
    }
    
    @RequestMapping("/favorite")
    @ResponseBody
    public Message favorite(String foodId,HttpServletRequest request) {
	Message msg=new Message();
	User user=(User)request.getSession().getAttribute("u");
	Food food=foodService.findFoodById(foodId);
	if (food==null) {
	    msg.setMsg("����Ʒ������");
	} else {
	    List<Favorite> favorites=userService.findAllFavorites(user);
	    boolean b=true;
	    for (Favorite favorite : favorites) {
		if (favorite.getFood().getId().equals(food.getId())) {
		    b=false;
		}
	    }
	    if (b) {
		Favorite favorite = new Favorite();
		favorite.setFood(food);
		favorite.setUser(user);
		favorite.setTime(new Date());
		userService.createFavorite(favorite);
		
		msg.setMsg("�ղسɹ�");
	    }else {
		 msg.setMsg("���Ѿ��ղع�����Ʒ��");
	    }
	    
	}
	return msg;
	
    }
    @RequestMapping("/cancalFavorite")
    @ResponseBody
    public Message cancalFavorite(String foodId,HttpServletRequest request) {
	Message msg=new Message();
	Food food=foodService.findFoodById(foodId);
	if (food!=null) {
	    Favorite favorite=userService.findFavoriteByFood(food);
	    if (favorite!=null) {
		userService.deleteFavorite(favorite);
		msg.setMsg("ȡ���ղسɹ�");
	    } else {
		msg.setMsg("ȡ���ղ�ʧ�ܣ��޷��ҵ��ü�¼��");
	    }
	} else {
	    msg.setMsg("ȡ���ղ�ʧ�ܣ��޷��ҵ��ü�¼��");
	}
	
	return msg;
	
    }
    
    
    @RequestMapping("/openFavorite")
    @ResponseBody
    public ModelAndView openFavorite(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	if (cartFoods.size()>0) {
	    cart.setCartFoods(cartFoods);   
	} else {
	    cart.setCartFoods(null);
	}
	
	List<Favorite> fList=userService.findAllFavorites(user);
	mav.addObject("cart",cart);
	mav.addObject("fList",fList);
	mav.setViewName("/userFavorites.jsp");
	
	return mav;
	
    }
    //�����������
    @RequestMapping("/openCenter")
    @ResponseBody
    public ModelAndView openCenter(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	
	Cart cart=cartService.findCartById(user.getCart().getId());
	
	List<Intent> iList1=intentService.findIntent1ByUser(user);//δ�����
	List<Intent> iList2=intentService.findIntent2ByUser(user);//���ջ�����
	
	for (Intent intent : iList1) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(iFoods);
	}
	
	for (Intent intent : iList2) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(iFoods);
	}
	
	
	
	mav.addObject("cart",cart);
	mav.addObject("iList1",iList1);
	mav.addObject("iList2",iList2);
	mav.setViewName("/userCenter.jsp");
	
	return mav;
	
    }
    //�鿴����
    @RequestMapping("/myIntent")
    @ResponseBody
    public ModelAndView myIntent(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	
	Cart cart=cartService.findCartById(user.getCart().getId());
	
	mav.addObject("cart",cart);
	
	List<Intent> iList1=intentService.findIntent1ByUser(user);
	List<Intent> iList2=intentService.findIntent2ByUser(user);
	List<Intent> iList=intentService.findIntentByUser(user);
	for (Intent intent : iList1) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(iFoods);
	}
	
	for (Intent intent : iList2) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(iFoods);
	}
	for (Intent intent : iList) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(iFoods);
	}
	mav.addObject("iList1",iList1);
	mav.addObject("iList2",iList2);
	mav.addObject("iList",iList);
	mav.setViewName("/userIntent.jsp");
	return mav;
	
    }
    
    //�鿴�ҵĵ�ַ
    @RequestMapping("/myAddress")
    @ResponseBody
    public ModelAndView myAddress(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	
	Cart cart=cartService.findCartById(user.getCart().getId());
	
	mav.addObject("cart",cart);
	
	List<UserAddress> userAddresses=userService.findAddressList(user);
	
	mav.addObject("userAddresses",userAddresses);
	mav.setViewName("/userAddress.jsp");
	return mav;
	
    }
    //��ӵ�ַ
    @RequestMapping("/addAddress")
    @ResponseBody
    public Message addAddress(String frontAddress,String detailAddress,String userName,String userPhone,HttpServletRequest request) {
	Message msg=new Message();
	User user=(User)request.getSession().getAttribute("u");
	UserAddress address=new UserAddress();
	address.setFrontAddress(frontAddress);
	address.setDetailAddress(detailAddress);
	address.setUserName(userName);
	address.setUserPhone(userPhone);
	
	try {
	    userService.addUserAddress(address, user);
	    msg.setMsg("��ӵ�ַ�ɹ�");
	    return msg;
	} catch (Exception e) {
	    // TODO: handle exception
	    msg.setMsg("ϵͳ�쳣");
	    return msg;
	}
	
    }
    //�޸ĵ�ַ
    @RequestMapping("/updateAddress")
    @ResponseBody
    public Message updateAddress(String id,String frontAddress,String detailAddress,String userName,String userPhone,HttpServletRequest request) {
	Message msg=new Message();
	UserAddress address=userService.findAddressById(id);
	address.setFrontAddress(frontAddress);
	address.setDetailAddress(detailAddress);
	address.setUserName(userName);
	address.setUserPhone(userPhone);
	try {
	    userService.updateAddress(address);
	    msg.setMsg("�޸ĳɹ�");
	    return msg;
	} catch (Exception e) {
	    msg.setMsg("ϵͳ�쳣");
	    return msg;
	}
	
    }
    //ɾ����ַ
    @RequestMapping("/deleteAddress")
    @ResponseBody
    public Message daleteAddress(String id,HttpServletRequest request) {
	Message msg=new Message();
	
	UserAddress address=userService.findAddressById(id);
	List<Intent> intents=intentService.findIntentByAddress(address);
	if (intents.size()>0) {
	    for (Intent intent : intents) {
		intent.setUserAddress(null);
		intentService.updateIntent(intent);
	    }
	}
	
	try {
	    userService.deleteAddress(address);
	    msg.setMsg("ɾ���ɹ�");
	    return msg;
	} catch (Exception e) {
	    // TODO: handle exception
	    msg.setMsg("ϵͳ�쳣");
	    return msg;
	}
	
    }
    
    //��������
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Message updatePassword(String pw1,String pw2,HttpServletRequest request) {
	Message msg=new Message();
	User user=(User)request.getSession().getAttribute("u");
	User u=userService.findUserById(user.getId());
	System.out.println(u.getId());
	if (pw1.equals(u.getUserPassword())) {
	    u.setUserPassword(pw2);
	    userService.updateUser(u);
	    msg.setMsg("�޸ĳɹ��������µ�¼��");
	    return msg;
	} else {
	    msg.setMsg("�������");
	    return msg;
	}
	
	
    }
    
    //ǰ������
    @RequestMapping("/goToPlay")
    @ResponseBody
    public ModelAndView goToPlay(String intentId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	mav.addObject("cart",cart);
	Intent intent=intentService.findIntentById(intentId);
	mav.addObject("intent",intent);
	mav.setViewName("/onlineplay.jsp");
	return mav;
	
    }
    //ɾ������
    @RequestMapping("/deleteIntent")
    @ResponseBody
    public ModelAndView deleteIntent(String intentId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	mav.addObject("cart",cart);
	Intent intent=intentService.findIntentById(intentId);
	if (intent==null) {
	    mav.addObject("msg","����������");
	}else {
	    List<Intent_foods> intent_foodss=intentService.findIntentFoodsListByIntent(intent);
	    for (Intent_foods i : intent_foodss) {
		intentService.deleteIntentFoods(i);
	    }
	    intentService.deleteIntent(intent);
	    mav.addObject("msg","ɾ�������ɹ�");
	}
	List<Intent> iList1=intentService.findIntent1ByUser(user);
	List<Intent> iList2=intentService.findIntent2ByUser(user);
	List<Intent> iList=intentService.findIntentByUser(user);
	for (Intent i : iList1) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
	    i.setIntent_foodss(iFoods);
	}
	
	for (Intent i : iList2) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
	    i.setIntent_foodss(iFoods);
	}
	for (Intent i : iList) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
	    i.setIntent_foodss(iFoods);
	}
	mav.addObject("iList1",iList1);
	mav.addObject("iList2",iList2);
	mav.addObject("iList",iList);
	mav.setViewName("/userIntent.jsp");
	return mav;
	
    }
    
    //�鿴����
    @RequestMapping("/viewIntent")
    @ResponseBody
    public ModelAndView viewIntent(String intentId,HttpServletRequest  request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	mav.addObject("cart",cart);
	
	Intent intent=intentService.findIntentById(intentId);
	if (intent==null) {
	    mav.addObject("msg", "����������");
	    List<Intent> iList1=intentService.findIntent1ByUser(user);
	    List<Intent> iList2=intentService.findIntent2ByUser(user);
	    List<Intent> iList=intentService.findIntentByUser(user);
	    for (Intent i : iList1) {
		List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
		i.setIntent_foodss(iFoods);
	    }
		
	    for (Intent i : iList2) {
		List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
		i.setIntent_foodss(iFoods);
	    }
	    for (Intent i : iList) {
		List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(i);
		i.setIntent_foodss(iFoods);
	    }
	    mav.addObject("iList1",iList1);
	    mav.addObject("iList2",iList2);
	    mav.addObject("iList",iList);
	    
	    mav.setViewName("/userIntent.jsp");
	}else {
	    List<Intent_foods> intent_foodss=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(intent_foodss);
	    mav.addObject("intent",intent);
	    mav.setViewName("/userIntentView.jsp");
	}
	return mav;
	
    }
    
  //�����ֻ���
    @RequestMapping("/changePhone")
    @ResponseBody
    public Message changePhone(String phone,String code,HttpServletRequest request) {
	Message msg=new Message();
	System.out.println(phone);
	System.out.println(code);
	User user=(User)request.getSession().getAttribute("u");
	User u=userService.findByPhone(phone);
	if (u!=null) {
	    msg.setMsg("�ֻ������Ѵ��ڻ�ú���Ϊ���˺ŵ�¼�ֻ�����");
	    return msg;
	}else {
	    JSONObject json = (JSONObject)request.getSession().getAttribute("verifyCode");
	    if(!json.getString("verifyCode").equals(code)){
		
		msg.setMsg("��֤�����");
		return msg;
	    }else if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 2){
		msg.setMsg("��֤����ڣ������·��ͣ�");
		return msg ;
	    }else {
		System.out.println("�����ֻ�����ɹ�");
		User user2=userService.findUserById(user.getId());
		user2.setUserPhone(phone);
		userService.updateUser(user2);
		msg.setMsg("�����ֻ�����ɹ�");
		request.getSession().setAttribute("u", user2);
		return msg ;
	    }
	}
	
    }
    //�޸���Ϣ
    @RequestMapping("/updateInfor")
    @ResponseBody
    public ModelAndView updateInfor(MultipartFile filedata,String userName,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	mav.addObject("cart",cart);
	try {
	    userService.updateInfor(filedata,userName,request);
	    mav.addObject("msg", "�޸ĳɹ�");
	    User u=userService.findUserById(user.getId());
	    request.getSession().setAttribute("u", u);
	    mav.setViewName("/userUpdate.jsp");
	    return mav;
	} catch (Exception e) {
	    // TODO: handle exception
	    mav.addObject("msg", "�޸�ʧ��");
	    mav.setViewName("/userUpdate.jsp");
	    return mav;
	}
	
	
    }
    @RequestMapping("/serachFood")
    @ResponseBody
    public ModelAndView serachFood(String keyword,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	
	Cart cart=cartService.findCartById(user.getCart().getId());
	List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	if (cartFoods.size()>0) {
	    cart.setCartFoods(cartFoods);   
	} else {
	    cart.setCartFoods(null);
	}
	mav.addObject("cart",cart);
	
	List<Food> foods=foodService.serachFood(keyword);
	mav.addObject("foods",foods);
	    
	List<Merchant> merchants=merchantService.getMerchantLists();
	mav.addObject("merchants",merchants);
	mav.setViewName("/foods.jsp");
	return mav;
	
    }
    @RequestMapping("/serachMerchant")
    @ResponseBody
    public ModelAndView serachMerchant(String keyword,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	if (user==null) {
	    mav.addObject("msg","���ȵ�¼");
	    mav.setViewName("/login.jsp");
	} else {
	    Cart cart=cartService.findCartById(user.getCart().getId());
	    List<CartFood> cartFoods=cartService.findCartFoodListByCartId(cart.getId());
	    if (cartFoods.size()>0) {
		cart.setCartFoods(cartFoods);   
	    } else {
		cart.setCartFoods(null);
	    }
	    mav.addObject("cart",cart);
	    
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("foods",foods);
	    
	    List<Merchant> merchants=merchantService.serachMerchant(keyword);
	    mav.addObject("merchants",merchants);
	    mav.setViewName("/merchants.jsp");
	}
	return mav;
	
    }
}
