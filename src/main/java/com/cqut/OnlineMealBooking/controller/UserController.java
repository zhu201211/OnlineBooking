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
    
    // 声明service的对象
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
	System.out.println("用户登录："+userPhone+"+"+userPassword);
	ModelAndView mav = new ModelAndView();
	Message msg=new Message();
	User user1=userService.loginByPhone(userPhone,userPassword);
	if (user1==null||user1.getUserName().equals("admin")) {
	    System.out.println("登录失败");
	    msg.setMsg("用户名或者密码错误");
	    mav.addObject("msg", msg);
	    mav.setViewName("/login.jsp");
	    return mav; 
	    
	} else {
	    
	    System.out.println("登录成功");
	   
	    //获取首页的人呢商家和热销商品
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("merchantss",merchants);
	    mav.addObject("foods",foods);
	    //初始化餐车
	    Cart cart=user1.getCart();
	    cart.setTotalNum(0);
	    cart.setTotalPrice(0);
	    cart.setCartFoods(null);
	    cartService.updateCart(cart);
	    cartService.deleteCartFoodByCart(cart);
	    mav.addObject("cart",cart);
//	    request.getSession().setAttribute("cart", cart);
	    
	   
	    mav.addObject("msg", "登录成功");
	    request.getSession().setAttribute("u", user1);//sesiion保存用户
	    mav.setViewName("/index.jsp");
	    
	    //获取所有用户集合
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
		//获取相似度矩阵对应的用户和相似值类的集合
		List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user1);
		//获取推荐用户集合
		List<User> uList2=RecommendUserCf.getUserList(s2);
		//获取推荐菜品集合
		RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user1);
	    }
	    if (user1.getMyFoodList().size()>0) {
		//获取相似度矩阵对应的用户和相似值类的集合
		List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user1);
		//获取推荐用户集合
		List<User> uList1=RecommendUserCf.getUserList(s1);
		//获取推荐菜品集合
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
	    System.out.println("根据购买历史推荐列表：");
	    for (Food food : RecommendFoodsByBuy) {
		System.out.println("f:"+food.getFoodName());
	    }
	    System.out.println("根据收藏推荐列表：");
	    for (Food food : RecommendFoodsByFavorite) {
		System.out.println("f:"+food.getFoodName());
	    }
	    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoods为food推荐集合
	    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoods为food推荐集合
	    return mav;
	}
    }
    
    //登录发送验证码
    @RequestMapping("/sendCode")
    @ResponseBody
    public Message sendCode(String userPhone,HttpServletRequest request) {
	System.out.println(userPhone);
	User u=userService.findByPhone(userPhone);
	Message msg = new Message();
	JSONObject jsonObject=new JSONObject();
	if (u==null) {
	    msg.setMsg("用户不存在");
	    return msg;
	} else {
	    jsonObject=SendCode.getJson(userPhone);
	    if (jsonObject.getString("msg")==null) {
		// 将认证码存入SESSION
		System.out.println("信息验证码："+jsonObject.getString("verifyCode"));
		request.getSession().setAttribute("verifyCode", jsonObject);	
		msg.setMsg("发送短信成功！有效期为两分钟");
		return msg;
	    } else {
		System.out.println(jsonObject.getString("msg"));
		msg.setMsg(jsonObject.getString("msg"));
		return msg;
	    }
	}
    }
    
  //注册发送验证码2
    @RequestMapping("/sendCode2")
    @ResponseBody
    public Message sendCode2(String userPhone,HttpServletRequest request) {
	System.out.println(userPhone);
	User u=userService.findByPhone(userPhone);
	Message msg = new Message();
	JSONObject jsonObject=new JSONObject();
	if (u!=null) {
	    msg.setMsg("用户已存在！请进入登录界面直接登录");
	    return msg;
	} else {
	    jsonObject=SendCode.getJson(userPhone);//发送短信
	    if (jsonObject.getString("msg")==null) {
		// 将认证码存入SESSION
		System.out.println("信息验证码："+jsonObject.getString("verifyCode"));
		request.getSession().setAttribute("verifyCode", jsonObject);	
		msg.setMsg("发送短信成功！有效期为两分钟");
		return msg;
	    } else {
		System.out.println(jsonObject.getString("msg"));
		msg.setMsg(jsonObject.getString("msg"));
		return msg;
	    }
	}
	
    }
    
    //登录发送验证码
    @RequestMapping("/sendCode3")
    @ResponseBody
    public Message sendCode3(String userPhone,HttpServletRequest request) {
	System.out.println(userPhone);
	User u=userService.findByPhone(userPhone);
	Message msg = new Message();
	JSONObject jsonObject=new JSONObject();
	if (u!=null) {
	    msg.setMsg("手机号码已存在或该号码为本账号登录手机号码");
	    return msg;
	} else {
	    jsonObject=SendCode.getJson(userPhone);
	    if (jsonObject.getString("msg")==null) {
		// 将认证码存入SESSION
		System.out.println("信息验证码："+jsonObject.getString("verifyCode"));
		request.getSession().setAttribute("verifyCode", jsonObject);	
		msg.setMsg("发送短信成功！有效期为两分钟");
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
	    msg.setMsg("用户不存在");
	    mav.addObject("msg", msg);
	    mav.setViewName("/login0.jsp");
	    return mav;
	}else {
	    JSONObject json = (JSONObject)request.getSession().getAttribute("verifyCode");
	    if(!json.getString("verifyCode").equals(code)){
		
		msg.setMsg("验证码错误");
		mav.addObject("msg", msg);
		mav.setViewName("/login0.jsp");
		return mav;
	    }else if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 2){
		msg.setMsg("验证码过期，请重新发送！");
		mav.addObject("msg", msg);
		mav.setViewName("/login0.jsp");
		return mav ;
	    }else {
		System.out.println("登录成功");
		//获取首页的热门商家和热销商品
		List<Merchant> merchants=merchantService.getMerchantLists();
		List<Food> foods=foodService.getFoodList();
		mav.addObject("merchantss",merchants);
		mav.addObject("foods",foods);
		//初始化餐车
		Cart cart=u.getCart();
		cart.setTotalNum(0);
		cart.setTotalPrice(0);
		cart.setCartFoods(null);
		cartService.updateCart(cart);
		cartService.deleteCartFoodByCart(cart);
		mav.addObject("cart",cart);
		
		mav.addObject("msg", "登录成功");
		request.getSession().setAttribute("u", u);
		mav.setViewName("/index.jsp");
		
		 //获取所有用户集合
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
			//获取相似度矩阵对应的用户和相似值类的集合
			List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, u);
			//获取推荐用户集合
			List<User> uList2=RecommendUserCf.getUserList(s2);
			//获取推荐菜品集合
			RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, u);
		    }
		    if (u.getMyFoodList().size()>0) {
			//获取相似度矩阵对应的用户和相似值类的集合
			List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, u);
			//获取推荐用户集合
			List<User> uList1=RecommendUserCf.getUserList(s1);
			//获取推荐菜品集合
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
		    System.out.println("根据购买历史推荐列表：");
		    for (Food food : RecommendFoodsByBuy) {
			System.out.println("f:"+food.getFoodName());
		    }
		    System.out.println("根据收藏推荐列表：");
		    for (Food food : RecommendFoodsByFavorite) {
			System.out.println("f:"+food.getFoodName());
		    }
		    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoods为food推荐集合
		    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoods为food推荐集合
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
	    msg.setMsg("用户已存在");
	    return msg;
	}else {
	    JSONObject json = (JSONObject)request.getSession().getAttribute("verifyCode");
	    if(!json.getString("verifyCode").equals(code)){
		 msg.setMsg("验证码错误");
		 return msg;
		 
	    }else if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 2) {
		msg.setMsg("验证码过期，请重新发送！");
		return msg;
	    }else {
		User user =new User();
		user.setUserPhone(userPhone);
		user.setUserPassword(userPassword);
		//为用户分配餐车
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
		    msg.setMsg("系统异常");
		    return msg;
		}
	    }
	    
	}
	
    }
    //菜品加入至餐车
    @RequestMapping("/addFoodToCart")
    @ResponseBody
    public ModelAndView addFoodToCart(String foodId,String flag,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=new Message();	
	User u=(User) request.getSession().getAttribute("u");
	User user=userService.findByPhone(u.getUserPhone());
	if (user==null) {
	    msg.setMsg("请先登录");
	    mav.addObject("msg", msg);
	    
	    mav.setViewName("/login.jsp");
	    return mav;
	} else {
	    Food food=foodService.findFoodById(foodId);
            if (food==null) {
        	mav.addObject("msg", "该商品不存在或已下架，请重新选择！");
        	mav.setViewName("/index.jsp");
        	return mav;
            }else{
        	CartFood cartFood=cartService.addCartFood(user.getCart().getId(),food);
        	if (cartFood==null) {
        	    System.out.println("添加失败");
	    	    mav.addObject("msg", "添加失败");
	    	    mav.setViewName("/index.jsp");
	    	    return mav;
		} else {
		    System.out.println("添加成功");	
		    mav.addObject("msg", "添加成功");
		    List<CartFood> cartFoods=cartService.findCartFoodListByCartId(user.getCart().getId());
		    Cart cart=cartService.findCartById(user.getCart().getId());
		    cart.setCartFoods(cartFoods);
		    user.setCart(cart);
		    mav.addObject("cart",cart);
		    switch (flag) {
			case "1":
			    //获取首页的人呢商家和热销商品
			    List<Merchant> merchants=merchantService.getMerchantLists();
			    List<Food> foods=foodService.getFoodList();
			    mav.addObject("merchantss",merchants);
			    mav.addObject("foods",foods);
			    //获取所有用户集合
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
				//获取相似度矩阵对应的用户和相似值类的集合
				List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user);
				//获取推荐用户集合
				List<User> uList2=RecommendUserCf.getUserList(s2);
				//获取推荐菜品集合
				RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user);
			    }
			    if (user.getMyFoodList().size()>0) {
				//获取相似度矩阵对应的用户和相似值类的集合
				List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user);
				//获取推荐用户集合
				List<User> uList1=RecommendUserCf.getUserList(s1);
				//获取推荐菜品集合
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
			    System.out.println("根据购买历史推荐列表：");
			    for (Food f : RecommendFoodsByBuy) {
				System.out.println("f:"+f.getFoodName());
			    }
			    System.out.println("根据收藏推荐列表：");
			    for (Food f : RecommendFoodsByFavorite) {
				System.out.println("f:"+f.getFoodName());
			    }
			    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoods为food推荐集合
			    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoods为food推荐集合
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
		    System.out.println("添加成功");
		    return mav;
		}
            }
	}
    }
    
    //删除餐车内的菜品
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
	mav.addObject("msg", "删除成功");
	switch (flag) {
	case "1":
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("merchantss",merchants);
	    mav.addObject("foods",foods);
		
	  //获取所有用户集合
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
		//获取相似度矩阵对应的用户和相似值类的集合
		List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user);
		//获取推荐用户集合
		List<User> uList2=RecommendUserCf.getUserList(s2);
		//获取推荐菜品集合
		RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user);
	    }
	    if (user.getMyFoodList().size()>0) {
		//获取相似度矩阵对应的用户和相似值类的集合
		List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user);
		//获取推荐用户集合
		List<User> uList1=RecommendUserCf.getUserList(s1);
		//获取推荐菜品集合
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
	    System.out.println("根据购买历史推荐列表：");
	    for (Food f : RecommendFoodsByBuy) {
		System.out.println("f:"+f.getFoodName());
	    }
	    System.out.println("根据收藏推荐列表：");
	    for (Food f : RecommendFoodsByFavorite) {
		System.out.println("f:"+f.getFoodName());
	    }
	    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoods为food推荐集合
	    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoods为food推荐集合
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
    //打开购物车
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
    //回到首页
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
	
	//获取首页的人呢商家和热销商品
    	List<Merchant> merchants=merchantService.getMerchantLists();
    	List<Food> foods=foodService.getFoodList();
    	mav.addObject("merchantss",merchants);
    	mav.addObject("foods",foods);
	
	mav.setViewName("/index.jsp");
	
	//获取所有用户集合
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
		//获取相似度矩阵对应的用户和相似值类的集合
		List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user);
		//获取推荐用户集合
		List<User> uList2=RecommendUserCf.getUserList(s2);
		//获取推荐菜品集合
		RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user);
	    }
	    if (user.getMyFoodList().size()>0) {
		//获取相似度矩阵对应的用户和相似值类的集合
		List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user);
		//获取推荐用户集合
		List<User> uList1=RecommendUserCf.getUserList(s1);
		//获取推荐菜品集合
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
	    System.out.println("根据购买历史推荐列表：");
	    for (Food f : RecommendFoodsByBuy) {
		System.out.println("f:"+f.getFoodName());
	    }
	    System.out.println("根据收藏推荐列表：");
	    for (Food f : RecommendFoodsByFavorite) {
		System.out.println("f:"+f.getFoodName());
	    }
	    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoods为food推荐集合
	    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoods为food推荐集合
	
	return mav;
	
    }
    //处理购物车菜品数变化
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
    
    //打开商家页面
    @RequestMapping("/openMerchant")
    @ResponseBody
    public ModelAndView openMerchant(String merchantId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	
	User user=(User)request.getSession().getAttribute("u");
	
	if (user==null) {
	    mav.addObject("msg","请先登录");
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
    //浏览所有菜品按销量排序
    @RequestMapping("/allFoods")
    @ResponseBody
    public ModelAndView allFoods(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	if (user==null) {
	    mav.addObject("msg","请先登录");
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
    //浏览商家列表
    @RequestMapping("/allMerchants")
    @ResponseBody
    public ModelAndView allMerchants(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	if (user==null) {
	    mav.addObject("msg","请先登录");
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
    //浏览所有菜品按价格排序
    @RequestMapping("/findFoodsByPrice")
    @ResponseBody
    public ModelAndView findFoodsByPrice(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	if (user==null) {
	    mav.addObject("msg","请先登录");
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
    
    //添加新地址
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
	    mav.addObject("msg","添加成功！");
	    mav.setViewName("/users/openCart");
	    return mav;
	} catch (Exception e) {
	    // TODO: handle exception
	    mav.addObject("msg","系统异常");
	    mav.setViewName("/users/openCart");
	    return mav;
	}
	
    } 
    
    //创建订单，在线支付
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
	
	//清空购物车
	Cart cart1=cartService.clearCart(cart);
	mav.addObject("cart",cart1);
	mav.addObject("intent",intent);
	
	mav.setViewName("/onlineplay.jsp");
	return mav;
	
    }
    //完成支付，进入订单详情
    @RequestMapping("/successPlay")
    @ResponseBody
    public ModelAndView successPlay(String intentId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	
	Intent intent = intentService.findIntentById(intentId);
	if (intent!=null) {
	    if(intent.getOrderStatus().equals("未付款")||intent.getOrderStatus()=="未付款") {
		intent.setOrderStatus("待收货");
		
	    }
	    intent=intentService.updateIntent(intent);
	    List<Intent_foods> intent_foodss=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(intent_foodss);
	    mav.addObject("intent",intent);
	    mav.addObject("msg","支付完成");
	} else {
	    mav.addObject("intent",null);
	}
	
	mav.addObject("cart",cart);
	
	mav.setViewName("/intent.jsp");
	return mav;
	
    }
    
    //取消支付
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
    	    mav.addObject("msg","取消成功");
    	    
	}else {
	    mav.addObject("msg","订单不存在");
    	   
	}
	
	switch (flag) {
	case "1":
	  //获取首页的人呢商家和热销商品
	    List<Merchant> merchants=merchantService.getMerchantLists();
	    List<Food> foods=foodService.getFoodList();
	    mav.addObject("merchantss",merchants);
	    mav.addObject("foods",foods);
	    //获取所有用户集合
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
		//获取相似度矩阵对应的用户和相似值类的集合
		List<Similarity> s2=RecommendUserCf.getSimilarityByFavorite(users, user);
		//获取推荐用户集合
		List<User> uList2=RecommendUserCf.getUserList(s2);
		//获取推荐菜品集合
		RecommendFoodsByFavorite=RecommendUserCf.getFoodsListByFavorite(uList2, user);
	    }
	    if (user.getMyFoodList().size()>0) {
		//获取相似度矩阵对应的用户和相似值类的集合
		List<Similarity> s1=RecommendUserCf.getSimilarityByBuy(users, user);
		//获取推荐用户集合
		List<User> uList1=RecommendUserCf.getUserList(s1);
		//获取推荐菜品集合
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
	    System.out.println("根据购买历史推荐列表：");
	    for (Food f : RecommendFoodsByBuy) {
		System.out.println("f:"+f.getFoodName());
	    }
	    System.out.println("根据收藏推荐列表：");
	    for (Food f : RecommendFoodsByFavorite) {
		System.out.println("f:"+f.getFoodName());
	    }
	    mav.addObject("myfoods",RecommendFoodsByBuy);//myfoods为food推荐集合
	    mav.addObject("myfavorites",RecommendFoodsByFavorite);//myfoods为food推荐集合
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
    
    //确认收货
    @RequestMapping("/confirmIntent")
    @ResponseBody
    public ModelAndView confirmIntent(String intentId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	Intent intent = intentService.findIntentById(intentId);
	intent.setOrderStatus("已收货");
	intent.setFinishTime(new Date());
	intent=intentService.updateIntent(intent);
	List<Intent_foods> intent_foodss=intentService.findIntentFoodsListByIntent(intent);
	
	List<Food> foods=FoodUtil.getFoodsFormIntentFoods(intent_foodss);
	myfoodsService.addFoodToMyFoodsList(user,foods);
	
	intent.setIntent_foodss(intent_foodss);
	
	mav.addObject("msg","已收货！请返回继续订餐");
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
	    msg.setMsg("该商品不存在");
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
		
		msg.setMsg("收藏成功");
	    }else {
		 msg.setMsg("您已经收藏过该商品！");
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
		msg.setMsg("取消收藏成功");
	    } else {
		msg.setMsg("取消收藏失败！无法找到该记录！");
	    }
	} else {
	    msg.setMsg("取消收藏失败！无法找到该记录！");
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
    //进入个人中心
    @RequestMapping("/openCenter")
    @ResponseBody
    public ModelAndView openCenter(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	
	Cart cart=cartService.findCartById(user.getCart().getId());
	
	List<Intent> iList1=intentService.findIntent1ByUser(user);//未付款订单
	List<Intent> iList2=intentService.findIntent2ByUser(user);//待收货订单
	
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
    //查看订单
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
    
    //查看我的地址
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
    //添加地址
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
	    msg.setMsg("添加地址成功");
	    return msg;
	} catch (Exception e) {
	    // TODO: handle exception
	    msg.setMsg("系统异常");
	    return msg;
	}
	
    }
    //修改地址
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
	    msg.setMsg("修改成功");
	    return msg;
	} catch (Exception e) {
	    msg.setMsg("系统异常");
	    return msg;
	}
	
    }
    //删除地址
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
	    msg.setMsg("删除成功");
	    return msg;
	} catch (Exception e) {
	    // TODO: handle exception
	    msg.setMsg("系统异常");
	    return msg;
	}
	
    }
    
    //更新密码
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
	    msg.setMsg("修改成功！请重新登录！");
	    return msg;
	} else {
	    msg.setMsg("密码错误！");
	    return msg;
	}
	
	
    }
    
    //前往付款
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
    //删除订单
    @RequestMapping("/deleteIntent")
    @ResponseBody
    public ModelAndView deleteIntent(String intentId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	mav.addObject("cart",cart);
	Intent intent=intentService.findIntentById(intentId);
	if (intent==null) {
	    mav.addObject("msg","订单不存在");
	}else {
	    List<Intent_foods> intent_foodss=intentService.findIntentFoodsListByIntent(intent);
	    for (Intent_foods i : intent_foodss) {
		intentService.deleteIntentFoods(i);
	    }
	    intentService.deleteIntent(intent);
	    mav.addObject("msg","删除订单成功");
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
    
    //查看订单
    @RequestMapping("/viewIntent")
    @ResponseBody
    public ModelAndView viewIntent(String intentId,HttpServletRequest  request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	mav.addObject("cart",cart);
	
	Intent intent=intentService.findIntentById(intentId);
	if (intent==null) {
	    mav.addObject("msg", "订单不存在");
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
    
  //更换手机号
    @RequestMapping("/changePhone")
    @ResponseBody
    public Message changePhone(String phone,String code,HttpServletRequest request) {
	Message msg=new Message();
	System.out.println(phone);
	System.out.println(code);
	User user=(User)request.getSession().getAttribute("u");
	User u=userService.findByPhone(phone);
	if (u!=null) {
	    msg.setMsg("手机号码已存在或该号码为本账号登录手机号码");
	    return msg;
	}else {
	    JSONObject json = (JSONObject)request.getSession().getAttribute("verifyCode");
	    if(!json.getString("verifyCode").equals(code)){
		
		msg.setMsg("验证码错误");
		return msg;
	    }else if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 2){
		msg.setMsg("验证码过期，请重新发送！");
		return msg ;
	    }else {
		System.out.println("更换手机号码成功");
		User user2=userService.findUserById(user.getId());
		user2.setUserPhone(phone);
		userService.updateUser(user2);
		msg.setMsg("更换手机号码成功");
		request.getSession().setAttribute("u", user2);
		return msg ;
	    }
	}
	
    }
    //修改信息
    @RequestMapping("/updateInfor")
    @ResponseBody
    public ModelAndView updateInfor(MultipartFile filedata,String userName,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=cartService.findCartById(user.getCart().getId());
	mav.addObject("cart",cart);
	try {
	    userService.updateInfor(filedata,userName,request);
	    mav.addObject("msg", "修改成功");
	    User u=userService.findUserById(user.getId());
	    request.getSession().setAttribute("u", u);
	    mav.setViewName("/userUpdate.jsp");
	    return mav;
	} catch (Exception e) {
	    // TODO: handle exception
	    mav.addObject("msg", "修改失败");
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
	    mav.addObject("msg","请先登录");
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
