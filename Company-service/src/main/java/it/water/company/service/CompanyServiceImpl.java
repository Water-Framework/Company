package it.water.company.service;

import it.water.company.api.CompanyApi;
import it.water.company.api.CompanySystemApi;
import it.water.company.model.Company;
import it.water.core.api.registry.ComponentRegistry;
import it.water.core.interceptors.annotations.FrameworkComponent;
import it.water.core.interceptors.annotations.Inject;
import it.water.repository.service.BaseEntityServiceImpl;
import lombok.Getter;
import lombok.Setter;


/**
 * @Generated by Water Generator
 * Service Api Class for Company entity.
 */
@FrameworkComponent
public class CompanyServiceImpl extends BaseEntityServiceImpl<Company> implements CompanyApi {

    @Inject
    @Getter
    @Setter
    private CompanySystemApi systemService;

    @Inject
    @Getter
    @Setter
    private ComponentRegistry componentRegistry;

    public CompanyServiceImpl() {

        super(Company.class);
    }

}
