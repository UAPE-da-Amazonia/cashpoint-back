package io.zhc1.uape.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.BusinessUnitRepository;

@Service
@RequiredArgsConstructor
public class BusinessUnitService {
    private final BusinessUnitRepository businessUnitRepository;

    /**
     * Get all business units.
     *
     * @return Returns all business units
     */
    public List<BusinessUnit> getAllBusinessUnits() {
        // Note: If there are too many business units, recommend apply cursor based pagination.
        return businessUnitRepository.findAll();
    }

    public Optional<BusinessUnit> findById(Long id) {
        return businessUnitRepository.findById(id);
    }

    public BusinessUnit save(BusinessUnit businessUnit) {
        return businessUnitRepository.save(businessUnit);
    }

    public void deletar(Long id) {
        businessUnitRepository.deleteById(id);
    }

    public BusinessUnit editar(Long id, String name, String description) {
        BusinessUnit businessUnit = businessUnitRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));
        businessUnit.setName(name);
        businessUnit.setDescription(description);
        return businessUnitRepository.save(businessUnit);
    }
}
