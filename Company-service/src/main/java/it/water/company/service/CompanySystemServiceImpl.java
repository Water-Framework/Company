package it.water.company.service;

import it.water.company.api.CompanyRepository;
import it.water.company.api.CompanySystemApi;
import it.water.company.model.Company;
import it.water.core.api.registry.filter.ComponentFilterBuilder;
import it.water.core.interceptors.annotations.FrameworkComponent;
import it.water.core.interceptors.annotations.Inject;
import it.water.repository.service.BaseEntitySystemServiceImpl;
import lombok.Getter;
import lombok.Setter;


/**
 * @Generated by Water Generator
 * System Service Api Class for Company entity.
 */
@FrameworkComponent
public class CompanySystemServiceImpl extends BaseEntitySystemServiceImpl<Company> implements CompanySystemApi {
    @Inject
    @Getter
    @Setter
    private CompanyRepository repository;

    @Inject
    @Setter
    private ComponentFilterBuilder componentFilterBuilder;

    public CompanySystemServiceImpl() {
        super(Company.class);
    }

}