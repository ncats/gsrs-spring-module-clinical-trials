package gov.hhs.gsrs.clinicaltrial.europe.indexers;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEuropeDrug;
import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEuropeProduct;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class ClinicalTrialEuropeEntityLinkIndexValueMaker implements IndexValueMaker<ClinicalTrialEurope> {
    @Override
    public Class<ClinicalTrialEurope> getIndexedEntityClass() {
        return ClinicalTrialEurope.class;
    }

    @Override
    public void createIndexableValues(ClinicalTrialEurope clinicalTrialEurope, Consumer<IndexableValue> consumer) {
        List<ClinicalTrialEuropeProduct> products = clinicalTrialEurope.getClinicalTrialEuropeProductList();
        if (products != null) {
            for (ClinicalTrialEuropeProduct product : products) {
                List<ClinicalTrialEuropeDrug> substances = product.getClinicalTrialEuropeDrugList();
                if (substances != null) {
                    for (ClinicalTrialEuropeDrug substance : substances) {
                        if (substance.getSubstanceKey() != null) {
                            consumer.accept(IndexableValue.simpleFacetStringValue("entity_link_substances", substance.getSubstanceKey()));
                        }
                    }
                }
            }
        }
    }
}