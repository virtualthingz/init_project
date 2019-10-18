package kr.supergate.shoppingfolder.repository;

import kr.supergate.shoppingfolder.domain.AgreementClause;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserAgreementRepository {
    List<AgreementClause> selectAgreedClauseForUserId(String userId);
    Integer countClauses(List<Integer> clauses);

    void insertAgreedClauseForUserId(@Param("userId") String userId, @Param("clauseId") Integer clauseId);
    void deleteAgreedClauseForUserId(@Param("userId") String userId, @Param("clauseId") Integer clauseId);
}
