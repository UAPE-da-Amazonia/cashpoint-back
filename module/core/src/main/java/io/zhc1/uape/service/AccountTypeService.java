package io.zhc1.uape.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.model.AccountType;
import io.zhc1.uape.model.AccountTypeRepository;
import io.zhc1.uape.model.BusinessUnit;

@Service
@RequiredArgsConstructor
public class AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    private final BusinessUnitService businessUnitService;

    /**
     * Get account type by id with access control.
     *
     * @param id account type id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns account type
     */
    public AccountType getAccountType(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        AccountType accountType = accountTypeRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("account type not found."));

        // Admin can access any account type, USER only from their business unit
        if (!isAdmin && !accountType.getBusinessUnit().getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access account types from your business unit.");
        }

        return accountType;
    }

    /**
     * Get account type by id.
     *
     * @param id account type id
     * @return Returns account type
     */
    public AccountType getAccountType(Integer id) {
        return accountTypeRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("account type not found."));
    }

    /**
     * Get all account types.
     *
     * @return Returns list of account types
     */
    public List<AccountType> getAllAccountTypes() {
        return accountTypeRepository.findAll();
    }

    /**
     * Get all account types by business unit with access control.
     *
     * @param businessUnitId business unit id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns list of account types
     */
    public List<AccountType> getAccountTypesByBusinessUnit(
            Long businessUnitId, Long userBusinessUnitId, boolean isAdmin) {
        // Admin can access any business unit, USER only their own
        if (!isAdmin && !businessUnitId.equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access account types from your business unit.");
        }

        return accountTypeRepository.findByBusinessUnitId(businessUnitId);
    }

    /**
     * Get all account types by business unit.
     *
     * @param businessUnitId business unit id
     * @return Returns list of account types
     */
    public List<AccountType> getAccountTypesByBusinessUnit(Long businessUnitId) {
        return accountTypeRepository.findByBusinessUnitId(businessUnitId);
    }

    /**
     * Create a new account type with access control.
     *
     * @param name account type name
     * @param businessUnitId business unit id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the created account type
     */
    public AccountType createAccountType(String name, Long businessUnitId, Long userBusinessUnitId, boolean isAdmin) {
        // Admin can create in any business unit, USER only in their own
        if (!isAdmin && !businessUnitId.equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only create account types in your business unit.");
        }

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("business unit not found."));

        var accountType = new AccountType(name, businessUnit);
        return accountTypeRepository.save(accountType);
    }

    /**
     * Create a new account type.
     *
     * @param name account type name
     * @param businessUnitId business unit id
     * @return Returns the created account type
     */
    public AccountType createAccountType(String name, Long businessUnitId) {
        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("business unit not found."));

        var accountType = new AccountType(name, businessUnit);
        return accountTypeRepository.save(accountType);
    }

    /**
     * Update an account type with access control.
     *
     * @param id account type id
     * @param name account type name
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the updated account type
     */
    public AccountType updateAccountType(Integer id, String name, Long userBusinessUnitId, boolean isAdmin) {
        AccountType accountType = getAccountType(id, userBusinessUnitId, isAdmin);
        accountType.setName(name);
        return accountTypeRepository.save(accountType);
    }

    /**
     * Update an account type.
     *
     * @param id account type id
     * @param name account type name
     * @return Returns the updated account type
     */
    public AccountType updateAccountType(Integer id, String name) {
        AccountType accountType = getAccountType(id);
        accountType.setName(name);
        return accountTypeRepository.save(accountType);
    }

    /**
     * Delete an account type with access control.
     *
     * @param id account type id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     */
    public void deleteAccountType(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        AccountType accountType = getAccountType(id, userBusinessUnitId, isAdmin);
        accountTypeRepository.delete(accountType);
    }

    /**
     * Delete an account type.
     *
     * @param id account type id
     */
    public void deleteAccountType(Integer id) {
        AccountType accountType = getAccountType(id);
        accountTypeRepository.delete(accountType);
    }
}
