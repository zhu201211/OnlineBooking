package com.cqut.OnlineMealBooking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.Favorite;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Intent;
import com.cqut.OnlineMealBooking.pojo.Intent_foods;
import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.pojo.Message;
import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.SalesState;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.pojo.UserAddress;
import com.cqut.OnlineMealBooking.service.AdminService;
import com.cqut.OnlineMealBooking.service.CartService;
import com.cqut.OnlineMealBooking.service.FoodService;
import com.cqut.OnlineMealBooking.service.IntentService;
import com.cqut.OnlineMealBooking.service.MerchantService;
import com.cqut.OnlineMealBooking.service.MyFoodsService;
import com.cqut.OnlineMealBooking.service.UserService;
import com.cqut.OnlineMealBooking.utils.SendCode;

@Controller
@RequestMapping("/admins")
public class AdminController {
    // 声明service的对象
    @Resource
    private AdminService adminService;
    @Resource
    private CartService cartService;
    @Resource
    private UserService userService;
    @Resource
    private FoodService foodService;
    @Resource
    private IntentService intentService;
    @Resource
    private MerchantService merchantService;
    @Resource
    private MyFoodsService myFoodsService;
    
    @RequestMapping("/login")
    public ModelAndView login(String userName,String userPassword,HttpServletRequest request) {
	System.out.println("管理员登录："+userName+","+userPassword);
	ModelAndView mav = new ModelAndView();
	User user=adminService.login(userName,userPassword);
	if (user==null) {
	    System.out.println("登录失败");
	    mav.addObject("msg", "用户名或者密码错误");
	    mav.setViewName("/admin-login.jsp");
	    return mav;
	} else if (!user.getUserName().equals("admin")) {
	    System.out.println("登录失败");
	    mav.addObject("msg", "用户名或者密码错误");
	    mav.setViewName("/admin-login.jsp");
	    return mav;
	}else {
	    System.out.println("登录成功");
	    mav.addObject("msg", "登录成功");
	    request.getSession().setAttribute("u", user);
	    mav.setViewName("/admin-home.jsp");
	    return mav;
	}
    }
    
    @RequestMapping("/findAllUser")
    @ResponseBody
    public ModelAndView findAllUser() {
	ModelAndView mav = new ModelAndView();
	List<User> users = adminService.findAllUser();
	mav.addObject("users", users);
	mav.setViewName("/allUser.jsp");
	return mav;
    }
    
    @RequestMapping("/updataUser")
    @ResponseBody
    public ModelAndView updataUser(User user,MultipartFile filedata,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=new Message();
	try {
	    msg=adminService.updateUser(user,filedata,request);
	    mav.addObject("msg",msg);
	    mav.setViewName("/admins/findAllUser");
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	    msg.setMsg("系统异常");
	    mav.addObject("msg",msg);
	    mav.setViewName("/admins/findAllUser");
	}
	return mav;
    }
    
