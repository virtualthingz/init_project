package kr.supergate.shoppingfolder.repository;

import kr.supergate.shoppingfolder.domain.sign.SignType;
import kr.supergate.shoppingfolder.domain.sign.UserSignHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserSignHistoryRepository {
    boolean insertSignHistory(@Param("type") SignType type, @Param("userId") String userId);
    List<UserSignHistory> selectSignHistoryCountByUserIdOrderByIdDescLimit30(@Param("userId") String userId);
    long selectSignHistoryCountBySignTypeAndUserId(@Param("type") SignType type, @Param("userId") String userId);
}
