
package gov.nih.ncats.clinicaltrial.inheritance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gsrs.BackupEntityProcessorListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


// Seems like this MappedSuperclass annotation prevents "table per class" from working.
// Therefore, I am using this instead of the original AbstractGsrsEntity.
// the only change is the commenting out of @MappedSuperclass
// @MappedSuperclass
@SuperBuilder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners({AuditingEntityListener.class, GsrsEntityProcessorListener.class, IndexerEntityListener.class, BackupEntityProcessorListener.class})
public abstract class AbstractGsrsEntityAlt {
    public AbstractGsrsEntityAlt() {
    }
}
