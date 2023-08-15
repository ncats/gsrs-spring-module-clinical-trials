package gov.hhs.gsrs.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.hhs.gsrs.clinicaltrial.base.models.ClinicalTrialBase;
import gsrs.security.GsrsSecurityUtils;
import ix.core.models.*;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;
@Data
@Entity
@AllArgsConstructor
@Backup
@IndexableRoot
@SuperBuilder
@Table(name="ctrial_eu")
@JsonInclude(JsonInclude.Include.ALWAYS)
@EqualsAndHashCode(exclude="clinicalTrialEuropeProduct")
@Slf4j
public class ClinicalTrialEurope extends ClinicalTrialBase {

        public ClinicalTrialEurope() {
             this.setKind("EUROPE");
        }
        // see base class for basic fields

        @Indexable(facet=true, name="Sponsor", sortable = true)
        @Column(name="sponsor_name", length=2000)
        public String sponsorName;

        @Indexable(facet=true, name="Trial Status")
        @Column(name="trial_status", length=2000)
        public String trialStatus;

        @Column(name="date_first_entered_db")
        public Date dateFirstEnteredDb;

        @Column(name="trial_results", length=2000)
        public String trialResults;

        @Indexable(facet=true, name="National Competent Authority")
        @Column(name="national_competent_auth", length=2000)
        public String nationalCompetentAuthority;

        @Column(name="competent_auth_decision", length=2000)
        public String competentAuthorityDecision;

        @Column(name="date_comp_auth_decision")
        public Date competentAuthorityDecisionDate;

        @Column(name="ethics_com_opinion_app", length=2000)
        public String ethicsComOpinionApp;

        @Column(name="ethics_com_opinion_reason", length=2000)
        public String ethicsComOpinionReason;

        @Column(name="date_ethics_com_opinion")
        public Date ethicsComOpinionDate;

        @Column(name="country", length=255)
        public String country;

        // had to add this, or I got circular references when string building.
        @ToString.Exclude
        @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        @LazyCollection(LazyCollectionOption.FALSE)
        public List<ClinicalTrialEuropeProduct> clinicalTrialEuropeProductList = new ArrayList<>();

        public void setClinicalTrialEuropeProductList(List<ClinicalTrialEuropeProduct> clinicalTrialEuropeProductList) {
                this.clinicalTrialEuropeProductList = clinicalTrialEuropeProductList;
                if(clinicalTrialEuropeProductList !=null) {
                        for ( ClinicalTrialEuropeProduct ctp : clinicalTrialEuropeProductList )
                        {
                                ctp.setOwner(this);
                        }
                }
                // setIsDirty("clinicalTrialDrugEurope");
        }

        @JsonIgnore
        @Indexable(facet=true, name="Deprecated")
        public String getDeprecated(){
                return "Not Deprecated";
        }

        @ToString.Exclude
        @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        @LazyCollection(LazyCollectionOption.FALSE)
        public List<ClinicalTrialEuropeMedical> clinicalTrialEuropeMedicalList = new ArrayList<>();

        public void setClinicalTrialEuropeMedicalList(List<ClinicalTrialEuropeMedical> clinicalTrialEuropeMedicalList) {
                this.clinicalTrialEuropeMedicalList = clinicalTrialEuropeMedicalList;
                if(clinicalTrialEuropeMedicalList !=null) {
                        for ( ClinicalTrialEuropeMedical ctp : clinicalTrialEuropeMedicalList )
                        {
                                ctp.setOwner(this);
                        }
                }
        }


        @ToString.Exclude
        @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        @LazyCollection(LazyCollectionOption.FALSE)
        public List<ClinicalTrialEuropeMeddra> clinicalTrialEuropeMeddraList = new ArrayList<>();

        public void setClinicalTrialEuropeMeddraList(List<ClinicalTrialEuropeMeddra> clinicalTrialEuropeMeddraList) {
                this.clinicalTrialEuropeMeddraList = clinicalTrialEuropeMeddraList;
                if(clinicalTrialEuropeMeddraList !=null) {
                        for ( ClinicalTrialEuropeMeddra ctp : clinicalTrialEuropeMeddraList )
                        {
                                ctp.setOwner(this);
                        }
                }
        }


        @JsonSerialize(using = GsrsDateSerializer.class)
        @JsonDeserialize(using = GsrsDateDeserializer.class)
        @LastModifiedDate
        @Indexable( name = "Last Modified Date", sortable=true)
        public Date lastModifiedDate;

        @JsonSerialize(using = GsrsDateSerializer.class)
        @JsonDeserialize(using = GsrsDateDeserializer.class)
        @CreatedDate
        @Indexable( name = "Create Date", sortable=true)
        public Date creationDate;

        @Indexable(facet = true, name = "Record Created By")
        @Column(name = "created_by", length=500)
        private String createdBy;

        @Indexable(facet = true, name = "Record Last Edited By")
        @Column(name = "modified_by", length=500)
        private String modifiedBy;

        @PrePersist
        public void prePersist() {
                try {
                        UserProfile profile = (UserProfile) GsrsSecurityUtils.getCurrentUser();
                        if (profile != null) {
                                Principal p = profile.user;
                                if (p != null) {
                                        this.createdBy = p.username;
                                        this.modifiedBy = p.username;
                                }
                        }
                }catch (Exception ex) {
                        log.error("Exception while setting user in prePersist.", ex);
                }
        }

        @PreUpdate
        public void preUpdate() {
                try {
                        UserProfile profile = (UserProfile) GsrsSecurityUtils.getCurrentUser();
                        if (profile != null) {
                                Principal p = profile.user;
                                if (p != null) {
                                        this.modifiedBy = p.username;
                                }
                        }
                }catch (Exception ex) {
                        log.error("Exception while setting user in preUpdate.", ex);
                }
        }

        @Indexable(sortable = true)
        public String getTrialNumber() {
                return this.trialNumber;
        }

}

