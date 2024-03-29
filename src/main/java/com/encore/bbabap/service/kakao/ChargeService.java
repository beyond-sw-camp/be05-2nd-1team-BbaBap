package com.encore.bbabap.service.kakao;

import com.encore.bbabap.domain.kakao.Charge;
import com.encore.bbabap.repository.kakao.ChargeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {

    private final ChargeRepository chargeRepository;

    @Autowired
    public ChargeService(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    public List<Charge> getAllCharges() {
        return chargeRepository.findAll();
    }

    public Charge getChargeById(String id) {
        return chargeRepository.findById(id).orElse(null);
    }

    public void saveCharge(Charge charge) {
        chargeRepository.save(charge);
    }

    public void updateCharge(Charge charge) {
        chargeRepository.save(charge);
    }

    public void deleteCharge(String id) {
        chargeRepository.deleteById(id);
    }

    public List<Charge> searchCharges(String searchType, String searchKeyword) {
        switch (searchType) {
            case "chargeName":
                return chargeRepository.findByChargeNameContaining(searchKeyword);
            case "chargeAddr":
                return chargeRepository.findByChargeAddrContaining(searchKeyword);
            case "chargeType":
                return chargeRepository.findByChargeTypeContaining(searchKeyword);
            case "busiName":
                return chargeRepository.findByBusiNameContaining(searchKeyword);
            default:
                return new ArrayList<>();
        }


    }



}
