package io.zhc1.realworld.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.api.request.BusinessUnitRequest;
import io.zhc1.realworld.api.response.BusinessUnitResponse;
import io.zhc1.realworld.config.AuthToken;
import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.service.BusinessUnitService;

@RestController
@RequestMapping("/api/businessunit")
@RequiredArgsConstructor
class BusinessUnitController {
    private final BusinessUnitService businessUnitService;

    @GetMapping
    BusinessUnitResponse getAllBusinessUnitResponse(AuthToken authToken) {
        // Se não há token, retorna todas as unidades (endpoint público)
        if (authToken == null) {
            return new BusinessUnitResponse(businessUnitService.getAllBusinessUnits());
        }

        // ADMIN pode ver todas as unidades, USER só vê a sua
        if (authToken.isAdmin()) {
            return new BusinessUnitResponse(businessUnitService.getAllBusinessUnits());
        } else {
            // USER só vê sua própria unidade
            BusinessUnit userBusinessUnit = businessUnitService
                    .findById(authToken.businessUnitId())
                    .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));
            return new BusinessUnitResponse(userBusinessUnit);
        }
    }

    @GetMapping("/{id}")
    BusinessUnitResponse getBusinessUnitById(AuthToken authToken, @PathVariable Long id) {
        // ADMIN pode ver qualquer unidade, USER só pode ver a sua
        if (!authToken.isAdmin() && !id.equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only access your own business unit.");
        }

        BusinessUnit businessUnit =
                businessUnitService.findById(id).orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        return new BusinessUnitResponse(businessUnit);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BusinessUnitResponse createBusinessUnit(AuthToken authToken, @RequestBody BusinessUnitRequest request) {
        // Apenas ADMIN pode criar unidades de negócio
        if (!authToken.isAdmin()) {
            throw new SecurityException("Access denied. Only ADMIN users can create business units.");
        }

        BusinessUnit businessUnit = new BusinessUnit(request.name(), request.description());
        BusinessUnit savedBusinessUnit = businessUnitService.save(businessUnit);
        return new BusinessUnitResponse(savedBusinessUnit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessUnitResponse> editarBusinessUnit(
            AuthToken authToken, @PathVariable Long id, @RequestBody BusinessUnitRequest request) {

        // ADMIN pode editar qualquer unidade, USER só pode editar a sua
        if (!authToken.isAdmin() && !id.equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only edit your own business unit.");
        }

        BusinessUnit businessUnit = businessUnitService.editar(id, request.name(), request.description());
        return ResponseEntity.ok(new BusinessUnitResponse(businessUnit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBusinessUnit(AuthToken authToken, @PathVariable Long id) {
        // Apenas ADMIN pode deletar unidades de negócio
        if (!authToken.isAdmin()) {
            throw new SecurityException("Access denied. Only ADMIN users can delete business units.");
        }

        businessUnitService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
