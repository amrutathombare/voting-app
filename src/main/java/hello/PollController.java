package hello;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


@RestController
public class PollController {

    int i = 1001;

    HashMap<Integer, ArrayList<String>> moderatorToPoll = new HashMap<Integer, ArrayList<String>>();
    HashMap<String, Poll> pollMap = new HashMap<String, Poll>();
    HashMap<String, Poll2> pollMap2 = new HashMap<String, Poll2>();
    @RequestMapping(method=RequestMethod.POST,value = "/api/v1/moderators/{moderator_Id}/polls")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Poll2 createPoll(@PathVariable String moderator_Id,
                           @RequestBody @Valid Poll2 p1) {

        String myid=Integer.toString(i, 36);
        int mod_Id = Integer.parseInt(moderator_Id);
        //String[] choiceArray = p1.getChoice().split(",");
        ArrayList<String> temp = new ArrayList<String>();
        if (moderatorToPoll.get(mod_Id) != null) {
            temp = moderatorToPoll.get(mod_Id);
        }
        //myid="1ADC2FZ";
        temp.add(myid);
        moderatorToPoll.put(mod_Id, temp);
        Poll p = new Poll(myid, p1.getQuestion(),p1.getstarted_at(), p1.getexpired_at(), p1.getChoice());

        Poll2 mypoll = new Poll2(myid, p1.getQuestion(),p1.getstarted_at(), p1.getexpired_at(), p1.getChoice());

        pollMap.put(myid, p);
        pollMap2.put(myid, mypoll);

        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(0);
        result.add(0);

        i++;
        return mypoll;
    }

    @RequestMapping(method=RequestMethod.GET, value = "/api/v1/polls/{poll_Id}")
    public Poll2 getPollWithoutResult(@PathVariable String poll_Id)
            {
        //int id = Integer.parseInt(poll_Id);
                //poll_Id="1ADC2FZ";

        Poll2 my = pollMap2.get(poll_Id);
        return my;
    }

    @RequestMapping(method=RequestMethod.GET, value = "/api/v1/moderators/{moderator_Id}/polls/{poll_Id}")
    public Poll getPollWithResult(@PathVariable String moderator_Id, @PathVariable String poll_Id) {
        //int pid = Integer.parseInt(poll_Id);
        int mid = Integer.parseInt(moderator_Id);
        ArrayList l = moderatorToPoll.get(mid);


        Poll p = null;
        if (l != null) {
            Iterator it = l.iterator();
            for (int j = 0; j < l.size(); j++) {
                if (l.contains(poll_Id)) {
                    p = pollMap.get(l.get(j));
                }
            }

        }

        return p;
    }

    @RequestMapping(method=RequestMethod.GET,value = "/api/v1/moderators/{moderator_Id}/polls")
    public Poll[] listAllPolls(@PathVariable String moderator_Id) {
        int mid = Integer.parseInt(moderator_Id);
        ArrayList l = moderatorToPoll.get(mid);
        int k = 0;
        Poll p[] = new Poll[l.size()];
        if (l != null) {
            for (int j = 0; j < l.size(); j++) {

                p[k] = pollMap.get(l.get(j));
                k++;
            }

        }

        return p;
    }

    @RequestMapping(method=RequestMethod.DELETE,value = "/api/v1/moderators/{moderator_Id}/polls/{poll_Id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deletePoll(@PathVariable String moderator_Id, @PathVariable String poll_Id) {

        int mid = Integer.parseInt(moderator_Id);
        ArrayList l = moderatorToPoll.get(mid);
        l.remove(poll_Id);


        moderatorToPoll.put(mid, l);
    }


    @RequestMapping(method=RequestMethod.PUT,value={"/polls/{poll_Id}"})
    public void giveVote(@PathVariable String poll_Id,
                         @RequestParam(value="choice", defaultValue="0") String choice) {
        // int id = Integer.parseInt(poll_Id);
        int ch = Integer.parseInt(choice);
        Poll p=pollMap.get(poll_Id);
        p.results[ch]++;
        pollMap.put(poll_Id,p);
    }


}