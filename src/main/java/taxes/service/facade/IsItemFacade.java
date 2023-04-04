package taxes.service.facade;

import taxes.bean.ISItem;
import taxes.bean.Societe;

public interface IsItemFacade {
    int save(ISItem isItem, Societe societe);
}
