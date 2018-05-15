<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>

<div class="profile-user-info profile-user-info-striped">
	<div class="profile-info-row">
		<div class="profile-info-name"> 登录名 </div>

		<div class="profile-info-value">
			<span class="editable" id="operLogin">${user.operLogin}</span>
		</div>
	</div>

	<div class="profile-info-row">
		<div class="profile-info-name"> 用户名 </div>

		<div class="profile-info-value">
			<span class="editable" id="operName">${user.operName}</span>
		</div>
	</div>

	<div class="profile-info-row">
		<div class="profile-info-name"> 联系电话 </div>

		<div class="profile-info-value">
			<span class="editable" id="operPhone">${user.operPhone}</span>
		</div>
	</div>

	<div class="profile-info-row">
		<div class="profile-info-name"> 头像 </div>

		<div class="profile-info-value">
			<img width="150" height="150" id="avatar" class="editable img-responsive editable-click editable-empty" alt="个人头像" src="${user.operHeadpic}"></img>
		</div>
	</div>

	<div class="profile-info-row">
		<div class="profile-info-name"> 性别 </div>

		<div class="profile-info-value">
			<span class="editable" id="operSex">
			<c:choose>
				<c:when test="${user.operSex==0 }">
			       	女
			    </c:when>
				<c:when test="${user.operSex==1 }">
			       	男
			    </c:when>
			    <c:otherwise>
			    	${user.operSex}
			    </c:otherwise>
			</c:choose>
			</span>
		</div>
	</div>

	<div class="profile-info-row">
		<div class="profile-info-name"> QQ </div>

		<div class="profile-info-value">
			<span class="editable" id="operQQ">${user.operQQ}</span>
		</div>
	</div>
	
	<div class="profile-info-row">
		<div class="profile-info-name"> 邮箱 </div>

		<div class="profile-info-value">
			<span class="editable" id="operEmail">${user.operEmail}</span>
		</div>
	</div>
	
	<div class="profile-info-row">
		<div class="profile-info-name">所属地区</div>
		<div id="operArea" class="profile-info-value">
			<i id="operProvience"></i>
			<i id="operCity"></i>
			<i id="operDistrict"></i>
		</div>
	</div>
	
	<div class="profile-info-row">
		<div class="profile-info-name"> 联系地址  </div>

		<div class="profile-info-value">
			<span class="editable" id="operEmail">${user.operAddress}</span>
		</div>
	</div>
	
	<div class="profile-info-row">
		<div class="profile-info-name"> 状态 </div>

		<div class="profile-info-value">
			<span class="editable" id="operStatus">
			<c:choose>
				<c:when test="${user.operStatus==0 }">
			       	禁用
			    </c:when>
				<c:when test="${user.operStatus==1 }">
			       	正常
			    </c:when>
			    <c:otherwise>
			    	${user.operStatus}
			    </c:otherwise>
			</c:choose>
			</span>
		</div>
	</div>
	
	<div class="profile-info-row">
		<div class="profile-info-name"> 注册时间  </div>

		<div class="profile-info-value">
			<span class="editable" id="operCreatedate"><fmt:formatDate value="${user.operCreatedate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name"> 功能权限  </div>
		<div class="profile-info-value">
			<ul id="functionTree" class="ztree"></ul>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name"> 可管理地区  </div>
		<div class="profile-info-value scrollable" >
			<ul id="areaTree" class="ztree"></ul>
		</div>
	</div>
</div>

<!-- 树控件需要引入 -->
<script type="text/javascript" src="component/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="component/ztree/js/jquery.ztree.excheck.min.js"></script>
<!-- 树型菜单样式 -->
<link rel="stylesheet" href="component/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script>
jQuery(function($){
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	
	//地区菜单树加载
	$.fn.zTree.init($("#areaTree"), setting, JSON.parse('${areaTree}'));
	
	//权限菜单树加载
	$.fn.zTree.init($("#functionTree"), setting, JSON.parse('${functionTree}'));
	
	//编辑用户信息是绑定地区选择框
	if('${!empty user}'){
		areainit($("#operArea"),1);
		areainit($("#operArea"),'${empty user.operProvience?0:user.operProvience}');
		areainit($("#operArea"),'${empty user.operCity?0:user.operCity}');
	}
	
	//可管理地区滚动条设置
	$('.scrollable').each(function () {
		var $this = $(this);
		$(this).ace_scroll({
			size: $this.attr('data-size') || 150,
		});
	});
});

//获取地区信息
function areainit(obj,parentId){
	if(typeof(parentId)!=="undefined"){
		$.get("area/list",{"parentId":parentId},function(result){
			if(result.code==200){
				var data=result.data;
				for(var i in data){
					var areaId = data[i].areaId;
					if('${!empty user}'){
						if(areaId == '${user.operProvience==null?-1:user.operProvience}'){
							$("#operProvience").text(data[i].areaName);
						}
						if(areaId == '${user.operCity == null?-1:user.operCity}'){
							$("#operCity").text(data[i].areaName);
						}
						if(areaId == '${user.operDistrict == null?-1:user.operDistrict}'){
							$("#operDistrict").text(data[i].areaName);
						}
					}
				}
			}
		},'json');
	}
}
</script>