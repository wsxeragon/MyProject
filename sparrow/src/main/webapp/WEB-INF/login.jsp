<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tb"%>

<!DOCTYPE html>
<html>
<head>

<base href="<%=basePath%>" />
<%@include file="/WEB-INF/import/base.jspf"%>
 <style type="text/css">  
            #code  
            {  
                font-family:Arial;  
                font-style:italic;  
                font-weight:bold;  
                border:0;  
                letter-spacing:2px;  
                color:blue;  
            }  
        </style>  
</head>

<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<i class="ace-icon fa fa-leaf green"></i> <span class="red">sparrow</span>
								<span class="white" id="id-text2">管理框架</span>
							</h1>
							<h4 class="blue" id="id-company-text">&copy; inphase</h4>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box"
								class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="ace-icon fa fa-coffee green"></i> 系统名称
										</h4>

										<div class="space-6"></div>

										<form method="post" id="loginForm" action="login">
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														name="userName" type="text" class="form-control"
														placeholder="Username" /> <i class="ace-icon fa fa-user"></i>
												</span> </label> 
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														name="password" type="password" class="form-control"
														placeholder="Password" /> <i class="ace-icon fa fa-lock"></i>
												</span> </label>
												 <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input name="validationCode"
														id="validationCode" type="text" class="form-control"
														placeholder="验证码" style="width:70%" /> <input type = "button" id="code" onclick="createCode()"/> 
												</span>  </label>
											
												<div style="color:red;" id="errordiv" class="hide">
													<label id="errorinfo"></label>
												</div>

												<div class="space-4"></div>

												<div class="clearfix">
													<label class="inline"> <input type="checkbox" name="rememberMe"
														class="ace" /> <span class="lbl"> 记住我 </span> </label>

													<button type="button" onclick="fromSubmit();"
														class="width-35 pull-right btn btn-sm btn-primary">
														<i class="ace-icon fa fa-key"></i> <span
															class="bigger-110">登录</span>
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>

									</div>
									<!-- /.widget-main -->

									<div class="toolbar clearfix">
										&nbsp;<br/>&nbsp;
									</div>
								</div>
								<!-- /.widget-body -->
							</div>
							<!-- /.login-box -->
							
						</div>
						<!-- /.position-relative -->

						<div class="navbar-fixed-top align-right">
							<br /> &nbsp; <a id="btn-login-dark" href="#">Dark</a> &nbsp; <span
								class="blue">/</span> &nbsp; <a id="btn-login-blur" href="#">Blur</a>
							&nbsp; <span class="blue">/</span> &nbsp; <a id="btn-login-light"
								href="#">Light</a> &nbsp; &nbsp; &nbsp;
						</div>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->

	<!-- 基础脚本 -->
	<tb:base_script />

	<script type="text/javascript">
		jQuery(function($) {	
			createCode();
			//错误提示处理
			if("${error}" != ""){
					alert("${msg}");
			}
			
			$('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');

				e.preventDefault();
			});
			$('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');

				e.preventDefault();
			});
			$('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');

				e.preventDefault();
			});
		});		
		
		//form表单校验
		function formValidate(){
			var inputCode = $("#validationCode").val().toUpperCase(); //取得输入的验证码并转化为大写        
			 if(inputCode != "" && inputCode != code ) { //若输入的验证码与产生的验证码不一致时  
			        alert("验证码输入错误"); //则弹出验证码输入错误  
			        //createCode();//刷新验证码  
			        $("#validationCode").val("");//清空文本框  
			        return;
			 } 
			return $("#loginForm").validate({
		        debug:false,
				rules : {
					userName : "required",
					password : "required",
					validationCode:"required"
					
				},
				messages : {
					userName : "请输入用户名！",
					password : "请输入密码！",
					validationCode:"请输入验证码"
				}	
		    }).form();
		}		
		
		//form表单提交
		function fromSubmit(){
			if(formValidate()){
				$("#loginForm").submit();
			}else{
				$("#errordiv").addClass("hide");
			}
		}		
		
		var code ; //在全局定义验证码  
		var task;
		//产生验证码  
		function createCode(){  
			clearTimeout(task);
		     code = "";   
		     var codeLength = 4;//验证码的长度  
		     var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',  
		     'S','T','U','V','W','X','Y','Z');//随机数  
		     for(var i = 0; i < codeLength; i++) {//循环操作  
		        var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）  
		        code += random[index];//根据索引取得随机数加到code上  
		    }  
		    $("#code").val(code);//把code值赋给验证码  
		    
		    var task= setTimeout(function(){
		    	code = "";
		    }, 60000);
		}  
	</script>
</body>

</html>
