package com.inphase.sparrow.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inphase.sparrow.base.handler.BusinessException;
import com.inphase.sparrow.dao.system.RoleDaoWithMyBatis;
import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.Role;

/**      
 * @Description:角色增删改查相关操作
 * @author: sunchao
 */
@Service
public class RoleService {

	@Autowired
	private RoleDaoWithMyBatis roleDaoWithMyBatis;

	//jdbcTemplate 使用
	/*@Autowired
	private RoleDao roleDao;*/
	
	/**
	 * @Description 添加角色
	 * @param role
	 */
	public void saveRoleTran(Role role){
		roleDaoWithMyBatis.saveRole(role);
		saveRoleFunctionItemMap(role.getRoleId(),role.getFunctionIds());
	}
	
	/**
	 * @Description 根据角色ID获取角色信息
	 * @param roleId
	 * @return
	 */
	public Role getRole(long roleId){
		return roleDaoWithMyBatis.getRole(roleId);
	}
	
	/**
	 * @Description 获取角色列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public TableMessage<List<Role>> listRole(TableParam tableParam){
		List<Role> role = roleDaoWithMyBatis.listRolePage(tableParam);
		return new TableMessage<List<Role>>(tableParam.getsTotalRecord(), tableParam.getsEcho(), role);
	}
	
	/**
	 * @Description 获取所有角色信息
	 * @param operId 
	 * @return
	 */
	public List<Role> listAllRole(long operId){
		return roleDaoWithMyBatis.listAllRole(operId);
	}
	
	/**
	 * @Description 更新角色信息
	 * @param role
	 */
	public void updateRole(Role role){
		roleDaoWithMyBatis.updateRole(role);
		saveRoleFunctionItemMap(role.getRoleId(),role.getFunctionIds());
	}
	
	/**
	 * @Description 删除角色
	 * @param roleId
	 */
	public void removeRoleTran(long roleId){
		int count = roleDaoWithMyBatis.getExistsOperator(roleId);
		if(count==0){
			roleDaoWithMyBatis.removeRole(roleId);
			roleDaoWithMyBatis.removeRoleFunctionItemMap(roleId);
		}else{
			throw new BusinessException(200001,"请先删除该角色下的用户。");
		}
		
	}
	
	/**
	 * @Description 添加角色和功能关联数据
	 * @param roleId
	 * @param functionItemIds
	 */
	public void saveRoleFunctionItemMap(long roleId, String functionIds){
		if(!StringUtils.isEmpty(functionIds)){
			roleDaoWithMyBatis.removeRoleFunctionItemMap(roleId);
			String[] functionIdArray = functionIds.split(",");
			List<Map<String,Long>> paramList = new ArrayList<Map<String,Long>>();
			Map<String,Long> tempMap;
			for(int i = 0; i < functionIdArray.length; i++){
				tempMap = new HashMap<String,Long>();
				tempMap.put("roleId", roleId);
				tempMap.put("functionId", Long.valueOf(functionIdArray[i]));
				paramList.add(tempMap);
			}
			roleDaoWithMyBatis.saveRoleFunctionMap(paramList);
		}
	}
}
