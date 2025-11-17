package vet.vetclinic.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.ai.tool.annotation.ToolParam;

@Getter
@Builder
public class GeneratedMedicalReportResponse {
    @JsonProperty("signalment")
    private String signalment;

    @JsonProperty("chief_complaint")
    private String chiefComplaint;

    @JsonProperty("assessment")
    private String assessment;

    @JsonProperty("plan")
    private String plan;

    @JsonProperty("postoperative_care")
    private String postoperativeCare;
}
