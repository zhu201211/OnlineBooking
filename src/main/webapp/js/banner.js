var _index=0;
		var timePlay=null;
		$("#container .ImgList").eq(0).show().siblings("div").hide();//只显示第一张

		//自动轮播
		function autoPlay(){
		    timePlay=setInterval(function(){
		        _index++;
		        if(_index<5){
		            if(_index==4){
		            	_index=-1; 
		            }
		            $("#container .ImgList").eq(_index).fadeIn().siblings("p").fadeOut();
		        }else{
		        	_index=-1;
		        }
		    },4000);
		};
		autoPlay();//调用和执行
		