package gov.nih.ncats.clinicaltrial.autoconfigure;

// import gov.nih.ncats.clinicaltrial.SubstanceModuleService;
// import gov.nih.ncats.clinicaltrial.searchcount.repositories.SearchCountRepository;
// import gov.nih.ncats.clinicaltrial.searchcount.searcher.LegacySearchCountSearcher;
// import gov.nih.ncats.clinicaltrial.searchcount.services.SearchCountEntityService;
import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableGsrsJpaEntities
@EnableGsrsApi
@Configuration
// @Import({
        // SubstanceModuleService.class,
        // searchcount
        // SearchCountEntityService.class, LegacySearchCountSearcher.class
//        }
// )
public class GsrsClinicalTrialsAutoConfiguration {
}
