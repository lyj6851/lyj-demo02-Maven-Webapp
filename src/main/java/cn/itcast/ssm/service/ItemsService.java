package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;


//服务层接口定义

public interface ItemsService {
	//增加商品信息
	
	//删除商品信息
	public void deleleItems(Integer id) throws Exception;
	//查询商品信息集合
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
	//根据商品id查询商品信息
	public ItemsCustom findItemsById(int id) throws Exception;
	//改商品信息
	public void updateItems(Integer id,ItemsCustom itemsCustom)throws Exception;
}
