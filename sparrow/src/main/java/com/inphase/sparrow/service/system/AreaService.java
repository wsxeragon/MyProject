package com.inphase.sparrow.service.system;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inphase.sparrow.base.util.JsonUtils;
import com.inphase.sparrow.dao.system.AreaDao;
import com.inphase.sparrow.entity.ZTree;
import com.inphase.sparrow.entity.system.Area;

/**      
 * @Description:地区操作的服务层
 * @author: sunchao
 */
@Service
public class AreaService {
	
	@Autowired 
	private AreaDao areaDao;

	/**
	 * @Description 根据用户id获取用户的可管理地区并格式化为ZTree树形结构
	 * @param operId
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NumberFormatException 
	 */
	public String listAllArea(long operId) {
		List<Area> areaList = areaDao.listAllArea(operId);
		areaList = createAreaList(areaList);
		List<ZTree> zTreeList = ZTree.format(areaList, Area.class);
		return JsonUtils.jsonSerialization(zTreeList);
	}
	
	/**
	 * @Description 根据父地区id获取子地区
	 * @param parentId
	 * @return
	 */
	public List<Area> listAreaByParentId(long parentId){
		return areaDao.listAreaByParentId(parentId);
	}
	
	/**
	 * @Description 根据用户id互殴去用户的可管理地区
	 * @param operId
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NumberFormatException 
	 */
	public String listAreaByOperId(long operId) {
		List<Area> areaList = areaDao.listAreaByOperId(operId);
		areaList = createAreaList(areaList);
		List<ZTree> zTreeList = ZTree.format(areaList, Area.class);
		return JsonUtils.jsonSerialization(zTreeList);
	}

	/**
	 * @Description 地区树形处理 广度优先遍历菜单(父节点处理)
	 * @param areaList
	 * @return
	 */
	private List<Area> createAreaList(List<Area> areaList){
		Deque<Area> areaDeque = new ArrayDeque<Area>();
		List<Area> resultList = new ArrayList<Area>();
		List<Area> children;
		for(Area node : areaList){
			if(node.getAreaFatherId()==0){
				resultList.add(node);
				areaDeque.add(node);
				while(!areaDeque.isEmpty()){
					node = areaDeque.pollFirst();
					children = getChildren(areaList, node);
					if(children!=null && !children.isEmpty()){
						for(Area child : children){
							areaDeque.add(child);
						}
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * @Description 获取叶子节点(子节点处理)
	 * @param areaList
	 * @param node
	 * @return
	 */
	private List<Area> getChildren(List<Area> areaList, Area node){
		List<Area> childrenList = new ArrayList<Area>();
		for(Area child : areaList){
			if(child.getAreaFatherId()!=0 && child.getAreaFatherId()==node.getAreaId()){
				childrenList.add(child);
			}
		}
		if(!childrenList.isEmpty()){
			node.setChildren(childrenList);
		}
		return childrenList;
	}
}
