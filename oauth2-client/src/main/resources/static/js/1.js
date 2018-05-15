/**
 * 
 */
        
$(function(){
 		var username = $("input[name='username']").val();
 		var password = $("input[name='password']").val();
 		if(username != null && username !="" && password != null && password !=""){
 			$("#from1").submit();
 		}
	
	if(getUrlParam("code") !=null)
	{
		var code = getUrlParam("code");
		
		$.ajax({

			type: "GET",
			url: "/public/getToken",
			data: {"grant_type":"authorization_code","code":code,"redirect_uri":"http://localhost:9082/public/autoLogin"},
			dataType: "json",

				success: function(data){
					if(data.respbody.access_token != null && data.respbody.access_token != "")
					{
						autoLogin(data.respbody.access_token);
					}
				}
			});
	}
	
});

function autoLogin(token){
	window.location.href="http://localhost:9082/public/autoLogin?token="+token;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}