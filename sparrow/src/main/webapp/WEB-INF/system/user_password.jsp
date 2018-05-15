<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tb"%>

<!DOCTYPE html>
<html>
<head>
<!-- 基础样式,页面都需要引用 -->
<%@include file="/WEB-INF/import/base.jspf"%>

<script type="text/javascript">
	$(function() {
		//菜单选中设置
		curMenu("menu4");
	});
</script>
</head>

<body class="no-skin">
	<!-- 导航菜单 -->
	<tb:navbar uc="${loginUser}" />

	<!-- 下方容器 -->
	<div class="main-container ace-save-state" id="main-container">

		<!-- 侧边菜单栏 -->
		<tb:sidebar functionItems="${loginUser.functionItemList}" />

		<!-- 主要功能展现内容 -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i><a href="#">首页</a>
						</li><li>系统管理</li>
						<li class="active">密码修改</li>
					</ul>
				</div>

				<div class="page-content">
					<!-- 页面设置功能 -->
					<tb:setting />
					
					<!-- 注:页面内容在此添加start -->
					<form id="passwordForm"  class="form-horizontal" action="pwdupdate" method="post">
						<div class="space-20"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1">旧密码 </label>
					
							<div class="col-sm-9">
								<input type="password" id="oldPassword" name="oldPassword" class="col-xs-10 col-sm-5"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1">新密码 </label>
					
							<div class="col-sm-9">
								<input type="password" id="newPassword" name="newPassword" class="col-xs-10 col-sm-5"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1">确认密码 </label>
					
							<div class="col-sm-9">
								<input type="password" id="newPasswordConfirm"  name="newPasswordConfirm" class="col-xs-10 col-sm-5"/>
							</div>
						</div>
										
						<div class="col-md-offset-4 col-md-8">	
							<c:if test="${info != null}">
								<div style="color:red;">
									<c:out value="${info}" />
								</div>
							</c:if>
							<button class="btn btn-info" type="submit">
								<i class="ace-icon fa fa-check bigger-110"></i>确&nbsp;&nbsp;定</button>
						</div>						
					</form>
					<!-- 页面内容end -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 页脚 -->
		<tb:footer />

	</div>
	<!-- /.main-container -->

	<!-- 基础脚本 -->
	<tb:base_script />
		
	<script type="text/javascript">
	jQuery(function($) {
		jQuery.validator.addMethod("isPassword", function(value, element) {
	        var mobile = /(?=.*[0-9])(?=.*[a-zA-Z]).{6,30}/;
	        return this.optional(element) ||  mobile.test(value);
	    }, "请正确填写您的手机号码");
		
		$("#passwordForm").validate({
		    rules : {
				oldPassword : "required",
				newPassword: {
			    	required: true,
			        minlength: 6,
			        isPassword:true
			    },
				newPasswordConfirm: {
			    	required: true,
			        minlength: 6,
			        equalTo: "#newPassword",
			        	isPassword:true
			    }
			},
			messages : {
				oldPassword : "请输入旧密码",
				newPassword: {
			    	required: "请输入密码！",
			        minlength: "密码长度不能小于6位",
			        isPassword:"密码太简单"
			    },
				newPasswordConfirm: {
			    	required: "请输入密码！",
			        minlength: "密码长度不能小于6位",
			        equalTo: "两次密码输入不一致",
			        isPassword:"密码太简单"
			    }
			}	
		});
	});
	</script>
	
	</body>
</html>

