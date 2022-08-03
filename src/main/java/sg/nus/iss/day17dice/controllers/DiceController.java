package sg.nus.iss.day17dice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.iss.day17dice.services.DiceService;

@RestController
@RequestMapping(path = "/roll")
public class DiceController {

    @Autowired
    private DiceService diceSvc;


    @GetMapping
    public ResponseEntity<String> getRoll(
        @RequestParam(name = "dice", defaultValue = "1")Integer count) {

            if((count < 1)|| (count > 10)){
                JsonObject errRes = Json.createObjectBuilder()
                                        .add("error", "INVALID, STAY BETWEEN 1-10. YOUR COUNT %d".formatted(count))
                                        .build();
                String payload = errRes.toString();
                //RETURN 400
                return ResponseEntity
                        //.status(HttpStatus.BAD_REQUEST)   <<alternative and more generic
                        .badRequest()
                        .body(payload);
            }

            //Get dice roll
            List<Integer> rolls = diceSvc.roll();

            //Create the response payload
        JsonObjectBuilder jb = Json.createObjectBuilder()
        .add("count", count);
        // or you can use 
        // jb = jb.add("count", count);
        JsonObject res = jb.build();

        return ResponseEntity.ok(res.toString());
    }

    
    
}
