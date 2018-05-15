<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>

<form id="roleForm" class="form-horizontal" method="post">
	<!-- hidden -->
	<input type="hidden" name="roleId"
		value="${empty role.roleId?0:role.roleId}">

	<div class="row">
		<div class="col-sm-6">
			<div class="widget-box">
				<div class="widget-header">
					<h4 class="widget-title">基本信息</h4>
				</div>

				<div class="widget-body">
						<div class="widget-main">
							<label >权限组名</label>
							<div class="row">
								<div class="col-sm-12">
									<input type="text" name="roleName" value="${role.roleName}"
										class="col-sm-12"/>
								</div>
							</div>
							<div class="space space-8"></div>
							<label >权限组说明</label>
							<div class="row">
								<div class="col-sm-12">
									<textarea class="form-control" rows="5" name="roleDescription">${role.roleDescription}</textarea>
								</div>
							</div>						
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-sm-6">
			<div class="widget-box">
				<div class="widget-header">
					<h4 class="widget-title">功能权限</h4>
				</div>

				<div class="widget-body">
					<div class="widget-main no-padding">
						<div class="widget-body">
							<ul id="functionTree" class="ztree"></ul>
							<input type="hidden" id="functionIds" name="functionIds"/>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
</form>


<!-- 树控件需要引入 -->
<script type="text/javascript" src="component/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="component/ztree/js/jquery.ztree.excheck.min.js"></script>
<script src="js/validator.js"></script>

<script type="text/javascript">
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
		
	jQuery(function($) {
		//权限菜单树加载
		$.fn.zTree.init($("#functionTree"), setting, JSON.parse('${functionItem}'));
	});
	
	//form表单校验
	function roleFormValidate(){
		return $("#roleForm").validate({
	        ignore: "",
			rules : {
				roleName : "required",
				roleDescription: {
			        maxlength: 60
			    },
			    functionIds :  {		    	
			        treeSelect: "functionTree"
			    },
			},
			messages : {
				roleName : "请输入权限组名！",
				roleDescription: {
			    	required: "请输入密码！",
			        maxlength: "权限组说明不能大于60位！"
			    },
			    functionIds :  {		    	
			        treeSelect: "请选择功能权限!"
			    }
			}	
	    }).form(); 
	}		
</script>
