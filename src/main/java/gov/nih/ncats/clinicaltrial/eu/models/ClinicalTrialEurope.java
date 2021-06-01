package gov.nih.ncats.clinicaltrial.eu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialDrug;
import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Indexable;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

@Data
// @EqualsAndHashCode(exclude="clinicalTrialDrug")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.ALWAYS)
@Table(name="clinical_trial_eu")
// @ToString

public class ClinicalTrialEurope extends AbstractGsrsEntity {

        @Id
        @Column(name="TRIAL_NUMBER")
        public String trialNumber;

        public String getTrialNumber() {
          return this.trialNumber;
        }

        @Column(name="TITLE")
        public String title;

        @Indexable(facet=true, name="Sponsor")
        @Column(name="SPONSOR_NAME")
        public String sponsorName;

        @Indexable(facet=true, name="Trial Status")
        @Column(name="TRIAL_STATUS")
        public String trialStatus;

        @Column(name="DATE_FIRST_ENTERED_DB")
        public Date dateFirstEnteredDb;

        @Column(name="TRIAL_RESULTS")
        public String trialResults;

        @Indexable(facet=true, name="National Competent Authority")
        @Column(name="NATIONAL_COMPETENT_AUTH")
        public String nationalCompetentAuthority;

        @Column(name="COMPETENT_AUTH_DECISION")
        public String competentAuthorityDecision;

        @Column(name="DATE_COMP_AUTH_DECISION")
        public Date competentAuthorityDecisionDate;

        @Column(name="ETHICS_COM_OPINION_APP")
        public String ethicsComOpinionApp;

        @Column(name="ETHICS_COM_OPINION_REASON")
        public String ethicsComOpinionReason;

        @Column(name="DATE_ETHICS_COM_OPINION")
        public Date ethicsComOpinionDate;

        @Column(name="COUNTRY")
        public String country;

        @Column(name="URL")
        public String url;

        // had to change to lazy to prevent multiple bag exception

        // @JsonIgnore
        // had to add this or I got circular references when string building.
        @ToString.Exclude
        // @JoinColumn(name = "TRIAL_NUMBER", referencedColumnName = "TRIAL_NUMBER")
        // was @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        //new
        @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        public List<ClinicalTrialEuropeProduct> clinicalTrialEuropeProductList = new ArrayList<>();

        public void setClinicalTrialEuropeProductList(List<ClinicalTrialEuropeProduct> clinicalTrialEuropeProductList) {
                System.out.println("HERE0");
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

                                if(ctp.getClinicalTrialEuropeDrugList() != null) {
                                        for (ClinicalTrialEuropeDrug ctd : ctp.getClinicalTrialEuropeDrugList()) {
                                                ctd.setOwner1(ctp);
                                        }
                                }



                                // System.out.println("HERE5");
                        }
                }
                // setIsDirty("clinicalTrialDrug");
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
        private Date lastModifiedDate;
        @JsonSerialize(using = GsrsDateSerializer.class)
        @JsonDeserialize(using = GsrsDateDeserializer.class)
        @CreatedDate
        @Indexable( name = "Create Date", sortable=true)
        private Date creationDate;
    }

