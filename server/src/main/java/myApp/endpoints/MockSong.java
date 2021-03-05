//This is Spring file, just like Nodejs.

package myApp.endpoints;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MockSong {
    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return "hi"
     */
    @GetMapping("test")
    @ResponseBody
    public String GetTest(){
        return "Test song";
    }
}
