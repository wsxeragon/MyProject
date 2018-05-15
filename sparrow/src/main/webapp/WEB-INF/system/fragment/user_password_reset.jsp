<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>

<form id="userPasswordForm" class="form-horizontal" method="post">
	<!-- hidden -->
	<input type="hidden" name="operId" value="${user.operId}">
	
	<!-- #section:elements.form -->
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 登录名 </label>

		<div class="col-sm-9">
			<input  type="text" readonly name="operLogin" value="${user.operLogin}"
				class="col-xs-10 col-sm-5"/>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 用户名 </label>

		<div class="col-sm-9">
			<input type="text" name="operName" value="${user.operName}"
				class="col-xs-10 col-sm-5"/>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 新密码 </label>

		<div class="col-sm-9">
			<input type="password" id="newPassword" name="operPassword" value=""
				class="col-xs-10 col-sm-5"/>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 确认密码 </label>

		<div class="col-sm-9">
			<input type="password" name="operPasswordConfirm" value=""
				class="col-xs-10 col-sm-5"/>
		</div>
	</div>
</form>

<script type="text/javascript">
//form表单校验
function userPasswordFormValidate(){
	return $("#userPasswordForm").validate({
       debug:false,
		rules : {
			operPassword: {
		    	required: true,
		        minlength: 6
		    },
			operPasswordConfirm: {
		    	required: true,
		        minlength: 6,
		        equalTo: "#newPassword"
		    }
		},
		messages : {
			operPassword: {
		    	required: "请输入密码！",
		        minlength: "密码长度不能小于6位"
		    },
			operPasswordConfirm: {
		    	required: "请输入密码！",
		        minlength: "密码长度不能小于6位",
		        equalTo: "两次密码输入不一致"
		    }
		}	
    }).form();
}					
</script>