package de.hbrs.se2.womm.views.layouts;

import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.AbstractDTO;

public abstract class AbstractViewDTObyNutzerID
        <ExtendsAbstractController, ExtendsAbstractDTO extends AbstractDTO>
        extends AViewWomm {

    SecurityService securityService;
    protected long selectNutzerIDfromLoggedInForDB(){
        return securityService.getLoggedInNutzerID();
    }
}
