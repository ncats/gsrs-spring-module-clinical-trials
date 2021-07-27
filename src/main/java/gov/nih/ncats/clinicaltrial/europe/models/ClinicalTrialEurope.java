package gov.nih.ncats.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.nih.ncats.clinicaltrial.base.models.ClinicalTrialBase;
import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import ix.core.models.ParentReference;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Data
// @EqualsAndHashCode(exclude="clinicalTrialDrug")
@Entity
// @Builder
@AllArgsConstructor
// @NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
// @Backup
@Table(name="CTRIAL_EU")
// @ToString
@SuperBuilder
public class ClinicalTrialEurope extends ClinicalTrialBase {

        public ClinicalTrialEurope() {
             this.setKind("EUROPE");
        }

        // see base class for basic fields

        @Indexable(facet=true, name="Sponsor")
        @Column(name="SPONSOR_NAME", length=2000)
        public String sponsorName;

        @Indexable(facet=true, name="Trial Status")
        @Column(name="TRIAL_STATUS", length=2000)
        public String trialStatus;

        @Column(name="DATE_FIRST_ENTERED_DB")
        public Date dateFirstEnteredDb;

        @Column(name="TRIAL_RESULTS", length=2000)
        public String trialResults;

        @Indexable(facet=true, name="National Competent Authority")
        @Column(name="NATIONAL_COMPETENT_AUTH", length=2000)
        public String nationalCompetentAuthority;

        @Column(name="COMPETENT_AUTH_DECISION", length=2000)
        public String competentAuthorityDecision;

        @Column(name="DATE_COMP_AUTH_DECISION")
        public Date competentAuthorityDecisionDate;

        @Column(name="ETHICS_COM_OPINION_APP", length=2000)
        public String ethicsComOpinionApp;

        @Column(name="ETHICS_COM_OPINION_REASON", length=2000)
        public String ethicsComOpinionReason;

        @Column(name="DATE_ETHICS_COM_OPINION")
        public Date ethicsComOpinionDate;

        @Column(name="COUNTRY", length=255)
        public String country;


        // had to change to lazy to prevent multiple bag exception

        // @JsonIgnore
        // had to add this or I got circular references when string building.
        @ToString.Exclude
        // @ParentReference
        @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        public List<ClinicalTrialEuropeProduct> clinicalTrialEuropeProductList = new ArrayList<>();


        public void setClinicalTrialEuropeProductList(List<ClinicalTrialEuropeProduct> clinicalTrialEuropeProductList) {
                System.out.println("... setClinicalTrialEuropeProductList");
                System.out.println("HERE1");
                this.clinicalTrialEuropeProductList = clinicalTrialEuropeProductList;
                System.out.println("HERE2");
                System.out.println("TN: "  + this.trialNumber);
                if(clinicalTrialEuropeProductList !=null) {
                        // System.out.println("HERE3");
                        for ( ClinicalTrialEuropeProduct ctp : clinicalTrialEuropeProductList )
                        {
                                // System.out.println("HERE4" + ctd.getSubstanceKeyType());
                                ctp.setOwner(this);
                                // modified so grandchild produt_id gets correctly.
                                //     if(ctp.getClinicalTrialEuropeDrugList() != null) {
                                //             for (ClinicalTrialEuropeDrug ctd : ctp.getClinicalTrialEuropeDrugList()) {
                                //                     ctd.setOwner(ctp);
                                //             }
                                //    }
                // System.out.println("HERE5");
                        }
                }
                // setIsDirty("clinicalTrialDrugEurope");
        }

        @JsonIgnore
        @Indexable(facet=true, name="Deprecated")
        public String getDeprecated(){
                return "Not Deprecated";
        }


        // had to add this or I got circular references when string building.
        @ToString.Exclude
        @JoinColumn(name = "TRIAL_NUMBER", referencedColumnName = "TRIAL_NUMBER")
        @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        public List<ClinicalTrialEuropeMedical> clinicalTrialEuropeMedicalList = new ArrayList<>();

        // had to add this or I got circular references when string building.
        @ToString.Exclude
        @JoinColumn(name = "TRIAL_NUMBER", referencedColumnName = "TRIAL_NUMBER")
        @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        public List<ClinicalTrialEuropeMeddra> clinicalTrialEuropeMeddraList = new ArrayList<>();

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

        public String getTrialNumber() {
                return this.trialNumber;
        }

}

