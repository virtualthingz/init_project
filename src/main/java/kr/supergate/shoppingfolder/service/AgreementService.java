package kr.supergate.shoppingfolder.service;


import kr.supergate.shoppingfolder.domain.AgreementClause;
import kr.supergate.shoppingfolder.repository.UserAgreementRepository;
import kr.supergate.shoppingfolder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgreementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAgreementRepository userAgreementRepository;

    public List<AgreementClause> getAgreedClauses(String userId) {
        return userAgreementRepository.selectAgreedClauseForUserId(userId);
    }

    @Transactional
    public List<AgreementClause> agreeClauses(String userId, List<Integer> clauses) {
        for(Integer clauseId : clauses)
            userAgreementRepository.insertAgreedClauseForUserId(userId, clauseId);

        return getAgreedClauses(userId);
    }

    @Transactional
    public List<AgreementClause> disagreeClauses(String userId, List<Integer> clauses) {
        for(Integer clauseId : clauses)
            userAgreementRepository.deleteAgreedClauseForUserId(userId, clauseId);

        return getAgreedClauses(userId);
    }

    public boolean isValidClauses(List<Integer> clauses) {
        return clauses.size() == userAgreementRepository.countClauses(clauses);
    }
}

