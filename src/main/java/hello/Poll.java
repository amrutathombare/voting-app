
package hello;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Poll extends Poll2{


    public int [] results;

    @JsonCreator
    public Poll(@JsonProperty("id") String id, @JsonProperty("question") String question, @JsonProperty("started_at") String started_At,
                @JsonProperty("expired_at") String expired_at ,@JsonProperty("choice") String choice[]) {
        super();
        this.id = id;
        this.question = question;
        this.started_At = started_At;
        this.expired_At = expired_at;
        this.choice = choice;
        this.results = new int[choice.length];
    }


}