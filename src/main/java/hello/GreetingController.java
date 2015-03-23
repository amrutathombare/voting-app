package hello;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RestController
public class GreetingController {

    //private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    int i=1;
    HashMap<Integer,Greeting> modMap=new HashMap<Integer, Greeting>();
    //private Date date =new Date();
    @RequestMapping(method=RequestMethod.POST,value={"/api/v1/moderators"},consumes= "application/json", produces="application/json")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Greeting greeting(@RequestBody @Valid Greeting g) {
        // @RequestParam(value="name", defaultValue="NA") String name, @RequestParam(value="email", defaultValue="NA") String email, @RequestParam(value="password", defaultValue="NA") String password) {
        long tempId=counter.incrementAndGet();
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'dd:HH:mm.sss'Z'");
        df.setTimeZone(tz);
        String myLocalTime = df.format(new Date());
        //Moderator myModerator=new Moderator( name, email, password, myLocalTime);
        Greeting greet= new Greeting((int)tempId,g.getName(),g.getEmail(),g.getPassword(),myLocalTime);
        modMap.put((int)tempId,greet);
        i++;
        return greet;
    }

    @RequestMapping(method=RequestMethod.GET, value={"/api/v1/moderators/{moderator_Id}"})
    public Greeting getFromId(@PathVariable String moderator_Id)
    {
        int id = Integer.parseInt(moderator_Id);
        return modMap.get(id);
    }

    @RequestMapping(method=RequestMethod.PUT,value={"/api/v1/moderators/{moderator_Id}"})
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Greeting updateFromId(@PathVariable String moderator_Id, @RequestBody @Valid Greeting g){
        int id = Integer.parseInt(moderator_Id);
        Greeting greet= modMap.get(id);
        greet.setEmail(g.getEmail());
        greet.setPassword(g.getPassword());
        modMap.remove(id);
        modMap.put(id,greet);
        //greet= Greeting((int) tempId, g.getName(), g.getEmail(), g.getPassword(), myLocalTime);
        return greet;
    }

    //@RequestParam(value="email", defaultValue="NA") String email, @RequestParam(value="password", defaultValue="NA") String password)

}