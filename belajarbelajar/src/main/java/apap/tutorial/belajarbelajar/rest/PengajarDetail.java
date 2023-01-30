package apap.tutorial.belajarbelajar.rest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Data
public class PengajarDetail {
    @JsonProperty("name")
    private String namaPengajar;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("probability")
    private Integer probability;
}
