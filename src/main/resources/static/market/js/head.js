let xmlHttp = new XMLHttpRequest();
/**
 * @param {string} url 链接
 * @param {string} method 请求类型
 * @return {Object} 返回json对象
 */
function get(url,method){
	let result;
	xmlHttp.onreadystatechange = function(){
		if(xmlHttp.readyState == 4){
			if(xmlHttp.status == 200){
				result = xmlHttp.responseText;
			}
		}
	}
	xmlHttp.open(method,url,false);
	xmlHttp.send();
	return JSON.parse(result);
}
window.onload = function(){
	let temp = setInterval(function(){run()},5000);
	let nowPhoto = 0;
	let photoCount;
	addPhoto();
	for(let i = 1;i <= 5;i++){
		let result = "";
		switch(i){
			case 1:
				result = "one";
				break;
			case 2:
				result = "two";
				break;
			case 3:
				result = "three";
				break;
			case 4:
				result = "four";
				break;
			case 5:
				result = "five";
				break;
		}

		$("#type_"+result).hover(
		function(){
			$(this).css("background-color","rgba(255,250,240,1)");
		},
		function(){
			$(this).css("background-color","rgba(255,250,240,0.5)");
		}
		);
	}

	function run(forward = 1){
		$("#head_photo").find($("#head_photo_"+nowPhoto)).fadeOut(300);
		$("#hand_photo_down_button_"+(photoCount-nowPhoto-1)).css("opacity","40%");
		nowPhoto+=forward+photoCount;
		nowPhoto%=photoCount;
		$("#head_photo").find($("#head_photo_"+nowPhoto)).fadeIn(150);
		$("#hand_photo_down_button_"+(photoCount-nowPhoto-1)).css("opacity","100%");
	}
	//添加图片方法
	function addPhoto(){
		let downButton = $("#head_photo_down_button_part").element;
		let result = get("/file/base/get/wheel_chart","get");
		let json = result.data;
		photoCount = json.length;
		for(let i = 0;i<photoCount;i++){
			let img = document.createElement("img");
			let buttonDiv = document.createElement("div");
			buttonDiv.id = "hand_photo_down_button_"+(photoCount-i-1).toString();
			if(i == 0){
				buttonDiv.style = "opacity:100%";
			}else{
				img.style = "display:none";
			}
			buttonDiv.addEventListener("click",clickEvent,false);
			img.src = json[i].url;
			img.id = "head_photo_"+i.toString();
			$("#head_photo").append(img);
			$("#head_photo_down_button_part").append(buttonDiv);
		}
	}
	//点击底部按钮执行的方法
	function clickEvent(){
		let ids = $(this).attr('id').toString().split("_",10);
		let id = ids[ids.length-1];
		console.log(id);
		if(photoCount - parseInt(id) - 1 == nowPhoto){
			return ;
		}
		$("#head_photo_"+nowPhoto).fadeOut(300);
		$("#hand_photo_down_button_"+(photoCount-nowPhoto-1)).css("opacity","40%");
		$("#head_photo_"+(photoCount-parseInt(id)-1)).fadeIn(150);
		$("#hand_photo_down_button_"+id).css("opacity","100%");
		nowPhoto = photoCount - parseInt(id) - 1;
		clearInterval(temp);
		temp = setInterval(function (){run()},5000);
	}
	//给左右按钮添加点击事件
	$("#head_photo_button_left").click(function (){
		run(-1);
		clearInterval(temp);
		temp = setInterval(function (){run()},5000);
	});
	$("#head_photo_button_right").click(function (){
		run();
		clearInterval(temp);
		temp = setInterval(function (){run()},5000);
	});
	$("#head_photo_down_button_part").hover(
		function (){
			$(this).find("div").css("height","60%");
		},
		function (){
			$(this).find("div").css("height","20%")
		}
	)
	$("#head_photo_back").hover(function(){
			clearInterval(temp);
			$("#head_photo_button_left_photo").fadeIn(15);
			$("#head_photo_button_right_photo").fadeIn(15);
		},
		function(){
			clearInterval(temp);
			temp = setInterval(function (){run()},5000);
			$("#head_photo_button_right_photo").fadeOut(15);
			$("#head_photo_button_left_photo").fadeOut(15);
		});
	$("#select").hover(
		function (){
			$("#hidden_select_type").css("display","block");
		},
		function (){
			$("#hidden_select_type").css("display","none");
		}
	)
}