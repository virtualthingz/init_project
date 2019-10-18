package kr.supergate.shoppingfolder.repository;

import kr.supergate.shoppingfolder.domain.Shoppingmall;
import kr.supergate.shoppingfolder.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ShoppingmallRepository {

    List<Shoppingmall> selectShoppingmalls();
    Shoppingmall selectShoppingmall(@Param("id") String id);
    void insertShoppingmall(@Param("shoppingmall") Shoppingmall shoppingmall);
    void updateShoppingmall(@Param("shoppingmall") Shoppingmall shoppingmall);
    void deleteShoppingmall(@Param("id") String id);
}

