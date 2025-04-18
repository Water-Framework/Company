
package it.water.company.service.rest.spring;

import it.water.company.model.Company;
import it.water.company.service.rest.CompanyRestControllerImpl;
import it.water.core.api.model.PaginableResult;
import it.water.core.api.repository.query.Query;
import it.water.core.api.repository.query.QueryOrder;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Generated by Water Generator
 * Rest Api Class for Company entity. It just overrides method invoking super in order to let spring find web methods.
 */
@RestController
public class CompanySpringRestControllerImpl extends CompanyRestControllerImpl implements CompanySpringRestApi {

    @Override
    @SuppressWarnings("java:S1185") //disabling sonar because spring needs to override this method
    public Company save(Company entity) {
        return super.save(entity);
    }

    @Override
    @SuppressWarnings("java:S1185") //disabling sonar because spring needs to override this method
    public Company update(Company entity) {
        return super.update(entity);
    }

    @Override
    @SuppressWarnings("java:S1185") //disabling sonar because spring needs to override this method
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    @SuppressWarnings("java:S1185") //disabling sonar because spring needs to override this method
    public Company find(long id) {
        return super.find(id);
    }

    @Override
    @SuppressWarnings("java:S1185") //disabling sonar because spring needs to override this method
    public PaginableResult<Company> findAll(Integer delta, Integer page, Query filter, QueryOrder order) {
        return super.findAll(delta, page, filter, order);
    }

    @Override
    @SuppressWarnings("java:S1185") //disabling sonar because spring needs to override this method
    public PaginableResult<Company> findAll() {
        return super.findAll();
    }
}
