package com.inphase.sparrow.service.system;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inphase.sparrow.base.util.JsonUtils;
import com.inphase.sparrow.dao.system.FunctionItemDao;
import com.inphase.sparrow.entity.ZTree;
import com.inphase.sparrow.entity.system.FunctionItem;

/**      
 * @Description:功能菜单相关的服务层操作
 * @author: sunchao
 */
@Service
public class FunctionItemService {
	
	@Autowired
	private FunctionItemDao functionItemDao;

	/**
	 * @Description 获取用户的权限列表
	 * @param operId 用户ID
	 * @param type 权限类型 1：菜单 2：按钮
	 * @param parentId 父权限id 如果需要所有权限就传递-1，否则传递相应父权限ID
	 * @param isFormat 如果格式化，则返回满足ZTree格式的json字符串 ，否则返回一个List<FunctionItem>
	 * @return 
	 */
	public Object getFunctionItem(long operId, int type, long parentId, boolean isFormat) {
		List<FunctionItem> functionItemList = functionItemDao.getFunctionItem(operId, type, parentId);
		functionItemList = createFunctionMenu(functionItemList);
		if(isFormat){
			List<ZTree> zTreeList = ZTree.format(functionItemList, FunctionItem.class);
			return JsonUtils.jsonSerialization(zTreeList);
		}else{
			return functionItemList;
		}
	}
	
	
	/**
	 * @Description 根据角色id用户获取角色功能
	 * @param roleId
	 * @return
	 */
	public String getFunctionItem(long roleId) {
		List<FunctionItem> functionItemList = functionItemDao.listFunctionItem(roleId);
		functionItemList = createFunctionMenu(functionItemList);
		List<ZTree> zTreeList = ZTree.format(functionItemList, FunctionItem.class);
		return JsonUtils.jsonSerialization(zTreeList);
	}	

	/**
	 * @Description 菜单树形处理 广度优先遍历菜单(父节点处理)
	 * @param functionItemList
	 * @return
	 */
	private List<FunctionItem> createFunctionMenu(List<FunctionItem> functionItemList){
		Deque<FunctionItem> functionDeque = new ArrayDeque<FunctionItem>();
		List<FunctionItem> resultList = new ArrayList<FunctionItem>();
		List<FunctionItem> children;

		for(FunctionItem node : functionItemList){
			if(node.getParentId()==0){
				resultList.add(node);
				functionDeque.add(node);
				while(!functionDeque.isEmpty()){
					node = functionDeque.pollFirst();
					children = getChildren(functionItemList,node);
					if(children!=null && !children.isEmpty()){
						for(FunctionItem child : children){
							functionDeque.add(child);
						}
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * @Description 获取叶子节点(子节点处理)
	 * @param functionObjList
	 * @param node
	 * @return
	 */
	private List<FunctionItem> getChildren(List<FunctionItem> functionObjList,FunctionItem node){
		List<FunctionItem> childrenList = new ArrayList<FunctionItem>();
		for(FunctionItem child : functionObjList){
			if(child.getParentId()!=0 && child.getParentId()==node.getFunnId()){
				childrenList.add(child);
			}
		}
		if(!childrenList.isEmpty()){
			node.setChildren(childrenList);
		}
		return childrenList;
	}
}
