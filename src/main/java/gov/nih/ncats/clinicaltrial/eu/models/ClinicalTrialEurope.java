package gov.nih.ncats.clinicaltrial.eu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Indexable;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@EqualsAndHashCode(exclude="clinicalTrialDrug")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="clinical_trial_eu")
// @ToString
public class ClinicalTrialEurope extends AbstractGsrsEntity {

        @Id
        @Column(name="EUDRACT_NUMBER")
        public String eudractNumber;

        public String getTrialNumber() {
          return this.eudractNumber;
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
        @JoinColumn(name = "EUDRACT_NUMBER", referencedColumnName = "EUDRACT_NUMBER")
        @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        public List<ClinicalTrialEuropeProduct> clinicalTrialEuropeProductList = new ArrayList<>();

        // had to add this or I got circular references when string building.
        @ToString.Exclude
        @JoinColumn(name = "EUDRACT_NUMBER", referencedColumnName = "EUDRACT_NUMBER")
        @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        public List<ClinicalTrialEuropeMedical> clinicalTrialEuropeMedicalList = new ArrayList<>();

        // had to add this or I got circular references when string building.
        @ToString.Exclude
        @JoinColumn(name = "EUDRACT_NUMBER", referencedColumnName = "EUDRACT_NUMBER")
        @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        public List<ClinicalTrialEuropeMeddra> clinicalTrialEuropeMeddraList = new ArrayList<>();

        // public ClinicalTrialEurope () {}
    }

