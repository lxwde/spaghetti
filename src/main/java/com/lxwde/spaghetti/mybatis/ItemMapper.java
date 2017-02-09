package com.lxwde.spaghetti.mybatis;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemMapper {
    public List<Item> getAllItems();
    public Item getItemById(Integer id);
    public void insertItem(Item item);
    public void updateItem(Item item);
    public void deleteItem(Integer itemId);

    @Select("select * from item where id=#{id}")
    public Item getItemByIdEx(Integer id);
}
