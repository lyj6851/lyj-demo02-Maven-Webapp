package cn.itcast.ssm.po;

import java.util.List;

/**
 * 
 * @author Administrator
 * 商品的包装类
 */
public class ItemsQueryVo {
	//单数商品信息
	private ItemsCustom itemsCustom;
	//商品信息集合属性(用于存入一组商品)
	private List<ItemsCustom> itemsList;
	
	public ItemsCustom getItemsCustom() {
		return itemsCustom;
	}
	public void setItemsCustom(ItemsCustom itemsCustom) {
		this.itemsCustom = itemsCustom;
	}
	public List<ItemsCustom> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<ItemsCustom> itemsList) {
		this.itemsList = itemsList;
	}
	
	
}
