package io.zhc1.realworld.api.response;

import java.util.Collection;
import java.util.List;

import io.zhc1.realworld.model.BusinessUnit;

public record BusinessUnitResponse(List<BusinessUnitData> businessUnits) {

    public record BusinessUnitData(Long id, String name, String description, String createdAt, String updatedAt) {
        public BusinessUnitData(BusinessUnit businessUnit) {
            this(
                    businessUnit.getId(),
                    businessUnit.getName(),
                    businessUnit.getDescription(),
                    businessUnit.getCreatedAt() != null
                            ? businessUnit.getCreatedAt().toString()
                            : null,
                    businessUnit.getUpdatedAt() != null
                            ? businessUnit.getUpdatedAt().toString()
                            : null);
        }
    }

    public BusinessUnitResponse(Collection<BusinessUnit> businessUnits) {
        this(businessUnits.stream().map(BusinessUnitData::new).toList());
    }

    public BusinessUnitResponse(BusinessUnit businessUnit) {
        this(List.of(new BusinessUnitData(businessUnit)));
    }
}
