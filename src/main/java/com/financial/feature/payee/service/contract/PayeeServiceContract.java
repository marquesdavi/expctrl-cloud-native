package com.financial.feature.payee.service.contract;

import com.financial.feature.payee.dto.PayeeDTO;
import com.financial.feature.payee.entity.Payee;
import com.financial.feature.tag.dto.TagDTO;
import com.financial.feature.tag.entity.Tag;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface PayeeServiceContract {
    List<PayeeDTO> list();
    PayeeDTO get(Long id);
    Response create(PayeeDTO dto);
    PayeeDTO update(Long id, PayeeDTO dto);
    void delete(Long id);
    Payee findByID(Long id);
}
