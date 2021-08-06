package de.novatec.tc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final KafkaBackendService kafkaBackendService;

    @Autowired
    KafkaController(KafkaBackendService kafkaBackendService) {
        this.kafkaBackendService = kafkaBackendService;
    }

    @GetMapping(value = "/asyncapi", produces = "application/json")
    public List<Map<String, Object>> getAsyncApiSummary() {
         return kafkaBackendService.getAsyncApiSummary().stream()
                 .map(JSONObject::toMap)
                 .collect(Collectors.toList());
    }

    @PostMapping(value = "/asyncapi", consumes = "application/json")
    public void publishAsyncApiDefinition(@RequestBody String body) {
        kafkaBackendService.publishAsyncApiDefinition(body);
    }

    @GetMapping(value = "/asyncapi/{artifactId}/latest", produces = "application/json")
    public Map<String, Object> getLatestVersionOfAsyncApi(@PathVariable String artifactId) {
        return kafkaBackendService.getLatestVersionOfAsyncApi(artifactId).toMap();
    }
}