    @RequestMapping("/deleteUser")
    @ResponseBody
    public Message deleteUser(String id,HttpServletRequest request) {
	Message msg=new Message();
	try {
	    User user=adminService.findUserById(id);
	    if (user==null) {
		msg.setMsg("用户不存在或已被删除");
	    } else if(user.getId().equals("0001")){
		msg.setMsg("无法删除管理员");
	    }else {
		Cart cart=user.getCart();
		List<UserAddress> addresses=userService.findAddressList(user);
		for (UserAddress userAddress : addresses) {
		    userService.deleteAddress(userAddress);
		}
		List<MyFoods> myFoods=userService.findMyFoodsByUser(user);
		for (MyFoods m : myFoods) {
		    myFoodsService.deleteMyFoods(m);
		}
		List<Favorite> favorites=userService.findAllFavorites(user);
		for (Favorite favorite : favorites) {
		    userService.deleteFavorite(favorite);
		}
		List<Intent> intents=intentService.findAllIntentByUser(user);
		for (Intent intent : intents) {
		    List<Intent_foods> intent_foods=intentService.findIntentFoodsListByIntent(intent);
		    for (Intent_foods intent_foods2 : intent_foods) {
			intentService.deleteIntentFoods(intent_foods2);
		    }
		    intentService.deleteIntent(intent);
		}
		adminService.deleteUser(user);//删除用户
		cartService.deleteCartFoodByCart(cart);
		cartService.deleteCart(cart);
		msg.setMsg("删除成功");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    msg.setMsg("系统异常");
	}
	return msg;
    }
    
    @RequestMapping("/searchUser")
    @ResponseBody
    public ModelAndView searchUser(String searchInfo,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	List<User> users = adminService.searchUsersByInfo(searchInfo);
	mav.addObject("users", users);
	mav.setViewName("/allUser.jsp");
	return mav;
	
    }
    
    
    @RequestMapping("/sendCode")
    @ResponseBody
    public Message sendCode(String userPhone,HttpServletRequest request) {
	System.out.println(userPhone);
	User u=adminService.findByPhone(userPhone);
	Message msg = new Message();
	JSONObject jsonObject=new JSONObject();
	if (u!=null) {
	    msg.setMsg("用户已存在");
	    return msg;
	} else {
	    jsonObject=SendCode.getJson(userPhone);//发送验证码
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
    
    @RequestMapping("/addUser")
    @ResponseBody
    public ModelAndView addUser(MultipartFile filedata, String userPhone,String code,String userName,String userPassword,HttpServletRequest request) {
	Message msg=new Message();
	ModelAndView mav=new ModelAndView();
	System.out.println("进入1");
	User u=adminService.findByPhone(userPhone);
	if (u!=null) {
	    msg.setMsg("用户已存在");
	    mav.addObject("msg",msg);
	    mav.setViewName("/addUser.jsp");
	    return mav;
	} else {
	    System.out.println("进入2");
	    JSONObject json = (JSONObject)request.getSession().getAttribute("verifyCode");
	    if(!json.getString("verifyCode").equals(code)){
		 msg.setMsg("验证码错误");
		 mav.addObject("msg",msg);
		 mav.setViewName("/addUser.jsp");
		 return mav;
		 
	    }else if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 2) {
		msg.setMsg("验证码过期，请重新发送！");
		mav.addObject("msg",msg);
		mav.setViewName("/addUser.jsp");
		return mav;
	    }else {
		try {
		    Cart cart=new Cart();
		    String id = UUID.randomUUID().toString().substring(0, 4);
		    cart.setId(id);
		    cart.setTotalNum(0);
		    cart.setTotalPrice(0);
		    cart.setCartFoods(null);
		    cartService.addCart(cart);
		    msg=adminService.addUser(filedata,userPhone,userName,userPassword,cart,request);
		    mav.addObject("msg",msg);
		    mav.setViewName("/admins/findAllUser");
		    return mav;
		} catch (Exception e) {
		    // TODO: handle exception
		    msg.setMsg("系统异常");
		    mav.addObject("msg",msg);
		    mav.setViewName("/addUser.jsp");
		    return mav;
		}
	    }
	}
	
    }
    
    
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Message updatePassword(String pw1,String pw2,HttpServletRequest request) {
	Message msg=new Message();
	msg=adminService.updatePassword(pw1,pw2,request);
	return msg;
	
    }
    
    @RequestMapping("/findAllMerchant")
    @ResponseBody
    public ModelAndView findAllMerchant() {
	ModelAndView mav = new ModelAndView();
	List<Merchant> Merchants = adminService.findAllMerchant();
	mav.addObject("Merchants", Merchants);
	mav.setViewName("/allMerchant.jsp");
	return mav;
	
    }
    
    @RequestMapping("/findMerchantByPop")
    @ResponseBody
    public ModelAndView findMerchantByPop(HttpServletRequest request) {
	ModelAndView mav = new ModelAndView();
	List<Merchant> Merchants = merchantService.getMerchantLists();
	mav.addObject("Merchants", Merchants);
	mav.setViewName("/allMerchant.jsp");
	return mav;
	
    }
    
    @RequestMapping("/findMerchantByNew")
    @ResponseBody
    public ModelAndView findMerchantByNew(HttpServletRequest request) {
	ModelAndView mav = new ModelAndView();
	List<Merchant> Merchants = adminService.findAllMerchant();
	List<Merchant> merchants = new ArrayList<>();
	for (Merchant merchant : Merchants) {
	    if (merchant.getState().equals("新入驻")) {
		merchants.add(merchant);
	    }
	}
	mav.addObject("Merchants", merchants);
	mav.setViewName("/allMerchant.jsp");
	return mav;
	
    }
    
    @RequestMapping("/findMerchantByYyz")
    @ResponseBody
    public ModelAndView findMerchantByYyz(HttpServletRequest request) {
	ModelAndView mav = new ModelAndView();
	List<Merchant> Merchants = adminService.findAllMerchant();
	List<Merchant> merchants = new ArrayList<>();
	for (Merchant merchant : Merchants) {
	    if (merchant.getState().equals("营业中")) {
		merchants.add(merchant);
	    }
	}
	mav.addObject("Merchants", merchants);
	mav.setViewName("/allMerchant.jsp");
	return mav;
	
    }
    
    @RequestMapping("/findMerchantBydelete")
    @ResponseBody
    public ModelAndView findMerchantBydelete(HttpServletRequest request) {
	ModelAndView mav = new ModelAndView();
	List<Merchant> Merchants = adminService.findAllMerchant();
	List<Merchant> merchants = new ArrayList<>();
	for (Merchant merchant : Merchants) {
	    if (merchant.getState().equals("已注销")) {
		merchants.add(merchant);
	    }
	}
	mav.addObject("Merchants", merchants);
	mav.setViewName("/allMerchant.jsp");
	return mav;
	
    }
    @RequestMapping("/findMerchantFoodById")
    @ResponseBody
    public ModelAndView findMerchantFoodById(String merchantId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	List<Food> foods=adminService.findMerchantFoodById(merchantId);
	List<Food> foods1=new ArrayList<>();
	List<Food> foods2=new ArrayList<>();
	if (foods!=null) {
	    for (Food food : foods) {
		    if (food.getSalesState().getId().equals("00001")) {
			foods1.add(food);
		    }else if (food.getSalesState().getId().equals("00002")) {
			foods2.add(food);
			}
		}
	    mav.addObject("foods1",foods1);
	    mav.addObject("foods2",foods2);
	} else {
	    mav.addObject("foods1",null);
	    mav.addObject("foods2",null);
	}
	mav.setViewName("/allFoods.jsp");
	
	return mav;
	
    }
    @RequestMapping("/addMerchantFoodById")
    @ResponseBody
    public ModelAndView addMerchantFoodById(String merchantId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=new Message();
	Merchant merchant =merchantService.findMerchantById(merchantId);
	if (merchant==null) {
	    msg.setMsg("商家不存在");
	    mav.addObject("msg", msg);
	    mav.setViewName("/admins/findAllMerchant");
	} else {
	    mav.addObject("merchant", merchant);
	    
	    List<Merchant> merchants=adminService.findAllMerchant();
	    mav.addObject("merchants",merchants);
	    mav.setViewName("/admin-addFood.jsp");
	}
	return mav;
	
    }
    
    @RequestMapping("/addMerchant")
    @ResponseBody
    public ModelAndView addMerchant(String name,String phone,String detailaddress,String province,String city,String town,MultipartFile filedata,String info,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=new Message();
	String address=province+city+town+detailaddress;
	try {
	    msg=adminService.addMerchant(name,phone,address,filedata,info,request);
	    mav.addObject("msg",msg);
	    mav.setViewName("/admin-addMerchant.jsp");
	    return mav;
	} catch (Exception e) {
	    // TODO: handle exception
	    msg.setMsg("系统异常");
	    mav.addObject("msg",msg);
	    mav.setViewName("/admin-addMerchant.jsp");
	    return mav;
	}
	
	
    }
    
    @RequestMapping("/findAllFoods")
    @ResponseBody
    public ModelAndView findAllFoods(HttpServletRequest request) {
	ModelAndView mav =new ModelAndView();
	
	List<Food> foods=adminService.findAllFoods();
	List<Food> foods1=new ArrayList<>();
	List<Food> foods2=new ArrayList<>();
	for (Food food : foods) {
	    if (food.getSalesState().getId().equals("00001")) {
		foods1.add(food);
	    }else if (food.getSalesState().getId().equals("00002")) {
		foods2.add(food);
		}
	}
	mav.addObject("foods1",foods1);
	mav.addObject("foods2",foods2);
	mav.setViewName("/allFoods.jsp");
	
	return mav;
	
    }
    
    @RequestMapping("/findFoodsByFoodInfo")
    @ResponseBody
    public ModelAndView findFoodsByFoodInfo(String info,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	
	List<Food> foods=adminService.findFoodsByInfo(info);
	List<Food> foods1=new ArrayList<>();
	List<Food> foods2=new ArrayList<>();
	for (Food food : foods) {
	    if (food.getSalesState().getId().equals("00001")) {
		foods1.add(food);
	    }else if (food.getSalesState().getId().equals("00002")) {
		foods2.add(food);
		}
	}
	mav.addObject("foods1",foods1);
	mav.addObject("foods2",foods2);
	mav.setViewName("/allFoods.jsp");
	
	return mav;
    }
    
    @RequestMapping("/findFoodsByMerchantInfo")
    @ResponseBody
    public ModelAndView findFoodsByMerchantInfo(String info,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	
	List<Food> foods=adminService.findFoodsByMerchantInfo(info);
	List<Food> foods1=new ArrayList<>();
	List<Food> foods2=new ArrayList<>();
	for (Food food : foods) {
	    if (food.getSalesState().getId().equals("00001")) {
		foods1.add(food);
	    }else if (food.getSalesState().getId().equals("00002")) {
		foods2.add(food);
		}
	}
	mav.addObject("foods1",foods1);
	mav.addObject("foods2",foods2);
	mav.setViewName("/allFoods.jsp");
	
	return mav;
    }
    
    @RequestMapping("/deleteFood")
    @ResponseBody
    public Message deleteFood(String foodId,HttpServletRequest request) {
	Message msg=new Message();
	Food food=foodService.findFoodById(foodId);
	if (food!=null) {
	    SalesState s=foodService.findSalesStateById("00002");
	    food.setSalesState(s);
	    foodService.update(food);
	    msg.setMsg("已下线该商品");
	} else {
	    msg.setMsg("没有找到该商品");
	}
	
	return msg;
	
    }
    @RequestMapping("/upFood")
    @ResponseBody
    public Message upFood(String foodId,HttpServletRequest request) {
	Message msg=new Message();
	Food food=foodService.findFoodById(foodId);
	if (food!=null) {
	    Merchant merchant=food.getMerchant();
	    if (!merchant.getState().equals("已注销")) {
		SalesState s=foodService.findSalesStateById("00001");
		food.setSalesState(s);
		foodService.update(food);
		msg.setMsg("已重新上线该商品");
	    }else {
		msg.setMsg("该商品所属商家已注销");
	    }
	    
	    
	} else {
	    msg.setMsg("没有找到该商品");
	}
	
	return msg;
	
    }
    
    @RequestMapping("/findFinishIntent")
    @ResponseBody
    public ModelAndView findFinishIntent(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	
	String state="已收货";
	List<Intent> intents=adminService.findIntentByState(state);
	mav.addObject("intents",intents);
	mav.setViewName("/admin-finish_intent.jsp");
	return mav;
	
    }
    
    @RequestMapping("/findUnpaidIntent")
    @ResponseBody
    public ModelAndView findUnpaidIntent(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	
	String state="未付款";
	List<Intent> intents=adminService.findIntentByState(state);
	mav.addObject("intents",intents);
	mav.setViewName("/admin-unpaid_intent.jsp");
	return mav;
	
    }
    
    @RequestMapping("/findNotfinishIntent")
    @ResponseBody
    public ModelAndView findNotfinishIntent(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	
	String state="待收货";
	List<Intent> intents=adminService.findIntentByState(state);
	mav.addObject("intents",intents);
	mav.setViewName("/admin-wait_intent.jsp");
	return mav;
	
    }
    
    @RequestMapping("/findIntentByUserId")
    @ResponseBody
    public ModelAndView findIntentByUserId(String userId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	List<User> users=adminService.findUserByInfo(userId);
	List<Intent> intents1=new ArrayList<>();
	List<Intent> intents2=new ArrayList<>();
	List<Intent> intents3=new ArrayList<>();
	for (User u : users) {
	    List<Intent> intents=intentService.findAllIntentByUser(u);
	    for (Intent intent : intents) {
		if (intent.getOrderStatus().equals("已收货")) {
		    intents1.add(intent);
		    continue;
		}
		if (intent.getOrderStatus().equals("待收货")) {
		    intents2.add(intent);
		    continue;
		}
		if (intent.getOrderStatus().equals("未付款")) {
		    intents3.add(intent);
		    continue;
		}
	    }
	}
	mav.addObject("intents1",intents1);
	mav.addObject("intents2",intents2);
	mav.addObject("intents3",intents3);
	mav.setViewName("/admin-intent.jsp");
	return mav;
	
    }

    @RequestMapping("/findIntentByInfo")
    @ResponseBody
    public ModelAndView findIntentByInfo(String info,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	List<User> users=adminService.findUserByInfo(info);
	List<Intent> intents1=new ArrayList<>();
	List<Intent> intents2=new ArrayList<>();
	List<Intent> intents3=new ArrayList<>();
	for (User u : users) {
	    List<Intent> intents=intentService.findAllIntentByUser(u);
	    for (Intent intent : intents) {
		if (intent.getOrderStatus().equals("已收货")) {
		    intents1.add(intent);
		    continue;
		}
		if (intent.getOrderStatus().equals("待收货")) {
		    intents2.add(intent);
		    continue;
		}
		if (intent.getOrderStatus().equals("未付款")) {
		    intents3.add(intent);
		    continue;
		}
	    }
	}
	mav.addObject("intents1",intents1);
	mav.addObject("intents2",intents2);
	mav.addObject("intents3",intents3);
	mav.setViewName("/admin-intent.jsp");
	return mav;
	
    }
    
    @RequestMapping("/openAddFood")
    @ResponseBody
    public ModelAndView openAddFood(HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	List<Merchant> merchants=adminService.findAllMerchant();
	mav.addObject("merchants",merchants);
	mav.setViewName("/admin-addFood.jsp");
	return mav;
    }
    
    @RequestMapping("/addFood")
    @ResponseBody
    public ModelAndView addFood(String foodName,String price,String merchantId,String foodIntro,MultipartFile filedata,HttpServletRequest request) {
	System.out.println(foodName+","+price+","+merchantId+","+foodIntro);
	ModelAndView mav=new ModelAndView();
	Message msg=new Message();
	
	msg=foodService.addFood(foodName,price,merchantId,foodIntro,filedata,request);
	mav.addObject("msg", msg);
	
	List<Merchant> merchants=adminService.findAllMerchant();
	mav.addObject("merchants",merchants);
	mav.setViewName("/admin-addFood.jsp");
	return mav;
	
    }
    @RequestMapping("/updateMerchant")
    @ResponseBody
    public ModelAndView updateMerchant(String merchantId,String name,String phone,String address,String intro,String state,MultipartFile filedata,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=adminService.updateMerchant(merchantId,name,phone,address,intro,state,filedata,request);
	mav.addObject("msg", msg);
	mav.setViewName("/admins/findAllMerchant");
	return mav;
	
    }
    @RequestMapping("/deleteMerchantById")
    @ResponseBody
    public ModelAndView deleteMerchantById(String merchantId,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=new Message();
	Merchant merchant = merchantService.findMerchantById(merchantId);
	if (merchant==null) {
	    msg.setMsg("商家不存在");
	    mav.addObject("msg",msg);
	    mav.setViewName("/admins/findAllMerchant");
	} else {
	    List<Food> foods=merchantService.findFoodsByMerchant(merchant);
	    SalesState state=foodService.findSalesStateById("00002");
	    for (Food food : foods) {
		food.setSalesState(state);
		foodService.update(food);
	    }
	    merchant.setState("已注销");
	    merchantService.updateMerchant(merchant);
	    msg.setMsg("注销商家已完成");
	    mav.addObject("msg",msg);
	    mav.setViewName("/admins/findMerchantBydelete");
	}
	
	return mav;
	
    }
    @RequestMapping("/upState")
    @ResponseBody
    public ModelAndView upState(String merchantId,HttpServletRequest request) {
	ModelAndView mav =new ModelAndView();
	Message msg=new Message();
	Merchant merchant=merchantService.findMerchantById(merchantId);
	if (merchant!=null) {
	    if (!merchant.getState().equals("已注销")) {
		msg.setMsg("该商家处于营业中");
		mav.addObject("msg", msg);
		mav.setViewName("/admins/findAllMerchant");
		return mav;
	    } else {
		List<Food> foods=merchantService.findFoodsByMerchant(merchant);
		SalesState state=foodService.findSalesStateById("00001");
		for (Food food : foods) {
		    food.setSalesState(state);
		    foodService.update(food);
		}
		merchant.setState("营业中");
		merchantService.updateMerchant(merchant);
		msg.setMsg("商家已重新营业");
		mav.addObject("msg",msg);
		mav.setViewName("/admins/findAllMerchant");
	    }
	} else {
	    msg.setMsg("商家不存在");
	    mav.addObject("msg", msg);
	    mav.setViewName("/admins/findAllMerchant");
	}
	
	return mav;
	
    }
    
    @RequestMapping("/findMerchantByInfo")
    @ResponseBody
    public ModelAndView findMerchantByInfo(String info,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=new Message();
	if (info==null||info=="") {
	    msg.setMsg("查询内容不能为空");
	    mav.addObject("msg", msg);
	    mav.setViewName("/admins/findAllMerchant");
	} else {
	    List<Merchant> merchants=adminService.findMerchantByInfo(info);
	    mav.addObject("Merchants", merchants);
	    mav.setViewName("/allMerchant.jsp");
	}
	
	return mav;
	
    }
    @RequestMapping("/updateFood")
    @ResponseBody
    public ModelAndView updateFood(String foodId,String name,String price,String intro,MultipartFile filedata,HttpServletRequest request) {
	ModelAndView mav=new ModelAndView();
	Message msg=adminService.updateFood(foodId,name,price,intro,filedata,request);
	mav.addObject("msg", msg);
	mav.setViewName("/admins/findAllFoods");
	
	return mav;
	
    }
    @RequestMapping("/findFoodByXL")
    @ResponseBody
    public ModelAndView findFoodByXL() {
	ModelAndView mav =new ModelAndView();
	List<Food> foods=foodService.getFoodList();
	mav.addObject("foods1",foods);
	mav.addObject("foods2",new ArrayList<>());
	mav.setViewName("/allFoods.jsp");
	return mav;
	
    }
    @RequestMapping("/findFoodByZS")
    @ResponseBody
    public ModelAndView findFoodByZS() {
	ModelAndView mav =new ModelAndView();
	
	List<Food> foods=adminService.findAllFoods();
	List<Food> foods1=new ArrayList<>();
	for (Food food : foods) {
	    if (food.getSalesState().getId().equals("00001")) {
		foods1.add(food);
	    }
	}
	mav.addObject("foods1",foods1);
	mav.addObject("foods2",new ArrayList<>());
	mav.setViewName("/allFoods.jsp");
	return mav;
	
    }
    @RequestMapping("/findFoodByXX")
    @ResponseBody
    public ModelAndView findFoodByXX() {
	ModelAndView mav =new ModelAndView();
	
	List<Food> foods=adminService.findAllFoods();
	List<Food> foods2=new ArrayList<>();
	for (Food food : foods) {
	    if (food.getSalesState().getId().equals("00002")) {
		foods2.add(food);
	    }
	}
	mav.addObject("foods1",new ArrayList<>());
	mav.addObject("foods2",foods2);
	mav.setViewName("/allFoods.jsp");
	return mav;
	
    }
    @RequestMapping("/viewIntentXQ")
    @ResponseBody
    public ModelAndView viewIntentXQ(String intentId,HttpServletRequest request) {
	ModelAndView mav =new ModelAndView();
	Message msg=new Message();
	Intent intent=intentService.findIntentById(intentId);
	if (intent!=null) {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(intent);
	    intent.setIntent_foodss(iFoods);
	    mav.addObject("intent", intent);
	    mav.setViewName("/intent_xq.jsp");
	} else {
	    msg.setMsg("订单不存在");
	    mav.addObject("msg",msg);
	    mav.setViewName("/admins/findFinishIntent");
	}
	
	return mav;
	
    }
    @RequestMapping("/deleteIntentById")
    @ResponseBody
    public Message deleteIntentById(String intentId,HttpServletRequest request) {
	Message msg=new Message();
	Intent intent=intentService.findIntentById(intentId);
	if (intent==null) {
	    msg.setMsg("订单不存在");
	    return msg;
	} else {
	    List<Intent_foods> iFoods=intentService.findIntentFoodsListByIntent(intent);
	    for (Intent_foods intent_foods : iFoods) {
		intentService.deleteIntentFoods(intent_foods);
	    }
	    intentService.deleteIntent(intent);
	    msg.setMsg("删除订单成功");
	    return msg;
	}
	
    }
}
