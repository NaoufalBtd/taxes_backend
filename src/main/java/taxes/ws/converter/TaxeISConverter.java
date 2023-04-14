package taxes.ws.converter;

import taxes.bean.TaxeIS;
import taxes.ws.dto.TaxeISDto;
import org.springframework.stereotype.Component;

@Component
public class TaxeISConverter extends AbstractConverter<TaxeIS,TaxeISDto> {
    public TaxeISDto toDto(TaxeIS item){
        TaxeISDto dto = null;
        if (item != null) {
            dto= new TaxeISDto();
            dto.setId(item.getId());
            dto.setAnnee(item.getAnnee());
            dto.setTrimestre(item.getTrimestre());
            dto.setCharge(item.getCharge());
            dto.setSociete(item.getSociete());
            dto.setDateEcheance(item.getDateEcheance());
            dto.setDatePaiement(item.getDatePaiement());
            dto.setChiffreAffaire(item.getChiffreAffaire());
            dto.setMontantIs(item.getMontantIs());
            dto.setResultatAvantImpot(item.getResultatAvantImpot());
            dto.setResultatApresImpot(item.getResultatApresImpot());
            dto.setTauxTaxeIS(item.getTauxTaxeIS());
        }
        return dto;
    }
    public TaxeIS toItem(TaxeISDto dto){
    TaxeIS item= null;
        if (dto != null) {
            item= new TaxeIS();
            item.setId(dto.getId());
            item.setAnnee(dto.getAnnee());
            item.setTrimestre(dto.getTrimestre());
            item.setCharge(dto.getCharge());
            item.setSociete(dto.getSociete());
            item.setChiffreAffaire(dto.getChiffreAffaire());
            item.setMontantIs(dto.getMontantIs());
            item.setResultatAvantImpot(dto.getResultatAvantImpot());
            item.setResultatApresImpot(dto.getResultatApresImpot());
            item.setTauxTaxeIS(dto.getTauxTaxeIS());
            item.setDateEcheance(dto.getDateEcheance());
            item.setDatePaiement(dto.getDatePaiement());
        }
        return item;
    }


}
