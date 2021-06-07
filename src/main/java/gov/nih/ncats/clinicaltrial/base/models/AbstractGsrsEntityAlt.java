
package gov.nih.ncats.clinicaltrial.base.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gsrs.BackupEntityProcessorListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;


// Seems like this MappedSuperclass annotation prevents "table per class" from working.
// Therefore, I am using this instead of the original AbstractGsrsEntity.
// the only change is the commenting out of @MappedSuperclass
// @MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners({AuditingEntityListener.class, GsrsEntityProcessorListener.class, IndexerEntityListener.class, BackupEntityProcessorListener.class})
public abstract class AbstractGsrsEntityAlt {
    public AbstractGsrsEntityAlt() {
    }
}
