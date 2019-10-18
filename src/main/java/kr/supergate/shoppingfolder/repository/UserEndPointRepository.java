package kr.supergate.shoppingfolder.repository;


import kr.supergate.shoppingfolder.domain.UserEndPoint;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserEndPointRepository {

    UserEndPoint selectUserEndPoint(String userId);
    UserEndPoint selectUserEndPointById(String id);
    void updateUserEndPoint(UserEndPoint userEndPoint);
    void insertUserEndPoint(UserEndPoint userEndPoint);
    void deleteUserEndPoint(String userId);
    void updateUserEndPointNullByUserId(String userId);
}
