package gov.hhs.gsrs.clinicaltrial;


import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
// import gsrs.startertests.jupiter.AbstractGsrsJpaEntityJunit5Test;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gov.hhs.gsrs.clinicaltrial.us.services.ClinicalTrialUSEntityService;
import gov.hhs.gsrs.clinicaltrial.us.validators.StringCleanerValidator;
import gsrs.controller.GsrsControllerConfiguration;
import gsrs.springUtils.AutowireHelper;
import gsrs.startertests.GsrsEntityTestConfiguration;
import gsrs.startertests.GsrsJpaTest;
import gsrs.startertests.jupiter.AbstractGsrsJpaEntityJunit5Test;
import ix.core.validator.ValidationMessage;
import ix.core.validator.ValidationResponse;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@GsrsJpaTest(classes = {GsrsEntityTestConfiguration.class})
@Import({ClinicalTrialUS.class})

public class TestTraverse extends AbstractGsrsJpaEntityJunit5Test {



    @Test
    public void traverseTest() {
        ClinicalTrialUS ct = new ClinicalTrialUS();
        StringCleanerValidator validator = new StringCleanerValidator();
        validator= AutowireHelper.getInstance().autowireAndProxy(validator);
        ValidationResponse<ClinicalTrialUS> response = validator.validate(ct, null);
  //      Assertions.assertEquals(1, response.getValidationMessages().stream()
//        .filter(m -> m.getMessageType().equals(ValidationMessage.MESSAGE_TYPE.ERROR)).count());


    }
}
