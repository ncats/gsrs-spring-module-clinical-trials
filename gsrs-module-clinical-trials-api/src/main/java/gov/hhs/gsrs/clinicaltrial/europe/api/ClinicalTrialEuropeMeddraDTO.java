package gov.hhs.gsrs.clinicaltrial.europe.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ix.core.models.ParentReference;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

public class ClinicalTrialEuropeMeddraDTO {

    private int id;
    private String meddraVersion;
    private String meddraClassCode;
    private String meddraTerm;
    private String meddraSystemOrganClass;
}
