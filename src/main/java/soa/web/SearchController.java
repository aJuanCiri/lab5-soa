package soa.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        Map<String,Object> header = new HashMap();
        if(q.indexOf("max:") != -1) {
            String[] allData = q.split("max:");
            String keywords = allData[0];
            int n = Integer.parseInt(allData[1]);
            header.put("CamelTwitterCount",n);
            header.put("CamelTwitterKeywords", keywords);
        } else {
            header.put("CamelTwitterKeywords",q);
        }
        return producerTemplate.requestBodyAndHeader("direct:search", "", header);
    }
}