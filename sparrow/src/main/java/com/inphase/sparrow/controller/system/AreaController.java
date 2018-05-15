package com.inphase.sparrow.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inphase.sparrow.entity.ResponseMessage;
import com.inphase.sparrow.entity.system.Area;
import com.inphase.sparrow.service.system.AreaService;

/**      
 * @Description:地区数据的控制层
 * @author: sunchao
 */
@Controller
@RequestMapping("area")
public class AreaController {
	
	@Autowired
	private AreaService areaService;

	/**
	 * @Description 根据父id获取子地区
	 * @param parentId
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public ResponseMessage<List<Area>> listAreaByParentId(long parentId){
		return new ResponseMessage<List<Area>>(200, "查询成功", areaService.listAreaByParentId(parentId));
	}
}
