package com.cqut.OnlineMealBooking.utils;

import java.util.*;
import java.util.Map.Entry;

import com.cqut.OnlineMealBooking.pojo.*;

public class RecommendUserCf {
    //根据购买历史获取相似矩阵
    public static List<Similarity> getSimilarityByBuy(List<User> users,User user) {
	
	List<User> usersList=new ArrayList<>();
	for (User user2 : users) {
	    if (user2.getMyFoodList().size()>0) {
		usersList.add(user2);
	    } 
	}
	int N=usersList.size();//N为用户个数
	
	Map<User, Integer> userItemLength = new HashMap<>();
	//存储每一个用户对应的不同物品总数 eg: A 3 
	
	Map<Food, Set<User>> itemUserCollection = new HashMap<>();
	//建立物品到用户的倒排表 eg: a A B
	
	Set<Food> items = new HashSet<>();
	//辅助存储物品集合 
	
	Map<User, Integer> userID = new HashMap<User, Integer>();
	//辅助存储每一个用户的用户ID映射 
	
	Map<Integer, User> idUser = new HashMap<Integer, User>();
	//辅助存储每一个ID对应的用户映射
	
	List<Similarity> similarities=new ArrayList<>();
	//得出所有用户的相似度
	int[][] sparseMatrix = new int[N][N];
	//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】 
	System.out.println("用户个数 N:"+N);
	int i=0;
	for (User u : usersList) {
	    userItemLength.put(u, u.getMyFoodList().size());
	    userID.put(u, i);
	    idUser.put(i, u);
	    //用户ID与稀疏矩阵建立对应关系 
		    
	    //建立物品--用户倒排表 
	    for(int j=0;j<u.getMyFoodList().size();j++) {
		if (items.contains(u.getMyFoodList().get(j).getFood())) {
		    //如果已经包含对应的物品--用户映射，直接添加对应的用户 
		    itemUserCollection.get(u.getMyFoodList().get(j).getFood()).add(u);
		} else {
		    items.add(u.getMyFoodList().get(j).getFood());
		    itemUserCollection.put(u.getMyFoodList().get(j).getFood(), new HashSet<User>());
		    //创建物品--用户倒排关系 
		    itemUserCollection.get(u.getMyFoodList().get(j).getFood()).add(u);
		}
	    }
	    i++;
	}
	//遍历 itemUserCollection 物品--用户倒排关系
	Iterator<Entry<Food, Set<User>>> iterator=itemUserCollection.entrySet().iterator();
	//计算相似度矩阵【稀疏】
	while(iterator.hasNext()) {
	    Set<User> us=iterator.next().getValue();
	    for (User user1 : us) {
		for (User user2 : us) {
		    if (user1.getId()==user2.getId()||user1.getId().equals(user2.getId())) {
			continue;
		    } else {
			//计算用户1与用户2都有正反馈的物品总数
			sparseMatrix[userID.get(user2)][userID.get(user1)]+=1;	
		    }
		}
	    }
	}
	User u1=new User();
	for (User user2 : usersList) {
	    if (user2.getId()==user.getId()||user2.getId().equals(user.getId())) {
		u1=user2;
	    } 
	}
	System.out.println(u1.getUserName());

	for (Entry<Integer, User> entry: idUser.entrySet()) {
	    int key = entry.getKey();
	    User t = entry.getValue();
	    System.out.println(key+"+"+t.getUserName());
	}
	
	//计算用户之间的相似度【余弦相似性】 
	int recommendUserId=userID.get(u1);
	for (int j = 0;j < sparseMatrix.length; j++) {
	    if(j != recommendUserId){
//		System.out.println(idUser.get(recommendUserId)+
//			"--"+idUser.get(j)+"相似度:"+
//			sparseMatrix[recommendUserId][j]
//			/Math.sqrt(userItemLength.get(idUser.get(recommendUserId))
//			*userItemLength.get(idUser.get(j))));
//		
		double value=(double)sparseMatrix[recommendUserId][j]/(double)Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j)));
		Similarity sim=new Similarity();
		sim.setUser(idUser.get(j));
		sim.setValue(value);
		similarities.add(sim);
	    }
	}
	
	return similarities;
	
    }
    
    //获取相似用户列表
    public static List<User> getUserList(List<Similarity> similarities) {
	List<Similarity> s1=new  ArrayList<>();
	List<User> uList=new ArrayList<>();//推荐用户列表
	for (Similarity s: similarities) {
	    if (s.getValue() > 0) {
		s1.add(s);
		System.out.println(s.getUser().getUserName()+"--"+s.getValue());
	    } 
	}
	//进行相似值降序排序
	Collections.sort(s1, new Comparator<Similarity>(){
	           
	    public int compare(Similarity p1, Similarity p2) {
		//按照Similarity的相似值value进行降序排列
		if(p1.getValue() < p2.getValue()){
		    return 1;
		}
		return -1;
	    }
	});
	for (Similarity s: s1) {
	    uList.add(s.getUser()); 
	}
	return uList;
	
    }
    
    
    //根据购买历史获取推荐菜品列表
    public static List<Food> getFoodsListByBuy(List<User> uList,User user) {
	
	List<MyFoods> myFoodsOfUser=user.getMyFoodList();
	List<Food> FoodsList=new ArrayList<>();
	
	for (User u : uList) {
	    List<MyFoods> mFoods=u.getMyFoodList();
	    for (MyFoods ms : mFoods) {
		boolean f=true;
		for (MyFoods mf : myFoodsOfUser) {
		    if(ms.getFood().getId()==mf.getFood().getId()||ms.getFood().getId().equals(mf.getFood().getId())) {
			f=false;
			break;
		    }
		}
		if (f) {
		    FoodsList.add(ms.getFood());
		}
	    }
	}
	
	return FoodsList;
	
    }
    
    //根据收藏获取相似矩阵
    public static List<Similarity> getSimilarityByFavorite(List<User> users,User user) {
	
	List<User> usersList=new ArrayList<>();
	for (User user2 : users) {
	    if (user2.getMyFoodList().size()>0) {
		usersList.add(user2);
	    } 
	}
	
	int N=usersList.size();//N为用户个数
	
	int[][] sparseMatrix = new int[N][N];
	//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】 
	
	Map<User, Integer> userItemLength = new HashMap<>();
	//存储每一个用户对应的不同物品总数 eg: A 3 
	
	Map<Food, Set<User>> itemUserCollection = new HashMap<>();
	//建立物品到用户的倒排表 eg: a A B
	
	Set<Food> items = new HashSet<>();
	//辅助存储物品集合 
	
	Map<User, Integer> userID = new HashMap<User, Integer>();
	//辅助存储每一个用户的用户ID映射 
	
	Map<Integer, User> idUser = new HashMap<Integer, User>();
	//辅助存储每一个ID对应的用户映射
	
	List<Similarity> similarities=new ArrayList<>();
	//得出所有用户的相似度
	 
	System.out.println("用户个数 N:"+N);
	int i=0;
	for (User u : usersList) {
	    userItemLength.put(u, u.getFavorites().size());
	    userID.put(u, i);
	    idUser.put(i, u);
	    //用户ID与稀疏矩阵建立对应关系 
		    
	    //建立物品--用户倒排表 
	    for(int j=0;j< u.getFavorites().size();j++) {
		if (items.contains(u.getFavorites().get(j).getFood())) {
		    //如果已经包含对应的物品--用户映射，直接添加对应的用户 
		    itemUserCollection.get(u.getFavorites().get(j).getFood()).add(u);
		} else {
		    items.add(u.getFavorites().get(j).getFood());
		    itemUserCollection.put(u.getFavorites().get(j).getFood(), new HashSet<User>());
		    //创建物品--用户倒排关系 
		    itemUserCollection.get(u.getFavorites().get(j).getFood()).add(u);
		}
	    }
	    i++;
	}
	//遍历 itemUserCollection 物品--用户倒排关系
	Iterator<Entry<Food, Set<User>>> iterator=itemUserCollection.entrySet().iterator();
	//计算相似度矩阵【稀疏】
	while(iterator.hasNext()) {
	    Set<User> us=iterator.next().getValue();
	    for (User user1 : us) {
		for (User user2 : us) {
		    if (user1.getId()==user2.getId()||user1.getId().equals(user2.getId())) {
			continue;
		    } else {
			//计算用户1与用户2都有正反馈的物品总数
			sparseMatrix[userID.get(user2)][userID.get(user1)]+=1;	
		    }
		}
	    }
	}
	User u1=new User();
	for (User user2 : usersList) {
	    if (user2.getId()==user.getId()||user2.getId().equals(user.getId())) {
		u1=user2;
	    } 
	}
	System.out.println(u1.getUserName());

	for (Entry<Integer, User> entry: idUser.entrySet()) {
	    int key = entry.getKey();
	    User t = entry.getValue();
	    System.out.println(key+"+"+t.getUserName());
	}
	
	//计算用户之间的相似度【余弦相似性】 
	int recommendUserId=userID.get(u1);
	for (int j = 0;j < sparseMatrix.length; j++) {
	    if(j != recommendUserId){
//		System.out.println(idUser.get(recommendUserId)+
//			"--"+idUser.get(j)+"相似度:"+
//			sparseMatrix[recommendUserId][j]
//			/Math.sqrt(userItemLength.get(idUser.get(recommendUserId))
//			*userItemLength.get(idUser.get(j))));
//		
		double value=(double)sparseMatrix[recommendUserId][j]/(double)Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j)));
		Similarity sim=new Similarity();
		sim.setUser(idUser.get(j));
		sim.setValue(value);
		similarities.add(sim);
	    }
	}
	
	return similarities;
	
    }
  //根据收藏获取推荐菜品列表
    public static List<Food> getFoodsListByFavorite(List<User> uList,User user) {
	
	List<Favorite> favoriteOfUser=user.getFavorites();
	List<Food> FoodsList=new ArrayList<>();
	
	for (User u : uList) {
	    List<Favorite> fss=u.getFavorites();
	    for (Favorite fs : fss) {
		boolean b=true;
		for (Favorite f : favoriteOfUser) {
		    if(fs.getFood().getId()==f.getFood().getId()||fs.getFood().getId().equals(f.getFood().getId())) {
			b=false;
			break;
		    }
		}
		if (b) {
		    FoodsList.add(fs.getFood());
		}
	    }
	}
	
	return FoodsList;
	
    }
}
