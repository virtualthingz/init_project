package kr.supergate.shoppingfolder.service;


import kr.supergate.shoppingfolder.domain.sign.SignType;
import kr.supergate.shoppingfolder.domain.sign.UserSignHistory;
import kr.supergate.shoppingfolder.repository.UserSignHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserSignHistoryService {

    @Autowired
    private UserSignHistoryRepository userSignHistoryRepository;

    @Transactional
    public boolean addSignHistory(SignType signType, String userId){
        if(userId == null)
            return false;
        return userSignHistoryRepository.insertSignHistory(signType, userId);
    }

    public List<UserSignHistory> selectSignHistoryCountByUserIdOrderByIdDescLimit30(String userId) {
        return userSignHistoryRepository.selectSignHistoryCountByUserIdOrderByIdDescLimit30(userId);
    }

    public long getSignHistoryCountBySignTypeAndUserId(SignType signType, String userId){
        return userSignHistoryRepository.selectSignHistoryCountBySignTypeAndUserId(signType, userId);
    }
}
