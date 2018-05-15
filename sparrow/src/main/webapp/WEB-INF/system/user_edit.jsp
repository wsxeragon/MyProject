<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tb"%>

<!DOCTYPE html>
<html>
<head>
<title>用户编辑</title>

<!-- 基础样式,页面都需要引用 -->
<%@include file="/WEB-INF/import/base.jspf"%>

<!-- 树型菜单样式 -->
<link rel="stylesheet" href="component/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript">
	$(function() {
		//菜单选中设置
		curMenu("menu2");
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a></li>
						<li>系统管理</li>
						<li class="active">用户管理</li>
					</ul>
				</div>
				
				<div class="page-content">
					<!-- 页面设置功能 -->
					<tb:setting />
					
					<div class="row">
						<div class="col-xs-12">
							<form id="userForm" action="<c:if test='${empty user.operId }'>user/saveuser</c:if><c:if test='${!empty user.operId }'>user/update</c:if>" class="form-horizontal" method="post" enctype="multipart/form-data">
								<!-- hidden -->
								<input type="hidden" name="operId" value="${empty user.operId?0:user.operId}">
								<input type="hidden" id="operHeadpicValue" name="operHeadpic" value="${user.operHeadpic}">
								<!-- #section:elements.form -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="operLogin"> 登录名 </label>
									<div class="col-sm-9">
										<input type="text" class="col-xs-10 col-sm-5" <c:if test="${!empty user.operLogin}"> readonly </c:if> name="operLogin" value="${user.operLogin}" id="operLogin" />
									</div>
								</div>
	
								<c:if test="${empty user.operLogin}">
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="operPassword"> 密码 </label>
										<div class="col-sm-9">
			     							<input type="password" autocomplete="new-password" id="operPassword" name="operPassword" value="" class="col-xs-10 col-sm-5" />
										</div>
									</div>
								</c:if>
	
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="operName"> 用户名 </label>
									<div class="col-sm-9">
										<input type="text" id="operName" name="operName" value="${user.operName}"
											class="col-xs-10 col-sm-5"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="operPhone"> 联系电话 </label>
									<div class="col-sm-9">
										<input type="text" id="operPhone" name="operPhone" value="${user.operPhone}"
											class="col-xs-10 col-sm-5"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="operHeadpic"> 头像 </label>

									<div class="col-sm-9" style="width:200px;">
										<input type="file" id="operHeadpic" name="headpic"/>
										<label id="headPic-error" class="hide fileerror" for="operHeadpic"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> 性别 </label>

									<div class="col-sm-9">
										<select class="multiselect" name="operSex">
											<option <c:if test='${user.operSex==0 }'>selected</c:if> value="0">女</option>
											<option <c:if test='${user.operSex==1 }'>selected</c:if> value="1">男</option>
										</select>
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="operQQ"> QQ </label>

									<div class="col-sm-9">
										<input type="text" id="operQQ" name="operQQ" value="${user.operQQ}"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="operEmail"> 邮箱 </label>

									<div class="col-sm-9">
										<input type="text" id="operEmail" name="operEmail" value="${user.operEmail}"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="operAddress"> 联系地址 </label>

									<div class="col-sm-9">
										<input type="text" id="operAddress" name="operAddress" value="${user.operAddress}"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> 状态 </label>

									<div class="col-sm-9">
										<select class="multiselect" name="operStatus">				
											<option <c:if test='${user.operStatus==1 }'>selected</c:if> value="1">正常</option>
											<option <c:if test='${user.operStatus==2 }'>selected</c:if> value="2">禁用</option>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> 所属地区 </label>
									<div class="col-sm-9" style="width:368px;" >
										<div class="row">
											<div class="col-sm-4">
												<select id="proviace" name="operProvience" class="form-control" >
													<option selected="selected" value="0">请选择</option>
												</select>
											</div>
											<div class="col-sm-4">
												<select id="city" name="operCity" class="form-control">
													<option selected="selected" value="0">请选择</option>
												</select>
											</div>
											<div class="col-sm-4">
												<select id="district" name="operDistrict" class="form-control">
													<option selected="selected" value="0">请选择</option>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> 权限组 </label>
									<div class="col-sm-9" style="width:368px;">
										<div class="control-group">
											<div class="checkbox" >
												<c:forEach items="${rolelist}"  var="rolelist">
													<label class="col-sm-6">
														<input name="operRole" type="checkbox" class="ace ace-checkbox-2" <c:if test="${rolelist.roleSelected==1 }">checked="checked"</c:if> value="${rolelist.roleId }"/>
														<span class="lbl"> ${rolelist.roleName }</span>
													</label>
												</c:forEach>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> 可管理地区 </label>
									<div class="col-sm-9" style="width:368px;heigth:162px;">
										<div class="widget-box">
											<div class="widget-body">
												<div class="widget-main scrollable">
													<ul id="areaTree" class="ztree"></ul>
													<input type="hidden" id="operArea" name="operArea" value="${user.operArea}"/>
												</div>
											</div>
										</div> 
									</div>
								</div>
								<div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">
										<a class="btn"  href="user/main">
											<i class="ace-icon fa fa-reply bigger-110"></i>
											返回
										</a>
										<button class="btn btn-info" type="submit">
											<i class="ace-icon fa fa-check bigger-110"></i>
											提交
										</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 页脚 -->
		<tb:footer />
		
	</div>
	
	<!-- 基础脚本 -->
	<tb:base_script />

	<!-- dataTables脚本 -->
	<tb:datatable />
	
	<!-- 文件上传js引用 -->
	<script src="component/ace/assets/js/src/elements.fileinput.js"></script>
	<!-- 树控件需要引入 -->
	<script type="text/javascript" src="component/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="component/ztree/js/jquery.ztree.excheck.min.js"></script>
	<script src="js/validator.js"></script>
	
	<script type="text/javascript">
	// 手机号码验证  
	jQuery.validator.addMethod("isMobile", function(value, element) {  
	    var length = value.length;  
	    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;  
	    return this.optional(element) || (length == 11 && mobile.test(value));  
	}, "请正确填写您的手机号码");
	
	jQuery(function($) {
		//文件上传控件初始化
		$('#operHeadpic').ace_file_input({
			style: 'well',
			btn_choose: '上传文件',
			btn_change: null,
			no_icon: 'ace-icon fa fa-cloud-upload',
			droppable: true,
			thumbnail: 'large',//small | large | fit
        	allowExt: ['jpg','png'],   //该属性只是对文件后缀的控制
			before_change:function(files, dropped) {
				//选择文件 展示之前的事件
            	$( "#headPic-error" ).addClass('hide');
            	return true; //true 保留当前文件; false 不保留文件；-1 重置文件框
			}	
		}).on('file.error.ace', function(event, info) {
			$( "#headPic-error" ).html("请上传jpg|png格式文件!");
			$( "#headPic-error" ).removeClass('hide');
    	});

		//文件上传控制初始赋值
		if($("#operHeadpicValue").val() != ''){
			$("#operHeadpic").ace_file_input('show_file_list', [{type: 'image', name: '当前头像' , path:$("#operHeadpicValue").val()}]);
		}
		
		//可管理地区滚动条设置
		$('.scrollable').each(function () {
			var $this = $(this);
			$(this).ace_scroll({
				size: $this.attr('data-size') || 150,
			});
		});
		
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
		//权限菜单树加载
		$.fn.zTree.init($("#areaTree"), setting, JSON.parse('${areaTree}'));
		
		//form表单校验
		$("#userForm").validate({
			ignore: "",
			rules : {
				operLogin : {
					required:true,
					remote: {
						url:"user/validateOperLogin",
						type:"POST",
						data:{operId:"${user.operId}",operLogin:function(){return $("#operLogin").val();}}
					}
				},
				operPassword: {
			    	required: true,
			        minlength: 6
			    },
				operName : "required",
				operPhone :  {
		            required : true,  
		            minlength : 11, 
		            isMobile : true  
		        },
				operEmail : "email",
				operArea :  {		    	
			        treeSelect: "areaTree"
			    },
			    operRole : "required"
			},
			messages : {
				operLogin :{
					required:"请输入用户名！",
					remote:"用户名已经被注册"
				},
				operPassword: {
			    	required: "请输入密码！",
			        minlength: "密码长度不能小于6位！"
			    },
				operName : "请输入用户名！",
				operPhone : {  
		            required : "请输入手机号",  
		            minlength : "确认手机不能小于11个字符",  
		            isMobile : "请正确填写您的手机号码"  
		        },
				operEmail : "请输入正确的邮箱！",
				operArea : "请选择可管理地区内容！",
				operRole : "请选择权限组！"
			},
			errorPlacement: function (error, element) { //指定错误信息位置
			      if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
			       var eid = element.attr('name'); //获取元素的name属性
			       error.appendTo(element.parent().parent()); //将错误信息添加当前元素的父结点后面
			     } else {
			       error.insertAfter(element);
			     }
			}
		});
		
		//编辑用户信息是绑定地区选择框
		areainit($("#proviace"),1);
		
		//地区选择框绑定事件
		$("#proviace,#city").on("change",function(){
			var parent=$(this).parent().next().children();
			areainit(parent,$(this).val());
		});
		
	});
	
	//获取地区信息
	function areainit(obj,parentId){
		var option = "<option selected='selected' value='0'>请选择</option>";
		
		if(typeof(parentId)!=="undefined"){
			$.get("area/list",{"parentId":parentId},function(result){
				if(result.code==200){
					var data=result.data;
					
					$(obj).empty();//清空当前下拉框内容
					$(option).appendTo(obj);//为当前下拉框增加默认选项
					$(obj).parent().nextAll().children().empty();//清空下级下拉框内容
					$(option).appendTo($(obj).parent().nextAll().children());//为下级下拉框增加默认选项
					var sel_id = 0;//被选中的上级地区ID
					
					for(var i in data){
						var areaId = data[i].areaId;
						var selected = false;
						if('${!empty user}'){
							if(areaId == '${user.operProvience==null?-1:user.operProvience}' ||
									areaId == '${user.operCity == null?-1:user.operCity}' || 
									areaId == '${user.operDistrict == null?-1:user.operDistrict}' ){
								selected = true;
								sel_id = areaId;
							} 
						}
						
						$("<option>",{
							val:areaId,
							text:data[i].areaName,
							selected:selected
						}).appendTo(obj);
					}
					
					if(sel_id > 0){
						//递归查询下一级内容
						areainit($(obj).parent().next().children(),sel_id);
					}
				}
			},'json');
		}
	}
	</script>
</body>
</html>