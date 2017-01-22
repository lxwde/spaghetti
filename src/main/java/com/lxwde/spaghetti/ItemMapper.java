package com.lxwde.spaghetti;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    public List<Item> getAllItems();
    public Item getItemById(Integer id);
    public void insertItem(Item item);
    public void updateItem(Item item);
    public void deleteItem(Integer itemId);
}
